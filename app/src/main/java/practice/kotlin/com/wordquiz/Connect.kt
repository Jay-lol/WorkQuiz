package practice.kotlin.com.wordquiz

import android.util.Log
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
class Connect : addText{
    fun getService(): apiService {

        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-15-164-214-98.ap-northeast-2.compute.amazonaws.com:8080")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiService::class.java)
        return retrofit
    }

    override fun addWord(list: String){
        Log.d("pass data result ",list)
    }
}
