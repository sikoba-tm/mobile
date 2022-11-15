package com.tekmob.sikoba.ui.pengguna.cariKorban

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.tekmob.sikoba.databinding.ActivityCariKorbanBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.rotateBitmap
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.camera.CameraActivity
import com.tekmob.sikoba.ui.pengguna.hasilPencarian.HasilPencarianActivity
import com.tekmob.sikoba.uriToFile
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

        viewModel = ViewModelProvider(this, ViewModelFactory())[CariKorbanViewModel::class.java]
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

    private fun cariKorban() {
        val intent = Intent(this@CariKorbanActivity, HasilPencarianActivity::class.java)
        intent.putExtra(BENCANA, bencana)
        startActivity(intent)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val BENCANA = "bencana"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val PICTURE = "picture"
        const val IS_BACK_CAMERA = "isBackCamera"
    }

}