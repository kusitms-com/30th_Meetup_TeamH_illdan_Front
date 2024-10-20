package com.poptato.mypage.viewer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.poptato.design_system.R
import java.io.File

@Composable
fun PolicyViewerScreen(
    goBackToMyPage: () -> Unit = {}
) {

    PolicyPDFViewer(
        pdfResId = R.raw.policy,
        onBack = { goBackToMyPage() }
    )
}

@Composable
fun PolicyPDFViewer(
    pdfResId: Int,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val pdfFile = remember {
        val inputStream = context.resources.openRawResource(pdfResId)
        val outputFile = File(context.cacheDir, "policy_new.pdf")
        inputStream.use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        outputFile
    }

    BackHandler(enabled = true) {
        onBack()
    }

    AndroidView(
        factory = { ctx ->
            PDFView(ctx, null).apply {
                fromFile(pdfFile)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .load()
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}