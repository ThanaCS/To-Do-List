package com.thanaa.to_do_list.fragment.add

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.data.models.TodoData
import com.thanaa.to_do_list.data.viewmodel.TodoViewModel
import com.thanaa.to_do_list.fragment.DatePickerFragment
import com.thanaa.to_do_list.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*

const val TAG = "AddFragment"
const val REQUEST_DATE = 0
const val DIALOG_DATE = "DialogDate"

class AddFragment : Fragment(), DatePickerFragment.Callbacks {

    private val mToDoViewModel: TodoViewModel by viewModels()

    //sharing function between add and update fragment
    private val mSharedViewModel: SharedViewModel by viewModels()
    private lateinit var todo: TodoData
    private lateinit var dateButton: Button
    private lateinit var completedCheckBox: CheckBox
    private var isComplete = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var initDate = Calendar.getInstance().time

        todo = TodoData(0, "", "", initDate, false)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        dateButton = view.findViewById(R.id.dateButton) as Button
        completedCheckBox = view.findViewById(R.id.isCompleted) as CheckBox

        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(todo.date).apply {
                setTargetFragment(this@AddFragment, REQUEST_DATE)
                show(this@AddFragment.requireFragmentManager(), DIALOG_DATE)
            }

        }

        completedCheckBox.setOnClickListener {
            isComplete = !isComplete
        }

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
        val validation = mSharedViewModel.verifyDataFormUser(mTitle, mDescription)
        //not null insert to database
        if (validation) {

            val newData = TodoData(0, mTitle, mDescription, todo.date, isComplete)
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added ", Toast.LENGTH_SHORT).show()
            findNavController().navigate((R.id.action_addFragment_to_listFragment))
        } else {
            Toast.makeText(requireContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDateSelected(date: Date) {
        todo.date = date
        dateButton.text = todo.date.toString()
    }

}
