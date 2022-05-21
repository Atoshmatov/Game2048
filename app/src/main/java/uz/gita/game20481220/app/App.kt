package uz.gita.game20481220.app

import android.app.Application
import uz.gita.game20481220.data.local.MySharedPreferences
import uz.gita.game20481220.data.repository.impl.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MySharedPreferences.init(this)
        AppRepositoryImpl.init()
    }
}