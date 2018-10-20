# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class nickname to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file nickname.
#-renamesourcefileattribute SourceFile

#-------------------------------------------定制化区域----------------------------------------------

#---------------------------------1.实体类---------------------------------
# 理解完战略级思想后，我们开始第一部分补充-实体类。
# 实体类由于涉及到与服务端的交互，各种gson的交互如此等等，是要保留的。
# 将你项目中实体类都拎出来，用以下语法进行保留。
# -keep class 你的实体类所在的包.** { *; }
# 如我的项目下类User的完整路径为：com.demo.login.bean.User, 那我的混淆如下
# -keep class com.demo.login.bean.** { *; }
# 当然你的实体类肯定不止这一个，请用上边的方式一一添加，如果你的实体类都在一个包下，那你就幸福了。


#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
# 第2部分是第三方包。打开你的build.gradle文件，查看你用了哪些第三方的包。
# dependencies {
#    compile 'com.github.bumptech.glide:glide:3.7.0'
#    compile 'org.greenrobot:eventbus:3.0.0'
# }
# 我这里用了glide，eventbus。我去他们的官网把已经写好的混淆copy下来。

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#log4j
# -libraryjars log4j-1.2.17.jar
-dontwarn org.apache.log4j.**
-keep class  org.apache.log4j.** { *;}

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------
# 第三部分与js互调的类，工程中没有直接跳过。一般你可以这样写
# -keep class 你的类所在的包.** { *; }
# 如果是内部类的话，你可以这样
# -keepclasseswithmembers class 你的类所在的包.父类$子类 { <methods>; }
# 例如
# -keepclasseswithmembers class com.demo.login.bean.ui.MainActivity$JSInterface {
#       <methods>;
# }

#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------
#第四部分与反射有关的类，工程中没有直接跳过。类的话直接这样
#-keep class 你的类所在的包.** { *; }
#熟练后不用五分钟就能搞定。大家可以把自己写完混淆所用的时间写在评论里（0，0）

#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------

#---------------------------------------------------------------------------------------------------