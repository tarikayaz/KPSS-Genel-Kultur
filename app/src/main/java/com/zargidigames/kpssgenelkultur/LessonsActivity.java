package com.zargidigames.kpssgenelkultur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zargidigames.kpssgenelkultur.adapter.LessonAdapter;
import com.zargidigames.kpssgenelkultur.api.ApiConfig;
import com.zargidigames.kpssgenelkultur.api.ApiService;
import com.zargidigames.kpssgenelkultur.config.AppConfig;
import com.zargidigames.kpssgenelkultur.model.Lesson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LessonsActivity extends ActionBarActivity {

    private List<Lesson> lessons = new ArrayList<>();
    private int lessonCount;
    private Lesson selectedLesson;
    private ListView listLesson;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        listLesson = (ListView) findViewById(R.id.listLesson);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLesson);

        getLessonsFromApi(AppConfig.LEVEL_ID);
    }

    private void getLessonsFromApi(final int levelId) {

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConfig.API_BASE_URL).build();
        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.getLessons(levelId, new Callback<List<Lesson>>() {
            @Override
            public void success(List<Lesson> lessons_, Response response) {

                progressBar.setVisibility(View.GONE);

                if (response.getStatus() == 200 && lessons_.size() > 0) {

                    lessons = lessons_;
                    lessonCount = lessons.size();

                    LessonAdapter lessonAdapter = new LessonAdapter(LessonsActivity.this, lessons);
                    listLesson.setAdapter(lessonAdapter);
                    listLesson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedLesson = lessons.get(position);
                            startUnitActivity(selectedLesson);
                        }
                    });

                } else {
                    showLoadingError();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showLoadingError();
            }
        });

    }

    private void startUnitActivity(Lesson selectedLesson) {
        AppConfig.SELECTED_LESSON_ID = selectedLesson.id;
        AppConfig.SELECTED_LESSON_TITLE = selectedLesson.name;
        Intent intent = new Intent(this, UnitsActivity.class);
        startActivity(intent);
    }

    private void showLoadingError() {

        String title = getString(R.string.loading_error_title);
        String message = getString(R.string.loading_error_message);
        String button = getString(R.string.loading_error_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                startMainActivity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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
