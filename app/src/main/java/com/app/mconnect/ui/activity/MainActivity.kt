package com.app.mconnect.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mconnect.R
import com.app.mconnect.databinding.ActivityMainBinding
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.mynetwork.FirebaseUtils
import com.app.mconnect.ui.adapter.HomeAdapter
import com.app.mconnect.ui.fragment.QrBottomSheet
import com.app.mconnect.utils.shortToast
import com.tapadoo.alerter.Alerter
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanCustomCode
import io.github.g00fy2.quickie.config.ScannerConfig

class MainActivity : AppCompatActivity(), HomeAdapter.ClickListener {
    private var binding: ActivityMainBinding? = null
    private var homeAdapter: HomeAdapter? = null
    private var qrBottomSheet:QrBottomSheet?=null

    private val scanCustomCode = registerForActivityResult(ScanCustomCode(), ::handleResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        FirebaseUtils().fireStoreDatabase.collection("Users").addSnapshotListener { value, error ->
            value?.let {
                val data = it.toObjects(EmployeeResponse::class.java) as List<EmployeeResponse>
                setData(data)
/*
                 binding?.progressW?.hide()
*/
            }


        }

        handleEvents()
    }

    private fun handleEvents() {

        binding?.recycleV?.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        binding?.btnAdd?.setOnClickListener {
            val i = Intent(this@MainActivity, AddEmployeeActivity::class.java)
            startActivity(i)
        }

        binding?.cvKeyboard?.setOnClickListener {
            val i = Intent(this@MainActivity, KeyboardActivity::class.java)
            startActivity(i)
        }

        binding?.cvComputer?.setOnClickListener {
            val i = Intent(this@MainActivity, ComputerActivity::class.java)
            startActivity(i)
        }

        binding?.cvMouse?.setOnClickListener {
            val i = Intent(this@MainActivity, MouseActivity::class.java)
            startActivity(i)
        }
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.ivQr?.setOnClickListener {

            scanCustomCode.launch(
                ScannerConfig.build {
                    //setBarcodeFormats(listOf(BarcodeFormat.FORMAT_CODE_128)) // set interested barcode formats
                    setOverlayStringRes(R.string.scan_barcode) // string resource used for the scanner overlay
                    setOverlayDrawableRes(R.drawable.ic_scan_bar_code) // drawable resource used for the scanner overlay
                    setHapticSuccessFeedback(true) // enable (default) or disable haptic feedback when a barcode was detected
                    setShowTorchToggle(true) // show or hide (default) torch/flashlight toggle button
                    setHorizontalFrameRatio(0f) // set the horizontal overlay ratio (default is 1 / square frame)

                }
            )

        }
      /*  binding?.ivQr1?.setOnClickListener {

            QrBottomSheet().show(supportFragmentManager, "qrBottomSheet")
        }*/
    }

    private fun setData(user: List<EmployeeResponse>) {
        homeAdapter = HomeAdapter(user, this)
        binding?.recycleV?.adapter = homeAdapter
/*
        itemClear()
*/
    }

    override fun onClick(employeeResponse: EmployeeResponse) {
        val i = Intent(this@MainActivity, EmployeeDataActivity::class.java)
        i.putExtra("data", employeeResponse)
        startActivity(i)
    }


    /*
    private fun itemClear() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedCourse: EmployeeResponse =
                    userList[viewHolder.adapterPosition]

                // below line is to get the position
                // of the item at that position.

                val deletePos = viewHolder.adapterPosition
                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                userList.removeAt(deletePos)

                // below line is to notify our item is removed from adapter.
                homeAdapter?.notifyItemRemoved(deletePos)

                Snackbar.make(
                    binding?.root!!,
                    "Deleted " + deletedCourse.name,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            userList.add(deletePos, deletedCourse)
                            // below line is to notify item is
                            // added to our adapter class.
                            homeAdapter?.notifyItemInserted(deletePos)
                        }).show()
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(binding?.recycleV)

    }
*/



    private var back_pressed = 0L
    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            shortToast("Press Again to Exit from  App")
        }
        back_pressed = System.currentTimeMillis()
    }


    private fun handleResult(result: QRResult) {
        when (result) {
            is QRResult.QRSuccess -> {
                shortToast(result.content.rawValue)
            }
            is QRResult.QRError -> {
                shortToast(result.exception.message)
            }
            is QRResult.QRMissingPermission -> {
                shortToast("User has permitted")

            }
            is QRResult.QRUserCanceled -> {
                shortToast("User has cancelled")

            }
        }
    }

}

