package com.app.thispersondoesnotexist;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.thispersondoesnotexist.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        generateImg();

        binding.genBtn.setOnClickListener(view1 -> {
            generateImg();
        });

    }

    private void generateImg() {
        binding.genBtn.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "https://100k-faces.glitch.me/random-image-url";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, response -> {
            try {
                String imgUrl = response.getString("url");
                Glide.with(getApplicationContext())
                        .load(imgUrl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.genBtn.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.faceImg);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e("TAG", "onErrorResponse: " + error.getMessage());
            if (error.getMessage() != null)
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        queue.add(jsonObjectRequest);
    }
}