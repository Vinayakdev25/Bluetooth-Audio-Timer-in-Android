package com.example.bluetoothtracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var usageText: TextView
    private lateinit var startServiceBtn: MaterialButton
    private lateinit var historyRv: RecyclerView
    private lateinit var adapter: HistoryAdapter

    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        // no-op; we don't need to handle here strictly
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usageText = findViewById(R.id.usageText)
        startServiceBtn = findViewById(R.id.startServiceBtn)
        historyRv = findViewById(R.id.historyRv)

        adapter = HistoryAdapter()
        historyRv.layoutManager = LinearLayoutManager(this)
        historyRv.adapter = adapter

        startServiceBtn.setOnClickListener {
            val intent = Intent(this, TrackingService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }

        requestPermissionsIfNeeded()
        updateUI()
    }

    private fun requestPermissionsIfNeeded() {
        val perms = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            perms.add(Manifest.permission.BLUETOOTH_CONNECT)
        }
        perms.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissionsLauncher.launch(perms.toTypedArray())
    }

    private fun updateUI() {
        val db = UsageDatabase.getDatabase(this)
        lifecycleScope.launch {
            val today = withContext(Dispatchers.IO) {
                db.usageDao().getTodayUsage() ?: 0L
            }
            usageText.text = "🎧 Today's usage: $today mins"
            val history = withContext(Dispatchers.IO) {
                db.usageDao().getLastNDays(14)
            }
            adapter.submitList(history)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}
