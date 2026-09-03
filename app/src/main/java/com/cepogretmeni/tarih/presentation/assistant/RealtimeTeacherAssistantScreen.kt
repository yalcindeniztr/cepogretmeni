package com.cepogretmeni.tarih.presentation.assistant

import androidx.compose.animation.AnimatedVisibility
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
    val isSavedToApp: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender { TEACHER_AI, USER }

enum class AssistantActionType {
    STORY_NARRATIVE, DAILY_PLAN, ANNUAL_PLAN, ZUMRE_RECORD, EXAM_PAPER, REGULATION_GUIDE
}

/**
 * Maarif Modeli Tarih Öğretmeni Asistanı
 * Sessiz ve profesyonel mod: Yalnızca kullanıcının talep ettiği belgeleri hazırlar,
 * PDF, Word (.docx) ve Uygulama İçi Kaydetme desteği sunar.
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
    onSaveToAppAction: (AssistantActionType, Int, String) -> Unit = { _, _, _ -> }
) {
    var inputText by remember { mutableStateOf("") }
    var showProfileDialog by remember { mutableStateOf(false) }
    var savedSuccessMessage by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                sender = MessageSender.TEACHER_AI,
                text = "Hoş geldiniz kıymetli öğretmenim. Türkiye Yüzyılı Maarif Modeli Tarih Dersi planlama motorunuz hazırdır. Haftalık ders saati sayısına göre çoklu saatli günlük planlar, 36 haftalık yıllık planlar, MEB senaryolu açık uçlu sınav ve rubrikler ile zümre tutanaklarını hazırlayabilir, PDF veya Word (.docx) olarak anında indirebilir ya da cihazınıza şifreli kaydedebilirsiniz."
            )
        )
    }

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
                    // Sınıf Seçici
                    GradeDropdownMenu(
                        currentGrade = selectedGrade,
                        onSelectGrade = onGradeChange
                    )

                    // Okul & Öğretmen Profil Ayarları Butonu
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
            // Hızlı Eylem Çubuğu
            QuickActionsBar(
                weeklyHours = profile.defaultWeeklyHours,
                onSelectAction = { actionType ->
                    val response = when (actionType) {
                        AssistantActionType.DAILY_PLAN -> {
                            val plan = MaarifCurriculumData.generateMultiHourDailyPlan(selectedGrade, profile.defaultWeeklyHours, profile)
                            buildString {
                                append("📋 **${plan.gradeLevel}. SINIF GÜNLÜK DERS PLANI (${plan.weeklyHoursCount} DERS SAATİ / HAFTALIK)**\n")
                                append("🏫 **Okul:** ${profile.schoolName}  |  **Öğretmen:** ${profile.teacherName}\n")
                                append("🎯 **Tema:** ${plan.themeName}\n")
                                append("📖 **Konu:** ${plan.topicTitle}\n\n")
                                plan.lessonHours.forEach { h ->
                                    append("📌 **${h.hourNumber}. DERS SAATİ:** ${h.hourTitle}\n")
                                    append("• **Öğrenme Çıktısı:** ${h.learningOutcomes}\n")
                                    append("• **Güdüleme / Nükte:** ${h.hookAndMotivation}\n")
                                    append("• **İşleniş:** ${h.instructionalProcess}\n")
                                    append("• **Çıkış Kartı:** ${h.evaluationAndExitTicket}\n\n")
                                }
                                append("✨ **Farklılaştırılmış Öğretim:** ${plan.differentiatedInstruction}\n")
                                append("✍️ **İmzalar:** ${profile.teacherName} (Tarih Öğretmeni)  •  ${profile.principalName} (Okul Müdürü)")
                            }
                        }
                        AssistantActionType.ANNUAL_PLAN -> {
                            "📅 **${selectedGrade}. SINIF MAARİF MODELİ YILLIK PLANI (36 HAFTALIK)**\n\nKurum: ${profile.schoolName}\nTarih Öğretmeni: ${profile.teacherName}  |  Okul Müdürü: ${profile.principalName}\n\nÖğrenme çıktıları, süreç bileşenleri, alan becerileri ve kök değer dağılımı yatay A4 PDF ve Word olarak hazırlanmıştır."
                        }
                        AssistantActionType.ZUMRE_RECORD -> {
                            val zumre = MaarifCurriculumData.getSampleZumreMeetingRecord()
                            "📑 **${profile.schoolName.uppercase()} TARİH ZÜMRE ÖĞRETMENLER KURULU TUTANAĞI**\n\nToplantı Tarihi: ${zumre.meetingDate}\nZümre Başkanı: ${profile.teacherName}  |  Okul Müdürü: ${profile.principalName}\n\nMaarif Modeli ve Ortaöğretim Kurumları Yönetmeliği Madde 45, 50, 56 sınıf geçme esaslarına göre tamamlanmıştır."
                        }
                        AssistantActionType.EXAM_PAPER -> {
                            "🎯 **${profile.schoolName.uppercase()} ${selectedGrade}. SINIF 1. DÖNEM 1. ORTAK YAZILI SINAVI & RUBRİK**\n\nMEB Senaryo 1 konu soru dağılım tablosuna tam uyumlu 4 adet yeni nesil açık uçlu soru ve ayrıntılı Dereceli Puanlama Anahtarı (Rubrik) hazırlanmıştır."
                        }
                        AssistantActionType.STORY_NARRATIVE -> {
                            val story = MaarifCurriculumData.getStoryNarrativeForGrade(selectedGrade)
                            "📖 **${selectedGrade}. SINIF DERS ANLATIM ÖZETİ:** ${story.topicTitle}\n\n🏛️ **Zihniyet:** ${story.historicalMindsetAnalysis}\n\n${story.narrativeStory}\n\n${story.historicalAnecdoteOrHumor}\n\n${story.concludingCoupletOrPoem}"
                        }
                        AssistantActionType.REGULATION_GUIDE -> {
                            "⚖️ **MEB ORTAÖĞRETİM KURUMLARI SINIF GEÇME YÖNETMELİĞİ:**\n\n• Yıl sonu ortalaması en az 50 olan öğrenci doğrudan sınıf geçer.\n• En fazla 3 dersten başarısız olan sorumlu geçer.\n• Alt sınıflar dahil toplam sorumlu ders sayısı 6'yı aşamaz.\n• Yazılı sınavlar MEB senaryo tablolarına göre açık uçlu yapılmak zorundadır."
                        }
                    }

                    messages.add(
                        ChatMessage(
                            id = System.currentTimeMillis().toString(),
                            sender = MessageSender.TEACHER_AI,
                            text = response,
                            actionType = actionType
                        )
                    )
                }
            )

            // Başarılı Kayıt Bildirimi
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
                            savedSuccessMessage = "Belge uygulama içine güvenle kaydedildi. 'Kayıtlı Belgelerim' sekmesinden dilediğiniz zaman erişebilirsiniz."
                        }
                    )
                }
            }

            // Alt Metin Giriş Çubuğu
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
                        placeholder = { Text("Hazırlatmak istediğiniz konuyu veya planı yazın...", fontSize = 13.sp, color = Color(0xFF64748B)) },
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

                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                val userQuery = inputText
                                messages.add(ChatMessage(id = System.currentTimeMillis().toString(), sender = MessageSender.USER, text = userQuery))
                                inputText = ""

                                val aiReply = generateMaarifCustomPlanResponse(userQuery, selectedGrade, profile)
                                messages.add(
                                    ChatMessage(
                                        id = (System.currentTimeMillis() + 1).toString(),
                                        sender = MessageSender.TEACHER_AI,
                                        text = aiReply,
                                        actionType = AssistantActionType.DAILY_PLAN
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
private fun QuickActionsBar(
    weeklyHours: Int,
    onSelectAction: (AssistantActionType) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF131C2E))
            .padding(vertical = 10.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ActionChip(
                title = "📝 Günlük Plan ($weeklyHours Saat)",
                color = Color(0xFF2563EB),
                onClick = { onSelectAction(AssistantActionType.DAILY_PLAN) }
            )
        }
        item {
            ActionChip(
                title = "📅 Yıllık Plan",
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
                title = "🎯 Sınav & Rubrik",
                color = Color(0xFF059669),
                onClick = { onSelectAction(AssistantActionType.EXAM_PAPER) }
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

/**
 * Kullanıcı isteğine özel Maarif Modeli ders planı yanıtı
 */
