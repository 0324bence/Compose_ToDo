package com.bence.todo.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bence.todo.dataStore
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val savedState: SavedStateHandle
) : ViewModel() {

//    var darkTheme by mutableStateOf(false)
//        private set
//
//    fun changeTheme() { darkTheme = !darkTheme }
}