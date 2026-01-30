package com.roshnab.aasra.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

object ReportRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reportsCollection = db.collection("reports")

    suspend fun submitReport(report: Report): Boolean {
        return try {
            val docRef = reportsCollection.document() // Auto-ID
            val finalReport = report.copy(reportId = docRef.id)
            docRef.set(finalReport).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun fetchOpenReports(): List<Report> {
        return try {
            val snapshot = reportsCollection
                .whereIn("status", listOf("pending", "verified"))
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(Report::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}