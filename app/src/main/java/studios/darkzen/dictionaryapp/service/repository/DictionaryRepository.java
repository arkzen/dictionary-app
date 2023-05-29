package studios.darkzen.dictionaryapp.service.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import studios.darkzen.dictionaryapp.service.callback.ProgressCallback;
import studios.darkzen.dictionaryapp.service.model.RootResponse;

public interface DictionaryRepository {

   MutableLiveData<RootResponse> getApiResponse(Context context, String word, ProgressCallback progressCallback);

}
