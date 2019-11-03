package com.example.spideysense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.widget.Toast
import android.util.TypedValue
import android.media.ImageReader.OnImageAvailableListener
import android.os.PersistableBundle
import android.util.Size
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import android.graphics.Bitmap
import android.view.View
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {


    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == 1) {
            val fragment = (pager.adapter as ComicPagerAdapter).getFragmentByPositionUsingTag(
                R.id.pager,
                0
            ) as ComicFragment1

            fragment.setImage(BitmapDrawable(BitmapFactory.decodeResource(resources, R.drawable.test2)))
            textcontainer.visibility = View.GONE
        } else if (position == 0) {
            val fragment = (pager.adapter as ComicPagerAdapter).getFragmentByPositionUsingTag(
                R.id.pager,
                1
            ) as ComicFragment2

            fragment.setImage(BitmapDrawable(BitmapFactory.decodeResource(resources, R.drawable.test)))
            textcontainer.visibility = View.GONE
        }
    }

    private var sensorOrientation: Int? = null

    private var detector: Classifier? = null

    private var lastProcessingTimeMs: Long = 0
    private var rgbFrameBitmap: Bitmap? = null
    private var croppedBitmap: Bitmap? = null
    private var cropCopyBitmap: Bitmap? = null

    private var computingDetection = false

    private var timestamp: Long = 0

    private var frameToCropTransform: Matrix? = null
    private var cropToFrameTransform: Matrix? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ComicPagerAdapter(supportFragmentManager)
        pager.adapter = adapter
        pager.setOnPageChangeListener(this)

        scan.setOnClickListener {
            var cropSize = TF_OD_API_INPUT_SIZE
            try {
                detector = TFLiteObjectDetectionAPIModel.create(
                    getAssets(),
                    TF_OD_API_MODEL_FILE,
                    TF_OD_API_LABELS_FILE,
                    TF_OD_API_INPUT_SIZE,
                    TF_OD_API_IS_QUANTIZED
                )
                cropSize = TF_OD_API_INPUT_SIZE
            } catch (e: IOException) {
                e.printStackTrace()
                val toast = Toast.makeText(
                    getApplicationContext(),
                    "Classifier could not be initialized",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }

            var bitmap =  BitmapFactory.decodeResource(resources, R.drawable.test2)
            if (pager.currentItem == 0) {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.test2)
            } else {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.test)
            }
            val r = resize(bitmap, 320, 320)
            val results = detector!!.recognizeImage(r)
            var minimumConfidence = MINIMUM_CONFIDENCE_TF_OD_API
            when (MODE) {
                DetectorMode.TF_OD_API -> minimumConfidence =
                    MINIMUM_CONFIDENCE_TF_OD_API
            }

            val mappedRecognitions = LinkedList<Classifier.Recognition>()

            for (result in results) {
                val location = result.getLocation()

                val rectPaint = Paint()
                rectPaint.color = Color.GREEN
                rectPaint.setStyle(Paint.Style.STROKE)
                rectPaint.strokeWidth = 10.0f
                val tempBitmap = Bitmap.createBitmap(r.width, r.height, Bitmap.Config.RGB_565)
                val canvas = Canvas(tempBitmap)
                canvas.drawBitmap(r, 0f, 0f, null)

                if (location != null && result.confidence!! >= minimumConfidence) {
                    textcontainer.visibility = View.VISIBLE
                    canvas.drawRect(location, rectPaint)
                    characterName.text = "We have detected the hero: " + result.title.toString()
                    percentage.text = "Confidence level: " + toPercentage(result.confidence)
                    learnMore.text = "Learn more about " + result.title.toString()
                    if (pager.currentItem == 0) {
                        val frag =
                            (pager.adapter as ComicPagerAdapter).getFragmentByPositionUsingTag(
                                R.id.pager,
                                pager.currentItem
                            ) as ComicFragment1
                        frag.setImage(BitmapDrawable(resources, tempBitmap))
                    } else {
                        val frag =
                            (pager.adapter as ComicPagerAdapter).getFragmentByPositionUsingTag(
                                R.id.pager,
                                pager.currentItem
                            ) as ComicFragment2
                        frag.setImage(BitmapDrawable(resources, tempBitmap))
                    }
                    //image.setImageDrawable(BitmapDrawable(resources, tempBitmap))
                    result.setLocation(location)
                    mappedRecognitions.add(result)
                }
            }
        }

        learnMore.setOnClickListener {
            startActivity(DetailActivity.newIntent(this))
        }

    }


    // Which detection model to use: by default uses Tensorflow Object Detection API frozen
    // checkpoints.
    private enum class DetectorMode {
        TF_OD_API
    }

    fun toPercentage(n: Float): String {
        return String.format("%.0f", n * 100) + "%"
    }

    private fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var image = image
        if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            return image
        } else {
            return image
        }
    }

    companion object {
        // Configuration values for the prepackaged SSD model.
        private val TF_OD_API_INPUT_SIZE = 320
        private val TF_OD_API_IS_QUANTIZED = true
        private val TF_OD_API_MODEL_FILE = "model.tflite"
        private val TF_OD_API_LABELS_FILE = "file:///android_asset/dict.txt"
        private val MODE = DetectorMode.TF_OD_API
        // Minimum detection confidence to track a detection.
        private val MINIMUM_CONFIDENCE_TF_OD_API = 0.75f
        private val MAINTAIN_ASPECT = false
        private val DESIRED_PREVIEW_SIZE = Size(640, 480)
        private val SAVE_PREVIEW_BITMAP = false
        private val TEXT_SIZE_DIP = 10f
    }
}
