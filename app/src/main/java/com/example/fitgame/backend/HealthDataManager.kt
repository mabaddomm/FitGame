package com.example.fitgame.backend

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Duration
import java.time.Instant

private const val TAG = "HealthDataManager"

data class HealthDataMetrics(
    val steps: Long = 0,
    val distance: Double = 0.0, // in meters
    val sleepSessions: Int = 0, // total hours
    //val exerciseSessions: Int = 0 // total minutes
)

val REQUIRED_PERMISSIONS = setOf(
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getReadPermission(DistanceRecord::class),
    HealthPermission.getReadPermission(SleepSessionRecord::class),
    //HealthPermission.getReadPermission(ExerciseSessionRecord::class)
)

class HealthDataManager(context: Context) {

    val client: HealthConnectClient? = initHealthConnectClient(context)

    private fun initHealthConnectClient(context: Context): HealthConnectClient? {
        return when (HealthConnectClient.getSdkStatus(context)) {
            HealthConnectClient.SDK_AVAILABLE -> {
                Log.i(TAG, "Health Connect SDK is available")
                HealthConnectClient.getOrCreate(context)
            }
            HealthConnectClient.SDK_UNAVAILABLE -> {
                Log.e(TAG, "Health Connect is not supported on this device")
                null
            }
            else -> {
                Log.w(TAG, "Health Connect status is not SDK_AVAILABLE. May need installation or update.")
                null
            }
        }
    }

    suspend fun hasAllPermissions(): Boolean {
        val client = client ?: return false
        return try {
            val granted = client.permissionController.getGrantedPermissions()
            granted.containsAll(REQUIRED_PERMISSIONS)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to check permissions", e)
            false
        }
    }

    suspend fun readHealthDataMetrics(startTime: Instant, endTime: Instant): HealthDataMetrics {
        val healthConnectClient = client ?: return HealthDataMetrics()

        if (!healthConnectClient.permissionController.getGrantedPermissions().containsAll(REQUIRED_PERMISSIONS)) {
            Log.e(TAG, "Not all required permissions granted for reading data.")
            return HealthDataMetrics()
        }

        val timeFilter = TimeRangeFilter.between(startTime, endTime)

        try {
            val stepsReadings = healthConnectClient.readRecords(
                ReadRecordsRequest(StepsRecord::class, timeRangeFilter = timeFilter)
            )
            val totalSteps = stepsReadings.records.sumOf { it.count }

            val distanceReadings = healthConnectClient.readRecords(
                ReadRecordsRequest(DistanceRecord::class, timeRangeFilter = timeFilter)
            )
            val totalDistanceMeters = distanceReadings.records.sumOf { it.distance.inMeters }

            val sleepReadings = healthConnectClient.readRecords(
                ReadRecordsRequest(SleepSessionRecord::class, timeRangeFilter = timeFilter)
            )
            val totalSleepHours = sleepReadings.records.sumOf {
                Duration.between(it.startTime, it.endTime).toHours().toInt()
            }


            return HealthDataMetrics(
                steps = totalSteps,
                distance = totalDistanceMeters,
                sleepSessions = totalSleepHours,
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error reading combined health data", e)
            return HealthDataMetrics()
        }
    }

}