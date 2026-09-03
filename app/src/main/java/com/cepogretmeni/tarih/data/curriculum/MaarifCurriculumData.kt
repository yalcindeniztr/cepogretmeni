package com.cepogretmeni.tarih.data.curriculum

import com.cepogretmeni.tarih.domain.model.*

/**
 * MEB Maarif Modeli Tarih Dersi Müfredat Havuzu & Sınıf Geçme Yönetmeliği Rehberi
 */
object MaarifCurriculumData {

    /**
     * MEB Ortaöğretim Kurumları Yönetmeliği (Sınıf Geçme ve Ölçme-Değerlendirme) Özet Rehberi
     */
    val regulationGuides = listOf(
        MebRegulationGuide(
            title = "Ders Yılı Sonu Başarı ve Sınıf Geçme Şartı",
            articleNumber = "Madde 56 & 57",
            summary = "Öğrencinin ders yılı sonunda herhangi bir dersten başarılı sayılabilmesi için iki dönem puanının aritmetik ortalamasının en az 50 olması veya 2. dönem puanının en az 70 olması gerekir. Yıl sonu başarı puanı en az 50 olan öğrenci doğrudan sınıf geçer.",
            practicalAdviceForTeacher = "Dönem notu hesaplanırken ortak yazılı sınavlar, performans çalışmaları ve ders içi katılım ölçme kriterleri şeffaf bir rubrikle öğrencilere duyurulmalıdır."
        ),
        MebRegulationGuide(
            title = "Ortak Yazılı Sınavlar ve Açık Uçlu Soru Zorunluluğu",
            articleNumber = "Madde 45",
            summary = "Bakanlık, il/ilçe veya okul bazında yapılan ortak yazılı sınavlarda sorular MEB Ölçme ve Değerlendirme Genel Müdürlüğü'nün yayımladığı konu soru dağılım tabloları (senaryolar) esas alınarak açık uçlu ve kısa cevaplı maddelerden oluşturulur. Çoktan seçmeli sınav yapılamaz.",
            practicalAdviceForTeacher = "Sınav soruları hazırlanırken MEB senaryo tablolarındaki kazanım eşleşmelerine ve puanlama anahtarı (rubrik) ayrıntısına dikkat edilmelidir."
        ),
        MebRegulationGuide(
            title = "Sorumlu Olarak Sınıf Geçme ve Sınavlar",
            articleNumber = "Madde 58",
            summary = "Doğrudan sınıfını geçemeyen öğrencilerden en fazla 3 dersten başarısız olanlar sorumlu olarak bir üst sınıfa geçer. Ancak alt sınıflar da dahil toplam sorumlu ders sayısı en fazla 6 olabilir.",
            practicalAdviceForTeacher = "Sorumluluk sınavları eylül, şubat ve haziran aylarında zümre öğretmenlerince hazırlanan komisyon sorularıyla yürütülür."
        ),
        MebRegulationGuide(
            title = "Performans Çalışması ve Proje Değerlendirme",
            articleNumber = "Madde 50",
            summary = "Öğrencilere her dönemde en az bir performans çalışması görevi verilir. Performans çalışması öğrencilerin süreç içerisindeki beceri kazanımlarını ölçecek biçimde dereceli puanlama anahtarı (rubrik) ile notlandırılır.",
            practicalAdviceForTeacher = "Maarif modeli gereği performans ödevleri sadece bilgi derlemesi değil; birincil kaynak inceleme, anakronizm avı, tarihsel empati yazısı veya yerel tarih araştırması şeklinde tasarlanmalıdır."
        )
    )

