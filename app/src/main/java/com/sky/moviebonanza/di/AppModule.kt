package com.sky.moviebonanza.di

import android.content.Context
import com.sky.moviebonanza.network.MovieApi
import com.sky.moviebonanza.repository.MovieRepository
import com.sky.moviebonanza.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context,
                             httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient
    = okHttpClient(context)


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: MovieApi) = MovieRepository(apiService)


    private fun okHttpClient(context: Context): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
        return builder.build()
    }


    private var onlineInterceptor: Interceptor = Interceptor { chain ->
        val response: Response = chain.proceed(chain.request())
        val maxAge = 60 * 10// read from cache for 10 minutes even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
}


