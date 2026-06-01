package be.howest.eliasosaer.fittrackpro.di

import be.howest.eliasosaer.fittrackpro.BuildConfig
import be.howest.eliasosaer.fittrackpro.data.remote.api.GearApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }.value

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("x-api-key", BuildConfig.API_KEY)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }.value

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttp: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideGearApi(retrofit: Retrofit): GearApi {
        return retrofit.create(GearApi::class.java)
    }
}