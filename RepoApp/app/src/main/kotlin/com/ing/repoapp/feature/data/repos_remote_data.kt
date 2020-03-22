package com.ing.repoapp.feature.data

import com.ing.repoapp.core.exception.Failure
import com.ing.repoapp.core.exception.Failure.NetworkConnection
import com.ing.repoapp.core.exception.Failure.ServerError
import com.ing.repoapp.core.functional.Either
import com.ing.repoapp.core.functional.Either.Left
import com.ing.repoapp.core.functional.Either.Right
import com.ing.repoapp.core.platform.NetworkHandler
import com.ing.repoapp.feature.domain.entities.Repo
import com.ing.repoapp.feature.domain.repositories.ReposRepository
import com.ing.repoapp.feature.domain.services.ReposService
import javax.inject.Inject
import retrofit2.Call
import javax.inject.Singleton

@Singleton
class Network
@Inject constructor(private val networkHandler: NetworkHandler, private val service: ReposService) : ReposRepository {

    override fun repos(username:String): Either<Failure, List<Repo>> {
        return when (networkHandler.isConnected) {
            true -> request(service.repos(username), { it.map { it.toRepo() } }, emptyList())
            false, null -> Left(NetworkConnection)
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> Left(ServerError)
            }
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }
}