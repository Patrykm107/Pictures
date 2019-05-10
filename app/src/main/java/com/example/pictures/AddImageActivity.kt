package com.example.pictures

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_image.*
import java.text.DateFormat.getDateTimeInstance
import java.util.*

class AddImageActivity : AppCompatActivity() {

    companion object {
        const val PIC_NAME_KEY = "pictureNameKey"
        const val PIC_URL_KEY = "pictureUrlKey"
        const val PIC_DATE_KEY = "pictureDateKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        addFinishButton.setOnClickListener {
            var goodInput = true
            if (addNameInput.text.isEmpty()) {
                addNameInput.error = getString(R.string.no_empty_input_error)
                goodInput = false
            }
            if (addUrlInput.text.isEmpty()) {
                addUrlInput.error = getString(R.string.no_empty_input_error)
                goodInput = false
            }

            if (goodInput) {
                val returnIntent = Intent()
                returnIntent.putExtra(PIC_NAME_KEY, addNameInput.text.toString())
                returnIntent.putExtra(PIC_URL_KEY, addUrlInput.text.toString())
                returnIntent.putExtra(PIC_DATE_KEY, Date().time)

                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
