package com.keixxdd.getip.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keixxdd.getip.data.repository.DefaultRepository
import com.keixxdd.getip.utils.Resource
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DefaultRepository
): ViewModel() {

    sealed class GetIpEvent{
        class Success(val resultString: String?): GetIpEvent()
        class Error(val errorText: String?): GetIpEvent()
        object Loading : GetIpEvent()
        object NotLoading : GetIpEvent()
    }

    private val _data = MutableStateFlow<GetIpEvent>(GetIpEvent.NotLoading)
    val data : StateFlow<GetIpEvent> = _data

    fun getIp(){

        viewModelScope.launch(Dispatchers.IO) {
            _data.value = GetIpEvent.Loading
            when(val ipResponce = repository.getIpData()){
                is Resource.Success -> {
                    if(ipResponce.data == null){
                        _data.value = GetIpEvent.Error("Unexpected error")
                    }else{
                        _data. value = GetIpEvent.Success(ipResponce.data.ip)
                    }
                }
                is Resource.Failure ->{
                    _data.value = GetIpEvent.Error(ipResponce.message)
                }
            }
        }
    }
}