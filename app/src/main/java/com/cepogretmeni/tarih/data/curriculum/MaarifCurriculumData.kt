package com.cepogretmeni.tarih.data.curriculum

import com.cepogretmeni.tarih.domain.model.*

/**
 * Türkiye Yüzyılı Maarif Modeli Tarih Dersi (9-12. Sınıf) Tam Kapsamlı Müfredat,
 * Sınıf Geçme Mevzuatı, Çoklu Saatli Günlük Planlar, Sorular, Hikâye Anlatımı ve Rubrik Motoru
 */
object MaarifCurriculumData {

    // === MEB Ortaöğretim Kurumları Yönetmeliği ===
    val regulationGuides = listOf(
        MebRegulationGuide(
            title = "Ders Yılı Sonu Başarı ve Doğrudan Sınıf Geçme",
            articleNumber = "Madde 56 & 57",
            summary = "Ders yılı sonunda ağırlıklı yıl sonu puanı en az 50 olan öğrenciler doğrudan sınıf geçer. Bir dersten başarılı sayılmak için iki dönem ortalamasının en az 50 veya 2. dönem notunun en az 70 olması gerekir.",
            practicalAdviceForTeacher = "Ortak sınavlar, performans ödevleri ve derse katılım puanları şeffaf bir rubrikle verilmelidir."
        ),
        MebRegulationGuide(
            title = "Açık Uçlu Ortak Sınavlar ve MEB Konu Soru Dağılım Senaryoları",
            articleNumber = "Madde 45",
            summary = "Bakanlık, il/ilçe veya okul bazındaki tüm yazılı sınavlar MEB Ölçme ve Değerlendirme Genel Müdürlüğü'nün senaryo tablolarına göre hazırlanır. Çoktan seçmeli sınav yapılamaz; sorular açık uçlu ve kısa cevaplı olmak zorundadır.",
            practicalAdviceForTeacher = "Her sınav sorusu birincil kaynak veya harita/tablo analizine dayanmalı, dereceli puanlama anahtarı (rubrik) sınavdan önce hazır olmalıdır."
        ),
        MebRegulationGuide(
            title = "Sorumlu Sınıf Geçme ve Sınav Hakları",
            articleNumber = "Madde 58",
            summary = "Doğrudan geçemeyenlerden en fazla 3 zayıfı olan sorumlu geçer. Alt sınıflar dahil toplam sorumlu ders sayısı 6'yı aşarsa öğrenci sınıf tekrarı yapar.",
            practicalAdviceForTeacher = "Sorumluluk sınavları eylül, şubat ve haziran aylarında zümre komisyonu tarafından yapılır."
        ),
        MebRegulationGuide(
            title = "Süreç Odaklı Performans Görevi ve Proje Değerlendirme",
            articleNumber = "Madde 50",
            summary = "Her dönem en az bir performans görevi verilir. Ezber rapor yerine birincil kaynak inceleme, anakronizm avı veya yerel tarih araştırması şeklinde tasarlanır.",
            practicalAdviceForTeacher = "Öğrenciye süreç içerisinde geri bildirim verilmeli ve öz değerlendirme formu uygulanmalıdır."
        )
    )

