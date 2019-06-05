package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {

    @GET("api/game/list")
    fun listOfGames(): Call<List<Games>>

    @GET("api/game/details")
    fun gameDetail(@Query("game_id") game_id : Int): Call<TODO>

}