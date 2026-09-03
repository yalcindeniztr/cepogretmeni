package com.cepogretmeni.tarih.domain.model

/**
 * MEB Ortaöğretim Kurumları Sınıf Geçme Yönetmeliği ve Mevzuat Modelleri
 */
data class MebRegulationGuide(
    val title: String,
    val articleNumber: String,
    val summary: String,
    val practicalAdviceForTeacher: String
)

/**
 * Maarif Modeli Yıllık Ders Planı Haftalık Birim Modeli
 */
data class AnnualPlanWeek(
    val weekNumber: Int,
    val monthName: String,
    val gradeLevel: Int,
    val themeName: String, // Öğrenme Alanı / Tema
    val learningOutcomeCode: String, // Örn: TAR.9.1.1
    val learningOutcomeDescription: String, // Öğrenme Çıktısı
    val skillComponents: String, // Süreç bileşenleri & beceriler
    val coreValues: List<CoreValue>, // Kök Değerler
    val methodsAndTechniques: String, // İstasyon tekniği, kaynak analizi, münazara vb.
    val measurementAndEvaluation: String, // Çıkış kartı, tanılayıcı dallanmış ağaç, rubrik
    val specificWeeksAndDays: String? = null // Belirli Gün ve Haftalar (Örn: 29 Ekim Cumhuriyet Bayramı)
)

/**
 * Maarif Modeli Günlük Ders Planı
 */
data class DailyLessonPlan(
    val id: String,
    val gradeLevel: Int,
    val themeName: String,
    val topicTitle: String,
    val durationMinutes: Int = 40,
    val learningOutcome: String,
    val essentialQuestion: String, // Temel / Kışkırtıcı Soru
    val motivationHook: String, // Güdüleme / Tarihsel fıkra, anekdot, beyit
    val instructionalSteps: List<InstructionalStep>, // 5E veya Adım Adım Ders Akışı
    val coreValuesIntegrated: List<CoreValue>,
    val differentiatedInstructionNotes: String, // Zenginleştirme ve Destekleme (Farklılaştırılmış Eğitim)
    val assessmentMethod: String // Rubrik / Açık uçlu soru / Çıkış kartı
)

data class InstructionalStep(
    val stepTitle: String,
    val durationMinutes: Int,
    val teacherActivity: String,
    val studentActivity: String,
    val pedagogicalGoal: String
)

/**
 * Zümre Öğretmenler Kurulu Kararları ve Tutanağı Modeli
 */
data class ZumreMeetingRecord(
    val schoolYear: String, // Örn: "2026-2027 Eğitim-Öğretim Yılı"
    val term: String, // "1. Dönem Başı / 2. Dönem Başı"
    val meetingDate: String,
    val agendaItems: List<String>, // Gündem Maddeleri
    val decisionsTaken: List<String>, // Alınan Kararlar
    val passFailRegulationDecisions: String, // Sınıf Geçme Yönetmeliği Uyarlamaları
    val measurementEvaluationCriteria: String // Ortak Sınav ve Performans Kriterleri
)

/**
 * MEB Ölçme ve Değerlendirme Senaryolarına Uygun Yazılı Sınav Kağıdı
 */
data class MebExamPaper(
    val examTitle: String, // Örn: "9. Sınıf Tarih Dersi 1. Dönem 1. Ortak Yazılı Sınavı (MEB Senaryo 1)"
    val gradeLevel: Int,
    val totalScore: Int = 100,
    val questions: List<ExamQuestionItem>,
    val rubricScoringKey: List<ExamScoringKeyItem>
)

data class ExamQuestionItem(
    val questionNumber: Int,
    val outcomeCode: String,
    val questionText: String,
    val sourceOrPremise: String?, // Birincil kaynak / Harita / Minyatür
    val pointValue: Int
)

data class ExamScoringKeyItem(
    val questionNumber: Int,
    val expectedAnswer: String,
    val partialScoreCriteria: String,
    val maxScore: Int
)
