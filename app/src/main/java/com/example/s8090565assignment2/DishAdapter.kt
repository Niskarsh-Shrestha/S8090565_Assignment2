import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8090565assignment2.DetailActivity
import com.example.s8090565assignment2.R

class DishAdapter(private var dishes: List<Dish>, private val onItemClick: (Dish) -> Unit) :
    RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val line1: TextView = itemView.findViewById(R.id.dishName)
        val line2: TextView = itemView.findViewById(R.id.origin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_dish, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        val values = dish.fields.entries.toList()
        holder.line1.text = values.getOrNull(0)?.let { "${it.key}: ${it.value}" } ?: "No Data"
        holder.line2.text = values.getOrNull(1)?.let { "${it.key}: ${it.value}" } ?: ""

        holder.itemView.setOnClickListener { onItemClick(dish) }
    }

    override fun getItemCount(): Int = dishes.size

    fun updateDishes(newDishes: List<Dish>) {
        dishes = newDishes
        notifyDataSetChanged()
    }
}
