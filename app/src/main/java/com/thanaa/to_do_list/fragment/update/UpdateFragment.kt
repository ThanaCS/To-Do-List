package com.thanaa.to_do_list.fragment.update

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.data.models.TodoData
import com.thanaa.to_do_list.data.viewmodel.TodoViewModel
import com.thanaa.to_do_list.fragment.DatePickerFragment
import com.thanaa.to_do_list.fragment.SharedViewModel
import com.thanaa.to_do_list.fragment.add.DIALOG_DATE
import com.thanaa.to_do_list.fragment.add.REQUEST_DATE
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.util.*


class UpdateFragment : Fragment(), DatePickerFragment.Callbacks {
    private val args by navArgs<UpdateFragmentArgs>()
    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var isComplete = false
    private lateinit var completedCheckBox: CheckBox
    private lateinit var dateButton: Button
    var date = Date()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        dateButton = view.findViewById(R.id.current_date) as Button
        completedCheckBox = view.findViewById(R.id.current_isCompleted) as CheckBox

        completedCheckBox.setOnClickListener {
            args.currentItem.isCompleted = !args.currentItem.isCompleted
        }
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(args.currentItem.date).apply {
                setTargetFragment(this@UpdateFragment, REQUEST_DATE)
                show(this@UpdateFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }
        //Set menu
        setHasOptionsMenu(true)
        view.current_title.setText(args.currentItem.title)
        view.current_description.setText(args.currentItem.description)
        view.current_date.text = args.currentItem.date.toString()
        view.current_isCompleted.isChecked = args.currentItem.isCompleted

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save)
            updateItem()
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_title.text.toString()
        val description = current_description.text.toString()


        val validation = mSharedViewModel.verifyDataFormUser(title, description)
        if (validation) {
            val updatedItem = TodoData(
                args.currentItem.id,
                title,
                description,
                args.currentItem.date,
                args.currentItem.isCompleted
            )
            mTodoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Pleas fill out the empty fields ", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDateSelected(date: Date) {
        args.currentItem.date = date
        dateButton.text = args.currentItem.date.toString()
    }


}