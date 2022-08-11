package com.app.mconnect.mynetwork

 import com.google.firebase.database.FirebaseDatabase


fun getReference(reference: String) =  FirebaseDatabase.getInstance().getReference(reference)

