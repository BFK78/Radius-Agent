package com.example.radiusagent.presentation.main.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radiusagent.domain.model.Facility
import com.example.radiusagent.domain.model.Option
import com.example.radiusagent.domain.usecases.GetAllFacilitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllFacilitiesUseCase: GetAllFacilitiesUseCase
) : ViewModel() {

    var facilityState by mutableStateOf(Facility(facilityId = "0"))
        private set

    var optionsList by mutableStateOf<List<Option>>(emptyList())
        private set

    private var facilitateList by mutableStateOf<List<Facility>>(emptyList())

    private var exclusionList = hashMapOf<String, MutableList<String>>()

    init {
        getFacilities()
    }

    private fun getFacilities() = viewModelScope.launch {
        getAllFacilitiesUseCase()?.let {
            facilitateList = it
            facilityState = facilitateList[0]
            optionsList = facilityState.options
        } ?: kotlin.run {
            Log.i("MainViewModel", "Facility list is null.")
        }
        facilitateList.forEach {
            exclusionList[it.facilityId] = mutableListOf()
        }
    }

    fun onCardSelect(facilityId: String, option: Option) {
        val currentIndex = facilitateList.indexOfFirst {
            it.facilityId == facilityId
        }

        if (currentIndex < facilitateList.size - 1) {
            facilityState = facilitateList[currentIndex + 1]
        }

        option.exclusion.forEach { (t, u) ->
            exclusionList[t]?.addAll(u)
        }

        optionsList =
            facilityState.options.filter { opt ->
                exclusionList[facilityState.facilityId]?.contains(opt.id) == false
            }
    }

    fun onBackPressed(facilityId: String) {
        val currentIndex = facilitateList.indexOfFirst {
            it.facilityId == facilityId
        }

        facilityState = facilitateList[currentIndex - 1]
        exclusionList[facilityId]?.clear()

        optionsList = facilityState.options
    }
}