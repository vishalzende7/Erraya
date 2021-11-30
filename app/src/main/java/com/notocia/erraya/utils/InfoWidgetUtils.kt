package com.notocia.erraya.utils

import com.notocia.erraya.R

class InfoWidgetUtils {
    companion object {
        private val dList: HashMap<Int, Int> = hashMapOf(
            0 to R.drawable.ic_ruler,
            1 to R.drawable.ic_dumbell,
            2 to R.drawable.ic_education_info,
            3 to R.drawable.ic_zodiac,
            4 to R.drawable.ic_religon,
            5 to R.drawable.ic_drinking_cat,
            6 to R.drawable.ic_smoking,
            7 to R.drawable.ic_map
        )

        private val dIList: HashMap<Int, Int> = hashMapOf(
            0 to R.drawable.ic_swimming,
            1 to R.drawable.ic_reading,
            2 to R.drawable.ic_cricket
        )

        fun getBasicInfoIcon(i: Int): Int {
            return if (dList[i] == null) 0 else dList[i]!!
        }

        fun getIIIcon(i: Int):Int{
            return if (dIList[i] == null) 0 else dIList[i]!!
        }
    }
}