package com.kalalaucantrell.youtubeplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.kalalaucantrell.youtubeplayer.Constants.YOUTUBE_PLAYLIST
import com.kalalaucantrell.youtubeplayer.Constants.YOUTUBE_VIDEO_ID
import com.kalalaucantrell.youtubeplayer.databinding.ActivityStandaloneBinding

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityStandaloneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStandaloneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlayVideo.setOnClickListener(this)
        binding.btnPlaylist.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_VIDEO_ID, 0, true, false
            )
            R.id.btnPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_PLAYLIST, 0, 0, true, false
            )
            else -> throw IllegalArgumentException("Undefined button clicked")
        }
        startActivity(intent)
    }
}
