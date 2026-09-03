package com.cepogretmeni.tarih.presentation.selfevaluation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cepogretmeni.tarih.domain.model.CompetencyLevel
import com.cepogretmeni.tarih.domain.model.CoreValue
import com.cepogretmeni.tarih.domain.model.HistorySkill

/**
 * Türkiye Yüzyılı Maarif Modeli - Öğrenci Öz Değerlendirme Formu UI Bileşeni
 * Material 3, Vibrant 3D Gölgelendirme, Kök Değer Rozetleri ve Süreç Odaklı Yetkinlik Takibi
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SelfEvaluationFormScreen(
    topicTitle: String = "İlk ve Orta Çağlarda Türk Dünyasında Devlet ve Toplum",
    gradeLevel: Int = 9,
    studentName: String = "Ahmet Yılmaz",
    studentNumber: String = "1042",
    onSaveForm: (
        inquiryLevel: Int,
        evidenceLevel: Int,
        chronologyLevel: Int,
        empathyLevel: Int,
        spatialLevel: Int,
        selectedValues: List<CoreValue>,
        reflectionNotes: String
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    onExportPdf: () -> Unit = {}
) {
    // State'ler
    var inquiryScore by remember { mutableFloatStateOf(2f) }
    var evidenceScore by remember { mutableFloatStateOf(2f) }
    var chronologyScore by remember { mutableFloatStateOf(3f) }
    var empathyScore by remember { mutableFloatStateOf(2f) }
    var spatialScore by remember { mutableFloatStateOf(1f) }

    val selectedCoreValues = remember { mutableStateListOf(CoreValue.ADALET, CoreValue.VATANSEVERLIK) }
    var reflectionNote by remember { mutableStateOf("") }
    var showSuccessBanner by remember { mutableStateOf(false) }

    // Genel Yetkinlik Yüzdesi (5 beceri, max 3 puan her biri = 15 puan)
    val totalScore = inquiryScore + evidenceScore + chronologyScore + empathyScore + spatialScore
    val percentage = (totalScore / 15f) * 100f

    val gradientBg = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F172A), // Slate 900
            Color(0xFF1E293B)  // Slate 800
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Maarif Modeli Öz Değerlendirme",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "$studentName • No: $studentNumber • $gradeLevel. Sınıf",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onExportPdf) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "PDF Çıktısı Al",
                            tint = Color(0xFFD97706) // Kehribar altın
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(gradientBg)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // 1. Canlı 3D Gelişim Özeti Kartı
            item {
                VibrantProgressCard(
                    topicTitle = topicTitle,
                    percentage = percentage,
                    totalScore = totalScore.toInt()
                )
            }

            // 2. Erdem-Değer-Eylem Kök Değer Seçici
            item {
                CoreValuesSelectorCard(
                    selectedValues = selectedCoreValues,
                    onToggleValue = { value ->
                        if (selectedCoreValues.contains(value)) {
                            selectedCoreValues.remove(value)
                        } else {
                            selectedCoreValues.add(value)
                        }
                    }
                )
            }

            // 3. Alan Becerileri Seviye Değerlendirme Listesi
            item {
                Text(
                    text = "TARİH ALAN BECERİLERİ GELİŞİM DÜZEYİ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8), // Muted slate
                    letterSpacing = 1.sp
                )
            }

            item {
                SkillSliderItem(
                    skill = HistorySkill.TARIHSEL_SORGULAMA,
                    score = inquiryScore,
                    onScoreChange = { inquiryScore = it }
                )
            }

            item {
                SkillSliderItem(
                    skill = HistorySkill.KANIT_KULLANMA,
                    score = evidenceScore,
                    onScoreChange = { evidenceScore = it }
                )
            }

            item {
                SkillSliderItem(
                    skill = HistorySkill.KRONOLOJIK_DUSUNME,
                    score = chronologyScore,
                    onScoreChange = { chronologyScore = it }
                )
            }

            item {
                SkillSliderItem(
                    skill = HistorySkill.TARIHSEL_EMPATI,
                    score = empathyScore,
                    onScoreChange = { empathyScore = it }
                )
            }

            item {
                SkillSliderItem(
                    skill = HistorySkill.MEKAN_ALGILAMA,
                    score = spatialScore,
                    onScoreChange = { spatialScore = it }
                )
            }

            // 4. Yansıtıcı Düşünme & Metin Alanı
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "✍️ Öğrenci Yansıtıcı Düşünme & Süreç Notu",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Bu konuda en çok hangi tarihi olay/kavram üzerinde düşündün? Kendinde hangi becerinin geliştiğini hissediyorsun?",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = reflectionNote,
                            onValueChange = { reflectionNote = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                            placeholder = {
                                Text(
                                    "Örn: Orhun Kitabelerindeki sosyal devlet anlayışı ile bugünkü anayasal haklarımızı karşılaştırırken empati kurabildim...",
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color(0xFFE2E8F0),
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            // 5. Kaydet & Güvenli Depolama Butonları
            item {
                Button(
                    onClick = {
                        onSaveForm(
                            inquiryScore.toInt(),
                            evidenceScore.toInt(),
                            chronologyScore.toInt(),
                            empathyScore.toInt(),
                            spatialScore.toInt(),
                            selectedCoreValues.toList(),
                            reflectionNote
                        )
                        showSuccessBanner = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(12.dp, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2563EB)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Uçtan Uca Şifreli Olarak Kaydet",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            item {
                AnimatedVisibility(visible = showSuccessBanner) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF065F46)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34D399))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Öz değerlendirme formu yerel SQLCipher şifreli hafızaya kaydedildi.",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Canlı 3D Gradient Gösterge Kartı
 */
