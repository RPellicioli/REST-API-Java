package com.example.trabalho2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import com.example.trabalho2.R;
import com.example.trabalho2.adapter.ItemsAdapter;
import com.example.trabalho2.model.Datum;
import com.example.trabalho2.model.DatumResponse;
import com.example.trabalho2.rest.ApiClient;
import com.example.trabalho2.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String AUTH = "4da03dfaaad739b5538684c194a128ee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AUTH.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your AUTH from fortnite first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.items_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DatumResponse> call = apiService.getItems(AUTH);
        call.enqueue(new Callback<DatumResponse>() {
            @Override
            public void onResponse(Call<DatumResponse> call, Response<DatumResponse> response) {
                int statusCode = response.code();
                List<Datum> datum = response.body().getData();
                recyclerView.setAdapter(new ItemsAdapter(datum, R.layout.list_items, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<DatumResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
