package com.ainsigne.mobiletechchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ainsigne.mobiletechchallenge.R
import com.ainsigne.mobiletechchallenge.databinding.MovieItemBinding
import com.ainsigne.mobiletechchallenge.model.Search

/**
 * Movie Adapter for displaying movie list returned from query as [MovieAdapter]
 * @param items as [List] of [Search] to populate the movie list
 * @param height as [Int] current screen height
 * @param onClick as [Unit] callback when view is clicked
 */
class MovieAdapter(
    private var items: List<Search>,
    private var height : Int = 0,
    private val onClick: (ViewHolderValues, Search) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    /**
     * updates the current populated list
     * @param items as [List] of [Search]
     */
    fun updateAdapter( items: List<Search>){
        this.items = items
        notifyDataSetChanged()
    }

    /**
     * Setup the binding for the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return MovieViewHolder(it, onClick)
        }
    }

    /**
     * Returns the number of movies
     */
    override fun getItemCount() = items.size

    /**
     * Binds the data to the view
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position], height)
    }

    /**
     * [MovieViewHolder] for binding the views
     * @param binding as [MovieItemBinding] the views to be binded
     * @param onClick as [Unit] callback when view is clicked
     */
    class MovieViewHolder(
        private val binding: MovieItemBinding,
        private val onClick: (ViewHolderValues, Search) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderValues {

        /**
         * binds the views
         * @param item as [Search]
         * @param height as [Int]
         */
        fun bind(item: Search, height : Int) {
            with(binding) {
                binding.root.layoutParams.height = height
                thumbnail.load(item.Poster) {
                    crossfade(true)
                    placeholder(R.drawable.ic_movie_reel)

                }
                name.text = item.Title
                tags.text = item.Year
                root.setOnClickListener {
                    onClick(this@MovieViewHolder, item)
                }
            }
        }

        /**
         * @return [Int] the thumbnail height
         */
        override fun getThumbnailHeight() = binding.thumbnail.height
        /**
         * @return [Int] the y axis of the current view
         */
        override fun getY() = binding.root.y.toInt()
        /**
         * @return [Int] the x axis of the current view
         */
        override fun getX() = binding.root.x.toInt()
        /**
         * @return [Int] the thumbnail width
         */
        override fun getThumbnailWidth() = binding.thumbnail.width
    }

    /**
     * [ViewHolderValues] for holding x and y position
     */
    interface ViewHolderValues {
        /**
         * @return [Int] the thumbnail height
         */
        fun getThumbnailHeight(): Int
        /**
         * @return [Int] the thumbnail width
         */
        fun getThumbnailWidth(): Int
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