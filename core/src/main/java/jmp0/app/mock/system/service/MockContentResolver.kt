package jmp0.app.mock.system.service

import android.content.ContentResolver
import android.content.Context
import android.content.IContentProvider
import jmp0.app.mock.annotations.ClassReplaceTo
import jmp0.app.mock.system.contentprovider.SettingsContentProvider

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/22
 */
@ClassReplaceTo("")
class MockContentResolver(context: Context):ContentResolver(context) {
    override fun acquireProvider(p0: Context?, p1: String?): IContentProvider {
        if (p1 == "settings"){
            return SettingsContentProvider()
        }
        TODO("Not yet implemented")
    }

    override fun releaseProvider(p0: IContentProvider?): Boolean {
        TODO("Not yet implemented")
    }

    override fun acquireUnstableProvider(p0: Context?, p1: String?): IContentProvider {
        TODO("Not yet implemented")
    }

    override fun releaseUnstableProvider(p0: IContentProvider?): Boolean {
        TODO("Not yet implemented")
    }

    override fun unstableProviderDied(p0: IContentProvider?) {
        TODO("Not yet implemented")
    }
}