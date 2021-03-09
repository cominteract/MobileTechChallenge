package com.ainsigne.mobiletechchallenge.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ainsigne.mobiletechchallenge.R
import com.ainsigne.mobiletechchallenge.databinding.ActivityMovieListBinding
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.Search
import com.ainsigne.mobiletechchallenge.ui.adapter.MovieAdapter
import com.ainsigne.mobiletechchallenge.ui.adapter.MovieRecentAdapter
import com.ainsigne.mobiletechchallenge.utils.*
import com.ainsigne.mobiletechchallenge.viewmodel.MovieListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * [MovieListActivity] Movie list page for displaying movie list from searched query
 */
class MovieListActivity : AppCompatActivity() {

    /**
     * View model to observe movie results and recent movies retrieval
     */
    private val moviesViewModel by viewModel<MovieListViewModel>()

    /**
     * Movie search list being retrieved as [List] of [Search]
     */
    var allSearches = ArrayList<Search>()
    /**
     * Data for all movie results currently added as value as [List] of [MovieDBResult]
     */
    var allResults = ArrayList<MovieDBResult>()

    /**
     * Config for saving preferences as [SharedConfig]
     */
    lateinit var config : SharedConfig

    /**
     * Movie Adapter for displaying movie list returned from query as [MovieAdapter]
     */
    var adapter : MovieAdapter? = null

    /**
     * Movie Recent Adapter for as [MovieRecentAdapter]
     */
    var recentAdapter : MovieRecentAdapter? = null

    /**
     * past visible items count as [Int]
     */
    var pastVisiblesItems: Int = 0
    /**
     * visible items count as [Int]
     */
    var visibleItemCount: Int = 0
    /**
     * total items count as [Int]
     */
    var totalItemCount: Int = 0

    /**
     * loading as [Boolean] to identify whether it is loading more movies
     */
    var loading = true;

    /**
     * currentPage as [Int]  to identify which page is already loaded
     */
    var currentPage : Int = 0

    /**
     * previousPage as [Int] to identify which is the previous page based on its current page
     */
    var previousPage : Int = -1

    /**
     * searchQuery as [String] for the search query
     */
    var searchQuery = ""

