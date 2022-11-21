package com.tekmob.sikoba.ui.pengguna.cariKorban

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.dataStore
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.databinding.ActivityCariKorbanBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.reduceFileImage
import com.tekmob.sikoba.rotateBitmap
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.camera.CameraActivity
import com.tekmob.sikoba.ui.pengguna.hasilPencarian.HasilPencarianActivity
import com.tekmob.sikoba.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CariKorbanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCariKorbanBinding
    private lateinit var viewModel: CariKorbanViewModel
    private lateinit var bencana: Bencana
    private var getFile: File? = null
    private val launcherIntentCameraX =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = it.data?.getSerializableExtra(PICTURE) as File
                val isBackCamera = it.data?.getBooleanExtra(IS_BACK_CAMERA, true) as Boolean

                getFile = myFile
                val result = rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )
                binding.fotoKorban.setImageBitmap(result)
            }
        }
    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                val selectedImage: Uri = it.data?.data as Uri
                val myFile = uriToFile(selectedImage, this@CariKorbanActivity)

                getFile = myFile
                binding.fotoKorban.setImageURI(selectedImage)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCariKorbanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Cari Korban"

        viewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[CariKorbanViewModel::class.java]
        bencana = intent.getParcelableExtra<Bencana>(BENCANA) as Bencana

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupAction()
    }

    private fun setupAction() {
        binding.buttonKamera.setOnClickListener { startCamera() }
        binding.buttonGaleri.setOnClickListener { startGallery() }
        binding.buttonCari.setOnClickListener { cariKorban() }
    }

    private fun startCamera(){
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih media")
        launcherIntentGallery.launch(chooser)
    }

    private fun toRequestBody(editable: Editable?): RequestBody {
        return editable.toString().toRequestBody("text/plain".toMediaType())
    }

    private fun cariKorban(){
        val data = mutableMapOf<String, RequestBody>()
        data["nama"] = toRequestBody(binding.editUsername.text)
        if (getFile == null) {
            AlertDialog.Builder(this).apply {
                setMessage("Harap Masukkan Foto")
                setNegativeButton("Tutup") { _, _ ->
                }
                create()
                show()
            }
            return
        }
        val file = reduceFileImage(getFile as File)
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart = MultipartBody.Part.createFormData(
            "foto",
            file.name,
            requestImageFile
        )

        bencana.id?.let { bencanaId ->
            viewModel.cariKorban(bencanaId, imageMultipart, data).observe(this) {
                val intent = Intent(this@CariKorbanActivity, HasilPencarianActivity::class.java)
                when(it){
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val lk = ArrayList<Korban>(it.data)
                        intent.putParcelableArrayListExtra(LIST_KORBAN, lk as java.util.ArrayList<Parcelable>);
                        intent.putExtra(BENCANA, bencana)
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        intent.putParcelableArrayListExtra(LIST_KORBAN, ArrayList<Parcelable>());
                        intent.putExtra(BENCANA, bencana)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val BENCANA = "bencana"
        const val LIST_KORBAN = "list_korban"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val PICTURE = "picture"
        const val IS_BACK_CAMERA = "isBackCamera"
    }

}