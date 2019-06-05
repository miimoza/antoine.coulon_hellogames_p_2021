package fr.epita.android.hellogames
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageFragment : Fragment() {

    var gameArray = arrayListOf<TODO>()
    val randomGames = arrayListOf<Games>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    interface InteractionListener {
        fun imageWasClicked(i: Int)
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
                Toast.makeText(this@ImageFragment.context, "WebService call failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TODO>, response: Response<TODO>) {
                if (response.code() == 200) {
                    val responseData = response.body()

                    if (responseData != null) {
                        gameArray.add(responseData)
                    }
                }
            }
        }

        val gameListCallback: Callback<List<Games>> = object : Callback<List<Games>> {
            override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                Toast.makeText(this@ImageFragment.context, "WebService call failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                if (response.code() == 200) {
                    val responseData = response.body()

                    if (responseData != null) {

                        for (i in 0..3) {
                            var d = responseData.get((Math.random() * responseData.size).toInt())

                            while (randomGames.contains(d)) {
                                d = responseData.get((Math.random() * responseData.size).toInt())
                            }

                            randomGames.add(d)
                            //service.gameDetail(d.id).enqueue(gameDetailCallback)
                        }


                        Glide.with(this@ImageFragment).load(randomGames[0].picture).override(350, 350).into(game1_image)
                        Glide.with(this@ImageFragment).load(randomGames[1].picture).override(350, 350).into(game2_image)
                        Glide.with(this@ImageFragment).load(randomGames[2].picture).override(350, 350).into(game3_image)
                        Glide.with(this@ImageFragment).load(randomGames[3].picture).override(350, 350).into(game4_image)
                    }
                }
            }
        }

        service.listOfGames().enqueue(gameListCallback)

        game1_image.setOnClickListener {
            //(activity as MainActivity).imageWasClicked(gameArray[0])
            (activity as MainActivity).imageWasClicked(randomGames[0].id)
        }

        game2_image.setOnClickListener {
           // (activity as MainActivity).imageWasClicked(gameArray[1])
            (activity as MainActivity).imageWasClicked(randomGames[1].id)
        }

        game3_image.setOnClickListener {
           // (activity as MainActivity).imageWasClicked(gameArray[2])
            (activity as MainActivity).imageWasClicked(randomGames[2].id)
        }

        game4_image.setOnClickListener {
            //(activity as MainActivity).imageWasClicked(gameArray[3])
            (activity as MainActivity).imageWasClicked(randomGames[3].id)
        }
    }
}
