package com.marekguran.raspberrypi.ui.pihole

import android.app.AlertDialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marekguran.raspberrypi.R
import com.marekguran.raspberrypi.databinding.FragmentPiholeBinding

class PiholeFragment : Fragment() {

    private var _binding: FragmentPiholeBinding? = null
    private lateinit var database: DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPiholeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance().reference.child("pihole")

        val totalqueriesTextView: TextView = binding!!.totalQueries
        val totalblockedTextView: TextView = binding!!.totalBlocked
        val totalqueriespercentTextView: TextView = binding!!.totalQueriesPercent
        val totaladslistTextView: TextView = binding!!.totalAdsList

        val totalqueriesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dnsqueriestoday = dataSnapshot.getValue(String::class.java)
                if (dnsqueriestoday != null) {
                    totalqueriesTextView.text = dnsqueriestoday
                } else {
                    totalqueriesTextView.text = getString(R.string.error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totalqueriesTextView.text = getString(R.string.error)
            }
        }

        val blockedListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val adsblockedtoday = dataSnapshot.getValue(String::class.java)
                if (adsblockedtoday != null) {
                    totalblockedTextView.text = adsblockedtoday
                } else {
                    totalblockedTextView.text = getString(R.string.error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totalblockedTextView.text = getString(R.string.error)
            }
        }

        val blockedpercentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val adspercentagetoday = dataSnapshot.getValue(String::class.java)
                if (adspercentagetoday != null) {
                    totalqueriespercentTextView.text = adspercentagetoday + " %"
                } else {
                    totalqueriespercentTextView.text = getString(R.string.error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totalqueriespercentTextView.text = getString(R.string.error)
            }
        }

        val domainsblockedListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val domainsbeingblocked = dataSnapshot.getValue(String::class.java)
                if (domainsbeingblocked != null) {
                    totaladslistTextView.text = domainsbeingblocked
                } else {
                    totaladslistTextView.text = getString(R.string.error)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totaladslistTextView.text = getString(R.string.error)
            }
        }

        database.child("dnsqueriestoday").addValueEventListener(totalqueriesListener)
        database.child("adsblockedtoday").addValueEventListener(blockedListener)
        database.child("adspercentagetoday").addValueEventListener(blockedpercentListener)
        database.child("domainsbeingblocked").addValueEventListener(domainsblockedListener)

        binding!!.updateGravity.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
            val inflater: LayoutInflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.update_gravity, null)
            dialogLayout.setBackgroundResource(android.R.color.transparent)
            builder.setView(dialogLayout)
            builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, i ->
                database = FirebaseDatabase.getInstance().reference.child("management").child("update_gravity")
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

        binding!!.restartPihole.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
            val inflater: LayoutInflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.restart_pihole, null)
            dialogLayout.setBackgroundResource(android.R.color.transparent)
            builder.setView(dialogLayout)
            builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, i ->
                database = FirebaseDatabase.getInstance().reference.child("management").child("restart_pihole")
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