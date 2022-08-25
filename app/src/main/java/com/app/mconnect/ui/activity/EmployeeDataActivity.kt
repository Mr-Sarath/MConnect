package com.app.mconnect.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.app.mconnect.databinding.ActivityEmployeeDataBinding
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.mynetwork.FirebaseUtils
import com.app.mconnect.utils.logThis
import com.app.mconnect.utils.shortToast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects

class EmployeeDataActivity : AppCompatActivity() {
    private var binding: ActivityEmployeeDataBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDataBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initViews()
        handleEvents()
    }

    private fun initViews() {
        val data = intent.getParcelableExtra<EmployeeResponse>("data")
        data?.let {
            with(it){
                binding?.tvName?.text= name
                binding?.tvDip?.text= dip
                binding?.tvEmail?.text= email
                binding?.tvId?.text= id
                binding?.tvMonitorName?.text= monitorName
                binding?.tvMonitorSerialNo?.text= monitorSerialNo
                binding?.tvKeyboardName?.text= keyboardName
                binding?.tvKeyboardSerialNo?.text= keyboardSerialNo
                binding?.tvMouseName?.text= mouseName
                binding?.tvMouseSerialNo?.text= mouseSerialNo
                binding?.tvProcessor?.text=processor
                binding?.tvRam?.text=ram
                binding?.tvStorage?.text=storage
                binding?.ivQr?.load(qrUri)


            }
        }
    }

    private fun handleEvents() {
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
    }
}