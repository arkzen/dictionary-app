package studios.darkzen.dictionaryapp.service.repository;

import androidx.lifecycle.MutableLiveData;

import studios.darkzen.dictionaryapp.service.model.ApiResponseData;

public interface DictionaryRepository {

   MutableLiveData<ApiResponseData> getApiResponse();
}
