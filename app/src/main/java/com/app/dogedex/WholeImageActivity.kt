package com.app.dogedex

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.app.dogedex.databinding.ActivityWholeImageBinding
import com.app.dogedex.utils.PHOTO_URI_KEY
import java.io.File

class WholeImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photoUri = intent.extras?.getString(PHOTO_URI_KEY)
        val uri = Uri.parse(photoUri)
        val path = uri.path

        if (path == null){
            Toast.makeText(this,
                getString(R.string.error_showing_image_no_photo_uri), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.wholeImage.load(File(path))

    }
}