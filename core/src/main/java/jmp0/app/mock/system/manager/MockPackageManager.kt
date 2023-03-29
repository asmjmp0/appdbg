package jmp0.app.mock.system.manager

import android.app.PackageInstallObserver
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.content.pm.*
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.UserHandle
import android.os.storage.VolumeInfo
import jmp0.app.mock.annotations.ClassReplaceTo

@ClassReplaceTo("")
class MockPackageManager:PackageManager() {
    override fun getPackageInfo(p0: String?, p1: Int): PackageInfo {
        TODO("Not yet implemented")
    }

    override fun currentToCanonicalPackageNames(p0: Array<out String>?): Array<String> {
        TODO("Not yet implemented")
    }

    override fun canonicalToCurrentPackageNames(p0: Array<out String>?): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getLaunchIntentForPackage(p0: String?): Intent {
        TODO("Not yet implemented")
    }

    override fun getLeanbackLaunchIntentForPackage(p0: String?): Intent {
        TODO("Not yet implemented")
    }

    override fun getPackageGids(p0: String?): IntArray {
        TODO("Not yet implemented")
    }

    override fun getPackageUid(p0: String?, p1: Int): Int {
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

    override fun getApplicationInfo(p0: String?, p1: Int): ApplicationInfo {
        TODO("Not yet implemented")
    }

    override fun getActivityInfo(p0: ComponentName?, p1: Int): ActivityInfo {
        TODO("Not yet implemented")
    }

    override fun getReceiverInfo(p0: ComponentName?, p1: Int): ActivityInfo {
        TODO("Not yet implemented")
    }

    override fun getServiceInfo(p0: ComponentName?, p1: Int): ServiceInfo {
        TODO("Not yet implemented")
    }

    override fun getProviderInfo(p0: ComponentName?, p1: Int): ProviderInfo {
        TODO("Not yet implemented")
    }

    override fun getInstalledPackages(p0: Int): MutableList<PackageInfo> {
        TODO("Not yet implemented")
    }

    override fun getInstalledPackages(p0: Int, p1: Int): MutableList<PackageInfo> {
        TODO("Not yet implemented")
    }

    override fun getPackagesHoldingPermissions(p0: Array<out String>?, p1: Int): MutableList<PackageInfo> {
        TODO("Not yet implemented")
    }

    override fun checkPermission(p0: String?, p1: String?): Int {
        TODO("Not yet implemented")
    }

    override fun isPermissionRevokedByPolicy(p0: String?, p1: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPermissionControllerPackageName(): String {
        TODO("Not yet implemented")
    }

    override fun addPermission(p0: PermissionInfo?): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPermissionAsync(p0: PermissionInfo?): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePermission(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun grantRuntimePermission(p0: String?, p1: String?, p2: UserHandle?) {
        TODO("Not yet implemented")
    }

    override fun revokeRuntimePermission(p0: String?, p1: String?, p2: UserHandle?) {
        TODO("Not yet implemented")
    }

    override fun getPermissionFlags(p0: String?, p1: String?, p2: UserHandle?): Int {
        TODO("Not yet implemented")
    }

    override fun updatePermissionFlags(p0: String?, p1: String?, p2: Int, p3: Int, p4: UserHandle?) {
        TODO("Not yet implemented")
    }

    override fun shouldShowRequestPermissionRationale(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkSignatures(p0: String?, p1: String?): Int {
        TODO("Not yet implemented")
    }

    override fun checkSignatures(p0: Int, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getPackagesForUid(p0: Int): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getNameForUid(p0: Int): String {
        TODO("Not yet implemented")
    }

    override fun getUidForSharedUser(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun getInstalledApplications(p0: Int): MutableList<ApplicationInfo> {
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

    override fun resolveActivity(p0: Intent?, p1: Int): ResolveInfo {
        TODO("Not yet implemented")
    }

    override fun resolveActivityAsUser(p0: Intent?, p1: Int, p2: Int): ResolveInfo {
        TODO("Not yet implemented")
    }

    override fun queryIntentActivities(p0: Intent?, p1: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentActivitiesAsUser(p0: Intent?, p1: Int, p2: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentActivityOptions(
        p0: ComponentName?,
        p1: Array<out Intent>?,
        p2: Intent?,
        p3: Int
    ): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryBroadcastReceivers(p0: Intent?, p1: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryBroadcastReceivers(p0: Intent?, p1: Int, p2: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun resolveService(p0: Intent?, p1: Int): ResolveInfo {
        TODO("Not yet implemented")
    }

    override fun queryIntentServices(p0: Intent?, p1: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentServicesAsUser(p0: Intent?, p1: Int, p2: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentContentProvidersAsUser(p0: Intent?, p1: Int, p2: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun queryIntentContentProviders(p0: Intent?, p1: Int): MutableList<ResolveInfo> {
        TODO("Not yet implemented")
    }

    override fun resolveContentProvider(p0: String?, p1: Int): ProviderInfo {
        TODO("Not yet implemented")
    }

    override fun resolveContentProviderAsUser(p0: String?, p1: Int, p2: Int): ProviderInfo {
        TODO("Not yet implemented")
    }

    override fun queryContentProviders(p0: String?, p1: Int, p2: Int): MutableList<ProviderInfo> {
        TODO("Not yet implemented")
    }

    override fun getInstrumentationInfo(p0: ComponentName?, p1: Int): InstrumentationInfo {
        TODO("Not yet implemented")
    }

    override fun queryInstrumentation(p0: String?, p1: Int): MutableList<InstrumentationInfo> {
        TODO("Not yet implemented")
    }

    override fun getDrawable(p0: String?, p1: Int, p2: ApplicationInfo?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getActivityIcon(p0: ComponentName?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getActivityIcon(p0: Intent?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getActivityBanner(p0: ComponentName?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getActivityBanner(p0: Intent?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getDefaultActivityIcon(): Drawable {
        TODO("Not yet implemented")
    }

    override fun getApplicationIcon(p0: ApplicationInfo?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getApplicationIcon(p0: String?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getApplicationBanner(p0: ApplicationInfo?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getApplicationBanner(p0: String?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getActivityLogo(p0: ComponentName?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getActivityLogo(p0: Intent?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getApplicationLogo(p0: ApplicationInfo?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getApplicationLogo(p0: String?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getUserBadgedIcon(p0: Drawable?, p1: UserHandle?): Drawable {
        TODO("Not yet implemented")
    }

    override fun getUserBadgedDrawableForDensity(p0: Drawable?, p1: UserHandle?, p2: Rect?, p3: Int): Drawable {
        TODO("Not yet implemented")
    }

    override fun getUserBadgeForDensity(p0: UserHandle?, p1: Int): Drawable {
        TODO("Not yet implemented")
    }

    override fun getUserBadgedLabel(p0: CharSequence?, p1: UserHandle?): CharSequence {
        TODO("Not yet implemented")
    }

    override fun getText(p0: String?, p1: Int, p2: ApplicationInfo?): CharSequence {
        TODO("Not yet implemented")
    }

    override fun getXml(p0: String?, p1: Int, p2: ApplicationInfo?): XmlResourceParser {
        TODO("Not yet implemented")
    }

    override fun getApplicationLabel(p0: ApplicationInfo?): CharSequence {
        TODO("Not yet implemented")
    }

    override fun getResourcesForActivity(p0: ComponentName?): Resources {
        TODO("Not yet implemented")
    }

    override fun getResourcesForApplication(p0: ApplicationInfo?): Resources {
        TODO("Not yet implemented")
    }

    override fun getResourcesForApplication(p0: String?): Resources {
        TODO("Not yet implemented")
    }

    override fun getResourcesForApplicationAsUser(p0: String?, p1: Int): Resources {
        TODO("Not yet implemented")
    }

    override fun installPackage(p0: Uri?, p1: IPackageInstallObserver?, p2: Int, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun installPackage(p0: Uri?, p1: PackageInstallObserver?, p2: Int, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun installPackageWithVerification(
        p0: Uri?,
        p1: IPackageInstallObserver?,
        p2: Int,
        p3: String?,
        p4: Uri?,
        p5: ManifestDigest?,
        p6: ContainerEncryptionParams?
    ) {
        TODO("Not yet implemented")
    }

    override fun installPackageWithVerification(
        p0: Uri?,
        p1: PackageInstallObserver?,
        p2: Int,
        p3: String?,
        p4: Uri?,
        p5: ManifestDigest?,
        p6: ContainerEncryptionParams?
    ) {
        TODO("Not yet implemented")
    }

    override fun installPackageWithVerificationAndEncryption(
        p0: Uri?,
        p1: IPackageInstallObserver?,
        p2: Int,
        p3: String?,
        p4: VerificationParams?,
        p5: ContainerEncryptionParams?
    ) {
        TODO("Not yet implemented")
    }

    override fun installPackageWithVerificationAndEncryption(
        p0: Uri?,
        p1: PackageInstallObserver?,
        p2: Int,
        p3: String?,
        p4: VerificationParams?,
        p5: ContainerEncryptionParams?
    ) {
        TODO("Not yet implemented")
    }

    override fun installExistingPackage(p0: String?): Int {
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

    override fun getDefaultBrowserPackageName(p0: Int): String {
        TODO("Not yet implemented")
    }

    override fun setDefaultBrowserPackageName(p0: String?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInstallerPackageName(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun deletePackage(p0: String?, p1: IPackageDeleteObserver?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun getInstallerPackageName(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun clearApplicationUserData(p0: String?, p1: IPackageDataObserver?) {
        TODO("Not yet implemented")
    }

    override fun deleteApplicationCacheFiles(p0: String?, p1: IPackageDataObserver?) {
        TODO("Not yet implemented")
    }

    override fun freeStorageAndNotify(p0: String?, p1: Long, p2: IPackageDataObserver?) {
        TODO("Not yet implemented")
    }

    override fun freeStorage(p0: String?, p1: Long, p2: IntentSender?) {
        TODO("Not yet implemented")
    }

    override fun getPackageSizeInfo(p0: String?, p1: Int, p2: IPackageStatsObserver?) {
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

    override fun addPreferredActivity(p0: IntentFilter?, p1: Int, p2: Array<out ComponentName>?, p3: ComponentName?) {
        TODO("Not yet implemented")
    }

    override fun replacePreferredActivity(
        p0: IntentFilter?,
        p1: Int,
        p2: Array<out ComponentName>?,
        p3: ComponentName?
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

    override fun getHomeActivities(p0: MutableList<ResolveInfo>?): ComponentName {
        TODO("Not yet implemented")
    }

    override fun setComponentEnabledSetting(p0: ComponentName?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun getComponentEnabledSetting(p0: ComponentName?): Int {
        TODO("Not yet implemented")
    }

    override fun setApplicationEnabledSetting(p0: String?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun getApplicationEnabledSetting(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun setApplicationHiddenSettingAsUser(p0: String?, p1: Boolean, p2: UserHandle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getApplicationHiddenSettingAsUser(p0: String?, p1: UserHandle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSafeMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun addOnPermissionsChangeListener(p0: OnPermissionsChangedListener?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPermissionsChangeListener(p0: OnPermissionsChangedListener?) {
        TODO("Not yet implemented")
    }

    override fun getKeySetByAlias(p0: String?, p1: String?): KeySet {
        TODO("Not yet implemented")
    }

    override fun getSigningKeySet(p0: String?): KeySet {
        TODO("Not yet implemented")
    }

    override fun isSignedBy(p0: String?, p1: KeySet?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSignedByExactly(p0: String?, p1: KeySet?): Boolean {
        TODO("Not yet implemented")
    }

    override fun movePackage(p0: String?, p1: VolumeInfo?): Int {
        TODO("Not yet implemented")
    }

    override fun getMoveStatus(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun registerMoveCallback(p0: MoveCallback?, p1: Handler?) {
        TODO("Not yet implemented")
    }

    override fun unregisterMoveCallback(p0: MoveCallback?) {
        TODO("Not yet implemented")
    }

    override fun getPackageCurrentVolume(p0: ApplicationInfo?): VolumeInfo {
        TODO("Not yet implemented")
    }

    override fun getPackageCandidateVolumes(p0: ApplicationInfo?): MutableList<VolumeInfo> {
        TODO("Not yet implemented")
    }

    override fun movePrimaryStorage(p0: VolumeInfo?): Int {
        TODO("Not yet implemented")
    }

    override fun getPrimaryStorageCurrentVolume(): VolumeInfo {
        TODO("Not yet implemented")
    }

    override fun getPrimaryStorageCandidateVolumes(): MutableList<VolumeInfo> {
        TODO("Not yet implemented")
    }

    override fun getVerifierDeviceIdentity(): VerifierDeviceIdentity {
        TODO("Not yet implemented")
    }

    override fun isUpgrade(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPackageInstaller(): PackageInstaller {
        TODO("Not yet implemented")
    }

    override fun addCrossProfileIntentFilter(p0: IntentFilter?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun clearCrossProfileIntentFilters(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun loadItemIcon(p0: PackageItemInfo?, p1: ApplicationInfo?): Drawable {
        TODO("Not yet implemented")
    }

    override fun loadUnbadgedItemIcon(p0: PackageItemInfo?, p1: ApplicationInfo?): Drawable {
        TODO("Not yet implemented")
    }

    override fun isPackageAvailable(p0: String?): Boolean {
        TODO("Not yet implemented")
    }
}