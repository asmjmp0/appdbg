package jmp0.app.mock.system.service

import android.net.LinkProperties
import android.net.NetworkCapabilities
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.telephony.*
import com.android.internal.telephony.IOnSubscriptionsChangedListener
import com.android.internal.telephony.IPhoneStateListener
import com.android.internal.telephony.ITelephonyRegistry
import jmp0.app.mock.annotations.ClassReplaceTo

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/6
 */
@ClassReplaceTo("")
class MockTelephonyRegistry:ITelephonyRegistry, Binder() {
    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun addOnSubscriptionsChangedListener(p0: String?, p1: IOnSubscriptionsChangedListener?) {
        TODO("Not yet implemented")
    }

    override fun removeOnSubscriptionsChangedListener(p0: String?, p1: IOnSubscriptionsChangedListener?) {
        TODO("Not yet implemented")
    }

    override fun listen(p0: String?, p1: IPhoneStateListener?, p2: Int, p3: Boolean) {
        TODO("Not yet implemented")
    }

    override fun listenForSubscriber(p0: Int, p1: String?, p2: IPhoneStateListener?, p3: Int, p4: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyCallState(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun notifyCallStateForSubscriber(p0: Int, p1: Int, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun notifyServiceStateForPhoneId(p0: Int, p1: Int, p2: ServiceState?) {
        TODO("Not yet implemented")
    }

    override fun notifySignalStrength(p0: SignalStrength?) {
        TODO("Not yet implemented")
    }

    override fun notifySignalStrengthForSubscriber(p0: Int, p1: SignalStrength?) {
        TODO("Not yet implemented")
    }

    override fun notifyMessageWaitingChangedForPhoneId(p0: Int, p1: Int, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyCallForwardingChanged(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyCallForwardingChangedForSubscriber(p0: Int, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyDataActivity(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun notifyDataActivityForSubscriber(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun notifyDataConnection(
        p0: Int,
        p1: Boolean,
        p2: String?,
        p3: String?,
        p4: String?,
        p5: LinkProperties?,
        p6: NetworkCapabilities?,
        p7: Int,
        p8: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun notifyDataConnectionForSubscriber(
        p0: Int,
        p1: Int,
        p2: Boolean,
        p3: String?,
        p4: String?,
        p5: String?,
        p6: LinkProperties?,
        p7: NetworkCapabilities?,
        p8: Int,
        p9: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun notifyDataConnectionFailed(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun notifyDataConnectionFailedForSubscriber(p0: Int, p1: String?, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun notifyCellLocation(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun notifyCellLocationForSubscriber(p0: Int, p1: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun notifyOtaspChanged(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun notifyCellInfo(p0: MutableList<CellInfo>?) {
        TODO("Not yet implemented")
    }

    override fun notifyPreciseCallState(p0: Int, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun notifyDisconnectCause(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun notifyPreciseDataConnectionFailed(p0: String?, p1: String?, p2: String?, p3: String?) {
        TODO("Not yet implemented")
    }

    override fun notifyCellInfoForSubscriber(p0: Int, p1: MutableList<CellInfo>?) {
        TODO("Not yet implemented")
    }

    override fun notifyDataConnectionRealTimeInfo(p0: DataConnectionRealTimeInfo?) {
        TODO("Not yet implemented")
    }

    override fun notifyVoLteServiceStateChanged(p0: VoLteServiceState?) {
        TODO("Not yet implemented")
    }

    override fun notifyOemHookRawEventForSubscriber(p0: Int, p1: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun notifySubscriptionInfoChanged() {
        TODO("Not yet implemented")
    }

    override fun notifyCarrierNetworkChange(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun queryLocalInterface(descriptor: String?): IInterface {
        if (descriptor == "com.android.internal.telephony.ITelephonyRegistry") return this
        TODO("Not yet implemented")
    }
}