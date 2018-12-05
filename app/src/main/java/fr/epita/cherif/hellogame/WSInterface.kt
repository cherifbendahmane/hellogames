package fr.epita.cherif.hellogame

import retrofit2.http.GET
import retrofit2.http.Query

interface WebInterface {

    @GET("game/list")
    fun listJeux(): retrofit2.Call<List<Jeux>>

    @GET("game/details")
    fun getDesc(@Query("game_id") id: Int): retrofit2.Call<Jeux>
}