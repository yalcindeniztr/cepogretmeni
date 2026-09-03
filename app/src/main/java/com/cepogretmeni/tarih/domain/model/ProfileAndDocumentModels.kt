package com.cepogretmeni.tarih.domain.model

/**
 * Öğretmen ve Okul Kurumsal Profil Bilgileri
 */
data class TeacherProfile(
    val schoolName: String = "Şehit Mehmet Çetin Fen Lisesi",
    val teacherName: String = "Tarih Öğretmeni",
    val principalName: String = "Okul Müdürü",
    val cityDistrict: String = "Ankara / Çankaya",
    val academicYear: String = "2026-2027",
    val defaultWeeklyHours: Int = 2 // Haftalık Ders Saati Sayısı
)

/**
 * Uygulama İçi Kaydedilen Resmî Belge Modeli
 */
data class SavedDocument(
    val id: Long = 0,
    val title: String,
    val documentType: String, // "GÜNLÜK PLAN", "YILLIK PLAN", "ZÜMRE TUTANAĞI", "SINAV & RUBRİK"
    val gradeLevel: Int,
    val contentJsonOrText: String,
    val createdAtFormatted: String,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

/**
 * Haftalık Ders Saati Kadar Çoklu Bloklu Günlük Ders Planı
 */
data class MultiHourDailyPlan(
    val id: String,
    val gradeLevel: Int,
    val themeName: String,
    val topicTitle: String,
    val weekNumber: Int,
    val weeklyHoursCount: Int = 2, // 2 Ders Saati
    val schoolName: String,
    val teacherName: String,
    val principalName: String,
    val dateRange: String,
    val essentialQuestion: String,
    val lessonHours: List<SingleLessonHourDetail>, // 1. Ders Saati, 2. Ders Saati
    val coreValues: List<CoreValue>,
    val differentiatedInstruction: String,
    val assessmentMethod: String
)

data class SingleLessonHourDetail(
    val hourNumber: Int, // 1. Saat, 2. Saat
    val hourTitle: String,
    val learningOutcomes: String,
    val hookAndMotivation: String, // Giriş / Nükte / Problem
    val instructionalProcess: String, // İstasyon, kaynak analizi, 5E adımları
    val evaluationAndExitTicket: String // Çıkış kartı / Değerlendirme
)
