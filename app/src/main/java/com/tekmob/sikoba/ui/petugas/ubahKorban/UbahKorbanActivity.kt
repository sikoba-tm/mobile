package com.tekmob.sikoba.ui.petugas.ubahKorban

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.datepicker.MaterialDatePicker
import com.tekmob.sikoba.*
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.databinding.ActivityUbahKorbanBinding
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.Posko
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.camera.CameraActivity
import com.tekmob.sikoba.ui.petugas.detailKorban.DetailKorbanActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UbahKorbanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUbahKorbanBinding
    private lateinit var viewModel: UbahKorbanViewModel
    private lateinit var listPosko: List<Posko>
    private var selectedPoskoPosition: Int = 0
    private var idBencana: Int = 0
    private lateinit var idKorban: String
    private lateinit var korban: Korban
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")


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
                val myFile = uriToFile(selectedImage, this@UbahKorbanActivity)

                getFile = myFile
                binding.fotoKorban.setImageURI(selectedImage)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahKorbanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Ubah Korban"

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        idBencana = intent.getIntExtra(ID_BENCANA, 0)
        idKorban = intent.getStringExtra(DetailKorbanActivity.ID_KORBAN) as String

        setupViewModel()
        setupAction()

    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[UbahKorbanViewModel::class.java]

        viewModel.getKorban(idBencana, idKorban).observe(this){ res ->
            when(res){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setKorban(res.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle("Gagal!")
                        setMessage("Terjadi kesalahan")
                        setNegativeButton("Tutup") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    fun setKorban(korban : Korban){
        binding.apply {
            Glide.with(this@UbahKorbanActivity)
                .load(korban.foto + "?c=" + System.currentTimeMillis())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions().signature(ObjectKey("updated")))
                .into(binding.fotoKorban)
            editNama.setText(korban.nama)
            editTempatLahir.setText(korban.tempatLahir)
            editTanggalLahir.setText(korban.tanggalLahir?.let {
                inputFormatter.parse(it)?.let {
                    dateFormatter.format(it)
                }
            })
            editIbu.setText(korban.namaIbuKandung)
        }
        this@UbahKorbanActivity.korban = korban
    }

    private fun setupAction() {
        // Kondisi drop-down
        val kondisi = resources.getStringArray(R.array.kondisi)
        val kondisiArrayAdapter = ArrayAdapter(this, R.layout.dropdown_textview, kondisi)
        binding.editKondisi.setAdapter(kondisiArrayAdapter)

        // Rentang umur drop-down
        val rentangUmur = resources.getStringArray(R.array.rentang_umur)
        val rentangUmurArrayAdapter = ArrayAdapter(this, R.layout.dropdown_textview, rentangUmur)
        binding.editRentangUmur.setAdapter(rentangUmurArrayAdapter)

        binding.editTanggalLahir.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val date = dateFormatter.format(Date(it))
                binding.editTanggalLahir.setText(date)
            }
        }

        binding.buttonKamera.setOnClickListener { startCamera() }
        binding.buttonGaleri.setOnClickListener { startGallery() }

        binding.buttonSimpan.setOnClickListener { ubahKorban() }

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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun ubahKorban(){
        val data = mutableMapOf<String, RequestBody>()
        data["nama"] = toRequestBody(binding.editNama.text)
        data["tempat_lahir"] = toRequestBody(binding.editTempatLahir.text)
        data["tanggal_lahir"] =
            "${strToTimestamp(binding.editTanggalLahir.text.toString())}T00:00:00+07:00"
                .toRequestBody("text/plain".toMediaType())
        data["rentang_usia"] = toRequestBody(binding.editRentangUmur.text)
        data["nama_ibu_kandung"] = toRequestBody(binding.editIbu.text)
        data["kondisi"] = toRequestBody(binding.editKondisi.text)
        data["foto_url"] = korban.foto.toString().toRequestBody("text/plain".toMediaType())

        if (getFile !== null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "foto",
                file.name,
                requestImageFile
            )
            viewModel.ubahKorban(idBencana, idKorban, imageMultipart, data).observe(this) {
                resultUbahKorban(it);
            }
        } else {
            viewModel.ubahKorban(idBencana, idKorban, null, data).observe(this) {
                resultUbahKorban(it);
            }
        }
    }

    private fun resultUbahKorban(it: Result<Korban>) {
        when(it){
            is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                AlertDialog.Builder(this).apply {
                    setTitle("Berhasil!")
                    setMessage("Data korban berhasil diubah")
                    setPositiveButton("Lihat Detail Korban") { _,_ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                AlertDialog.Builder(this).apply {
                    setTitle("Gagal!")
                    setMessage("Terjadi kesalahan")
                    setNegativeButton("Tutup") { _, _ ->
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun toRequestBody(editable: Editable?) =
        editable.toString().toRequestBody("text/plain".toMediaType())

    private fun strToTimestamp(stringDate: String) : String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(stringDate))
    }

    companion object{
        const val ID_KORBAN = "id_korban"
        const val ID_BENCANA = "id_bencana"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val PICTURE = "picture"
        const val IS_BACK_CAMERA = "isBackCamera"
    }
}