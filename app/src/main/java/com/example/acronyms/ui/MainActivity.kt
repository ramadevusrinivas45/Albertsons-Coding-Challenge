package com.example.acronyms.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.acronyms.R
import com.example.acronyms.data.Acronyms
import com.example.acronyms.data.NetworkResult
import com.example.acronyms.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var acronymsAdapter: AcronymsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvAcronyms.adapter = acronymsAdapter

        binding.imgSearch.setOnClickListener {
            checkAndFetchAcronyms()
        }

        binding.textInputSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkAndFetchAcronyms()
                return@setOnEditorActionListener false
            }
            false
        }

        acronymsAdapter.setItemClick(object : ClickInterface<Acronyms> {
            override fun onClick(data: Acronyms) {
                Toast.makeText(this@MainActivity, data.lf, Toast.LENGTH_SHORT).show()
            }
        })

        mainViewModel.acronymResponse.observe(this) { it ->
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                    binding.includeLayoutEmptyList.layoutEmptyList.isVisible = false
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                    binding.includeLayoutEmptyList.layoutEmptyList.isVisible = true
                    binding.rvAcronyms.isVisible = false
                }

                is NetworkResult.Success -> {
                    if (it.data.isEmpty()) {
                        binding.includeLayoutEmptyList.layoutEmptyList.isVisible = true
                        binding.rvAcronyms.isVisible = false
                    } else {
                        binding.includeLayoutEmptyList.layoutEmptyList.isVisible = false
                        binding.rvAcronyms.isVisible = true
                        acronymsAdapter.updateAcronyms(it.data[0].lfs)
                    }
                    binding.progressbar.isVisible = false
                }
            }
        }
    }

    private fun checkAndFetchAcronyms() {
        val inputStr = binding.textInputSearch.text?.toString()
        if (inputStr.isNullOrBlank()) {
            Toast.makeText(
                applicationContext,
                getString(R.string.empty_search_text),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            mainViewModel.fetchAcronyms(inputStr)
        }
    }


}
