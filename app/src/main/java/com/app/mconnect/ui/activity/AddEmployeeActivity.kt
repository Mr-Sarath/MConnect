package com.app.mconnect.ui.activity

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.mconnect.R
import com.app.mconnect.databinding.ActivityAddEmployeeBinding
import com.app.mconnect.mynetwork.*
import com.app.mconnect.utils.logThis
import com.app.mconnect.utils.shortToast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.tapadoo.alerter.Alerter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class AddEmployeeActivity : AppCompatActivity() {
    private var binding: ActivityAddEmployeeBinding? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        database = getReference("user").child("SHINJITH")

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
            ram = ram,
            storage = storage,
        )
        val gson = Gson()
        val jsonString = gson.toJson(data)
        val qrUri = qrGenerator(jsonString)

        uploadImage("$id-$name-$dip", qrUri).addOnSuccessListener { downloadUri ->
            val dataWithQr = EmployeeDataModel(
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
                ram = ram,
                storage = storage,
                qrUri = downloadUri.toString()
            )

            FirebaseUtils().fireStoreDatabase.collection("Users")
                .add(dataWithQr)
                .addOnSuccessListener {
                    binding?.etId?.text?.clear()
                    shortToast("uploaded")
                    onBackPressed()
                }
                .addOnFailureListener {
                    shortToast("Failed")
                }
        }.addOnFailureListener {
            logThis(it)
        }
    }

    private fun qrGenerator(data: String): Uri {
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            //binding?.ivBack?.setImageBitmap(bmp)
            return saveMediaToStorage(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return Uri.EMPTY
    }

    private fun saveMediaToStorage(bitmap: Bitmap): Uri {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"
        //Output stream
        var fos: OutputStream? = null
        var savedImageUri: Uri = Uri.EMPTY
        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            contentResolver?.also { resolver ->
                //Content resolver will process the contentValues
                val contentValues = ContentValues().apply {
                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                savedImageUri = imageUri ?: Uri.EMPTY
                //Opening an outputStream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            savedImageUri = Uri.fromFile(image)
            fos = FileOutputStream(image)
        }
        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            shortToast("Saved to Gallery")
        }
        return savedImageUri
        /* val bmp: Bitmap = drawTextToBitmap(this,R.drawable.back, "Hello Android")
        img.setImageBitmap(bmp)*/
    }

    private fun uploadImage(imageName: String, imageFileUri: Uri): Task<Uri> {
        val mStorageRef = FirebaseStorage.getInstance().reference
        val uploadTask = mStorageRef.child("SHINJITH/$imageName").putFile(imageFileUri)
        uploadTask.addOnSuccessListener {
            shortToast("image uploaded")
        }.addOnFailureListener {
            shortToast("image uploaded failed")
        }
        return mStorageRef.child("SHINJITH").child(imageName).downloadUrl
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