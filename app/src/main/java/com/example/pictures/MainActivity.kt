package com.example.pictures

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pictures.managePics.Entry
import com.example.pictures.managePics.MainAdapter
import java.util.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CODE = 2139
        const val PICS_SHARED_PREF = "PicsSharedPref"
        const val PICS_SHARED_PREF_KEY = "PicsSharedPrefKey"
    }

    private var mainAdapter = MainAdapter(ArrayList())
    private var sharedPrefUpToDate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences(PICS_SHARED_PREF,Context.MODE_PRIVATE)
        if(sharedPref.contains(PICS_SHARED_PREF_KEY)){
            val type = object : TypeToken<ArrayList<Entry>>() {}.type
            val entryList = Gson().fromJson<ArrayList<Entry>>(sharedPref.getString(PICS_SHARED_PREF_KEY, ""), type)
            mainAdapter = MainAdapter(entryList)
        }

        val recyclerView : RecyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mainAdapter

        val itemTouchHelper = ItemTouchHelper(getLeftSwipeCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onPause() {
        super.onPause()
        if(!sharedPrefUpToDate){
            updateSharedPref()
        }
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
                val name = data!!.extras!!.getString(AddImageActivity.PIC_NAME_KEY)
                val url = data.extras!!.getString(AddImageActivity.PIC_URL_KEY)
                val date = Date()
                date.time= data.extras!!.getLong(AddImageActivity.PIC_DATE_KEY)
                val newEntry = Entry(name, url, date)
                mainAdapter.addItem(newEntry)
                sharedPrefUpToDate = false
            }
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,getString(R.string.adding_picture_failed), Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateSharedPref(){
        val sharedPref = getSharedPreferences(PICS_SHARED_PREF, Context.MODE_PRIVATE)
        val prefsEdit = sharedPref.edit()
        val json = Gson().toJson(mainAdapter.values)
        prefsEdit.putString(PICS_SHARED_PREF_KEY, json)
        prefsEdit.apply()
        sharedPrefUpToDate = true
    }

    private fun getLeftSwipeCallback() : ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                mainAdapter.removeItem(position)
                sharedPrefUpToDate = false
            }
        }
    }
}
