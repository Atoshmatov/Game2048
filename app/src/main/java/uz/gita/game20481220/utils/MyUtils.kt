package uz.gita.game20481220.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

fun myLog(message : String, tag : String = "TTT") {
    Log.d(tag, message)
}

fun Fragment.showToast(message : String, duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}


