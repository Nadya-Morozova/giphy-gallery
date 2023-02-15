package com.example.giphyapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.giphyapp.R
import com.example.giphyapp.databinding.ActivityMainBinding
import com.example.giphyapp.screens.detail.DetailFragment
import com.example.giphyapp.screens.main.MainFragment
import com.example.giphyapp.utils.OnGifClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnGifClick {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.container.id, MainFragment())
                .commit()
        }
    }

    override fun onGifClick(position: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, DetailFragment.newInstance(position))
            .addToBackStack(null)
            .commit()
    }

}