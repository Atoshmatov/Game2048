package uz.gita.game2048.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.game2048.data.enums.SideEnum
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private var resultListener: ((SideEnum) -> Unit)? = null
    private val gesture = GestureDetector(context, MyGestureDetector())

    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        private val minMov = 100
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (abs(e2.x - e1.x) > abs(e2.y - e1.y)) {
                if (abs(e2.x - e1.x) < minMov) return false;
                if (e2.x > e1.x)
                    resultListener?.invoke(SideEnum.RIGHT)
                else
                    resultListener?.invoke(SideEnum.LEFT)
            } else {
                if (abs(e2.y - e1.y) < minMov) return false;
                if (e2.y > e1.y)
                    resultListener?.invoke(SideEnum.DOWN)
                else
                    resultListener?.invoke(SideEnum.UP)
            }
            return true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gesture.onTouchEvent(event)
        return true
    }

    fun setResultListener(block: ((SideEnum) -> Unit)) {
        resultListener = block
    }
}