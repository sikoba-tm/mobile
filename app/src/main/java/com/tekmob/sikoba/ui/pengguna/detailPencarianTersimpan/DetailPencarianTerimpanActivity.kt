package com.tekmob.sikoba.ui.pengguna.detailPencarianTersimpan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tekmob.sikoba.R
import com.tekmob.sikoba.databinding.ActivityDetailPencarianTerimpanBinding
import com.tekmob.sikoba.model.KorbanLocal
import com.tekmob.sikoba.model.PoskoLocal
import com.tekmob.sikoba.ui.pengguna.pencarianTersimpan.PencarianTersimpanViewModel
import com.tekmob.sikoba.ui.pengguna.pencarianTersimpan.ViewModelFactoryPencarianTersimpan

class DetailPencarianTerimpanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPencarianTerimpanBinding
    private lateinit var viewModel: PencarianTersimpanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPencarianTerimpanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Korban"

        viewModel = ViewModelProvider(
            this,
            ViewModelFactoryPencarianTersimpan(application)
        )[PencarianTersimpanViewModel::class.java]

        val idKorban = intent.getStringExtra(ID_KORBAN) as String

        viewModel.getKorbanAndPoskoById(idKorban).observe(this){ korbanAndPosko ->
            if (korbanAndPosko != null) {
                setKorban(korbanAndPosko.korban)
                setPosko(korbanAndPosko.posko)
            }
        }
    }

    fun setKorban(korbanLocal: KorbanLocal){
        binding.apply {
            Glide.with(this@DetailPencarianTerimpanActivity)
                .load(korbanLocal.foto)
                .into(binding.foto)
            nama.text = korbanLocal.nama
            tempatTanggalLahir.text = getString(R.string.tempat_tanggal_lahir, (if (korbanLocal.tempatLahir == "") "-" else korbanLocal.tempatLahir), korbanLocal.tanggalLahir?.slice(IntRange(0,9)))
            namaIbuKandung.text = if (korbanLocal.namaIbuKandung == "") "-" else korbanLocal.namaIbuKandung
            kondisi.text = korbanLocal.kondisi
            fabHapus.setOnClickListener{
                AlertDialog.Builder(this@DetailPencarianTerimpanActivity).apply {
                    setTitle("Hapus Data Korban dari Pencarian Tersimpan")
                    setMessage("Anda yakin ingin menghapus data korban dari pencarian tersimpan?")
                    setPositiveButton("Hapus") { _,_ ->
                        viewModel.deleteKorban(korbanLocal)
                        finish()
                    }
                    setNegativeButton("Batal") { _, _ ->
                    }
                    create()
                    show()
                }
            }
        }
    }

    fun setPosko(poskoLocal: PoskoLocal){
        binding.apply {
            namaPosko.text = poskoLocal.nama
            alamatPosko.text = poskoLocal.alamat
            namaPj.text = poskoLocal.namaPj
            noTelpPj.text = poskoLocal.notelpPj
        }
    }

    companion object{
        const val ID_KORBAN = "id_korban"
    }
}