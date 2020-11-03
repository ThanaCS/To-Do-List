package com.thanaa.to_do_list.fragment.list

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.data.models.TodoData
import kotlinx.android.synthetic.main.row_layout.view.*
import java.util.*


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<TodoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title_tv.text = dataList[position].title
        holder.itemView.description_tv.text = dataList[position].description
        val mDate = DateFormat.format("EEE, MMM, dd", dataList[position].date).toString()
        holder.itemView.date_tv.text = mDate
        //check if the user completed the task or not

//      val isCompleted  = dataList[position].isCompleted
//        if(isCompleted){
//            holder.itemView.isCompleted.visibility =  View.VISIBLE
//        }else{
//            holder.itemView.isCompleted.visibility =  View.INVISIBLE
//        }
        //random colors for the card view

        var rand = Random().nextInt(8)
        when (rand) {
            0 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c0))
            1 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c1))
            2 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c2))
            3 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c3))
            4 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c4))
            5 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c5))
            6 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c6))
            7 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c7))
            8 -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.c8))
        }

    }

    //notify the recycler view about these changes
    fun setData(todoDate: List<TodoData>) {
        this.dataList = todoDate
        notifyDataSetChanged()
    }


}