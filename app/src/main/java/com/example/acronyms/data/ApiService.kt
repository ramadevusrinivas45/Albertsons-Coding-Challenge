package com.example.acronyms.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("software/acromine/dictionary.py")
    suspend fun getAcronyms(@Query("sf") abbreviation: String): List<AcronymsResponse>
}