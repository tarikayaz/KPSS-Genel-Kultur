package com.zargidigames.kpssgenelkultur.api;

import com.zargidigames.kpssgenelkultur.model.Lesson;
import com.zargidigames.kpssgenelkultur.model.Subunit;
import com.zargidigames.kpssgenelkultur.model.Topic;
import com.zargidigames.kpssgenelkultur.model.Unit;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface ApiService {

    @Headers({"api_key: " + ApiConfig.API_KEY, "api_client_id: " + ApiConfig.API_CILENT_ID})
    @GET("/getLessons/{level_id}")
    public void getLessons(@Path("level_id") Integer level_id, Callback<List<Lesson>> response);

    @Headers({"api_key: " + ApiConfig.API_KEY, "api_client_id: " + ApiConfig.API_CILENT_ID})
    @GET("/getUnits/{lesson_id}")
    public void getUnits(@Path("lesson_id") Integer lesson_id, Callback<List<Unit>> response);

    @Headers({"api_key: " + ApiConfig.API_KEY, "api_client_id: " + ApiConfig.API_CILENT_ID})
    @GET("/getSubunits/{unit_id}")
    public void getSubunits(@Path("unit_id") Integer unit_id, Callback<List<Subunit>> response);

    @Headers({"api_key: " + ApiConfig.API_KEY, "api_client_id: " + ApiConfig.API_CILENT_ID})
    @GET("/getTopic/{subunit_id}")
    public void getTopic(@Path("subunit_id") Integer subunit_id, Callback<Topic> response);

}
