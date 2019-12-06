package com.example.rozgaar

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.indeterminateProgressDialog
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : androidx.appcompat.app.AppCompatActivity() {
    lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotPassword = findViewById<TextView>(R.id.go_to_forgot_password)


        forgotPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPassworActivity::class.java))

        }

        val SendTOSignUp:TextView = findViewById(R.id.go_to_sign_up)
        SendTOSignUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }









        val toolBar: Toolbar?=findViewById(R.id.login_toolbar)
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Sign In"

        val loginEmail: EditText =findViewById(R.id.loginEmail)
        val loginPass: EditText =findViewById(R.id.loginPass)
        val loginBtn: Button =findViewById(R.id.signinBtn)

        loginBtn.setOnClickListener {
            val email:String=loginEmail.text.toString()
            val password:String=loginPass.text.toString()

            if(email.isEmpty() || password.isEmpty()) Toast.makeText(this,"Please fill all of the required fields first.",
                Toast.LENGTH_SHORT).show()
            if(!isEmailValid(email))Toast.makeText(this,"Email is not valid! Try Again.",
                Toast.LENGTH_SHORT).show()
            else {
                progressBar=indeterminateProgressDialog("Signing In.. Please Wait!")
                progressBar.setCanceledOnTouchOutside(false)
                progressBar.show()
                logInUser(email,password)
            }
        }
    }

    private fun logInUser(email: String, password: String) {


        val user = UserLogin(email,password)
        RetrofitInstance.instance.LoginUser(
            user
        ).enqueue(object: Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                progressBar.dismiss()
               Toast.makeText(this@LoginActivity,"Something's wrong with the connection! Try Again!",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Boolean>, response: retrofit2.Response<Boolean>) {

                if(response.body()!!){
                    progressBar.dismiss()
                    val intent:Intent = Intent(this@LoginActivity,JobsFeedActivity::class.java)
                    intent.putExtra("userEmail",email)
                    startActivity(intent)
                    finish()
                    Toast.makeText(applicationContext,"Sign In Successful! Welcome!",Toast.LENGTH_LONG).show()

                }
                else {
                    RetrofitInstance.instance.CheckUserExistence(
                        user.Email
                    ).enqueue(object: Callback<Boolean>{
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            progressBar.dismiss()
                            Toast.makeText(this@LoginActivity,"Something's wrong with the connection! Try Again!",Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            progressBar.dismiss()
                            if(response.body()!!){
                                Toast.makeText(this@LoginActivity,"Wrong Password! Tap Forgot Password in case you don't remember it.",Toast.LENGTH_LONG).show()

                            }
                            else {

                                Toast.makeText(this@LoginActivity,"Sorry we couldn't recognize you. Try Signing Up!",Toast.LENGTH_LONG).show()
                            }
                        }

                    })

                }
            }

        })


    }
    private fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}
