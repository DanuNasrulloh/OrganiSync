package com.example.organisync.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organisync.API.ApiConfig
import com.example.organisync.Response.ResponseLogin
import com.example.organisync.User.ModelUser
import com.example.organisync.User.UserPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelLogin(private val pref: UserPreferences) : ViewModel() {

    private val _loginResponse = MutableLiveData<ResponseLogin>()
    val loginResponse: LiveData<ResponseLogin> = _loginResponse

    fun login(email: String, password: String) {
        val call = ApiConfig.getApiService().login(email, password)
        call.enqueue(LoginCallback())
    }

    private inner class LoginCallback : Callback<ResponseLogin> {
        override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    _loginResponse.postValue(it)
                    saveUser(ModelUser(it.loginResult.token.toString(), true))
                }
            } else {
                Log.e("ViewModelLogin", "Error: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
            Log.e("ViewModelLogin", "Failure: ${t.message}")
        }
    }

    private fun saveUser(user: ModelUser) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun getLogin(): LiveData<ResponseLogin> {
        return loginResponse
    }
}