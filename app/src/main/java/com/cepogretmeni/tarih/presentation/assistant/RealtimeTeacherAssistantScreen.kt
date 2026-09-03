package com.cepogretmeni.tarih.presentation.assistant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cepogretmeni.tarih.data.curriculum.MaarifCurriculumData
import com.cepogretmeni.tarih.domain.model.TeacherProfile
import com.cepogretmeni.tarih.presentation.profile.TeacherProfileDialog

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val actionType: AssistantActionType? = null,
    val generatedCustomPlan: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender { TEACHER_AI, USER }

enum class AssistantActionType {
    DAILY_PLAN, ANNUAL_PLAN, ZUMRE_RECORD, EXAM_PAPER, REGULATION_GUIDE, STORY_NARRATIVE
}

enum class AssistantWizardStep {
    IDLE,
    WAITING_DAILY_PLAN_DETAILS,
    WAITING_EXAM_DETAILS,
    WAITING_ANNUAL_PLAN_DETAILS
}

/**
 * Maarif Modeli İnteraktif Sesli Komut ve Soru-Cevap Planlama Asistanı
 * 1. Kullanıcıdan sesli/yazılı komut alır.
 * 2. Eksik bilgileri adım adım sorar (Sınıf, konu, ders saati, senaryo).
 * 3. Cevaplara göre kişiselleştirilmiş Maarif planı üretir.
 * 4. %100 telefon yerel şifreli hafızasında saklar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealtimeTeacherAssistantScreen(
    selectedGrade: Int = 9,
    profile: TeacherProfile,
    onProfileChange: (TeacherProfile) -> Unit = {},
    onGradeChange: (Int) -> Unit = {},
    onExportPdfAction: (AssistantActionType, Int) -> Unit = { _, _ -> },
    onExportWordAction: (AssistantActionType, Int) -> Unit = { _, _ -> },
    onSaveToAppAction: (AssistantActionType, Int, String) -> Unit = { _, _, _ -> },
    onStartListening: () -> Unit = {},
    onStopListening: () -> Unit = {}
) {
    var isListening by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var showProfileDialog by remember { mutableStateOf(false) }
    var wizardState by remember { mutableStateOf(AssistantWizardStep.IDLE) }
    var savedSuccessMessage by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                sender = MessageSender.TEACHER_AI,
                text = "Sayın öğretmenim, hoş geldiniz! Ben Türkiye Yüzyılı Maarif Modeli Asistanınız.\n\nİstediğiniz işlemi mikrofona basarak sesli söyleyebilir veya yazabilirsiniz. Ne hazırlamamı istersiniz? (Örn: 'Ders planı hazırla', 'Yazılı sınav yap', 'Yıllık plan çıkar'). Size gerekli soruları sorup tam istediğiniz gibi hazırlayacağım."
            )
        )
    }

    // Mikrofon nabız animasyonu
    val infiniteTransition = rememberInfiniteTransition(label = "micPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isListening) 1.25f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    if (showProfileDialog) {
        TeacherProfileDialog(
            currentProfile = profile,
            onDismiss = { showProfileDialog = false },
            onSaveProfile = { newProfile ->
                onProfileChange(newProfile)
                showProfileDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2563EB),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Maarif Tarih Asistanı",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Text(
                                text = "${profile.schoolName} • $selectedGrade. Sınıf",
                                fontSize = 11.sp,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }
                },
                actions = {
                    GradeDropdownMenu(
                        currentGrade = selectedGrade,
                        onSelectGrade = onGradeChange
                    )

                    IconButton(onClick = { showProfileDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Okul/Öğretmen Bilgileri",
                            tint = Color(0xFFFBBF24)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF0B0F19))
        ) {
            // Hızlı Başlatıcı Çubuğu
            QuickActionsBar(
                onSelectAction = { actionType ->
                    when (actionType) {
                        AssistantActionType.DAILY_PLAN -> {
                            wizardState = AssistantWizardStep.WAITING_DAILY_PLAN_DETAILS
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    sender = MessageSender.TEACHER_AI,
                                    text = "📝 Günlük Ders Planı hazırlığı için lütfen şu 3 bilgiyi sesli söyleyin veya yazın:\n\n1️⃣ Hangi sınıf? (9, 10, 11 veya 12)\n2️⃣ İşleyeceğiniz konu nedir?\n3️⃣ Kaç ders saati planlayalım? (Örn: '2 saat')"
                                )
                            )
                        }
                        AssistantActionType.EXAM_PAPER -> {
                            wizardState = AssistantWizardStep.WAITING_EXAM_DETAILS
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    sender = MessageSender.TEACHER_AI,
                                    text = "🎯 Yazılı Sınav Kağıdı & Rubrik hazırlığı için:\n\n1️⃣ Hangi sınıf düzeyi?\n2️⃣ MEB Senaryo 1 mi yoksa Senaryo 2 mi olsun?\n3️⃣ Soru sayısı ve odaklanmak istediğiniz tema nedir?"
                                )
                            )
                        }
                        AssistantActionType.ANNUAL_PLAN -> {
                            val annualPlan = MaarifCurriculumData.getFullAnnualPlan(selectedGrade)
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    sender = MessageSender.TEACHER_AI,
                                    text = "📅 **${profile.schoolName} ${selectedGrade}. SINIF MAARİF MODELİ YILLIK PLANI**\n\n36 haftalık öğrenme çıktıları, süreç bileşenleri, alan becerileri ve kök değer dağılımı hazırlandı. Aşağıdan indirebilir veya kaydedebilirsiniz.",
                                    actionType = AssistantActionType.ANNUAL_PLAN
                                )
                            )
                        }
                        AssistantActionType.ZUMRE_RECORD -> {
                            val zumre = MaarifCurriculumData.getSampleZumreMeetingRecord()
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    sender = MessageSender.TEACHER_AI,
                                    text = "📑 **${profile.schoolName.uppercase()} TARİH ZÜMRE ÖĞRETMENLER KURULU TUTANAĞI**\n\nZümre Başkanı: ${profile.teacherName}  |  Okul Müdürü: ${profile.principalName}\nOrtaöğretim Kurumları Yönetmeliği sınıf geçme ve ortak sınav maddeleriyle hazırlandı.",
                                    actionType = AssistantActionType.ZUMRE_RECORD
                                )
                            )
                        }
                        AssistantActionType.REGULATION_GUIDE -> {
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    sender = MessageSender.TEACHER_AI,
                                    text = "⚖️ **MEB ORTAÖĞRETİM KURUMLARI SINIF GEÇME REHBERİ:**\n\n• Yıl sonu başarı puanı en az 50 olan öğrenci doğrudan geçer.\n• En fazla 3 dersten zayıfı olan sorumlu geçer.\n• Toplam sorumlu ders 6'yı geçemez.\n• Ortak sınavlar MEB senaryolarına göre sadece açık uçlu yapılır.",
                                    actionType = AssistantActionType.REGULATION_GUIDE
                                )
                            )
                        }
                        AssistantActionType.STORY_NARRATIVE -> {
                            val story = MaarifCurriculumData.getStoryNarrativeForGrade(selectedGrade)
                            messages.add(
                                ChatMessage(
                                    id = System.currentTimeMillis().toString(),
                                    sender = MessageSender.TEACHER_AI,
                                    text = "📖 **${selectedGrade}. SINIF DERS ANLATIM ÖZETİ:** ${story.topicTitle}\n\n🏛️ **Zihniyet:** ${story.historicalMindsetAnalysis}\n\n${story.narrativeStory}\n\n${story.historicalAnecdoteOrHumor}\n\n${story.concludingCoupletOrPoem}",
                                    actionType = AssistantActionType.STORY_NARRATIVE
                                )
                            )
                        }
                    }
                }
            )

            // Kayıt Başarı Uyarısı
            AnimatedVisibility(visible = savedSuccessMessage != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF065F46)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = savedSuccessMessage ?: "",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Mesaj Akışı
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(messages) { msg ->
                    ChatBubbleItem(
                        message = msg,
                        selectedGrade = selectedGrade,
                        onExportPdf = { action -> onExportPdfAction(action, selectedGrade) },
                        onExportWord = { action -> onExportWordAction(action, selectedGrade) },
                        onSaveToApp = { action, text ->
                            onSaveToAppAction(action, selectedGrade, text)
                            savedSuccessMessage = "Belge telefonun yerel şifreli hafızasına kaydedildi. 'Kayıtlı Belgelerim' sekmesinden istediğiniz an açabilirsiniz."
                        }
                    )
                }
            }

            // Alt Giriş ve Sesli Komut Çubuğu
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1E293B),
                shadowElevation = 16.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text("Sesli komut verin veya yazın...", fontSize = 13.sp, color = Color(0xFF64748B)) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF334155)
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Mikrofon Butonu (Sesli Komut Girişi)
                    IconButton(
                        onClick = {
                            if (isListening) {
                                isListening = false
                                onStopListening()
                            } else {
                                isListening = true
                                onStartListening()
                            }
                        },
                        modifier = Modifier
                            .scale(if (isListening) pulseScale else 1f)
                            .size(46.dp)
                            .background(
                                color = if (isListening) Color(0xFFDC2626) else Color(0xFF2563EB),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = "Sesli Komut",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Gönder Butonu
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                val userQuery = inputText
                                messages.add(ChatMessage(id = System.currentTimeMillis().toString(), sender = MessageSender.USER, text = userQuery))
                                inputText = ""

                                val responsePair = processTeacherInput(userQuery, wizardState, selectedGrade, profile)
                                wizardState = responsePair.first
                                messages.add(
                                    ChatMessage(
                                        id = (System.currentTimeMillis() + 1).toString(),
                                        sender = MessageSender.TEACHER_AI,
                                        text = responsePair.second,
                                        actionType = responsePair.third
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFF059669), shape = CircleShape)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Gönder", tint = Color.White)
                    }
                }
            }
        }
    }
}

/**
 * Kullanıcı Girişini ve İnteraktif Soru-Cevap Akışını Yöneten Zeka Motoru
 */
