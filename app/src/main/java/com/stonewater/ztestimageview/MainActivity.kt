package com.stonewater.ztestimageview

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {
    private var soundPool: SoundPool? = null
    private var soundId = 1
    val samplePictures = intArrayOf(R.drawable.blackbird_340, R.drawable.cat_340, R.drawable.food_640, R.drawable.waterandcolor_640)
    val scaleType = arrayListOf(ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.FIT_XY)
    val grayFilterType = intArrayOf(R.color.filter_gray_200, R.color.filter_gray_500, R.color.filter_gray_700, R.color.filter_gray_900)
    val redFilterType = intArrayOf(R.color.filter_red_200, R.color.filter_red_500, R.color.filter_red_700, R.color.filter_red_900)
    //val sampleMusic = R.raw.nothing_from_nothing
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        soundPool!!.load(baseContext, R.raw.nothing_from_nothing, 1)

        imageView.setOnClickListener(this)
        imageView.setOnLongClickListener(this)
        imageButton.setOnClickListener(this)
        imageButton.setOnLongClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            is ImageButton -> {
                if (i == samplePictures.size) {
                    i = 0
                    imageView.setImageResource(samplePictures[i])
                    imageView.colorFilter = null
                    i++
                } else {
                    imageView.setImageResource(samplePictures[i])
                    imageView.setColorFilter(ContextCompat.getColor(this, grayFilterType[Random.nextInt(0,3)]))
                    i++
                }
                //Toast.makeText(this, "Button click : Not yet implemented", Toast.LENGTH_LONG).show()
            }
            is ImageView -> {
                imageView.setColorFilter(ContextCompat.getColor(this, redFilterType[Random.nextInt(0,3)]))
                imageView.scaleType = scaleType.shuffled().take(1)[0]
                //Toast.makeText(this, "Image click : Not yet implemented", Toast.LENGTH_LONG).show()
            }
            else -> Toast.makeText(this, "Click : this does nothing !!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        when (v) {
            is ImageButton -> {
                playSound(this.soundId)
                return false
            }
            is ImageView -> {
                if (i == samplePictures.size) {
                    i = 0
                    imageView.setImageResource(samplePictures[i])
                    imageView.colorFilter = null
                    i++
                    return true
                } else {
                    imageView.setImageResource(samplePictures[i])
                    imageView.setColorFilter(ContextCompat.getColor(this, grayFilterType[Random.nextInt(0,3)]))
                    i++
                    return true
                }
                //Toast.makeText(this, "Image click : Not yet implemented", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Click : this does nothing !!!", Toast.LENGTH_LONG).show()
                return false
            }
        }
    }

    fun playSound(soundId: Int){
        soundPool?.play(soundId,1F, 1F, 0, 0, 1F)
        Toast.makeText(this, "Playing music...", Toast.LENGTH_SHORT).show()
    }

    /*fun stopSound(soundId: Int){
        soundPool?.stop(soundId)
        Toast.makeText(this, "Stop music...", Toast.LENGTH_LONG).show()
    }*/
}