    /**
     * 9. Sınıf Maarif Modeli Örnek Yıllık Plan Haftaları
     */
    fun getSample9thGradeAnnualPlan(): List<AnnualPlanWeek> {
        return listOf(
            AnnualPlanWeek(
                weekNumber = 1,
                monthName = "Eylül",
                gradeLevel = 9,
                themeName = "1. TEMA: Geçmişin İnşası ve Tarih Yazıcılığı",
                learningOutcomeCode = "TAR.9.1.1",
                learningOutcomeDescription = "Tarihsel bilginin üretim sürecinde birincil ve ikincil kaynakların rolünü sorgulayabilme.",
                skillComponents = "Tarihsel kanıt analizi, kaynak tenkidi (iç ve dış tenkit), sorgulama.",
                coreValues = listOf(CoreValue.DURUSTLUK, CoreValue.SORUMLULUK),
                methodsAndTechniques = "Örnek olay incelemesi, arşiv belgesi analizi, beyin fırtınası.",
                measurementAndEvaluation = "Tanılayıcı dallanmış ağaç, süreç gözlem formu."
            ),
            AnnualPlanWeek(
                weekNumber = 2,
                monthName = "Eylül",
                gradeLevel = 9,
                themeName = "1. TEMA: Geçmişin İnşası ve Tarih Yazıcılığı",
                learningOutcomeCode = "TAR.9.1.2",
                learningOutcomeDescription = "Tarihsel olayların yorumlanmasında neden-sonuç ilişkisini ve dönemin zihniyetini analiz edebilme.",
                skillComponents = "Tarihsel empati, anakronizm tespiti, kronolojik bağlam kurma.",
                coreValues = listOf(CoreValue.ADALET, CoreValue.SAYGI),
                methodsAndTechniques = "Tarihsel mahkeme rol oynama, münazara.",
                measurementAndEvaluation = "Öz değerlendirme formu, analitik rubrik."
            ),
            AnnualPlanWeek(
                weekNumber = 7,
                monthName = "Ekim",
                gradeLevel = 9,
                themeName = "2. TEMA: Eski Çağ Medeniyetlerinde Hukuk ve Toplum",
                learningOutcomeCode = "TAR.9.2.1",
                learningOutcomeDescription = "Eski Çağ medeniyetlerindeki hukuk kurallarının (Hammurabi, Hitit, Roma) toplumsal düzen ve adalet anlayışı üzerindeki etkilerini karşılaştırabilme.",
                skillComponents = "Karşılaştırmalı analiz, değişim ve sürekliliği algılama.",
                coreValues = listOf(CoreValue.ADALET, CoreValue.SORUMLULUK),
                methodsAndTechniques = "Kavram haritası, istasyon tekniği.",
                measurementAndEvaluation = "Açık uçlu soru ve dereceli puanlama anahtarı.",
                specificWeeksAndDays = "29 Ekim Cumhuriyet Bayramı (Cumhuriyetin Hukuk Devrimi ile İlişkilendirme)"
            )
        )
    }

