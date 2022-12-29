package de.hdmstuttgart.swagrecipes.data.api
import de.hdmstuttgart.swagrecipes.data.model.recipe.RandomRecipesResponse
import de.hdmstuttgart.swagrecipes.data.model.recipe.RecipeResponse
import de.hdmstuttgart.swagrecipes.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    // @Headers("X-RapidAPI-Key: $API_KEY")
    // @Headers("X-RapidAPI-Host: $API_HOST")
    @GET("recipes/479101/information")
    suspend fun getRecipeInformation(@Query("id") id: String): RecipeResponse


    @Headers("x-api-key: $API_KEY")
    @GET("recipes/random")
    suspend fun getRandomRecipes(@Query("number") number: Int = 5): RandomRecipesResponse
}