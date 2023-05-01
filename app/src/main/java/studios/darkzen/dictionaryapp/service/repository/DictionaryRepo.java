package studios.darkzen.dictionaryapp.service.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studios.darkzen.dictionaryapp.service.model.Meanings;
import studios.darkzen.dictionaryapp.service.model.Phonetics;
import studios.darkzen.dictionaryapp.service.model.RootResponse;
import studios.darkzen.dictionaryapp.service.network.ApiServices;
import studios.darkzen.dictionaryapp.service.network.RetrofitInstanse;

public class DictionaryRepo {

    private static DictionaryRepo instanse;
    private static Context dContext;
    private String word = "go";
    private RootResponse rootResponse;
    private List<Meanings> meanings;
    private List<Phonetics> phonetics;


    public static DictionaryRepo getInstanse(Context context) {
        if (instanse == null) {
            dContext = context;
            instanse = new DictionaryRepo();

        }
        return instanse;
    }

    public RootResponse getApiResponse() {

        ApiServices apiServices = RetrofitInstanse.getRetrofitInstanse().create(ApiServices.class);
        Call<List<RootResponse>> call = apiServices.getDefinition(word);

        try {
            call.enqueue(new Callback<List<RootResponse>>() {
                @Override
                public void onResponse(Call<List<RootResponse>> call, Response<List<RootResponse>> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(dContext, "Error! Something is Wrong", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    assert response.body() != null;
                    rootResponse = response.body().get(0);

                }

                @Override
                public void onFailure(Call<List<RootResponse>> call, Throwable t) {
                    Toast.makeText(dContext, "An Error Occurred, Try Later!!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(dContext, "An Error Occurred, Try Later!!", Toast.LENGTH_SHORT).show();

        }
        return rootResponse;
    }


}
