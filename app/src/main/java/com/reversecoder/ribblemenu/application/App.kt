//package io.armcha.ribble
//
//import android.app.Application
//import com.luseen.logger.LogType
//import com.luseen.logger.Logger
//import com.squareup.leakcanary.LeakCanary
//import io.armcha.ribble.di.component.ApplicationComponent
//import io.armcha.ribble.di.component.DaggerApplicationComponent
//import io.armcha.ribble.di.module.ApplicationModule
//import io.armcha.ribble.presentation.utils.extensions.nonSafeLazy
//
///**
// * Created by Chatikyan on 29.07.2017.
// */
//class App : Application() {
//
//    val applicationComponent: ApplicationComponent by nonSafeLazy {
//        DaggerApplicationComponent.builder()
//                .applicationModule(ApplicationModule(this))
//                .build()
//    }
//
//    companion object {
//        var instance = App()
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        if (LeakCanary.isInAnalyzerProcess(this)) return
//        LeakCanary.install(this)
//        App.instance = this
//        initLogger()
//    }
//
//    private fun initLogger() {
//        Logger.Builder()
//                .isLoggable(BuildConfig.DEBUG)
//                .logType(LogType.ERROR)
//                .tag("Ribble")
//                .setIsKotlin(true)
//                .build()
//    }
//}