    // === HİKÂYELEŞTİRİCİ DERS ANLATIM MODÜLLERİ ===
    fun getStoryNarrativeForGrade(grade: Int): StorytellingLessonNarrative {
        return when (grade) {
            10 -> StorytellingLessonNarrative(
                themeTitle = "Beylikten Devlete Osmanlı Siyaseti",
                topicTitle = "İstimalet (Adalet & Hoşgörü) ve Gaza Ruhu",
                gradeLevel = 10,
                historicalMindsetAnalysis = "14. yüzyıl Osmanlı insanı kılıcı sadece fethetmek için değil, mazluma adalet dağıtmak ve töreyi ihya etmek için çekiyordu. Fethedilen yerdeki gayrimüslim halka dini serbestlik verilmesi (İstimalet), gönülleri fethetti.",
                narrativeStory = "Osman Gazi Söğüt'te beyliğin temelini atarken kılıcın yanına teraziyi koydu. Bilecik pazarında bir Hristiyan tüccarın hakkını Müslüman müşteriye karşı teslim etti. Bu adalet haberi Bizans kalelerinin kapılarını kılıçtan önce açtı.",
                historicalAnecdoteOrHumor = "Fatih Sultan Mehmed ile Rum mimarın Kadı Hızır Bey önündeki meşhur mahkemesi: Padişah dahi olsan töre ve adaletin üstünde değilsin!",
                concludingCoupletOrPoem = "Şeyh Edebali: 'İnsanı yaşat ki devlet yaşasın! Adaletten ayrılma ki mülkün daim olsun.'",
                mindMapSummary = listOf("1. Gaza ve Cihat", "2. İstimalet (Adalet)", "3. İskân Siyaseti", "4. Ahilik Teşkilatı"),
                criticalThinkingPrompt = "Bir devleti yüzyıllarca ayakta tutan asıl güç ordusu mudur, mahkemelerindeki adalet terazisi midir?"
            )
            12 -> StorytellingLessonNarrative(
                themeTitle = "Millî Mücadele ve Bağımsızlık",
                topicTitle = "Milletin Azim ve Kararı: Amasya'dan Sivas'a",
                gradeLevel = 12,
                historicalMindsetAnalysis = "1919 Türkiyesi işgaller altındaydı ancak milletin bağrındaki istiklal ateşi sönmemişti. Mustafa Kemal Paşa, kurtuluşun saraydan değil doğrudan milletin iradesinden doğacağını görüyordu.",
                narrativeStory = "Mustafa Kemal Paşa Amasya'da tarihin akışını değiştiren cümleyi yazdırdı: 'Milletin istiklâlini yine milletin azim ve kararı kurtaracaktır!'",
                historicalAnecdoteOrHumor = "Mustafa Kemal'in işgal donanmalarına bakarak söylediği tarihî söz: 'Geldikleri gibi giderler!'",
                concludingCoupletOrPoem = "Mehmet Akif Ersoy: 'Ben ezelden beridir hür yaşadım, hür yaşarım. Hangi çılgın bana zincir vuracakmış? Şaşarım!'",
                mindMapSummary = listOf("1. Amasya Genelgesi", "2. Erzurum Kongresi", "3. Sivas Kongresi", "4. TBMM'nin Açılışı"),
                criticalThinkingPrompt = "Tekâlif-i Milliye Emirleri'nde milletin varını yoğunu orduyla paylaşması hangi kök değerlerin göstergesidir?"
            )
            else -> StorytellingLessonNarrative(
                themeTitle = "İlk ve Orta Çağlarda Türk Dünyası",
                topicTitle = "Töre, Kut ve Sosyal Devlet Anlayışı",
                gradeLevel = 9,
                historicalMindsetAnalysis = "Eski Türkler için devlet bir şahsın mülkü değil, milletin töreyle korunan ortak yuvasıydı. Hakan ancak halkını doyurduğu ve giydirdiği sürece hakandı.",
                narrativeStory = "Avrasya bozkırlarında at koşturan atalarımız için en büyük erdem 'Töre' idi. Orhun Kitabeleri'nde Bilge Kağan: 'Aç milleti doyurdum, çıplak milleti giydirdim' diyerek sosyal devleti taşa kazımıştır.",
                historicalAnecdoteOrHumor = "Vezir Tonyukuk: 'Bizim atlarımız rüzgar, gökyüzü çadırımız, töremiz ise surumuzdur.'",
                concludingCoupletOrPoem = "Kutadgu Bilig: 'Beyliğin temeli adalet üzerinedir; Bey adil olursa beylik uzun sürer.'",
                mindMapSummary = listOf("1. Kut İnancı", "2. Töre Hukuku", "3. Kurultay (Toy)", "4. Ordu-Millet"),
                criticalThinkingPrompt = "Eski Türklerdeki 'Töre' ile günümüz 'Hukukun Üstünlüğü' arasındaki benzerlikler nelerdir?"
            )
        }
    }

