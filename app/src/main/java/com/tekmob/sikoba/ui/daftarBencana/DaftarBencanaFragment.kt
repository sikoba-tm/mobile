package com.tekmob.sikoba.ui.daftarBencana

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.tekmob.sikoba.databinding.FragmentDaftarBencanaBinding
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.ui.adapter.ListBencanaAdapter

class DaftarBencanaFragment : Fragment() {

    private var _binding: FragmentDaftarBencanaBinding? = null


    private val binding get() = _binding!!

    private lateinit var viewModel: DaftarBencanaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[DaftarBencanaViewModel::class.java]

        _binding = FragmentDaftarBencanaBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvBencana.layoutManager = layoutManager

        viewModel.listBencana.observe(viewLifecycleOwner) {
            setListBencana(it)
        }

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(DaftarBencanaViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    private fun setListBencana(listBencana : List<Bencana>) {
        val adapter = ListBencanaAdapter(listBencana)
        binding.rvBencana.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}