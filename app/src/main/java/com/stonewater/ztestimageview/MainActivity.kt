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
    var i = 0
    var rotationClockwise = true
    var rotationX = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        soundPool!!.load(baseContext, R.raw.nothing_from_nothing, 1)

        // Image clicks
        imageView.setOnClickListener(this)
        imageView.setOnLongClickListener(this)

        // Image button clicks
        imageButton.setOnClickListener(this)
        imageButton.setOnLongClickListener(this)

        // Rotate and filter button clicks
        rotateButton.setOnClickListener(this)
        rotateButton.setOnLongClickListener(this)
        rotateXyButton.setOnClickListener(this)
        rotateXyButton.setOnLongClickListener(this)
        filterButton.setOnClickListener(this)
        filterButton.setOnLongClickListener(this)

        // Play and stop button clicks
        playButton.setOnClickListener(this)
        playButton.setOnLongClickListener(this)
        stopButton.setOnClickListener(this)
        stopButton.setOnLongClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            // simple click on buttons
            is ImageButton -> {
                if (v?.id == imageButton.id) {
                    if (i == samplePictures.size) {
                        i = 0
                        imageView.setImageResource(samplePictures[i])
                        imageView.colorFilter = null
                        i++
                    } else {
                        imageView.setImageResource(samplePictures[i])
                        i++
                    }
                } else if (v?.id == rotateButton.id){
                    if (rotationClockwise) imageView.rotation += 5f
                    else imageView.rotation -= 5f
                } else if (v?.id == rotateXyButton.id){
                    if (rotationX && rotationClockwise) imageView.rotationX += 5f           // rotation on X axis clockwise
                    else if (rotationX && !rotationClockwise) imageView.rotationX -= 5f     // rotation on X axis counter-clockwise
                    else if (!rotationX && rotationClockwise) imageView.rotationY += 5f     // rotation on Y axis clockwise
                    else imageView.rotationY -= 5f                                          // rotation on Y axis counter-clockwise
                    //Toast.makeText(this, "Rotate Axis : not yet implemented", Toast.LENGTH_SHORT).show()
                } else if(v?.id == filterButton.id){
                    // RED filter
                    imageView.setColorFilter(ContextCompat.getColor(this, redFilterType[Random.nextInt(1,4)]))
                } else if (v?.id == playButton.id){
                    resumeSound(this.soundId)
                    Toast.makeText(this, "Play Button : please Long press to play a sound", Toast.LENGTH_SHORT).show()
                } else if (v?.id == stopButton.id) {
                    pauseSound(this.soundId)
                    //Toast.makeText(this, "Stop Button : Not yet implemented", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "This button is not implemented yet", Toast.LENGTH_SHORT).show()
                }
            }

            //simple click on the picture
            is ImageView -> {
                imageView.scaleType = scaleType.shuffled().take(1)[0]
                Toast.makeText(this, "Shuffled scale type applied !!!", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(this, "Click : this does nothing !!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        when (v) {
            // long click on buttons
            is ImageButton -> {
                if (v?.id == imageButton.id) {
                    imageView.colorFilter = null
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.rotation = 0f
                    imageView.rotationX = 0f
                    imageView.rotationY = 0f
                    Toast.makeText(this, "Scale type, rotation and color filter reset !!!", Toast.LENGTH_SHORT).show()
                    return true
                } else if (v?.id == rotateButton.id){
                    // reverse rotation orientation (whatever X, Y or Z axis)
                    rotationClockwise = !rotationClockwise
                    Toast.makeText(this, "Rotation direction reversed", Toast.LENGTH_SHORT).show()
                    return true
                } else if (v?.id == rotateXyButton.id){
                    // reverse rotation Axis (X vs Y)
                    rotationX = !rotationX
                    Toast.makeText(this, "XY axis rotation type changed", Toast.LENGTH_SHORT).show()
                    return true
                } else if (v?.id == filterButton.id){
                    // Grey filter
                    imageView.setColorFilter(ContextCompat.getColor(this, grayFilterType[Random.nextInt(1,4)]))
                    Toast.makeText(this, "Grey filter applied !!!", Toast.LENGTH_SHORT).show()
                    return true
                } else if (v?.id == playButton.id){
                    playSound(this.soundId)
                    return true
                } else {
                    Toast.makeText(this, "Long click : this does nothing !!!", Toast.LENGTH_SHORT).show()
                    return true
                }
            }

            // long click on picture
            is ImageView -> {
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                Toast.makeText(this, "Scale type reset !!!", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                Toast.makeText(this, "Long click : this does nothing !!!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
    }

    fun playSound(soundId: Int){
        soundPool?.play(soundId,1F, 1F, 0, 0, 1F)
        soundPool?.autoResume()
        Toast.makeText(this, "Playing music...", Toast.LENGTH_SHORT).show()
    }

    fun resumeSound(soundId: Int){
        soundPool?.autoResume()
        Toast.makeText(this, "Playing music...", Toast.LENGTH_SHORT).show()
    }

    fun pauseSound(soundId: Int){
        soundPool?.autoPause()
        Toast.makeText(this, "Stop music...", Toast.LENGTH_SHORT).show()
    }
}