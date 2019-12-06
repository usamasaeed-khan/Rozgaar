package com.example.rozgaar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.find
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*


class SetupProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {

    lateinit var dateTextView:TextView
    lateinit var userDomain:String
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_profile)



        val toolBar: Toolbar?=findViewById(R.id.login_toolbar)
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Set Up Profile"


        val bundle = intent.extras
        val userEmail = bundle?.getString("email")
        val userPassword = bundle?.getString("password")
        val userName = bundle?.getString("name")
        val domain = findViewById<EditText>(R.id.qualif)
        dateTextView = findViewById(R.id.dob_text)





        val spinner: Spinner = findViewById(R.id.gender_spinner)
        val genders = ArrayList<String>()
        genders.add("M")
        genders.add("F")
        genders.add("Other")

        val dataAdapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,genders)

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = dataAdapter

        spinner.onItemSelectedListener = this

        val dateButton:Button = findViewById(R.id.date_button)

        dateButton.setOnClickListener {
            val datePicker:DialogFragment = DatePickerFragment()

            datePicker.show(supportFragmentManager,"date picker")
        }










        val save_button= findViewById<Button>(R.id.save_button)
        save_button.setOnClickListener {



            userDomain = domain.text.toString()
            signUpUser(userName!!,spinner.selectedItem.toString()[0],userDomain,dateTextView.text.toString(),"Karachi",userEmail!!,userPassword!!)


        }



    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val c:Calendar = Calendar.getInstance()

        c.set(Calendar.YEAR,year)
        c.set(Calendar.MONTH,month)
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth)

        val currentDateString:String = "$year - $month - $dayOfMonth"



        dateTextView.text = currentDateString

    }





    private fun signUpUser(userName:String,gender:Char,domain: String,DOB:String,Location:String,Email:String,Password:String){


        val user = UserSignUp(userName,gender,domain, DOB,Location, Email, Password)


        RetrofitInstance.instance.SignupUser(

            user

        ).enqueue(object: Callback<Boolean> {

            override fun onFailure(call: Call<Boolean>, t: Throwable) {

                // ErrorInfo = t.localizedMessage

                Toast.makeText(applicationContext,"Something's wrong with the connection! Try Again!", Toast.LENGTH_LONG).show()



            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {


                if(response.body()!!) {

                    Toast.makeText(applicationContext,"Signed Up Successfully!",Toast.LENGTH_LONG).show()
                    val intent:Intent = Intent(this@SetupProfileActivity,JobsFeedActivity::class.java)
                    intent.putExtra("userEmail",user.Email)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(applicationContext,"Email already registered or is null! Try another email.",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@SetupProfileActivity,RegisterActivity::class.java))
                    finish()
                }
            }

        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item:String = parent!!.getItemAtPosition(position).toString()
    }
}
