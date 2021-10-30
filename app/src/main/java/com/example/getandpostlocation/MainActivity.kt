package com.example.getandpostlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var nameEd: EditText
    lateinit var locationEd: EditText
    lateinit var nameEd2: EditText
    lateinit var saveBtn: Button
    lateinit var textView: TextView
    lateinit var getLocBtn: Button
    lateinit var showTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEd = findViewById(R.id.editText1)
        locationEd = findViewById(R.id.editText2)
        nameEd2 = findViewById(R.id.editText3)
        textView = findViewById(R.id.textView2)
        showTextView = findViewById(R.id.textView)
        saveBtn = findViewById(R.id.saveBtn)
        getLocBtn = findViewById(R.id.getLocarionBtn)

        //Save the name and location to the server
        saveBtn.setOnClickListener {
            //use the constructor of Users class to get the name and location then save it into f variable
            val f = Users(nameEd.text.toString(), locationEd.text.toString())
            Log.e("", "onCreate: "+ nameEd.text.toString() + locationEd.text.toString())

            addSingleUser(f , onResult = {
                nameEd.setText("")
                locationEd.setText("")
                Toast.makeText(applicationContext, "Save Success!!", Toast.LENGTH_SHORT).show()
            })
        }

        //get the location from the server by enter the name
        getLocBtn.setOnClickListener {
            val f = Users(nameEd.text.toString(), locationEd.text.toString())

            getSingleLocation(f, onResult = {
                showTextView.text = ""
            })
        }
    }

    private fun addSingleUser(f: Users, onResult: () -> Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.addUser(f)?.enqueue(object : Callback<Users>{
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                onResult()
                Log.d("TAG","onResponse"+ response.code()+"\n"+response.errorBody()
                +""+response.message())
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                onResult()
                Toast.makeText(applicationContext, "ERROR!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getSingleLocation(f: Users, onResult: () -> Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.getUser()?.enqueue(object : Callback<Array<Users>> {
            override fun onResponse(call: Call<Array<Users>>, response: Response<Array<Users>>) {
                var nameFound = false
                for(i in response.body()!!){
                    if(i.name == nameEd2.text.toString()){
                        showTextView.text = i.location
                        nameFound = true
                    }
                }
                if(!nameFound){
                    showTextView.text = ""
                    Toast.makeText(applicationContext, "Name dose not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Array<Users>>, t: Throwable) {
                onResult()
                Toast.makeText(applicationContext, "ERROR!", Toast.LENGTH_LONG).show()
            }
        })
    }
}