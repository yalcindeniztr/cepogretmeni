package com.cepogretmeni.tarih.data.curriculum

import com.cepogretmeni.tarih.domain.model.*

/**
 * Türkiye Yüzyılı Maarif Modeli Tarih Dersi (9-12. Sınıf) Tam Kapsamlı Müfredat,
 * Sınıf Geçme Mevzuatı, Hikâye Anlatımları, Sorular ve Rubrik Motoru
 */
object MaarifCurriculumData {

    // === MEB Ortaöğretim Kurumları Yönetmeliği (Sınıf Geçme & Ölçme-Değerlendirme) ===
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

    // === MAARİF MODELİ 9, 10, 11, 12. SINIF TEMALARI ===
    val allMaarifThemes = listOf(
        // 9. Sınıf
        MaarifTheme(
            id = "T-9-1",
            gradeLevel = 9,
            themeNumber = 1,
            themeTitle = "Geçmişin İnşası ve Tarih Yazıcılığı",
            essentialIdea = "Tarih, geçmişin donuk bir ezberi değil; birincil kanıtların sorgulanması ve eleştirel düşünmeyle yeniden inşa edilen canlı bir süreçtir.",
            targetOutcomes = listOf(
                MaarifOutcome("TAR.9.1.1", "Tarihsel bilginin üretim sürecinde kaynakların rolünü sorgular.", "Eleştirel Düşünme", "Tarihsel Kanıt Analizi", "Merak ve Sorgulama", "Dürüstlük ve Bilim Etiği"),
                MaarifOutcome("TAR.9.1.2", "Tarihsel olayları dönemin şartları ve zihniyetiyle (anakronizme düşmeden) yorumlar.", "Neden-Sonuç İlişkisi", "Tarihsel Empati", "Açık Fikirlilik", "Adalet ve Nesnellik")
            ),
            integratedCoreValues = listOf(CoreValue.DURUSTLUK, CoreValue.ADALET, CoreValue.SAYGI),
            primarySkills = listOf(HistorySkill.KANIT_KULLANMA, HistorySkill.TARIHSEL_EMPATI)
        ),
        MaarifTheme(
            id = "T-9-3",
            gradeLevel = 9,
            themeNumber = 3,
            themeTitle = "İlk ve Orta Çağlarda Türk Dünyasında Devlet ve Toplum",
            essentialIdea = "Eski Türklerde devletin meşruiyeti (Kut), törenin adaleti ve halkın refahını sağlama (Sosyal Devlet) sorumluluğuna dayanır.",
            targetOutcomes = listOf(
                MaarifOutcome("TAR.9.3.1", "Türk devlet teşkilatında Kut anlayışı ile töre ilişkisini analiz eder.", "Kavramsal Analiz", "Tarihsel Sorgulama", "Vatanseverlik Bilinci", "Adalet ve Sorumluluk"),
                MaarifOutcome("TAR.9.3.2", "Konargöçer yaşam tarzının Türk toplum yapısı ve ordu-millet anlayışına etkilerini değerlendirir.", "Mekân-İnsan İlişkisi", "Mekânı Algılama", "Dayanışma", "Yardımseverlik")
            ),
            integratedCoreValues = listOf(CoreValue.ADALET, CoreValue.SORUMLULUK, CoreValue.VATANSEVERLIK),
            primarySkills = listOf(HistorySkill.TARIHSEL_SORGULAMA, HistorySkill.MEKAN_ALGILAMA)
        ),

        // 10. Sınıf
        MaarifTheme(
            id = "T-10-2",
            gradeLevel = 10,
            themeNumber = 2,
            themeTitle = "Beylikten Devlete Osmanlı Siyaseti ve Gaza Anlayışı",
            essentialIdea = "Osmanlı'nın beylikten cihan devletine dönüşümünde adalet (İstimalet), hoşgörü ve gaza ruhu belirleyici olmuştur.",
            targetOutcomes = listOf(
                MaarifOutcome("TAR.10.2.1", "Osmanlı Devleti'nin kuruluşunu kolaylaştıran jeopolitik, toplumsal ve kurumsal etkenleri analiz eder.", "Çok Boyutlu Düşünme", "Kronolojik Düşünme", "Stratejik Bakış", "Vatanseverlik"),
                MaarifOutcome("TAR.10.2.2", "İstimalet (hoşgörü ve adalet) politikasının fetihlerin kalıcı olmasındaki rolünü birincil belgelerle değerlendirir.", "Kanıt Kullanma", "Tarihsel Empati", "Adalet Eğilimi", "Adalet ve Saygı")
            ),
            integratedCoreValues = listOf(CoreValue.ADALET, CoreValue.SAYGI, CoreValue.ESTETIK),
            primarySkills = listOf(HistorySkill.KANIT_KULLANMA, HistorySkill.TARIHSEL_EMPATI)
        ),

        // 11. Sınıf
        MaarifTheme(
            id = "T-11-3",
            gradeLevel = 11,
            themeNumber = 3,
            themeTitle = "Uluslararası İlişkilerde Denge Stratejisi (1774-1914)",
            essentialIdea = "Osmanlı Devleti 19. yüzyılda varlığını korumak için büyük güçler arasındaki çıkar çatışmalarını yöneterek Denge Politikası uygulamıştır.",
            targetOutcomes = listOf(
                MaarifOutcome("TAR.11.3.1", "Osmanlı Devleti'nin 19. yüzyılda uyguladığı denge siyasetini diplomasi tarihi belgeleri üzerinden analiz eder.", "Stratejik Analiz", "Tarihsel Kanıt Analizi", "Diplomasi Bilinci", "Sorumluluk ve Vatanseverlik")
            ),
            integratedCoreValues = listOf(CoreValue.VATANSEVERLIK, CoreValue.SORUMLULUK),
            primarySkills = listOf(HistorySkill.KANIT_KULLANMA, HistorySkill.KRONOLOJIK_DUSUNME)
        ),

        // 12. Sınıf
        MaarifTheme(
            id = "T-12-2",
            gradeLevel = 12,
            themeNumber = 2,
            themeTitle = "Millî Mücadele ve Türk Bağımsızlık Ruhu",
            essentialIdea = "Milletin bağımsızlığını yine milletin azim ve kararı kurtarmış; adalet, fedakârlık ve hürriyet aşkıyla Türkiye Cumhuriyeti kurulmuştur.",
            targetOutcomes = listOf(
                MaarifOutcome("TAR.12.2.1", "Amasya Genelgesi, Erzurum ve Sivas Kongreleri kararlarını millî egemenlik ve tam bağımsızlık ilkeleri doğrultusunda analiz eder.", "Metin Tenkidi", "Tarihsel Sorgulama", "Hürriyet Eğilimi", "Vatanseverlik ve Sorumluluk"),
                MaarifOutcome("TAR.12.2.2", "Tekâlif-i Milliye Emirleri'nin millet-ordu dayanışması ve topyekûn kurtuluş mücadelesindeki yerini değerlendirir.", "Tarihsel Empati", "Kanıt Kullanma", "Fedakârlık", "Yardımseverlik ve Vatanseverlik")
            ),
            integratedCoreValues = listOf(CoreValue.VATANSEVERLIK, CoreValue.YARDIMSEVERLIK, CoreValue.ADALET),
            primarySkills = listOf(HistorySkill.TARIHSEL_SORGULAMA, HistorySkill.TARIHSEL_EMPATI)
        )
    )

