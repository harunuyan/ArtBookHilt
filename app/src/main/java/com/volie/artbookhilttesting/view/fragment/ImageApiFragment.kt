package com.volie.artbookhilttesting.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.volie.artbookhilttesting.adapter.ImageRecyclerAdapter
import com.volie.artbookhilttesting.databinding.FragmentImageApiBinding
import com.volie.artbookhilttesting.util.Status
import com.volie.artbookhilttesting.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    val mAdapter: ImageRecyclerAdapter
) : Fragment() {
    private var _mBinding: FragmentImageApiBinding? = null
    private val mBinding get() = _mBinding!!
    lateinit var mViewModel: ArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentImageApiBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        setupAdapter()
        searchForImage()
        subscribeToObservers()

        mAdapter.setOnItemClickListener {
            findNavController().popBackStack() // back to previous fragment
            mViewModel.setSelectedImage(it) // set selected image
        }
    }

    private fun searchForImage() {
        var job: Job? = null // job for delay

        // when text changed
        mBinding.etSearch.addTextChangedListener {
            job?.cancel() // cancel previous job
            job = lifecycleScope.launch {
                delay(1000L) // wait 1 second
                it?.let {
                    if (it.toString().isNotEmpty()) { // if text is not empty
                        mViewModel.searchForImage(it.toString())
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        mBinding.RvImage.adapter = mAdapter
        mBinding.RvImage.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun subscribeToObservers() {
        mViewModel.images.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        // map to get only image urls
                        imageResult.previewURL
                    }

                    mAdapter.imageList = urls ?: listOf() // if urls is null then return empty list
                    mBinding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    mBinding.progressBar.visibility = View.GONE
                }


                Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}