package com.ing.repoapp.feature.domain.entities

data class RepoEntity(private val id: Int,
                      private val name: String,
                      private val owner:Owner,
                      private val stargazers_count: Int,
                      private val open_issues: Int
                      ) {
    fun toRepo() = Repo(id, name,owner,stargazers_count,open_issues)
}
