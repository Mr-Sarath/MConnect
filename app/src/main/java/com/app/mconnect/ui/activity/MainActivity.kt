package com.app.mconnect.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.mconnect.R
import com.app.mconnect.databinding.ActivityMainBinding
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.mynetwork.FirebaseUtils
import com.app.mconnect.ui.adapter.HomeAdapter
import com.app.mconnect.utils.logThis
import com.tapadoo.alerter.Alerter

class MainActivity : AppCompatActivity(), HomeAdapter.ClickListener {
    private var binding: ActivityMainBinding? = null
    private var homeAdapter: HomeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        FirebaseUtils().fireStoreDatabase.collection("Users").addSnapshotListener { value, error ->
            value?.let {
                val user = it.toObjects(EmployeeResponse::class.java) as List<EmployeeResponse>
                setData(user)
                binding?.progressW?.visibility = View.GONE
            }

/*
            logThis("monitor name   " + user[0].monitorName)
*/
        }

        handleEvents()
    }

    private fun handleEvents() {
/*
        binding?.recycleV?.layoutManager =StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
*/
        binding?.recycleV?.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        binding?.btnAdd?.setOnClickListener {
            val i = Intent(this@MainActivity, AddEmployeeActivity::class.java)
            startActivity(i)
        }
        binding?.ivQr?.setOnClickListener {
            showAlert("QR", "On Progress", "Qr code on progress")
        }

    }

    private fun setData(user: List<EmployeeResponse>) {
        homeAdapter = HomeAdapter(user, this)
        binding?.recycleV?.adapter = homeAdapter
    }

    override fun onClick(employeeResponse: EmployeeResponse) {
        val i = Intent(this@MainActivity, EmployeeDataActivity::class.java)
        i.putExtra("data", employeeResponse)
        startActivity(i)
    }

    private fun showAlert(title: String, msg: String, clickMsg: String) {
        Alerter.create(this@MainActivity)
            .setTitle(title)
            .setText(msg)
            .setDuration(1000)
            .setBackgroundColorRes(R.color.skyBlue)
            .setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    clickMsg,
                    Toast.LENGTH_SHORT
                ).show();
            })
            .show()

    }
}