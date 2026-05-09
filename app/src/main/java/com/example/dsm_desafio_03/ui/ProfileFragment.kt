package com.example.dsm_desafio_03.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dsm_desafio_03.R
import org.json.JSONObject

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val tvRole = view.findViewById<TextView>(R.id.tvRole)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val btnTerminateSession = view.findViewById<LinearLayout>(R.id.btnTerminateSession)

        val sharedPreferences = requireActivity().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)

        if (token != null) {
            try {
                val parts = token.split(".")
                if (parts.size == 3) {
                    val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                    val jsonObject = JSONObject(payload)

                    tvUsername.text = jsonObject.optString("username", "Unknown")
                    tvRole.text = jsonObject.optString("rol", "USER")
                    tvEmail.text = jsonObject.optString("email", "No Email")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle decode error or keep default
            }
        }

        btnTerminateSession.setOnClickListener {
            // Clear token
            sharedPreferences.edit().remove("jwt_token").apply()

            // Navigate back to login
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
