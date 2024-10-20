package com.poptato.mypage.viewer

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@Composable
fun FAQViewerScreen(
    goBackToMyPage: () -> Unit = {}
) {

    val faqWebView = rememberWebViewState(
        url = "https://mountain-fang-96a.notion.site/FAQ-124d60b563cc801e8650edf2b64a445c?pvs=4",
        additionalHttpHeaders = emptyMap()
    )
    val webviewClient = AccompanistWebViewClient()
    val webChromeClient = AccompanistWebChromeClient()
    val webViewNavigator = rememberWebViewNavigator()

    CreateFAQWebViewer(
        webViewState = faqWebView,
        webViewClient = webviewClient,
        webChromeClient = webChromeClient,
        webViewNavigator = webViewNavigator,
        onClickBackBtn = { goBackToMyPage() }
    )
}

@Composable
fun CreateFAQWebViewer(
    webViewState: WebViewState,
    webViewClient: AccompanistWebViewClient,
    webChromeClient: AccompanistWebChromeClient,
    webViewNavigator: WebViewNavigator,
    onClickBackBtn: () -> Unit = {}
) {

    WebView(
        state = webViewState,
        navigator = webViewNavigator,
        client = webViewClient,
        chromeClient = webChromeClient,
        onCreated = { webView ->
            with (webView) {
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = false
                }
            }
        }
    )

    BackHandler(enabled = true) {
        if (webViewNavigator.canGoBack) {
            webViewNavigator.navigateBack()
        } else {
            onClickBackBtn()
        }
    }
}