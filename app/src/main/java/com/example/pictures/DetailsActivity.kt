package com.example.pictures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.pictures.fragments.PictureDetailsFragment
import com.example.pictures.fragments.PictureFragment


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val picNameKey = "picNameKey"
        const val picUrlKey = "picUrlKey"
        const val picTagsKey = "picTagsKey"
        const val picDateKey = "picDateKey"
        const val similarPicsUrlKey = "similarPicsUrlKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewPager = findViewById<ViewPager>( R.id.viewPager)
        setupViewPager(viewPager)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = DetailsPageAdapter(supportFragmentManager)

        val pictureBundle = Bundle()
        pictureBundle.putString(picUrlKey, intent.getStringExtra(picUrlKey))
        val pictureFragment = PictureFragment()
        pictureFragment.arguments = pictureBundle

        val detailsBundle = Bundle()
        detailsBundle.putString(picNameKey, intent.getStringExtra(picNameKey))
        detailsBundle.putStringArrayList(picTagsKey, intent.getStringArrayListExtra(picTagsKey))
        detailsBundle.putLong(picDateKey, intent.getLongExtra(picDateKey, 0))
        detailsBundle.putStringArrayList(similarPicsUrlKey, intent.getStringArrayListExtra(similarPicsUrlKey))
        val detailsFragment = PictureDetailsFragment()
        detailsFragment.arguments = detailsBundle


        adapter.addItem(pictureFragment, getString(R.string.picture))
        adapter.addItem(detailsFragment, getString(R.string.details))
        viewPager.adapter = adapter
    }
}

class DetailsPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = arrayListOf<Fragment>()
    private val fragmentTitleList = arrayListOf<String>()

    fun addItem(fragment : Fragment, title : String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }
}