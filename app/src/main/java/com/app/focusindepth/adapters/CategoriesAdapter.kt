package com.app.focusindepth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusindepth.databinding.RowItemNewsCategoriesBinding
import com.app.focusindepth.room.entity.Category

class CategoriesAdapter(
    private var categoriesList: List<Category>,
    val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowItemNewsCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemNewsCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(categoriesList[position]) {
                binding.apply {

                    ivCategory.setImageResource(categoryImage)
                    tvCategoryName.text = categoryName
                    itemView.setOnClickListener {
                        onItemClick(categoryName.lowercase())
                    }
                }
            }
        }
    }

    override fun getItemCount() = categoriesList.size

    fun addData(_categoriesList: List<Category>) {
        categoriesList = _categoriesList
        notifyItemRangeInserted(0, categoriesList.size)
    }
}