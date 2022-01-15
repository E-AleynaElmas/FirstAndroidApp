package com.ealeynaelmas.firstandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.hotel_list.*
import kotlinx.android.synthetic.main.hotelview.view.*
import kotlinx.android.synthetic.main.rezervationview.view.*

class AllRezervations : AppCompatActivity() {
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_rezervations)

        val intentGetter = intent
        val data = intentGetter.getStringExtra("hotelLicanceNo")
        createRezervationGroup(data!!)
    }

    fun createRezervationGroup(licanceNo:String){
        database.collection("Rezervations")
            .whereEqualTo("OtelRuhsatNo", licanceNo.toLong())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    var view: View = LayoutInflater.from(this).inflate(R.layout.rezervationview, null);
                    myLayout2.addView(view);
                    view.rNameSurname.text = document.data["IrtibatIsim"].toString() + " " + document.data["IrtibatSoyad"].toString()
                    view.rCheckInDate.text = document.data["GirisTarih"].toString()
                    view.rCheckOutDate.text = document.data["CikisTarih"].toString()
                    view.rPersonNumber.text = document.data["KisiSayisi"].toString()
                    view.rbutton.setOnClickListener{
                        loadRezervation(document.id)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "Bu ruhsat No'ya ait rezervation bulunamadı", Toast.LENGTH_LONG).show()
            }
    }

    fun loadRezervation(id:String){
        val intent = Intent(applicationContext, ShowRezervation ::class.java)
        intent.putExtra("RezervationId", id)
        startActivity(intent)
    }
}