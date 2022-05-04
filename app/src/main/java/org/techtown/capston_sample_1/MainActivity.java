package org.techtown.capston_sample_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "<<MainActivity>>";

    ViewPager pager;

    Button buttonBack;
    Button buttonNext;
    Button buttonRandom;

    String textInputed = "";
    String styleInputed = "";

    Boolean login = false;
    String id = "";
    String pwd = "";

    final int PERMISSION = 1;

    String quality = "100";

    // 번역
    Translator koToEnTranslator;
    String translatedInput = ""; // 영어로 번역된 글자
    Boolean downloadCheck = false; // 번역 모델 다운로드 확인

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 번역 초기화
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.KOREAN)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build();
        koToEnTranslator = Translation.getClient(options);

        // 번역 모델 다운로드
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        koToEnTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(unused -> {
            Log.d(TAG, "번역 모델 다운로드 성공");
            downloadCheck = true;
        }).addOnFailureListener(e -> {
            Log.w(TAG, "번역 모델 다운로드 실패");
        });

        buttonBack = findViewById(R.id.buttonBack);
        buttonNext = findViewById(R.id.buttonNext);

        buttonBack.setVisibility(View.GONE);
        buttonNext.setText("Start!");

        buttonRandom = findViewById(R.id.buttonRandom);

        buttonRandom.setVisibility(View.INVISIBLE);
        buttonRandom.setEnabled(false);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem() == 1){
                    pager.setCurrentItem(0);
                }else if(pager.getCurrentItem() == 2){
                    pager.setCurrentItem(1);
                }else if(pager.getCurrentItem() == 3){
                    pager.setCurrentItem(2);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem() == 0){
                    pager.setCurrentItem(1);
                }else if(pager.getCurrentItem() == 1){
                    pager.setCurrentItem(2);
                }else if(pager.getCurrentItem() == 2){
                    pager.setCurrentItem(3);
                }else if(pager.getCurrentItem() == 3){
                    if(downloadCheck) {
                        // 번역
                        koToEnTranslator.translate(textInputed).addOnSuccessListener(s -> {
                            // 번역 성공
                            translatedInput = s;
                            Log.d(TAG, translatedInput);

                            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                            intent.putExtra("login",login);
                            intent.putExtra("id", id);
                            intent.putExtra("text", textInputed);
                            intent.putExtra("style", styleInputed);
                            intent.putExtra("quality", quality);
                            intent.putExtra("translatedInput", translatedInput); // 영어로 번역된 글자
                            startActivityForResult(intent, 101);
                        }).addOnFailureListener(e -> {
                            // 번역 실패
                        });
                    }


                }
            }
        });

        pager = findViewById(R.id.pagerInfo);
        pager.setOffscreenPageLimit(4);


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        StartFragment startFragment = new StartFragment();
        adapter.addItem(startFragment);

        TextFragment textFragment = new TextFragment();
        adapter.addItem(textFragment);

        StyleFragment styleFragment = new StyleFragment();
        adapter.addItem(styleFragment);

        EndFragment endFragment = new EndFragment();
        adapter.addItem(endFragment);

        pager.setAdapter(adapter);

        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ranText = random.nextInt(2);
                int ranStyle = random.nextInt(3);

                switch (ranText){
                    case 0:
                        textFragment.editText.setText("과일 그릇에 담긴 사과 그림");
                        break;
                    case 1:
                        textFragment.editText.setText("반 고흐의 초상화가 그려진 침실 그림");
                        break;
                }

                switch(ranStyle){
                    case 0:
                        styleFragment.selectedStyle.setName("Picasso");
                        break;
                    case 1:
                        styleFragment.selectedStyle.setName("Monet");
                        break;
                    case 2:
                        styleFragment.selectedStyle.setName("Pop Art");
                        break;
                }
                pager.setCurrentItem(3);
            }
        });


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

                if (position == 0){
                    buttonBack.setVisibility(View.GONE);
                    buttonNext.setText("Start!");

                    buttonRandom.setVisibility(View.GONE);
                    buttonRandom.setEnabled(false);

                } else if(position == 3) {

                    buttonNext.setText("Make Image!");

                    buttonRandom.setVisibility(View.INVISIBLE);
                    buttonRandom.setEnabled(false);

                    textInputed = textFragment.editText.getText().toString();
                    styleInputed = styleFragment.selectedStyle.getName();

                    endFragment.textInput.setText(textInputed);
                    endFragment.styleInput.setText(styleInputed);

                    if(textInputed.length() == 0 || styleInputed.length() == 0){
                        buttonNext.setEnabled(false);
                    }
                    if(textInputed.length() == 0){
                        endFragment.textInput.setText("문장을 입력해주세요!");
                    }
                    if(styleInputed.length() == 0){
                        endFragment.styleInput.setText("그림체를 선택해주세요!");
                    }


                }else{

                    buttonRandom.setVisibility(View.VISIBLE);
                    buttonRandom.setEnabled(true);

                    buttonBack.setVisibility(View.VISIBLE);
                    buttonNext.setText("Next");
                    buttonNext.setEnabled(true);
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    class MyPagerAdapter extends FragmentStatePagerAdapter{
        ArrayList<Fragment> items = new ArrayList<Fragment>();
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        public Fragment getItem(int position){
            return items.get(position);
        }

        @Override
        public int getCount(){
            return items.size();
        }
    }
}