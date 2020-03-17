package practice.kotlin.com.wordquiz

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface apiService {

    // 1 은 토익단어 2는 토스단어
    @GET("/word")
    fun getWord(@Query("category") a : Int) : Observable<JsonObject>

    @POST("/toeic")
    fun getUserP(korean : String?, english: String?) :  Call<ResponseBody>

}