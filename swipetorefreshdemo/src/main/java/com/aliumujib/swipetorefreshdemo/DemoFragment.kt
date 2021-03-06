package com.aliumujib.swipetorefreshdemo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aliumujib.swipetorefreshdemo.dummy.DummyContent
import com.aliumujib.swipetorefreshdemo.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_demo_list.*
import android.R.attr.direction
import android.os.Handler
import android.util.Log
import com.aliumujib.swipetorefresh.RefreshDirection


class DemoFragment : Fragment() {

    val TAG = javaClass.simpleName
    val DISMISS_TIMEOUT: Long = 5000


    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_demo_list, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        if (mColumnCount <= 1) {
            swipetorefreshlist.layoutManager = LinearLayoutManager(context)
        } else {
            swipetorefreshlist.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        swipetorefreshlist.adapter = DemoRecyclerViewAdapter(DummyContent.ITEMS, mListener)

        swipetorefreshlayout.setOnRefreshListener {
            direction->
            Log.d(TAG, "Refresh triggered at " + if (direction == RefreshDirection.TOP) "top" else "bottom")
            Handler().postDelayed(Runnable {
                //Hide the refresh after 2sec
                activity.runOnUiThread(Runnable { swipetorefreshlayout.setRefreshing(false) })
            }, DISMISS_TIMEOUT)
        }


    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): DemoFragment {
            val fragment = DemoFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