    /**
     * Örnek Maarif Modeli Günlük Ders Planı (Hazırlık, Keşfetme, Açıklama, Derinleştirme, Değerlendirme)
     */
    fun getSampleDailyLessonPlan(): DailyLessonPlan {
        return DailyLessonPlan(
            id = "DLP-9-HIST-01",
            gradeLevel = 9,
            themeName = "Eski Türklerde Devlet Teşkilatı ve Adalet",
            topicTitle = "Töre, Kut ve Sosyal Devlet Anlayışı",
            durationMinutes = 40,
            learningOutcome = "TAR.9.3.2: İlk Türk devletlerinde hükümdarın yetkilerini sınırlayan töre kurallarını ve halkın refahını sağlama (il-töre) ilişkisini analiz eder.",
            essentialQuestion = "Hakanın iradesi mi yoksa törenin adaleti mi üstündür? Günümüz hukuk devleti anlayışıyla nasıl bir köprü kurabiliriz?",
            motivationHook = """
                📜 Güdüleme & Tarihsel Fıkra:
                Dersimize Nasreddin Hoca'nın kadılık yaptığı günlerden bir nükte ile başlayalım:
                Bir gün Hoca kadıyken bir adam gelir, 'Kadı Efendi, komşumun ineği benim tarlamı talan etti, ne ceza düşer?' der. Hoca: 'Hayvanın aklı ermez, sahibine ceza verilemez' buyurur. Adam sinsice güler: 'Kadı Efendi, o inek senindi!' deyince Hoca hemen cübbesini düzeltir: 'Dur bakalım, o zaman mesele derinleşti, raftaki kara kaplı töre/hukuk kitabını getir de bakalım!' der.
                Gençler! Eski Türklerde de hakan 'ben törenin üstündeyim' diyebilir miydi? İşte bugün bunu tartışıyoruz!
            """.trimIndent(),
            instructionalSteps = listOf(
                InstructionalStep(
                    stepTitle = "1. Giriş ve Güdüleme (Problem Durumu)",
                    durationMinutes = 7,
                    teacherActivity = "Tarihsel nükteyi anlatır, Orhun Abideleri'nden 'Aç milleti doyurdum, çıplak milleti giydirdim' sözünü tahtaya yazar.",
                    studentActivity = "Sözün sosyal devlet anlayışıyla ilişkisi üzerine fikir yürütür.",
                    pedagogicalGoal = "Öğrencide merak uyandırma ve ön bilgileri harekete geçirme."
                ),
                InstructionalStep(
                    stepTitle = "2. Keşfetme ve Birincil Kaynak Analizi",
                    durationMinutes = 15,
                    teacherActivity = "Kutadgu Bilig ve Orhun Yazıtları'ndan pasajlar dağıtır, 'İstasyon Tekniği' ile gruplara rehberlik eder.",
                    studentActivity = "Kaynaklardaki 'Töre', 'Kut', 'Toy' ve 'Adalet' kavramlarını eşleştirir.",
                    pedagogicalGoal = "Tarihsel kanıt kullanma ve kaynak tenkiti becerisini geliştirme."
                ),
                InstructionalStep(
                    stepTitle = "3. Açıklama ve Derinleştirme (Kavram Haritası)",
                    durationMinutes = 10,
                    teacherActivity = "Grupların bulgularını tahtada kavram haritasına dönüştürür, Yusuf Has Hacib'in adalet ilkesini açıklar.",
                    studentActivity = "Defterlerine zihin haritası oluşturur, sorular yöneltir.",
                    pedagogicalGoal = "Kavram yanılgılarını giderme ve bilgiyi yapılandırma."
                ),
                InstructionalStep(
                    stepTitle = "4. Değerlendirme & Çıkış Kartı (Exit Ticket)",
                    durationMinutes = 8,
                    teacherActivity = "Öğrencilere 2 soruluk çıkış kartı dağıtır.",
                    studentActivity = "'Bugün öğrendiğim en çarpıcı kavram' ve 'Töre ile bugünkü anayasa arasındaki 1 benzerlik' yazar.",
                    pedagogicalGoal = "Süreç odaklı formatif değerlendirme sağlama."
                )
            ),
            coreValuesIntegrated = listOf(CoreValue.ADALET, CoreValue.SORUMLULUK, CoreValue.VATANSEVERLIK),
            differentiatedInstructionNotes = "Zenginleştirme: İleri düzey öğrenciler için Kutadgu Bilig'deki adaleti temsil eden 'Kün Toğdı' karakteri ile Platon'un Devlet felsefesi karşılaştırması verilir. Destekleme: Temel kavram kartları ile eşleştirme yaptırılır.",
            assessmentMethod = "Çıkış Kartı + Süreç Odaklı Rubrik + Öğrenci Öz Değerlendirme Formu"
        )
    }

