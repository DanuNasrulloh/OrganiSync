package com.example.organisync.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.organisync.API.ApiConfig
import com.example.organisync.Response.ResponseStory
import com.example.organisync.Response.Story
import com.example.organisync.User.ModelUser
import com.example.organisync.User.UserPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelMain(private val pref: UserPreferences) : ViewModel() {

    private val _listUser = MutableLiveData<ArrayList<Story>>()
    val listUser: LiveData<ArrayList<Story>> = _listUser

    fun setStory(token: String) {
        val retro = ApiConfig.getApiService().getStory("Bearer $token")
        retro.enqueue(StoryCallback())
    }

    private inner class StoryCallback : Callback<ResponseStory> {
        override fun onResponse(call: Call<ResponseStory>, response: Response<ResponseStory>) {
            if (response.isSuccessful) {
                response.body()?.listStory?.let {
                    _listUser.postValue(it as ArrayList<Story>)
                }
            }
        }

        override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
            Log.d("onFailure", t.message!!)
        }
    }

    fun getListStory(): LiveData<ArrayList<Story>> {
        return listUser
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getUser(): LiveData<ModelUser> {
        return pref.getUser().asLiveData()
    }
}