package com.example.rozgaar

//import android.support.v7.app.AppCompatActivity
import android.app.Activity
import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_jobs_feed.*
import org.jetbrains.anko.indeterminateProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class JobsFeedActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressDialog

    private val CHANNEL_ID = "personal_notifications"





    lateinit var adapter:JobsFeedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_feed)
        displayNotification()
        val intent:Intent = intent
        val userEmail = intent.getStringExtra("userEmail")
        //var userDataId:Int

        //Toast.makeText(this,userEmail,Toast.LENGTH_LONG).show()





        val toolbar: Toolbar = findViewById(R.id.logout_appbar)
        setSupportActionBar(toolbar)
        title = "Jobs Feed"
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val layoutManager = LinearLayoutManager(this)

        recycler_view_jobs_feed.layoutManager = layoutManager
        var jobList: ArrayList<PostedJobs>
        progressBar=indeterminateProgressDialog("Loading.. Please Wait!")
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.show()


        // Load Jobs Feed Data.

        RetrofitInstance.instance.GetJobs()
            .enqueue(object: Callback<ArrayList<PostedJobs>> {
                override fun onFailure(call: Call<ArrayList<PostedJobs>>, t: Throwable) {
                    progressBar.dismiss()
                    Toast.makeText(this@JobsFeedActivity,"Error:"+t.message,Toast.LENGTH_LONG).show()
                }

                override fun onResponse(

                    call: Call<ArrayList<PostedJobs>>,
                    response: Response<ArrayList<PostedJobs>>
                ) {
                    progressBar.dismiss()
                    if(response.isSuccessful){
                        jobList = response.body()!!
                        adapter = JobsFeedAdapter(this@JobsFeedActivity,jobList)
                        recycler_view_jobs_feed.adapter = adapter
                    }
                }

            })




        //  Get User Data.


        RetrofitInstance.instance.GetUserData(userEmail)
            .enqueue(object: Callback<UserData> {
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    progressBar.dismiss()
                    Toast.makeText(this@JobsFeedActivity,"Error:"+t.message,Toast.LENGTH_LONG).show()
                }

                override fun onResponse(

                    call: Call<UserData>,
                    response: Response<UserData>
                ) {
                    progressBar.dismiss()
                    if(response.isSuccessful){
                        SingletonUser.userID=response.body()!!.UserID
                        //Toast.makeText(this@JobsFeedActivity, SingletonUser.userID.toString(),Toast.LENGTH_LONG).show()
                    }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.example_menu,menu)

        menuInflater.inflate(R.menu.options_menu,menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView:SearchView = searchItem.actionView as SearchView


        val searchManager:SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        //searchView.setSearchableInfo(searchManager.)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))





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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        val id = item!!.itemId
        if(id == R.id.go_to_Categories){
            startActivity(Intent(this,JobCategories::class.java))
            return true
        }
        else if(id == R.id.mic_search){
            getSpeechInput()
        }
        else if(id == R.id.logout_option){
            Toast.makeText(this,"Logging Out!",Toast.LENGTH_LONG).show()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }







    private fun getSpeechInput() {
        val intent_speech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent_speech.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent_speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        if (intent_speech.resolveActivity(packageManager) != null) {
            startActivityForResult(intent_speech, 10)
        } else {
            Toast.makeText(this, "Your Device Don't support speech Input", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> if (resultCode == Activity.RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                adapter.filter.filter(result[0].toString())

            }
        }
    }






    //NOTIFICATIONS.


    private fun displayNotification() {
        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_sms_notificatio)
        builder.setContentTitle("Rozgaar")
        builder.setContentText("New Job Update")
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        val notificationManagerCompat =
            NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(1, builder.build())
    }



}
class SingletonUser{
    companion object{
        var userID:Int = 0
    }
}
