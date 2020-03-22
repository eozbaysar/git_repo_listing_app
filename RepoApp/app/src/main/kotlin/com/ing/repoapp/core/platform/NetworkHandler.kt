package com.ing.repoapp.core.platform
import android.content.Context
import com.fernandocejas.sample.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Emrah ÖZBAYSAR
 * Injectable class which returns information about the network connection state.
 */

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}