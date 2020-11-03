package com.thanaa.to_do_list.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.data.viewmodel.TodoViewModel
import com.thanaa.to_do_list.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val mTodoViewModel: TodoViewModel by viewModels()
    private lateinit var emptyView: ImageView
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        emptyView = view.findViewById(R.id.empty_view) as ImageView
        recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //check if the database is empty to show empty view
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyView(it)
        })
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        //Menu of Deletion and Sorting
        setHasOptionsMenu(true)
        return view
    }

    private fun showEmptyView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.empty_view?.visibility = View.VISIBLE
            view?.emptyListText?.visibility = View.VISIBLE
        } else {
            view?.empty_view?.visibility = View.INVISIBLE
            view?.emptyListText?.visibility = View.INVISIBLE

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all)
            confirmRemoval()
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Deleted All Tasks", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All Tasks")
        builder.setMessage("Are you sure you want to delete all tasks?")
        builder.create().show()
    }


}