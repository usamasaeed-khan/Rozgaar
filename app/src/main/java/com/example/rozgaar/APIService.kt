package com.example.rozgaar

import retrofit2.Call
import retrofit2.http.*

interface APIService {

    //@FormUrlEncoded
    @POST("Login")
    fun LoginUser(
        @Body user:UserLogin
    ):Call<Boolean>


    @POST("Signup")
    fun SignupUser(
        @Body user:UserSignUp
    ):Call<Boolean>

    @GET("GetCategories")
    fun GetCategories():Call<ArrayList<Categories>>

    @GET("GetJobs")
    fun GetJobs():Call<ArrayList<PostedJobs>>

    @GET("GetJobsByCategoryID")
    fun GetJobsByCategory(
        @Query("CategoryID") CategoryID:Int
    ):Call<ArrayList<PostedJobs>>

    @POST("Apply")
    fun ApplyJob(
        @Body applyJob:AppliedJobs
    ):Call<Boolean>


    @GET("CheckIfUserExists")
    fun CheckUserExistence(
        @Query("email") email:String
    ):Call<Boolean>


    @GET("ChangePass")
    fun UpdatePassword(
        @Query("email") email:String
    ):Call<Boolean>


    @GET("GetUser")
    fun GetUserData(
        @Query("email") email:String
    ):Call<UserData>


    @POST("UploadCV")
    fun Upload(
        @Body file:String
    ):Call<Boolean>


}