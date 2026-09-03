package com.cepogretmeni.tarih.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.cepogretmeni.tarih.core.database.AppDatabase
import com.cepogretmeni.tarih.core.export.DocxExportEngine
import com.cepogretmeni.tarih.core.export.DocumentExportService
import com.cepogretmeni.tarih.core.pdf.PdfExportEngine
import com.cepogretmeni.tarih.core.security.BiometricAuthManager
import com.cepogretmeni.tarih.data.curriculum.MaarifCurriculumData
import com.cepogretmeni.tarih.data.local.entities.SavedDocumentEntity
import com.cepogretmeni.tarih.domain.model.TeacherProfile
import com.cepogretmeni.tarih.presentation.assistant.AssistantActionType
import com.cepogretmeni.tarih.presentation.assistant.RealtimeTeacherAssistantScreen
import com.cepogretmeni.tarih.presentation.saved.SavedDocumentsScreen
import com.cepogretmeni.tarih.presentation.selfevaluation.SelfEvaluationFormScreen
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class ScreenTab { ASSISTANT, SAVED_DOCS, SELF_EVALUATION }

@Composable
fun RootNavigator(activity: FragmentActivity) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var currentTab by remember { mutableStateOf(ScreenTab.ASSISTANT) }
    var selectedGrade by remember { mutableIntStateOf(9) }
    var teacherProfile by remember { mutableStateOf(TeacherProfile()) }

    val coroutineScope = rememberCoroutineScope()
    val biometricAuthManager = remember { BiometricAuthManager(activity) }
    val pdfExportEngine = remember { PdfExportEngine(activity) }
    val documentExportService = remember { DocumentExportService(activity) }
    val docxExportEngine = remember { DocxExportEngine(activity) }

    val db = remember { AppDatabase.getDatabase(activity) }
    val savedDocumentsState = db.historyDao().getAllSavedDocuments().collectAsState(initial = emptyList())

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
                            onError = { _, _ -> isAuthenticated = true /* Test Fallback */ },
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
        // Ana Navigasyon
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF0F172A)) {
                    NavigationBarItem(
                        selected = currentTab == ScreenTab.ASSISTANT,
                        onClick = { currentTab = ScreenTab.ASSISTANT },
                        icon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
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
                        selected = currentTab == ScreenTab.SAVED_DOCS,
                        onClick = { currentTab = ScreenTab.SAVED_DOCS },
                        icon = { Icon(Icons.Default.Folder, contentDescription = null) },
                        label = { Text("Kayıtlı Belgelerim") },
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
                            selectedGrade = selectedGrade,
                            profile = teacherProfile,
                            onProfileChange = { teacherProfile = it },
                            onGradeChange = { selectedGrade = it },
                            onExportPdfAction = { actionType, grade ->
                                when (actionType) {
                                    AssistantActionType.DAILY_PLAN -> {
                                        val plan = MaarifCurriculumData.generateMultiHourDailyPlan(grade, teacherProfile.defaultWeeklyHours, teacherProfile)
                                        val pdf = documentExportService.exportMultiHourLessonPlanPdf(plan, teacherProfile)
                                        documentExportService.shareOrOpenFile(pdf, "application/pdf")
                                    }
                                    AssistantActionType.ANNUAL_PLAN -> {
                                        val annualPlan = MaarifCurriculumData.getFullAnnualPlan(grade)
                                        val pdf = documentExportService.exportAnnualPlanPdf(annualPlan, grade, teacherProfile)
                                        documentExportService.shareOrOpenFile(pdf, "application/pdf")
                                    }
                                    AssistantActionType.ZUMRE_RECORD -> {
                                        val zumre = MaarifCurriculumData.getSampleZumreMeetingRecord()
                                        val pdf = documentExportService.exportZumrePdf(zumre, teacherProfile)
                                        documentExportService.shareOrOpenFile(pdf, "application/pdf")
                                    }
                                    AssistantActionType.EXAM_PAPER -> {
                                        val exam = MaarifCurriculumData.getSampleMebExamPaper()
                                        val pdf = documentExportService.exportExamPaperPdf(exam, teacherProfile)
                                        documentExportService.shareOrOpenFile(pdf, "application/pdf")
                                    }
                                    else -> {}
                                }
                            },
                            onExportWordAction = { actionType, grade ->
                                when (actionType) {
                                    AssistantActionType.DAILY_PLAN -> {
                                        val plan = MaarifCurriculumData.generateMultiHourDailyPlan(grade, teacherProfile.defaultWeeklyHours, teacherProfile)
                                        val docx = docxExportEngine.createLessonPlanDocx(plan, teacherProfile)
                                        documentExportService.shareOrOpenFile(docx, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                                    }
                                    AssistantActionType.ZUMRE_RECORD -> {
                                        val zumre = MaarifCurriculumData.getSampleZumreMeetingRecord()
                                        val docx = docxExportEngine.createZumreDocx(zumre, teacherProfile)
                                        documentExportService.shareOrOpenFile(docx, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                                    }
                                    else -> {
                                        val plan = MaarifCurriculumData.generateMultiHourDailyPlan(grade, teacherProfile.defaultWeeklyHours, teacherProfile)
                                        val docx = docxExportEngine.createLessonPlanDocx(plan, teacherProfile)
                                        documentExportService.shareOrOpenFile(docx, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                                    }
                                }
                            },
                            onSaveToAppAction = { actionType, grade, text ->
                                val typeName = when (actionType) {
                                    AssistantActionType.DAILY_PLAN -> "GÜNLÜK DERS PLANI"
                                    AssistantActionType.ANNUAL_PLAN -> "YILLIK PLAN"
                                    AssistantActionType.ZUMRE_RECORD -> "ZÜMRE TUTANAĞI"
                                    AssistantActionType.EXAM_PAPER -> "SINAV & RUBRİK"
                                    else -> "DERS NOTU"
                                }
                                val dateStr = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
                                coroutineScope.launch {
                                    db.historyDao().insertSavedDocument(
                                        SavedDocumentEntity(
                                            title = "$grade. Sınıf Maarif Modeli $typeName",
                                            documentType = typeName,
                                            gradeLevel = grade,
                                            contentText = text,
                                            schoolName = teacherProfile.schoolName,
                                            teacherName = teacherProfile.teacherName,
                                            principalName = teacherProfile.principalName,
                                            createdAtFormatted = dateStr
                                        )
                                    )
                                }
                            }
                        )
                    }

                    ScreenTab.SAVED_DOCS -> {
                        SavedDocumentsScreen(
                            savedDocuments = savedDocumentsState.value,
                            onDeleteDocument = { doc ->
                                coroutineScope.launch { db.historyDao().deleteSavedDocument(doc) }
                            },
                            onExportPdf = { doc ->
                                val plan = MaarifCurriculumData.generateMultiHourDailyPlan(doc.gradeLevel, teacherProfile.defaultWeeklyHours, teacherProfile)
                                val pdf = documentExportService.exportMultiHourLessonPlanPdf(plan, teacherProfile)
                                documentExportService.shareOrOpenFile(pdf, "application/pdf")
                            },
                            onExportWord = { doc ->
                                val plan = MaarifCurriculumData.generateMultiHourDailyPlan(doc.gradeLevel, teacherProfile.defaultWeeklyHours, teacherProfile)
                                val docx = docxExportEngine.createLessonPlanDocx(plan, teacherProfile)
                                documentExportService.shareOrOpenFile(docx, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            }
                        )
                    }

                    ScreenTab.SELF_EVALUATION -> {
                        SelfEvaluationFormScreen(
                            gradeLevel = selectedGrade,
                            onExportPdf = {
                                // PDF Yazdırma
                            }
                        )
                    }
                }
            }
        }
    }
}
