package com.assignment.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.R
import com.assignment.databinding.CustomerFragmentBinding
import com.assignment.model.EdittextModel
import com.assignment.model.responsemodel.Data
import com.assignment.view.adapter.CustomerFormAdapter
import com.assignment.view.listener.FormEditistener
import com.assignment.viewmodel.CustomerViewModel


class CustomerFragment : Fragment(), FormEditistener {
    private lateinit var binding: CustomerFragmentBinding
    private lateinit var adapter: CustomerFormAdapter
    private lateinit var list: MutableList<Data>
    private lateinit var viewModel: CustomerViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.customer_fragment,
            container,
            false
        )
        init()
        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        viewModel.callJsonApi(activity!!).observe(this as LifecycleOwner, Observer {
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })
        binding.viewModel = viewModel
        return binding.root
    }

    private fun init() {
        val recyclerView = binding.recyclerview
        list = ArrayList<Data>()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CustomerFormAdapter(list, this)
        recyclerView.adapter = adapter
    }

    override fun formEdited(hashMap: HashMap<String, EdittextModel>) {
        Log.e("form", hashMap.toString())
        viewModel.hashMapObservableField.set(hashMap)
    }

}