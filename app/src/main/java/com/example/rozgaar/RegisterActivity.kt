package com.example.rozgaar

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.indeterminateProgressDialog
import retrofit2.Call
import retrofit2.Callback
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.net.ssl.*


//import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue
    val TAG = "MyTag"
    var abc=""

    private lateinit var progressBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerName: EditText = findViewById(R.id.registerName)
        val registerEmail: EditText = findViewById(R.id.registerEmail)
        val registerPass: EditText = findViewById(R.id.registerPass)
        val registerBtn: Button = findViewById(R.id.signupBtn)
        val confimPass :EditText = findViewById(R.id.confirmPass)

        val toolBar: Toolbar? = findViewById(R.id.register_toolbar)
        setSupportActionBar(toolBar)
        supportActionBar!!.title = "Create Account"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        registerBtn.setOnClickListener {

            val userName: String = registerName.text.toString()
            val userEmail: String = registerEmail.text.toString()
            val userPass: String = registerPass.text.toString()
            val confirmedPass:String = confirmPass.text.toString()

            if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty() || confirmedPass.isEmpty()) Toast.makeText(
                this, "Please fill all of the required fields first.",
                Toast.LENGTH_SHORT
            ).show()
            else if ( userPass != confirmedPass)Toast.makeText(
                this, "Passwords doesn't match. Try again.",
                Toast.LENGTH_SHORT
            ).show()

            else if (!isEmailValid(userEmail))Toast.makeText(
                this, "Email is not valid. Try again",
                Toast.LENGTH_SHORT
            ).show()
            else if(userPass.length < 8)Toast.makeText(
                this, "Password must have at least 8 characters.",
                Toast.LENGTH_SHORT
            ).show()
            else {
                progressBar = indeterminateProgressDialog("Registering User... Please Set Up Your Profile!")
                progressBar.show()
                progressBar.setCanceledOnTouchOutside(false)
                progressBar.show()
                registerUser(userEmail, userPass,userName)
            }
        }
    }
    private fun registerUser(userEmail: String, userPass: String,userName:String) {

        /* Retrofit. */

        //val url = "http://10.0.2.2:8091/TestCall"

//        val user = User(userEmail,userPass)
//        RetrofitInstance.instance.addUser(
//            user
//        ).enqueue(object: Callback<Boolean> {
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Toast.makeText(applicationContext,t.localizedMessage,Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<Boolean>, response: retrofit2.Response<Boolean>) {
//                Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_LONG).show()
//            }
//
//        })


        /* Volley. */

//        val url = "http://10.0.2.2:8091/TestCall"
//
//        queue = Volley.newRequestQueue(this)
//
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                // Display the first 500 characters of the response string.
//                //textView.text = "Response is: ${response.substring(0, 500)}"
//                Toast.makeText(this,response,Toast.LENGTH_LONG).show()
//            },
//            Response.ErrorListener {
//                //Log.e("ERROR",it.message)
//                Toast.makeText(this,"Error: "+it.message,Toast.LENGTH_LONG).show() })
//
//
//        stringRequest.tag=TAG
//
//        queue.add(stringRequest)


        progressBar.dismiss()
        //Toast.makeText(this,"Signed Up Successfully", Toast.LENGTH_SHORT).show()
        val i= Intent(this,SetupProfileActivity::class.java)
        i.putExtra("email",userEmail)
        i.putExtra("password",userPass)
        i.putExtra("name",userName)
        startActivity(i)
        finish()
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }


}
