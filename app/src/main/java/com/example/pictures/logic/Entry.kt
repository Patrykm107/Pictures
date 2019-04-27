package com.example.pictures.logic

import java.util.*

data class Entry(val name: String, val pictureURL : String, val date: Date, var tags : ArrayList<String> = arrayListOf())