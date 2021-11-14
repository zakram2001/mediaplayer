package androidatc.com.mediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaPlayer
import android.widget.SeekBar
import android.os.Handler
import android.util.Log
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer?=null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.relaxing_music)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controlSound(currentSong[0])
    }
    private fun controlSound(id:Int) {

        fab_play.setOnClickListener{

            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")

                initializeSeekBar()
            }
            mp?.start()
            Log.d("MainActivity", "Duration: ${mp!!.duration/1000} seconds")
        }

        fab_pause.setOnClickListener {
            if (mp !==null) mp?.pause()
            Log.d("MainActivity", "Paused at: ${mp!!.currentPosition/1000} seconds")
        }

        fab_stop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun initializeSeekBar(){

        seekbar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)
    }
}