    // === HİKÂYELEŞTİRİCİ, NÜKTELİ VE BEYİTLİ DERS ANLATIM MODÜLLERİ ===
    fun getStoryNarrativeForGrade(grade: Int): StorytellingLessonNarrative {
        return when (grade) {
            10 -> StorytellingLessonNarrative(
                themeTitle = "Beylikten Devlete Osmanlı Siyaseti",
                topicTitle = "İstimalet (Adalet & Hoşgörü) ve Gaza Ruhu",
                gradeLevel = 10,
                historicalMindsetAnalysis = "14. yüzyıl Osmanlı insanı kılıcı sadece fethetmek için değil, mazluma adalet dağıtmak ve töreyi ihya etmek için çekiyordu. Fethedilen yerdeki gayrimüslim halka dini serbestlik verilmesi (İstimalet), gönülleri fethetti.",
                narrativeStory = """
                    Değerli gençler! Osman Gazi Söğüt'te beyliğin temelini atarken kılıcın yanına teraziyi koydu. Bilecik fethedildiğinde pazarda bir Hristiyan tüccar ile bir Müslüman arasında anlaşmazlık çıkar. Osman Gazi tereddüt etmeden Hristiyan tüccarın hakkını teslim eder. 
                    Bunu gören ahali şaşırır! İşte bu adalet haberi Bizans kalelerine bir ok gibi değil, bir bahar rüzgarı gibi yayıldı. Kalelerin kapıları kılıçtan önce bu adaletle açıldı!
                """.trimIndent(),
                historicalAnecdoteOrHumor = """
                    🎭 Tarihsel Nükte (Fatih Sultan Mehmed ve Rum Mimar Mahkemesi):
                    İstanbul'un fethinden sonra Fatih Sultan Mehmed, cami sütununu istediğinden kısa kesen Rum mimarı cezalandırır. Rum mimar kadıya başvurur. Kadı Hızır Bey, koca Cihan Padişahı Fatih'i sanık sandalyesine oturtur ve 'Kısasa kısas!' hükmü verir. 
                    Fatih karara boyun eğer. Mimar ise bu adaleti görünce hayretle bağışlar ve Müslüman olur. İşte nükte buradadır: Padişah olsan da kadının hükmünden kaçamazsın!
                """.trimIndent(),
                concludingCoupletOrPoem = """
                    📜 Şeyh Edebali'nin Osman Gazi'ye Nasihati:
                    'İnsanı yaşat ki devlet yaşasın!
                    Öfke bize, uysallık sana;
                    Güceniklik bize, gönül almak sana;
                    Adaletten ayrılma ki mülkün daim olsun.'
                """.trimIndent(),
                mindMapSummary = listOf(
                    "1. Gaza ve Cihat: Fetihlerin manevi dinamizmi.",
                    "2. İstimalet Politikası: Gönül alma, din ve vicdan hürriyeti.",
                    "3. İskân Siyaseti: Balkanların imarı ve kalıcı Türk yurdu haline gelmesi.",
                    "4. Ahilik Teşkilatı: İktisadi ve ahlaki teşkilatlanma."
                ),
                criticalThinkingPrompt = "Sizce bir devleti yüzyıllarca ayakta tutan asıl güç ordularının büyüklüğü müdür, yoksa mahkemelerindeki adaletin terazisi midir?"
            )
            12 -> StorytellingLessonNarrative(
                themeTitle = "Millî Mücadele ve Bağımsızlık",
                topicTitle = "Milletin Azim ve Kararı: Amasya'dan Sivas'a",
                gradeLevel = 12,
                historicalMindsetAnalysis = "1919 Türkiyesi işgaller altındaydı ancak milletin bağrındaki istiklal ateşi sönmemişti. Mustafa Kemal Paşa, kurtuluşun saraydan değil doğrudan milletin iradesinden doğacağını görüyordu.",
                narrativeStory = """
                    Sevgili gençler! Mustafa Kemal Paşa 19 Mayıs 1919'da Samsun'a ayak bastığında ceplerinde fermanlar değil, milletine duyduğu sınırsız inanç vardı. Amasya'ya geçtiğinde öyle bir cümle yazdırdı ki tarihin akışı değişti: 'Milletin istiklâlini yine milletin azim ve kararı kurtaracaktır!' Bu cümle; saltanata değil, millete dayanan yeni bir devletin ilk doğum çığlığıydı.
                """.trimIndent(),
                historicalAnecdoteOrHumor = """
                    🎭 Tarihsel Hazırcevaplık (Atatürk ve İngiliz Subayı):
                    İşgal günlerinde İstanbul'da bir restoranda İngiliz subayları Mustafa Kemal'e kibirle bakarak garsona: 'Şu Türk subayına sor bakalım, bizim gibi muzaffer komutanların arasında ne arıyor?' der. 
                    Mustafa Kemal Paşa tebessüm eder ve tarihe geçecek şu cevabı verir: 'Kendilerine söyleyin; buranın asıl sahibi biziz, onlar ise burada sadece geçici birer misafirdir. Geldikleri gibi giderler!'
                """.trimIndent(),
                concludingCoupletOrPoem = """
                    📜 Mehmet Akif Ersoy - İstiklal Marşı:
                    'Ben ezelden beridir hür yaşadım, hür yaşarım.
                    Hangi çılgın bana zincir vuracakmış? Şaşarım!
                    Kükremiş sel gibiyim; bendimi çiğner, aşarım;
                    Yırtarım dağları, enginlere sığmam, taşarım.'
                """.trimIndent(),
                mindMapSummary = listOf(
                    "1. Havza & Amasya Genelgeleri: Millî uyanış ve kurtuluşun gerekçesi.",
                    "2. Erzurum Kongresi: Misakımillî sınırları ve mandacılığın reddi.",
                    "3. Sivas Kongresi: Temsil Heyeti'nin bütün vatanı temsil etmesi.",
                    "4. TBMM'nin Açılışı: Egemenliğin kayıtsız şartsız millete geçmesi."
                ),
                criticalThinkingPrompt = "Tekâlif-i Milliye Emirleri'nde Türk milletinin çarığını, çorabını ve ununu orduyla paylaşması hangi kök değerlerimizin en somut göstergesidir?"
            )
            else -> StorytellingLessonNarrative(
                themeTitle = "İlk ve Orta Çağlarda Türk Dünyası",
                topicTitle = "Töre, Kut ve Sosyal Devlet Anlayışı",
                gradeLevel = 9,
                historicalMindsetAnalysis = "Eski Türkler için devlet bir şahsın mülkü değil, milletin töreyle korunan ortak yuvasıydı. Hakan ancak halkını doyurduğu ve giydirdiği sürece hakandı.",
                narrativeStory = """
                    Gençler! Uçsuz bucaksız Avrasya bozkırlarında at koşturan atalarımız için en büyük erdem 'Töre' idi. Orhun Kitabeleri'nde Bilge Kağan taşa ne kazımış biliyor musunuz? 'Aç milleti doyurdum, çıplak milleti giydirdim, fakir milleti zengin kıldım.' İşte 1300 yıl önceden bugünün modern anayasalarına uzanan sosyal devlet köprüsü!
                """.trimIndent(),
                historicalAnecdoteOrHumor = """
                    🎭 Tarihsel Nükte (Vezir Tonyukuk'un Hazırcevaplığı):
                    Bir gün Çin elçisi vezir Tonyukuk'a kibirle der ki: 'Bizim saraylarımız altından, surlarımız taştandır. Siz neden taş duvarlar arkasında yaşamazsınız?'
                    Tonyukuk bıyık altından güler ve der ki: 'Sizin surlarınız sizi korumaya yetmez, hapseder! Bizim atlarımız rüzgar, gökyüzü çadırımız, töremiz ise surumuzdur. Rüzgarı hangi taş duvar hapsedebilmiş ki?'
                """.trimIndent(),
                concludingCoupletOrPoem = """
                    📜 Yusuf Has Hacib - Kutadgu Bilig:
                    'Beyliğin temeli adalet üzerinedir;
                    Bey adil olursa beylik uzun sürer,
                    Zulüm ederse beylik tez yıkılır.
                    Gök çökerse yer yarılır, töre kalırsa el kurtulur.'
                """.trimIndent(),
                mindMapSummary = listOf(
                    "1. Kut İnancı: Egemenliğin ilahi kaynağı ve meşruiyet şartı.",
                    "2. Töre Hukuku: Yazısız ama hakanı dahi bağlayan amir kurallar bütünü.",
                    "3. Kurultay (Toy): İstişare meclisi ve demokratik danışma kültürü.",
                    "4. Ordu-Millet: Kadın-erkek her ferdin vatan savunmasında yer alması."
                ),
                criticalThinkingPrompt = "Eski Türklerdeki 'Töre' kavramı ile günümüz 'Hukukun Üstünlüğü' ilkesini karşılaştırdığınızda hangi süreklilikleri görüyorsunuz?"
            )
        }
    }

