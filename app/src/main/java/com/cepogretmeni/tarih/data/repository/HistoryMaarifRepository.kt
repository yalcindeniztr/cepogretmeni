package com.cepogretmeni.tarih.data.repository

import com.cepogretmeni.tarih.domain.model.CoreValue
import com.cepogretmeni.tarih.domain.model.HistorySkill
import com.cepogretmeni.tarih.domain.model.RubricCriteria
import com.cepogretmeni.tarih.domain.model.SkillBasedQuestion

/**
 * Türkiye Yüzyılı Maarif Modeli Tarih Müfredatı İçerik ve Örnek Havuzu
 */
object HistoryMaarifRepository {

    /**
     * Örnek Maarif Modeli Beceri Temelli Yeni Nesil Soru & Rubrik
     */
    fun getSampleSkillQuestion(): SkillBasedQuestion {
        return SkillBasedQuestion(
            id = "Q-9-01",
            topicTitle = "İlk Türk Devletlerinde Kut Anlayışı ve Adalet (Töre)",
            gradeLevel = 9,
            premiseText = """
                Orhun Abideleri'nde Bilge Kağan şöyle der:
                'Tanrı lütfettiği için, talihim ve kısmetim olduğu için hakan oturdum. Aç milleti doyurdum, çıplak milleti giydirdim. Yoksul milleti zengin kıldım. Az milleti çok kıldım. Dört bucaktaki milleti hep barışık kıldım, bağımlı kıldım.'
                Kutadgu Bilig'de ise Yusuf Has Hacib adaleti şöyle tarif eder:
                'Beyliğin temeli adalet üzerinedir. Bey adalete uyarsa beylik uzun sürer, zulüm ederse beylik tez yıkılır.'
            """.trimIndent(),
            visualSourceUrl = "assets/images/orhun_yazitlari_tonyukuk.png",
            questionText = "Yukarıdaki birincil kaynak metinleri birlikte değerlendirildiğinde, Türk devlet felsefesinde 'egemenliğin kaynağı' ve 'yöneticinin meşruiyetini sürdürme şartı' hakkında aşağıdakilerden hangisi söylenebilir?",
            options = listOf(
                "A) Hükümdarın yetkileri sınırsız olup, töreye uyma zorunluluğu bulunmamaktadır.",
                "B) Egemenliğin ilahi kaynaklı (Kut) olduğuna inanılmakla birlikte, hükümdarın meşruiyeti halkın refahını sağlama ve adaleti uygulama (Sosyal Devlet) şartına bağlanmıştır.",
                "C) Devlet yönetiminde veraset sistemi katı kurallarla belirlenmiş ve taht kavgaları tamamen önlenmiştir.",
                "D) Hükümdarın tek görevi askeri fetihler yapmak olup toplumsal adalet din adamlarının inisiyatifine bırakılmıştır."
            ),
            correctOptionIndex = 1,
            explanationWithHumor = """
                💡 Öğretmen Notu & Tarihsel Nükte:
                Tebrikler genç tarihçi! Doğru cevap B seçeneğidir. 
                Bakınız, eski Türklerde 'Kut' gökten gelse de 'Töre' yere basardı! Hakan 'Ben gökten yetki aldım, canım ne isterse yaparım' diyemezdi; töreye uymayan hakanın kutu elinden alınırdı (Kut'un geri çekilmesi).
                Hani meşhur hazırcevap vezir Tonyukuk'un dediği gibi: 'Taş taş üstünde kalır ama töresiz baş baş üstünde kalmaz!' 
                Devletin bekası, adaletin terazisine emanettir.
            """.trimIndent(),
            relatedSkill = HistorySkill.KANIT_KULLANMA,
            relatedCoreValue = CoreValue.ADALET
        )
    }

    /**
     * Süreç Odaklı Örnek Rubrik Kriterleri Listesi
     */
    fun getSampleRubricCriteria(): List<RubricCriteria> {
        return listOf(
            RubricCriteria(
                id = "R-1",
                skill = HistorySkill.TARIHSEL_SORGULAMA,
                criteriaDescription = "Tarihi metindeki iddiaların tutarlılığını sorgulama ve eleştirel sorular üretebilme.",
                maxScore = 5,
                selectedScore = 4,
                feedbackNotes = "Öğrenci kaynaklar arası çapraz sorgulamayı başarıyla gerçekleştirdi."
            ),
            RubricCriteria(
                id = "R-2",
                skill = HistorySkill.KANIT_KULLANMA,
                criteriaDescription = "Birincil yazılı kaynaklardan (Orhun Kitabeleri) çıkarım yaparak tezini somut delille destekleme.",
                maxScore = 5,
                selectedScore = 5,
                feedbackNotes = "Alıntıları doğrudan argümanına entegre etti."
            ),
            RubricCriteria(
                id = "R-3",
                skill = HistorySkill.TARIHSEL_EMPATI,
                criteriaDescription = "Dönemin konargöçer yaşam tarzını ve zihniyetini günümüz değerleriyle yargılamadan (anakronizm yapmadan) anlama.",
                maxScore = 5,
                selectedScore = 4,
                feedbackNotes = "Anakronizme düşmeden dönemin koşullarını analiz etti."
            )
        )
    }
}
