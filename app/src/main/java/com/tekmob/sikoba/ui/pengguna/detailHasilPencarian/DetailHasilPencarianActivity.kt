package com.tekmob.sikoba.ui.pengguna.detailHasilPencarian

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tekmob.sikoba.R
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.databinding.ActivityDetailHasilPencarianBinding
import com.tekmob.sikoba.databinding.ActivityDetailKorbanBinding
import com.tekmob.sikoba.databinding.ActivityHasilPencarianBinding
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.pengguna.hasilPencarian.HasilPencarianViewModel
import com.tekmob.sikoba.ui.petugas.detailKorban.DetailKorbanViewModel

class DetailHasilPencarianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHasilPencarianBinding
    private lateinit var viewModel: DetailHasilPencarianViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHasilPencarianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Korban"

        viewModel = ViewModelProvider(this, ViewModelFactory())[DetailHasilPencarianViewModel::class.java]
        val idBencana = intent.getIntExtra(ID_BENCANA, 0)
        val idKorban = intent.getIntExtra(ID_KORBAN, 0)
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
            Glide.with(this@DetailHasilPencarianActivity)
                .load(korban.foto)
                .into(binding.foto)
            nama.text = korban.nama
            tempatTanggalLahir.text = getString(R.string.tempat_tanggal_lahir, (if (korban.tempatLahir == "") "-" else korban.tempatLahir), korban.tangalLahir)
            namaIbuKandung.text = if (korban.namaIbuKandung == "") "-" else korban.namaIbuKandung
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