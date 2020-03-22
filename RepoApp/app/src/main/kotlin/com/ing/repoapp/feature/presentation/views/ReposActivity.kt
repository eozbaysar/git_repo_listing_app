package com.ing.repoapp.feature.presentation.views

import android.content.Context
import android.content.Intent
import com.ing.repoapp.core.platform.BaseActivity

class ReposActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, ReposActivity::class.java)
    }

    override fun fragment() = ReposFragment()


}
