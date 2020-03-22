package com.ing.repoapp.core.platform
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.fernandocejas.sample.core.extension.inTransaction
import com.ing.repoapp.R
import com.ing.repoapp.R.id
import com.ing.repoapp.R.layout


/**
 * @author Emrah ÖZBAYSAR
 *
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_layout)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
            id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction { add(
            id.fragmentContainer, fragment()) }

    abstract fun fragment(): BaseFragment

}