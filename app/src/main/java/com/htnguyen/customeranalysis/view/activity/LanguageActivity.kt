package com.htnguyen.customeranalysis.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.htnguyen.customeranalysis.adapter.LanguageAdapter
import com.htnguyen.customeranalysis.base.BaseActivity
import com.htnguyen.customeranalysis.databinding.ActivityLanguageBinding
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.ultils.FileUtils

class LanguageActivity : BaseActivity() {

    private lateinit var binding: ActivityLanguageBinding
    var listLanguage = mutableListOf<String>()
    var layoutManager: LinearLayoutManager? = null
    private var languageAdapter: LanguageAdapter = LanguageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        binding.recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager
        generateItemWork()

        binding.edtSearch.doAfterTextChanged {
            if (binding.edtSearch.text.toString().isNotEmpty()) {
                languageAdapter.submitList(listLanguage.filter {
                    FileUtils.getStringLanguage(it).lowercase()
                        .contains(binding.edtSearch.text.toString().lowercase())
                })
                languageAdapter.notifyDataSetChanged()
            } else {
                languageAdapter.submitList(listLanguage)
            }

        }

        binding.imgClose.setOnClickListener {
            binding.edtSearch.setText("")
        }

        binding.txtBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun generateItemWork() {
        addListLanguage()
        languageAdapter = LanguageAdapter()
        binding.recyclerview.adapter = languageAdapter
        languageAdapter.submitList(listLanguage)
        languageAdapter.notifyDataSetChanged()
    }

    private fun addListLanguage() {
        listLanguage.add(Constants.LG_VIETNAMESE)
        listLanguage.add(Constants.LG_KOREAN)
        listLanguage.add(Constants.LG_ENGLISH)
        listLanguage.add(Constants.LG_LAOS)
        listLanguage.add(Constants.LG_MYANMAR)
        listLanguage.add(Constants.LG_RUSSIAN)
        listLanguage.add(Constants.LG_THAI)
        listLanguage.add(Constants.LG_CHINESE)
        listLanguage.add(Constants.LG_JAPANESE)
        listLanguage.add(Constants.LG_FILIPINO)
        listLanguage.add(Constants.LG_INDONESIAN)
        listLanguage.add(Constants.LG_SPANISH)
        listLanguage.add(Constants.LG_FRENCH)
        listLanguage.add(Constants.LG_INDIAN)
        listLanguage.add(Constants.LG_GERMAN)
        listLanguage.add(Constants.LG_ITALIAN)
    }

}