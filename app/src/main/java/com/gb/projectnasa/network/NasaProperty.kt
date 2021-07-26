package com.gb.projectnasa.network

import android.os.Parcel
import android.os.Parcelable


data class NasaProperty(
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val media_type: String?,
    val title: String?,
    val url: String?,
    val hdurl: String?,
    val thumbnail_url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(copyright)
        parcel.writeString(date)
        parcel.writeString(explanation)
        parcel.writeString(media_type)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(hdurl)
        parcel.writeString(thumbnail_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NasaProperty> {
        override fun createFromParcel(parcel: Parcel): NasaProperty {
            return NasaProperty(parcel)
        }

        override fun newArray(size: Int): Array<NasaProperty?> {
            return arrayOfNulls(size)
        }
    }

}