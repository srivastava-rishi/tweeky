package com.rsstudio.tweeky.ui.search


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsstudio.tweeky.R
import com.rsstudio.tweeky.data.network.model.Athlete
import com.rsstudio.tweeky.databinding.ActivitySearchBinding
import com.rsstudio.tweeky.ui.base.BaseActivity
import com.rsstudio.tweeky.ui.search.adapter.SearchAdapter
import com.rsstudio.tweeky.util.Constant

class SearchActivity : BaseActivity(), View.OnClickListener {

    var logTag = "@SearchActivity"

    lateinit var binding: ActivitySearchBinding

    private lateinit var list: MutableList<Athlete>

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        //
        initAction()
        initData()
        initRecyclerView()
        submitData()
        initView()
    }

    private fun initData() {
        list = intent.getSerializableExtra(Constant.PARAM_DATA) as MutableList<Athlete>
        Log.d(logTag, "initData: ${list[0].athletes}")
    }

    private fun initAction() {
        binding.ivCancel.setOnClickListener(this)
        binding.ivSearch.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        val llm = LinearLayoutManager(this)
        binding.rvAthlete.setHasFixedSize(true)
        binding.rvAthlete.layoutManager = llm
        searchAdapter = SearchAdapter(this)
        binding.rvAthlete.adapter = searchAdapter
    }

    private fun submitData() {
        searchAdapter.submitList(list[0].athletes, pref.getSortType())
    }

    private fun initView() {

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(logTag, "onTextChanged: $s")
                if (s.isNullOrEmpty()) {
                    searchAdapter.clearFilteredList()
                }
            }
        })

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun search() {
        var searchText = binding.searchInput.text

        if (searchText.isNotEmpty() || searchText != null) {
            Log.d(logTag, "onTextChanged: $searchText")
            searchAdapter.filter.filter(searchText)
            binding.searchInput.hideKeyboard()
        }
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.ivCancel -> {
                finish()
            }
            R.id.ivSearch -> {
                search()
            }

        }
    }
}