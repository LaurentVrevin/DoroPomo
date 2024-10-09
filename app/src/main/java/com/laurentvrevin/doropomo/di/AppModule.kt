package com.laurentvrevin.doropomo.di

import com.laurentvrevin.doropomo.data.repository.TimerRepositoryImpl
import com.laurentvrevin.doropomo.domain.repository.TimerRepository
import com.laurentvrevin.doropomo.domain.usecase.PauseTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.ResetTimerUseCase
import com.laurentvrevin.doropomo.domain.usecase.SetTimerPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.StartTimerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}