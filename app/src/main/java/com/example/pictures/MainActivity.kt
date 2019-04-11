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
import android.support.v7.widget.helper.ItemTouchHelper




class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CODE = 1
    }

    private val mainAdapter = MainAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mainAdapter

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //do something
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                mainAdapter.removeItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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
                val date = Date()
                date.time= data.extras!!.getLong(AddImageActivity.PIC_DATE_KEY)
                val newEntry = Entry(name, url, date)
                mainAdapter.addItem(newEntry)
            }
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,getString(R.string.adding_picture_failed), Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
