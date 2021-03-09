package com.ainsigne.mobiletechchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ainsigne.mobiletechchallenge.ui.MovieListActivity

/**
 * Splash activity as entry point
 */
class MainActivity : AppCompatActivity() {

    /**
     * Once created launches the movie list page
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val i = Intent(this, MovieListActivity::class.java)
            startActivity(i)
            finish()
        }, 1000)

    }
}