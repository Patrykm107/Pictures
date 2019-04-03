package com.example.pictures

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pictures.layout.MainAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        const val requestImageCode = 1
    }

    private val mainAdapter = MainAdapter(ArrayList())
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
                startActivityForResult(intent, requestImageCode)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == requestImageCode){
            if(resultCode == Activity.RESULT_OK){

            }
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,getString(R.string.adding_picture_failed), Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