fun processTeacherInput(
    query: String,
    currentStep: AssistantWizardStep,
    selectedGrade: Int,
    profile: TeacherProfile
): Triple<AssistantWizardStep, String, AssistantActionType?> {
    val q = query.lowercase()

    return when {
        currentStep == AssistantWizardStep.WAITING_DAILY_PLAN_DETAILS || (q.contains("plan") && (q.contains("saat") || q.contains("sınıf") || q.contains("konu"))) -> {
            // Kullanıcı detayları verdi, planı oluştur
            val targetGrade = extractGradeNumber(q) ?: selectedGrade
            val targetHours = extractHoursNumber(q) ?: profile.defaultWeeklyHours
            val customTopic = extractTopicText(query) ?: "İlk Türk Devletlerinde Töre ve Kut Anlayışı"

            val plan = MaarifCurriculumData.generateMultiHourDailyPlan(targetGrade, targetHours, profile)
            val output = buildString {
                append("📋 **${targetGrade}. SINIF MAARİF MODELİ GÜNLÜK PLANI ($targetHours SAAT)**\n")
                append("🏫 **Okul:** ${profile.schoolName}  |  **Öğretmen:** ${profile.teacherName}\n")
                append("📖 **Konu:** $customTopic\n\n")
                plan.lessonHours.forEach { h ->
                    append("📌 **${h.hourNumber}. DERS SAATİ:** ${h.hourTitle}\n")
                    append("• **Öğrenme Çıktısı:** ${h.learningOutcomes}\n")
                    append("• **Güdüleme & Nükte:** ${h.hookAndMotivation}\n")
                    append("• **İşleniş (İstasyon):** ${h.instructionalProcess}\n")
                    append("• **Çıkış Kartı:** ${h.evaluationAndExitTicket}\n\n")
                }
                append("✨ **Farklılaştırılmış Öğretim:** ${plan.differentiatedInstruction}\n")
                append("✍️ **İmzalar:** ${profile.teacherName} (Tarih Öğretmeni)  •  ${profile.principalName} (Okul Müdürü)")
            }
            Triple(AssistantWizardStep.IDLE, output, AssistantActionType.DAILY_PLAN)
        }

        currentStep == AssistantWizardStep.WAITING_EXAM_DETAILS || (q.contains("sınav") && (q.contains("senaryo") || q.contains("soru"))) -> {
            val targetGrade = extractGradeNumber(q) ?: selectedGrade
            val output = "🎯 **${profile.schoolName.uppercase()} ${targetGrade}. SINIF 1. DÖNEM 1. ORTAK YAZILI SINAVI & RUBRİK**\n\nMEB Senaryo 1 konu soru dağılım tablosuna tam uyumlu açık uçlu sorular ve dereceli puanlama anahtarı hazırlandı."
            Triple(AssistantWizardStep.IDLE, output, AssistantActionType.EXAM_PAPER)
        }

        q.contains("plan") || q.contains("günlük") -> {
            val prompt = "📝 Günlük Ders Planınızı hazırlayabilmem için lütfen şu bilgileri belirtin:\n\n1️⃣ Kaçıncı sınıf? (9, 10, 11 veya 12)\n2️⃣ İşlenecek konu nedir?\n3️⃣ Kaç ders saati hazırlayalım? (Örn: '2 saat')"
            Triple(AssistantWizardStep.WAITING_DAILY_PLAN_DETAILS, prompt, null)
        }

        q.contains("sınav") || q.contains("yazılı") || q.contains("soru") -> {
            val prompt = "🎯 Yazılı Sınav Kağıdı hazırlamam için:\n\n1️⃣ Hangi sınıf düzeyi?\n2️⃣ MEB Senaryo 1 mi Senaryo 2 mi olsun?\n3️⃣ Kaç soru olsun?"
            Triple(AssistantWizardStep.WAITING_EXAM_DETAILS, prompt, null)
        }

        q.contains("yıllık") -> {
            val output = "📅 **${profile.schoolName} ${selectedGrade}. SINIF MAARİF MODELİ YILLIK PLANI**\n\n36 haftalık kazanım, süreç bileşenleri ve kök değer dağılımı hazırlandı."
            Triple(AssistantWizardStep.IDLE, output, AssistantActionType.ANNUAL_PLAN)
        }

        q.contains("zümre") -> {
            val output = "📑 **${profile.schoolName.uppercase()} TARİH ZÜMRE TUTANAĞI**\n\nZümre Başkanı: ${profile.teacherName}  |  Okul Müdürü: ${profile.principalName}\nOrtaöğretim Kurumları Yönetmeliği hükümlerine uygun olarak düzenlendi."
            Triple(AssistantWizardStep.IDLE, output, AssistantActionType.ZUMRE_RECORD)
        }

        else -> {
            val guide = "Ne hazırlamamı istersiniz sayın öğretmenim? (Örn: '10. sınıf Osmanlı kuruluşu için 2 saatlik ders planı yap' veya '9. sınıf ortak sınav hazırla')"
            Triple(AssistantWizardStep.IDLE, guide, null)
        }
    }
}

