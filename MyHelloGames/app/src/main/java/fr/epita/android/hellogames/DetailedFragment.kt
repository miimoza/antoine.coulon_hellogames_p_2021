package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_detailed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailedFragment(private val i: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?) {
        val baseURL = "https://androidlessonsapi.herokuapp.com/"

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WSInterface = retrofit.create(WSInterface::class.java)

        val gameDetailCallback: Callback<TODO> = object : Callback<TODO> {
            override fun onFailure(call: Call<TODO>, t: Throwable) {
                Toast.makeText(this@DetailedFragment.context, "WebService call failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TODO>, response: Response<TODO>) {
                if (response.code() == 200) {
                    val responseData = response.body()

                    if (responseData != null) {
                        Glide.with(this@DetailedFragment).load(responseData.picture).override(160, 160).into(gameImage)

                        // Type
                        gameType.text = responseData.type

                        // Numbers of Players
                        gameNbPlayers.text = responseData.players.toString()

                        // Year
                        gameYear.text = responseData.year.toString()

                        // Name
                        gameName.text = responseData.name
                        gameDescription.text = responseData.description_en


                        gameKnowMore.setOnClickListener {
                            val uriUrl = Uri.parse(responseData.url)
                            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                            startActivity(launchBrowser)
                        }
                    }
                }
            }
        }

        service.gameDetail(i).enqueue(gameDetailCallback)


    }
}
