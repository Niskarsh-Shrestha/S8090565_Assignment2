import android.os.Parcel
import android.os.Parcelable


data class Dish(
    val dishName: String,
    val origin: String,
    val mainIngredient: String,
    val mealType: String,
    val description: String
) : Parcelable {



    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dishName)
        parcel.writeString(origin)
        parcel.writeString(mainIngredient)
        parcel.writeString(mealType)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Dish> = object : Parcelable.Creator<Dish> {
            override fun createFromParcel(parcel: Parcel): Dish {
                return Dish(parcel)
            }

            override fun newArray(size: Int): Array<Dish?> {
                return arrayOfNulls(size)
            }
        }
    }
}
