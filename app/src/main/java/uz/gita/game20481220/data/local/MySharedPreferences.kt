package uz.gita.game20481220.data.local

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences {
    companion object {
        private var preferenced: SharedPreferences? = null
        private var mySharedPreferences: MySharedPreferences? = null
        fun init(context: Context) {
            when {
                preferenced != null -> return
                else -> {
                    mySharedPreferences = MySharedPreferences()
                    preferenced =
                        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
                }
            }
        }

        fun getInstance(): MySharedPreferences = mySharedPreferences!!
    }

    var bestResult: Int
        get() = preferenced!!.getInt("BEST_RESULT", 0)
        set(value) = preferenced!!.edit().putInt("BEST_RESULT", value).apply()

    var currentScore: Int
        get() = preferenced!!.getInt("CURRENT_SCORE", 0)
        set(value) = preferenced!!.edit().putInt("CURRENT_SCORE", value).apply()

    var isGameSaved: Boolean
        get() = preferenced!!.getBoolean("SAVE_GAME", false)
        set(value) = preferenced!!.edit().putBoolean("SAVE_GAME", value).apply()

    var matrix: String?
        get() = preferenced!!.getString("MATRIX", null)
        set(value) = preferenced!!.edit().putString("MATRIX", value).apply()
}