package com.laurentvrevin.doropomo.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentvrevin.doropomo.domain.model.UserPreferences
import com.laurentvrevin.doropomo.domain.usecase.preferences.GetUserPreferencesUseCase
import com.laurentvrevin.doropomo.domain.usecase.preferences.SaveUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val saveUserPreferencesUseCase: SaveUserPreferencesUseCase,
    private val redirectFocusModeUseCase: RedirectFocusModeUseCase
): ViewModel() {

    val isDarkTheme = mutableStateOf(false)

    private val _userPreferences = MutableStateFlow(UserPreferences())
    val userPreferences: StateFlow<UserPreferences> = _userPreferences

    init {
        viewModelScope.launch {
            val loadedPreferences = getUserPreferencesUseCase.execute()
            _userPreferences.value = loadedPreferences
        }
    }
    fun savePreferences(updatedPreferences: UserPreferences) {
        viewModelScope.launch {
            saveUserPreferencesUseCase.execute(updatedPreferences)
            _userPreferences.value = updatedPreferences
        }
    }

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

    fun redirectToFocusModeSettings() {
        redirectFocusModeUseCase.execute()
    }

}
