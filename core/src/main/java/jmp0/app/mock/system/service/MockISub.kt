package jmp0.app.mock.system.service

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.telephony.SubscriptionInfo
import com.android.internal.telephony.ISub
import jmp0.app.mock.annotations.ClassReplaceTo

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/6
 */
@ClassReplaceTo("")
class MockISub:ISub,Binder() {

    override fun queryLocalInterface(descriptor: String?): IInterface {
        if (descriptor == "com.android.internal.telephony.ISub") return this
        TODO("Not yet implemented")
    }

    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun getAllSubInfoList(p0: String?): MutableList<SubscriptionInfo> {
        TODO("Not yet implemented")
    }

    override fun getAllSubInfoCount(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun getActiveSubscriptionInfo(p0: Int, p1: String?): SubscriptionInfo {
        TODO("Not yet implemented")
    }

    override fun getActiveSubscriptionInfoForIccId(p0: String?, p1: String?): SubscriptionInfo {
        TODO("Not yet implemented")
    }

    override fun getActiveSubscriptionInfoForSimSlotIndex(p0: Int, p1: String?): SubscriptionInfo {
        TODO("Not yet implemented")
    }

    override fun getActiveSubscriptionInfoList(p0: String?): MutableList<SubscriptionInfo> {
        TODO("Not yet implemented")
    }

    override fun getActiveSubInfoCount(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun getActiveSubInfoCountMax(): Int {
        TODO("Not yet implemented")
    }

    override fun addSubInfoRecord(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun setIconTint(p0: Int, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun setDisplayName(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun setDisplayNameUsingSrc(p0: String?, p1: Int, p2: Long): Int {
        TODO("Not yet implemented")
    }

    override fun setDisplayNumber(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun setDataRoaming(p0: Int, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getSlotId(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getSubId(p0: Int): IntArray {
        return intArrayOf(12,32,33,55)
    }

    override fun getDefaultSubId(): Int {
        TODO("Not yet implemented")
    }

    override fun clearSubInfo(): Int {
        TODO("Not yet implemented")
    }

    override fun getPhoneId(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getDefaultDataSubId(): Int {
        TODO("Not yet implemented")
    }

    override fun setDefaultDataSubId(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getDefaultVoiceSubId(): Int {
        TODO("Not yet implemented")
    }

    override fun setDefaultVoiceSubId(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getDefaultSmsSubId(): Int {
        TODO("Not yet implemented")
    }

    override fun setDefaultSmsSubId(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun clearDefaultsForInactiveSubIds() {
        TODO("Not yet implemented")
    }

    override fun getActiveSubIdList(): IntArray {
        TODO("Not yet implemented")
    }

    override fun setSubscriptionProperty(p0: Int, p1: String?, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun getSubscriptionProperty(p0: Int, p1: String?, p2: String?): String {
        TODO("Not yet implemented")
    }

    override fun getSimStateForSlotIdx(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun isActiveSubId(p0: Int): Boolean {
        TODO("Not yet implemented")
    }
}