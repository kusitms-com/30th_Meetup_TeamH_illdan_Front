package com.poptato.ui.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

@Composable
fun DismissKeyboardOnClick(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    val currentFocus = (context as Activity).currentFocus
                    currentFocus?.let {
                        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
                    }
                })
            }
    ) {
        content()
    }
}