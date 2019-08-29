package com.example.interviewsort.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.interviewsort.AppConstant;
import com.example.interviewsort.OnItemCLickListener;
import com.example.interviewsort.R;
import com.example.interviewsort.adapter.PostListAdapter;
import com.example.interviewsort.model.PostListModel;
import com.example.interviewsort.network.ApiClient;
import com.example.interviewsort.network.ApiInterface;
import com.google.android.gms.ads.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class PostListingActivity extends BaseActivity implements OnItemCLickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ContentLoadingProgressBar mProgressBar;
    private RecyclerView recyclerView;
    private PostListAdapter postListAdapter;
    private int id;
    private String title;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_listing);
        getIntentData(getIntent());
        enitilizeElement();
        addImplimentation();
        apiCall();
        setUpToolbar(title);
    }

    private void addImplimentation() {
        final NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView1);
        adView.setVisibility(View.GONE);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(String.valueOf(R.string.appid))
                .build();
        adView.loadAd(request);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        mInterstitialAd = new InterstitialAd(this);

// set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));


// Load ads into Interstitial Ads

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getIntentData(Intent intent) {
        if (intent != null) {
            id = intent.getIntExtra(AppConstant.EXTRAS.id,-1);
            title = intent.getStringExtra(AppConstant.EXTRAS.TITLE);
        }
    }

    private void enitilizeElement() {
        mProgressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rvPostList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        postListAdapter = new PostListAdapter(this);
        postListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(postListAdapter);
    }

    private void apiCall() {
        mProgressBar.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PostListModel>> call = apiService.getPostListData(id, "list");
        call.enqueue(new Callback<List<PostListModel>>() {
            @Override
            public void onResponse(Call<List<PostListModel>> call, Response<List<PostListModel>> response) {
                mProgressBar.hide();
                List<PostListModel> courseListModelsc = response.body();
                postListAdapter.setData(courseListModelsc);
            }

            @Override
            public void onFailure(Call<List<PostListModel>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }

        });
    }

    @Override
    public void onItemClick(View v, int pos) {
        if (v.getId() == R.id.cardView) {
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra(AppConstant.EXTRAS.TITLE, postListAdapter.getItemAt(pos).getTitle());
            intent.putExtra(AppConstant.EXTRAS.WEB_URL, postListAdapter.getItemAt(pos).getUrl());
            startActivity(intent);

        }

    }
}
