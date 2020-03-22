package com.ing.repoapp.feature.domain.repositories

import com.ing.repoapp.feature.domain.entities.RepoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ReposApi {


    @GET("{username}/repos") fun repos(@Path("username") username:String): Call<List<RepoEntity>>
    //@GET(MOVIE_DETAILS) fun movieDetails(@Path(PARAM_MOVIE_ID) movieId: Int): Call<MovieDetailsEntity>

}
