package com.example.pictures.managePics

import java.util.*

data class Entry(val name: String, val pictureURL : String, val date: Date, var tags : ArrayList<String> = arrayListOf()){

    fun compareTagsSimilarity(other: Entry):Int{
        var count = 0
        for(tag in tags){
            if (other.tags.contains(tag)) count++
        }
        return count
    }
}