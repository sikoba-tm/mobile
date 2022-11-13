package com.tekmob.sikoba.ui.petugas.detailBencana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.databinding.ActivityDetailBencanaBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.adapter.ListKorbanAdapter
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.ui.petugas.detailKorban.DetailKorbanActivity
import com.tekmob.sikoba.ui.petugas.tambahKorban.TambahKorbanActivity

class DetailBencanaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBencanaBinding
    private lateinit var viewModel: DetailBencanaViewModel
    private lateinit var bencana: Bencana

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Bencana"

        viewModel = ViewModelProvider(this, ViewModelFactory())[DetailBencanaViewModel::class.java]

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

        binding.fab.setOnClickListener {
            val intent = Intent(this@DetailBencanaActivity, TambahKorbanActivity::class.java)
            intent.putExtra(TambahKorbanActivity.ID_BENCANA, bencana.id)
            startActivity(intent)
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
                val intent = Intent(this@DetailBencanaActivity, DetailKorbanActivity::class.java)
                intent.putExtra(DetailKorbanActivity.ID_KORBAN, data.id)
                intent.putExtra(DetailKorbanActivity.ID_BENCANA, bencana.id)
                startActivity(intent)
            }
        } )

    }

    companion object {
        private const val TAG = "DetailBencanaActivity"
        const val BENCANA = "bencana"
    }
}