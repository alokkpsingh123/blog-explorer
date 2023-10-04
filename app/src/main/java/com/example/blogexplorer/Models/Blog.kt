package com.example.blogexplorer.Models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Blog(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val id: String,
    val image_url: String?,
    val title: String?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(image_url)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Blog> {
        override fun createFromParcel(parcel: Parcel): Blog {
            return Blog(parcel)
        }

        override fun newArray(size: Int): Array<Blog?> {
            return arrayOfNulls(size)
        }
    }

}


