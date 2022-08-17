package com.app.mconnect.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mconnect.databinding.ActivityKeyboardBinding
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.mynetwork.FirebaseUtils
import com.app.mconnect.ui.adapter.HomeAdapter
import com.app.mconnect.ui.adapter.KeyboardAdapter

class KeyboardActivity : AppCompatActivity() {
    private var binding_: ActivityKeyboardBinding? = null
    private var keyboardAdapter: KeyboardAdapter? = null

    var userList = mutableListOf<EmployeeResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding_?.root)
        init()
    }

    private fun init() {
        FirebaseUtils().fireStoreDatabase.collection("Users").addSnapshotListener { value, error ->
            value?.let {
                userList.addAll(it.toObjects(EmployeeResponse::class.java) as List<EmployeeResponse>)
                setData(userList)

                binding_?.progressW?.visibility = View.GONE
            }
        }
        handleEvents()

    }

    private fun handleEvents() {
        binding_?.recycleV?.layoutManager =
            GridLayoutManager(this, 2)
        setData(userList)
    }

    private fun setData(user: List<EmployeeResponse>) {
        keyboardAdapter = KeyboardAdapter(user, object : KeyboardAdapter.ClickListener {
            override fun onClick(employeeResponse: EmployeeResponse) {
                Toast.makeText(
                    this@KeyboardActivity,
                    employeeResponse.keyboardSerialNo,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        binding_?.recycleV?.adapter = keyboardAdapter
    }

}