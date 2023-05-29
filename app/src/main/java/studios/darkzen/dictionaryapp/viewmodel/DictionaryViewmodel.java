package studios.darkzen.dictionaryapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import studios.darkzen.dictionaryapp.service.callback.ProgressCallback;
import studios.darkzen.dictionaryapp.service.model.RootResponse;
import studios.darkzen.dictionaryapp.service.repository.DictionaryRepoImp;
import studios.darkzen.dictionaryapp.service.repository.DictionaryRepository;

public class DictionaryViewmodel extends AndroidViewModel {


    private DictionaryRepository dRepo;

    public DictionaryViewmodel(@NonNull Application application) {
        super(application);

        dRepo = DictionaryRepoImp.getInstanse(application);
    }

    public MutableLiveData<RootResponse> getApiResponse(Context context, String word, ProgressCallback progressCallback) {
        return dRepo.getApiResponse(context, word, progressCallback);
    }
}
