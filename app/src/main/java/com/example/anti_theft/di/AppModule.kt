package com.example.anti_theft.di

import android.content.BroadcastReceiver
import android.content.Context
import com.example.anti_theft.broadcastreceivers.ChargingStatusReceiver
import com.example.anti_theft.sensors.AccelerometerSensor
import com.example.anti_theft.sensors.PocketDetectionSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideProximitySensor(@ApplicationContext context: Context) =
        PocketDetectionSensor(context)


    @Provides
    @Singleton
    fun provideAccelerometerSensor(@ApplicationContext context: Context) =
        AccelerometerSensor(context)


    @Provides
    @Singleton
    fun providesChargingBroadCastReceiver(): BroadcastReceiver = ChargingStatusReceiver()


}