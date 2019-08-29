package com.example.interviewsort.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.interviewsort.AppConstant;
import com.example.interviewsort.OnItemCLickListener;
import com.example.interviewsort.R;
import com.example.interviewsort.adapter.CourseListAdapter;
import com.example.interviewsort.model.CourseListModel;
import com.example.interviewsort.network.ApiClient;
import com.example.interviewsort.network.ApiInterface;
import com.google.android.gms.ads.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * Created by alok on 17/6/19.
 */

public class MainActivity extends BaseActivity implements OnItemCLickListener {
    private ContentLoadingProgressBar mProgressBar;
    private RecyclerView recyclerView;
    private CourseListAdapter mAdapter;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enitilizeElement();
        addImplimentation();
        apiCall();
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

    private void apiCall() {
        mProgressBar.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CourseListModel>> call = apiService.getCourseListData();
        call.enqueue(new Callback<List<CourseListModel>>() {
            @Override
            public void onResponse(Call<List<CourseListModel>> call, Response<List<CourseListModel>> response) {
                mProgressBar.hide();
                List<CourseListModel> courseListModelsc = response.body();
                mAdapter.setData(courseListModelsc);

            }

            @Override
            public void onFailure(Call<List<CourseListModel>> call, Throwable t) {

            }

        });
    }

    private void enitilizeElement() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);



        ((TextView) findViewById(R.id.txvHeader)).setText(getString(R.string.app_name));
        ((TextView) findViewById(R.id.txvHeader)).setGravity(Gravity.CENTER);

        mProgressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rvCourseList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CourseListAdapter(this);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View v, int pos) {
        if (v.getId() == R.id.cardView) {
            Intent intent = new Intent(this, PostListingActivity.class);
            intent.putExtra(AppConstant.EXTRAS.TITLE, mAdapter.getItemAt(pos).getName());
            intent.putExtra(AppConstant.EXTRAS.id, mAdapter.getItemAt(pos).getId());
            startActivity(intent);
        }

    }
}
