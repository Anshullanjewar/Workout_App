package com.example.a10minworkout.Activity

import android.net.Uri.Builder
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a10minworkout.databinding.ActivityTrainerBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol.Companion.get
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class TrainerActivity : AppCompatActivity() {
    private var binding: ActivityTrainerBinding?=null
    lateinit var chatRV : RecyclerView
    lateinit var chatList : ArrayList<Message>
    lateinit var quesTxt : String
    lateinit var msgAdapter : MessageAdapter

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    var client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=  ActivityTrainerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        chatList = ArrayList()
        chatRV = binding!!.chatRv

        quesTxt = binding?.etText?.text.toString()
        chatRV = binding!!.chatRv
        chatList = ArrayList<Message>()

        msgAdapter = MessageAdapter(chatList)
        chatRV.setAdapter(msgAdapter)
        val llm = LinearLayoutManager(this)
        llm.stackFromEnd = true
        chatRV.setLayoutManager(llm)

        binding?.sendBtn?.setOnClickListener{
//            val question: String = quesTxt
//            addToChat(question,Message.SENT_BY_ME);
//            binding?.etText?.setText("");
            val question: String = binding!!.etText.getText().toString().trim()
            addToChat(question, Message.SENT_BY_ME)
            binding!!.etText.setText("")
            callApi(question)
        }
    }

    fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            chatList.add(Message(message, sentBy))
            msgAdapter.notifyDataSetChanged()
            chatRV.smoothScrollToPosition(msgAdapter.getItemCount())
        }
    }

    fun addResponse(response: String?) {
        chatList.removeAt(chatList.size - 1)
        addToChat(response!!, Message.SENT_BY_BOT)
    }
    fun callApi(question:String){

        chatList.add(Message("Typing... ", Message.SENT_BY_BOT))

        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "text-davinci-003")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val body: RequestBody = RequestBody.create(JSON,jsonBody.toString())
        val request: Request=Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer API")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        addResponse(result.trim { it <= ' ' })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body.toString())
                }
            }
        })

    }



}