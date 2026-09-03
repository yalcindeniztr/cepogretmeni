package com.cepogretmeni.tarih.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_plans")
data class LessonPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topicTitle: String,
    val themeName: String, // Maarif Modeli Tema Adı
    val gradeLevel: Int,
    val durationMinutes: Int,
    val primarySkills: String, // JSON serialized List<HistorySkill>
    val coreValues: String,    // JSON serialized List<CoreValue>
    val motivationHook: String, // Güdüleme / Tarihsel fıkra, şiir veya beyit
    val teachingSteps: String,  // Adım adım ders akışı
    val assessmentMethods: String, // Değerlendirme stratejileri
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "self_evaluations")
data class SelfEvaluationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentName: String,
    val studentNumber: String,
    val gradeLevel: Int,
    val topicTitle: String,
    val inquirySkillLevel: Int,      // 1: Geliştirilmeli, 2: Yetkin, 3: İleri Düzey
    val evidenceSkillLevel: Int,
    val chronologySkillLevel: Int,
    val empathySkillLevel: Int,
    val spatialSkillLevel: Int,
    val selectedCoreValues: String,  // Virgülle ayrılmış değerler
    val reflectionNotes: String,     // Öğrenci yansıtıcı düşünme notu
    val totalCompetencyScore: Float, // % hesaplanmış genel yetkinlik skoru
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
