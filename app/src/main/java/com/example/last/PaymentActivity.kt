package com.example.last

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.nio.charset.Charset

val list = ArrayList<String>()    //create list for payment database, it shows amount and return result

open class PaymentActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = intent.extras       //get our data with specified keys
        var value = -1
        var qr = ""

        if (b != null) {                            //if extras is not null
            value = b.getInt("amount")          //get amount with key "amount"
            qr = b.getString("qr").toString()   //get qr data with key "qr" and convert to String
        }

        val queue = Volley.newRequestQueue(this)    //create new request queue and set payment api url
        val url = "https://sandbox-api.payosy.com/api/payment"

        val jsonString = """
                {
                "returnCode":1000,
                "returnDesc":"success",
                "receiptMsgCustomer":"beko Campaign/n2018",
                "receiptMsgMerchant":"beko Campaign Merchant/n2018",
                "paymentInfoList":[
                    {
                        "paymentProcessorID":67,
                        "paymentActionList":[
                            {
                                "paymentType":3,
                                "amount":${value},
                                "currencyID":949,
                                "vatRate":800
                            }
                        ]
                    }
                ],
                "QRdata":"$qr"
          }'
            """.trimIndent()        //create our json string and use trimIndent function for delete
                                                                    //first line and last line (""")

        val body = JSONObject(jsonString)         //put these json to json object and create body
        var apiResponse: PaymentApiResponse?      //create PaymentApiResponse object for get response from API

        val request = object: StringRequest(    //create new request as StringRequest
            Method.POST,                        //set the method to POST
            url,                                //set url to our api url
            { response ->
                // parse our response
                apiResponse = PaymentApiResponse(
                    JSONObject(response).getString("applicationID"),
                    JSONObject(response).getString("posID"),
                    JSONObject(response).getInt("returnCode"),
                    JSONObject(response).getString("returnDesc"),
                    JSONObject(response).getString("sessionID")
                )
                //add value and return result to list for payment database
                list.add("Amount: $value, Return: ${apiResponse!!.returnDesc}")
            },
            { error ->
                println("Error is: $error")         //if error occurred, print
            }
        )

        {
            override fun getHeaders(): MutableMap<String, String> {     //add headers
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["accept"] = "application/json"
                headers["x-ibm-client-id"] = "d56a0277-2ee3-4ae5-97c8-467abeda984d"
                headers["x-ibm-client-secret"] = "U1wY2tV5dU2rO7iF7qI7wI4sH8pF0vO8oQ2fE1iS5tU4vW5kO1"
                return headers
            }

            override fun getBody(): ByteArray {         //convert body to byte array
                return body.toString().toByteArray(Charset.defaultCharset())
            }
        }

        queue.add(request)      //add our request to queue
        finish()                //finish the payment activity and return main activity file
    }

}