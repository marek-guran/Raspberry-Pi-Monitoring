package com.marekguran.raspberrypi.ui.pihole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        val totalqueriesTextView: TextView = binding.totalQueries
        val totalblockedTextView: TextView = binding.totalBlocked
        val totalqueriespercentTextView: TextView = binding.totalQueriesPercent
        val totaladslistTextView: TextView = binding.totalAdsList

        val totalqueriesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dnsqueriestoday = dataSnapshot.getValue(String::class.java)
                if (dnsqueriestoday != null) {
                    totalqueriesTextView.text = dnsqueriestoday
                } else {
                    totalqueriesTextView.text = "chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totalqueriesTextView.text = "chyba"
            }
        }

        val blockedListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val adsblockedtoday = dataSnapshot.getValue(String::class.java)
                if (adsblockedtoday != null) {
                    totalblockedTextView.text = adsblockedtoday
                } else {
                    totalblockedTextView.text = "chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totalblockedTextView.text = "chyba"
            }
        }

        val blockedpercentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val adspercentagetoday = dataSnapshot.getValue(String::class.java)
                if (adspercentagetoday != null) {
                    totalqueriespercentTextView.text = adspercentagetoday + " %"
                } else {
                    totalqueriespercentTextView.text = "chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totalqueriespercentTextView.text = "chyba"
            }
        }

        val domainsblockedListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val domainsbeingblocked = dataSnapshot.getValue(String::class.java)
                if (domainsbeingblocked != null) {
                    totaladslistTextView.text = domainsbeingblocked
                } else {
                    totaladslistTextView.text = "chyba"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                totaladslistTextView.text = "chyba"
            }
        }

        database.child("dnsqueriestoday").addValueEventListener(totalqueriesListener)
        database.child("adsblockedtoday").addValueEventListener(blockedListener)
        database.child("adspercentagetoday").addValueEventListener(blockedpercentListener)
        database.child("domainsbeingblocked").addValueEventListener(domainsblockedListener)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}