    // === MAARİF MODELİ YILLIK DERS PLANI (9, 10, 11, 12. SINIF) ===
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

    // === GÜNLÜK DERS PLANI (5E MODELİ, FARKLILAŞTIRILMIŞ EĞİTİM & ÇIKIŞ KARTI) ===
    fun getSampleDailyLessonPlan(): DailyLessonPlan {
        return DailyLessonPlan(
            id = "DLP-9-HIST-01",
            gradeLevel = 9,
            themeName = "3. TEMA: Türk Dünyasında Devlet ve Toplum",
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
                InstructionalStep("1. Giriş ve Güdüleme (Problem Durumu)", 7, "Tarihsel nükteyi anlatır, Orhun Abideleri'nden 'Aç milleti doyurdum, çıplak milleti giydirdim' sözünü tahtaya yazar.", "Sözün sosyal devlet anlayışıyla ilişkisi üzerine fikir yürütür.", "Merak uyandırma ve ön bilgileri harekete geçirme."),
                InstructionalStep("2. Keşfetme ve Kaynak Analizi (İstasyon)", 15, "Kutadgu Bilig ve Orhun Yazıtları'ndan pasajlar dağıtır, istasyon tekniği ile rehberlik eder.", "Kaynaklardaki 'Töre', 'Kut', 'Toy' ve 'Adalet' kavramlarını eşleştirir.", "Tarihsel kanıt kullanma ve kaynak tenkiti becerisini geliştirme."),
                InstructionalStep("3. Açıklama ve Derinleştirme", 10, "Bulguları tahtada kavram haritasına dönüştürür, Yusuf Has Hacib'in adalet ilkesini açıklar.", "Zihin haritası oluşturur, karşılaştırma yapar.", "Kavram yanılgılarını giderme ve bilgiyi yapılandırma."),
                InstructionalStep("4. Değerlendirme & Çıkış Kartı", 8, "2 soruluk çıkış kartı dağıtır.", "'Bugün öğrendiğim en çarpıcı kavram' ve 'Töre ile bugünkü anayasa arasındaki benzerlik' yazar.", "Süreç odaklı formatif değerlendirme.")
            ),
            coreValuesIntegrated = listOf(CoreValue.ADALET, CoreValue.SORUMLULUK, CoreValue.VATANSEVERLIK),
            differentiatedInstructionNotes = "Zenginleştirme: İleri düzey öğrenciler için Kutadgu Bilig'deki adaleti temsil eden 'Kün Toğdı' karakteri ile Platon'un Devlet felsefesi karşılaştırılır. Destekleme: Temel kavram eşleştirme kartları verilir.",
            assessmentMethod = "Çıkış Kartı + Süreç Odaklı Rubrik + Öğrenci Öz Değerlendirme Formu"
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
