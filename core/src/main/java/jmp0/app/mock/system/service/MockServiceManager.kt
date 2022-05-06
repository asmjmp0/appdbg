package jmp0.app.mock.system.service

import android.os.IBinder
import android.os.IPermissionController
import jmp0.app.mock.annotations.ClassReplaceTo


@ClassReplaceTo("")
class MockServiceManager:android.os.IServiceManager {
    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun getService(name: String?): IBinder? {
        if (name == "activity") return MockActivityManager()
        if (name == "package") return MockPackageManager()
        if (name == "display") return null
        if (name == "connectivity") return null
        if (name == "telephony.registry") return MockTelephonyRegistry()
        if (name == "isub") return MockISub()
        if (name == "iphonesubinfo") return MockIPhoneSubInfo()
        TODO("Not yet implemented")
    }

    override fun checkService(name: String?): IBinder {
        TODO("Not yet implemented")
    }

    override fun addService(name: String?, service: IBinder?, allowIsolated: Boolean) {
        TODO("Not yet implemented")
    }

    override fun listServices(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun setPermissionController(controller: IPermissionController?) {
        TODO("Not yet implemented")
    }

}