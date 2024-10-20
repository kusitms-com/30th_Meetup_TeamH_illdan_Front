package com.poptato.di

import com.poptato.data.datastore.PoptatoDataStore
import com.poptato.domain.model.request.ReissueRequestModel
import com.poptato.domain.model.response.auth.TokenModel
import com.poptato.domain.repository.AuthRepository
import dagger.Lazy
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authRepository: Lazy<AuthRepository>,
    private val dataStore: PoptatoDataStore
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = runBlocking { dataStore.accessToken.firstOrNull() }
        val refreshToken = runBlocking { dataStore.refreshToken.firstOrNull() }

        return if (accessToken != null && refreshToken != null) {
            runBlocking {
                try {
                    val newTokens = authRepository.get().reissueToken(
                        request = ReissueRequestModel(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    ).firstOrNull()

                    newTokens?.getOrNull()?.let { token ->
                        dataStore.saveAccessToken(token.accessToken)
                        dataStore.saveRefreshToken(token.refreshToken)

                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${token.accessToken}")
                            .build()
                    } ?: run {
                        Timber.e("Token reissue failed: 새로운 토큰 없음")
                        null
                    }
                } catch (e: Exception) {
                    Timber.e("Token reissue failed: ${e.message}")
                    null
                }
            }
        } else {
            Timber.e("유효한 토큰 없음")
            null
        }
    }
}