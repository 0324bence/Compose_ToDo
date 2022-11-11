package com.bence.todo.ui.components

import android.widget.CalendarView
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.bence.todo.R
import java.util.Calendar

data class ToDoData(
    val title: String,
    val content: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToDo(
    state: Boolean,
    onDismiss: () -> Unit,
    onAccept: (data: ToDoData) -> Unit,
) {
    if (state) {
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { onAccept(ToDoData(title, content)) }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.new_todo))
            },
            text = {
                Column(modifier = Modifier.fillMaxSize()) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = stringResource(id = R.string.new_todo_title)) },
                        placeholder = { Text(text = stringResource(id = R.string.new_todo_title)) },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text(text = stringResource(id = R.string.new_todo_title)) },
                        placeholder = { Text(text = stringResource(id = R.string.new_todo_title)) },
                        singleLine = false,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        )
    }
}