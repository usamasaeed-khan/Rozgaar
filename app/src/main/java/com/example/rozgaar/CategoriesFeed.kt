package com.example.rozgaar

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_job_categories.*
import kotlinx.android.synthetic.main.activity_jobs_feed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFeed : AppCompatActivity() {

    lateinit var adapter:JobsFeedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_feed)

        val toolbar: Toolbar = findViewById(R.id.logout_appbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)


        val intent: Intent = intent
        val categoryID:Int = intent.extras["cat_id"] as Int
        val categoryName:String = intent.extras["cat_name"] as String
        title = "$categoryName Jobs"

        recycler_view_jobs_feed.layoutManager = layoutManager
        var jobList: ArrayList<PostedJobs>
        RetrofitInstance.instance.GetJobsByCategory(categoryID)
            .enqueue(object: Callback<ArrayList<PostedJobs>> {
                override fun onFailure(call: Call<ArrayList<PostedJobs>>, t: Throwable) {
                    Toast.makeText(this@CategoriesFeed,"Error:"+t.message,Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ArrayList<PostedJobs>>,
                    response: Response<ArrayList<PostedJobs>>
                ) {
                    //Toast.makeText(this@CategoriesFeed,response.message(),Toast.LENGTH_LONG).show()
                    if(response.isSuccessful){
                        jobList = response.body()!!
                        if(jobList.size == 0) Toast.makeText(this@CategoriesFeed,"Currently no jobs are available in Category $categoryName!",Toast.LENGTH_LONG).show()
                        adapter = JobsFeedAdapter(this@CategoriesFeed,jobList)
                        recycler_view_jobs_feed.adapter = adapter
                    }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.example_menu,menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView:SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        return true
    }
}
