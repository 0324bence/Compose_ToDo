package com.bence.todo.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipableItem(
    headLine: @Composable () -> Unit,
    supportText: @Composable () -> Unit = {},
    trailingContent: @Composable () -> Unit,
    stayOpen: Boolean = true,
    //isExpanded: Boolean,
    onExpand: () -> Unit = {},
    onCollapse: () -> Unit = {},
    actions: @Composable RowScope.(modifier: Modifier) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var expandAmount by remember { mutableStateOf(0f) }
    var itemOffsetX by remember { mutableStateOf(0f) }
    val itemOffsetXTransition by updateTransition(targetState = itemOffsetX, label = "").animateFloat(label = "") { it }
    Box(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            actions(modifier = Modifier.onGloballyPositioned {
                expandAmount = it.size.width.toFloat()
                if (isExpanded && stayOpen) {
                    itemOffsetX += expandAmount
                } else {
                    itemOffsetX = 0f
                }
            })
        }
        ListItem(
            modifier = Modifier
                .offset { IntOffset(itemOffsetXTransition.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState {
                        itemOffsetX += if ((itemOffsetX + it) > 0 && (itemOffsetX + it) <= expandAmount) it else 0f
                    },
                    onDragStopped = {
                        if (itemOffsetX >= expandAmount/2) {
                            onExpand()
                            isExpanded = true
                        } else if (itemOffsetX < expandAmount && isExpanded) {
                            onCollapse()
                            isExpanded = false
                        }
                        itemOffsetX = if (isExpanded && stayOpen) expandAmount else 0f
                    }
                ),
            headlineText = headLine,
            trailingContent = trailingContent,
            supportingText = supportText
        )
    }
}