    // === HAFTALIK DERS SAATİ KADAR ÇOKLU BLOKLU GÜNLÜK DERS PLANI ÜRETİCİSİ ===
    fun generateMultiHourDailyPlan(
        gradeLevel: Int,
        weeklyHours: Int,
        profile: TeacherProfile
    ): MultiHourDailyPlan {
        val themeTitle = when (gradeLevel) {
            10 -> "2. TEMA: Beylikten Devlete Osmanlı Siyaseti ve Gaza Anlayışı"
            11 -> "3. TEMA: Uluslararası İlişkilerde Denge Stratejisi (1774-1914)"
            12 -> "2. TEMA: Millî Mücadele ve Bağımsızlık Ruhu"
            else -> "3. TEMA: Türk Dünyasında Devlet ve Toplum"
        }

        val topicTitle = when (gradeLevel) {
            10 -> "Osmanlı'nın Kuruluşu, İstimalet (Adalet) ve Gaza Politikası"
            11 -> "19. Yüzyıl Osmanlı Diplomasisi ve Boğazlar Meselesi"
            12 -> "Amasya Genelgesi'nden Erzurum ve Sivas Kongrelerine Millî İrade"
            else -> "İlk Türk Devletlerinde Töre, Kut ve Sosyal Devlet Anlayışı"
        }

        val hours = mutableListOf<SingleLessonHourDetail>()

        hours.add(
            SingleLessonHourDetail(
                hourNumber = 1,
                hourTitle = "$topicTitle (1. Bölüm: Kavramsal Çerçeve ve Birincil Kaynak Keşfi)",
                learningOutcomes = "TAR.$gradeLevel.1: Tarihsel kanıtları sorgular, dönemin zihniyetiyle ilişkilendirir.",
                hookAndMotivation = "Nasreddin Hoca nükte güdülemesi ve Orhun / Arşiv belgesinden çarpıcı bir problem durumu sorusu ile derse giriş.",
                instructionalProcess = "15 Dk İstasyon Tekniği: Gruplara birincil kaynak metinleri dağıtılır. Öğrenciler kavram kartları ile tenkit yapar.",
                evaluationAndExitTicket = "1. Saat Çıkış Kartı: 'Bugün incelediğim kaynakta beni en çok şaşırtan tarihsel bilgi'."
            )
        )

        if (weeklyHours >= 2) {
            hours.add(
                SingleLessonHourDetail(
                    hourNumber = 2,
                    hourTitle = "$topicTitle (2. Bölüm: Karşılaştırmalı Analiz ve Değerlendirme)",
                    learningOutcomes = "TAR.$gradeLevel.2: Değişim ve sürekliliği algılar, adalet ve vatanseverlik kök değerleriyle köprü kurar.",
                    hookAndMotivation = "Kutadgu Bilig / Şeyh Edebali nasihati üzerinden adalet-devlet ilişkisi üzerine beyin fırtınası.",
                    instructionalProcess = "20 Dk Zihin Haritası ve Tarihsel Mahkeme Simülasyonu: Öğrenciler sav ve karşı-savlar geliştirir.",
                    evaluationAndExitTicket = "2. Saat Değerlendirmesi: Tanılayıcı Dallanmış Ağaç ve Öğrenci Süreç Öz Değerlendirme Formu."
                )
            )
        }

        if (weeklyHours >= 3) {
            hours.add(
                SingleLessonHourDetail(
                    hourNumber = 3,
                    hourTitle = "$topicTitle (3. Bölüm: Derinleştirme ve Atölye Çalışması)",
                    learningOutcomes = "TAR.$gradeLevel.3: Harita okuryazarlığı ve anakronizm tespiti atölyesi.",
                    hookAndMotivation = "Tarihsel harita ve minyatürler üzerinden coğrafi mekan analizi.",
                    instructionalProcess = "Harita üzerinde fetih ve göç rotalarının çizimi, anakronizm avı etkinliği.",
                    evaluationAndExitTicket = "Analitik Rubrik değerlendirmesi."
                )
            )
        }

        return MultiHourDailyPlan(
            id = "PLAN-$gradeLevel-W7",
            gradeLevel = gradeLevel,
            themeName = themeTitle,
            topicTitle = topicTitle,
            weekNumber = 7,
            weeklyHoursCount = weeklyHours,
            schoolName = profile.schoolName,
            teacherName = profile.teacherName,
            principalName = profile.principalName,
            dateRange = "20.10.2026 - 24.10.2026",
            essentialQuestion = "Geçmişin adalet anlayışı ile bugünün anayasal hakları arasında nasıl bir süreklilik vardır?",
            lessonHours = hours,
            coreValues = listOf(CoreValue.ADALET, CoreValue.VATANSEVERLIK, CoreValue.SORUMLULUK),
            differentiatedInstruction = "Zenginleştirme: İleri düzey öğrenciler için birincil metinlerin orijinal transkripsiyonu verilir. Destekleme: Kavram eşleştirme tabloları kullanılır.",
            assessmentMethod = "Çıkış Kartı (Her Ders Sonu) + Süreç Rubriği + Öz Değerlendirme Formu"
        )
    }

