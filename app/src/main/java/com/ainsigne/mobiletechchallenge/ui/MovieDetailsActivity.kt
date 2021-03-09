package com.ainsigne.mobiletechchallenge.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import coil.load
import com.ainsigne.mobiletechchallenge.R
import com.ainsigne.mobiletechchallenge.databinding.ActivityMovieDetailsBinding
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import com.ainsigne.mobiletechchallenge.utils.xAxis
import com.ainsigne.mobiletechchallenge.utils.yAxis
import com.ainsigne.mobiletechchallenge.viewmodel.MovieDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Animation duration constant for motion layout transitions
 */
private const val ANIMATION_DURATION = 500

/**
 * Movie details page for displaying movie details information
 */
class MovieDetailsActivity : AppCompatActivity() {

    /**
     * binding to be used for updating the views to display movie details
     */
    private lateinit var  binding : ActivityMovieDetailsBinding

    /**
     * constraint set id for start transition
     */
    var mConstraintSet1 = R.id.start
    /**
     * constraint set id for mid transition
     */
    var mConstraintSet2 = R.id.mid
    /**
     * constraint set id for end transition
     */
    var mConstraintSet3 = R.id.end
    /**
     * constraint set id for start set transition
     */
    var mConstraintSet4 = R.id.start_set
    /**
     * constraint set id for mid set transition
     */
    var mConstraintSet5 = R.id.mid_set
    /**
     * constraint set id for end set transition
     */
    var mConstraintSet6 = R.id.end_set
    /**
     * constraint set id for mid end set transition
     */
    var mConstraintSet7 = R.id.mid_end_set

    /**
     * View model to observe movie details retrieval
     */
    private val movieDetailsViewModel by viewModel<MovieDetailsViewModel>()

    /**
     * Motionlayout for movie details related transition
     */
    private lateinit var animatedView: MotionLayout
    /**
     * Motionlayout for movie display related transitin
     */
    private lateinit var swipeView: MotionLayout

    /**
     * Setups the binding when [MovieDetailsActivity] is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        animatedView = binding.motionLayout
        swipeView = binding.swipeMotionLayout
        /**
         * fetch movie details based on its imdbid from the api if not cached
         * and displays detail and binds animation if already cached
         */
        binding.viewOverlay.visibility = View.VISIBLE
        binding.viewOverlay.animate().alpha(0.0f).setDuration(900)
        intent.extras?.let {
            it.getString("movie_id")?.let { id ->
                movieDetailsViewModel.getMovieDetails(id).observe(this){ movie ->
                    if(movie == null){
                        movieDetailsViewModel.fetchMovieDetails(id)
                    }else{
                        binding.thumbnail.load(movie.Poster) {
                            crossfade(true)
                            placeholder(R.drawable.ic_movie_reel)
                        }
                        bindAnimation(movie, it)
                    }
                }
            }
        }
    }

    /**
     * bind motion layout animations for better user experience
     */
    private fun bindAnimation(movie: MovieDetailsDB, it: Bundle){

        var currentConstraintSet = mConstraintSet4
        var activeConstraintSet = mConstraintSet5
        if(it.getInt(xAxis, 0) > 200) {
            currentConstraintSet = mConstraintSet6
            activeConstraintSet = mConstraintSet7
        }

        animatedView.also { ml ->
            var constraintSet = ml.getConstraintSet(activeConstraintSet)
            constraintSet.setMargin(R.id.thumbnail, ConstraintSet.TOP, it.getInt(yAxis, 200))
            constraintSet.applyTo(ml)
            ml.setTransition(currentConstraintSet, activeConstraintSet)
            ml.setTransitionDuration(ANIMATION_DURATION)
            ml.transitionToState(currentConstraintSet)
            Handler().postDelayed({
                constraintSet = ml.getConstraintSet(activeConstraintSet)
                constraintSet.setMargin(R.id.thumbnail, ConstraintSet.TOP, it.getInt(yAxis, 200))
                constraintSet.applyTo(ml)
                constraintSet = ml.getConstraintSet(mConstraintSet3)
                constraintSet.setMargin(R.id.thumbnail, ConstraintSet.TOP, 0)
                constraintSet.applyTo(ml)
                ml.setTransition(activeConstraintSet, mConstraintSet3)
                ml.setTransitionDuration(ANIMATION_DURATION)
                ml.transitionToState(mConstraintSet3)
                val colorFrom = Color.parseColor("#00000000")
                val colorTo = ContextCompat.getColor(
                    this,
                    R.color.white
                )
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                colorAnimation.duration = 500 // milliseconds
                colorAnimation.addUpdateListener { animator ->
                    binding.container.setBackgroundColor(animator.animatedValue as Int)

                }
                colorAnimation.start()
            }, 100)
        }

        binding.name.text = movie.Title
        binding.aboutText.text = movie.Plot
        binding.tags.text = String.format(getString(R.string.movie_year), movie.Year)
        binding.description.text = String.format(getString(R.string.movie_actors), movie.Actors)
        binding.durationText.text = String.format(getString(R.string.movie_duration), movie.Runtime)
        binding.language.text = String.format(getString(R.string.movie_language), movie.Language)
    }
}