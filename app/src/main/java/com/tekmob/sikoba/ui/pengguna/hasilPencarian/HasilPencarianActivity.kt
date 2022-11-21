package com.tekmob.sikoba.ui.pengguna.hasilPencarian

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.dataStore
import com.tekmob.sikoba.databinding.ActivityHasilPencarianBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.adapter.ListKorbanAdapter
import com.tekmob.sikoba.ui.pengguna.detailHasilPencarian.DetailHasilPencarianActivity

class HasilPencarianActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHasilPencarianBinding
    private lateinit var viewModel: HasilPencarianViewModel
    private lateinit var bencana: Bencana

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilPencarianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Hasil Pencarian"

        viewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[HasilPencarianViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvKorban.layoutManager = layoutManager

        bencana = intent.getParcelableExtra<Bencana>(BENCANA) as Bencana
        setBencana(bencana)

        bencana.id?.let {
            viewModel.getDaftarKorban(it).observe(this){ res ->
                when(res){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setListKorban(res.data)
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
    }

    private fun setBencana(bencana: Bencana){
        binding.bencanaCard.apply {
            nama.text = bencana.nama
            lokasi.text = bencana.lokasi
            tanggalKejadian.text = bencana.tanggalKejadian.toString()
        }
    }

    private fun setListKorban(listKorban : List<Korban>) {
        val adapter = ListKorbanAdapter(listKorban, this)
        binding.rvKorban.adapter = adapter
        adapter.setOnItemClickCallback(object : ListKorbanAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Korban) {
                val intent = Intent(this@HasilPencarianActivity, DetailHasilPencarianActivity::class.java)
                intent.putExtra(ID_KORBAN, data.id)
                intent.putExtra(ID_BENCANA, bencana.id)
                startActivity(intent)
            }
        } )
    }

    companion object {
        const val BENCANA = "bencana"
        const val ID_BENCANA = "id_bencana"
        const val ID_KORBAN = "id_korban"
    }
}