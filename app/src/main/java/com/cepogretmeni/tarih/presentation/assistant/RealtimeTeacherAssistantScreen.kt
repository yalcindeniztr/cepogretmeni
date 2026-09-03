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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cepogretmeni.tarih.data.curriculum.MaarifCurriculumData

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val actionType: AssistantActionType? = null,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender { TEACHER_AI, USER }

enum class AssistantActionType {
    DAILY_PLAN, ANNUAL_PLAN, ZUMRE_RECORD, EXAM_PAPER, REGULATION_GUIDE
}

/**
 * Maarif Modeli Gerçek Zamanlı Konuşan ve Cevap Veren Tarih Öğretmeni Asistanı
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealtimeTeacherAssistantScreen(
    onExportPdfAction: (AssistantActionType) -> Unit = {},
    onVoiceSpeak: (String) -> Unit = {},
    onStartListening: () -> Unit = {},
    onStopListening: () -> Unit = {}
) {
    var isListening by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                sender = MessageSender.TEACHER_AI,
                text = "Selamlar kıymetli meslektaşım ve sevgili öğrencim! Ben Türkiye Yüzyılı Maarif Modeli Tarih Asistanınız. Müfredat planlamasından sınıf geçme yönetmeliğine, nükte ve beyitlerle zenginleştirilmiş ders anlatımlarından MEB senaryolu sınav ve rubrik üretimine kadar her an yanınızdayım. Bugün hangi ders veya plan üzerinde çalışıyoruz?"
            )
        )
    }

    // Mikrofon dalgalanma animasyonu
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2563EB),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.RecordVoiceOver,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Tarih Cep Öğretmeni",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Maarif Modeli & Mevzuat Uzmanı",
                                fontSize = 11.sp,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val latestAiMsg = messages.lastOrNull { it.sender == MessageSender.TEACHER_AI }?.text
                        latestAiMsg?.let { onVoiceSpeak(it) }
                    }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Sesli Oku",
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
            // Hızlı Eylem Çubuğu (Quick Action Bar)
            QuickActionsBar(
                onSelectAction = { actionType ->
                    val response = when (actionType) {
                        AssistantActionType.DAILY_PLAN -> {
                            "Hemen 9. Sınıf 'Eski Türklerde Töre ve Sosyal Devlet' temalı MEB Maarif Modeli günlük ders planını hazırladım. 40 dakikalık istasyon etkinliği, Nasreddin Hoca nükte güdülemesi ve çıkış kartı hazır! Aşağıdaki butondan A4 PDF çıktısını alabilirsiniz."
                        }
                        AssistantActionType.ANNUAL_PLAN -> {
                            "2026-2027 Maarif Modeli 9. Sınıf Tarih Dersi Yıllık Planı (36 haftalık kazanım ve kök değer dağılımı) oluşturuldu. Yatay A4 formatında yazdırmaya hazır."
                        }
                        AssistantActionType.ZUMRE_RECORD -> {
                            "1. Dönem Başı Tarih Zümre Öğretmenler Kurulu Kararları ve Tutanağı, Ortaöğretim Kurumları Yönetmeliği Madde 45 ve 56 sınıf geçme esaslarına göre tamamlandı."
                        }
                        AssistantActionType.EXAM_PAPER -> {
                            "MEB Senaryo 1 konu soru dağılım tablosuna tam uyumlu 4 adet yeni nesil açık uçlu soru ve ayrıntılı Dereceli Puanlama Anahtarı (Rubrik) hazırlandı."
                        }
                        AssistantActionType.REGULATION_GUIDE -> {
                            "MEB Sınıf Geçme Yönetmeliği Rehberi: Yıl sonu ortalaması en az 50 olanlar doğrudan geçer. En fazla 3 zayıfı olan sorumlu geçer. Toplam sorumlu ders 6'yı geçemez."
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
                    onVoiceSpeak(response)
                }
            )

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
                        onExportPdf = { action -> onExportPdfAction(action) },
                        onSpeakText = { onVoiceSpeak(msg.text) }
                    )
                }
            }

            // Alt Giriş ve Mikrofon Çubuğu
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
                        placeholder = { Text("Sorunuzu yazın veya mikrofona dokunun...", fontSize = 13.sp, color = Color(0xFF64748B)) },
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

                    // Mikrofon Butonu (Animasyonlu)
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
                            contentDescription = "Mikrofon",
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

                                // Akıllı Maarif Modeli Yanıtı Üretimi
                                val aiReply = generateHistoryTeacherResponse(userQuery)
                                messages.add(ChatMessage(id = (System.currentTimeMillis() + 1).toString(), sender = MessageSender.TEACHER_AI, text = aiReply))
                                onVoiceSpeak(aiReply)
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFF059669), shape = CircleShape)
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Gönder", tint = Color.White)
                    }
                }
            }
        }
    }
}

/**
 * Hızlı Eylemler Yatay Çubuğu
 */
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
                title = "📝 Günlük Plan",
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
    onExportPdf: (AssistantActionType) -> Unit,
    onSpeakText: () -> Unit
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
                .widthIn(max = 320.dp)
                .shadow(6.dp, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (isAi) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tarih Öğretmeni",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8),
                            fontSize = 12.sp
                        )
                        IconButton(onClick = onSpeakText, modifier = Modifier.size(24.dp)) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 13.5.sp,
                    lineHeight = 19.sp
                )

                // PDF Çıktı Butonu (Eğer bir idari belge üretilmişse)
                message.actionType?.let { action ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { onExportPdf(action) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Resmî MEB PDF'ini İndir / Yazdır", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * Maarif Modeli ve Tarih Öğretmeni zekasını simüle eden yanıt üretici
 */
fun generateHistoryTeacherResponse(query: String): String {
    val q = query.lowercase()
    return when {
        q.contains("plan") || q.contains("ders") -> {
            "Maarif Modeli'nde ders planı hazırlarken merkezimize ezber yerine 'Tarihsel Sorgulama' ve 'Kanıt Kullanma' alan becerilerini alıyoruz. Hani meşhur sözdür: 'Geçmişi bilmeyen geleceğe yön veremez!' İsterseniz hemen 40 dakikalık 5E modeline uygun günlük planınızı veya yıllık planınızı A4 formatında hazırlayayım."
        }
        q.contains("sınav") || q.contains("yazılı") || q.contains("soru") -> {
            "MEB Ölçme ve Değerlendirme Yönetmeliği gereğince artık çoktan seçmeli ortak sınav devri kapandı; tamamen açık uçlu, harita/metin analizli beceri soruları esas alınıyor. Senaryo 1 ve Senaryo 2'ye uygun dereceli puanlama anahtarlı (rubrik) sınav kağıdınızı tek tıkla hazır edebilirim."
        }
        q.contains("geçme") || q.contains("kaldı") || q.contains("zayıf") || q.contains("yönetmelik") -> {
            "Ortaöğretim Kurumları Yönetmeliği Madde 56'ya göre; öğrencinin yıl sonu ağırlıklı not ortalaması en az 50 olursa doğrudan geçer. En fazla 3 dersten zayıfı olan sorumlu geçer. Ancak alt sınıflar dahil toplam 6 zayıfı aşan öğrenci sınıf tekrarına kalır."
        }
        else -> {
            "Tarih sadece geçmişin hikâyesi değil, bugünün pusulasıdır! Sorduğunuz konuyla ilgili Maarif Modeli çerçevesinde etkinlik, kaynak analizi, beyitler ve resmî planlama desteği sunabilirim. Ne üzerinde yoğunlaşalım?"
        }
    }
}
