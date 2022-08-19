package com.rsstudio.tweeky.ui.main


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rsstudio.tweeky.R
import com.rsstudio.tweeky.databinding.ActivityMainBinding
import com.rsstudio.tweeky.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        https://srivastava-rishi.github.io/tweekapi/tweek.json
        initTheme()
    }

    private fun initTheme() {
        window.statusBarColor = resources.getColor(R.color.red)
        window.navigationBarColor = resources.getColor(R.color.white)
    }
}