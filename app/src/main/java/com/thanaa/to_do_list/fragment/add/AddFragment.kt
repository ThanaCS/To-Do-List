package com.thanaa.to_do_list.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.data.models.TodoData
import com.thanaa.to_do_list.data.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*

class AddFragment : Fragment() {
    private val mToDoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)


        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = title.text.toString()
        val mDescription = description.text.toString()
        val mDate = date.text.toString() as Date
        val mIsCompleted = isCompleted.text.toString() as Boolean
        val validation = verifyDataFormUser(mTitle, mDescription)
        if (validation) {
            //not null insert to database
            val newData = TodoData(0, mTitle, mDescription, mDate, mIsCompleted)
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added ", Toast.LENGTH_SHORT).show()
            findNavController().navigate((R.id.action_addFragment_to_listFragment))
        } else {
            Toast.makeText(requireContext(), "Field are empty", Toast.LENGTH_SHORT).show()
        }

    }

    private fun verifyDataFormUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())


    }


}