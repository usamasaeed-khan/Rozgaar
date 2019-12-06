package com.example.rozgaar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import retrofit2.Call
import retrofit2.Callback
import java.util.regex.Matcher
import java.util.regex.Pattern

class ForgotPassworActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passwor)
        val toolBar: Toolbar?=findViewById(R.id.login_toolbar)
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Forgot Password"





        var emailToVerify:String = ""

        val verifyEmailBtn = findViewById<Button>(R.id.verify_email_btn)
        val sendEmailBtn= findViewById<Button>(R.id.send_email_btn)

        sendEmailBtn.isEnabled = false

        val emailVerify:EditText = findViewById(R.id.forgot_password_email)

        //Toast.makeText(this,emailToVerify,Toast.LENGTH_LONG).show()


        verifyEmailBtn.setOnClickListener {
            emailToVerify = emailVerify.text.toString()

            if (!isEmailValid(emailToVerify)) {
                Toast.makeText(this, "Email is not valid. Try again.", Toast.LENGTH_LONG).show()
            } else {
                RetrofitInstance.instance.CheckUserExistence(
                    emailToVerify
                ).enqueue(object : Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {

                        Toast.makeText(
                            this@ForgotPassworActivity,
                            "Something's wrong with the connection! Try Again!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<Boolean>,
                        response: retrofit2.Response<Boolean>
                    ) {
                        if (response.body()!!) {
                            sendEmailBtn.visibility = View.VISIBLE
                            sendEmailBtn.isEnabled = true
                            verifyEmailBtn.visibility = View.GONE
                            verifyEmailBtn.isEnabled = false


                            Toast.makeText(
                                applicationContext,
                                "Your account has been verified! You can now tap the send email button to get your password via email.",
                                Toast.LENGTH_LONG
                            ).show()


                        } else {
                            Toast.makeText(
                                this@ForgotPassworActivity,
                                "Email does not exist in the database! Try again!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                })
            }
        }


            sendEmailBtn.setOnClickListener {
                RetrofitInstance.instance.UpdatePassword(
                    emailToVerify
                ).enqueue(object: Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(this@ForgotPassworActivity,"Something's wrong with the connection! Try Again!",
                            Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Boolean>, response: retrofit2.Response<Boolean>) {
                        if(response.body()!!){

                            startActivity(Intent(this@ForgotPassworActivity,LoginActivity::class.java))
                            finish()
                            Toast.makeText(applicationContext,"Email is being sent to $emailToVerify with the new password!", Toast.LENGTH_LONG).show()


                        }
                        else {
                            Toast.makeText(this@ForgotPassworActivity,"Error sending Email! Try again!",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }

    }
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }


}
