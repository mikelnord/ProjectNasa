package com.gb.projectnasa.ui

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.projectnasa.network.NasaApi
import com.gb.projectnasa.network.NasaProperty
import com.gb.myapplication.util.convertDayToString
import com.gb.projectnasa.BuildConfig
import kotlinx.coroutines.launch
import java.time.LocalDate

class PagerViewModel : ViewModel() {
    private val _response = MutableLiveData<NasaProperty>()
    val response: LiveData<NasaProperty>
        get() = _response

    init {
//        getNasaFotoToDay()
    }

    private fun getNasaFotoToDay() {
        viewModelScope.launch {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    _response.value = NasaApi.retrofitService.requestFotoDay(
                        convertDayToString(LocalDate.now()),
                        BuildConfig.NASA_API_KEY
                    )
                }
            } catch (e: Exception) {
                Log.e("Foto_NASA", e.message.toString())
            }
        }
    }
}