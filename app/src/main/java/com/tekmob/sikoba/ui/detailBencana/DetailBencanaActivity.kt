package com.tekmob.sikoba.ui.detailBencana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.databinding.ActivityDetailBencanaBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.adapter.ListKorbanAdapter
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.ui.daftarBencana.DaftarBencanaViewModel

class DetailBencanaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBencanaBinding
    private lateinit var viewModel: DetailBencanaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory())[DetailBencanaViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvKorban.layoutManager = layoutManager

        val bencana = intent.getParcelableExtra<Bencana>(BENCANA) as Bencana
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
    }

    companion object {
        private const val TAG = "DetailBencanaActivity"
        const val BENCANA = "bencana"
    }
}