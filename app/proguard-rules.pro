# ProGuard / R8 Kuralları - Anti-Reverse Engineering & Kod Karıştırma

# Room Database kuralları
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# SQLCipher Kuralları
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }
-dontwarn net.sqlcipher.**

# Biometric & Security Crypto
-keep class androidx.biometric.** { *; }
-keep class androidx.security.crypto.** { *; }

# Domain Modelleri ve Entity'ler
-keep class com.cepogretmeni.tarih.domain.model.** { *; }
-keep class com.cepogretmeni.tarih.data.local.entities.** { *; }

# Logları ve Console çıktılarını üretim sürümünde tamamen sil
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}
