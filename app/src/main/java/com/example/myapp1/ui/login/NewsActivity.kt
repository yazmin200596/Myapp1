package com.example.myapp1.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp1.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NewsActivity : AppCompatActivity() {

    var recyclerViewNews : RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        recyclerViewNews = findViewById(R.id.recyclerView_news)
    }


    fun createUserNetPay( responseToken: (responseDTO: CreateUserNetPayResponseDTO?, error: ErrorDTO?) -> Unit){
        getNewsClient().getNews(createTokenBody).enqueue(object : Callback<CreateUserNetPayResponseDTO> {
            override fun onFailure(call: Call<CreateUserNetPayResponseDTO>, t: Throwable) {
                val errorDTO = ErrorDTO(FailRequest,t.message ?: FailRequestMessage)
                responseToken.invoke(null,errorDTO)
            }
            override fun onResponse(call: Call<CreateUserNetPayResponseDTO>, response: Response<CreateUserNetPayResponseDTO>) {
                responseToken.invoke(response.body(),if (response.isSuccessful) null else ErrorDTO(FailRequest,FailRequestServerMessage))
            }
        })
    }

    fun getNewsClient() : NewsServices{
        val client = getNews("")
            .create(NewsServices::class.java)
        return client
    }

    fun getNews(url: String): Retrofit {
        //

        val AurigaClient = OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.MINUTES)
            .readTimeout(6, TimeUnit.MINUTES)
            .writeTimeout(6, TimeUnit.MINUTES)

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(AurigaClient.build())
            .build()
        return retrofit
    }
}