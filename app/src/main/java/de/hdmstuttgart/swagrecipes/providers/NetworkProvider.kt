package de.hdmstuttgart.swagrecipes.providers

import de.hdmstuttgart.swagrecipes.data.api.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {
    private lateinit var instance: NetworkService
    private const val baseUrl = "https://api.spoonacular.com/"
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create()

    fun getInstance(): NetworkService {
        synchronized(this) {
            if (!::instance.isInitialized) {
                instance = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .build()
                    .create(NetworkService::class.java)
            }
            return instance
        }
    }
}