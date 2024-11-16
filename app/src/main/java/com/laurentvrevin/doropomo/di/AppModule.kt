package com.laurentvrevin.doropomo.di

import android.content.Context
import com.laurentvrevin.doropomo.data.repository.AlarmRepositoryImpl
import com.laurentvrevin.doropomo.data.repository.TimerRepositoryImpl
import com.laurentvrevin.doropomo.domain.repository.AlarmRepository
import com.laurentvrevin.doropomo.domain.repository.TimerRepository
import com.laurentvrevin.doropomo.domain.usecase.*
import com.laurentvrevin.doropomo.data.layer.PreferencesManager
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
    fun provideStartTimerUseCase(timerRepository: TimerRepository): StartTimerUseCase {
        return StartTimerUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun providePauseTimerUseCase(timerRepository: TimerRepository): PauseTimerUseCase {
        return PauseTimerUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun provideResetTimerUseCase(timerRepository: TimerRepository): ResetTimerUseCase {
        return ResetTimerUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun provideSetTimerPreferencesUseCase(timerRepository: TimerRepository): SetTimerPreferencesUseCase {
        return SetTimerPreferencesUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository = alarmRepositoryImpl

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
