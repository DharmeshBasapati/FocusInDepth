package com.app.focusindepth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusindepth.databinding.RowItemNewsBinding
import com.app.focusindepth.models.News
import com.bumptech.glide.Glide

class NewsAdapter(
    private var newsList: List<News>, val onNewsClick: (News)->Any
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(newsList[position]) {
                binding.apply {

                    tvNewsTitle.text = title
                    tvNewsContent.text = content

                    Glide.with(itemView.context).load(imageUrl).into(ivNewsImage)

                    itemView.setOnClickListener {
                        onNewsClick(newsList[position])
                    }

                }
            }
        }
    }

    override fun getItemCount() = newsList.size

    fun addData(_newsList: List<News>) {
        newsList = _newsList
        notifyDataSetChanged()
    }
}