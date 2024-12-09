package com.example.organisync.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organisync.API.ApiConfig
import com.example.organisync.Response.ResponseRegister
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ViewModelRegister : ViewModel() {

    val data = MutableLiveData<ResponseRegister>()

    fun register(name: String?, email: String?, password: String?) {
        val retro = ApiConfig.getApiService().register(name, email, password)
        retro.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                }
                else {
                    Log.d("Error :", response.message())
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.d("onFailure", t.message!!)
            }
        })
    }
    fun getRegister(): LiveData<ResponseRegister>{
        return data
    }
}