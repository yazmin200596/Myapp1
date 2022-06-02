package com.example.myapp1.ui.login

import retrofit2.http.GET

interface NewsServices {
    @GET("top-headlines?country=mx&apiKey=c4b0de2e99c9432db73fd5f1bd6636ea")
    fun getNews(): Call<CreateUserNetPayResponseDTO>

}