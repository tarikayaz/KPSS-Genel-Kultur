package com.zargidigames.kpssgenelkultur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private TextView textViewTitle;
    private Button buttonStartApp;

    private Animation slideTop;
    private Animation slideBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        buttonStartApp = (Button) findViewById(R.id.buttonStartApp);

        slideTop = AnimationUtils.loadAnimation(this, R.anim.slide_top);
        slideBottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom);

        slideBottom.setStartOffset(500);

        textViewTitle.startAnimation(slideTop);
        buttonStartApp.startAnimation(slideBottom);


        buttonStartApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == buttonStartApp.getId()) {
                    startLevelActivity();
                }
            }
        });

    }

    private void startLevelActivity() {
        Intent intent = new Intent(this, LessonsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
