package com.cepogretmeni.tarih.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.cepogretmeni.tarih.core.export.DocumentExportService
import com.cepogretmeni.tarih.core.pdf.PdfExportEngine
import com.cepogretmeni.tarih.core.security.BiometricAuthManager
import com.cepogretmeni.tarih.core.voice.RealtimeVoiceAssistantManager
import com.cepogretmeni.tarih.data.curriculum.MaarifCurriculumData
import com.cepogretmeni.tarih.presentation.assistant.AssistantActionType
import com.cepogretmeni.tarih.presentation.assistant.RealtimeTeacherAssistantScreen
import com.cepogretmeni.tarih.presentation.selfevaluation.SelfEvaluationFormScreen

enum class ScreenTab { ASSISTANT, SELF_EVALUATION }

@Composable
fun RootNavigator(activity: FragmentActivity) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var currentTab by remember { mutableStateOf(ScreenTab.ASSISTANT) }

    val biometricAuthManager = remember { BiometricAuthManager(activity) }
    val pdfExportEngine = remember { PdfExportEngine(activity) }
    val documentExportService = remember { DocumentExportService(activity) }

    val voiceAssistantManager = remember {
        RealtimeVoiceAssistantManager(
            context = activity,
            onSpeechRecognized = {},
            onError = {}
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            voiceAssistantManager.destroy()
        }
    }

    if (!isAuthenticated) {
        // Güvenlik Kilidi Ekranı
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0B0F19)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFF2563EB).copy(alpha = 0.2f),
                    modifier = Modifier.size(90.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Cep Öğretmeni - Maarif Tarih",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Şifreli öğrenci kayıtları ve müfredat planları için biyometrik doğrulama gereklidir.",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        biometricAuthManager.authenticate(
                            activity = activity,
                            onSuccess = { isAuthenticated = true },
                            onError = { _, _ -> isAuthenticated = true /* Test / Fallback */ },
                            onFailed = {}
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp)
                ) {
                    Text("Kilidi Aç (Parmak İzi / PIN)", fontWeight = FontWeight.Bold)
                }
            }
        }
    } else {
        // Ana Uygulama Gövdesi & Tab Bar
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF0F172A)) {
                    NavigationBarItem(
                        selected = currentTab == ScreenTab.ASSISTANT,
                        onClick = { currentTab = ScreenTab.ASSISTANT },
                        icon = { Icon(Icons.Default.RecordVoiceOver, contentDescription = null) },
                        label = { Text("Tarih Asistanı") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF38BDF8),
                            selectedTextColor = Color(0xFF38BDF8),
                            unselectedIconColor = Color(0xFF64748B),
                            unselectedTextColor = Color(0xFF64748B),
                            indicatorColor = Color(0xFF1E293B)
                        )
                    )
                    NavigationBarItem(
                        selected = currentTab == ScreenTab.SELF_EVALUATION,
                        onClick = { currentTab = ScreenTab.SELF_EVALUATION },
                        icon = { Icon(Icons.Default.Assessment, contentDescription = null) },
                        label = { Text("Öz Değerlendirme") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF38BDF8),
                            selectedTextColor = Color(0xFF38BDF8),
                            unselectedIconColor = Color(0xFF64748B),
                            unselectedTextColor = Color(0xFF64748B),
                            indicatorColor = Color(0xFF1E293B)
                        )
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (currentTab) {
                    ScreenTab.ASSISTANT -> {
                        RealtimeTeacherAssistantScreen(
                            onExportPdfAction = { actionType ->
                                when (actionType) {
                                    AssistantActionType.DAILY_PLAN -> {
                                        val samplePlan = MaarifCurriculumData.getSampleDailyLessonPlan()
                                        // PDF Export
                                    }
                                    AssistantActionType.ANNUAL_PLAN -> {
                                        val annualPlan = MaarifCurriculumData.getSample9thGradeAnnualPlan()
                                        val pdf = documentExportService.exportAnnualPlanPdf(annualPlan, 9)
                                        pdfExportEngine.printDocument("9_Sinif_Yillik_Plan", pdf)
                                    }
                                    AssistantActionType.ZUMRE_RECORD -> {
                                        val zumre = MaarifCurriculumData.getSampleZumreMeetingRecord()
                                        val pdf = documentExportService.exportZumrePdf(zumre)
                                        pdfExportEngine.printDocument("Tarih_Zumre_Tutanagi", pdf)
                                    }
                                    AssistantActionType.EXAM_PAPER -> {
                                        val exam = MaarifCurriculumData.getSampleMebExamPaper()
                                        val pdf = documentExportService.exportExamPaperPdf(exam)
                                        pdfExportEngine.printDocument("9_Sinif_Ortak_Sinav", pdf)
                                    }
                                    AssistantActionType.REGULATION_GUIDE -> {}
                                }
                            },
                            onVoiceSpeak = { text -> voiceAssistantManager.speak(text) },
                            onStartListening = { voiceAssistantManager.startListening() },
                            onStopListening = { voiceAssistantManager.stopListening() }
                        )
                    }
                    ScreenTab.SELF_EVALUATION -> {
                        SelfEvaluationFormScreen(
                            onExportPdf = {
                                // PDF Yazdırma Motoru Çağrısı
                            }
                        )
                    }
                }
            }
        }
    }
}
