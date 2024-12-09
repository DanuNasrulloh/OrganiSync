package com.example.organisync.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.organisync.API.ApiConfig
import com.example.organisync.Response.UploadFileResponse
import com.example.organisync.User.ModelUser
import com.example.organisync.User.UserPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUpload (private val pref: UserPreferences): ViewModel() {

    val data = MutableLiveData<UploadFileResponse>()

    fun setPostStory(token: String, image: MultipartBody.Part, desc: RequestBody) {
        val service = ApiConfig.getApiService().postStory("Bearer $token", image, desc)
        service.enqueue(object : Callback<UploadFileResponse> {
            override fun onResponse(call: Call<UploadFileResponse>, response: Response<UploadFileResponse>) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                    response.body()?.message?.let {
                        Log.d("RESULT POST :", it)
                    }
                }
            }
            override fun onFailure(call: Call<UploadFileResponse>, t: Throwable) {
                Log.d("Error :", t.message!!)
            }
        })
    }
    fun getUser(): LiveData<ModelUser> {
        return pref.getUser().asLiveData()
    }
    fun getStory(): LiveData<UploadFileResponse> {
        return data
    }
}