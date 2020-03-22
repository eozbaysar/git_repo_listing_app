package com.ing.repoapp.feature.domain.repositories

import com.ing.repoapp.RepoAppUnitTest
import com.ing.repoapp.core.exception.Failure
import com.ing.repoapp.core.functional.Either
import com.ing.repoapp.core.functional.Either.Right
import com.ing.repoapp.core.platform.NetworkHandler
import com.ing.repoapp.feature.data.Network
import com.ing.repoapp.feature.domain.entities.Owner
import com.ing.repoapp.feature.domain.entities.Repo
import com.ing.repoapp.feature.domain.entities.RepoEntity
import com.ing.repoapp.feature.domain.services.ReposService
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class ReposRepositoryTest : RepoAppUnitTest(){


    private lateinit var networkRepository: Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: ReposService

    @Mock
    private lateinit var reposCall: Call<List<RepoEntity>>
    @Mock
    private lateinit var reposResponse: Response<List<RepoEntity>>

    @Before
    fun setUp() {
        networkRepository = Network(networkHandler, service)
    }


    @Test
    fun `should return empty list by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { reposResponse.body() }.willReturn(null)
        given { reposResponse.isSuccessful }.willReturn(true)
        given { reposCall.execute() }.willReturn(reposResponse)
        given { service.repos("eozbaysar") }.willReturn(reposCall)

        val repos = networkRepository.repos("eozbaysar")

        repos shouldEqual Right(emptyList<Repo>())

        verify(service).repos("eozbaysar")
    }

    @Test
    fun `should get movie list from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { reposResponse.body() }.willReturn(listOf(RepoEntity(
            230303429,
            "flutter_workmanager",
            Owner("eozbaysar", "https://avatars1.githubusercontent.com/u/12526860?v=4"),
            0,
            0
        )))
        given { reposResponse.isSuccessful }.willReturn(true)
        given { reposCall.execute() }.willReturn(reposResponse)
        given { service.repos("eozbaysar") }.willReturn(reposCall)

        val repos = networkRepository.repos("eozbaysar")

        repos shouldEqual Right(listOf(
            Repo(
                230303429,
                "flutter_workmanager",
                Owner("eozbaysar", "https://avatars1.githubusercontent.com/u/12526860?v=4"),
                0,
                0
            )
        ))
        verify(service).repos("eozbaysar")
    }

    @Test
    fun `repos service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val movies = networkRepository.repos("eozbaysar")

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `repos service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val movies = networkRepository.repos("eozbaysar")

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `repos service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movies = networkRepository.repos("eozbaysar")

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test
    fun `repos request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val movies = networkRepository.repos("eozbaysar")

        movies shouldBeInstanceOf Either::class.java
        movies.isLeft shouldEqual true
        movies.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

}