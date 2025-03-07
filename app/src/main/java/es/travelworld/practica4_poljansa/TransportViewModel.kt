package es.travelworld.practica4_poljansa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransportViewModel : ViewModel() {
    private val _transportList = MutableLiveData<List<Transporte>>()
    val transportList: LiveData<List<Transporte>> get() = _transportList

    init {
        loadFakeData()
    }

    private fun loadFakeData() {
        _transportList.value = listOf(
            Transporte("Classic Car", "34$/day",R.drawable.classiccar),
            Transporte("Sport Car","55$/day",R.drawable.sportcart),
            Transporte("Flying Car","500$/day",R.drawable.flyingcar),
            Transporte("Electric Car","42$/day",R.drawable.electriccar),
            Transporte("MotorHome","23$/day",R.drawable.motorhome),
            Transporte("Pickup","10$/day",R.drawable.pickupcar),
            Transporte("Airplane","11$/day",R.drawable.airplain),
            Transporte("Bus","14$/day",R.drawable.bus)


        )
    }
}
