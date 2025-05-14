import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8090565assignment2.DetailActivity
import com.example.s8090565assignment2.R

class DishAdapter(private var dishes: List<Dish>, private val onItemClick: (Dish) -> Unit) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    // ViewHolder class to hold references to the UI components of each item
    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishName: TextView = itemView.findViewById(R.id.dishName)
        val origin: TextView = itemView.findViewById(R.id.origin)
    }

    // Create a new ViewHolder when needed (inflate the item layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_dish, parent, false)
        return DishViewHolder(itemView)
    }

    // Inside onBindViewHolder
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        holder.dishName.text = dish.dishName
        holder.origin.text = dish.origin

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("dish", dish) // Make sure Dish implements Parcelable
            holder.itemView.context.startActivity(intent)
        }
    }



    // Return the total number of items in the list
    override fun getItemCount(): Int {
        return dishes.size
    }

    // Update the list of dishes and notify the adapter
    fun updateDishes(newDishes: List<Dish>) {
        dishes = newDishes
        notifyDataSetChanged()
    }
}
