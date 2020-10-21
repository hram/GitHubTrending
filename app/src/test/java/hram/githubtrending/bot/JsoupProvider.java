package hram.githubtrending.bot;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Kosh on 02 Jun 2017, 12:47 PM
 */

public class JsoupProvider {

    private static OkHttpClient okHttpClient;

    private static OkHttpClient provideOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(60, TimeUnit.SECONDS);
            client.readTimeout(60, TimeUnit.SECONDS);
            client.writeTimeout(60, TimeUnit.SECONDS);
            //client.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            okHttpClient = client.build();
        }
        return okHttpClient;
    }

    public static ScrapService getTrendingService(@NonNull String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ScrapService.class);
    }
}
