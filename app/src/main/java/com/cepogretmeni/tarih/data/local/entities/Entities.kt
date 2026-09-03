package com.cepogretmeni.tarih.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_plans")
data class LessonPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topicTitle: String,
    val themeName: String,
    val gradeLevel: Int,
    val durationMinutes: Int,
    val primarySkills: String,
    val coreValues: String,
    val motivationHook: String,
    val teachingSteps: String,
    val assessmentMethods: String,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "self_evaluations")
data class SelfEvaluationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentName: String,
    val studentNumber: String,
    val gradeLevel: Int,
    val topicTitle: String,
    val inquirySkillLevel: Int,
    val evidenceSkillLevel: Int,
    val chronologySkillLevel: Int,
    val empathySkillLevel: Int,
    val spatialSkillLevel: Int,
    val selectedCoreValues: String,
    val reflectionNotes: String,
    val totalCompetencyScore: Float,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "teacher_notes")
data class TeacherNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val isEncrypted: Boolean = true,
    val updatedAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_documents")
data class SavedDocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val documentType: String, // "GÜNLÜK DERS PLANI", "YILLIK PLAN", "ZÜMRE TUTANAĞI", "SINAV & RUBRİK"
    val gradeLevel: Int,
    val contentText: String,
    val schoolName: String,
    val teacherName: String,
    val principalName: String,
    val createdAtFormatted: String,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)