    /**
     * get screen width for the app as [Int]
     */
    fun getScreenWidth(@NonNull activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    /**
     * binding as [ActivityMovieListBinding] to bind the views
     */
    lateinit var binding : ActivityMovieListBinding

    /**
     * Hide recent searched list on resume
     */
    override fun onResume() {
        super.onResume()
        if(binding != null){
            binding.rvRecents.visibility = View.GONE
        }
    }

    /**
     * Setup view binding on create
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(
                this,
                R.layout.activity_movie_list
        )
        config = SharedConfig(
                getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
        )
        /**
         * initialize search with avengers
         * queries previously searched query
         */
        if(config.getLastSearched() == null){
            binding.etSearchMovies.setText("avengers")
            searchQuery = "avengers"
            refreshSearch(binding, "avengers")
        }else {
            config.getLastSearched()?.let {
                binding.etSearchMovies.setText(it)
                searchQuery = it
                refreshSearch(binding, it)
            }
        }

        /**
         * observes error visibility
         */
        moviesViewModel.getError().observe(this){
            if(it && allSearches.isNullOrEmpty()){
                binding.notFound.visibility = View.VISIBLE
            }else{
                binding.notFound.visibility = View.GONE
            }
        }
        /**
         * show recent searched for ease of access to queries made
         */
        binding.etSearchMovies.addTextChangedListener { t ->
            if(!t.isNullOrEmpty()){

                binding.rvRecents.visibility = View.VISIBLE
                moviesViewModel.getMovieRecents(t.toString()).observe(this){
                    it?.let {
                        if(recentAdapter != null){
                            recentAdapter?.updateAdapter(it)
                        }else{
                            recentAdapter = MovieRecentAdapter(it) { values, recent ->
                                binding.etSearchMovies.setText(recent.searched)
                                searchQuery = recent.searched
                                refreshSearch(binding, recent.searched)
                            }
                            binding.rvRecents.adapter = recentAdapter
                        }
                    }
                }
                binding.ivReset.visibility = View.VISIBLE
            }else{
                binding.rvRecents.visibility = View.GONE
                binding.ivReset.visibility = View.GONE
            }
        }
        /**
         * perform search based on the input
         */
        binding.etSearchMovies.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchQuery = v?.text.toString()
                refreshSearch(binding, searchQuery)
                true
            }
            false
        }
        /**
         * resets the text input
         */
        binding.ivReset.setOnClickListener {
            binding.etSearchMovies.setText("")
            binding.etSearchMovies.clearFocus()
            binding.ivReset.visibility = View.GONE
        }
        binding.etSearchMovies.setSelectAllOnFocus(true)


        /**
         * loads more data when available
         * shows search results headear when already scrolled for a bit
         */
        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                recyclerView.layoutManager?.let { gl ->
                    val lBManager: GridLayoutManager = gl as GridLayoutManager
                    if (lBManager.findFirstVisibleItemPosition() > 5) {
                        binding.etSearchMovies.visibility = View.INVISIBLE
                        binding.tvSearchMovies.visibility = View.VISIBLE
                        binding.tvSearchMovies.text = String.format(getString(R.string.search_results), searchQuery)
                    } else {
                        binding.etSearchMovies.visibility = View.VISIBLE
                        binding.tvSearchMovies.visibility = View.INVISIBLE
                    }
                }

                if (dy > 0) {
                    recyclerView.layoutManager?.let { ll ->
                        val lManager: GridLayoutManager = ll as GridLayoutManager
                        visibleItemCount = lManager.childCount
                        totalItemCount = lManager.itemCount
                        pastVisiblesItems = lManager.findFirstVisibleItemPosition()
                    }
                    if (loading) {

                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            Log.d(" Refreshing $currentPage ", " Refreshing $searchQuery ")
                            loading = false
                            previousPage = currentPage
                            currentPage++
                            refresh(binding, currentPage.toString(), previousPage.toString(), searchQuery)
                        }
                    }
                }

            }
        })
    }

    /**
     * hide keyboard when search is already done
     */
    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * queries a refresh to search for page of a new query
     */
    private fun refreshSearch(binding: ActivityMovieListBinding, query: String){
        moviesViewModel.clearObservers()
        binding.etSearchMovies.clearFocus()
        hideKeyboardFrom(this, binding.etSearchMovies)
        binding.rvRecents.visibility = View.GONE
        allSearches.clear()
        allResults.clear()
        adapter?.updateAdapter(allSearches)
        currentPage = 1
        previousPage = -1
        refresh(binding, currentPage.toString(), previousPage.toString(), query)
        config.saveLastSearched(query)
    }

    /**
     * refreshes the results to be queried
     */
    private fun refresh(binding: ActivityMovieListBinding, page: String, previousPage: String, query: String){
        moviesViewModel.getMovieResults(query, page, previousPage, this).observe(this){
            ListObserver(binding, page, it)
        }
    }

    /**
     * display movie list if results exist and display 404 when results is empty
     */
    private fun ListObserver(binding: ActivityMovieListBinding, page: String, it: List<MovieDBResult>?){
        if(it != null && it.isNotEmpty()){
            binding.notFound?.visibility = View.GONE
            val width: Int = getScreenWidth(this)/2
            val height: Int = (width * 1.5).toInt()
            if(!allResults.contains(it.last()) && it.last().id.contains(searchQuery)) {
                allResults.add(it.last())
                allSearches.addAll(it.last().Search)
                Log.d(" Search ", " Movies Fetching Has Values ${allSearches.size}")
                if(adapter == null){
                    adapter = MovieAdapter(allSearches, height = height){ values, search ->
                        val intent = Intent(this, MovieDetailsActivity::class.java)
                        intent.putExtra(movieId, search.imdbID)
                        intent.putExtra(xAxis, values.getX())
                        intent.putExtra(yAxis, values.getY())
                        intent.putExtra(tWidth, values.getThumbnailWidth())
                        intent.putExtra(tHeight, values.getThumbnailHeight())
                        startActivity(intent)
                    }
                    binding.rvMovies.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    binding.rvMovies.adapter = adapter
                }else{
                    adapter?.updateAdapter(allSearches)
                }
                loading = true
            }
        }else if(it != null && it.isEmpty()){
            binding.rvRecents.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE

        }else if(it == null){
            binding.rvRecents.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE

        }
    }
}