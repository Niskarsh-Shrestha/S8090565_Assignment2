import android.os.Parcel
import android.os.Parcelable

// Data class representing a Dish, which is a collection of key-value string pairs
data class Dish(val fields: Map<String, String>) : Parcelable {

    // Constructor used to recreate a Dish object from a Parcel
    constructor(parcel: Parcel) : this(
        mutableMapOf<String, String>().apply {
            val size = parcel.readInt()  // Read number of entries
            repeat(size) {
                val key = parcel.readString() ?: ""
                val value = parcel.readString() ?: ""
                put(key, value)
            }
        }
    )

    // Writes the Dish data to a Parcel for passing between activities
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(fields.size)  // Write number of entries
        for ((key, value) in fields) {
            parcel.writeString(key)
            parcel.writeString(value)
        }
    }

    // Describes the contents (required for Parcelable, usually returns 0)
    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Dish> {
        // Creates a Dish instance from a Parcel
        override fun createFromParcel(parcel: Parcel): Dish = Dish(parcel)

        // Creates an array of nullable Dish objects
        override fun newArray(size: Int): Array<Dish?> = arrayOfNulls(size)
    }
}
