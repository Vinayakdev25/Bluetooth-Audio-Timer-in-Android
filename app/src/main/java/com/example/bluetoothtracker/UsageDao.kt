package com.example.bluetoothtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsageDao {
    @Insert
    fun insert(session: UsageSession)

    @Query("""SELECT SUM(minutes) FROM UsageSession 
             WHERE date(timestamp/1000, 'unixepoch') = date('now')""")
    fun getTodayUsage(): Long?

    @Query("""SELECT * FROM UsageSession 
             WHERE date(timestamp/1000, 'unixepoch') >= date('now', '-14 days')
             ORDER BY timestamp DESC""")
    fun getLastNDays(limit: Int = 14): List<UsageSession>
}
