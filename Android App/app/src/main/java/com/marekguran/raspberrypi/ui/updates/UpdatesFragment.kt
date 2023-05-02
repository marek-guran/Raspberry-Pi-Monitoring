package com.marekguran.raspberrypi.ui.updates

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marekguran.raspberrypi.R
import com.marekguran.raspberrypi.databinding.FragmentUpdatesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class UpdatesFragment : Fragment() {

    private var _binding: FragmentUpdatesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Fetch the latest release of the repository
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://api.github.com/repos/marek-guran/Raspberry-Pi-Monitoring/releases/latest")
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()

            var inputLine: String?
            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }

            bufferedReader.close()
            inputStream.close()
            connection.disconnect()

            // Parse the response and display the release details
            val jsonObject = JSONObject(response.toString())
            val name = jsonObject.getString("name")
            val publishedAt = jsonObject.getString("published_at")
            val htmlUrl = jsonObject.getString("html_url")
            val body = jsonObject.getString("body")

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("CET")
            val date = sdf.parse(publishedAt)
            sdf.timeZone = TimeZone.getDefault()

            val dateString = sdf.format(date)
            val message = "$name released on $dateString:\n$body"

            GlobalScope.launch(Dispatchers.Main) {
                binding.releaseNameTextView.text = name
                binding.releaseDateTimeTextView.text = dateString
                binding.changelogTextView.text = body
                binding.openLinkButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))
                    startActivity(intent)
                }
            }

            // Get the app version and display it
            val packageManager = requireActivity().packageManager
            val packageName = requireActivity().packageName
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val appVersion = packageInfo.versionName
            binding.appVersion.text = getString(R.string.version) + " " + appVersion
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
