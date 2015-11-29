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

import com.zargidigames.kpssgenelkultur.adapter.UnitAdapter;
import com.zargidigames.kpssgenelkultur.api.ApiConfig;
import com.zargidigames.kpssgenelkultur.api.ApiService;
import com.zargidigames.kpssgenelkultur.config.AppConfig;
import com.zargidigames.kpssgenelkultur.model.Unit;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UnitsActivity extends ActionBarActivity {

    private List<Unit> units = new ArrayList<>();
    private int unitCount;
    private Unit selectedUnit;
    private ListView listUnit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);

        getSupportActionBar().setTitle(AppConfig.SELECTED_LESSON_TITLE);

        listUnit = (ListView) findViewById(R.id.listUnit);
        progressBar = (ProgressBar) findViewById(R.id.progressBarUnit);

        if (AppConfig.SELECTED_LESSON_ID > 0) {
            getUnitsFromApi(AppConfig.SELECTED_LESSON_ID);
        } else {
            showLoadingError();
        }

    }

    private void getUnitsFromApi(final int selectedLessonId_) {

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConfig.API_BASE_URL).build();
        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.getUnits(selectedLessonId_, new Callback<List<Unit>>() {
            @Override
            public void success(List<Unit> units_, Response response) {

                progressBar.setVisibility(View.GONE);

                if (response.getStatus() == 200 && units_.size() > 0) {

                    units = units_;
                    unitCount = units.size();

                    UnitAdapter unitAdapter = new UnitAdapter(UnitsActivity.this, units);
                    listUnit.setAdapter(unitAdapter);
                    listUnit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            selectedUnit = units.get(position);
                            startSubunitActivity(selectedUnit);
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

    private void startSubunitActivity(Unit selectedUnit) {

        AppConfig.SELECTED_UNIT_ID = selectedUnit.id;
        AppConfig.SELECTED_UNIT_TITLE = selectedUnit.name;

        Intent intent = new Intent(this, SubunitsActivity.class);
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
