package br.edu.ifsp.scl.ads.prdm.sc3039439.contactlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var id: Int? = -1,
    var name: String = "",
    var adress: String = "",
    var phone: String = "",
    var email: String = ""
): Parcelable
