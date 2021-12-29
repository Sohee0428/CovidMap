package com.autocrypt.covidmap.map

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.autocrypt.covidmap.R

object MapBindingAdapter {

    @BindingAdapter("")
    @JvmStatic
    fun setCenterTypeMark(view: ImageView, centerType: String) {
        when (centerType) {
            "중앙/권역" -> view.setImageResource(R.drawable.ic_baseline_place_24)
            "지역" -> view.setImageResource(R.drawable.ic_baseline_place_25)
        }
    }
}