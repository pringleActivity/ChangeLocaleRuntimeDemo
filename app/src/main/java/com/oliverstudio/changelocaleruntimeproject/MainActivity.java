package com.oliverstudio.changelocaleruntimeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

    }

    private void updateView(String lang) {
        LocaleHelper.setLocale(this, lang);
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idClicked = item.getItemId();
        switch (idClicked) {
            case R.id.language_en:
                updateView(LocaleHelper.ENGLISH_LANGUAGE);
                break;
            case R.id.language_vi:
                updateView(LocaleHelper.VIETNAM_LANGUAGE);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isChangeLocale()) {
            recreate();
        }
    }

    private boolean isChangeLocale() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isChangeLang = prefs.getBoolean(LocaleHelper.IS_CHANGE_LANGUAGE, false);

        if(isChangeLang) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(LocaleHelper.IS_CHANGE_LANGUAGE, false);
            editor.apply();
            return true;
        }
        return false;
    }
}


