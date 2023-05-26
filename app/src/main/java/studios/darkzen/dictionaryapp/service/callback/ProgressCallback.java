package studios.darkzen.dictionaryapp.service.callback;

public interface ProgressCallback {
    void onDone(String message);
    void onFail(String message);
}
