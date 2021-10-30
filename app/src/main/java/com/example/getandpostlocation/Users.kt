package com.example.getandpostlocation

import com.google.gson.annotations.SerializedName

class Users {

    @SerializedName("name")
    var name : String? = null

    @SerializedName("location")
    var location : String?= null

    constructor(name: String , location: String){
        this.name = name
        this.location = location
    }
}