package jmp0.app.mock.system.contentprovider

import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.ContentValues
import android.content.IContentProvider
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.ICancellationSignal
import android.os.ParcelFileDescriptor
import jmp0.app.mock.annotations.ClassReplaceTo
import java.util.ArrayList

/**
 * @author jmp0 <jmp0@qq.com>
 * Create on 2022/3/22
 */
@ClassReplaceTo("")
class SettingsContentProvider:IContentProvider {
    override fun asBinder(): IBinder {
        TODO("Not yet implemented")
    }

    override fun query(
        p0: String?,
        p1: Uri?,
        p2: Array<out String>?,
        p3: String?,
        p4: Array<out String>?,
        p5: String?,
        p6: ICancellationSignal?
    ): Cursor {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri?): String {
        TODO("Not yet implemented")
    }

    override fun insert(p0: String?, p1: Uri?, p2: ContentValues?): Uri {
        TODO("Not yet implemented")
    }

    override fun bulkInsert(p0: String?, p1: Uri?, p2: Array<out ContentValues>?): Int {
        TODO("Not yet implemented")
    }

    override fun delete(p0: String?, p1: Uri?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: String?, p1: Uri?, p2: ContentValues?, p3: String?, p4: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun openFile(
        p0: String?,
        p1: Uri?,
        p2: String?,
        p3: ICancellationSignal?,
        p4: IBinder?
    ): ParcelFileDescriptor {
        TODO("Not yet implemented")
    }

    override fun openAssetFile(p0: String?, p1: Uri?, p2: String?, p3: ICancellationSignal?): AssetFileDescriptor {
        TODO("Not yet implemented")
    }

    override fun applyBatch(p0: String?, p1: ArrayList<ContentProviderOperation>?): Array<ContentProviderResult> {
        TODO("Not yet implemented")
    }

    override fun call(p0: String?, p1: String?, p2: String?, p3: Bundle?): Bundle {
        if ((p1 == "GET_secure") and (p2 == "android_id"))
            return Bundle.forPair(p2,"00954ad33eb077a8")
        TODO("Not yet implemented")
    }

    override fun createCancellationSignal(): ICancellationSignal {
        TODO("Not yet implemented")
    }

    override fun canonicalize(p0: String?, p1: Uri?): Uri {
        TODO("Not yet implemented")
    }

    override fun uncanonicalize(p0: String?, p1: Uri?): Uri {
        TODO("Not yet implemented")
    }

    override fun getStreamTypes(p0: Uri?, p1: String?): Array<String> {
        TODO("Not yet implemented")
    }

    override fun openTypedAssetFile(
        p0: String?,
        p1: Uri?,
        p2: String?,
        p3: Bundle?,
        p4: ICancellationSignal?
    ): AssetFileDescriptor {
        TODO("Not yet implemented")
    }
}