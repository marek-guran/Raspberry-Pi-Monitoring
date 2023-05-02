package com.marekguran.raspberrypi.ui.hardware

import android.app.AlertDialog
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.marekguran.raspberrypi.databinding.FragmentHardwareBinding
import com.marekguran.raspberrypi.R

class HardwareFragment : Fragment() {

    private var _binding: FragmentHardwareBinding? = null
    private lateinit var database: DatabaseReference

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
        val cpuClockTextView: TextView = binding.cpuClock
        val cpuVoltageTextView: TextView = binding.cpuVoltage
        val gpuClockTextView: TextView = binding.gpuClock
        val gpuTempTextView: TextView = binding.gpuTemp
        val modelTextView: TextView = binding.model

        val modelListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val model = dataSnapshot.getValue(String::class.java)
                if (model != null) {
                    modelTextView.text = model
                } else {
                    modelTextView.text = getString(R.string.model_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                modelTextView.text = getString(R.string.model_error)
            }
        }

        val cpuUsageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cpuUsage = dataSnapshot.getValue(String::class.java)
                if (cpuUsage != null) {
                    cpuUsageTextView.text = getString(R.string.cpu_usage)+ " " + cpuUsage + " %"
                } else {
                    cpuUsageTextView.text = getString(R.string.usage_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuUsageTextView.text = getString(R.string.usage_error)
            }
        }

        val cpuTempListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cpuTemp = dataSnapshot.getValue(String::class.java)
                if (cpuTemp != null) {
                    cpuTempTextView.text = getString(R.string.cpu_temp)+ " " + cpuTemp + " ℃"
                } else {
                    cpuTempTextView.text = getString(R.string.temperature_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuTempTextView.text = getString(R.string.temperature_error)
            }
        }

        val cpuClockListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cpuClock = dataSnapshot.getValue(String::class.java)
                if (cpuClock != null) {
                    cpuClockTextView.text = getString(R.string.cpu_freq)+ " " + cpuClock + " MHz"
                } else {
                    cpuClockTextView.text = getString(R.string.fequency_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuClockTextView.text = getString(R.string.fequency_error)
            }
        }

        val cpuVoltageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cpuVoltage = dataSnapshot.getValue(String::class.java)
                if (cpuVoltage != null) {
                    cpuVoltageTextView.text = getString(R.string.cpu_voltage)+ " " + cpuVoltage + " V"
                } else {
                    cpuVoltageTextView.text = getString(R.string.voltage_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuVoltageTextView.text = getString(R.string.voltage_error)
            }
        }

        val gpuClockListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gpuClock = dataSnapshot.getValue(String::class.java)
                if (gpuClock != null) {
                    gpuClockTextView.text = getString(R.string.gpu_freq)+ " " + gpuClock + " MHz"
                } else {
                    gpuClockTextView.text = getString(R.string.fequency_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                gpuClockTextView.text = getString(R.string.fequency_error)
            }
        }

        val gpuTempListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gpuTemp = dataSnapshot.getValue(String::class.java)
                if (gpuTemp != null) {
                    gpuTempTextView.text = getString(R.string.gpu_temp)+ " " + gpuTemp + " ℃"
                } else {
                    gpuTempTextView.text = getString(R.string.temperature_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cpuUsageTextView.text = getString(R.string.temperature_error)
            }
        }

        val ramUsageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ramUsage = dataSnapshot.getValue(String::class.java)
                if (ramUsage != null) {
                    ramUsageTextView.text = getString(R.string.ram_usage)+ " " + ramUsage + " %"
                } else {
                    ramUsageTextView.text = getString(R.string.usage_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                ramUsageTextView.text = getString(R.string.usage_error)
            }
        }

        val storageUsageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val storageUsage = dataSnapshot.getValue(String::class.java)
                if (storageUsage != null) {
                    storageUsageTextView.text = getString(R.string.free_storage)+ " " + storageUsage + " GB"
                } else {
                    storageUsageTextView.text = getString(R.string.storage_error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                storageUsageTextView.text = getString(R.string.storage_error)
            }
        }

        database.child("cpuusage").addValueEventListener(cpuUsageListener)
        database.child("cputemp").addValueEventListener(cpuTempListener)
        database.child("ramusage").addValueEventListener(ramUsageListener)
        database.child("avaiblestorage").addValueEventListener(storageUsageListener)
        database.child("cpu_clock_freq").addValueEventListener(cpuClockListener)
        database.child("cpu_voltage").addValueEventListener(cpuVoltageListener)
        database.child("gpu_clock_freq").addValueEventListener(gpuClockListener)
        database.child("gpu_temp").addValueEventListener(gpuTempListener)
        database.child("model").addValueEventListener(modelListener)

        binding!!.rebootPi.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
            val inflater: LayoutInflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.reboot_pi, null)
            dialogLayout.setBackgroundResource(android.R.color.transparent)
            builder.setView(dialogLayout)
            builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, i ->
                database = FirebaseDatabase.getInstance().reference.child("management").child("reboot")
                database.setValue("1")
                dialogInterface.dismiss() // Close the Alert Dialog
            }
            builder.setNegativeButton(getString(R.string.no)) { dialogInterface, i ->
                dialogInterface.dismiss() // Close the Alert Dialog
            }
            val alertDialog = builder.show()
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            val textSize = 20f // Set the text size to 20sp
            positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }

        binding!!.shutdownPi.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
            val inflater: LayoutInflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.shutdown_pi, null)
            dialogLayout.setBackgroundResource(android.R.color.transparent)
            builder.setView(dialogLayout)
            builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, i ->
                database = FirebaseDatabase.getInstance().reference.child("management").child("shutdown")
                database.setValue("1")
                dialogInterface.dismiss() // Close the Alert Dialog
            }
            builder.setNegativeButton(getString(R.string.no)) { dialogInterface, i ->
                dialogInterface.dismiss() // Close the Alert Dialog
            }
            val alertDialog = builder.show()
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            val textSize = 20f // Set the text size to 20sp
            positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
