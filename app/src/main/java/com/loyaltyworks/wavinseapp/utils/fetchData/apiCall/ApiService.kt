package com.loyaltyworks.wavinseapp.utils.fetchData.apiCall

import com.example.authenticationmodule.PreferenceHelperToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


object ApiService {

    // Null or Empty Check from response
    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *> {
            val delegate: Converter<ResponseBody, *> =
                retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
            return Converter { body ->
                try {
                    if (body.contentLength() == 0L) null else delegate.convert(body)
                } catch (e: Exception) { null }
            }
        }
    }

    // we are creating a networking client using OkHttp and add our authInterceptor.
//    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    private val builder = OkHttpClient().newBuilder()
        .connectTimeout(5, TimeUnit.SECONDS)
//        .writeTimeout(30, TimeUnit.SECONDS)
//        .readTimeout(60, TimeUnit.SECONDS)

    private fun getRetrofit(): Retrofit {

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        builder.addInterceptor(Interceptor { chain ->

            val url = chain.request().url.newBuilder().build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .addHeader("Authorization", "${PreferenceHelperToken.getTokenDetails(
                    ApplicationClass.appContext)?.token_type} ${PreferenceHelperToken.getTokenDetails(ApplicationClass.appContext)?.access_token}")
                .build()
//             try {
            chain.proceed(request)
//            } catch (e: Exception) {
//                Response()
//            }
        })

        val apiClient = builder.build()

        return Retrofit.Builder().client(apiClient)
            .baseUrl(BuildConfig.BaseUrl)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val apiInterface: ApiInterface = getRetrofit().create(ApiInterface::class.java)

}