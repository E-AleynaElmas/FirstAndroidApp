package com.ealeynaelmas.firstandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.DatePicker
import android.widget.TextClock
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var rezervation :Rezervation
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()

    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("ODABAK")

        textview_date = this.CheckInDate

    }

    fun dateListener(text: View){
        var dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(text as TextView)
            }
        }
        DatePickerDialog(
            this@MainActivity,
            dateSetListener,

            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView(textView: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView!!.text = sdf.format(cal.getTime())
    }

    fun loadHotelList(data:String){
        val intent = Intent(applicationContext, HotelListActivity ::class.java)
        intent.putExtra("hotelsData", data)
        startActivity(intent)
    }

    fun loadMainPage(view : View){
        val intent = Intent(applicationContext, MainActivity ::class.java)
        startActivity(intent)
    }

    fun loadHotelLoginPage(view : View){
        val intent = Intent(applicationContext, HotelLogin ::class.java)
        startActivity(intent)
    }

    fun getDataFromDB(view: View) {
        if (!checkAnyEmpty()) {
            setRezervationInfo()
            database.collection("Hotels")
                .whereEqualTo("Sehir", City.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.count() > 0){
                        loadHotelList(City.text.toString())
                    }
                    else {
                        Toast.makeText(
                            applicationContext,
                            "Bu sehire ait otel bulunamadi",
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

    fun checkAnyEmpty(): Boolean{
        return (City.text.isEmpty() || CheckInDate.text.isEmpty() || CheckOutDate.text.isEmpty()
                || NumberOfPerson.text.isEmpty() || NumberOfChild.text.isEmpty())
    }

    fun setRezervationInfo(){
        RezervationData.rezervation = Rezervation()
        RezervationData.rezervation.rcheckindate = CheckInDate.text.toString()
        RezervationData.rezervation.rcheckoutdate = CheckOutDate.text.toString()
        RezervationData.rezervation.rchilcount = NumberOfChild.text.toString().toInt()
        RezervationData.rezervation.rpersoncount = NumberOfPerson.text.toString().toInt()
    }
}