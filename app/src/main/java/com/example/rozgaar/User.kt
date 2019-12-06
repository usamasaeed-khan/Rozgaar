package com.example.rozgaar

data class UserLogin constructor(var Email:String,var Password:String)

data class UserSignUp constructor(var Name:String, var Gender:Char, var Domain:String, var DOB: String, var Location:String, var Email: String, var Password: String)


data class UserData constructor(var UserID:Int,var Name:String, var Gender:Char, var Domain:String, var DOB: String, var Location:String, var Email: String, var Password: String)
