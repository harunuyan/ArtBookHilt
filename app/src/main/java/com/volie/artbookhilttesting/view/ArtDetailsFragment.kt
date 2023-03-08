package com.volie.artbookhilttesting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.volie.artbookhilttesting.databinding.FragmentArtDetailsBinding

class ArtDetailsFragment : Fragment() {
    private var _mBinding: FragmentArtDetailsBinding? = null
    private val mBinding get() = _mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}