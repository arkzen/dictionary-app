package studios.darkzen.dictionaryapp.view.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.dictionaryapp.R;

import studios.darkzen.dictionaryapp.view.utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnSearch;
    private EditText etWordSearch;
    private String word;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWordSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        context = MainActivity.this;

        btnSearch.setOnClickListener(v -> SearchAction());
        etWordSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                SearchAction();
                return true;
            }
            return false;
        });

    }

    private void SearchAction() {
        word = etWordSearch.getText().toString().trim();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!TextUtils.isEmpty(word)) {
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                    intent.putExtra("SearchWord", word);
                    startActivity(intent);
                } else {
                    Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_sad);
                    ToastUtil.customShowToast(context, "No Internet", icon);
                }
            } else {
                Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_sad);
                ToastUtil.customShowToast(context, "Try later", icon);

            }
        } else {
            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_sad);
            ToastUtil.customShowToast(context, "Enter any word", icon);

        }
    }

}


