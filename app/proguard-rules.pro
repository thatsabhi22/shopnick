# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/aviator/android-sdks/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote com.google.vending.licensing.ILicensingService
-dontnote com.fasterxml.jackson.**
-dontnote com.nineoldandroids.util.ReflectiveProperty
-dontnote android.support.v4.**
-dontnote com.squareup.picasso.**