package com.ealeynaelmas.firstandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.activity_hotel_login.*
import kotlinx.android.synthetic.main.activity_main.*

class HotelLogin : AppCompatActivity() {
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_login)
    }
    fun setHotelLoginInfo(){
        HotelData.hotelData = Hotel()
        HotelData.hotelData.hdocumentno = documentNoEnter.text.toString().toLong()
        HotelData.hotelData.hlicanceno = licanceNoEnter.text.toString().toLong()
    }

    fun loadRezervationsPage(view: View){
        if (!checkAnyEmpty()) {
            setHotelLoginInfo()
            database.collection("Rezervations")
                .whereEqualTo("OtelRuhsatNo", licanceNoEnter.text.toString().toLong())
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.count() > 0) {
                        loadRezervations(licanceNoEnter.text.toString())
                    }
                    else{
                        Toast.makeText(
                            applicationContext,
                            "Bu otele ait rezervasyon bulunamadı!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        else{
            Toast.makeText(
                applicationContext,
                "Lütfen tüm alanları giriniz!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun loadRezervations(licanceNo:String){
        val intent = Intent(applicationContext, AllRezervations ::class.java)
        intent.putExtra("hotelLicanceNo", licanceNo)
        startActivity(intent)
    }

    fun loadAddHotelPage(){
        val intent = Intent(applicationContext, AddHotel ::class.java)
        startActivity(intent)
    }

    fun checkAnyEmpty(): Boolean{
        return (documentNoEnter.text.isEmpty() || licanceNoEnter.text.isEmpty())
    }

    fun getDataFromDB(view: View) {
        if (!checkAnyEmpty()) {
            setHotelLoginInfo()
            database.collection("Hotels")
                .whereEqualTo("RuhsatNo", licanceNoEnter.text.toString().toLong())
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.count() <= 0){
                        database.collection("Hotels")
                            .whereEqualTo("BelgeNo", documentNoEnter.text.toString().toLong())
                            .get()
                            .addOnSuccessListener { docs ->
                                if(docs.count() <= 0){
                                    loadAddHotelPage()
                                }
                                else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Bu Belge No'ya ait otel zaten var",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }.addOnFailureListener { exception ->
                                Toast.makeText(
                                    applicationContext,
                                    exception.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                    else {
                        Toast.makeText(
                            applicationContext,
                            "Bu Ruhsat No'ya ait otel zaten var",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        else{
            Toast.makeText(
                applicationContext,
                "Lütfen tüm alanları giriniz!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}