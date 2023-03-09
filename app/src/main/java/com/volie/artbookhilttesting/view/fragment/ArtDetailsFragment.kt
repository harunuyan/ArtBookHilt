package com.volie.artbookhilttesting.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.volie.artbookhilttesting.databinding.FragmentArtDetailsBinding
import com.volie.artbookhilttesting.util.Status
import com.volie.artbookhilttesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment() {
    private var _mBinding: FragmentArtDetailsBinding? = null
    private val mBinding get() = _mBinding!!
    lateinit var mViewModel: ArtViewModel
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

        mViewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        subscribeToObservers()
        mBinding.artImg.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        // when back button pressed
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // to last fragment
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        mBinding.btnSave.setOnClickListener {
            mViewModel.makeArt(
                mBinding.etName.text.toString(),
                mBinding.etArtist.text.toString(),
                mBinding.etYear.text.toString()
            )
        }
    }

    private fun subscribeToObservers() {
        mViewModel.selectedImage.observe(viewLifecycleOwner) { url ->
            mBinding.let {
                glide.load(url).into(it.artImg)
            }
        }
        mViewModel.insertArtMessage.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    mViewModel.resetInsertArtMesssage() // need to reset it after use it 
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.LOADING -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}