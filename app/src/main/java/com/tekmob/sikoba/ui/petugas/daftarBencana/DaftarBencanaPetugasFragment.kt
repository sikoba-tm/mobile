package com.tekmob.sikoba.ui.petugas.daftarBencana

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.R
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.databinding.FragmentDaftarBencanaPetugasBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.adapter.ListBencanaAdapter
import com.tekmob.sikoba.ui.petugas.detailBencana.DetailBencanaActivity

class DaftarBencanaPetugasFragment : Fragment() {

    private var _binding : FragmentDaftarBencanaPetugasBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DaftarBencanaPetugasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, ViewModelFactory())[DaftarBencanaPetugasViewModel::class.java]

        _binding = FragmentDaftarBencanaPetugasBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvBencana.layoutManager = layoutManager

        setListBencana()

        return binding.root
    }

    private fun setListBencana() {
        viewModel.getDaftarBencana().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setListBencanaContent(res.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    context?.let {
                        AlertDialog.Builder(it).apply {
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

    private fun setListBencanaContent(listBencana : List<Bencana>){
        val adapter = ListBencanaAdapter(listBencana)
        binding.rvBencana.adapter = adapter
        adapter.setOnItemClickCallback(object : ListBencanaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Bencana) {
                val detailBencanaIntent = Intent(context, DetailBencanaActivity::class.java)
                detailBencanaIntent.putExtra(DetailBencanaActivity.BENCANA, data)
                startActivity(detailBencanaIntent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}