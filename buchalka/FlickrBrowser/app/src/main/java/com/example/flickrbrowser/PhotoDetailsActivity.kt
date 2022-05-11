package com.example.flickrbrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flickrbrowser.databinding.ActivityPhotoDetailsBinding

class PhotoDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }
}
