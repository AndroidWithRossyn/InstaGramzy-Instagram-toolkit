# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class yourpackage.** { *; }
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-keep class com.esri.** { *; }
-keep interface com.esri.** { *; }
-keep class org.codehaus.jackson.** { *; }
-keep class com.jackandphantom.** { *; }
-dontwarn org.codehaus.jackson.map.ext.**
-dontwarn jcifs.http.**
-keepclasseswithmembernames,includedescriptorclasses class * {
native <methods>;
}
# RenderScript to be safe on "native callback" side
-keep class androidx.renderscript.** { *; }