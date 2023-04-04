package jmp0.app.mock.system.user

import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.telephony.TelephonyManager
import android.view.Display
import android.view.DisplayAdjustments
import jmp0.app.AndroidEnvironment
import jmp0.app.DbgContext
import jmp0.app.classloader.XAndroidClassLoader
import jmp0.app.mock.annotations.ClassReplaceTo
import jmp0.app.mock.system.service.MockSubscriptionManager
import jmp0.app.mock.system.service.MockTelephonyManager
import jmp0.app.mock.system.sharedpreferences.MockSharedPreferences
import jmp0.app.mock.system.sharedpreferences.MockSharedPreferencesManager
import org.apache.log4j.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/14
 */
@ClassReplaceTo("")
class UserContext:Context() {
    private val logger = Logger.getLogger(UserContext::class.java)

    private fun getEnv(): AndroidEnvironment {
        return DbgContext.getAndroidEnvironmentWithClassLoader(this.javaClass.classLoader as XAndroidClassLoader)
    }

    override fun getAssets(): AssetManager {
        val env = getEnv()
        return env.findClass("android.content.res.AssetManager").getDeclaredConstructor().newInstance() as android.content.res.AssetManager
    }

    override fun getResources(): Resources {
        val env = getEnv()
        return env.findClass("android.content.res.Resources")
            .getDeclaredConstructor(env.findClass("android.content.res.AssetManager"),env.findClass("android.util.DisplayMetrics"),env.findClass("android.content.res.Configuration"))
            .newInstance(assets,env.findClass("android.util.DisplayMetrics").getDeclaredConstructor().newInstance(),env.findClass("android.content.res.Configuration").getDeclaredConstructor().newInstance())
        as Resources
    }

    override fun getPackageManager(): PackageManager {
        return jmp0.app.mock.system.manager.MockPackageManager();
    }

    override fun getContentResolver(): ContentResolver {
        val env = getEnv();
        return env.findClass("jmp0.app.mock.system.service.MockContentResolver").getDeclaredConstructor(env.findClass("android.content.Context"))
            .newInstance(this) as ContentResolver
    }

    override fun getMainLooper(): Looper {
        TODO("Not yet implemented")
    }

    override fun getApplicationContext(): Context {
        return this
    }

    override fun setTheme(p0: Int) {
        logger.warn("setTheme not support")
    }

    override fun getTheme(): Resources.Theme {
        TODO("Not yet implemented")
    }

    override fun getClassLoader(): ClassLoader {
        return getEnv().getClassLoader()
    }

    override fun getPackageName(): String {
        return getEnv().apkFile.packageName
    }

    override fun getBasePackageName(): String {
        return getEnv().apkFile.packageName
    }

    override fun getOpPackageName(): String {
        return getEnv().apkFile.packageName
    }

    override fun getApplicationInfo(): ApplicationInfo {
        TODO("Not yet implemented")
    }

    override fun getPackageResourcePath(): String {
        TODO("Not yet implemented")
    }

    override fun getPackageCodePath(): String {
        TODO("Not yet implemented")
    }

    override fun getSharedPrefsFile(p0: String?): File {
        TODO("Not yet implemented")
    }

    override fun getSharedPreferences(p0: String, p1: Int): SharedPreferences {
        return MockSharedPreferencesManager.getSharedPreferences(p0)
    }

    override fun openFileInput(p0: String?): FileInputStream {
        TODO("Not yet implemented")
    }

    override fun openFileOutput(p0: String?, p1: Int): FileOutputStream {
        TODO("Not yet implemented")
    }

