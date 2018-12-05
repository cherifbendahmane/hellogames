package fr.epita.cherif.hellogame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val listJeux = arrayListOf<Jeux>()
	
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
	
    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
	
    val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
	
    val service: WebInterface = retrofit.create(WebInterface::class.java)
	
    val callback = object : Callback<List<Jeux>> {
	
        override fun onFailure(call: Call<List<Jeux>>?, t: Throwable?) {
		
            Log.d("TAG", "error")
        }
		
        override fun onResponse(call: Call<List<Jeux>>?, response: Response<List<Jeux>>?) {
		
            if (response != null) {
                if (response.code() == 200) {
                    val value = response.body()
                    if (value != null) {
                        listJeux.addAll(value)
                        Log.d("TAG", "success")
                        val gameTab = ArrayList<Int>()
                        for (i in 0..4) {
                            val randomInt = (0..listJeux.size - 1).shuffled().first()
                            if (!gameTab.contains(randomInt)) {
                                gameTab.add(randomInt)
                            }
                        }
                        image1.setImageResource(getImage(listJeux[gameTab.get(0)].id!!))
                        image1.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("selector", listJeux[gameTab.get(0)].id)
                            startActivity(explicitIntent)
                        }

                        image2.setImageResource(getImage(listJeux[gameTab.get(1)].id!!))

                        image2.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("selector", listJeux[gameTab.get(1)].id)
                            startActivity(explicitIntent)
                        }

                        image3.setImageResource(getImage(listJeux[gameTab.get(2)].id!!))

                        image3.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("selector", listJeux[gameTab.get(2)].id)
                            startActivity(explicitIntent)
                        }

                        image4.setImageResource(getImage(listJeux[gameTab.get(3)].id!!))

                        image4.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("selector", listJeux[gameTab.get(3)].id)
                            startActivity(explicitIntent)
                        }

                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service.listJeux().enqueue(callback)
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