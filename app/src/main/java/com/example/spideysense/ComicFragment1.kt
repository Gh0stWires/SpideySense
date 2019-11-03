package com.example.spideysense


import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_comic_fragment1.*

/**
 * A simple [Fragment] subclass.
 */
class ComicFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_fragment1, container, false)
    }



    fun setImage(bp: BitmapDrawable) {
        image1.setImageDrawable(bp)
    }

}
