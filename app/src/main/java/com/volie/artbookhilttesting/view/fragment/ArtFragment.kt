package com.volie.artbookhilttesting.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volie.artbookhilttesting.adapter.ArtRecyclerAdapter
import com.volie.artbookhilttesting.databinding.FragmentArtsBinding
import com.volie.artbookhilttesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val mAdapter: ArtRecyclerAdapter
) : Fragment() {
    private var _mBinding: FragmentArtsBinding? = null
    private val mBinding get() = _mBinding!!
    lateinit var mViewModel: ArtViewModel

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
        mViewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()
        mBinding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }

    private fun setupRecyclerView() {
        mBinding.recyclerViewArt.adapter = mAdapter
        mBinding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        swipeToDelete()
    }

    private fun swipeToDelete() {
        val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition // get the position of the item
                val selectedArt = mAdapter.artList[layoutPosition] // get the item
                mViewModel.deleteArt(selectedArt)   // delete the item
            }
        }
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(mBinding.recyclerViewArt)   // attach the swipe to the recycler view
    }

    private fun subscribeToObservers() {
        mViewModel.artList.observe(viewLifecycleOwner) {
            mAdapter.artList = it // diffUtil will handle the changes automatically
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}