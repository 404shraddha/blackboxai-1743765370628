package com.example.posturemonitor.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posturemonitor.ble.BleManager
import com.example.posturemonitor.ble.PostureData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bleManager: BleManager
) : ViewModel() {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _postureData = MutableStateFlow<PostureData?>(null)
    val postureData: StateFlow<PostureData?> = _postureData.asStateFlow()

    private val _postureHistory = MutableStateFlow<List<Float>>(emptyList())
    val postureHistory: StateFlow<List<Float>> = _postureHistory.asStateFlow()

    private val _batteryLevel = MutableStateFlow<Int?>(null)
    val batteryLevel: StateFlow<Int?> = _batteryLevel.asStateFlow()

    init {
        viewModelScope.launch {
            startBleConnection()
        }
    }

    private suspend fun startBleConnection() {
        _connectionState.value = ConnectionState.Scanning
        try {
            bleManager.scanForDevices().collect { advertisement ->
                _connectionState.value = ConnectionState.Connecting
                val peripheral = bleManager.connectToDevice(advertisement)
                _connectionState.value = ConnectionState.Connected(peripheral)

                // Discover services
                peripheral.services().collect { service ->
                    if (service.uuid == BleManager.POSTURE_SERVICE_UUID) {
                        // Setup posture data notifications
                        peripheral.notify(service.uuid, BleManager.POSTURE_DATA_CHARACTERISTIC_UUID) {
                            val angle = it.toFloat()
                            val status = when {
                                angle < 10 -> PostureStatus.GOOD
                                angle < 20 -> PostureStatus.WARNING
                                else -> PostureStatus.BAD
                            }
                            _postureData.value = PostureData(angle, status)
                        }

                        // Read battery level
                        peripheral.read(service.uuid, BleManager.BATTERY_LEVEL_CHARACTERISTIC_UUID) {
                            _batteryLevel.value = it[0].toInt()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
        }
    }

    sealed class ConnectionState {
        object Disconnected : ConnectionState()
        object Scanning : ConnectionState()
        object Connecting : ConnectionState()
        data class Connected(val peripheral: Any) : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }
}