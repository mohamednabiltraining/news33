package com.route.newsappc33.api

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {

    //Todo: search how to cache responses if no internet connection to get data
    companion object{
        private var retrofit : Retrofit? =null
        val loggingInterceptor = HttpLoggingInterceptor(object:
            HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.e("api",message)
            }
        })
        val langInterceptor = object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()
                val url =
                    request.url.newBuilder()
                        .addQueryParameter("language", "en").
                        build();
                request = request.newBuilder().url(url).build()
                return chain.proceed(request);
            }
        }
        val apiKeyInterceptor = object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()
                val url =
                    request.url.newBuilder()
                        .addQueryParameter("apiKey", "5909ae28122a471d8b0c237d5989cb73").
                        build();
                request = request.newBuilder().url(url).build()
                return chain.proceed(request);
            }
        }
        val tokenInterceptor = object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()

                val token = "asadsd";
                request = request.newBuilder()
                    .addHeader("Authorization","Bearer "+token)
                    .build()
                return chain.proceed(request);
            }
        }
        /*val authInterceptor = object :Authenticator{
            override fun authenticate(route: Route?, response: Response): Request? {
                /// call this function when response returns 401
                // regenerate auth token -> call api to refresh token
                // regenerate request to procceed again
                }
        }*/
        private fun getInstance():Retrofit{
            if (retrofit==null){

                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(langInterceptor)
                    .addInterceptor(apiKeyInterceptor)
                    .build()

                retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!;
        }

        fun getApis():WebServices{
           return getInstance().create(WebServices::class.java)
        }

    }
}