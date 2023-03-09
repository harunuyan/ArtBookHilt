package com.volie.artbookhilttesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.volie.artbookhilttesting.view.fragment.ArtDetailsFragment
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide: RequestManager
) : FragmentFactory() {

    // this method will be called when we create a fragment
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}