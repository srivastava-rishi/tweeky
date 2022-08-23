package com.rsstudio.tweeky.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
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
class MainActivity : BaseActivity() , View.OnClickListener {

    var logTag = "@MainActivity"

    lateinit var binding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initRecyclerView()
        initAction()
        initView()
        initObservers()
    }

    private fun initTheme() {
        window.statusBarColor = resources.getColor(R.color.red)
        window.navigationBarColor = resources.getColor(R.color.white)
    }

    private fun initView(){
        changeSortType()
    }

    private fun initAction() {
        binding.iViewBottom.rlBottom.setOnClickListener(this)
        binding.fbMyScore.setOnClickListener(this)
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
                mainAdapter.submitList(list[0].athletes,pref.getSortType())
                binding.iLoader.visibility = View.GONE
                binding.fbMyScore.visibility = View.VISIBLE
            }
        }

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun openSortSetting()  {

        val bsd = BottomSheetDialog(this@MainActivity, R.style.BottomSheetStyle)
        bsd.setContentView(R.layout.bottom_sheet_sort)

        val radioButton1 = bsd.findViewById<RadioButton>(R.id.radioScore)
        val radioButton2 = bsd.findViewById<RadioButton>(R.id.radioRunUp)
        val radioButton3 = bsd.findViewById<RadioButton>(R.id.radioJump)
        val radioButton4 = bsd.findViewById<RadioButton>(R.id.radioBackFootContact)
        val radioButton5 = bsd.findViewById<RadioButton>(R.id.radioFrontFootContact)
        val radioButton6 = bsd.findViewById<RadioButton>(R.id.radioRelease)

        when {
            pref.getSortType() == 1 -> {
                radioButton1!!.isChecked = true
            }
            pref.getSortType() == 2 -> {
                radioButton2!!.isChecked = true
            }
            pref.getSortType() == 3 -> {
                radioButton3!!.isChecked = true
            }
            pref.getSortType() == 4 -> {
                radioButton4!!.isChecked = true
            }
            pref.getSortType() == 5 -> {
                radioButton5!!.isChecked = true
            }
            pref.getSortType() == 5 -> {
                radioButton6!!.isChecked = true
            }
        }

        radioButton1!!.setOnClickListener {
            pref.saveSortType(1)
            binding.iViewBottom.tvSortType.text = radioButton1.text
            mainAdapter.sortList(pref.getSortType())
            bsd.dismiss()
        }

        radioButton2!!.setOnClickListener {
            pref.saveSortType(2)
            binding.iViewBottom.tvSortType.text = radioButton2.text
            mainAdapter.sortList(pref.getSortType())
            bsd.dismiss()
        }
        radioButton3!!.setOnClickListener {
            pref.saveSortType(3)
            binding.iViewBottom.tvSortType.text = radioButton3.text
            mainAdapter.sortList(pref.getSortType())
            bsd.dismiss()
        }
        radioButton4!!.setOnClickListener {
            pref.saveSortType(4)
            binding.iViewBottom.tvSortType.text = radioButton4.text
            mainAdapter.sortList(pref.getSortType())
            bsd.dismiss()
        }
        radioButton5!!.setOnClickListener {
            pref.saveSortType(5)
            binding.iViewBottom.tvSortType.text = radioButton5.text
            mainAdapter.sortList(pref.getSortType())
            bsd.dismiss()
        }
        radioButton6!!.setOnClickListener {
            pref.saveSortType(6)
            binding.iViewBottom.tvSortType.text = radioButton6.text
            mainAdapter.sortList(pref.getSortType())
            bsd.dismiss()
        }
        bsd.show()

    }

    @SuppressLint("SetTextI18n")
    private fun changeSortType(){
        when {
            pref.getSortType() == 1 -> {
                binding.iViewBottom.tvSortType.text = "Score"
            }
            pref.getSortType() == 2 -> {
                binding.iViewBottom.tvSortType.text = "Run-Up"
            }
            pref.getSortType() == 3 -> {
                binding.iViewBottom.tvSortType.text = "Jump"
            }
            pref.getSortType() == 4 -> {
                binding.iViewBottom.tvSortType.text = "Back-Foot Contact"
            }
            pref.getSortType() == 5 -> {
                binding.iViewBottom.tvSortType.text = "Front-Foot Contact"
            }
            pref.getSortType() == 5 -> {
                binding.iViewBottom.tvSortType.text = "Release"
            }
        }

    }

    override fun onClick(p0: View?) {

        when(p0?.id){
            R.id.rlBottom -> {
               openSortSetting()
            }
            R.id.fbMyScore -> {
                binding.rvAthlete.smoothScrollToPosition(mainAdapter.getMyPosition())
            }


        }
    }

}