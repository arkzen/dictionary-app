package studios.darkzen.dictionaryapp.service.repository;

import androidx.lifecycle.MutableLiveData;

import studios.darkzen.dictionaryapp.service.callback.ProgressCallback;
import studios.darkzen.dictionaryapp.service.model.RootResponse;

public interface DictionaryRepository {

   MutableLiveData<RootResponse> getApiResponse(String word, ProgressCallback progressCallback);

}
