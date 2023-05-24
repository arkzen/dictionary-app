package studios.darkzen.dictionaryapp.view.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.dictionaryapp.R;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnSearch;
    private EditText etWordSearch;
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWordSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchAction();
            }
        });

        etWordSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    SearchAction();
                    return true;
                }
                return false;
            }
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
                    Toast.makeText(MainActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Something is wrong, try later", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Please enter any valid English word", Toast.LENGTH_SHORT).show();
        }
    }
}


