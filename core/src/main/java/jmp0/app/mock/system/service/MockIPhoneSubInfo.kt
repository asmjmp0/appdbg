package jmp0.app.mock.system.service

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import com.android.internal.telephony.IPhoneSubInfo
import jmp0.app.mock.annotations.ClassReplaceTo

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/6
 */
@ClassReplaceTo("")
class MockIPhoneSubInfo:IPhoneSubInfo,Binder() {
    override fun queryLocalInterface(descriptor: String?): IInterface {
        if(descriptor == "com.android.internal.telephony.IPhoneSubInfo") return this
        TODO("Not yet implemented")
    }
    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun getDeviceId(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getNaiForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getDeviceIdForPhone(p0: Int): String {
        TODO("Not yet implemented")
    }

    override fun getImeiForSubscriber(p0: Int, p1: String?): String {
        if (p0 == 12){
            return "721837785467868721321"
        }
        TODO("Not yet implemented")
    }

    override fun getDeviceSvn(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getDeviceSvnUsingSubId(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getSubscriberId(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getSubscriberIdForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getGroupIdLevel1(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getGroupIdLevel1ForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getIccSerialNumber(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getIccSerialNumberForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getLine1Number(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getLine1NumberForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getLine1AlphaTag(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getLine1AlphaTagForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getMsisdn(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getMsisdnForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getVoiceMailNumber(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getVoiceMailNumberForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getCompleteVoiceMailNumber(): String {
        TODO("Not yet implemented")
    }

    override fun getCompleteVoiceMailNumberForSubscriber(p0: Int): String {
        TODO("Not yet implemented")
    }

    override fun getVoiceMailAlphaTag(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getVoiceMailAlphaTagForSubscriber(p0: Int, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun getIsimImpi(): String {
        TODO("Not yet implemented")
    }

    override fun getIsimDomain(): String {
        TODO("Not yet implemented")
    }

    override fun getIsimImpu(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getIsimIst(): String {
        TODO("Not yet implemented")
    }

    override fun getIsimPcscf(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getIsimChallengeResponse(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun getIccSimChallengeResponse(p0: Int, p1: Int, p2: String?): String {
        TODO("Not yet implemented")
    }
}