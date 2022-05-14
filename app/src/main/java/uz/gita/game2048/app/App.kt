package uz.gita.game2048.app

import android.app.Application
import uz.gita.game2048.data.local.MySharedPreferences
import uz.gita.game2048.data.repository.impl.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MySharedPreferences.init(this)
        AppRepositoryImpl.init()
    }
}