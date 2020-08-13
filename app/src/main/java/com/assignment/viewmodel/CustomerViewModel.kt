package com.assignment.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.R
import com.assignment.model.EdittextModel
import com.assignment.model.responsemodel.CustomerResponse
import com.assignment.model.responsemodel.Data
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.*
import java.nio.charset.Charset

class CustomerViewModel : ViewModel() {
     var formResponseLiveData: MutableLiveData<List<Data>>? =null
    var hashMapObservableField:ObservableField<HashMap<String, EdittextModel>> = ObservableField()
    private lateinit var jsonArray:JsonArray
    fun callJsonApi(context: Context):LiveData<List<Data>>{
        if (formResponseLiveData==null){
            formResponseLiveData=MutableLiveData()
            //here i am loading data from assets we can also get data from url
            loadjson(context)
        }
        return formResponseLiveData!!
    }
   private fun loadjson(context: Context) {
        val stream = context.assets.open("response.json")
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        val formResponse = Gson().fromJson<CustomerResponse>(
            String(buffer, Charset.forName("UTF-8")),
            object : TypeToken<CustomerResponse>() {}.type
        )
        formResponseLiveData!!.postValue(formResponse.data)
    }

    fun submit(view:View){
        if (validate(view)){
            val jsonObj=JsonObject()
            jsonObj.add("data",jsonArray)
            Log.e("json",jsonObj.toString())
        }
    }
    private fun validate(view:View):Boolean{
        jsonArray=JsonArray()
        if (hashMapObservableField.get()!=null){
            hashMapObservableField.get()?.forEach { (key, value) ->
//                println("$key = $value")
//                val pattern = Regex(pattern = value.vaildRegex)
                if (value.text.equals("")){
                    Toast.makeText(view.context,view.context.resources.getString(R.string.validator_message)+"${value.hintText}",Toast.LENGTH_SHORT).show()
                    return false
                }

                else if (!value.text.matches(value.vaildRegex.toRegex())){
                    Toast.makeText(
                        view.context,
                        view.context.resources.getString(R.string.validator_message_regex) + "${value.hintText}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }

                    val jsonObj=JsonObject()
                    jsonObj.addProperty("id","$key")
                    jsonObj.addProperty("text","${value.text}")
                    jsonArray.add(jsonObj)



            }
            return true
        }
        else{
            Toast.makeText(view.context,view.context.resources.getString(R.string.please_enter),Toast.LENGTH_SHORT).show()
        }
        return true
    }
}