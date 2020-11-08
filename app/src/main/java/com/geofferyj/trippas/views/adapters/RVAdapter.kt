package com.geofferyj.trippas.views.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geofferyj.trippas.R
import com.geofferyj.trippas.models.Trip


class RVAdapter(private val trips: List<Trip>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RVAdapter.RVViewHolder>() {


    inner class RVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val rvMenu: ImageView = itemView.findViewById(R.id.rv_menu)
        val dest: TextView = itemView.findViewById(R.id.rv_destination)
        val destDate: TextView = itemView.findViewById(R.id.rv_destination_date)
        val destTime: TextView = itemView.findViewById(R.id.rv_destination_time)
        val dep: TextView = itemView.findViewById(R.id.rv_departure)
        val depDate: TextView = itemView.findViewById(R.id.rv_departure_date)
        val depTime: TextView = itemView.findViewById(R.id.rv_departure_time)
        val tripType: TextView = itemView.findViewById(R.id.rv_trip_type)

        init {
            rvMenu.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION){
                listener.showMenu(rvMenu, trips[adapterPosition])
            }
        }

    }

    interface OnItemClickListener {
        fun showMenu(view: View, trip: Trip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val l = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return RVViewHolder(l)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val trip = trips[position]
        holder.apply {
            dest.text = trip.destination
            destDate.text = trip.destination_date
            destTime.text = trip.destination_time
            dep.text = trip.departure
            depDate.text = trip.departure_date
            depTime.text = trip.departure_time

            tripType.apply {
                text = trip.trip_type

                backgroundTintList = when (text.toString()) {
                    "Business" -> ContextCompat.getColorStateList(this.context, R.color.business)
                    "Vacation" -> ContextCompat.getColorStateList(this.context, R.color.vacation)
                    "Education" -> ContextCompat.getColorStateList(this.context, R.color.education)
                    "Medical" -> ContextCompat.getColorStateList(this.context, R.color.medical)
                    else -> ContextCompat.getColorStateList(this.context, R.color.colorGreyText)

                }
            }

        }

    }

    override fun getItemCount(): Int {
        return trips.size
    }
}