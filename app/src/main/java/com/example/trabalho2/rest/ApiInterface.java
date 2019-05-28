package com.example.trabalho2.rest;

import com.example.trabalho2.model.DatumResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("items/list")
    Call<DatumResponse> getItems(@Header("Authorization") String authorization);

    @GET("item/get?id={id}")
    Call<DatumResponse> getItemDetails(@Path("id") int id, @Header("Authorization") String authorization);
}