@Composable
private fun VibrantProgressCard(
    topicTitle: String,
    percentage: Float,
    totalScore: Int
) {
    val cardGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1E3A8A), // Koyu Mavi
            Color(0xFF4338CA), // İndigo
            Color(0xFF6D28D9)  // Mor
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardGradient)
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TÜRKİYE YÜZYILI MAARİF MODELİ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF93C5FD),
                        letterSpacing = 1.2.sp
                    )
                    Badge(containerColor = Color(0xFF10B981)) {
                        Text(
                            text = "Süreç Odaklı",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = topicTitle,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Genel Süreç Yetkinliği",
                            fontSize = 12.sp,
                            color = Color(0xFFE2E8F0)
                        )
                        Text(
                            text = "%${percentage.toInt()}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFBBF24)
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "$totalScore/15",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { percentage / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = Color(0xFF38BDF8),
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }
    }
}

/**
 * Erdem-Değer-Eylem Kök Değer Seçim Kartı
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CoreValuesSelectorCard(
    selectedValues: List<CoreValue>,
    onToggleValue: (CoreValue) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Dönemle İlişkilendirilen Kök Değerler",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CoreValue.values().forEach { value ->
                    val isSelected = selectedValues.contains(value)
                    val badgeBg by animateColorAsState(
                        targetValue = if (isSelected) Color(value.colorHex) else Color(0xFF334155),
                        animationSpec = spring(stiffness = Spring.StiffnessLow),
                        label = "badgeColor"
                    )

                    FilterChip(
                        selected = isSelected,
                        onClick = { onToggleValue(value) },
                        label = {
                            Text(
                                text = value.titleTr,
                                color = if (isSelected) Color.White else Color(0xFFCBD5E1),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = badgeBg,
                            containerColor = Color(0xFF334155)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Tek Bir Tarih Alan Becerisi İçin Süreç Değerlendirme Bileşeni
 */
@Composable
private fun SkillSliderItem(
    skill: HistorySkill,
    score: Float,
    onScoreChange: (Float) -> Unit
) {
    val level = when (score.toInt()) {
        1 -> CompetencyLevel.GELISTIRILMELI
        2 -> CompetencyLevel.YETKIN
        else -> CompetencyLevel.ILERI_DUZEY
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = skill.titleTr,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(level.badgeColorHex).copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, Color(level.badgeColorHex))
                ) {
                    Text(
                        text = level.titleTr,
                        color = Color(level.badgeColorHex),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = skill.descriptionTr,
                fontSize = 11.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Slider(
                value = score,
                onValueChange = onScoreChange,
                valueRange = 1f..3f,
                steps = 1, // 1, 2, 3
                colors = SliderDefaults.colors(
                    thumbColor = Color(level.badgeColorHex),
                    activeTrackColor = Color(level.badgeColorHex),
                    inactiveTrackColor = Color(0xFF334155)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("1. Geliştirilmeli", fontSize = 10.sp, color = Color(0xFF64748B))
                Text("2. Yetkin", fontSize = 10.sp, color = Color(0xFF64748B))
                Text("3. İleri Düzey", fontSize = 10.sp, color = Color(0xFF64748B))
            }
        }
    }
}
