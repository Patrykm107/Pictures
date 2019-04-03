package com.example.pictures

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_image.*

class AddImageActivity : AppCompatActivity() {
    private var tagsArray:Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        addTagButton.setOnClickListener {
            when {
                tagsInput.text.isEmpty() -> tagsInput.error = getString(R.string.no_empty_input_error)
                tagsArray.contains(tagsInput.text.toString()) -> tagsInput.error = getString(R.string.same_tag_already_added_error)
                else -> {
                    val tag = tagsInput.text.toString()
                    tagsText.append("$tag ")
                    tagsArray += tag
                }
            }
        }
    }
}
