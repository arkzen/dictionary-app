package studios.darkzen.dictionaryapp.view.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;

import java.io.IOException;
import java.util.List;

import studios.darkzen.dictionaryapp.service.model.Phonetics;
import studios.darkzen.dictionaryapp.service.model.RootResponse;
import studios.darkzen.dictionaryapp.view.adapter.MeaningAdapter;
import studios.darkzen.dictionaryapp.viewmodel.DictionaryViewmodel;

public class Homepage extends AppCompatActivity {


    private MeaningAdapter meaningAdapter;
    private String word, phontxt;
    private RecyclerView rvMeaning;
    private TextView tvWord, tvPhonetics, sourceUrl;
    private ImageButton imbAudioplay;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        SearchView svSearch = findViewById(R.id.etSearch);
        tvWord = findViewById(R.id.tvWord);
        tvPhonetics = findViewById(R.id.tvPhonetics);
        imbAudioplay = findViewById(R.id.btnAudioplay);
        rvMeaning = findViewById(R.id.rvMeaning);
        sourceUrl = findViewById(R.id.tvSlink);
        progressDialog = new ProgressDialog(this);

        svSearch.clearFocus();
        svSearch.setFocusable(false);
        svSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svSearch.setFocusable(true);
                svSearch.setFocusableInTouchMode(true);
                svSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(svSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Searching meanings for word: " + word);
                progressDialog.show();
                try {
                    if (!TextUtils.isEmpty(query)) {

                        if (isConnected()) {
                            setvalues(query);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Homepage.this, "No internet, Try later", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Homepage.this, "Please enter any valid English word", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Homepage.this, "Error, Try later", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        Intent intent = getIntent();
        if (intent.hasExtra("SearchWord")) {
            progressDialog.setTitle("Searching meanings for word: " + word);
            progressDialog.show();
            word = intent.getStringExtra("SearchWord");
            if (!TextUtils.isEmpty(word)) {
                try {
                    if (isConnected()) {
                        setvalues(word);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Homepage.this, "No internet, Try later", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Homepage.this, "Error, Try later", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(Homepage.this, "No search word provided", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void setvalues(String word) {


        DictionaryViewmodel dictionaryViewmodel = new ViewModelProvider(Homepage.this).get(DictionaryViewmodel.class);
        dictionaryViewmodel.getApiResponse(word).observe(Homepage.this, new Observer<RootResponse>() {
            @Override
            public void onChanged(RootResponse apiResponseData) {

                rvMeaning.setHasFixedSize(true);
                rvMeaning.setLayoutManager(new GridLayoutManager(Homepage.this, 1));
                meaningAdapter = new MeaningAdapter(Homepage.this, apiResponseData.getMeanings());
                rvMeaning.setAdapter(meaningAdapter);


                if (!apiResponseData.getWord().isEmpty()) {
                    String searchword = apiResponseData.getWord();
                    tvWord.setText(searchword);

                }
                if (!apiResponseData.getPhonetics().isEmpty()) {
                    if (apiResponseData.getPhonetics().get(0).getText() != null) {
                        phontxt = apiResponseData.getPhonetics().get(0).getText();
                        tvPhonetics.setText(phontxt);
                    } else if (apiResponseData.getPhonetics().get(1).getText() != null) {
                        phontxt = apiResponseData.getPhonetics().get(0).getText();
                        tvPhonetics.setText(phontxt);
                    } else {
                        tvPhonetics.setText("");
                    }
                } else {
                    tvPhonetics.setText("");
                }


                imbAudioplay.setOnClickListener(v -> {
                    String phnAudioLink = null;
                    List<Phonetics> phonetics = apiResponseData.getPhonetics();

                    if (!phonetics.isEmpty()) {
                        phonetics.size();
                        if (phonetics.get(0).getAudio() != null) {
                            phnAudioLink = phonetics.get(0).getAudio();
                        } else if (phonetics.size() > 1 && phonetics.get(1).getAudio() != null) {
                            phnAudioLink = phonetics.get(1).getAudio();
                        }
                    }

                    if (phnAudioLink != null) {
                        try {
                            MediaPlayer player = new MediaPlayer();
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            player.setDataSource(phnAudioLink);
                            player.prepare();
                            player.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Homepage.this, "Couldn't play audio for this word", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if (!apiResponseData.getSourceUrls().get(0).isEmpty()) {
                    StringBuilder sourceurl = new StringBuilder();
                    sourceurl.append(apiResponseData.getSourceUrls().get(0));
                    sourceUrl.setText(sourceurl);
                    sourceUrl.setSelected(true);
                } else {
                    sourceUrl.setText("");
                }
                progressDialog.dismiss();
            }
        });
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 dictionaryapi.dev";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}

