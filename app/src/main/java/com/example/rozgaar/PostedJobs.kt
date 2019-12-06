package com.example.rozgaar


import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class PostedJobs constructor(var Posted_id:Int,var Description:String, var Deadline:String,var MaxCVs:Int, var NoOfPositions:Int,var EmployeeIDFK:Int,var CategoriesID_FK:Int,var Location:String,var PostDate:String,var EmployerImage:String,var EmployerName:String) :
    Serializable