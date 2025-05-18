import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8090565assignment2.R

// Adapter to bind a list of Dish objects to a RecyclerView
class DishAdapter(
    private var dishes: List<Dish>,
    private val onItemClick: (Dish) -> Unit // Lambda function to handle item click
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    // ViewHolder class holds references to the views for each list item
    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val line1: TextView = itemView.findViewById(R.id.dishName)   // First line of text (e.g. dish name)
        val line2: TextView = itemView.findViewById(R.id.origin)     // Second line of text (e.g. origin)
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_dish, parent, false)
        return DishViewHolder(view)
    }

    // Binds data from a Dish object to the views in the ViewHolder
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        val values = dish.fields.entries.toList()

        // Safely display the first two fields of the dish, if available
        holder.line1.text = values.getOrNull(0)?.let { "${it.key}: ${it.value}" } ?: "No Data"
        holder.line2.text = values.getOrNull(1)?.let { "${it.key}: ${it.value}" } ?: ""

        // Set up click listener to trigger the provided callback with the clicked dish
        holder.itemView.setOnClickListener { onItemClick(dish) }
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int = dishes.size

    // Updates the list of dishes and refreshes the RecyclerView
    fun updateDishes(newDishes: List<Dish>) {
        dishes = newDishes
        notifyDataSetChanged()
    }
}
