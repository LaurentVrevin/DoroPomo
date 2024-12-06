package com.laurentvrevin.doropomo.di

import android.content.Context
import com.laurentvrevin.doropomo.data.source.DoNotDisturbManager
import com.laurentvrevin.doropomo.data.source.UserPreferencesStorage
import com.laurentvrevin.doropomo.data.repository.AlarmRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.DoNotDisturbRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.UserPreferencesRepositoryImpl
import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import com.laurentvrevin.doropomo.domain.repository.DoNotDisturbRepository
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.GetUserPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SaveUserPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.ToggleDoNotDisturbUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // --- Source ---
    @Singleton
    @Provides
    fun provideDoNotDisturbManager(@ApplicationContext context: Context): DoNotDisturbManager {
        return DoNotDisturbManager(context)
    }

    @Singleton
    @Provides
    fun provideUserPreferencesStorage(@ApplicationContext context: Context): UserPreferencesStorage {
        return UserPreferencesStorage(context)
    }

    // --- Repositories ---
    /*@Singleton
    @Provides
    fun provideTimerStateRepository(): TimerStateRepository {
        return TimerStateRepositoryImpl()
    }*/

    @Singleton
    @Provides
    fun provideUserPreferencesRepository(
        userPreferencesStorage: UserPreferencesStorage
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(userPreferencesStorage)
    }

    @Singleton
    @Provides
    fun provideDoNotDisturbRepository(
        doNotDisturbManager: DoNotDisturbManager
    ): DoNotDisturbRepository {
        return DoNotDisturbRepositoryImpl(doNotDisturbManager)
    }

    @Singleton
    @Provides
    fun provideAlarmRepository(@ApplicationContext context: Context): AlarmRepository {
        return AlarmRepositoryImpl(context)
    }

    // --- Timer UseCases ---



    // --- Preferences UseCases ---
    @Singleton
    @Provides
    fun provideGetUserPreferencesUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ): GetUserPreferencesUseCase {
        return GetUserPreferencesUseCase(userPreferencesRepository)
    }

    @Singleton
    @Provides
    fun provideSaveUserPreferencesUseCase(
        repository: UserPreferencesRepository
    ): SaveUserPreferencesUseCase {
        return SaveUserPreferencesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideToggleDoNotDisturbUseCase(
        doNotDisturbRepository: DoNotDisturbRepository
    ): ToggleDoNotDisturbUseCase {
        return ToggleDoNotDisturbUseCase(doNotDisturbRepository)
    }

    // --- Alarm UseCases ---
    @Singleton
    @Provides
    fun providePlayAlarmUseCase(alarmRepository: AlarmRepository): PlayAlarmUseCase {
        return PlayAlarmUseCase(alarmRepository)
    }

    @Singleton
    @Provides
    fun provideStopAlarmUseCase(alarmRepository: AlarmRepository): StopAlarmUseCase {
        return StopAlarmUseCase(alarmRepository)
    }
}
