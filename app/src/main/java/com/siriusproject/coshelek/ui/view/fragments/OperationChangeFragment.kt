package com.siriusproject.coshelek.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.siriusproject.coshelek.databinding.FragmentOperationChangeBinding

class OperationChangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOperationChangeBinding.inflate(layoutInflater)

        return binding.root
    }
}