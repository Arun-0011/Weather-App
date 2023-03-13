package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.retrofit.ApiInterface
import com.example.weatherapp.retrofit.RetrofitClient
import com.example.weatherapp.retrofit.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding?.searchIc?.setOnClickListener {
            weatherApi(binding!!.searchBar.text.toString())
        }
    }

    private fun weatherApi(cityName: String) {
        val apiInterface = RetrofitClient.getInstance().create(ApiInterface::class.java)
        apiInterface.weatherApi(cityName)
            .enqueue(object :
                Callback<WeatherResponse> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.code() == 200 || response.code() == 201) {
                        binding?.tvDegree?.text = response.body()?.main?.temp.toString() + "°C"
                        binding?.tvHumidity?.text = response.body()?.main?.humidity.toString() + "%"
                        binding?.tvCityName?.text = binding?.searchBar?.text
                        binding?.tvCityCode?.text = response.body()?.sys?.country.toString()
                        binding?.tvWindSpeed?.text = response.body()?.wind?.speed.toString()
                        binding?.tvLat?.text = response.body()?.coord?.lat.toString()
                        binding?.tvLon?.text = response.body()?.coord?.lon.toString()

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Please enter a valid State",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: $t")
                }
            })
    }
}