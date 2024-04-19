package com.borjali.presentation.ui.home.movieslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.borjali.domain.model.MovieDto
import com.borjali.domain.model.event.EventListIsEmptyResult
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.presentation.R
import com.borjali.presentation.databinding.ItemOfMovieBinding

import com.bumptech.glide.Glide


/**
 * adapter class for worker ist
 */
class MovieListAdapter(
    private val itemList: List<MovieDto>) : RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {
    private var filteredItemList: List<MovieDto> = itemList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOfMovieBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = filteredItemList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return filteredItemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItemList = if (query.isEmpty()) {
            itemList
        } else {
            itemList.filter { it.title.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
        EventOfCleanApp.send(EventListIsEmptyResult(filteredItemList.isEmpty()))
    }

    inner class ItemViewHolder(private val binding: ItemOfMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDto, position: Int) {
            binding.title.text = item.title
            binding.overview.text = item.overview
            Glide.with(binding.rootItem.context)
                .load("https://media.themoviedb.org/t/p/w220_and_h330_face"+item.poster_path)
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.moviePoster)
            binding.executePendingBindings()
        }
    }
}
