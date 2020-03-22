package com.ing.repoapp.feature.presentation.views

import android.content.Context
import android.content.Intent
import com.ing.repoapp.core.platform.BaseActivity
import com.ing.repoapp.feature.presentation.parcelableviews.RepoView

class RepoDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM = "com.ing.intentrepo"

        fun callingIntent(context: Context, repo: RepoView): Intent {
            val intent = Intent(context, RepoDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM, repo)
            return intent
        }

    }

    override fun fragment() = RepoDetailsFragment.forRepo(intent.getParcelableExtra(INTENT_EXTRA_PARAM))
}