fun generateMaarifCustomPlanResponse(query: String, grade: Int, profile: TeacherProfile): String {
    val plan = MaarifCurriculumData.generateMultiHourDailyPlan(grade, profile.defaultWeeklyHours, profile)
    return buildString {
        append("📋 **${grade}. SINIF ÖZEL GÜNLÜK DERS PLANI (${profile.defaultWeeklyHours} SAAT)**\n")
        append("🏫 **Kurum:** ${profile.schoolName}  |  **Tarih Öğretmeni:** ${profile.teacherName}\n")
        append("🎯 **Talep Edilen Konu:** $query\n")
        append("📖 **Maarif Teması:** ${plan.themeName}\n\n")
        plan.lessonHours.forEach { h ->
            append("📌 **${h.hourNumber}. DERS SAATİ:** ${h.hourTitle}\n")
            append("• **Öğrenme Çıktısı:** ${h.learningOutcomes}\n")
            append("• **Güdüleme / Nükte:** ${h.hookAndMotivation}\n")
            append("• **İşleniş:** ${h.instructionalProcess}\n")
            append("• **Çıkış Kartı:** ${h.evaluationAndExitTicket}\n\n")
        }
        append("✨ **Farklılaştırılmış Öğretim:** ${plan.differentiatedInstruction}\n")
        append("✍️ **İmzalar:** ${profile.teacherName} (Tarih Öğretmeni)  •  ${profile.principalName} (Okul Müdürü)")
    }
}
