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
            docRef.set(finalReport).await() // Ensure you have the play-services-coroutines lib or use tasks.await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getOpenReportsFlow(): Flow<List<Report>> = callbackFlow {
        val query = reportsCollection
            .whereIn("status", listOf("pending", "verified"))
            .orderBy("timestamp", Query.Direction.DESCENDING)

        val subscription = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FIRESTORE_ERROR", "Listen failed!", error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                try {
                    val reports = snapshot.toObjects(Report::class.java)
                    trySend(reports)
                } catch (e: Exception) {
                    Log.e("PARSING_ERROR", "Could not map data to Report object", e)
                }
            }
        }

        awaitClose { subscription.remove() }
    }
}