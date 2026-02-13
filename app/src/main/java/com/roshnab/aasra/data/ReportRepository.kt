package com.roshnab.aasra.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object ReportRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reportsCollection = db.collection("reports")

    suspend fun submitReport(report: Report): Boolean {
        return try {
            val docRef = reportsCollection.document()
            val finalReport = report.copy(reportId = docRef.id)
            docRef.set(finalReport).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getOpenReportsFlow(): Flow<List<Report>> = callbackFlow {
        val query = reportsCollection.limit(50)

        val subscription = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FIRESTORE_DEBUG", "Still failing: ${error.message}", error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val reports = snapshot.toObjects(Report::class.java)
                Log.d("FIRESTORE_DEBUG", "Success! Found ${reports.size} reports.")
                trySend(reports)
            } else {
                Log.d("FIRESTORE_DEBUG", "Snapshot was null")
            }
        }

        awaitClose { subscription.remove() }
    }
}