package jmp0.app.mock.system.service

import android.content.Context
import android.telephony.SubscriptionManager
import jmp0.app.mock.annotations.ClassReplaceTo

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/5/6
 */
@ClassReplaceTo("")
class MockSubscriptionManager(context: Context):SubscriptionManager(context) {
}