package com.gb.projectnasa.ui

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.projectnasa.BuildConfig
import com.gb.projectnasa.network.NasaApi
import com.gb.projectnasa.network.NasaProperty
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _response: MutableLiveData<List<NasaProperty>> =
        MutableLiveData<List<NasaProperty>>()
    val response: LiveData<List<NasaProperty>>
        get() = _response

    fun getSize(): Int {
        if (response.value != null) return response.value!!.size
        return 0
    }

    fun getPosition(position: Int): NasaProperty? {
        return response.value?.get(position)
    }

    init {
        getNasaFotoToDay()
    }

    private fun getNasaFotoToDay() {
        val startDate = "2021-07-01"
        val endDate = "2021-07-10"
        viewModelScope.launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    _response.value = NasaApi.retrofitService.requestListFoto(
                        startDate, endDate,
                        BuildConfig.NASA_API_KEY, "true"
                    )
                }
            } catch (e: Exception) {
                Log.e("Foto_NASA", e.message.toString())
            }
        }
    }

}