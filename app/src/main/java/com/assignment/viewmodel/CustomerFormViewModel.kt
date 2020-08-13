package com.assignment.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.assignment.model.EdittextModel
import com.assignment.model.responsemodel.Data
import com.assignment.view.listener.FormEditistener


class CustomerFormViewModel(var data:Data,var formEditistener: FormEditistener,var edittextModel: EdittextModel, var hashmap:HashMap<String,EdittextModel>):ViewModel() {


    companion object {
        @BindingAdapter("textChangedListener")
        @JvmStatic
        fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
            editText.addTextChangedListener(textWatcher)
        }
    }
    fun  getTextWatcher(): TextWatcher {
        return object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                Log.e("po",p0.toString())
                if (p0!!.length>0) {
                    edittextModel.text=p0.toString()
                    hashmap.put(data.id, edittextModel)
                    formEditistener.formEdited(hashmap)
                }
                else{
                    edittextModel.text=""
                    hashmap.put(data.id, edittextModel)
                    formEditistener.formEdited(hashmap)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        }
    }
}