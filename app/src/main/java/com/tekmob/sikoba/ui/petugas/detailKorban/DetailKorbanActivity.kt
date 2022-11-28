package com.tekmob.sikoba.ui.petugas.detailKorban

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tekmob.sikoba.R
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.dataStore
import com.tekmob.sikoba.databinding.ActivityDetailKorbanBinding
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.petugas.ubahKorban.UbahKorbanActivity

class DetailKorbanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKorbanBinding
    private lateinit var viewModel: DetailKorbanViewModel
    private var idBencana: Int = 0
    private lateinit var idKorban: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKorbanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Korban"
        idBencana = intent.getIntExtra(ID_BENCANA, 0)
        idKorban = intent.getStringExtra(ID_KORBAN) as String

        setupViewModel()
        setupAction()
    }

    fun setupAction(){
        binding.buttonHapus.setOnClickListener { hapusKorban() }
        binding.buttonUbah.setOnClickListener { ubahKorban() }
    }

    fun setupViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[DetailKorbanViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
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

    fun hapusKorban(){
        AlertDialog.Builder(this).apply {
            setTitle("Peringatan!")
            setMessage("Anda yakin ingin menghapus data korban?")
            setPositiveButton("Hapus") { _,_ ->
                viewModel.hapusKorban(idBencana, idKorban)
                finish()
            }
            setNegativeButton("Kembali") { _, _ ->
            }
            create()
            show()
        }
    }

    fun ubahKorban(){
        val intent = Intent(this@DetailKorbanActivity, UbahKorbanActivity::class.java)
        intent.putExtra(ID_KORBAN, idKorban)
        intent.putExtra(ID_BENCANA, idBencana)
        startActivity(intent)
    }

    fun setKorban(korban : Korban){
        binding.apply {
            Glide.with(this@DetailKorbanActivity)
                .load(korban.foto)
                .into(binding.foto)
            nama.text = korban.nama
            tempatTanggalLahir.text = getString(R.string.tempat_tanggal_lahir, (if (korban.tempatLahir == "") "Jakarta" else korban.tempatLahir), korban.tanggalLahir?.slice(IntRange(0,9)))
            namaIbuKandung.text = if (korban.namaIbuKandung == "") "Fulanah" else korban.namaIbuKandung
            kondisi.text = korban.kondisi
            namaPosko.text = korban.posko?.nama ?: ""
            alamatPosko.text = korban.posko?.alamat ?: ""
            namaPj.text = korban.posko?.namaPj ?: ""
            noTelpPj.text = korban.posko?.notelpPj ?: ""
        }
    }

    companion object{
        const val ID_BENCANA = "id_bencana"
        const val ID_KORBAN = "id_korban"
    }

}