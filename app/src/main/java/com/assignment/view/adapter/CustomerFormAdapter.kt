package com.assignment.view.adapter

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.databinding.FormRowBinding
import com.assignment.model.EdittextModel
import com.assignment.model.responsemodel.Data
import com.assignment.view.listener.FormEditistener
import com.assignment.viewmodel.CustomerFormViewModel

class CustomerFormAdapter(val list: List<Data>,val formEditistener: FormEditistener): RecyclerView.Adapter<CustomerFormAdapter.FormViewHolder>() {
    lateinit var binding: FormRowBinding
    val hashmap:HashMap<String,EdittextModel> = HashMap()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.form_row, parent, false)
        val videoViewHolder=FormViewHolder(binding)

        return videoViewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val data = list.get(position)
        holder.bind(data)

    }

    inner class FormViewHolder(binding: FormRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            var validateRegex:String?=null
            if (binding.viewModel == null) {
                if (data.validator.size > 0) {
                   for (i in 0 until data.validator.size){
                       if (data.validator.get(i).type.equals("Regex",true)){
                           validateRegex=data.validator.get(i).Value
//                           when(data.validator.get(i).Value){
//                               "Alphabetic"->{
//                                   inputType="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
//                               }
//                               "Alphanumeric"->{
//                                   inputType="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
//                               }
//
//                           }
                       }
                   }
                }
                val edittextModel=EdittextModel("",validateRegex!!,data.hintText)
                hashmap.put(data.id,edittextModel)
                binding.viewModel = CustomerFormViewModel(data, formEditistener,edittextModel,hashmap)
            }
            else {
                val edittextModel=EdittextModel("",validateRegex!!,data.hintText)
                hashmap.put(data.id,edittextModel)
                binding.viewModel!!.data = data
                binding.viewModel!!.edittextModel=edittextModel!!
                binding.viewModel!!.hashmap=hashmap!!
                binding.viewModel!!.formEditistener=formEditistener
            }
        }
    }
}