package studios.darkzen.dictionaryapp.service.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstanse {

    private static Retrofit retrofit;
    public static String BASE_URL = "https://api.dictionaryapi.dev/api/v2/";

    public static Retrofit getRetrofitInstanse() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
