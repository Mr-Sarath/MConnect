package com.app.mconnect.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.app.mconnect.R
import com.app.mconnect.databinding.ActivityAddEmployeeBinding
import com.app.mconnect.mynetwork.EmployeeDataModel
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.mynetwork.FirebaseUtils
import com.app.mconnect.mynetwork.getReference
import com.app.mconnect.utils.shortToast
import com.google.firebase.database.DatabaseReference
import com.tapadoo.alerter.Alerter

class AddEmployeeActivity : AppCompatActivity() {
    private var binding: ActivityAddEmployeeBinding? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        database = getReference("user").push()


        /*   val mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
           val user = FirebaseAuth.getInstance().currentUser
   */
        handleEvents()

    }

    private fun handleEvents() {
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.btnSave?.setOnClickListener {

            validation()
        }

    }

    private fun validation(): Boolean {
        val id = binding?.etId?.text.toString().trim()
        val name = binding?.etName?.text.toString().trim()
        val dip = binding?.etDip?.text.toString().trim()
        val email = binding?.etEmail?.text.toString().trim()
        val monitorName = binding?.etMonitorName?.text.toString().trim()
        val monitorSerialNo = binding?.etMonitorSerialNo?.text.toString().trim()
        val keyboardName = binding?.etKeyboardName?.text.toString().trim()
        val keyboardSerialNo = binding?.etKeyboardSerialNo?.text.toString().trim()
        val mouseName = binding?.etMouseName?.text.toString().trim()
        val mouseSerialNo = binding?.etMouseSerialNo?.text.toString().trim()
        val processor = binding?.etProcessor?.text.toString().trim()
        val ram = binding?.etRam?.text.toString().trim()
        val storage = binding?.etStorage?.text.toString().trim()

        if (id.isNotBlank() && id.length > 1) {
            if (name.isNotBlank()) {
                if (dip.isNotBlank()) {
                    if (email.isNotBlank() && email.length > 4) {
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            if (monitorName.isNotBlank()) {
                                if (monitorSerialNo.isNotBlank()) {
                                    if (keyboardName.isNotBlank()) {
                                        if (keyboardSerialNo.isNotBlank()) {
                                            if (mouseName.isNotBlank()) {
                                                if (mouseSerialNo.isNotBlank()) {
                                                    if (processor.isNotBlank()) {
                                                        if (ram.isNotBlank()) {
                                                            if (storage.isNotBlank()) {

                                                                firebaseApi(
                                                                    id,
                                                                    name,
                                                                    dip,
                                                                    email,
                                                                    monitorName,
                                                                    monitorSerialNo,
                                                                    keyboardName,
                                                                    keyboardSerialNo,
                                                                    mouseName,
                                                                    mouseSerialNo,
                                                                    processor,
                                                                    ram,
                                                                    storage
                                                                )

                                                            } else showAlert(
                                                                "Employee",
                                                                "please enter your Storage",
                                                                "please enter your Storage"
                                                            )
                                                        } else showAlert(
                                                            "Employee",
                                                            "Please enter RAM",
                                                            "Please enter RAM"
                                                        )
                                                    } else showAlert(
                                                        "Employee",
                                                        "Please accept the Terms of Use",
                                                        "Please accept the Terms of Use"
                                                    )
                                                } else showAlert(
                                                    "Employee",
                                                    "Enter mouse serial Number",
                                                    "Enter mouse serial Number"
                                                )
                                            } else showAlert(
                                                "Employee",
                                                "Enter mouse Name",
                                                "Enter mouse Name"
                                            )
                                        } else showAlert(
                                            "Employee",
                                            "Enter Keyboard serial Number",
                                            "Enter Keyboard serial Number"
                                        )
                                    } else showAlert(
                                        "Employee",
                                        "Enter Keyboard Name",
                                        "Enter Keyboard Name"
                                    )
                                } else showAlert(
                                    "Employee",
                                    "Enter Monitor serial Number",
                                    "Enter Monitor serial Number"
                                )
                            } else showAlert("Employee", "Enter Monitor Name", "Enter Monitor Name")
                        } else showAlert(
                            "Employee",
                            "Invalid Email Address",
                            "Invalid Email Address"
                        )
                    } else showAlert("Employee", "Invalid Phone Number !", "Invalid Phone Number !")
                } else showAlert("Employee", "Please Enter your Dip ", "Please Enter your Dip ")
            } else showAlert("Employee", "Please type your Name", "Please type your Name")
        } else showAlert("Employee", "Enter your ID", "Enter your ID")
        return false
    }

    private fun firebaseApi(
        id: String,
        name: String,
        dip: String,
        email: String,
        monitorName: String,
        monitorSerialNo: String,
        keyboardName: String,
        keyboardSerialNo: String,
        mouseName: String,
        mouseSerialNo: String,
        processor: String,
        ram: String,
        storage: String
    ) {

        // create a dummy data
/*        val dataMap = hashMapOf<String, String>(
            "id" to id,
            "name" to name,
            "dip" to dip,
            "email" to email,
            "monitorName" to monitorName,
            "monitorSerialNo" to monitorSerialNo,
            "keyboardName" to keyboardName,
            "keyboardSerialNo" to keyboardSerialNo,
            "mouseName" to mouseName,
            "mouseSerialNo" to mouseSerialNo,
            "processor" to processor,
            "ram" to ram,
            "storage" to storage,
        )*/
        val data = EmployeeDataModel(
            id = id,
            name = name,
            dip = dip,
            email = email,
            monitorName = monitorName,
            monitorSerialNo = monitorSerialNo,
            keyboardName = keyboardName,
            keyboardSerialNo = keyboardSerialNo,
            mouseName = mouseName,
            mouseSerialNo = mouseSerialNo,
            processor = processor,
            ram = ram ,
            storage = storage


        )


        FirebaseUtils().fireStoreDatabase.collection("Users")
            .add(data)
            .addOnSuccessListener {
                binding?.etId?.text?.clear()
                shortToast("uploaded")
                onBackPressed()


            }
            .addOnFailureListener {

                shortToast("Failed")
            }


    }


    private fun showAlert(title: String, msg: String, clickMsg: String) {
        Alerter.create(this@AddEmployeeActivity)
            .setTitle(title)
            .setText(msg)
            .setDuration(1000)
            .setBackgroundColorRes(R.color.skyBlue)
            .setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    this@AddEmployeeActivity,
                    clickMsg,
                    Toast.LENGTH_SHORT
                ).show();
            })
            .show()

    }

}