    // === MAARİF MODELİ YILLIK DERS PLANI ===
    fun getFullAnnualPlan(grade: Int): List<AnnualPlanWeek> {
        return when (grade) {
            10 -> listOf(
                AnnualPlanWeek(1, "Eylül", 10, "1. TEMA: Selçuklu Türkiye'si", "TAR.10.1.1", "Anadolu'ya yapılan Türk göçlerinin jeopolitik ve kültürel sonuçlarını analiz eder.", "Mekânı algılama, harita analizi", listOf(CoreValue.VATANSEVERLIK), "Harita okuma atölyesi", "Süreç gözlem formu"),
                AnnualPlanWeek(2, "Eylül", 10, "1. TEMA: Selçuklu Türkiye'si", "TAR.10.1.2", "Miryokefalon Zaferi ile Anadolu'nun kesin Türk yurdu oluşunu değerlendirir.", "Kronolojik düşünme, kanıt analizi", listOf(CoreValue.VATANSEVERLIK, CoreValue.SORUMLULUK), "Örnek olay incelemesi", "Tanılayıcı dallanmış ağaç"),
                AnnualPlanWeek(7, "Ekim", 10, "2. TEMA: Beylikten Devlete Osmanlı", "TAR.10.2.1", "Osmanlı Devleti'nin kuruluşunda İstimalet (adalet ve hoşgörü) politikasının rolünü sorgular.", "Tarihsel empati, kanıt kullanma", listOf(CoreValue.ADALET, CoreValue.SAYGI), "Kaynak tahlili ve münazara", "Analitik rubrik", "29 Ekim Cumhuriyet Bayramı")
            )
            12 -> listOf(
                AnnualPlanWeek(1, "Eylül", 12, "1. TEMA: 20. Yüzyıl Başlarında Osmanlı", "TAR.12.1.1", "Mustafa Kemal'in askerî ve entelektüel gelişimini etkileyen ortamı analiz eder.", "Biyografi analizi, empati", listOf(CoreValue.VATANSEVERLIK), "Zihin haritası çıkarma", "Açık uçlu soru formu"),
                AnnualPlanWeek(4, "Ekim", 12, "2. TEMA: Millî Mücadele", "TAR.12.2.1", "Amasya Genelgesi ve kongreler sürecinde millî irade kavramının doğuşunu açıklar.", "Tarihsel sorgulama, metin tenkidi", listOf(CoreValue.VATANSEVERLIK, CoreValue.SORUMLULUK), "Tarihsel mahkeme simülasyonu", "Dereceli puanlama anahtarı (Rubrik)"),
                AnnualPlanWeek(7, "Ekim", 12, "2. TEMA: Millî Mücadele", "TAR.12.2.2", "Tekâlif-i Milliye Emirleri doğrultusunda halk-ordu dayanışmasını değerlendirir.", "Kanıt kullanma, empati", listOf(CoreValue.YARDIMSEVERLIK, CoreValue.VATANSEVERLIK), "Görsel ve anı incelemesi", "Öz değerlendirme formu", "29 Ekim Cumhuriyet Bayramı")
            )
            else -> getSample9thGradeAnnualPlan()
        }
    }

    fun getSample9thGradeAnnualPlan(): List<AnnualPlanWeek> {
        return listOf(
            AnnualPlanWeek(1, "Eylül", 9, "1. TEMA: Geçmişin İnşası ve Tarih Yazıcılığı", "TAR.9.1.1", "Tarihsel bilginin üretim sürecinde birincil ve ikincil kaynakların rolünü sorgular.", "Tarihsel kanıt analizi, iç-dış tenkit", listOf(CoreValue.DURUSTLUK, CoreValue.SORUMLULUK), "Arşiv belgesi incelemesi, istasyon", "Tanılayıcı dallanmış ağaç"),
            AnnualPlanWeek(2, "Eylül", 9, "1. TEMA: Geçmişin İnşası ve Tarih Yazıcılığı", "TAR.9.1.2", "Tarihsel olayları dönemin şartları ve zihniyetiyle (anakronizmden uzak) analiz eder.", "Tarihsel empati, kronolojik bağlam", listOf(CoreValue.ADALET, CoreValue.SAYGI), "Tarihsel rol oynama, münazara", "Analitik rubrik"),
            AnnualPlanWeek(7, "Ekim", 9, "2. TEMA: Eski Çağ Hukuku ve Toplum", "TAR.9.2.1", "Eski Çağ kanunlarının (Hammurabi, Hitit, Roma) adalet anlayışı ve insan hakları gelişimine etkisini karşılaştırır.", "Karşılaştırmalı analiz, değişim-süreklilik", listOf(CoreValue.ADALET, CoreValue.SORUMLULUK), "Kavram haritası, istasyon", "Açık uçlu sınav sorusu", "29 Ekim Cumhuriyet Bayramı"),
            AnnualPlanWeek(12, "Aralık", 9, "3. TEMA: Türk Dünyasında Devlet ve Toplum", "TAR.9.3.1", "İlk Türk devletlerinde Kut anlayışı, töre ve sosyal devlet yapısını analiz eder.", "Tarihsel sorgulama, kavram analizi", listOf(CoreValue.ADALET, CoreValue.VATANSEVERLIK), "Orhun Yazıtları atölyesi", "Öz değerlendirme formu")
        )
    }

