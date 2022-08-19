package com.rsstudio.tweeky.di

import android.content.Context
import com.rsstudio.tweeky.app.TweekyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun applicationContext( @ApplicationContext applicationContext: Context) : TweekyApp {
        return applicationContext as TweekyApp
    }
}