package com.example.interviewsort.network;

import com.example.interviewsort.model.CourseListModel;
import com.example.interviewsort.model.PostListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alok on 17/6/19.
 */

public interface ApiInterface {
    @GET("api/?category=all")
    Call<List<CourseListModel>> getCourseListData();

    @GET("api/")
    Call<List<PostListModel>> getPostListData(@Query("category") int id,@Query("post") String post);

}
