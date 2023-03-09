package com.volie.artbookhilttesting.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.volie.artbookhilttesting.databinding.FragmentArtsBinding

class ArtFragment : Fragment() {
    private var _mBinding: FragmentArtsBinding? = null
    private val mBinding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentArtsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}