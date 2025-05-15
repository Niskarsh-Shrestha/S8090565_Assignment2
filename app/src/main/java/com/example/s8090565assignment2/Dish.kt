import android.os.Parcel
import android.os.Parcelable

data class Dish(val fields: Map<String, String>) : Parcelable {

    constructor(parcel: Parcel) : this(
        mutableMapOf<String, String>().apply {
            val size = parcel.readInt()
            repeat(size) {
                val key = parcel.readString() ?: ""
                val value = parcel.readString() ?: ""
                put(key, value)
            }
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(fields.size)
        for ((key, value) in fields) {
            parcel.writeString(key)
            parcel.writeString(value)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Dish> {
        override fun createFromParcel(parcel: Parcel): Dish = Dish(parcel)
        override fun newArray(size: Int): Array<Dish?> = arrayOfNulls(size)
    }
}
