package jmp0.app.mock.system.service

import android.app.*
import android.app.assist.AssistContent
import android.app.assist.AssistStructure
import android.content.*
import android.content.pm.*
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.*
import android.service.voice.IVoiceInteractionSession
import com.android.internal.app.IVoiceInteractor
import com.android.internal.os.IResultReceiver
import jmp0.app.DbgContext
import jmp0.app.mock.ClassReplaceTo
import org.apache.log4j.Logger
import java.util.*

@ClassReplaceTo("")
class MockActivityManager: IActivityManager, Binder() {

    private val logger = Logger.getLogger(MockActivityManager::class.java)

    override fun queryLocalInterface(descriptor: String?): IInterface {
        if (descriptor == "android.app.IActivityManager") return this
        TODO("Not yet implemented")
    }
    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun startActivity(
        caller: IApplicationThread?,
        callingPackage: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        profilerInfo: ProfilerInfo?,
        options: Bundle?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun startActivityAsUser(
        caller: IApplicationThread?,
        callingPackage: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        profilerInfo: ProfilerInfo?,
        options: Bundle?,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun startActivityAsCaller(
        caller: IApplicationThread?,
        callingPackage: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        profilerInfo: ProfilerInfo?,
        options: Bundle?,
        ignoreTargetSecurity: Boolean,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun startActivityAndWait(
        caller: IApplicationThread?,
        callingPackage: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        profilerInfo: ProfilerInfo?,
        options: Bundle?,
        userId: Int
    ): IActivityManager.WaitResult {
        TODO("Not yet implemented")
    }

    override fun startActivityWithConfig(
        caller: IApplicationThread?,
        callingPackage: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        startFlags: Int,
        newConfig: Configuration?,
        options: Bundle?,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun startActivityIntentSender(
        caller: IApplicationThread?,
        intent: IntentSender?,
        fillInIntent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flagsMask: Int,
        flagsValues: Int,
        options: Bundle?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun startVoiceActivity(
        callingPackage: String?,
        callingPid: Int,
        callingUid: Int,
        intent: Intent?,
        resolvedType: String?,
        session: IVoiceInteractionSession?,
        interactor: IVoiceInteractor?,
        flags: Int,
        profilerInfo: ProfilerInfo?,
        options: Bundle?,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun startNextMatchingActivity(callingActivity: IBinder?, intent: Intent?, options: Bundle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun startActivityFromRecents(taskId: Int, options: Bundle?): Int {
        TODO("Not yet implemented")
    }

    override fun finishActivity(token: IBinder?, code: Int, data: Intent?, finishTask: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishSubActivity(token: IBinder?, resultWho: String?, requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun finishActivityAffinity(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishVoiceTask(session: IVoiceInteractionSession?) {
        TODO("Not yet implemented")
    }

    override fun releaseActivityInstance(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun releaseSomeActivities(app: IApplicationThread?) {
        TODO("Not yet implemented")
    }

    override fun willActivityBeVisible(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun registerReceiver(
        caller: IApplicationThread?,
        callerPackage: String?,
        receiver: IIntentReceiver?,
        filter: IntentFilter?,
        requiredPermission: String?,
        userId: Int
    ): Intent {
        TODO("Not yet implemented")
    }

    override fun unregisterReceiver(receiver: IIntentReceiver?) {
        TODO("Not yet implemented")
    }

    override fun broadcastIntent(
        caller: IApplicationThread?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IIntentReceiver?,
        resultCode: Int,
        resultData: String?,
        map: Bundle?,
        requiredPermissions: Array<out String>?,
        appOp: Int,
        options: Bundle?,
        serialized: Boolean,
        sticky: Boolean,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun unbroadcastIntent(caller: IApplicationThread?, intent: Intent?, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun finishReceiver(
        who: IBinder?,
        resultCode: Int,
        resultData: String?,
        map: Bundle?,
        abortBroadcast: Boolean,
        flags: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun attachApplication(app: IApplicationThread) {
        val uuid = javaClass.getDeclaredField("xxUuid").get(null) as String
        val androidEnvironment = DbgContext.getAndroidEnvironment(uuid)
        val mockConfiguration = Configuration()
        mockConfiguration.locale = Locale("en")

        app.bindApplication(androidEnvironment!!.apkFile.packageName,
            MockApplicationInfo(),null,ComponentName(androidEnvironment.apkFile.packageName,androidEnvironment.apkFile.packageName),
        MockProfilerInfo(),null,null,null,
            0,false,false,false,mockConfiguration,null,null, Bundle()
        )
        logger.warn("attachApplication just return")
    }

    override fun activityResumed(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun activityIdle(token: IBinder?, config: Configuration?, stopProfiling: Boolean) {
        TODO("Not yet implemented")
    }

    override fun activityPaused(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun activityStopped(
        token: IBinder?,
        state: Bundle?,
        persistentState: PersistableBundle?,
        description: CharSequence?
    ) {
        TODO("Not yet implemented")
    }

    override fun activitySlept(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun activityDestroyed(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun getCallingPackage(token: IBinder?): String {
        TODO("Not yet implemented")
    }

    override fun getCallingActivity(token: IBinder?): ComponentName {
        TODO("Not yet implemented")
    }

    override fun getAppTasks(callingPackage: String?): MutableList<IAppTask> {
        TODO("Not yet implemented")
    }

    override fun addAppTask(
        activityToken: IBinder?,
        intent: Intent?,
        description: ActivityManager.TaskDescription?,
        thumbnail: Bitmap?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun getAppTaskThumbnailSize(): Point {
        TODO("Not yet implemented")
    }

    override fun getTasks(maxNum: Int, flags: Int): MutableList<ActivityManager.RunningTaskInfo> {
        TODO("Not yet implemented")
    }

    override fun getRecentTasks(maxNum: Int, flags: Int, userId: Int): MutableList<ActivityManager.RecentTaskInfo> {
        TODO("Not yet implemented")
    }

    override fun getTaskThumbnail(taskId: Int): ActivityManager.TaskThumbnail {
        TODO("Not yet implemented")
    }

    override fun getServices(maxNum: Int, flags: Int): MutableList<ActivityManager.RunningServiceInfo> {
        TODO("Not yet implemented")
    }

    override fun getProcessesInErrorState(): MutableList<ActivityManager.ProcessErrorStateInfo> {
        TODO("Not yet implemented")
    }

    override fun moveTaskToFront(task: Int, flags: Int, options: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun moveActivityTaskToBack(token: IBinder?, nonRoot: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun moveTaskBackwards(task: Int) {
        TODO("Not yet implemented")
    }

    override fun moveTaskToStack(taskId: Int, stackId: Int, toTop: Boolean) {
        TODO("Not yet implemented")
    }

    override fun resizeStack(stackId: Int, bounds: Rect?) {
        TODO("Not yet implemented")
    }

    override fun getAllStackInfos(): MutableList<ActivityManager.StackInfo> {
        TODO("Not yet implemented")
    }

    override fun getStackInfo(stackId: Int): ActivityManager.StackInfo {
        TODO("Not yet implemented")
    }

    override fun isInHomeStack(taskId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFocusedStack(stackId: Int) {
        TODO("Not yet implemented")
    }

    override fun getFocusedStackId(): Int {
        TODO("Not yet implemented")
    }

    override fun registerTaskStackListener(listener: ITaskStackListener?) {
        TODO("Not yet implemented")
    }

    override fun getTaskForActivity(token: IBinder?, onlyRoot: Boolean): Int {
        TODO("Not yet implemented")
    }

    override fun getContentProvider(
        caller: IApplicationThread?,
        name: String?,
        userId: Int,
        stable: Boolean
    ): IActivityManager.ContentProviderHolder {
        TODO("Not yet implemented")
    }

    override fun getContentProviderExternal(
        name: String?,
        userId: Int,
        token: IBinder?
    ): IActivityManager.ContentProviderHolder {
        TODO("Not yet implemented")
    }

    override fun removeContentProvider(connection: IBinder?, stable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun removeContentProviderExternal(name: String?, token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun publishContentProviders(
        caller: IApplicationThread?,
        providers: MutableList<IActivityManager.ContentProviderHolder>?
    ) {
        TODO("Not yet implemented")
    }

    override fun refContentProvider(connection: IBinder?, stableDelta: Int, unstableDelta: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun unstableProviderDied(connection: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun appNotRespondingViaProvider(connection: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun getRunningServiceControlPanel(service: ComponentName?): PendingIntent {
        TODO("Not yet implemented")
    }

    override fun startService(
        caller: IApplicationThread?,
        service: Intent?,
        resolvedType: String?,
        callingPackage: String?,
        userId: Int
    ): ComponentName {
        TODO("Not yet implemented")
    }

    override fun stopService(caller: IApplicationThread?, service: Intent?, resolvedType: String?, userId: Int): Int {
        TODO("Not yet implemented")
    }

    override fun stopServiceToken(className: ComponentName?, token: IBinder?, startId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun setServiceForeground(
        className: ComponentName?,
        token: IBinder?,
        id: Int,
        notification: Notification?,
        keepNotification: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun bindService(
        caller: IApplicationThread?,
        token: IBinder?,
        service: Intent?,
        resolvedType: String?,
        connection: IServiceConnection?,
        flags: Int,
        callingPackage: String?,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun unbindService(connection: IServiceConnection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun publishService(token: IBinder?, intent: Intent?, service: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun unbindFinished(token: IBinder?, service: Intent?, doRebind: Boolean) {
        TODO("Not yet implemented")
    }

    override fun serviceDoneExecuting(token: IBinder?, type: Int, startId: Int, res: Int) {
        TODO("Not yet implemented")
    }

    override fun peekService(service: Intent?, resolvedType: String?, callingPackage: String?): IBinder {
        TODO("Not yet implemented")
    }

    override fun bindBackupAgent(appInfo: ApplicationInfo?, backupRestoreMode: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearPendingBackup() {
        TODO("Not yet implemented")
    }

    override fun backupAgentCreated(packageName: String?, agent: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun unbindBackupAgent(appInfo: ApplicationInfo?) {
        TODO("Not yet implemented")
    }

    override fun killApplicationProcess(processName: String?, uid: Int) {
        TODO("Not yet implemented")
    }

    override fun startInstrumentation(
        className: ComponentName?,
        profileFile: String?,
        flags: Int,
        arguments: Bundle?,
        watcher: IInstrumentationWatcher?,
        connection: IUiAutomationConnection?,
        userId: Int,
        abiOverride: String?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishInstrumentation(target: IApplicationThread?, resultCode: Int, results: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun getConfiguration(): Configuration {
        TODO("Not yet implemented")
    }

    override fun updateConfiguration(values: Configuration?) {
        TODO("Not yet implemented")
    }

    override fun setRequestedOrientation(token: IBinder?, requestedOrientation: Int) {
        TODO("Not yet implemented")
    }

    override fun getRequestedOrientation(token: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun getActivityClassForToken(token: IBinder?): ComponentName {
        TODO("Not yet implemented")
    }

    override fun getPackageForToken(token: IBinder?): String {
        TODO("Not yet implemented")
    }

    override fun getIntentSender(
        type: Int,
        packageName: String?,
        token: IBinder?,
        resultWho: String?,
        requestCode: Int,
        intents: Array<out Intent>?,
        resolvedTypes: Array<out String>?,
        flags: Int,
        options: Bundle?,
        userId: Int
    ): IIntentSender {
        TODO("Not yet implemented")
    }

    override fun cancelIntentSender(sender: IIntentSender?) {
        TODO("Not yet implemented")
    }

    override fun clearApplicationUserData(packageName: String?, observer: IPackageDataObserver?, userId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPackageForIntentSender(sender: IIntentSender?): String {
        TODO("Not yet implemented")
    }

    override fun getUidForIntentSender(sender: IIntentSender?): Int {
        TODO("Not yet implemented")
    }

    override fun handleIncomingUser(
        callingPid: Int,
        callingUid: Int,
        userId: Int,
        allowAll: Boolean,
        requireFull: Boolean,
        name: String?,
        callerPackage: String?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun setProcessLimit(max: Int) {
        TODO("Not yet implemented")
    }

    override fun getProcessLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun setProcessForeground(token: IBinder?, pid: Int, isForeground: Boolean) {
        TODO("Not yet implemented")
    }

    override fun checkPermission(permission: String?, pid: Int, uid: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkPermissionWithToken(permission: String?, pid: Int, uid: Int, callerToken: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun checkUriPermission(uri: Uri?, pid: Int, uid: Int, mode: Int, userId: Int, callerToken: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun grantUriPermission(
        caller: IApplicationThread?,
        targetPkg: String?,
        uri: Uri?,
        mode: Int,
        userId: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun revokeUriPermission(caller: IApplicationThread?, uri: Uri?, mode: Int, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun takePersistableUriPermission(uri: Uri?, modeFlags: Int, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun releasePersistableUriPermission(uri: Uri?, modeFlags: Int, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun getPersistedUriPermissions(packageName: String?, incoming: Boolean): ParceledListSlice<UriPermission> {
        TODO("Not yet implemented")
    }

    override fun showWaitingForDebugger(who: IApplicationThread?, waiting: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getMemoryInfo(outInfo: ActivityManager.MemoryInfo?) {
        TODO("Not yet implemented")
    }

    override fun killBackgroundProcesses(packageName: String?, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun killAllBackgroundProcesses() {
        TODO("Not yet implemented")
    }

    override fun forceStopPackage(packageName: String?, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun setLockScreenShown(shown: Boolean) {
        TODO("Not yet implemented")
    }

    override fun unhandledBack() {
        TODO("Not yet implemented")
    }

    override fun openContentUri(uri: Uri?): ParcelFileDescriptor {
        TODO("Not yet implemented")
    }

    override fun setDebugApp(packageName: String?, waitForDebugger: Boolean, persistent: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setAlwaysFinish(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setActivityController(watcher: IActivityController?) {
        TODO("Not yet implemented")
    }

    override fun enterSafeMode() {
        TODO("Not yet implemented")
    }

    override fun noteWakeupAlarm(sender: IIntentSender?, sourceUid: Int, sourcePkg: String?, tag: String?) {
        TODO("Not yet implemented")
    }

    override fun noteAlarmStart(sender: IIntentSender?, sourceUid: Int, tag: String?) {
        TODO("Not yet implemented")
    }

    override fun noteAlarmFinish(sender: IIntentSender?, sourceUid: Int, tag: String?) {
        TODO("Not yet implemented")
    }

    override fun killPids(pids: IntArray?, reason: String?, secure: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun killProcessesBelowForeground(reason: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun handleApplicationCrash(app: IBinder?, crashInfo: ApplicationErrorReport.CrashInfo?) {
        TODO("Not yet implemented")
    }

    override fun handleApplicationWtf(
        app: IBinder?,
        tag: String?,
        system: Boolean,
        crashInfo: ApplicationErrorReport.CrashInfo?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun handleApplicationStrictModeViolation(
        app: IBinder?,
        violationMask: Int,
        crashInfo: StrictMode.ViolationInfo?
    ) {
        TODO("Not yet implemented")
    }

    override fun signalPersistentProcesses(signal: Int) {
        TODO("Not yet implemented")
    }

    override fun getRunningAppProcesses(): MutableList<ActivityManager.RunningAppProcessInfo> {
        TODO("Not yet implemented")
    }

    override fun getRunningExternalApplications(): MutableList<ApplicationInfo> {
        TODO("Not yet implemented")
    }

    override fun getMyMemoryState(outInfo: ActivityManager.RunningAppProcessInfo?) {
        TODO("Not yet implemented")
    }

    override fun getDeviceConfigurationInfo(): ConfigurationInfo {
        TODO("Not yet implemented")
    }

    override fun profileControl(
        process: String?,
        userId: Int,
        start: Boolean,
        profilerInfo: ProfilerInfo?,
        profileType: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun shutdown(timeout: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun stopAppSwitches() {
        TODO("Not yet implemented")
    }

    override fun resumeAppSwitches() {
        TODO("Not yet implemented")
    }

    override fun addPackageDependency(packageName: String?) {
        TODO("Not yet implemented")
    }

    override fun killApplicationWithAppId(pkg: String?, appid: Int, reason: String?) {
        TODO("Not yet implemented")
    }

    override fun closeSystemDialogs(reason: String?) {
        TODO("Not yet implemented")
    }

    override fun getProcessMemoryInfo(pids: IntArray?): Array<Debug.MemoryInfo> {
        TODO("Not yet implemented")
    }

    override fun overridePendingTransition(token: IBinder?, packageName: String?, enterAnim: Int, exitAnim: Int) {
        TODO("Not yet implemented")
    }

    override fun isUserAMonkey(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setUserIsMonkey(monkey: Boolean) {
        TODO("Not yet implemented")
    }

    override fun finishHeavyWeightApp() {
        TODO("Not yet implemented")
    }

    override fun convertFromTranslucent(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun convertToTranslucent(token: IBinder?, options: ActivityOptions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun notifyActivityDrawn(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun getActivityOptions(token: IBinder?): ActivityOptions {
        TODO("Not yet implemented")
    }

    override fun bootAnimationComplete() {
        TODO("Not yet implemented")
    }

    override fun setImmersive(token: IBinder?, immersive: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isImmersive(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTopActivityImmersive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTopOfTask(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun crashApplication(uid: Int, initialPid: Int, packageName: String?, message: String?) {
        TODO("Not yet implemented")
    }

    override fun getProviderMimeType(uri: Uri?, userId: Int): String {
        TODO("Not yet implemented")
    }

    override fun newUriPermissionOwner(name: String?): IBinder {
        TODO("Not yet implemented")
    }

    override fun grantUriPermissionFromOwner(
        owner: IBinder?,
        fromUid: Int,
        targetPkg: String?,
        uri: Uri?,
        mode: Int,
        sourceUserId: Int,
        targetUserId: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun revokeUriPermissionFromOwner(owner: IBinder?, uri: Uri?, mode: Int, userId: Int) {
        TODO("Not yet implemented")
    }

    override fun checkGrantUriPermission(
        callingUid: Int,
        targetPkg: String?,
        uri: Uri?,
        modeFlags: Int,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun dumpHeap(
        process: String?,
        userId: Int,
        managed: Boolean,
        path: String?,
        fd: ParcelFileDescriptor?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun startActivities(
        caller: IApplicationThread?,
        callingPackage: String?,
        intents: Array<out Intent>?,
        resolvedTypes: Array<out String>?,
        resultTo: IBinder?,
        options: Bundle?,
        userId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun getFrontActivityScreenCompatMode(): Int {
        TODO("Not yet implemented")
    }

    override fun setFrontActivityScreenCompatMode(mode: Int) {
        TODO("Not yet implemented")
    }

    override fun getPackageScreenCompatMode(packageName: String?): Int {
        TODO("Not yet implemented")
    }

    override fun setPackageScreenCompatMode(packageName: String?, mode: Int) {
        TODO("Not yet implemented")
    }

    override fun getPackageAskScreenCompat(packageName: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPackageAskScreenCompat(packageName: String?, ask: Boolean) {
        TODO("Not yet implemented")
    }

    override fun switchUser(userid: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun startUserInBackground(userid: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun stopUser(userid: Int, callback: IStopUserCallback?): Int {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): UserInfo {
        TODO("Not yet implemented")
    }

    override fun isUserRunning(userid: Int, orStopping: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRunningUserIds(): IntArray {
        TODO("Not yet implemented")
    }

    override fun removeTask(taskId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun registerProcessObserver(observer: IProcessObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterProcessObserver(observer: IProcessObserver?) {
        TODO("Not yet implemented")
    }

    override fun registerUidObserver(observer: IUidObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterUidObserver(observer: IUidObserver?) {
        TODO("Not yet implemented")
    }

    override fun isIntentSenderTargetedToPackage(sender: IIntentSender?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isIntentSenderAnActivity(sender: IIntentSender?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getIntentForIntentSender(sender: IIntentSender?): Intent {
        TODO("Not yet implemented")
    }

    override fun getTagForIntentSender(sender: IIntentSender?, prefix: String?): String {
        TODO("Not yet implemented")
    }

    override fun updatePersistentConfiguration(values: Configuration?) {
        TODO("Not yet implemented")
    }

    override fun getProcessPss(pids: IntArray?): LongArray {
        TODO("Not yet implemented")
    }

    override fun showBootMessage(msg: CharSequence?, always: Boolean) {
        TODO("Not yet implemented")
    }

    override fun keyguardWaitingForActivityDrawn() {
        TODO("Not yet implemented")
    }

    override fun keyguardGoingAway(disableWindowAnimations: Boolean, keyguardGoingToNotificationShade: Boolean) {
        TODO("Not yet implemented")
    }

    override fun shouldUpRecreateTask(token: IBinder?, destAffinity: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun navigateUpTo(token: IBinder?, target: Intent?, resultCode: Int, resultData: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLaunchedFromUid(activityToken: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun getLaunchedFromPackage(activityToken: IBinder?): String {
        TODO("Not yet implemented")
    }

    override fun registerUserSwitchObserver(observer: IUserSwitchObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterUserSwitchObserver(observer: IUserSwitchObserver?) {
        TODO("Not yet implemented")
    }

    override fun requestBugReport() {
        TODO("Not yet implemented")
    }

    override fun inputDispatchingTimedOut(pid: Int, aboveSystem: Boolean, reason: String?): Long {
        TODO("Not yet implemented")
    }

    override fun getAssistContextExtras(requestType: Int): Bundle {
        TODO("Not yet implemented")
    }

    override fun requestAssistContextExtras(
        requestType: Int,
        receiver: IResultReceiver?,
        activityToken: IBinder?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun reportAssistContextExtras(
        token: IBinder?,
        extras: Bundle?,
        structure: AssistStructure?,
        content: AssistContent?,
        referrer: Uri?
    ) {
        TODO("Not yet implemented")
    }

    override fun launchAssistIntent(
        intent: Intent?,
        requestType: Int,
        hint: String?,
        userHandle: Int,
        args: Bundle?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAssistDataAllowedOnCurrentActivity(): Boolean {
        TODO("Not yet implemented")
    }

    override fun showAssistFromActivity(token: IBinder?, args: Bundle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun killUid(appId: Int, userId: Int, reason: String?) {
        TODO("Not yet implemented")
    }

    override fun hang(who: IBinder?, allowRestart: Boolean) {
        TODO("Not yet implemented")
    }

    override fun reportActivityFullyDrawn(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun restart() {
        TODO("Not yet implemented")
    }

    override fun performIdleMaintenance() {
        TODO("Not yet implemented")
    }

    override fun createVirtualActivityContainer(
        parentActivityToken: IBinder?,
        callback: IActivityContainerCallback?
    ): IActivityContainer {
        TODO("Not yet implemented")
    }

    override fun createStackOnDisplay(displayId: Int): IActivityContainer {
        TODO("Not yet implemented")
    }

    override fun deleteActivityContainer(container: IActivityContainer?) {
        TODO("Not yet implemented")
    }

    override fun getActivityDisplayId(activityToken: IBinder?): Int {
        TODO("Not yet implemented")
    }

    override fun startLockTaskModeOnCurrent() {
        TODO("Not yet implemented")
    }

    override fun startLockTaskMode(taskId: Int) {
        TODO("Not yet implemented")
    }

    override fun startLockTaskMode(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun stopLockTaskMode() {
        TODO("Not yet implemented")
    }

    override fun stopLockTaskModeOnCurrent() {
        TODO("Not yet implemented")
    }

    override fun isInLockTaskMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLockTaskModeState(): Int {
        TODO("Not yet implemented")
    }

    override fun showLockTaskEscapeMessage(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun setTaskDescription(token: IBinder?, values: ActivityManager.TaskDescription?) {
        TODO("Not yet implemented")
    }

    override fun setTaskResizeable(taskId: Int, resizeable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun resizeTask(taskId: Int, bounds: Rect?) {
        TODO("Not yet implemented")
    }

    override fun getTaskDescriptionIcon(filename: String?): Bitmap {
        TODO("Not yet implemented")
    }

    override fun startInPlaceAnimationOnFrontMostApplication(opts: ActivityOptions?) {
        TODO("Not yet implemented")
    }

    override fun requestVisibleBehind(token: IBinder?, visible: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun isBackgroundVisibleBehind(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun backgroundResourcesReleased(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun notifyLaunchTaskBehindComplete(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun notifyEnterAnimationComplete(token: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun notifyCleartextNetwork(uid: Int, firstPacket: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun setDumpHeapDebugLimit(processName: String?, uid: Int, maxMemSize: Long, reportPackage: String?) {
        TODO("Not yet implemented")
    }

    override fun dumpHeapFinished(path: String?) {
        TODO("Not yet implemented")
    }

    override fun setVoiceKeepAwake(session: IVoiceInteractionSession?, keepAwake: Boolean) {
        TODO("Not yet implemented")
    }

    override fun updateLockTaskPackages(userId: Int, packages: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun updateDeviceOwner(packageName: String?) {
        TODO("Not yet implemented")
    }

    override fun getPackageProcessState(packageName: String?, callingPackage: String?): Int {
        TODO("Not yet implemented")
    }

    override fun setProcessMemoryTrimLevel(process: String?, uid: Int, level: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun isRootVoiceInteraction(token: IBinder?): Boolean {
        TODO("Not yet implemented")
    }

    override fun testIsSystemReady(): Boolean {
        TODO("Not yet implemented")
    }
}