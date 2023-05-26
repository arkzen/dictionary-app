package studios.darkzen.dictionaryapp.view.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;

import java.io.IOException;
import java.util.List;

import studios.darkzen.dictionaryapp.service.callback.ProgressCallback;
import studios.darkzen.dictionaryapp.service.model.Phonetics;
import studios.darkzen.dictionaryapp.service.model.RootResponse;
import studios.darkzen.dictionaryapp.view.adapter.MeaningAdapter;
import studios.darkzen.dictionaryapp.viewmodel.DictionaryViewmodel;

public class Homepage extends AppCompatActivity {


    private MeaningAdapter meaningAdapter;
    private String word, phontxt;
    private RecyclerView rvMeaning;
    private EditText etSearch;
    private boolean isResume;
    private TextView tvWord, tvPhonetics, sourceUrl;
    private ImageButton imbAudioplay, btnSearch;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        tvWord = findViewById(R.id.tvWord);
        tvPhonetics = findViewById(R.id.tvPhonetics);
        imbAudioplay = findViewById(R.id.btnAudioplay);
        rvMeaning = findViewById(R.id.rvMeaning);
        sourceUrl = findViewById(R.id.tvSlink);
        progressDialog = new ProgressDialog(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etSearch.getText().toString().isEmpty()) {
                    word = etSearch.getText().toString().trim();
                    onSearch(word);
                } else {
                    Toast.makeText(Homepage.this, "Please enter a valid english word", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!etSearch.getText().toString().isEmpty()) {
                    word = etSearch.getText().toString().trim();
                    onSearch(word);
                } else {
                    Toast.makeText(Homepage.this, "Please enter a valid english word", Toast.LENGTH_SHORT).show();
                }
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
        dictionaryViewmodel.getApiResponse(word, new ProgressCallback() {
            @Override
            public void onDone(String message) {
                progressDialog.dismiss();
            }

            @Override
            public void onFail(String message) {
               progressDialog.dismiss();
            }
        }).observe(Homepage.this, new Observer<RootResponse>() {
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

                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                    tvPhonetics.setText("");
                }


                imbAudioplay.setOnClickListener(v -> {
                    String phnAudioLink = null;
                    List<Phonetics> phonetics = apiResponseData.getPhonetics();

                    try {
                        if (!phonetics.isEmpty()) {
                            phonetics.size();
                            if (phonetics.get(0).getAudio() != null && !phonetics.get(0).getAudio().isEmpty()) {
                                phnAudioLink = phonetics.get(0).getAudio();
                            } else if (phonetics.size() >= 1 && phonetics.get(1).getAudio() != null && !phonetics.get(1).getAudio().isEmpty()) {
                                phnAudioLink = phonetics.get(1).getAudio();
                            } else if (phonetics.size() >= 2 && phonetics.get(2).getAudio() != null && !phonetics.get(2).getAudio().isEmpty()) {
                                phnAudioLink = phonetics.get(2).getAudio();
                            } else {
                                Toast.makeText(Homepage.this, "Couldn't play audio for this word", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Homepage.this, "Couldn't play audio for this word", Toast.LENGTH_SHORT).show();
                    }


                    if (phnAudioLink != null && !phnAudioLink.isEmpty()) {
                        try {
                            if (!isResume) {
                                isResume = true;
                                imbAudioplay.setImageDrawable(ContextCompat.getDrawable(Homepage.this,R.drawable.ic_pause));
                                MediaPlayer player = new MediaPlayer();
                                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                player.setDataSource(phnAudioLink);
                                player.prepare();
                                player.setOnCompletionListener(mp -> {
                                    isResume = false;
                                    imbAudioplay.setImageDrawable(ContextCompat.getDrawable(Homepage.this,R.drawable.ic_playbtn));
                                });
                                player.start();
                            } else {
                                isResume = false;
                                imbAudioplay.setImageDrawable(ContextCompat.getDrawable(Homepage.this,R.drawable.ic_playbtn));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Homepage.this, "Couldn't play audio for this word", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                try {
                    if (!apiResponseData.getSourceUrls().get(0).isEmpty()) {
                        StringBuilder sourceurl = new StringBuilder();
                        sourceurl.append(apiResponseData.getSourceUrls().get(0));
                        sourceUrl.setText(sourceurl);
                        sourceUrl.setSelected(true);
                    } else {
                        sourceUrl.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sourceUrl.setText("");
                }

            }
        });
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 dictionaryapi.dev";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

    public void onSearch(String word) {
        progressDialog.setTitle("Searching meanings for word: " + word);
        progressDialog.show();
        try {
            if (!TextUtils.isEmpty(word)) {

                if (isConnected()) {
                    setvalues(word);
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
    }
}

