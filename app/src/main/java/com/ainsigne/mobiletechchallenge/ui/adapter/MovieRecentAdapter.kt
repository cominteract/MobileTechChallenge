package com.ainsigne.mobiletechchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ainsigne.mobiletechchallenge.R
import com.ainsigne.mobiletechchallenge.databinding.MovieItemBinding
import com.ainsigne.mobiletechchallenge.databinding.MovieRecentItemBinding
import com.ainsigne.mobiletechchallenge.model.MovieRecent
import com.ainsigne.mobiletechchallenge.model.Search
import com.ainsigne.mobiletechchallenge.ui.adapter.MovieAdapter.MovieViewHolder

/**
 * Movie Recent Adapter for displaying movie recents from query inputted as [MovieRecentAdapter]
 * @param items as [List] of [MovieRecent] to populate the recent searched movie list
 * @param onClick as [Unit] callback when view is clicked
 */
class MovieRecentAdapter(
    private var items: List<MovieRecent>,
    private val onClick: (ViewHolderValues, MovieRecent) -> Unit
) : RecyclerView.Adapter<MovieRecentAdapter.RecentViewHolder>() {


    /**
     * updates the current populated list
     * @param items as [List] of [MovieRecent]
     */
    fun updateAdapter( items: List<MovieRecent>){
        this.items = items
        notifyDataSetChanged()
    }
    /**
     * Setup the binding for the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {

        /**
         * inflates the current layout with view holder
         */
        MovieRecentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return RecentViewHolder(it, onClick)
        }
    }

    /**
     * Returns the number of recent movies
     */
    override fun getItemCount() = items.size

    /**
     * Binds the data to the view
     */
    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * [RecentViewHolder] for binding the views
     * @param binding as [MovieRecentItemBinding] the views to be binded
     * @param onClick as [Unit] callback when view is clicked
     */
    class RecentViewHolder(
        private val binding: MovieRecentItemBinding,
        private val onClick: (ViewHolderValues, MovieRecent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderValues {

        /**
         * binds the item as [MovieRecent] to the views
         */
        fun bind(item: MovieRecent) {
            with(binding) {
                name.text = item.searched
                recentRoot.setOnClickListener {
                    onClick(this@RecentViewHolder, item)
                }
            }
        }
        /**
         * @return [Int] the y axis of the current view
         */
        override fun getY() = binding.recentRoot.y.toInt()
        /**
         * @return [Int] the x axis of the current view
         */
        override fun getX() = binding.recentRoot.x.toInt()
    }

    /**
     * [ViewHolderValues] for holding x and y position
     */
    interface ViewHolderValues {
        /**
         * @return [Int] the y axis of the current view
         */
        fun getY(): Int
        /**
         * @return [Int] the x axis of the current view
         */
        fun getX(): Int
    }
}