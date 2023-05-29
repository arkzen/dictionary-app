package studios.darkzen.dictionaryapp.service.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.dictionaryapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studios.darkzen.dictionaryapp.service.callback.ProgressCallback;
import studios.darkzen.dictionaryapp.service.model.RootResponse;
import studios.darkzen.dictionaryapp.service.network.ApiServices;
import studios.darkzen.dictionaryapp.service.network.RetrofitInstanse;
import studios.darkzen.dictionaryapp.view.utils.ToastUtil;

public class DictionaryRepoImp implements DictionaryRepository {

    private static DictionaryRepoImp instanse;
    private static Context dContext;
    private RootResponse rootResponse;
    private MutableLiveData mLivedata;


    public static DictionaryRepoImp getInstanse(Context context) {
        if (instanse == null) {
            dContext = context;
            instanse = new DictionaryRepoImp();

        }
        return instanse;
    }

    public MutableLiveData<RootResponse> getApiResponse(Context context, String word, ProgressCallback progressCallback) {

        if (mLivedata == null) {
            mLivedata = new MutableLiveData();
        }

        ApiServices apiServices = RetrofitInstanse.getRetrofitInstanse().create(ApiServices.class);
        Call<List<RootResponse>> call = apiServices.getDefinition(word);

        try {
            call.enqueue(new Callback<List<RootResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<RootResponse>> call, @NonNull Response<List<RootResponse>> response) {

                    if (!response.isSuccessful()) {
                        Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_sad);
                        ToastUtil.customShowToast(context, "Not Found", icon);
                        progressCallback.onFail(response.message());
                        return;
                    }
                    assert response.body() != null;
                    rootResponse = response.body().get(0);

                    mLivedata.postValue(rootResponse);
                    progressCallback.onDone(response.message());

                }

                @Override
                public void onFailure(@NonNull Call<List<RootResponse>> call, @NonNull Throwable t) {
                    Toast.makeText(dContext, "An Error Occurred, Try Later!!", Toast.LENGTH_SHORT).show();
                    progressCallback.onFail(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(dContext, "An Error Occurred, Try Later!!", Toast.LENGTH_SHORT).show();
            progressCallback.onFail(e.getMessage());

        }
        return mLivedata;
    }


}
