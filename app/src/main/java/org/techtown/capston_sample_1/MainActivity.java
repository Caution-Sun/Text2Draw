package org.techtown.capston_sample_1;

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
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;

    Button buttonBack;
    Button buttonNext;

    String textInputed = "";
    String styleInputed = "";

    Boolean login = false;
    String id = "";
    String pwd = "";

    final int PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonBack = findViewById(R.id.buttonBack);
        buttonNext = findViewById(R.id.buttonNext);

        buttonBack.setVisibility(View.GONE);
        buttonNext.setText("Start!");

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
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("login",login);
                    intent.putExtra("id", id);
                    intent.putExtra("text", textInputed);
                    intent.putExtra("style", styleInputed);
                    startActivityForResult(intent, 101);
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


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

                if (position == 0){
                    buttonBack.setVisibility(View.GONE);
                    buttonNext.setText("Start!");
                }else if(position == 3) {
                    buttonNext.setText("Make Image!");

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