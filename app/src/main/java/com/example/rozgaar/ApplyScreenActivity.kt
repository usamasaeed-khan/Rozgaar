package com.example.rozgaar

import android.R.attr.path
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.activity_apply_screen.*
import retrofit2.Call
import retrofit2.Callback
import java.io.*
import java.util.regex.Pattern


class ApplyScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_screen)
        val finishBtn = findViewById<Button>(R.id.finish_btn)



        val toolbar: Toolbar = findViewById(R.id.logout_appbar)
        setSupportActionBar(toolbar)
        title = "Job Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val intent: Intent = intent
        val jobDesc:PostedJobs = intent.extras["jobDesc"] as PostedJobs

        val imageBytes = Base64.decode(jobDesc.EmployerImage,Base64.DEFAULT)
        val bmp: Bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)


        company_image_apply_screen.setImageBitmap(bmp)
        company_deadline_apply_screen.text = "DEADLINE: ${jobDesc.Deadline.substring(0,10)}"
        company_description_apply_screen.text = jobDesc.Description

        company_name_apply_screen.text = "COMPANY: ${jobDesc.EmployerName}"

        company_noOfPositions_apply_screen.text = "POSITIONS: ${jobDesc.NoOfPositions}"

        company_location.text = "LOCATION: ${jobDesc.Location}"



        finishBtn.setOnClickListener {











            finishBtn.text = "APPLICATION SENT"
            finishBtn.isEnabled = false
            val applyJob = AppliedJobs(SingletonUser.userID,jobDesc.Posted_id)
            RetrofitInstance.instance.ApplyJob(
                applyJob
            ).enqueue(object: Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {

                    Toast.makeText(this@ApplyScreenActivity,"Something's wrong with the connection! Try Again!",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Boolean>, response: retrofit2.Response<Boolean>) {
                    if(response.body()!!){



                        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),10001)
                        //Toast.makeText(this,SingletonUser.userID.toString(),Toast.LENGTH_LONG).show()

                        MaterialFilePicker()
                            .withActivity(this@ApplyScreenActivity)
                            .withRequestCode(1)
                            .withFilter(Pattern.compile(".*\\.*$")) // Filtering files and directories by file name using regexp
                            .withFilterDirectories(true) // Set directories filterable (false by default)
                            .withHiddenFiles(true) // Show hidden files and folders
                            .start()







                        Toast.makeText(this@ApplyScreenActivity,"Applied for Job Successfully At ${jobDesc.EmployerName}!",Toast.LENGTH_LONG).show()
                        //val intent = Intent(this@ApplyScreenActivity,JobsFeedActivity::class.java)
                        //intent.putExtra("userEmail",email)
                        //startActivity(intent)
                        //finish()
                    }
                    else {
                        Toast.makeText(this@ApplyScreenActivity,"You have already applied for the job! Thank You!",Toast.LENGTH_LONG).show()
                    }
                }

            })
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1&& resultCode == Activity.RESULT_OK){
            val filePath = data!!.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)

            //val file = File(filePath)

//            var size = file.length().toInt()
//            if(size>Int.MAX_VALUE)size = Int.MAX_VALUE
//            val bytes = ByteArray(size)
//            try {
//                val buf = BufferedInputStream(FileInputStream(file))
//                buf.read(bytes, 0, bytes.size)
//                buf.close()
//                Toast.makeText(this,bytes.toString(),Toast.LENGTH_LONG).show()
//            } catch (e: FileNotFoundException) { // TODO Auto-generated catch block
//                e.printStackTrace()
//            } catch (e: IOException) { // TODO Auto-generated catch block
//                e.printStackTrace()
//            }
            RetrofitInstance.instance.Upload(
                filePath
            ).enqueue(object: Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {

                    Toast.makeText(this@ApplyScreenActivity,"Something's wrong with the connection! Try Again!",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Boolean>, response: retrofit2.Response<Boolean>) {

                    if(response.body()!!){
                        Toast.makeText(applicationContext,"CV Uploaded Successfully! Welcome!",Toast.LENGTH_LONG).show()

                    }
                    else {
                        Toast.makeText(this@ApplyScreenActivity,"Sorry! The file type is invalid! Try again!",Toast.LENGTH_LONG).show()
                    }
                }

            })








        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            1001->if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permissions Granted!",Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this,"Permissions Not Granted!",Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