    override fun deleteFile(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFileStreamPath(p0: String?): File {
        TODO("Not yet implemented")
    }

    override fun getFilesDir(): File {
        TODO("Not yet implemented")
    }

    override fun getNoBackupFilesDir(): File {
        TODO("Not yet implemented")
    }

    override fun getExternalFilesDir(p0: String?): File {
        TODO("Not yet implemented")
    }

    override fun getExternalFilesDirs(p0: String?): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getObbDir(): File {
        TODO("Not yet implemented")
    }

    override fun getObbDirs(): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getCodeCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getExternalCacheDir(): File {
        TODO("Not yet implemented")
    }

    override fun getExternalCacheDirs(): Array<File> {
        TODO("Not yet implemented")
    }

    override fun getExternalMediaDirs(): Array<File> {
        TODO("Not yet implemented")
    }

    override fun fileList(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getDir(p0: String?, p1: Int): File {
        TODO("Not yet implemented")
    }

    override fun openOrCreateDatabase(p0: String?, p1: Int, p2: SQLiteDatabase.CursorFactory?): SQLiteDatabase {
        TODO("Not yet implemented")
    }

    override fun openOrCreateDatabase(
        p0: String?,
        p1: Int,
        p2: SQLiteDatabase.CursorFactory?,
        p3: DatabaseErrorHandler?
    ): SQLiteDatabase {
        TODO("Not yet implemented")
    }

    override fun deleteDatabase(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDatabasePath(p0: String?): File {
        TODO("Not yet implemented")
    }

    override fun databaseList(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getWallpaper(): Drawable {
        TODO("Not yet implemented")
    }

    override fun peekWallpaper(): Drawable {
        TODO("Not yet implemented")
    }

    override fun getWallpaperDesiredMinimumWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun getWallpaperDesiredMinimumHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun setWallpaper(p0: Bitmap?) {
        TODO("Not yet implemented")
    }

    override fun setWallpaper(p0: InputStream?) {
        TODO("Not yet implemented")
    }

    override fun clearWallpaper() {
        TODO("Not yet implemented")
    }

    override fun startActivity(p0: Intent?) {
        TODO("Not yet implemented")
    }

    override fun startActivity(p0: Intent?, p1: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun startActivities(p0: Array<out Intent>?) {
        TODO("Not yet implemented")
    }

    override fun startActivities(p0: Array<out Intent>?, p1: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun startIntentSender(p0: IntentSender?, p1: Intent?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun startIntentSender(p0: IntentSender?, p1: Intent?, p2: Int, p3: Int, p4: Int, p5: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun sendBroadcast(p0: Intent?) {
        logger.warn("sendBroadcast not support parm:$p0")
    }

    override fun sendBroadcast(p0: Intent?, p1: String?) {
        logger.warn("sendBroadcast not support parm:$p0 $p1")
    }

    override fun sendBroadcast(p0: Intent?, p1: String?, p2: Bundle?) {
        logger.warn("sendBroadcast not support parm:$p0 $p1 $p2")
    }

    override fun sendBroadcast(p0: Intent?, p1: String?, p2: Int) {
        logger.warn("sendBroadcast not support parm:$p0 $p1 $p2")
    }

    override fun sendBroadcastMultiplePermissions(p0: Intent?, p1: Array<out String>?) {
        logger.warn("sendBroadcastMultiplePermissions not support parm:$p0 ${p1.contentToString()}")
    }

    override fun sendOrderedBroadcast(p0: Intent?, p1: String?) {
        logger.warn("sendOrderedBroadcast not support parm:$p0 $p1")
    }

    override fun sendOrderedBroadcast(
        p0: Intent?,
        p1: String?,
        p2: BroadcastReceiver?,
        p3: Handler?,
        p4: Int,
        p5: String?,
        p6: Bundle?
    ) {
        logger.warn("sendOrderedBroadcast not support parm:$p0 $p1")
    }

    override fun sendOrderedBroadcast(
        p0: Intent?,
        p1: String?,
        p2: Bundle?,
        p3: BroadcastReceiver?,
        p4: Handler?,
        p5: Int,
        p6: String?,
        p7: Bundle?
    ) {
        logger.warn("sendOrderedBroadcast not support parm:$p0 $p1")
    }

    override fun sendOrderedBroadcast(
        p0: Intent?,
        p1: String?,
        p2: Int,
        p3: BroadcastReceiver?,
        p4: Handler?,
        p5: Int,
        p6: String?,
        p7: Bundle?
    ) {
        logger.warn("sendOrderedBroadcast not support parm:$p0 $p1")
    }

    override fun sendBroadcastAsUser(p0: Intent?, p1: UserHandle?) {
        logger.warn("sendBroadcastAsUser not support parm:$p0 $p1")
    }

    override fun sendBroadcastAsUser(p0: Intent?, p1: UserHandle?, p2: String?) {
        logger.warn("sendBroadcastAsUser not support parm:$p0 $p1")
    }

    override fun sendBroadcastAsUser(p0: Intent?, p1: UserHandle?, p2: String?, p3: Int) {
        logger.warn("sendBroadcastAsUser not support parm:$p0 $p1")
    }

    override fun sendOrderedBroadcastAsUser(
        p0: Intent?,
        p1: UserHandle?,
        p2: String?,
        p3: BroadcastReceiver?,
        p4: Handler?,
        p5: Int,
        p6: String?,
        p7: Bundle?
    ) {
        logger.warn("sendOrderedBroadcastAsUser not support parm:$p0 $p1")
    }

    override fun sendOrderedBroadcastAsUser(
        p0: Intent?,
        p1: UserHandle?,
        p2: String?,
        p3: Int,
        p4: BroadcastReceiver?,
        p5: Handler?,
        p6: Int,
        p7: String?,
        p8: Bundle?
    ) {
        logger.warn("sendOrderedBroadcastAsUser not support parm:$p0 $p1")
    }

    override fun sendOrderedBroadcastAsUser(
        p0: Intent?,
        p1: UserHandle?,
        p2: String?,
        p3: Int,
        p4: Bundle?,
        p5: BroadcastReceiver?,
        p6: Handler?,
        p7: Int,
        p8: String?,
        p9: Bundle?
    ) {
        logger.warn("sendOrderedBroadcastAsUser not support parm:$p0 $p1")
    }

    override fun sendStickyBroadcast(p0: Intent?) {
        logger.warn("sendStickyBroadcast not support parm:$p0 ")
    }

    override fun sendStickyOrderedBroadcast(
        p0: Intent?,
        p1: BroadcastReceiver?,
        p2: Handler?,
        p3: Int,
        p4: String?,
        p5: Bundle?
    ) {
        logger.warn("sendStickyOrderedBroadcast not support parm:$p0 ")
    }

    override fun removeStickyBroadcast(p0: Intent?) {
        logger.warn("removeStickyBroadcast not support parm:$p0 ")
    }

    override fun sendStickyBroadcastAsUser(p0: Intent?, p1: UserHandle?) {
        logger.warn("sendStickyBroadcastAsUser not support parm:$p0 ")
    }

    override fun sendStickyOrderedBroadcastAsUser(
        p0: Intent?,
        p1: UserHandle?,
        p2: BroadcastReceiver?,
        p3: Handler?,
        p4: Int,
        p5: String?,
        p6: Bundle?
    ) {
        logger.warn("sendStickyOrderedBroadcastAsUser not support parm:$p0 ")
    }

    override fun removeStickyBroadcastAsUser(p0: Intent?, p1: UserHandle?) {
        logger.warn("removeStickyBroadcastAsUser not support parm:$p0 ")
    }

    override fun registerReceiver(p0: BroadcastReceiver?, p1: IntentFilter?): Intent {
        logger.warn("registerReceiver not support parm:$p0 ")
        return Intent()
    }

    override fun registerReceiver(p0: BroadcastReceiver?, p1: IntentFilter?, p2: String?, p3: Handler?): Intent {
        logger.warn("registerReceiver not support parm:$p0 ")
        return Intent()
    }

    override fun registerReceiverAsUser(
        p0: BroadcastReceiver?,
        p1: UserHandle?,
        p2: IntentFilter?,
        p3: String?,
        p4: Handler?
    ): Intent {
        logger.warn("registerReceiverAsUser not support parm:$p0 ")
        return Intent()
    }

    override fun unregisterReceiver(p0: BroadcastReceiver?) {
        logger.warn("unregisterReceiver not support parm:$p0 ")
    }

    override fun startService(p0: Intent?): ComponentName? {
        logger.warn("stopServiceAsUser $p0")
        return null
    }

    override fun stopService(p0: Intent?): Boolean {
        logger.warn("stopServiceAsUser $p0")
        return true
    }

    override fun startServiceAsUser(p0: Intent?, p1: UserHandle?): ComponentName? {
        logger.warn("startServiceAsUser return null")
        return null
    }

    override fun stopServiceAsUser(p0: Intent?, p1: UserHandle?): Boolean {
        logger.warn("stopServiceAsUser")
        return true
    }

    override fun bindService(p0: Intent?, p1: ServiceConnection?, p2: Int): Boolean {
        logger.warn("bindService")
        return true
    }

    override fun unbindService(p0: ServiceConnection?) {
        logger.warn("unbindService")
    }

    override fun startInstrumentation(p0: ComponentName?, p1: String?, p2: Bundle?): Boolean {
        logger.warn("startInstrumentation return false")
        return false
    }

    override fun getSystemService(p0: String?): Any? {
        if (p0 == "phone") return MockTelephonyManager(this)
        if (p0 == "telephony_subscription_service") return MockSubscriptionManager(this)
        logger.warn("getSystemService $p0 return null")
        return null
    }

    override fun getSystemServiceName(p0: Class<*>?): String {
        return p0!!.simpleName
    }

    override fun checkPermission(p0: String?, p1: Int, p2: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkPermission(p0: String?, p1: Int, p2: Int, p3: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingPermission(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingOrSelfPermission(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun checkSelfPermission(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun enforcePermission(p0: String?, p1: Int, p2: Int, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingPermission(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingOrSelfPermission(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun grantUriPermission(p0: String?, p1: Uri?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun revokeUriPermission(p0: Uri?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun checkUriPermission(p0: Uri?, p1: Int, p2: Int, p3: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkUriPermission(p0: Uri?, p1: Int, p2: Int, p3: Int, p4: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun checkUriPermission(p0: Uri?, p1: String?, p2: String?, p3: Int, p4: Int, p5: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingUriPermission(p0: Uri?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkCallingOrSelfUriPermission(p0: Uri?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun enforceUriPermission(p0: Uri?, p1: Int, p2: Int, p3: Int, p4: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceUriPermission(p0: Uri?, p1: String?, p2: String?, p3: Int, p4: Int, p5: Int, p6: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingUriPermission(p0: Uri?, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun enforceCallingOrSelfUriPermission(p0: Uri?, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun createPackageContext(p0: String?, p1: Int): Context {
        TODO("Not yet implemented")
    }

    override fun createPackageContextAsUser(p0: String?, p1: Int, p2: UserHandle?): Context {
        TODO("Not yet implemented")
    }

    override fun createApplicationContext(p0: ApplicationInfo?, p1: Int): Context {
        TODO("Not yet implemented")
    }

    override fun getUserId(): Int {
        return 0
    }

    override fun createConfigurationContext(p0: Configuration?): Context {
        TODO("Not yet implemented")
    }

    override fun createDisplayContext(p0: Display?): Context {
        TODO("Not yet implemented")
    }

    override fun getDisplayAdjustments(p0: Int): DisplayAdjustments {
        TODO("Not yet implemented")
    }
}