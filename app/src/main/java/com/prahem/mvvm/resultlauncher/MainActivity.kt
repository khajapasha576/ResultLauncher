package com.prahem.mvvm.resultlauncher

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.graphics.Bitmap
import android.os.Build
import android.widget.ImageView
import android.widget.Toast

import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initListeners()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initListeners() {


        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 0);
        }

        val startForResult = registerForActivityResult(StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode === RESULT_OK && result.data != null) {
                val bundle = result.data!!.extras
                val bitmap = bundle!!["data"] as Bitmap?
               findViewById<ImageView>(R.id.iv_img) .setImageBitmap(bitmap)
            }
        }

        findViewById<ImageView>(R.id.iv_img).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startForResult.launch(intent)
            } else {
                Toast.makeText(
                    this@MainActivity, "There is no app that support this action",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}