    /**
     * Resmî MEB Formatında Zümre Öğretmenler Kurulu Tutanağı Örneği
     */
    fun getSampleZumreMeetingRecord(): ZumreMeetingRecord {
        return ZumreMeetingRecord(
            schoolYear = "2026-2027 Eğitim ve Öğretim Yılı",
            term = "1. Dönem Başı Tarih Zümre Öğretmenler Kurulu Tutanağı",
            meetingDate = "08.09.2026",
            agendaItems = listOf(
                "1. Açılış ve yoklama.",
                "2. Bir önceki eğitim-öğretim yılının başarı durumunun değerlendirilmesi.",
                "3. Türkiye Yüzyılı Maarif Modeli Tarih Dersi Öğretim Programı'nın incelenmesi ve yıllık planların hazırlanması.",
                "4. MEB Ortaöğretim Kurumları Yönetmeliği uyarınca ortak yazılı sınav senaryolarının belirlenmesi.",
                "5. Öğrencilerin süreç odaklı değerlendirilmesi, performans ve proje görev kriterlerinin tespiti.",
                "6. Derslerde kullanılacak araç-gereç, dijital materyaller ve okul dışı öğrenme ortamları (müze, ören yeri vb.).",
                "7. Başarıyı artırıcı tedbirler ve dilek-temenniler."
            ),
            decisionsTaken = listOf(
                "Tüm sınıf düzeylerinde dersler Türkiye Yüzyılı Maarif Modeli'nin 'Erdem-Değer-Eylem' çerçevesine ve beceri temelli öğrenme çıktılarına göre işlenecektir.",
                "Yazılı sınavlar MEB Ölçme ve Değerlendirme Yönetmeliği uyarınca tamamen açık uçlu ve kısa cevaplı sorularla hazırlanacak, ortak sınav konu soru dağılım tabloları (senaryolar) sınavdan 2 hafta önce panolarda ilan edilecektir.",
                "Her dönem en az bir performans görevi verilecek; performans görevleri ezber bilgi yerine birincil kaynak incelemesi ve tarihsel empati metni formatında rubrikle değerlendirilecektir.",
                "Öğrencilerin sınıf geçme durumları e-Okul üzerinden düzenli takip edilecek; başarısızlık riski taşıyan öğrencilere destekleme ve yetiştirme kursları (DYK) veya bireysel çalışma planları sunulacaktır."
            ),
            passFailRegulationDecisions = "Ortaöğretim Kurumları Yönetmeliği Madde 56 ve 57 hükümleri gereği, ders yılı sonu ağırlıklı not ortalaması en az 50 olanların doğrudan geçeceği, en fazla 3 dersten başarısız olanların sorumlu geçeceği veli toplantılarında hatırlatılacaktır.",
            measurementEvaluationCriteria = "Sınav soruları hazırlanırken analiz, sentez ve değerlendirme basamaklarındaki beceri sorularına %60, kavrama ve bilgi düzeyine %40 ağırlık verilecek; ayrıntılı dereceli puanlama anahtarı (rubrik) kullanılacaktır."
        )
    }

