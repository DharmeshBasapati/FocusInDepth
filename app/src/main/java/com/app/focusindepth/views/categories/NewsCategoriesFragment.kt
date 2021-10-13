package com.app.focusindepth.views.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.focusindepth.adapters.CategoriesAdapter
import com.app.focusindepth.databinding.FragmentNewsCategoriesBinding
import com.app.focusindepth.room.builder.DatabaseBuilder
import com.app.focusindepth.room.entity.Category
import com.app.focusindepth.utils.ViewModelFactory
import com.app.focusindepth.viewmodels.NewsCategoriesViewModel

class NewsCategoriesFragment : Fragment() {

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var binding: FragmentNewsCategoriesBinding
    private lateinit var newsCategoriesViewModel: NewsCategoriesViewModel

    private val TAG = "NewsCategoriesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsCategoriesBinding.inflate(layoutInflater)

        setupViewModel()
        setupCategoriesList()
        setupObserver()

        return binding.root
    }

    private fun setupCategoriesList() {

        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)

        categoriesAdapter = CategoriesAdapter(arrayListOf()) { categoryName ->
            navigateToNewsList(categoryName)
        }

        binding.rvCategories.adapter = categoriesAdapter

    }

    private fun navigateToNewsList(categoryName: String) {
        val action =
            NewsCategoriesFragmentDirections.actionNewsCategoriesFragmentToNewsFragment(categoryName)
        findNavController().navigate(action)

    }

    private fun setupViewModel() {

        newsCategoriesViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
            )
        )
            .get(NewsCategoriesViewModel::class.java)

    }

    private fun setupObserver() {

        newsCategoriesViewModel.getAllCategories().observe(requireActivity(), {

            Log.d(TAG, "setupObserver: All Categories - $it")
            updateList(it)

        })

    }

    private fun updateList(categoriesList: List<Category>) {
        categoriesAdapter.addData(categoriesList)
    }

}