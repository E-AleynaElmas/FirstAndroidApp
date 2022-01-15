package com.ealeynaelmas.firstandroidapp

import java.util.*

class Rezervation {
    var rhotelname:String = ""
    var rhotellicanceno:Long = 0
    var rpersoncount:Int = 0
    var rchilcount:Int = 0
    var rcheckindate:String = ""
    var rcheckoutdate:String = ""
}

class RezervationData {
    companion object {
        lateinit var rezervation:Rezervation
    }
}