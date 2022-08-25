package com.app.mconnect.mynetwork

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.app.mconnect.utils.shortToast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


fun getReference(reference: String) = FirebaseDatabase.getInstance().getReference(reference)


