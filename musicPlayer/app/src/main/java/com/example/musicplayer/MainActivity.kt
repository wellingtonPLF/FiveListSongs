package com.example.musicplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var playBtn : Button
    private lateinit var positionBar : SeekBar
    private lateinit var t1 : TextView
    private lateinit var t2 : TextView
    private lateinit var lvSongs: ListView
    private lateinit var lista : ArrayList<Songs>
    private lateinit var mp: MediaPlayer
    private var rodando: Int? = null
    private var totalTime: Int = 0
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.playBtn = findViewById(R.id.playBtn)
        this.positionBar = findViewById(R.id.positionBar)
        this.t1 = findViewById(R.id.elapsedTimeLabel)
        this.t2 = findViewById(R.id.remainingTimeLabel)
        this.lvSongs = findViewById(R.id.lvSongs)

        this.mp = MediaPlayer()
        this.lista = ArrayList()

        this.lista.add(Songs("numeroum","First song"))
        this.lista.add(Songs("numerodois","Second song"))
        this.lista.add(Songs("numerotres","Third song"))
        this.lista.add(Songs("numeroquatro","Fourth song"))
        this.lista.add(Songs("numerocinco","Fifth song"))

        this.lvSongs.adapter = Base_Adapter(this, this.lista)
        this.lvSongs.setOnItemClickListener(clickCurto())

        //Event
        playBtn.setOnClickListener(clickMusic())
    }

    fun MediaP(song: Int?){
        if(song != null) {
            mp = MediaPlayer.create(this, song)
            mp.isLooping = true
            mp.setVolume(0.5f, 0.5f)
            totalTime = mp.duration
        }
    }
    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    inner class clickCurto : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
            if(mp.isPlaying){
                mp.pause()
                this@MainActivity.playBtn.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorRed))
            }
            if(position == 0){
                this@MainActivity.rodando = R.raw.one
            }
            else if(position == 1){
                this@MainActivity.rodando = R.raw.two
            }
            else if(position == 2){
                this@MainActivity.rodando = R.raw.three
            }
            else if(position == 3){
                this@MainActivity.rodando = R.raw.fourth
            }
            else{
                this@MainActivity.rodando = R.raw.fifth
            }
            MediaP(this@MainActivity.rodando)
            @SuppressLint("HandlerLeak")
            var handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    var currentPosition = msg.what

                    // Update positionBar
                    positionBar.progress = currentPosition

                    // Update Labels
                    var elapsedTime = createTimeLabel(currentPosition)
                    t1.text = elapsedTime

                    var remainingTime = createTimeLabel(totalTime - currentPosition)
                    t2.text = "$remainingTime"
                }
            }

            // Thread
            Thread(Runnable {
                while (mp != null) {
                    try {
                        var msg = Message()
                        msg.what = mp.currentPosition
                        handler.sendMessage(msg)
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                    }
                }
            }).start()

            // Position Bar
            positionBar.max = totalTime
            positionBar.setOnSeekBarChangeListener(
                    object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            if (fromUser) {
                                mp.seekTo(progress)
                            }
                        }
                        override fun onStartTrackingTouch(p0: SeekBar?) {
                        }
                        override fun onStopTrackingTouch(p0: SeekBar?) {
                        }
                    }
            )
        }
    }

    inner class clickMusic : View.OnClickListener{
        override fun onClick(v: View?) {
            if(this@MainActivity.rodando!=null){
                if (mp.isPlaying) {
                    // Stop
                    mp.pause()
                    this@MainActivity.playBtn.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorRed))
                }
                else{
                    Log.i("APP_R", this@MainActivity.rodando.toString())
                    // Start
                    mp.start()
                    this@MainActivity.playBtn.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorGreen))
                }
            }
        }
    }
}
