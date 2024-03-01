package com.example.quickidenti

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment

interface Listener{
    fun itemClicked(id: Int)
}
class PeopleListFragment : ListFragment() {
    lateinit var listener: Listener
    val data: Array<String> = arrayOf("kotlin", "jave", "C++")
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        this.listener = context.to(Listener)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val adapter: ListAdapter = ArrayAdapter<Any?>(
//            activity,
//            R.layout.simple_list_item_1, data
//        )
//        listAdapter = adapter
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//       val adapter: ListAdapter = ArrayAdapter(
//           context=activity, android.R.layout.simple_list_item_1, data
//       )
//        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }
//    private var columnCount = 1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_people_list, container, false)
//
//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
//            }
//        }
//        return view
//    }
//
    companion object {
    @JvmStatic
    fun newInstance() = PeopleListFragment()
        }

}