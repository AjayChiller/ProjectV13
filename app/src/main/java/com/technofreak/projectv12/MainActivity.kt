package com.technofreak.projectv12

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE = 10
    private val PERMISSIONS_REQUIRED = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    private lateinit var viewModel: MainViewModel

    private lateinit var albumAdapter: RecyclerAdapter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.settemp(this)
        if (!hasPermissions(this)) {
            this.getPermission()

        }
        else{
            initRecyclerView()
            addDataSet()

        }
    }
    private fun addDataSet(){

        val data = viewModel.loadAllImages()
        albumAdapter.submitList(data)
    }

    private fun initRecyclerView(){

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            albumAdapter = RecyclerAdapter()
            adapter = albumAdapter
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    public fun getPermission():Boolean
    {
        if (!hasPermissions(this)) {
            // Request camera-related permissions
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
            return false
        } else {
            // If permissions have already been granted, proceed
            return true
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                // Take the user to the success fragment when permission is granted
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
                // Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(                    R.id.action_permission_Fragment_to_mainActivity)
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


}

