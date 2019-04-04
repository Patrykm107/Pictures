package com.example.pictures

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_image.*
import java.text.DateFormat.getDateTimeInstance
import java.util.*

class AddImageActivity : AppCompatActivity() {
    private var tagsArray:Array<String> = arrayOf()

    companion object {
        const val PIC_NAME_KEY = "pictureNameKey"
        const val PIC_URL_KEY = "pictureUrlKey"
        const val PIC_DATE_KEY = "pictureDateKey"
        const val PIC_TAGS_KEY = "pictureTagsKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        addTagButton.setOnClickListener {
            when {
                addTagsInput.text.isEmpty() -> addTagsInput.error = getString(R.string.no_empty_input_error)
                tagsArray.contains(addTagsInput.text.toString()) -> addTagsInput.error =
                    getString(R.string.same_tag_already_added_error)
                addTagsInput.text.contains(" ") -> getString(R.string.tags_no_spaces_error)
                else -> {
                    val tag = addTagsInput.text.toString()
                    addTagsText.append("$tag ")
                    tagsArray += tag
                }
            }
        }

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
                returnIntent.putExtra(PIC_DATE_KEY, getDateTimeInstance().format(Date()).toString())
                returnIntent.putExtra(PIC_TAGS_KEY, tagsArray)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }


    }
}
