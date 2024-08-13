package com.example.a10minworkout.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a10minworkout.R


class MessageAdapter (private val dataSet: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(){
    class ViewHolder (view : View): RecyclerView.ViewHolder(view) {
        val leftLv : LinearLayout = itemView.findViewById(R.id.leftLv)
        val rightLv : LinearLayout = itemView.findViewById(R.id.rightLv)
        val leftTxt : TextView = itemView.findViewById(R.id.responseTxt)
        val rightTxt : TextView = itemView.findViewById(R.id.queryTxt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gpt_item,parent,false)
        return ViewHolder(itemView)    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: Message = dataSet.get(position)
        if (message.sentBy == Message.SENT_BY_ME) {
            holder.leftLv.setVisibility(View.GONE)
            holder.rightLv.setVisibility(View.VISIBLE)
            holder.rightTxt.setText(message.message)
        } else {
            holder.rightLv.setVisibility(View.GONE)
            holder.leftLv.setVisibility(View.VISIBLE)
            holder.leftTxt.setText(message.message)
        }
    }
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = dataSet[position]
//        if(item.getSentBy().equals(item.SENT_BY_ME)){
//            holder.leftLv.isVisible =false
//            holder.rightLv.isVisible = true
//            holder.rightTxt.setText(item.getMessage())
//        }
//        else{
//            holder.rightLv.isVisible =false
//            holder.leftLv.isVisible = true
//            holder.leftTxt.setText(item.getMessage())
//        }
//}
}