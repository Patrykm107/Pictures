package com.example.pictures

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pictures.logic.Entry
import com.example.pictures.logic.MainAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CODE = 1
    }

    private val mainAdapter = MainAdapter(ArrayList(), this)
    var i=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.mainRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mainAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addTagButton -> {
                val intent = Intent(this, AddImageActivity::class.java)
                startActivityForResult(intent, REQUEST_IMAGE_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CODE) {
            if(resultCode == Activity.RESULT_OK){
                val name = data?.extras!!.getString(AddImageActivity.PIC_NAME_KEY)
                val url = data.extras!!.getString(AddImageActivity.PIC_URL_KEY)
                val date = data.extras!!.getString(AddImageActivity.PIC_DATE_KEY)
                val tags = data.extras!!.getStringArray(AddImageActivity.PIC_TAGS_KEY)
                val newEntry = Entry(name, url, date, tags)
                mainAdapter.addItem(newEntry)
            }
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,getString(R.string.adding_picture_failed), Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
