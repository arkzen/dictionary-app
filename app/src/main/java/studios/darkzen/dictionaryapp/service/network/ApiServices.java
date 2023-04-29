package studios.darkzen.dictionaryapp.service.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import studios.darkzen.dictionaryapp.service.model.RootResponse;

public interface ApiServices {

    @GET("entries/en/{word}")
    Call<List<RootResponse>> getDefinition(@Path("word") String word);
}
