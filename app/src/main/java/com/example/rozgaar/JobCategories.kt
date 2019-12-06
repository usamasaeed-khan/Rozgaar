package com.example.rozgaar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_job_categories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobCategories : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_categories)

        val toolbar:Toolbar= findViewById(R.id.logout_appbar)
        setSupportActionBar(toolbar)
        title = "Job Categories"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)

        recycler_view_job_categories.layoutManager = layoutManager
        var categoryList: ArrayList<Categories>


        RetrofitInstance.instance.GetCategories()
            .enqueue(object: Callback<ArrayList<Categories>> {
                override fun onFailure(call: Call<ArrayList<Categories>>, t: Throwable) {
                    Toast.makeText(this@JobCategories,t.message,Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ArrayList<Categories>>,
                    response: Response<ArrayList<Categories>>
                ) {
                    if(response.isSuccessful){
                        categoryList = response.body()!!
                        val adapter = CategoriesAdapter(this@JobCategories,categoryList)
                        recycler_view_job_categories.adapter = adapter
                    }
                }

            })
    }
}
