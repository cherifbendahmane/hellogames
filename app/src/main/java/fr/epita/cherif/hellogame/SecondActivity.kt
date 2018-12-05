package fr.epita.cherif.hellogame


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {

    var jeu = Jeux()
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
    val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
    val service: WebInterface = retrofit.create(WebInterface::class.java)
    val callback = object : retrofit2.Callback<Jeux> {
        override fun onFailure(call: Call<Jeux>?, t: Throwable?) {
            Log.d("TAG", "WebInterface call failed")
        }
        override fun onResponse(call: Call<Jeux>?,
                                response: Response<Jeux>?) {
            // Code here what happens when WebInterface responds
            if (response != null) {
                Log.d("TAG", response.toString())
                if (response.code() == 200) {
                    val value = response.body()
                    if (value != null) {
                        jeu = value
                        findViewById<ImageView>(R.id.imageView).setImageResource(getImage(jeu.id!!))
                        findViewById<TextView>(R.id.name).text = "Nom: " + jeu.name
                        findViewById<TextView>(R.id.type).text = "Type: " + jeu.type
                        findViewById<TextView>(R.id.nbjoueurs).text = "Nb Joueur(s): " + jeu.players.toString()
                        findViewById<TextView>(R.id.year).text = "Annee: " + jeu.year.toString()
                        findViewById<TextView>(R.id.desc).text = "Description: " + jeu.description_en

                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val selector = intent.getIntExtra("selector", -1)
        service.getDesc(selector).enqueue(callback)
    }

    fun getImage(id: Int): Int {

        var selection = 0

        when(id) {

            1 -> selection = R.drawable.tictactoe
            2 -> selection = R.drawable.hangman
            3 -> selection = R.drawable.sudoku
            4 -> selection = R.drawable.battleship
            5 -> selection = R.drawable.mineswepper
            6 -> selection = R.drawable.gameoflife
            7 -> selection = R.drawable.memory
            8 -> selection = R.drawable.simon
            9 -> selection = R.drawable.slidingpuzzle
            10 -> selection = R.drawable.mastermind

        }

        return selection
    }
}