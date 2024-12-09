package com.example.organisync.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.organisync.User.UserPreferences

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val pref: UserPreferences) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return getViewModelInstance(modelClass)
    }

    private fun <T : ViewModel> getViewModelInstance(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ViewModelLogin::class.java) -> {
                ViewModelLogin(pref) as T
            }
            modelClass.isAssignableFrom(ViewModelMain::class.java) -> {
                ViewModelMain(pref) as T
            }
            modelClass.isAssignableFrom(ViewModelUpload::class.java) -> {
                ViewModelUpload(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}