package studios.darkzen.dictionaryapp.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dictionaryapp.R;

import studios.darkzen.dictionaryapp.service.repository.DictionaryRepo;

public class MainActivity extends AppCompatActivity {

    private DictionaryRepo dictionaryRepo;
    private AppCompatButton btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DictionaryRepo dictionaryRepo=DictionaryRepo.getInstanse(this);
        dictionaryRepo.getApiResponse();

        btnSearch=(AppCompatButton) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Homepage.class);
                startActivity(intent);
            }
        });
    }
}