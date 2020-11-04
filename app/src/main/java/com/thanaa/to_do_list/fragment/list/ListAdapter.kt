package com.thanaa.to_do_list.fragment.list


import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.data.models.TodoData
import kotlinx.android.synthetic.main.row_layout.view.*
import java.util.*


const val TAG = "ListAdapter"
class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<TodoData>()
    private lateinit var completedImage: ImageView
    private lateinit var pastDueIndicator: ImageView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        completedImage = view.findViewById(R.id.Checkcompleted) as ImageView
        pastDueIndicator = view.findViewById(R.id.due_past) as ImageView
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title_tv.text = dataList[position].title
        holder.itemView.description_tv.text = dataList[position].description

        //when user clicks on item it navigate to update of the data postion
        holder.itemView.row_background.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }
        val mDate = DateFormat.format("EEE, MMM, dd", dataList[position].date).toString()
        holder.itemView.date_tv.text = mDate
        //check if the user completed the task or not

        val taskCompleted = dataList[position].isCompleted
        if (taskCompleted) {
            completedImage.visibility = View.VISIBLE
        } else {
            completedImage.visibility = View.INVISIBLE
        }

        //check if the task past due
        val taskDate = dataList[position].date
        var prevDay = System.currentTimeMillis() - 1000 * 60 * 60 * 24
        val prev = Date(prevDay)
        if (taskDate.before(prev) && taskCompleted == false) {
            pastDueIndicator.visibility = View.VISIBLE

        } else if (DateUtils.isToday(taskDate.time) && taskCompleted == false) {


            pastDueIndicator.visibility = View.INVISIBLE
        } else {
//            pastDueIndicator.visibility = View.INVISIBLE
        }

        when (dataList[position].id % 2 == 0) {
            true -> holder.itemView.RandomCardView.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.purple_500))
            false -> holder.itemView.RandomCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.bright_pink))
        }


    }

    //notify the recycler view about these changes
    fun setData(todoDate: List<TodoData>) {
        this.dataList = todoDate
        notifyDataSetChanged()
    }


}