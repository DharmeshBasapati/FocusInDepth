package com.app.focusindepth.views.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.focusindepth.MainActivity
import com.app.focusindepth.adapters.NewsAdapter
import com.app.focusindepth.databinding.FragmentNewsBinding
import com.app.focusindepth.models.News
import com.app.focusindepth.room.builder.DatabaseBuilder
import com.app.focusindepth.utils.Status
import com.app.focusindepth.utils.ViewModelFactory
import com.app.focusindepth.viewmodels.NewsViewModel

class NewsFragment : Fragment() {

    private var newsAdapter: NewsAdapter? = null
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)

        (requireActivity() as MainActivity).supportActionBar?.title =
            args.categoryName.replaceFirstChar {
                if (it.isLowerCase()) it.uppercase() else it.toString()
            }

        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                newsViewModel.addNewsToReadLater(newsAdapter!!.getNewsList()[viewHolder.adapterPosition])
                newsAdapter?.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(requireContext(), "Added to Read Later.", Toast.LENGTH_SHORT).show()
            }


        }

        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.rvNews)

        binding.swipeRefreshLayout.setOnRefreshListener {
            newsViewModel.fetchNewsForSelectedCategory(args.categoryName)
        }

        setupViewModel()
        setupObserver()
        setupNewsList()

        return binding.root
    }

    private fun setupNewsList() {

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        newsAdapter = NewsAdapter(arrayListOf()) {
            navigateToNewsDetail(it)
        }

        binding.rvNews.adapter = newsAdapter

    }

    private fun navigateToNewsDetail(selectedNews: News) {
        val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(selectedNews)
        findNavController().navigate(action)
    }

    private fun setupViewModel() {
        newsViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
            )
        )
            .get(NewsViewModel::class.java)

        newsViewModel.fetchNewsForSelectedCategory(args.categoryName)
    }

    private fun setupObserver() {

        newsViewModel.getNewsList().observe(requireActivity(), {

            when (it.status) {

                Status.SUCCESS -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                    it.data?.let { it1 -> newsAdapter?.addData(it1) }
                }
                Status.ERROR -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                    binding.rvNews.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvNews.visibility = View.GONE
                }
            }

        })

    }

}