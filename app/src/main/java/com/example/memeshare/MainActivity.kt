package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var currImageUrl:String?=null
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMeme()
    }
//
    private fun loadMeme(){

        binding.progress.visibility= View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        currImageUrl = "https://meme-api.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,  currImageUrl, null,
        Response.Listener { response ->
            val url=response.getString("url")
            Glide.with(this).load(url).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.visibility= View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.visibility= View.GONE
                    return false
                }
            }).into(binding.imageView)

        },
        Response.ErrorListener { error ->
            Toast.makeText(this,"Something went wrong ;)",Toast.LENGTH_LONG).show()
        }
    )


        queue.add(jsonObjectRequest)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
    fun memeShare(view: View) {
    val intent=Intent(Intent.ACTION_SEND)
    intent.type="text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this $currImageUrl")
    val chooser=Intent.createChooser(intent,"Share this meme using....")
    startActivity(chooser)
    }
}