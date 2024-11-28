package com.laurentvrevin.doropomo.di

import android.content.Context
import com.laurentvrevin.doropomo.data.manager.DoNotDisturbManager
import com.laurentvrevin.doropomo.data.manager.UserPreferencesStorage
import com.laurentvrevin.doropomo.data.repository.AlarmRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.DoNotDisturbRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.TimerStateRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.UserPreferencesRepositoryImpl
import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import com.laurentvrevin.doropomo.domain.repository.DoNotDisturbRepository
import com.laurentvrevin.doropomo.domain.repository.TimerStateRepository
import com.laurentvrevin.doropomo.domain.repository.UserPreferencesRepository
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.GetUserPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SaveUserPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.UpdateCycleCountUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // --- Managers ---
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
    @Singleton
    @Provides
    fun provideTimerStateRepository(): TimerStateRepository {
        return TimerStateRepositoryImpl()
    }

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
    @Singleton
    @Provides
    fun provideStartTimerUseCase(
        timerStateRepository: TimerStateRepository,
        updateTimerStateUseCase: UpdateTimerStateUseCase,
        playAlarmUseCase: PlayAlarmUseCase
    ): StartTimerUseCase {
        return StartTimerUseCase(timerStateRepository, updateTimerStateUseCase, playAlarmUseCase)
    }

    @Singleton
    @Provides
    fun providePauseTimerUseCase(timerStateRepository: TimerStateRepository): PauseTimerUseCase {
        return PauseTimerUseCase(timerStateRepository)
    }

    @Singleton
    @Provides
    fun provideResetTimerUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ): ResetTimerUseCase {
        return ResetTimerUseCase(userPreferencesRepository)
    }

    @Singleton
    @Provides
    fun provideFinishTimerUseCase(
        playAlarmUseCase: PlayAlarmUseCase,
        stopAlarmUseCase: StopAlarmUseCase,
        timerStateRepository: TimerStateRepository
    ): FinishTimerUseCase {
        return FinishTimerUseCase(playAlarmUseCase, stopAlarmUseCase, timerStateRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateTimerStateUseCase(
        timerStateRepository: TimerStateRepository
    ): UpdateTimerStateUseCase {
        return UpdateTimerStateUseCase(timerStateRepository)
    }

    @Singleton
    @Provides
    fun provideSetTimerStateUseCase(
        timerStateRepository: TimerStateRepository
    ): SetTimerStateUseCase {
        return SetTimerStateUseCase(timerStateRepository)
    }

    // --- Preferences UseCases ---
    @Singleton
    @Provides
    fun provideSetTimerPreferencesUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ): GetUserPreferencesUseCase {
        return GetUserPreferencesUseCase(userPreferencesRepository)
    }

    @Provides
    fun provideSaveUserPreferencesUseCase(
        repository: UserPreferencesRepository
    ): SaveUserPreferencesUseCase {
        return SaveUserPreferencesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateCycleCountUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ): UpdateCycleCountUseCase {
        return UpdateCycleCountUseCase(userPreferencesRepository)
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
