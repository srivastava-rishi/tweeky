package com.rsstudio.tweeky.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsstudio.tweeky.data.network.model.Athlete
import com.rsstudio.tweeky.data.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {

    var logTag = "@MainViewModel"

    // the pattern that i usually follow
    private val _athleteData: MutableLiveData<Athlete> = MutableLiveData()
    val athleteData: LiveData<Athlete> get() = _athleteData


    init {
       getAthleteData()
    }

    private fun getAthleteData() {

        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getAthleteData()

            _athleteData.value = result

        }
    }



}