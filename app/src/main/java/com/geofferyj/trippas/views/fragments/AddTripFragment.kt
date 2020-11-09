package com.geofferyj.trippas.views.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geofferyj.trippas.R
import com.geofferyj.trippas.models.Trip
import com.geofferyj.trippas.viewmodels.TripViewModel
import kotlinx.android.synthetic.main.fragment_add_trip.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class AddTripFragment : Fragment(R.layout.fragment_add_trip) {
    private val args by navArgs<AddTripFragmentArgs>()

    private lateinit var tripsViewModel: TripViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.tripInfo != null) {
            departure.text = args.tripInfo?.departure?.toEditable()
            departure_date.text = args.tripInfo?.departure_date?.toEditable()
            departure_time.text = args.tripInfo?.departure_time?.toEditable()
            destination.text = args.tripInfo?.destination?.toEditable()
            destination_date.text = args.tripInfo?.destination_date?.toEditable()
            destination_time.text = args.tripInfo?.destination_time?.toEditable()
            tv_trip_type.text = args.tripInfo?.trip_type ?: "Trip Type"

            btn_add_trip.text = "UPDATE TRIP"
        }




        tripsViewModel = ViewModelProvider(this).get(TripViewModel::class.java)


        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        trip_type_selector.setOnClickListener {
            showMenu(iv_arrow)
        }

        btn_add_trip.setOnClickListener {
            val button = it as Button
            if (button.text.toString() == "UPDATE TRIP") {
                addTrip()
            } else {

                addTrip()
            }
        }

        departure.setOnFocusChangeListener { v, hasFocus ->
            setTitleCase(v, hasFocus)
        }

        destination.setOnFocusChangeListener { v, hasFocus ->
            setTitleCase(v, hasFocus)
        }

        departure_date.setOnClickListener {
            selectDate(it)
        }

        destination_date.setOnClickListener {
            selectDate(it)
        }

        departure_time.setOnClickListener {
            selectTime(it)
        }

        destination_time.setOnClickListener {
            selectTime(it)
        }


    }

    private fun selectDate(v: View) {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, dayOfMonth ->
                val formattedDate = formatDate(mYear, mMonth, dayOfMonth)

                (v as EditText).text = formattedDate.toEditable()
            },
            year,
            month,
            day
        ).show()
    }

    private fun selectTime(v: View) {
        val c = Calendar.getInstance()

        val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        val minOfDay = c.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hour, min ->
                val am_pm = if (hour >= 12) "PM" else "AM"
                val time =
                    if (hour == 12 || hour == 0) "12 : $min $am_pm" else "${hour % 12} : $min $am_pm"
                (v as EditText).text = time.toEditable()
            },
            hourOfDay,
            minOfDay,
            false
        ).show()
    }

    private fun addTrip() {
        val dest = destination.text.toString()
        val destDate = destination_date.text.toString()
        val destTime = destination_time.text.toString()
        val dep = departure.text.toString()
        val depDate = departure_date.text.toString()
        val depTime = departure_time.text.toString()
        val tripType = tv_trip_type.text.toString()

        val isValid: Boolean =
            dest.isNotEmpty() && destDate.isNotEmpty() && destTime.isNotEmpty() && dep.isNotEmpty() && depDate.isNotEmpty() && depTime.isNotEmpty()
        if (isValid) {

            if (args.tripInfo != null) {

                val trip = Trip(
                    args.tripInfo!!.id,
                    dep,
                    depDate,
                    depTime,
                    dest,
                    destDate,
                    destTime,
                    tripType
                )
                tripsViewModel.updateTrip(trip)
                Toast.makeText(context, "Trip Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_AddTripFragment_to_ListFragment)

            } else {

                val trip = Trip(0, dep, depDate, depTime, dest, destDate, destTime, tripType)
                tripsViewModel.addTrip(trip)
                Toast.makeText(context, "Trip Added Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_AddTripFragment_to_ListFragment)

            }

        } else {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showMenu(view: View) {

        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.add_trip_menu)
        popupMenu.gravity = Gravity.CENTER

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.business -> {
                    tv_trip_type.text = it.title
                }
                R.id.education -> {
                    tv_trip_type.text = it.title
                }
                R.id.medical -> {
                    tv_trip_type.text = it.title
                }
                R.id.vacation -> {
                    tv_trip_type.text = it.title
                }
            }
            true
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun formatDate(year: Int, month: Int, day: Int): String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDate.of(year, month, day)
            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
            val answer: String = date.format(formatter)
            Log.d("answer", answer)
            answer
        } else {

            var date = Date(year - 1900, month, day)
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            val answer: String = formatter.format(date)
            Log.d("answer", answer)
            Log.d("answerYear", year.toString())
            answer

        }
    }

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.toLowerCase().capitalize() }


    private fun setTitleCase(v: View, hasFocus: Boolean) {
        val editText = v as EditText
        if (!hasFocus) {
            editText.text =  editText.text.toString().capitalizeWords().toEditable()
        }
    }
}