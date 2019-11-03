package com.example.spideysense

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val characterViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DetailRecyclerAdapter {}
        characterViewModel.name = "Spider-man"
        characterViewModel.detail.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    name.text = "Spider-man"
                    val path = it.data?.data?.results?.get(0)?.thumbnail?.path + "/standard_fantastic.jpg"
                    //Picasso.get().load(path).resize(250, 250).centerInside().into(characterImage)
                    Glide.with(this).load(path).into(characterImage)
                    it.data?.data?.results?.forEach {
                        (recyclerView.adapter as DetailRecyclerAdapter).swapData(it.comics.items)
                    }
                }
                Status.ERROR -> {}
                Status.LOADING -> {}
            }
        })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DetailActivity::class.java)
        }
    }
}
