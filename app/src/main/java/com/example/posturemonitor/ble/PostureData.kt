package com.example.posturemonitor.ble

data class PostureData(
    val tiltAngle: Float,
    val status: PostureStatus
)

enum class PostureStatus {
    GOOD,
    WARNING,
    BAD
}