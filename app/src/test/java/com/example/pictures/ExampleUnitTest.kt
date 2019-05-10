package com.example.pictures

import com.example.pictures.managePics.Entry
import com.example.pictures.managePics.MainAdapter
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun mainAdapter_findSimilarEntries_returnsCorrectData(){
        val entries = arrayListOf<Entry>()
        entries.add(Entry("0", "", Date(), arrayListOf("1","2","3","4","5")))
        entries.add(Entry("1", "", Date(), arrayListOf("1")))
        entries.add(Entry("2", "", Date(), arrayListOf("1","2")))
        entries.add(Entry("3", "", Date(), arrayListOf("1","2","6")))
        entries.add(Entry("4", "", Date(), arrayListOf("1","2", "3")))
        entries.add(Entry("5", "", Date(), arrayListOf("1","2", "3", "4", "5")))
        entries.add(Entry("6", "", Date(), arrayListOf("1","2", "3", "4")))
        val resultEntries = arrayListOf<Entry>()
        resultEntries.add(entries[5])
        resultEntries.add(entries[6])
        resultEntries.add(entries[4])
        resultEntries.add(entries[3])
        resultEntries.add(entries[2])
        resultEntries.add(entries[1])

        val adapter = MainAdapter(entries)
        assertEquals(adapter.findSimilarEntries(0,6),resultEntries)
    }

    @Test
    fun mainAdapter_findSimilarEntries_littleData_returnsCorrectData(){
        val entries = arrayListOf<Entry>()
        entries.add(Entry("0", "", Date(), arrayListOf("1","2","3","4","5")))
        entries.add(Entry("1", "", Date(), arrayListOf("1")))
        entries.add(Entry("2", "", Date(), arrayListOf("1","2","3")))
        entries.add(Entry("3", "", Date(), arrayListOf("1","2","6")))

        val resultEntries = arrayListOf<Entry>()

        resultEntries.add(entries[2])
        resultEntries.add(entries[3])
        resultEntries.add(entries[1])

        val adapter = MainAdapter(entries)
        assertEquals(adapter.findSimilarEntries(0,6),resultEntries)
    }
}
