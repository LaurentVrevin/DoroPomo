package com.laurentvrevin.doropomo.di

import android.content.Context
import com.laurentvrevin.doropomo.data.manager.DoNotDisturbManager
import com.laurentvrevin.doropomo.data.repository.AlarmRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.PreferenceRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.TimerRepositoryImpl
import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import com.laurentvrevin.doropomo.domain.repository.TimerRepository
import com.laurentvrevin.doropomo.data.manager.PreferenceManager
import com.laurentvrevin.doropomo.domain.repository.PreferenceRepository
import com.laurentvrevin.doropomo.domain.usecase.timer.FinishTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.PlayAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.alarm.StopAlarmUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SavePomodoroPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SetTimerPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.UpdateCycleCountUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.ResetTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.StartTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.timer.UpdateTimerStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTimerRepository(): TimerRepository {
        return TimerRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideStartTimerUseCase(timerRepository: TimerRepository, updateTimerStateUseCase: UpdateTimerStateUseCase, playAlarmUseCase: PlayAlarmUseCase): StartTimerUseCase {
        return StartTimerUseCase(timerRepository, updateTimerStateUseCase, playAlarmUseCase)
    }
    @Singleton
    @Provides
    fun provideUpdateTimerStateUseCase(timerRepository: TimerRepository): UpdateTimerStateUseCase {
        return UpdateTimerStateUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun providePauseTimerUseCase(timerRepository: TimerRepository): PauseTimerUseCase {
        return PauseTimerUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun provideResetTimerUseCase(timerRepository: TimerRepository, preferenceManager: PreferenceManager): ResetTimerUseCase {
        return ResetTimerUseCase(timerRepository, preferenceManager)
    }

    @Singleton
    @Provides
    fun provideUpdateCycleCountUseCase(preferenceRepository: PreferenceRepository): UpdateCycleCountUseCase {
        return UpdateCycleCountUseCase(preferenceRepository)
    }

    @Singleton
    @Provides
    fun provideFinishTimerUseCase(playAlarmUseCase: PlayAlarmUseCase, stopAlarmUseCase: StopAlarmUseCase, timerRepository: TimerRepository): FinishTimerUseCase {
        return FinishTimerUseCase(playAlarmUseCase, stopAlarmUseCase, timerRepository)
    }

    @Singleton
    @Provides
    fun providePreferenceRepository(preferenceManager: PreferenceManager, doNotDisturbManager: DoNotDisturbManager): PreferenceRepository {
        return PreferenceRepositoryImpl(preferenceManager, doNotDisturbManager)
    }

    @Singleton
    @Provides
    fun provideSetTimerPreferencesUseCase(timerRepository: TimerRepository): SetTimerPreferencesUseCase {
        return SetTimerPreferencesUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun provideSavePreferenceUseCase(preferenceManager: PreferenceManager, timerRepository: TimerRepository): SavePomodoroPreferencesUseCase {
        return SavePomodoroPreferencesUseCase(preferenceManager, timerRepository)
    }

    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

    @Singleton
    @Provides
    fun provideDoNotDisturbManager(@ApplicationContext context: Context): DoNotDisturbManager {
        return DoNotDisturbManager(context)
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(@ApplicationContext context: Context): AlarmRepository {
        return AlarmRepositoryImpl(context)
    }

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