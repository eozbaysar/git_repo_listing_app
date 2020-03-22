package com.ing.repoapp.feature.domain.entities

import com.ing.repoapp.core.extension.empty

data class Repo(val id: Int, val name: String,val owner: Owner,val stargazers_count: Int, val open_issues: Int) {

    companion object {
        fun empty() = Repo(0, String.empty(),Owner("",""),0,0)
    }

}
