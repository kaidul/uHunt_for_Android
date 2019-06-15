package org.onlinejudge.uhunt.data.network

import org.onlinejudge.uhunt.CommonUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiEndPointClient {
//    @Volatile
//    private var instance: ApiEndPointInterface? = null

    fun newInstance(): ApiEndPointInterface {
//        if (instance == null) {
//            synchronized(ApiEndPointClient::class.java) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(CommonUtils.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

//                instance = retrofit.create(ApiEndPointInterface::class.java)
//            }
//        }
        return retrofit.create(ApiEndPointInterface::class.java)
    }
}
