package com.roboticamedellin.helloapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _randomColorLiveData = MutableLiveData<String>()
    val randomColorLiveData : LiveData<String> = _randomColorLiveData

    init {
        _randomColorLiveData.value = "Red"
    }

    fun updateRandomColor() {
        val randomColor = listOf("Red", "Green", "Blue", "Black").random()
        _randomColorLiveData.value = randomColor
    }

}