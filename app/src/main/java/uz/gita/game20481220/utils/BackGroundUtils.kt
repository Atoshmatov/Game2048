package uz.gita.game20481220.utils

import uz.gita.game20481220.R

object BackGroundUtils {
    val color = arrayOf(
        R.drawable.bg_item_0,
        R.drawable.bg_item_2,
        R.drawable.bg_item_4,
        R.drawable.bg_item_8,
        R.drawable.bg_item_16,
        R.drawable.bg_item_32,
        R.drawable.bg_item_64,
        R.drawable.bg_item_128,
        R.drawable.bg_item_256,
        R.drawable.bg_item_512,
        R.drawable.bg_item_1024,
        R.drawable.bg_item_2048,
        R.drawable.bg_item_4096,
        R.drawable.bg_item_8192,
        R.drawable.bg_item_16384,
        R.drawable.bg_item_32768,
        R.drawable.bg_item_65536,
        R.drawable.bg_item_131072
    )
}

fun Int.getBackgroundByValue(): Int {
    var amount = this
    var degree = 0
    while (amount > 0) {
        amount /= 2
        degree++
    }
    return BackGroundUtils.color[degree]
}