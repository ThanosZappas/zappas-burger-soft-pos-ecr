package gr.android.softposecr.di;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {

    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String API_KEY = "f82912e5c27b184e90326831b51734bc"; // Your TMDb API key

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    okhttp3.Request original = chain.request();
                    okhttp3.HttpUrl originalHttpUrl = original.url();

                    // Request customization: add request headers
                    okhttp3.HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .build();

                    okhttp3.Request.Builder requestBuilder = original.newBuilder().url(url);
                    okhttp3.Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .build();
    }

}