    // === RESMÎ ZÜMRE TUTANAĞI ===
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

    // === MEB SENARYOLU YAZILI SINAV VE AYRINTILI DERECELİ PUANLAMA ANAHTARI (RUBRİK) ===
    fun getSampleMebExamPaper(): MebExamPaper {
        return MebExamPaper(
            examTitle = "2026-2027 EĞİTİM ÖĞRETİM YILI 9. SINIF TARİH DERSİ 1. DÖNEM 1. ORTAK YAZILI SINAVI (MEB SENARYO 1)",
            gradeLevel = 9,
            totalScore = 100,
            questions = listOf(
                ExamQuestionItem(1, "TAR.9.1.1", "Yukarıdaki ifadeden hareketle; tarihsel bilginin üretiminde 'belge ve birincil kaynakların' neden vazgeçilmez olduğunu iki gerekçe belirterek açıklayınız.", "Tarihçi Leopold von Ranke: 'Tarihçi geçmişi yalnızca gerçekten olduğu gibi göstermekle yükümlüdür. Bunun için de belgelere başvurmak zorundadır.' demiştir.", 20),
                ExamQuestionItem(2, "TAR.9.1.2", "Bu romanda yapılan 'tarihsel hata' (kavram) nedir? Tarihsel olayları incelerken dönemin şartlarını gözetmenin önemini bir cümleyle ifade ediniz.", "Bir araştırmacı, 14. yüzyılda yaşamış bir Osmanlı akıncısının cebinden pusula ve mekanik saat çıktığını iddia eden bir tarihi roman yazmıştır.", 20),
                ExamQuestionItem(3, "TAR.9.2.1", "Yukarıda verilen iki Eski Çağ hukuk maddesini 'ceza hukuku mantığı ve insan hakları gelişimi' açısından karşılaştırarak Hitit hukukunun en belirgin özelliğini yazınız.", "Hammurabi Kanunları: 'Bir adam başka bir adamın gözünü çıkarırsa onun da gözü çıkarılır (Kısasa kısas).' \nHitit Kanunları: 'Bir adam başkasının kölesini öldürürse tazminat olarak iki köle verir veya bedelini gümüş olarak öder.'", 30),
                ExamQuestionItem(4, "TAR.9.3.2", "Bu metinde geçen 'Sosyal Devlet' ilkesini ve hükümdarın halkına karşı sorumluluğunu günümüz Türkiye Cumhuriyeti Anayasası'nın ilgili ilkesiyle ilişkilendirerek yazınız.", "Orhun Kitabeleri: 'Tanrı buyurduğu için kağan oldum... Aç milleti doyurdum, çıplak milleti donattım.'", 30)
            ),
            rubricScoringKey = listOf(
                ExamScoringKeyItem(1, "1. Birincil kaynaklar olayın gerçekleştiği anda üretildiği için objektiflik ve güvenilirlik sağlar. 2. Tarihçinin sübjektif yorumdan kaçınarak tezlerini somut kanıtlara dayandırmasını sağlar.", "Her geçerli gerekçe 10 puan.", 20),
                ExamScoringKeyItem(2, "Kavram: 'Anakronizm' (Tarih yanılgısı). Açıklama: Geçmiş olaylar bugünün bilgi ve teknolojisiyle değil, yaşandığı çağın imkan ve zihniyetiyle değerlendirilmelidir.", "Anakronizm terimi 10 puan, açıklama 10 puan.", 20),
                ExamScoringKeyItem(3, "Hammurabi Kanunları katı ve bedensel cezaya (kısas) dayanırken; Hitit Kanunları insancıl olup fidye/tazminat esasına dayanır. Hitit hukukunun en belirgin özelliği mülkiyet ve tazminat odaklı yumuşak bir ceza anlayışına sahip olmasıdır.", "Karşılaştırma 15 puan, Hitit özelliği 15 puan.", 30),
                ExamScoringKeyItem(4, "Halkın açlığını ve çıplaklığını gidermek 'Sosyal Devlet' ilkesidir. T.C. Anayasası 2. maddesindeki 'Demokratik, laik ve sosyal bir hukuk devletidir' ilkesiyle doğrudan örtüşür.", "Sosyal devlet analizi 15 puan, T.C. Anayasası 2. madde eşleştirmesi 15 puan.", 30)
            )
        )
    }
}
