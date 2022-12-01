package com.tekmob.sikoba.ui.pengguna.pencarianTersimpan

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.databinding.FragmentPencarianTersimpanBinding
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanLocal
import com.tekmob.sikoba.ui.adapter.PencarianTerimpanAdapter
import com.tekmob.sikoba.ui.pengguna.detailHasilPencarian.DetailHasilPencarianActivity
import com.tekmob.sikoba.ui.pengguna.detailPencarianTersimpan.DetailPencarianTerimpanActivity
import com.tekmob.sikoba.ui.pengguna.hasilPencarian.HasilPencarianActivity

class PencarianTersimpanFragment : Fragment() {

    private var _binding: FragmentPencarianTersimpanBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter : PencarianTerimpanAdapter

    private lateinit var viewModel: PencarianTersimpanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPencarianTersimpanBinding.inflate(inflater, container, false)

        activity?.let {
            viewModel = ViewModelProvider(this, ViewModelFactoryPencarianTersimpan(it.application))[PencarianTersimpanViewModel::class.java]
        }

        adapter = PencarianTerimpanAdapter(requireContext())
        adapter.setOnItemClickCallback(object : PencarianTerimpanAdapter.OnItemClickCallback{
            override fun onItemClicked(data: KorbanLocal) {
                val intent = Intent(context, DetailPencarianTerimpanActivity::class.java)
                intent.putExtra(DetailPencarianTerimpanActivity.ID_KORBAN, data.id)
                startActivity(intent)
            }
        })
        adapter.setOnDeleteClickCallback(object : PencarianTerimpanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KorbanLocal) {
                viewModel.deleteKorban(data)
                Toast.makeText(requireContext(), "Deleted ${data.nama}", Toast.LENGTH_SHORT).show()
            }

        })

        val layoutManager = LinearLayoutManager(context)
        binding.rvPencarianTerimpan.layoutManager = layoutManager
        binding.rvPencarianTerimpan.adapter = adapter

        viewModel.getAllKorban().observe(viewLifecycleOwner) {
            adapter.setListKorban(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}