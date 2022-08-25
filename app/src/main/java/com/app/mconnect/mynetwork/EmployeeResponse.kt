package com.app.mconnect.mynetwork

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class EmployeeResponse(

	@field:SerializedName("mouseName")
	val mouseName: String? = null,

	@field:SerializedName("keyboardName")
	val keyboardName: String? = null,

	@field:SerializedName("keyboardSerialNo")
	val keyboardSerialNo: String? = null,

	@field:SerializedName("monitorName")
	val monitorName: String? = null,

	@field:SerializedName("monitorSerialNo")
	val monitorSerialNo: String? = null,

	@field:SerializedName("dip")
	val dip: String? = null,

	@field:SerializedName("storage")
	val storage: String? = null,

	@field:SerializedName("processor")
	val processor: String? = null,

	@field:SerializedName("mouseSerialNo")
	val mouseSerialNo: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("ram")
	val ram: String? = null,

	@field:SerializedName("qrUri")
	val qrUri: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
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
		parcel.writeString(mouseName)
		parcel.writeString(keyboardName)
		parcel.writeString(keyboardSerialNo)
		parcel.writeString(monitorName)
		parcel.writeString(monitorSerialNo)
		parcel.writeString(dip)
		parcel.writeString(storage)
		parcel.writeString(processor)
		parcel.writeString(mouseSerialNo)
		parcel.writeString(name)
		parcel.writeString(id)
		parcel.writeString(email)
		parcel.writeString(ram)
		parcel.writeString(qrUri)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<EmployeeResponse> {
		override fun createFromParcel(parcel: Parcel): EmployeeResponse {
			return EmployeeResponse(parcel)
		}

		override fun newArray(size: Int): Array<EmployeeResponse?> {
			return arrayOfNulls(size)
		}
	}
}