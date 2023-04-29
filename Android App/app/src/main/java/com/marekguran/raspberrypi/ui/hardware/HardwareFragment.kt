package com.marekguran.raspberrypi.ui.hardware

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.marekguran.raspberrypi.databinding.FragmentHardwareBinding

class HardwareFragment : Fragment() {

    private var _binding: FragmentHardwareBinding? = null
    private lateinit var database: DatabaseReference
    private lateinit var cpuUsageListener: ValueEventListener
    private lateinit var cpuTempListener: ValueEventListener
    private lateinit var ramUsageListener: ValueEventListener
    private lateinit var storageUsageListener: ValueEventListener

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHardwareBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase database
        database = FirebaseDatabase.getInstance().reference.child("hw")

        // Set up listeners to update TextViews in real-time
        val cpuUsageTextView: TextView = binding.cpuUsage
        val cpuTempTextView: TextView = binding.cpuTemp
        val ramUsageTextView: TextView = binding.ramUsage
        val storageUsageTextView: TextView = binding.storageUsage

        val cpuUsageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cpuUsage = dataSnapshot.getValue(String::class.java)
                if (cpuUsage != null) {
                    cpuUsageTextView.text = "Využitie: " + cpuUsage + " %"
                } else {
                    cpuUsageTextView.text = "Využitie: chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuUsageTextView.text = "Využitie: chyba"
            }
        }

        val cpuTempListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cpuTemp = dataSnapshot.getValue(String::class.java)
                if (cpuTemp != null) {
                    cpuTempTextView.text = "Teplota: " + cpuTemp + " ℃"
                } else {
                    cpuTempTextView.text = "Teplota: chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuTempTextView.text = "Teplota: chyba"
            }
        }

        val ramUsageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ramUsage = dataSnapshot.getValue(String::class.java)
                if (ramUsage != null) {
                    ramUsageTextView.text = "Využitie: " + ramUsage + " %"
                } else {
                    ramUsageTextView.text = "Využitie: chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                ramUsageTextView.text = "Využitie: chyba"
            }
        }

        val storageUsageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val storageUsage = dataSnapshot.getValue(String::class.java)
                if (storageUsage != null) {
                    storageUsageTextView.text = "Voľné: " + storageUsage + " GB"
                } else {
                    storageUsageTextView.text = "Voľné: chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                storageUsageTextView.text = "Voľné: chyba"
            }
        }

        database.child("cpuusage").addValueEventListener(cpuUsageListener)
        database.child("cputemp").addValueEventListener(cpuTempListener)
        database.child("ramusage").addValueEventListener(ramUsageListener)
        database.child("avaiblestorage").addValueEventListener(storageUsageListener)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove the listeners to avoid memory leaks - crashing
        //database.child("cpuusage").removeEventListener(cpuUsageListener)
        //database.child("cputemp").removeEventListener(cpuTempListener)
        //database.child("ramusage").removeEventListener(ramUsageListener)
        //database.child("avaiblestorage").removeEventListener(storageUsageListener)
        _binding = null
    }
}
