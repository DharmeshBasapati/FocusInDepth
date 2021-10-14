package com.app.focusindepth.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusindepth.R
import com.app.focusindepth.databinding.RowItemNewsBinding
import com.app.focusindepth.room.entity.News
import com.bumptech.glide.Glide

class ReadLaterAdapter(
    private var newsList: List<News>, val onNewsClick: (News) -> Any
) :
    RecyclerView.Adapter<ReadLaterAdapter.ViewHolder>() {

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

                    tvAuthorWithTime.text = String.format(
                        itemView.context.getString(R.string.label_author_and_time),
                        author,
                        time,
                        date
                    )

                    btnReadLater.visibility = View.GONE

                    btnShareNews.setOnClickListener {

                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.let {
                            it.type = "text/plain"
                            it.putExtra(Intent.EXTRA_TEXT, url)
                            itemView.context.startActivity(
                                Intent.createChooser(
                                    it,
                                    "Share News..."
                                )
                            )
                        }

                    }

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
        newsList = _newsList.sortedByDescending { it.date }
        notifyDataSetChanged()
    }

    fun getList(): List<News> = newsList
}