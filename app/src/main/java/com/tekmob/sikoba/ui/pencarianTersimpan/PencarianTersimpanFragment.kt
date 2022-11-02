package com.tekmob.sikoba.ui.pencarianTersimpan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tekmob.sikoba.R
import com.tekmob.sikoba.databinding.FragmentPencarianTersimpanBinding

class PencarianTersimpanFragment : Fragment() {

    private var _binding: FragmentPencarianTersimpanBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PencarianTersimpanFragment()
    }

    private lateinit var viewModel: PencarianTersimpanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[PencarianTersimpanViewModel::class.java]

        _binding = FragmentPencarianTersimpanBinding.inflate(inflater, container, false)

        viewModel.text.observe(viewLifecycleOwner) {
            binding.text.text = it
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}