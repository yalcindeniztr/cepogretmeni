package com.cepogretmeni.tarih.domain.model

/**
 * Türkiye Yüzyılı Maarif Modeli - Temel Kök Değerler
 */
enum class CoreValue(val titleTr: String, val iconResName: String, val colorHex: Long) {
    ADALET("Adalet", "ic_justice", 0xFF3F51B5),
    SORUMLULUK("Sorumluluk", "ic_responsibility", 0xFF009688),
    VATANSEVERLIK("Vatanseverlik", "ic_patriotism", 0xFFD32F2F),
    DURUSTLUK("Dürüstlük", "ic_honesty", 0xFF388E3C),
    YARDIMSEVERLIK("Yardımseverlik", "ic_help", 0xFFFF9800),
    SAYGI("Saygı", "ic_respect", 0xFF7B1FA2),
    ESTETIK("Estetik", "ic_aesthetic", 0xFFE91E63)
}

/**
 * Türkiye Yüzyılı Maarif Modeli - Tarih Dersi Alan Becerileri
 */
enum class HistorySkill(val titleTr: String, val descriptionTr: String) {
    TARIHSEL_SORGULAMA(
        "Tarihsel Sorgulama",
        "Geçmişteki olaylar hakkında soru sorma, hipotez kurma ve araştırma yürütme becerisi."
    ),
    KANIT_KULLANMA(
        "Tarihsel Kanıt ve Kaynak Kullanımı",
        "Birincil ve ikincil kaynakları ayırt etme, güvenilirliklerini sorgulama ve delil çıkarma."
    ),
    KRONOLOJIK_DUSUNME(
        "Kronolojik Düşünme ve Değişim-Süreklilik",
        "Zaman dizilimini anlama, dönemsel kırılma ve devamlılıkları analiz edebilme."
    ),
    TARIHSEL_EMPATI(
        "Tarihsel Empati ve Zihniyet",
        "Tarihi şahsiyetlerin kararlarını dönemin şartları, inançları ve zihniyeti çerçevesinde anlama."
    ),
    MEKAN_ALGILAMA(
        "Tarihsel Mekânı Algılama ve Harita Okuryazarlığı",
        "Olayların geçtiği coğrafi koşulların tarihi gelişim üzerindeki etkisini kavrama."
    )
}

/**
 * Öğrencinin beceri düzeyini belirleme derecesi
 */
enum class CompetencyLevel(val levelScore: Int, val titleTr: String, val badgeColorHex: Long) {
    GELISTIRILMELI(1, "Geliştirilmeli", 0xFFE57373),
    YETKIN(2, "Yetkin", 0xFF64B5F6),
    ILERI_DUZEY(3, "İleri Düzey", 0xFF81C784)
}

/**
 * Süreç Odaklı Rubrik Kriteri
 */
data class RubricCriteria(
    val id: String,
    val skill: HistorySkill,
    val criteriaDescription: String,
    val maxScore: Int = 5,
    val selectedScore: Int = 0,
    val feedbackNotes: String = ""
)

/**
 * Beceri Temelli Maarif Modeli Soru Formatı
 */
data class SkillBasedQuestion(
    val id: String,
    val topicTitle: String,
    val gradeLevel: Int, // 9, 10, 11, 12
    val premiseText: String, // Öncül metin / Kaynak alıntısı
    val visualSourceUrl: String? = null, // Harita / Minyatür / Belge görseli
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanationWithHumor: String, // Tarihsel nükte veya beyit içeren pedagojik çözüm
    val relatedSkill: HistorySkill,
    val relatedCoreValue: CoreValue
)