fun extractGradeNumber(text: String): Int? {
    return when {
        text.contains("9") -> 9
        text.contains("10") -> 10
        text.contains("11") -> 11
        text.contains("12") -> 12
        else -> null
    }
}

fun extractHoursNumber(text: String): Int? {
    return when {
        text.contains("1 saat") || text.contains("1 ders") -> 1
        text.contains("2 saat") || text.contains("2 ders") -> 2
        text.contains("3 saat") || text.contains("3 ders") -> 3
        text.contains("4 saat") || text.contains("4 ders") -> 4
        else -> null
    }
}

fun extractTopicText(text: String): String? {
    if (text.length > 5) return text
    return null
}

@Composable
private fun GradeDropdownMenu(
    currentGrade: Int,
    onSelectGrade: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Surface(
            onClick = { expanded = true },
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF1E293B),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$currentGrade. Sınıf", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(16.dp))
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1E293B))
        ) {
            listOf(9, 10, 11, 12).forEach { grade ->
                DropdownMenuItem(
                    text = { Text("$grade. Sınıf Tarih", color = Color.White, fontSize = 13.sp) },
                    onClick = {
                        onSelectGrade(grade)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun QuickActionsBar(onSelectAction: (AssistantActionType) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF131C2E))
            .padding(vertical = 10.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ActionChip(
                title = "📝 Günlük Plan Hazırla",
                color = Color(0xFF2563EB),
                onClick = { onSelectAction(AssistantActionType.DAILY_PLAN) }
            )
        }
        item {
            ActionChip(
                title = "🎯 Sınav & Rubrik Yap",
                color = Color(0xFF059669),
                onClick = { onSelectAction(AssistantActionType.EXAM_PAPER) }
            )
        }
        item {
            ActionChip(
                title = "📅 Yıllık Plan İndir",
                color = Color(0xFF7C3AED),
                onClick = { onSelectAction(AssistantActionType.ANNUAL_PLAN) }
            )
        }
        item {
            ActionChip(
                title = "📑 Zümre Tutanağı",
                color = Color(0xFFD97706),
                onClick = { onSelectAction(AssistantActionType.ZUMRE_RECORD) }
            )
        }
        item {
            ActionChip(
                title = "⚖️ Sınıf Geçme",
                color = Color(0xFFDB2777),
                onClick = { onSelectAction(AssistantActionType.REGULATION_GUIDE) }
            )
        }
    }
}

@Composable
private fun ActionChip(title: String, color: Color, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.2f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun ChatBubbleItem(
    message: ChatMessage,
    selectedGrade: Int,
    onExportPdf: (AssistantActionType) -> Unit,
    onExportWord: (AssistantActionType) -> Unit,
    onSaveToApp: (AssistantActionType, String) -> Unit
) {
    val isAi = message.sender == MessageSender.TEACHER_AI

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isAi) Alignment.Start else Alignment.End
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isAi) 2.dp else 16.dp,
                bottomEnd = if (isAi) 16.dp else 2.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isAi) Color(0xFF1E293B) else Color(0xFF2563EB)
            ),
            modifier = Modifier
                .widthIn(max = 350.dp)
                .shadow(6.dp, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (isAi) {
                    Text(
                        text = "Maarif Modeli Asistanı • $selectedGrade. Sınıf",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF38BDF8),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                // PDF, WORD ve UYGULAMAYA KAYDET BUTONLARI
                message.actionType?.let { action ->
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = { onExportPdf(action) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "PDF", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { onExportWord(action) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Description, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Word", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { onSaveToApp(action, message.text) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Kaydet", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
