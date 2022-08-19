package com.rsstudio.tweeky.ui.main


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsstudio.tweeky.R
import com.rsstudio.tweeky.data.network.model.Athlete
import com.rsstudio.tweeky.data.network.model.Data
import com.rsstudio.tweeky.databinding.ActivityMainBinding
import com.rsstudio.tweeky.ui.base.BaseActivity
import com.rsstudio.tweeky.ui.main.adapter.MainAdapter
import com.rsstudio.tweeky.ui.main.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    var logTag = "@MainActivity"

    lateinit var binding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initRecyclerView()
        initObservers()
    }

    private fun initTheme() {
        window.statusBarColor = resources.getColor(R.color.red)
        window.navigationBarColor = resources.getColor(R.color.white)
    }

    private fun initRecyclerView() {
        val llm = LinearLayoutManager(this)
        binding.rvAthlete.setHasFixedSize(true)
        binding.rvAthlete.layoutManager = llm
        mainAdapter = MainAdapter(this)
        binding.rvAthlete.adapter = mainAdapter
    }

    private fun initObservers() {

        viewModel.athleteData.observe(this) {

            if (it != null) {
                initTheme()
                val list: MutableList<Athlete> = mutableListOf()
                list.add(it)
                // submit list
                mainAdapter.submitList(list)
                binding.iLoader.visibility = View.GONE
            }
        }

    }
}