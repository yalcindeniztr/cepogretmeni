# GİZLİLİK POLİTİKASI (PRIVACY POLICY)
**Cep Öğretmeni - Maarif Modeli Tarih Asistanı**
*Son Güncelleme: 3 Eylül 2026*

Bu Gizlilik Politikası; **Cep Öğretmeni** mobil uygulamasının öğretmen ve öğrenci verilerini nasıl işlediğini, sakladığını ve koruduğunu açıklamaktadır. Uygulamamız; 6698 sayılı Kişisel Verilerin Korunması Kanunu (KVKK), Avrupa Birliği Genel Veri Koruma Tüzüğü (GDPR) ve Google Play Store Geliştirici Politikaları'na tam uyumlu olarak geliştirilmiştir.

---

## 1. Temel İlke: %100 Cihaz İçi (On-Device) ve Çevrimdışı Çalışma
Cep Öğretmeni uygulaması **sıfır sunucu veri aktarımı** prensibini benimser:
- Uygulamada oluşturulan ders planları, zümre tutanakları, sınav soruları, rubrikler ve öğrenci öz değerlendirme puanları **yalnızca kullanıcının kendi cihazında** yerel olarak tutulur.
- Hiçbir öğrenci veya öğretmen verisi üçüncü taraf bulut sunucularına, reklam ağlarına veya harici veri tabanlarına **gönderilmez ve satılmaz**.

---

## 2. Toplanan ve İşlenen Veri Türleri

### A. Biyometrik Kimlik Doğrulama Verileri (Biometric Data)
- Uygulama, hassas kayıtların korunması amacıyla Android `BiometricPrompt API` kullanır.
- Parmak izi veya yüz tanıma verileri doğrudan cihazın güvenli donanım katmanında (TEE - Trusted Execution Environment / Android Keystore) işlenir. Uygulama geliştiricisi veya yazılımı biyometrik ham verilere asla erişemez.

### B. Sesli Komut ve Konuşma Verileri (Microphone & Voice Data)
- Sesli asistan özelliği, kullanıcının sorularını dinlemek ve sesli cevap vermek amacıyla mikrofon izni (`RECORD_AUDIO`) kullanır.
- Ses verileri yalnızca cihaz üzerinde yerel konuşma tanıma (Speech-to-Text) işlemi için anlık olarak işlenir; ses kayıtları kaydedilmez veya harici sunuculara aktarılmaz.

### C. Öğrenci ve Öğretmen Çalışma Kayıtları
- Öğrenci numarası, adı-soyadı, alan becerisi puanları ve öğretmen ders notları yerel **SQLCipher (AES-256)** şifreli veri tabanında saklanır.

---

## 3. Cihaz İzinleri ve Kullanım Amaçları
| İzin Adı | Kullanım Amacı |
|---|---|
| `android.permission.USE_BIOMETRIC` | Uygulama kilidinin parmak izi/yüz ile güvenle açılması için. |
| `android.permission.RECORD_AUDIO` | Tarih öğretmenine sesli soru sormak için mikrofon erişimi. |

---

## 4. Çocukların Gizliliği ve MEB Öğrenci Güvenliği
Uygulamamız lise öğrencileri ve öğretmenler için tasarlanmış olup; çocukların kişisel gizliliğini ihlal edecek hiçbir izleme (tracking), çerez (cookie) veya hedefli reklam bileşeni içermez.

---

## 5. İletişim
Gizlilik politikamızla ilgili soru, görüş ve talepleriniz için:
- **E-posta:** destek@cepogretmeni.com
- **Web:** https://cepogretmeni.com/privacy-policy
