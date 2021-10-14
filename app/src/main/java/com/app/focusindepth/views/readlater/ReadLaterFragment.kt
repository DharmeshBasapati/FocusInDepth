package com.app.focusindepth.views.readlater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.focusindepth.adapters.ReadLaterAdapter
import com.app.focusindepth.databinding.FragmentReadLaterBinding
import com.app.focusindepth.room.builder.DatabaseBuilder
import com.app.focusindepth.room.entity.News
import com.app.focusindepth.utils.ViewModelFactory
import com.app.focusindepth.viewmodels.ReadLaterViewModel

class ReadLaterFragment : Fragment() {

    private lateinit var readLaterAdapter: ReadLaterAdapter
    private lateinit var readLaterViewModel: ReadLaterViewModel
    private lateinit var binding: FragmentReadLaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadLaterBinding.inflate(layoutInflater)

        setupReadLaterList()
        setupViewModel()
        setupObserver()


        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val selectedNews =
                    readLaterAdapter.getList()[viewHolder.adapterPosition]
                readLaterViewModel.removeFromReadLater(selectedNews)

                Toast.makeText(requireContext(), "Removed.", Toast.LENGTH_SHORT).show()
            }


        }

        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.rvReadLater)

        return binding.root
    }

    private fun setupObserver() {

        readLaterViewModel.getAllReadLaterNews().observe(requireActivity(), {
            it?.let {
                updateUI(it)
            }
        })

    }

    private fun updateUI(it: List<News>) {
        if (it.isEmpty()) {
            binding.rvReadLater.visibility = View.GONE
            binding.ivEmpty.visibility = View.VISIBLE
            binding.tvNoItemFound.visibility = View.VISIBLE
        } else {
            binding.rvReadLater.visibility = View.VISIBLE
            binding.ivEmpty.visibility = View.GONE
            binding.tvNoItemFound.visibility = View.GONE
            readLaterAdapter.addData(it)
        }
    }

    private fun setupViewModel() {
        readLaterViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
            )
        ).get(ReadLaterViewModel::class.java)
        readLaterViewModel.fetchReadLaterNews()
    }

    private fun setupReadLaterList() {

        binding.rvReadLater.layoutManager = LinearLayoutManager(requireContext())

        readLaterAdapter = ReadLaterAdapter(arrayListOf()) {

        }

        binding.rvReadLater.adapter = readLaterAdapter

    }

}