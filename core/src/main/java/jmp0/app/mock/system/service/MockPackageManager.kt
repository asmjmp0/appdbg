package jmp0.app.mock.system.service

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.content.pm.*
import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import jmp0.app.DbgContext
import jmp0.app.mock.ClassReplaceTo
import java.io.FileDescriptor

@ClassReplaceTo("")
class MockPackageManager:IPackageManager,IBinder {
    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun isPackageFrozen(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPackageAvailable(p0: String?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPackageInfo(packageName: String?, p1: Int, p2: Int): PackageInfo {
        val uuid = javaClass.getDeclaredField("xxUuid").get(null) as String
        val androidEnvironment = DbgContext.getAndroidEnvironment(uuid)
        if (packageName == androidEnvironment!!.apkFile.packageName) return MockPackageInfo()
        TODO("Not yet implemented")
    }

    override fun getPackageUid(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getPackageGids(p0: String?, p1: Int): IntArray {
        TODO("Not yet implemented")
    }

    override fun currentToCanonicalPackageNames(p0: Array<out String>?): Array<String> {
        TODO("Not yet implemented")
    }

    override fun canonicalToCurrentPackageNames(p0: Array<out String>?): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getPermissionInfo(p0: String?, p1: Int): PermissionInfo {
        TODO("Not yet implemented")
    }

    override fun queryPermissionsByGroup(p0: String?, p1: Int): MutableList<PermissionInfo> {
        TODO("Not yet implemented")
    }

    override fun getPermissionGroupInfo(p0: String?, p1: Int): PermissionGroupInfo {
        TODO("Not yet implemented")
    }

    override fun getAllPermissionGroups(p0: Int): MutableList<PermissionGroupInfo> {
        TODO("Not yet implemented")
    }

    override fun getApplicationInfo(p0: String?, p1: Int, p2: Int): ApplicationInfo {
        TODO("Not yet implemented")
    }

    override fun getActivityInfo(p0: ComponentName?, p1: Int, p2: Int): ActivityInfo {
        TODO("Not yet implemented")
    }

    override fun activitySupportsIntent(p0: ComponentName?, p1: Intent?, p2: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getReceiverInfo(p0: ComponentName?, p1: Int, p2: Int): ActivityInfo {
        TODO("Not yet implemented")
    }

    override fun getServiceInfo(p0: ComponentName?, p1: Int, p2: Int): ServiceInfo {
        TODO("Not yet implemented")
    }

    override fun getProviderInfo(p0: ComponentName?, p1: Int, p2: Int): ProviderInfo {
        TODO("Not yet implemented")
    }

    override fun checkPermission(p0: String?, p1: String?, p2: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkUidPermission(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun addPermission(p0: PermissionInfo?): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePermission(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun grantRuntimePermission(p0: String?, p1: String?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun revokeRuntimePermission(p0: String?, p1: String?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun resetRuntimePermissions() {
        TODO("Not yet implemented")
    }

    override fun getPermissionFlags(p0: String?, p1: String?, p2: Int): Int {
        TODO("Not yet implemented")
    }

    override fun updatePermissionFlags(p0: String?, p1: String?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun updatePermissionFlagsForAllApps(p0: Int, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun shouldShowRequestPermissionRationale(p0: String?, p1: String?, p2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun isProtectedBroadcast(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkSignatures(p0: String?, p1: String?): Int {
        TODO("Not yet implemented")
    }

    override fun checkUidSignatures(p0: Int, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getPackagesForUid(p0: Int): Array<String> {
        val uuid = javaClass.getDeclaredField("xxUuid").get(null) as String
        val androidEnvironment = DbgContext.getAndroidEnvironment(uuid)
        return arrayOf(androidEnvironment!!.apkFile.packageName)
    }

    override fun getNameForUid(p0: Int): String {
        TODO("Not yet implemented")
    }

    override fun getUidForSharedUser(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun getFlagsForUid(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getPrivateFlagsForUid(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun isUidPrivileged(p0: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAppOpPermissionPackages(p0: String?): Array<String> {
        TODO("Not yet implemented")
    }

    override fun resolveIntent(p0: Intent?, p1: String?, p2: Int, p3: Int): ResolveInfo {
        TODO("Not yet implemented")
    }

    override fun canForwardTo(p0: Intent?, p1: String?, p2: Int, p3: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun queryIntentActivities(p0: Intent?, p1: String?, p2: Int, p3: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentActivityOptions(
        p0: ComponentName?,
        p1: Array<out Intent>?,
        p2: Array<out String>?,
        p3: Intent?,
        p4: String?,
        p5: Int,
        p6: Int
    ): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentReceivers(p0: Intent?, p1: String?, p2: Int, p3: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun resolveService(p0: Intent?, p1: String?, p2: Int, p3: Int): ResolveInfo {
        TODO("Not yet implemented")
    }

    override fun queryIntentServices(p0: Intent?, p1: String?, p2: Int, p3: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentContentProviders(p0: Intent?, p1: String?, p2: Int, p3: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun getInstalledPackages(p0: Int, p1: Int): ParceledListSlice<*> {
        TODO("Not yet implemented")
    }

    override fun getPackagesHoldingPermissions(p0: Array<out String>?, p1: Int, p2: Int): ParceledListSlice<*> {
        TODO("Not yet implemented")
    }

    override fun getInstalledApplications(p0: Int, p1: Int): ParceledListSlice<*> {
        TODO("Not yet implemented")
    }

    override fun getPersistentApplications(p0: Int): MutableList<ApplicationInfo> {
        TODO("Not yet implemented")
    }

    override fun resolveContentProvider(p0: String?, p1: Int, p2: Int): ProviderInfo {
        TODO("Not yet implemented")
    }

    override fun querySyncProviders(p0: MutableList<String>?, p1: MutableList<ProviderInfo>?) {
        TODO("Not yet implemented")
    }

    override fun queryContentProviders(p0: String?, p1: Int, p2: Int): ParceledListSlice<*> {
        TODO("Not yet implemented")
    }

    override fun getInstrumentationInfo(p0: ComponentName?, p1: Int): InstrumentationInfo {
        TODO("Not yet implemented")
    }

    override fun queryInstrumentation(p0: String?, p1: Int): MutableList<InstrumentationInfo> {
        TODO("Not yet implemented")
    }

    override fun installPackage(
        p0: String?,
        p1: IPackageInstallObserver2?,
        p2: Int,
        p3: String?,
        p4: VerificationParams?,
        p5: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun installPackageAsUser(
        p0: String?,
        p1: IPackageInstallObserver2?,
        p2: Int,
        p3: String?,
        p4: VerificationParams?,
        p5: String?,
        p6: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun finishPackageInstall(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun setInstallerPackageName(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun deletePackageAsUser(p0: String?, p1: IPackageDeleteObserver?, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun deletePackage(p0: String?, p1: IPackageDeleteObserver2?, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun getInstallerPackageName(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun addPackageToPreferred(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun removePackageFromPreferred(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPreferredPackages(p0: Int): MutableList<PackageInfo> {
        TODO("Not yet implemented")
    }

    override fun resetApplicationPreferences(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastChosenActivity(p0: Intent?, p1: String?, p2: Int): ResolveInfo {
        TODO("Not yet implemented")
    }

    override fun setLastChosenActivity(
        p0: Intent?,
        p1: String?,
        p2: Int,
        p3: IntentFilter?,
        p4: Int,
        p5: ComponentName?
    ) {
        TODO("Not yet implemented")
    }

    override fun addPreferredActivity(
        p0: IntentFilter?,
        p1: Int,
        p2: Array<out ComponentName>?,
        p3: ComponentName?,
        p4: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun replacePreferredActivity(
        p0: IntentFilter?,
        p1: Int,
        p2: Array<out ComponentName>?,
        p3: ComponentName?,
        p4: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun clearPackagePreferredActivities(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPreferredActivities(
        p0: MutableList<IntentFilter>?,
        p1: MutableList<ComponentName>?,
        p2: String?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun addPersistentPreferredActivity(p0: IntentFilter?, p1: ComponentName?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun clearPackagePersistentPreferredActivities(p0: String?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun addCrossProfileIntentFilter(p0: IntentFilter?, p1: String?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun clearCrossProfileIntentFilters(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun getPreferredActivityBackup(p0: Int): ByteArray {
        TODO("Not yet implemented")
    }

    override fun restorePreferredActivities(p0: ByteArray?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getDefaultAppsBackup(p0: Int): ByteArray {
        TODO("Not yet implemented")
    }

    override fun restoreDefaultApps(p0: ByteArray?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getIntentFilterVerificationBackup(p0: Int): ByteArray {
        TODO("Not yet implemented")
    }

    override fun restoreIntentFilterVerification(p0: ByteArray?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getHomeActivities(p0: MutableList<ResolveInfo>?): ComponentName {
        TODO("Not yet implemented")
    }

    override fun setComponentEnabledSetting(p0: ComponentName?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun getComponentEnabledSetting(p0: ComponentName?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun setApplicationEnabledSetting(p0: String?, p1: Int, p2: Int, p3: Int, p4: String?) {
        TODO("Not yet implemented")
    }

    override fun getApplicationEnabledSetting(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun setPackageStoppedState(p0: String?, p1: Boolean, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun freeStorageAndNotify(p0: String?, p1: Long, p2: IPackageDataObserver?) {
        TODO("Not yet implemented")
    }

    override fun freeStorage(p0: String?, p1: Long, p2: IntentSender?) {
        TODO("Not yet implemented")
    }

    override fun deleteApplicationCacheFiles(p0: String?, p1: IPackageDataObserver?) {
        TODO("Not yet implemented")
    }

    override fun clearApplicationUserData(p0: String?, p1: IPackageDataObserver?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun getPackageSizeInfo(p0: String?, p1: Int, p2: IPackageStatsObserver?) {
        TODO("Not yet implemented")
    }

    override fun getSystemSharedLibraryNames(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getSystemAvailableFeatures(): Array<FeatureInfo> {
        TODO("Not yet implemented")
    }

    override fun hasSystemFeature(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun enterSafeMode() {
        TODO("Not yet implemented")
    }

    override fun isSafeMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun systemReady() {
        TODO("Not yet implemented")
    }

    override fun hasSystemUidErrors(): Boolean {
        TODO("Not yet implemented")
    }

    override fun performBootDexOpt() {
        TODO("Not yet implemented")
    }

    override fun performDexOptIfNeeded(p0: String?, p1: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun forceDexOpt(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun updateExternalMediaStatus(p0: Boolean, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun nextPackageToClean(p0: PackageCleanItem?): PackageCleanItem {
        TODO("Not yet implemented")
    }

    override fun getMoveStatus(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun registerMoveCallback(p0: IPackageMoveObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterMoveCallback(p0: IPackageMoveObserver?) {
        TODO("Not yet implemented")
    }

    override fun movePackage(p0: String?, p1: String?): Int {
        TODO("Not yet implemented")
    }

    override fun movePrimaryStorage(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun addPermissionAsync(p0: PermissionInfo?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInstallLocation(p0: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getInstallLocation(): Int {
        TODO("Not yet implemented")
    }

    override fun installExistingPackageAsUser(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun verifyPendingInstall(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun extendVerificationTimeout(p0: Int, p1: Int, p2: Long) {
        TODO("Not yet implemented")
    }

    override fun verifyIntentFilter(p0: Int, p1: Int, p2: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun getIntentVerificationStatus(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun updateIntentVerificationStatus(p0: String?, p1: Int, p2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getIntentFilterVerifications(p0: String?): MutableList<IntentFilterVerificationInfo> {
        TODO("Not yet implemented")
    }

    override fun getAllIntentFilters(p0: String?): MutableList<IntentFilter> {
        TODO("Not yet implemented")
    }

    override fun setDefaultBrowserPackageName(p0: String?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDefaultBrowserPackageName(p0: Int): String {
        TODO("Not yet implemented")
    }

    override fun getVerifierDeviceIdentity(): VerifierDeviceIdentity {
        TODO("Not yet implemented")
    }

    override fun isFirstBoot(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isOnlyCoreApps(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isUpgrade(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPermissionEnforced(p0: String?, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isPermissionEnforced(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isStorageLow(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setApplicationHiddenSettingAsUser(p0: String?, p1: Boolean, p2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getApplicationHiddenSettingAsUser(p0: String?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPackageInstaller(): IPackageInstaller {
        TODO("Not yet implemented")
    }

    override fun setBlockUninstallForUser(p0: String?, p1: Boolean, p2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBlockUninstallForUser(p0: String?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getKeySetByAlias(p0: String?, p1: String?): KeySet {
        TODO("Not yet implemented")
    }

    override fun getSigningKeySet(p0: String?): KeySet {
        TODO("Not yet implemented")
    }

    override fun isPackageSignedByKeySet(p0: String?, p1: KeySet?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPackageSignedByKeySetExactly(p0: String?, p1: KeySet?): Boolean {
        TODO("Not yet implemented")
    }

    override fun addOnPermissionsChangeListener(p0: IOnPermissionsChangeListener?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPermissionsChangeListener(p0: IOnPermissionsChangeListener?) {
        TODO("Not yet implemented")
    }

    override fun grantDefaultPermissionsToEnabledCarrierApps(p0: Array<out String>?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun isPermissionRevokedByPolicy(p0: String?, p1: String?, p2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPermissionControllerPackageName(): String {
        TODO("Not yet implemented")
    }

    override fun getInterfaceDescriptor(): String {
        TODO("Not yet implemented")
    }

    override fun pingBinder(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBinderAlive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun queryLocalInterface(descriptor: String?): IInterface {
        return this
    }

    override fun dump(fd: FileDescriptor?, args: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun dumpAsync(fd: FileDescriptor?, args: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun transact(code: Int, data: Parcel?, reply: Parcel?, flags: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun linkToDeath(recipient: IBinder.DeathRecipient?, flags: Int) {
        TODO("Not yet implemented")
    }

    override fun unlinkToDeath(recipient: IBinder.DeathRecipient?, flags: Int): Boolean {
        TODO("Not yet implemented")
    }
}