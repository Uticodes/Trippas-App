package com.geofferyj.trippas.views.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.geofferyj.trippas.R
import com.geofferyj.trippas.views.adapters.RVAdapter
import com.geofferyj.trippas.models.Trip
import com.geofferyj.trippas.views.fragments.TripListFragmentDirections
import com.geofferyj.trippas.viewmodels.TripViewModel
import kotlinx.android.synthetic.main.fragment_trip_list.*

class TripListFragment : Fragment(R.layout.fragment_trip_list), RVAdapter.OnItemClickListener {

    private lateinit var tripViewModel: TripViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_AddTripFragment)
        }

        tripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        tripViewModel.getTrips.observe(viewLifecycleOwner, Observer {

            val adapter = RVAdapter(it, this)
            adapter.notifyDataSetChanged()
            rv_trips.adapter = adapter


            trip_count.text = if (it.size == 1) "${it.size.toString()} Trip" else "${it.size.toString()} Trips"

        })

    }



    override fun showMenu(view: View, trip: Trip) {

        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.rv_menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    tripViewModel.deleteTrip(trip)
                }
                R.id.update -> {
                    val action =
                        TripListFragmentDirections.actionListFragmentToAddTripFragment(trip)
                    view.findNavController().navigate(action)
                }
            }
            true
        }
    }
}
