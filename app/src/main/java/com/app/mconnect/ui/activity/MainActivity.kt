package com.app.mconnect.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mconnect.R
import com.app.mconnect.databinding.ActivityMainBinding
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.mynetwork.FirebaseUtils
import com.app.mconnect.ui.adapter.HomeAdapter
import com.app.mconnect.utils.hide
import com.app.mconnect.utils.logThis
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter

class MainActivity : AppCompatActivity(), HomeAdapter.ClickListener {
    private var binding: ActivityMainBinding? = null
    private var homeAdapter: HomeAdapter? = null
    var userList = mutableListOf<EmployeeResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        FirebaseUtils().fireStoreDatabase.collection("Users").addSnapshotListener { value, error ->
            value?.let {
                userList.addAll(it.toObjects(EmployeeResponse::class.java) as List<EmployeeResponse>)
                setData(userList)
/*
                 binding?.progressW?.hide()
*/
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


    }

    private fun setData(user: List<EmployeeResponse>) {
        homeAdapter = HomeAdapter(user, this)
        binding?.recycleV?.adapter = homeAdapter
        itemClear()
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
}
