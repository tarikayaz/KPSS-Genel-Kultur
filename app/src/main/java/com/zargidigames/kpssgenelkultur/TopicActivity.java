package com.zargidigames.kpssgenelkultur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zargidigames.kpssgenelkultur.api.ApiConfig;
import com.zargidigames.kpssgenelkultur.api.ApiService;
import com.zargidigames.kpssgenelkultur.config.AppConfig;
import com.zargidigames.kpssgenelkultur.model.Topic;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopicActivity extends ActionBarActivity {

    private WebView webTopic;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        getSupportActionBar().setTitle(AppConfig.SELECTED_SUBUNIT_TITLE);

        webTopic = (WebView) findViewById(R.id.webViewTopic);
        progressBar = (ProgressBar) findViewById(R.id.progressBarTopic);

        WebSettings settings = webTopic.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");

        getTopicFromApi(AppConfig.SELECTED_SUBUNIT_ID);

    }

    private void getTopicFromApi(final int topicId) {

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConfig.API_BASE_URL).build();
        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.getTopic(topicId, new Callback<Topic>() {
            @Override
            public void success(Topic topic, Response response) {

                progressBar.setVisibility(View.GONE);
                webTopic.loadData(topic.getHtmlContent(), "text/html; charset=UTF-8", "UTF-8");

            }

            @Override
            public void failure(RetrofitError error) {
                showLoadingError();
            }
        });

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
