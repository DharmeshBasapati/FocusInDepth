package com.app.focusindepth.views.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.app.focusindepth.R
import com.app.focusindepth.databinding.FragmentNewsDetailBinding
import com.app.focusindepth.room.builder.DatabaseBuilder
import com.app.focusindepth.utils.ViewModelFactory
import com.app.focusindepth.viewmodels.NewsViewModel
import com.bumptech.glide.Glide

class NewsDetailFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsDetailBinding.inflate(layoutInflater)

        val SELECTED_NEWS = args.selectedNews

        newsViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
            )
        )
            .get(NewsViewModel::class.java)

        binding.apply {

            tvNewsTitle.text = SELECTED_NEWS.title
            tvNewsContent.text = SELECTED_NEWS.content

            tvAuthorWithTime.text = String.format(
                getString(R.string.label_author_and_time),
                SELECTED_NEWS.author,
                SELECTED_NEWS.time,
                SELECTED_NEWS.date
            )

            btnReadLater.setOnClickListener {
                newsViewModel.addNewsToReadLater(SELECTED_NEWS)
                Toast.makeText(requireContext(), "Added to Read Later.", Toast.LENGTH_SHORT).show()
            }

            btnShareNews.setOnClickListener {

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.let {
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_TEXT, SELECTED_NEWS.url)
                    startActivity(
                        Intent.createChooser(
                            it,
                            "Share News..."
                        )
                    )
                }

            }

            Glide.with(requireActivity()).load(SELECTED_NEWS.imageUrl).into(ivNewsImage)

        }


        return binding.root
    }

}