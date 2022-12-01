package com.tekmob.sikoba.ui.pengguna.detailHasilPencarian

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.tekmob.sikoba.R
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.dataStore
import com.tekmob.sikoba.databinding.ActivityDetailHasilPencarianBinding
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanLocal
import com.tekmob.sikoba.model.PoskoLocal
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.pengguna.pencarianTersimpan.PencarianTersimpanViewModel
import com.tekmob.sikoba.ui.pengguna.pencarianTersimpan.ViewModelFactoryPencarianTersimpan

class DetailHasilPencarianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHasilPencarianBinding
    private lateinit var detailHasilPencarianViewModel: DetailHasilPencarianViewModel
    private lateinit var pencarianTerimpanViewModel: PencarianTersimpanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHasilPencarianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Korban"

        detailHasilPencarianViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailHasilPencarianViewModel::class.java]

        pencarianTerimpanViewModel = ViewModelProvider(
            this,
            ViewModelFactoryPencarianTersimpan(application)
        )[PencarianTersimpanViewModel::class.java]

        val idBencana = intent.getIntExtra(ID_BENCANA, 0)
        val idKorban = intent.getStringExtra(ID_KORBAN) as String
        detailHasilPencarianViewModel.getKorban(idBencana, idKorban).observe(this){ res ->
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

        pencarianTerimpanViewModel.getKorbanById(idKorban).observe(this){
            if (it == null){
                binding.fab.visibility = View.VISIBLE
                binding.fabHapus.visibility = View.GONE
            } else {
                binding.fab.visibility = View.GONE
                binding.fabHapus.visibility = View.VISIBLE
            }
        }
    }

    fun setKorban(korban : Korban){
        binding.apply {
            Glide.with(this@DetailHasilPencarianActivity)
                .load(korban.foto + "?c=" + System.currentTimeMillis())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions().signature(ObjectKey("updated")))
                .into(binding.foto)
            nama.text = korban.nama
            tempatTanggalLahir.text = getString(R.string.tempat_tanggal_lahir, (if (korban.tempatLahir == "") "-" else korban.tempatLahir), korban.tanggalLahir?.slice(IntRange(0,9)))
            namaIbuKandung.text = if (korban.namaIbuKandung == "") "-" else korban.namaIbuKandung
            kondisi.text = korban.kondisi
            namaPosko.text = korban.posko?.nama ?: ""
            alamatPosko.text = korban.posko?.alamat ?: ""
            namaPj.text = korban.posko?.namaPj ?: ""
            noTelpPj.text = korban.posko?.notelpPj ?: ""
            fab.setOnClickListener{
                AlertDialog.Builder(this@DetailHasilPencarianActivity).apply {
                    setTitle("Simpan Data Korban")
                    setMessage("Anda yakin ingin menyimpan data korban di pencarian tersimpan?")
                    setPositiveButton("Simpan") { _,_ ->
                        val posko = korban.posko
                        if (posko != null){
                            posko.id?.let {
                                pencarianTerimpanViewModel.insertPosko(
                                    PoskoLocal(
                                        nama = posko.nama,
                                        namaPj = posko.namaPj,
                                        notelpPj = posko.notelpPj,
                                        id = posko.id,
                                        alamat = posko.alamat
                                    )
                                )
                            }
                        }
                        korban.id?.let {
                            pencarianTerimpanViewModel.insertKorban(
                                KorbanLocal(
                                    id = korban.id,
                                    nama = korban.nama,
                                    foto = korban.foto,
                                    tanggalLahir = korban.tanggalLahir,
                                    tempatLahir = korban.tempatLahir,
                                    kondisi = korban.kondisi,
                                    namaIbuKandung = korban.namaIbuKandung,
                                    rentangUsia = korban.rentangUsia,
                                    poskoId = korban.posko?.id
                                )
                            )
                        }
                        Toast.makeText(this@DetailHasilPencarianActivity, "Data korban berhasil disimpan", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Batal") { _, _ ->
                    }
                    create()
                    show()
                }
            }
            fabHapus.setOnClickListener{
                AlertDialog.Builder(this@DetailHasilPencarianActivity).apply {
                    setTitle("Hapus Data Korban dari Pencarian Tersimpan")
                    setMessage("Anda yakin ingin menghapus data korban dari pencarian tersimpan?")
                    setPositiveButton("Hapus") { _,_ ->
                        korban.id?.let { idKorban -> pencarianTerimpanViewModel.deleteKorbanById(idKorban) }
                        Toast.makeText(this@DetailHasilPencarianActivity, "Data korban berhasil dihapus dari pencarian tersimpan", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Batal") { _, _ ->
                    }
                    create()
                    show()
                }
            }
        }
    }

    companion object{
        const val ID_BENCANA = "id_bencana"
        const val ID_KORBAN = "id_korban"
    }
}