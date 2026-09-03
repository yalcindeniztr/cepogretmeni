package com.cepogretmeni.tarih.domain.model

/**
 * Türkiye Yüzyılı Maarif Modeli - Tarih Dersi Sınıf Düzeyleri ve Temaları
 */
enum class GradeLevel(val grade: Int, val title: String) {
    GRADE_9(9, "9. Sınıf Tarih (Maarif Modeli)"),
    GRADE_10(10, "10. Sınıf Tarih (Maarif Modeli)"),
    GRADE_11(11, "11. Sınıf Tarih (Maarif Modeli)"),
    GRADE_12(12, "12. Sınıf T.C. İnkılap Tarihi ve Atatürkçülük")
}

/**
 * Maarif Modeli Öğrenme Alanı / Tema Tanımı
 */
data class MaarifTheme(
    val id: String,
    val gradeLevel: Int,
    val themeNumber: Int,
    val themeTitle: String,
    val essentialIdea: String, // Temel Fikir / Büyük Fikir
    val targetOutcomes: List<MaarifOutcome>,
    val integratedCoreValues: List<CoreValue>,
    val primarySkills: List<HistorySkill>
)

/**
 * Maarif Modeli Öğrenme Çıktısı (Kazanım) ve Süreç Bileşenleri
 */
data class MaarifOutcome(
    val code: String, // Örn: TAR.9.1.1
    val title: String,
    val conceptualSkills: String, // Kavramsal Beceriler (KB)
    val fieldSkills: String, // Alan Becerileri (AB)
    val tendencies: String, // Eğilimler (EB)
    val coreValueRelationship: String // Erdem-Değer-Eylem ilişkisi
)

/**
 * Hikâyeleştirici ve Nüktedan Ders Anlatım Modülü
 */
data class StorytellingLessonNarrative(
    val themeTitle: String,
    val topicTitle: String,
    val gradeLevel: Int,
    val historicalMindsetAnalysis: String, // Dönemin Zihniyeti (Mantalite)
    val narrativeStory: String, // Canlı ve sürükleyici olay akışı
    val historicalAnecdoteOrHumor: String, // Nasreddin Hoca, İncili Çavuş veya Lider Hazırcevaplığı
    val concludingCoupletOrPoem: String, // Özetleyici beyit / şiir / halk deyişi
    val mindMapSummary: List<String>, // Zihin haritası ana maddeleri
    val criticalThinkingPrompt: String // Öğrenciyi düşündüren kışkırtıcı soru
)
