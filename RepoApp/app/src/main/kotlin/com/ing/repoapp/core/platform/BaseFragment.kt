package com.ing.repoapp.core.platform

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fernandocejas.sample.core.extension.appContext
import com.fernandocejas.sample.core.extension.viewContainer
import com.google.android.material.snackbar.Snackbar
import com.ing.repoapp.AndroidApplication
import com.ing.repoapp.R
import com.ing.repoapp.R.color
import com.ing.repoapp.core.di.ApplicationComponent
import javax.inject.Inject


/**
 * @author Emrah ÖZBAYSAR
 *
 * Base Fragment class with helper methods for handling views and back button events.
 * Why internal fun ? Because The tools consider the main and test source paths as part of the same module.
 * @see Fragment
 */

abstract class BaseFragment : Fragment() {


    abstract fun layoutId(): Int


    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun changeActionbarTitle(title:String) = with(activity) { if (this is BaseActivity) this.supportActionBar!!.title=title }

    internal fun loadActionBar(title:String,backable:Boolean)=with(activity){
        if (this is BaseActivity){
            this.supportActionBar!!.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            this.supportActionBar!!.setDisplayShowCustomEnabled(true)
            this.supportActionBar!!.setCustomView(R.layout.custom_action_bar_layout)
            val view = supportActionBar!!.customView
            val backButton = view.findViewById(R.id.action_bar_back) as ImageButton
            backButton.setOnClickListener {
                onBackPressed()
            }
            (view.findViewById(R.id.title) as TextView).text=title
            when(backable){
                true->{
                    backButton.visibility=View.VISIBLE
                    val parent: Toolbar = view.parent as Toolbar
                    parent.setContentInsetsAbsolute(0, 20)
                }
                else->{
                   backButton.visibility=View.GONE
                }
            }
        }
    }

    internal fun hideKeyboardFrom() {
        val imm: InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null
    /*
      internal fun showProgress() = progressStatus(View.VISIBLE)

      internal fun hideProgress() = progressStatus(View.GONE)

          private fun progressStatus(viewStatus: Int) = with(activity) { if (this is BaseActivity) this.progress.visibility = viewStatus }



          internal fun hideToolbar() = with(activity) { if (this is BaseActivity) this.toolbar.visibility = View.GONE }

          internal fun showToolbar() = with(activity) { if (this is BaseActivity) this.toolbar.visibility = View.VISIBLE }
      */
    internal fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(appContext,
            color.colorAccent))
        snackBar.show()
    }

}