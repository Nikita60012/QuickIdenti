@file:Suppress("DEPRECATION")

package com.example.quickidenti

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class IdentificationActivity: AppCompatActivity() {

    private val requestTakePhoto = 1
    private lateinit var unknownPerson: Bitmap
    private lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identification)

        imageView = findViewById(R.id.identPhotoImageView)
        val photoButton: Button = findViewById(R.id.identPhotoButton)
        photoButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, requestTakePhoto)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        val identificateButton: Button = findViewById(R.id.identificateButton)
        identificateButton.setOnClickListener{
            val intent = Intent(this, IdentificationResultActivity::class.java)
            finish()
            startActivity(intent)
        }
//  Взять фото из галереи
//        val galleryButton: Button = findViewById(R.id.identGalleryButton)
//        galleryButton.setOnClickListener{
//            val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivity(intentGallery)
//        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestTakePhoto && resultCode == RESULT_OK) {
            val thumbnailBitmap = data?.extras?.get("data") as Bitmap
            unknownPerson = thumbnailBitmap
            imageView.setImageBitmap(thumbnailBitmap)
        }
    }
}