    /**
     * Resmî MEB Senaryo 1 Uyumlu 9. Sınıf 1. Dönem 1. Ortak Yazılı Sınav Kağıdı
     */
    fun getSampleMebExamPaper(): MebExamPaper {
        return MebExamPaper(
            examTitle = "2026-2027 EĞİTİM ÖĞRETİM YILI 9. SINIF TARİH DERSİ 1. DÖNEM 1. ORTAK YAZILI SINAVI (MEB SENARYO 1)",
            gradeLevel = 9,
            totalScore = 100,
            questions = listOf(
                ExamQuestionItem(
                    questionNumber = 1,
                    outcomeCode = "TAR.9.1.1",
                    sourceOrPremise = "Tarihçi Leopold von Ranke: 'Tarihçi geçmişi yalnızca gerçekten olduğu gibi göstermekle yükümlüdür. Bunun için de belgelere başvurmak zorundadır.' demiştir.",
                    questionText = "Yukarıdaki ifadeden hareketle; tarihsel bilginin üretiminde 'belge ve birincil kaynakların' neden vazgeçilmez olduğunu iki gerekçe belirterek açıklayınız.",
                    pointValue = 20
                ),
                ExamQuestionItem(
                    questionNumber = 2,
                    outcomeCode = "TAR.9.1.2",
                    sourceOrPremise = "Bir araştırmacı, 14. yüzyılda yaşamış bir Osmanlı akıncısının cebinden pusula ve mekanik saat çıktığını iddia eden bir tarihi roman yazmıştır.",
                    questionText = "Bu romanda yapılan 'tarihsel hata' (kavram) nedir? Tarihsel olayları incelerken dönemin şartlarını gözetmenin önemini bir cümleyle ifade ediniz.",
                    pointValue = 20
                ),
                ExamQuestionItem(
                    questionNumber = 3,
                    outcomeCode = "TAR.9.2.1",
                    sourceOrPremise = "Hammurabi Kanunları: 'Bir adam başka bir adamın gözünü çıkarırsa onun da gözü çıkarılır (Kısasa kısas).' \nHitit Kanunları: 'Bir adam başkasının kölesini öldürürse tazminat olarak iki köle verir veya bedelini gümüş olarak öder.'",
                    questionText = "Yukarıda verilen iki Eski Çağ hukuk maddesini 'ceza hukuku mantığı ve insan hakları gelişimi' açısından karşılaştırarak Hitit hukukunun en belirgin özelliğini yazınız.",
                    pointValue = 30
                ),
                ExamQuestionItem(
                    questionNumber = 4,
                    outcomeCode = "TAR.9.3.2",
                    sourceOrPremise = "Orhun Kitabeleri: 'Tanrı buyurduğu için kağan oldum... Aç milleti doyurdum, çıplak milleti donattım.'",
                    questionText = "Bu metinde geçen 'Sosyal Devlet' ilkesini ve hükümdarın halkına karşı sorumluluğunu günümüz Türkiye Cumhuriyeti Anayasası'nın ilgili ilkesiyle ilişkilendirerek yazınız.",
                    pointValue = 30
                )
            ),
            rubricScoringKey = listOf(
                ExamScoringKeyItem(
                    questionNumber = 1,
                    expectedAnswer = "1. Belge ve birincil kaynaklar olayın gerçekleştiği anda oluşturulduğu için güvenilirlik ve objektiflik sağlar. 2. Tarihçinin sübjektif yorumlardan kaçınarak geçmişi kanıtlara dayandırmasını mümkün kılar.",
                    partialScoreCriteria = "Her bir geçerli gerekçe için 10 puan verilir.",
                    maxScore = 20
                ),
                ExamScoringKeyItem(
                    questionNumber = 2,
                    expectedAnswer = "Yapılan hata 'Anakronizm' (Tarih yanılgısı / zaman aşımı) kavramıdır. Olaylar değerlendirilirken dönemin bilimsel ve teknolojik imkanları göz önünde bulundurulmalı, bugünün şartlarıyla geçmiş yargılanmamalıdır.",
                    partialScoreCriteria = "Anakronizm terimi için 10 puan, açıklama için 10 puan.",
                    maxScore = 20
                ),
                ExamScoringKeyItem(
                    questionNumber = 3,
                    expectedAnswer = "Hammurabi Kanunları katı ve bedensel cezaya (kısasa kısas) dayanırken; Hitit Kanunları insancıl olup fidye/tazminat esasına dayanır. Hitit hukukunun belirgin özelliği mülkiyet ve tazminat odaklı daha yumuşak bir ceza anlayışına sahip olmasıdır.",
                    partialScoreCriteria = "Karşılaştırma için 15 puan, Hitit hukukunun özelliğini belirtme için 15 puan.",
                    maxScore = 30
                ),
                ExamScoringKeyItem(
                    questionNumber = 4,
                    expectedAnswer = "Hükümdarın halkın açlığını gidermesi ve giydirmesi 'Sosyal Devlet' (halkın refahını sağlama) ilkesidir. Türkiye Cumhuriyeti Anayasası'nın 2. maddesinde yer alan 'Demokratik, lâik ve sosyal bir hukuk devletidir' ilkesiyle doğrudan örtüşür.",
                    partialScoreCriteria = "Sosyal devlet ilkesi analizi 15 puan, T.C. Anayasası 2. madde eşleştirmesi 15 puan.",
                    maxScore = 30
                )
            )
        )
    }
}
