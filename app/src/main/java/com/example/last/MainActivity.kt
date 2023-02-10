package com.example.last

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.last.SSL.ssl
import org.json.JSONObject
import java.nio.charset.Charset


open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)                  //set layout to activity_main.xml

        val amountInput = findViewById<EditText>(R.id.numberinput)  //take layout items' id for edit and
        val amountValue = findViewById<TextView>(R.id.textView4)    //show them
        val responseList = findViewById<TextView>(R.id.textView5)
        val sendButton = findViewById<Button>(R.id.button)

        sendButton.setOnClickListener{                   //onclick function for Send Button
            val value = amountInput.text.toString()             //take our amount by amountInput field
            amountValue.text = value                            //and show them in layout with .text property
            ssl()                                               //disable ssl server verification

            val queue = Volley.newRequestQueue(this)    //open a new Volley request queue for post requests
            val url = "https://sandbox-api.payosy.com/api/get_qr_sale" //set the url for request
            val data = JSONObject()                                 //open a json object for transfer data with json
            data.put("totalReceiptAmount", value.toInt())     //put amount in these json object
            val body = data.toString()                              //put these data to body as String

            var apiResponse: ApiResponse?            //create ApiResponse object for get response from API

            val request = object: StringRequest(     //create new request as StringRequest
                Method.POST,                        //set the method to POST
                url,                                //set url to our api url
                { response ->
                    // parse our response
                    apiResponse = ApiResponse(
                        QRdata = JSONObject(response).getString("QRdata"),
                        returnCode = JSONObject(response).getInt("returnCode"),
                        returnDesc = JSONObject(response).getString("returnDesc")
                    )

                    //get qr data to the screen
                    val qr = findViewById<TextView>(R.id.textView)
                    qr.text = apiResponse?.QRdata.toString()

                    //open new Intent for change activity to Payment (To achieve Payment API)
                    val intent = Intent(this@MainActivity, PaymentActivity::class.java)
                    val b = Bundle()
                    b.putString("qr", apiResponse?.QRdata.toString())   //put qr data and value to extras
                    b.putInt("amount",value.toInt())
                    intent.putExtras(b)
                    startActivity(intent)
                },
                { error ->
                    println("Error is: $error")         //if error occurred, print
                }
            )

            {
                override fun getHeaders(): MutableMap<String, String> {    //Add headers with getHeaders func
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["accept"] = "application/json"
                    headers["x-ibm-client-id"] = "d56a0277-2ee3-4ae5-97c8-467abeda984d"
                    headers["x-ibm-client-secret"] = "U1wY2tV5dU2rO7iF7qI7wI4sH8pF0vO8oQ2fE1iS5tU4vW5kO1"
                    return headers
                }

                override fun getBody(): ByteArray {     //convert body to bytearray and return
                    return body.toByteArray(Charset.defaultCharset())
                }
            }
            queue.add(request)      //add our request to queue
        }

        val mobileButton = findViewById<Button>(R.id.mobile)    //Get button's id for interface change
        mobileButton.setOnClickListener{
            if (amountValue.visibility == View.INVISIBLE){      //These code block for mobile interface
                amountValue.visibility = View.VISIBLE
                responseList.text = list.toString()
                responseList.visibility = View.VISIBLE
                amountInput.visibility = View.INVISIBLE
                sendButton.visibility = View.INVISIBLE
                mobileButton.text = "POS"
            }
            else {                                           //These code block for pos interface
                amountValue.visibility = View.INVISIBLE
                responseList.visibility = View.INVISIBLE
                amountInput.visibility = View.VISIBLE
                sendButton.visibility = View.VISIBLE
                mobileButton.text = "Mobile"
            }
        }
    }
}