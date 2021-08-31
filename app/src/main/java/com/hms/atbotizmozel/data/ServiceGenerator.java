package com.hms.atbotizmozel.data;

import android.content.Context;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Batuhan on 03.10.2017.
 */

public class ServiceGenerator {

    public static final String BASE_URL = "https://autotrainbrains.com";
    public static final String API_ADRESS = "/api";
    public static final String URL_PORT = "3500";
//    public static final String URL_PORT = "3000";

    private Context context;

    private static ServiceGenerator serviceGenerator;

    public static ServiceGenerator with(Context c) {
        if (serviceGenerator == null)
            serviceGenerator = new ServiceGenerator(c);
        return serviceGenerator;
    }

    private ServiceGenerator(Context c) {
        context = c;
    }

    private static Retrofit retrofit;

    /**
     * Default serviceGenerator generator for rest api calls used with retrofit2.
     *
     * @param serviceClass serviceGenerator class
     * @param <S>          serviceGenerator
     * @return serviceGenerator object
     */
    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = getRetrofitBuilder();
        return retrofit.create(serviceClass);
    }

    /**
     * Returns the default retrofitBuilder builder.
     *
     * @return retrofitBuilder object
     */
    private Retrofit getRetrofitBuilder() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getDefaultClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * Default http client.
     *
     * @return OkHttpClient
     */
    private OkHttpClient getDefaultClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build();
        } catch (Exception ignored) {

        }


        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
