package com.tekmob.sikoba.ui.detailBencana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.databinding.ActivityDetailBencanaBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.adapter.ListKorbanAdapter
import com.tekmob.sikoba.ui.daftarBencana.DaftarBencanaViewModel

class DetailBencanaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBencanaBinding
    private val viewModel: DaftarBencanaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvKorban.layoutManager = layoutManager

        val bencana = intent.getParcelableExtra<Bencana>(BENCANA) as Bencana
        viewModel.setBencana(bencana)

        viewModel.bencana.observe(this){
            setBencana(it)
        }

        viewModel.listKorban.observe(this){
            setListKorban(it)
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