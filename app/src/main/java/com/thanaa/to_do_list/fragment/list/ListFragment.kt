package com.thanaa.to_do_list.fragment.list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.to_do_list.R
import com.thanaa.to_do_list.SwipeToDelete
import com.thanaa.to_do_list.data.viewmodel.TodoViewModel
import com.thanaa.to_do_list.fragment.SharedViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*


class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    lateinit var datePasser: OnDatePass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        datePasser = context as OnDatePass
    }
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
        swipeToDelete(recyclerView)
        //Animation
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 1000

        }
        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
            data.forEach {
                // for the Notification
                passDate(it.date, it.title)
            }

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
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.newest -> mTodoViewModel.sortByNewDate.observe(this, Observer { adapter.setData(it) })
            R.id.oldest -> mTodoViewModel.sortByOldDate.observe(this, Observer { adapter.setData(it) })
            R.id.alphabetical_order -> mTodoViewModel.sortByTitle.observe(this, Observer { adapter.setData(it) })
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchInDB(query)
            //Hide soft keys
            hideKeyboard()
        }
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchInDB(query)
        }
        return true
    }

    private fun searchInDB(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"
        mTodoViewModel.searchTitle(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
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

    fun hideKeyboard() {
        activity?.let {
            val inputManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = it.currentFocus
            if (view != null) {
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

    }

    private fun swipeToDelete(recycleView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                mTodoViewModel.deleteItem(itemToDelete)

                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(
                    requireContext(), "'${itemToDelete.title}' has been deleted", Toast.LENGTH_SHORT
                ).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recycleView)
    }

    fun passDate(date: Date, title: String) {
        datePasser.onDatePass(date, title)
    }

}
interface OnDatePass {
    fun onDatePass(date: Date, title: String)
}
