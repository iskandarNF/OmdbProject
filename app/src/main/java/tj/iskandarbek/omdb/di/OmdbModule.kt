package tj.iskandarbek.omdb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import tj.iskandarbek.omdb.data.remote.OmdbApi
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object OmdbModule {
    @Provides
    fun providesOmdbApi(
        @Named("appRetrofit") retrofit: Retrofit
    ): OmdbApi = retrofit.create(OmdbApi::class.java)
}