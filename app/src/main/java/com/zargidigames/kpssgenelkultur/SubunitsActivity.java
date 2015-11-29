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

import com.zargidigames.kpssgenelkultur.adapter.SubunitAdapter;
import com.zargidigames.kpssgenelkultur.api.ApiConfig;
import com.zargidigames.kpssgenelkultur.api.ApiService;
import com.zargidigames.kpssgenelkultur.config.AppConfig;
import com.zargidigames.kpssgenelkultur.model.Subunit;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SubunitsActivity extends ActionBarActivity {

    private List<Subunit> subunits = new ArrayList<>();
    private int subunitCount;
    private Subunit selectedSubunit;
    private ListView listSubunit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subunits);

        getSupportActionBar().setTitle(AppConfig.SELECTED_UNIT_TITLE);

        listSubunit = (ListView) findViewById(R.id.listSubunit);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSubunit);

        if (AppConfig.SELECTED_UNIT_ID > 0) {
            getSubunitsFromApi(AppConfig.SELECTED_UNIT_ID);
        } else {
            showLoadingError();
        }

    }

    private void getSubunitsFromApi(final int selectedUnitId_) {

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConfig.API_BASE_URL).build();
        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.getSubunits(selectedUnitId_, new Callback<List<Subunit>>() {
            @Override
            public void success(List<Subunit> subunits_, Response response) {

                progressBar.setVisibility(View.GONE);

                if (response.getStatus() == 200 && subunits_.size() > 0) {

                    subunits = subunits_;
                    subunitCount = subunits.size();

                    SubunitAdapter subunitAdapter = new SubunitAdapter(SubunitsActivity.this, subunits);
                    listSubunit.setAdapter(subunitAdapter);
                    listSubunit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            selectedSubunit = subunits.get(position);
                            startTopicActivity(selectedSubunit);
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

    private void startTopicActivity(Subunit selectedSubunit) {

        AppConfig.SELECTED_SUBUNIT_ID = selectedSubunit.id;
        AppConfig.SELECTED_SUBUNIT_TITLE = selectedSubunit.name;

        Intent intent = new Intent(this, TopicActivity.class);
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
