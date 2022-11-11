package com.bence.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.bence.todo.R
import com.bence.todo.viewmodels.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bence.todo.dataStore
import com.bence.todo.ui.components.AddToDo
import com.bence.todo.ui.components.SwipableItem
import com.bence.todo.ui.theme.ToDoTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MainView(viewModel: MainViewModel = viewModel()) {

    //#region Preferences
    val context = LocalContext.current
    val DARK_THEME = booleanPreferencesKey("darkTheme")
    val darkThemeFlow: State<Boolean> = remember {
        context.dataStore.data.map {
            it[DARK_THEME] ?: false
        }
    }.collectAsState(initial = false)
    val darkTheme by darkThemeFlow

     suspend fun changeTheme() {
        context.dataStore.edit { settings ->
            val currentValue = settings[DARK_THEME] ?: false
            settings[DARK_THEME] = !currentValue
        }
    }
    //#endregion

    var todoModalState by remember { mutableStateOf(false) }
    
    ToDoTheme(darkTheme = darkTheme) {
        Scaffold(
            topBar = { TopAppBar(
                darkTheme = darkTheme,
                onThemeChange =  { GlobalScope.async { changeTheme() } }
            ) },
            floatingActionButton = {
                FloatingActionButton(onClick = { todoModalState = true }) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add icon")
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        SwipableItem(
                            headLine = { Text(text = "Todo Item") },
                            supportText = { Text(text = "Todo Item") },
                            trailingContent = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Todo more")
                                }
                            },
                            stayOpen = false,
                            onExpand = { println("expanded") },
                            actions = { modifier ->
                                Box(
                                    modifier = modifier.width(50.dp).background(MaterialTheme.colorScheme.tertiary).fillMaxHeight()
                                ) {

                                }
                            }
                        )
                    }
                }
                AddToDo(state = todoModalState, onDismiss = { todoModalState = false }, onAccept = {})
            }
        }
    }
}

@Composable
internal fun TopAppBar(darkTheme: Boolean, onThemeChange: ()->Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = onThemeChange) {
                Icon(
                    painter = if (darkTheme) painterResource(id = R.drawable.dark_theme) else painterResource(id = R.drawable.light_theme),
                    contentDescription = "Theme switcher"
                )
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2f.dp))
    )
}