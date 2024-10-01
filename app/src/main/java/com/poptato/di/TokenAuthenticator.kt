package com.poptato.di

import com.poptato.data.datastore.PoptatoDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
//    private val tokenProvider: Lazy<TokenProvider>,
    private val dataStore: PoptatoDataStore
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
//        val refreshToken = runBlocking { dataStore.refreshToken.firstOrNull() }
//
//        return refreshToken?.let {
//            val newTokens = runBlocking {
//                tokenProvider.get().refreshTokens(refreshToken)
//            }
//
//            newTokens?.let { token ->
//                runBlocking {
//                    dataStore.saveAccessToken(token.accessToken)
//                    dataStore.saveRefreshToken(token.refreshToken)
//                }
//
//                response.request.newBuilder()
//                    .header("Authorization", "Bearer ${token.accessToken}")
//                    .build()
//            }
//        }
        return null
    }
}