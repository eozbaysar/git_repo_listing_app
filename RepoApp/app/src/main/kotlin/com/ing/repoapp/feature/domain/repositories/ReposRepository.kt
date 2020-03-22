package com.ing.repoapp.feature.domain.repositories

import com.ing.repoapp.core.exception.Failure
import com.ing.repoapp.core.functional.Either
import com.ing.repoapp.feature.domain.entities.Repo

interface ReposRepository {

    fun repos(username:String): Either<Failure, List<Repo>>

    //fun movieDetails(movieId: Int): Either<Failure, MovieDetails>

}
