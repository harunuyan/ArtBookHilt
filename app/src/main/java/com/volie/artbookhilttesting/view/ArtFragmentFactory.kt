package com.volie.artbookhilttesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.volie.artbookhilttesting.adapter.ArtRecyclerAdapter
import com.volie.artbookhilttesting.adapter.ImageRecyclerAdapter
import com.volie.artbookhilttesting.view.fragment.ArtDetailsFragment
import com.volie.artbookhilttesting.view.fragment.ArtFragment
import com.volie.artbookhilttesting.view.fragment.ImageApiFragment
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory() {

    // this method will be called when we create a fragment
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}