package com.example.posturemonitor.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.juul.kable.Advertisement
import com.juul.kable.AndroidPeripheral
import com.juul.kable.Peripheral
import com.juul.kable.Scanner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class BleManager(context: Context) {
    private val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

    // UUIDs for our ESP32 service and characteristics
    companion object {
        const val POSTURE_SERVICE_UUID = "0000ff00-0000-1000-8000-00805f9b34fb"
        const val POSTURE_DATA_CHARACTERISTIC_UUID = "0000ff01-0000-1000-8000-00805f9b34fb"
        const val BATTERY_LEVEL_CHARACTERISTIC_UUID = "0000ff02-0000-1000-8000-00805f9b34fb"
    }

    val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter.isEnabled

    @SuppressLint("MissingPermission")
    fun scanForDevices(): Flow<Advertisement> {
        return Scanner().advertisements
            .filter { advertisement ->
                advertisement.name?.startsWith("PostureMonitor") == true
            }
    }

    suspend fun connectToDevice(advertisement: Advertisement): Peripheral {
        return AndroidPeripheral(advertisement).apply {
            connect()
        }
    }

    fun hasRequiredPermissions(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}