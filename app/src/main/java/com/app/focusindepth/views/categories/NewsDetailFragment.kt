package com.app.focusindepth.views.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.focusindepth.databinding.FragmentNewsDetailBinding
import com.bumptech.glide.Glide

class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsDetailBinding.inflate(layoutInflater)

        val SELECTED_NEWS = args.selectedNews

        binding.apply {

            tvNewsTitle.text = SELECTED_NEWS.title
            tvNewsContent.text = SELECTED_NEWS.content

            Glide.with(requireActivity()).load(SELECTED_NEWS.imageUrl).into(ivNewsImage)

        }


        return binding.root
    }

}