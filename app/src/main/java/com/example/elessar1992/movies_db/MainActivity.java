package com.example.elessar1992.movies_db;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elessar1992.movies_db.Model.Movie;
import com.example.elessar1992.movies_db.Model.MoviesResponse;
import com.example.elessar1992.movies_db.api.Client;
import com.example.elessar1992.movies_db.api.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private TextView search;
    private Button find;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    private AppCompatActivity activity = MainActivity.this;
    public static final String Tag = MoviesAdapter.class.getName();
    private final static String API_KEY = "5bf838270fac55799c2a21e65505ae5b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        search = (TextView) findViewById(R.id.search);
        find = (Button) findViewById(R.id.find);

        if (API_KEY.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please obtain API Key", Toast.LENGTH_LONG).show();
            return;
        }

        find.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String value = search.getText().toString().trim();
                if(value.isEmpty())
                {
                    search.setError("its empty");
                }
                else
                {
                    build_retrofit_and_get_response(value);
                }
            }
        });

    }


        private void build_retrofit_and_get_response(String query)
        {
        //it should be here, otherwise, not working
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Service service = Client.getClient().create(Service.class);
        Call<MoviesResponse> call = service.searchMovies(API_KEY, query);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                Log.d(Tag, "my responce is .. " + response.body().getResults().get(0).getBackdropPath());
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(Tag, t.toString());
            }
        });
    }
}
