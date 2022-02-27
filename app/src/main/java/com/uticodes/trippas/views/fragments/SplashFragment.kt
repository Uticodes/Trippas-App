package com.uticodes.trippas.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.uticodes.trippas.R
import kotlinx.coroutines.*

class SplashFragment : Fragment(R.layout.fragment_splash) {
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      CoroutineScope(Dispatchers.Main).launch {
         delay(2000)
       findNavController().navigate(R.id.action_splashFragment_to_ListFragment)
      }

   }
}