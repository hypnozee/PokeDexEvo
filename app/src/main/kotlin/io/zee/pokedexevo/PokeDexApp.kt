package io.zee.pokedexevo

import android.app.Application
import io.zee.pokedexevo.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokeDexEvoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeDexEvoApp)
            modules(
                dataModule,
            )
        }
    }
}
