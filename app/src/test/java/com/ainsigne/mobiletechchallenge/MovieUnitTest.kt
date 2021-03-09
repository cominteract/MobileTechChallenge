package com.ainsigne.mobiletechchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.ainsigne.mobiletechchallenge.fake.FakeMovieDetailsRepository
import com.ainsigne.mobiletechchallenge.fake.FakeMovieDetailsViewModel
import com.ainsigne.mobiletechchallenge.fake.FakeMovieListRepository
import com.ainsigne.mobiletechchallenge.fake.FakeMovieListViewModel
import com.ainsigne.mobiletechchallenge.model.MovieDBResult
import com.ainsigne.mobiletechchallenge.model.MovieDetailsDB
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class MovieUnitTest {

    inline fun <reified T> lambdaMock(): T = Mockito.mock(T::class.java)

    lateinit var repository: FakeMovieListRepository
    lateinit var detailsRepo : FakeMovieDetailsRepository
    lateinit var viewmodel: FakeMovieListViewModel
    lateinit var detailsViewmodel: FakeMovieDetailsViewModel

    @Before
    fun setup(){

        repository = FakeMovieListRepository()
        detailsRepo = FakeMovieDetailsRepository()
        viewmodel = FakeMovieListViewModel(repository)
        detailsViewmodel = FakeMovieDetailsViewModel(detailsRepo)
    }

    fun observeMovieResultChanges(query : String, page : String, previous : String, lifecycle: Lifecycle, observer: (MovieDBResult) -> Unit) {
        viewmodel.getMovieResults(query, page, previous).observe( { lifecycle } ) { movie ->
            movie?.let(observer)
        }
    }

    fun observeMovieDetailChanges(id : String, lifecycle: Lifecycle, observer: (MovieDetailsDB) -> Unit) {
        detailsViewmodel.getMovieDetails(id).observe( { lifecycle } ) { movie ->
            if(movie.imdbID == id){
                movie?.let(observer)
            }
        }
    }


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun testMovies() {
        val observerMovie = lambdaMock<(MovieDBResult) -> Unit>()
        val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        observeMovieResultChanges(repository.queryToBeAdded,repository.pageToBeAdded,"-1", lifecycle, observerMovie)
        repository.fetchMovies(repository.queryToBeAdded, repository.pageToBeAdded)
        // verify that it is added and it is the same list that is being updated meaning the repository works!
        viewmodel.mutableLiveData.value?.let {
            assert(it.size == 2)
            Mockito.verify(observerMovie).invoke(it.last())
        }
    }

    @Test
    fun testMovieDetails() {
        val observerMovieDetails = lambdaMock<(MovieDetailsDB) -> Unit>()
        val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        observeMovieDetailChanges("tt0848228", lifecycle, observerMovieDetails)
        detailsViewmodel.getMovieDetails("tt0848228").value?.let {
            assert(it.imdbID == "tt0848228")
        }
        observeMovieDetailChanges("tt4154756", lifecycle, observerMovieDetails)
        detailsViewmodel.fetchMovieDetails("tt4154756")
        detailsViewmodel.getMovieDetails("tt4154756").value?.let {
            assert(it.imdbID == "tt4154756")
        }
        //tt4154756
    }

}