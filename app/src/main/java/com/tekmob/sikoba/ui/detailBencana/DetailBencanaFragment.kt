package com.tekmob.sikoba.ui.detailBencana

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.R
import com.tekmob.sikoba.databinding.FragmentDetailBencanaBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.ui.adapter.ListKorbanAdapter
import com.tekmob.sikoba.ui.daftarBencana.DaftarBencanaViewModel

class DetailBencanaFragment : Fragment() {

    private var _binding : FragmentDetailBencanaBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: DaftarBencanaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[DaftarBencanaViewModel::class.java]

        _binding = FragmentDetailBencanaBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvKorban.layoutManager = layoutManager



        viewModel.bencana.observe(viewLifecycleOwner){
            setBencana(it)
        }

        viewModel.listKorban.observe(viewLifecycleOwner){
            setListKorban(it)
        }

        return inflater.inflate(R.layout.fragment_detail_bencana, container, false)
    }

    private fun setBencana(bencana: Bencana){
        binding.bencanaCard.apply {
            nama.text = bencana.nama
            lokasi.text = bencana.lokasi
            tanggalKejadian.text = bencana.tanggalKejadian.toString()
        }
    }

    private fun setListKorban(listKorban : List<Korban>) {
        val adapter = context?.let { ListKorbanAdapter(listKorban, it) }
        binding.rvKorban.adapter = adapter
    }


}