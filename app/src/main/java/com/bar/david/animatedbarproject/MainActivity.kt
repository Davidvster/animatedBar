package com.bar.david.animatedbarproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animated_bar.initAnimatedBar(listOf(0.1, 0.5, 0.7, 0.1).toDoubleArray() , listOf(ContextCompat.getColor(this, R.color.accent_material_light), ContextCompat.getColor(this, R.color.accent_material_dark), ContextCompat.getColor(this, R.color.colorAccent), ContextCompat.getColor(this, R.color.abc_tint_btn_checkable)).toIntArray(), 0.0, 1000)
        animated_bar.startAnimatedBar()
    }
}
