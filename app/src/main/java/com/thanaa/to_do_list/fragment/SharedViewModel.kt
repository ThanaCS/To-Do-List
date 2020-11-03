package com.thanaa.to_do_list.fragment

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    fun verifyDataFormUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())


    }
}