# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Preserve line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve all public classes and methods
-keep public class * {
    public protected *;
}

# Coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Gson
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
