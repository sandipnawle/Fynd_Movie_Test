package com.sandip.fynd_movie_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sandip.fynd_movie_test.databinding.ActivityMainBinding
import com.sandip.fynd_movie_test.view.HomeFragment
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private  var searchResultShown = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setToolbar("Movies")

        if(savedInstanceState == null) {
            loadHomeFragment()
        }
    }

    private fun setToolbar(title: String) {
        activityMainBinding.toolbar.title = "Movies"
        setSupportActionBar(activityMainBinding.toolbar)
        activityMainBinding.toolbar.searchView.setOnQueryTextListener(this)
        activityMainBinding.toolbar.searchView.queryHint = "Search movie here"
    }

    private fun loadHomeFragment() {
       addOrReplaceFragment(HomeFragment.newInstance(),activityMainBinding.fragmentContainer.id,true,"Home",false)
    }

    private fun addOrReplaceFragment(fragment: Fragment, holder: Int, addToBackStack: Boolean, tagName: String, isReplace: Boolean) {

        val supportFragmentManagerTransaction = supportFragmentManager.beginTransaction()

        supportFragmentManagerTransaction
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )

        if (isReplace)
            supportFragmentManagerTransaction.replace(holder, fragment, tagName)
        else
            supportFragmentManagerTransaction.add(holder, fragment, tagName)

        if (addToBackStack)
            supportFragmentManagerTransaction.addToBackStack(tagName)

        supportFragmentManagerTransaction.commitAllowingStateLoss()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       getHomeFragment()?.searchQuery(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        getHomeFragment()?.searchQuery(newText)
        return false
    }



    override fun onBackPressed() {
        if(!activityMainBinding.toolbar.searchView.isIconified){
            getHomeFragment()?.searchQuery("")
            activityMainBinding.toolbar.searchView.isIconified = true
        }else {
            finish()
        }
    }

    fun getHomeFragment(): HomeFragment? {
        return supportFragmentManager.fragments[0] as? HomeFragment
    }

}