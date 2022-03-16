package jmp0.app.mock.system
import android.system.*
import android.util.MutableInt
import android.util.MutableLong
import jmp0.app.mock.annotations.ClassReplaceTo
import org.apache.log4j.Logger
import java.nio.NioUtils
import java.io.FileDescriptor
import java.io.InterruptedIOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.net.SocketException
import java.nio.ByteBuffer

@ClassReplaceTo("libcore.io.Posix")
class Posix internal constructor() : libcore.io.Os {
    companion object{

        const val xxClassName: String = "libcore.io.Posix"
        private val logger = Logger.getLogger(Posix::class.java)
        @JvmStatic
        private fun maybeUpdateBufferPosition(buffer: ByteBuffer, originalPosition: Int, bytesReadOrWritten: Int) {
            if (bytesReadOrWritten > 0) {
                buffer.position(bytesReadOrWritten + originalPosition)
            }
        }
    }

    external override fun accept(fd: FileDescriptor?, peerAddress: InetSocketAddress?): FileDescriptor?
    
    external override fun access(path: String?, mode: Int): Boolean
    
    external override fun android_getaddrinfo(node: String?, hints: StructAddrinfo?, netId: Int): Array<InetAddress?>?
    
    external override fun bind(fd: FileDescriptor?, address: InetAddress?, port: Int)
    
    external override fun bind(fd: FileDescriptor?, address: SocketAddress?)
    
    override fun chmod(path: String?, mode: Int){
        logger.debug("chmod called $path $mode")
        return
    }
    
    external override fun chown(path: String?, uid: Int, gid: Int)
    
    external override fun close(fd: FileDescriptor?)
    
    external override fun connect(fd: FileDescriptor?, address: InetAddress?, port: Int)
    
    external override fun connect(fd: FileDescriptor?, address: SocketAddress?)
    
    external override fun dup(oldFd: FileDescriptor?): FileDescriptor?
    
    external override fun dup2(oldFd: FileDescriptor?, newFd: Int): FileDescriptor?
    
    external override fun environ(): Array<String?>?
    
    external override fun execv(filename: String?, argv: Array<String?>?)
    
    external override fun execve(filename: String?, argv: Array<String?>?, envp: Array<String?>?)
    
    external override fun fchmod(fd: FileDescriptor?, mode: Int)
    
    external override fun fchown(fd: FileDescriptor?, uid: Int, gid: Int)
    
    external override fun fcntlFlock(fd: FileDescriptor?, cmd: Int, arg: StructFlock?): Int
    
    external override fun fcntlInt(fd: FileDescriptor?, cmd: Int, arg: Int): Int
    
    external override fun fcntlVoid(fd: FileDescriptor?, cmd: Int): Int
    
    external override fun fdatasync(fd: FileDescriptor?)
    
    external override fun fstat(fd: FileDescriptor?): StructStat?
    
    external override fun fstatvfs(fd: FileDescriptor?): StructStatVfs?

    external override fun fsync(fd: FileDescriptor?)
    
    external override fun ftruncate(fd: FileDescriptor?, length: Long)
    external override fun gai_strerror(error: Int): String?
    external override fun getegid(): Int
    external override fun geteuid(): Int
    external override fun getgid(): Int
    external override fun getenv(name: String?): String?

    external override fun getnameinfo(address: InetAddress?, flags: Int): String?
    
    external override fun getpeername(fd: FileDescriptor?): SocketAddress?
    external override fun getpgid(pid: Int): Int
    external override fun getpid(): Int
    external override fun getppid(): Int
    
    external override fun getpwnam(name: String?): StructPasswd?
    
    external override fun getpwuid(uid: Int): StructPasswd?
    
    external override fun getsockname(fd: FileDescriptor?): SocketAddress?
    
    external override fun getsockoptByte(fd: FileDescriptor?, level: Int, option: Int): Int
    
    external override fun getsockoptInAddr(fd: FileDescriptor?, level: Int, option: Int): InetAddress?
    
    external override fun getsockoptInt(fd: FileDescriptor?, level: Int, option: Int): Int
    
    external override fun getsockoptLinger(fd: FileDescriptor?, level: Int, option: Int): StructLinger?
    
    external override fun getsockoptTimeval(fd: FileDescriptor?, level: Int, option: Int): StructTimeval?
    
    external override fun getsockoptUcred(fd: FileDescriptor?, level: Int, option: Int): StructUcred?
    external override fun gettid(): Int

    
    override fun getuid(): Int{
        logger.warn("getuid() return 0")
        return 0
    }
    
    external override fun getxattr(path: String?, name: String?, outValue: ByteArray?): Int
    external override fun if_indextoname(index: Int): String?
    external override fun inet_pton(family: Int, address: String?): InetAddress?
    
    external override fun ioctlInetAddress(fd: FileDescriptor?, cmd: Int, interfaceName: String?): InetAddress?
    
    external override fun ioctlInt(fd: FileDescriptor?, cmd: Int, arg: MutableInt?): Int
    external override fun isatty(fd: FileDescriptor?): Boolean
    
    external override fun kill(pid: Int, signal: Int)
    
    external override fun lchown(path: String?, uid: Int, gid: Int)
    
    external override fun link(oldPath: String?, newPath: String?)
    
    external override fun listen(fd: FileDescriptor?, backlog: Int)
    
    external override fun lseek(fd: FileDescriptor?, offset: Long, whence: Int): Long
    
    external override fun lstat(path: String?): StructStat?
    
    external override fun mincore(address: Long, byteCount: Long, vector: ByteArray?)
    
    external override fun mkdir(path: String?, mode: Int)
    
    external override fun mkfifo(path: String?, mode: Int)
    
    external override fun mlock(address: Long, byteCount: Long)
    
    external override fun mmap(address: Long, byteCount: Long, prot: Int, flags: Int, fd: FileDescriptor?, offset: Long): Long
    
    external override fun msync(address: Long, byteCount: Long, flags: Int)
    
    external override fun munlock(address: Long, byteCount: Long)
    
    external override fun munmap(address: Long, byteCount: Long)
    
    external override fun open(path: String?, flags: Int, mode: Int): FileDescriptor?
    
    external override fun pipe2(flags: Int): Array<FileDescriptor?>?
    
    external override fun poll(fds: Array<StructPollfd?>?, timeoutMs: Int): Int
    
    external override fun posix_fallocate(fd: FileDescriptor?, offset: Long, length: Long)
    
    external override fun prctl(option: Int, arg2: Long, arg3: Long, arg4: Long, arg5: Long): Int
    @Throws(ErrnoException::class, InterruptedIOException::class)
    override fun pread(fd: FileDescriptor, buffer: ByteBuffer, offset: Long): Int {
        val bytesRead: Int
        val position = buffer.position()
        bytesRead = if (buffer.isDirect) {
            preadBytes(fd, buffer, position, buffer.remaining(), offset)
        } else {
            preadBytes(
                fd,
                NioUtils.unsafeArray(buffer),
                NioUtils.unsafeArrayOffset(buffer) + position,
                buffer.remaining(),
                offset
            )
        }
        maybeUpdateBufferPosition(buffer, position, bytesRead)
        return bytesRead
    }

    @Throws(ErrnoException::class, InterruptedIOException::class)
    override fun pread(fd: FileDescriptor, bytes: ByteArray, byteOffset: Int, byteCount: Int, offset: Long): Int {
        // This indirection isn't strictly necessary, but ensures that our public interface is type safe.
        return preadBytes(fd, bytes, byteOffset, byteCount, offset)
    }

    @Throws(ErrnoException::class, InterruptedIOException::class)
    external fun preadBytes(
        fd: FileDescriptor,
        buffer: Any,
        bufferOffset: Int,
        byteCount: Int,
        offset: Long
    ): Int


    override fun pwrite(fd: FileDescriptor, buffer: ByteBuffer, offset: Long): Int {
        val bytesWritten: Int
        val position = buffer.position()
        bytesWritten = if (buffer.isDirect) {
            pwriteBytes(fd, buffer, position, buffer.remaining(), offset)
        } else {
            pwriteBytes(
                fd,
                NioUtils.unsafeArray(buffer),
                NioUtils.unsafeArrayOffset(buffer) + position,
                buffer.remaining(),
                offset
            )
        }
        maybeUpdateBufferPosition(buffer, position, bytesWritten)
        return bytesWritten
    }


    override fun pwrite(fd: FileDescriptor, bytes: ByteArray, byteOffset: Int, byteCount: Int, offset: Long): Int {
        // This indirection isn't strictly necessary, but ensures that our public interface is type safe.
        return pwriteBytes(fd, bytes, byteOffset, byteCount, offset)
    }

    private external fun pwriteBytes(
        fd: FileDescriptor,
        buffer: Any,
        bufferOffset: Int,
        byteCount: Int,
        offset: Long
    ): Int


    override fun read(fd: FileDescriptor, buffer: ByteBuffer): Int {
        val bytesRead: Int
        val position = buffer.position()
        bytesRead = if (buffer.isDirect) {
            readBytes(fd, buffer, position, buffer.remaining())
        } else {
            readBytes(
                fd,
                NioUtils.unsafeArray(buffer),
                NioUtils.unsafeArrayOffset(buffer) + position,
                buffer.remaining()
            )
        }
        maybeUpdateBufferPosition(buffer, position, bytesRead)
        return bytesRead
    }

    @Throws(ErrnoException::class, InterruptedIOException::class)
    override fun read(fd: FileDescriptor, bytes: ByteArray, byteOffset: Int, byteCount: Int): Int {
        // This indirection isn't strictly necessary, but ensures that our public interface is type safe.
        return readBytes(fd, bytes, byteOffset, byteCount)
    }

    private external fun readBytes(fd: FileDescriptor, buffer: Any, offset: Int, byteCount: Int): Int
    
    external override fun readlink(path: String?): String?
    @Throws(ErrnoException::class, InterruptedIOException::class)
    external override fun readv(fd: FileDescriptor?, buffers: Array<Any?>?, offsets: IntArray?, byteCounts: IntArray?): Int
    @Throws(ErrnoException::class, SocketException::class)
    override fun recvfrom(fd: FileDescriptor, buffer: ByteBuffer, flags: Int, srcAddress: InetSocketAddress): Int {
        val bytesReceived: Int
        val position = buffer.position()
        bytesReceived = if (buffer.isDirect) {
            recvfromBytes(fd, buffer, position, buffer.remaining(), flags, srcAddress)
        } else {
            recvfromBytes(
                fd,
                NioUtils.unsafeArray(buffer),
                NioUtils.unsafeArrayOffset(buffer) + position,
                buffer.remaining(),
                flags,
                srcAddress
            )
        }
        maybeUpdateBufferPosition(buffer, position, bytesReceived)
        return bytesReceived
    }

    @Throws(ErrnoException::class, SocketException::class)
    override fun recvfrom(
        fd: FileDescriptor,
        bytes: ByteArray,
        byteOffset: Int,
        byteCount: Int,
        flags: Int,
        srcAddress: InetSocketAddress
    ): Int {
        // This indirection isn't strictly necessary, but ensures that our public interface is type safe.
        return recvfromBytes(fd, bytes, byteOffset, byteCount, flags, srcAddress)
    }

    @Throws(ErrnoException::class, SocketException::class)
    external fun recvfromBytes(
        fd: FileDescriptor,
        buffer: Any,
        byteOffset: Int,
        byteCount: Int,
        flags: Int,
        srcAddress: InetSocketAddress
    ): Int

    
    external override fun remove(path: String?)
    
    external override fun removexattr(path: String?, name: String?)
    
    external override fun rename(oldPath: String?, newPath: String?)
    
    external override fun sendfile(outFd: FileDescriptor?, inFd: FileDescriptor?, inOffset: MutableLong?, byteCount: Long): Long
    @Throws(ErrnoException::class, SocketException::class)
    override fun sendto(fd: FileDescriptor, buffer: ByteBuffer, flags: Int, inetAddress: InetAddress, port: Int): Int {
        val bytesSent: Int
        val position = buffer.position()
        bytesSent = if (buffer.isDirect) {
            sendtoBytes(fd, buffer, position, buffer.remaining(), flags, inetAddress, port)
        } else {
            sendtoBytes(
                fd,
                NioUtils.unsafeArray(buffer),
                NioUtils.unsafeArrayOffset(buffer) + position,
                buffer.remaining(),
                flags,
                inetAddress,
                port
            )
        }
        maybeUpdateBufferPosition(buffer, position, bytesSent)
        return bytesSent
    }

    @Throws(ErrnoException::class, SocketException::class)
    override fun sendto(
        fd: FileDescriptor,
        bytes: ByteArray,
        byteOffset: Int,
        byteCount: Int,
        flags: Int,
        inetAddress: InetAddress,
        port: Int
    ): Int {
        // This indirection isn't strictly necessary, but ensures that our public interface is type safe.
        return sendtoBytes(fd, bytes, byteOffset, byteCount, flags, inetAddress, port)
    }

    @Throws(ErrnoException::class, SocketException::class)
    override fun sendto(
        fd: FileDescriptor,
        bytes: ByteArray,
        byteOffset: Int,
        byteCount: Int,
        flags: Int,
        address: SocketAddress
    ): Int {
        return sendtoBytes(fd, bytes, byteOffset, byteCount, flags, address)
    }

    @Throws(ErrnoException::class, SocketException::class)
    external fun sendtoBytes(
        fd: FileDescriptor,
        buffer: Any,
        byteOffset: Int,
        byteCount: Int,
        flags: Int,
        inetAddress: InetAddress,
        port: Int
    ): Int

    @Throws(ErrnoException::class, SocketException::class)
    external fun sendtoBytes(
        fd: FileDescriptor,
        buffer: Any,
        byteOffset: Int,
        byteCount: Int,
        flags: Int,
        address: SocketAddress
    ): Int

    
    external override fun setegid(egid: Int)
    
    external override fun setenv(name: String?, value: String?, overwrite: Boolean)
    
    external override fun seteuid(euid: Int)
    
    external override fun setgid(gid: Int)
    
    external override fun setpgid(pid: Int, pgid: Int)
    
    external override fun setregid(rgid: Int, egid: Int)
    
    external override fun setreuid(ruid: Int, euid: Int)
    
    external override fun setsid(): Int
    
    external override fun setsockoptByte(fd: FileDescriptor?, level: Int, option: Int, value: Int)
    
    external override fun setsockoptIfreq(fd: FileDescriptor?, level: Int, option: Int, value: String?)
    
    external override fun setsockoptInt(fd: FileDescriptor?, level: Int, option: Int, value: Int)
    
    external override fun setsockoptIpMreqn(fd: FileDescriptor?, level: Int, option: Int, value: Int)
    
    external override fun setsockoptGroupReq(fd: FileDescriptor?, level: Int, option: Int, value: StructGroupReq?)
    
    external override fun setsockoptGroupSourceReq(fd: FileDescriptor?, level: Int, option: Int, value: StructGroupSourceReq?)
    
    external override fun setsockoptLinger(fd: FileDescriptor?, level: Int, option: Int, value: StructLinger?)
    
    external override fun setsockoptTimeval(fd: FileDescriptor?, level: Int, option: Int, value: StructTimeval?)
    
    external override fun setuid(uid: Int)
    
    external override fun setxattr(path: String?, name: String?, value: ByteArray?, flags: Int)
    
    external override fun shutdown(fd: FileDescriptor?, how: Int)
    
    external override fun socket(domain: Int, type: Int, protocol: Int): FileDescriptor?
    
    external override fun socketpair(domain: Int, type: Int, protocol: Int, fd1: FileDescriptor?, fd2: FileDescriptor?)
    
    external override fun stat(path: String?): StructStat?
    
    external override fun statvfs(path: String?): StructStatVfs?
    external override fun strerror(errno: Int): String?
    external override fun strsignal(signal: Int): String?
    
    external override fun symlink(oldPath: String?, newPath: String?)
    external override fun sysconf(name: Int): Long
    
    external override fun tcdrain(fd: FileDescriptor?)
    
    external override fun tcsendbreak(fd: FileDescriptor?, duration: Int)
    override fun umask(mask: Int): Int {
        require(mask and 511 == mask) { "Invalid umask: $mask" }
        return umaskImpl(mask)
    }

    private external fun umaskImpl(mask: Int): Int
    external override fun uname(): StructUtsname?
    
    external override fun unsetenv(name: String?)
    
    external override fun waitpid(pid: Int, status: MutableInt?, options: Int): Int
    @Throws(ErrnoException::class, InterruptedIOException::class)
    override fun write(fd: FileDescriptor, buffer: ByteBuffer): Int {
        val bytesWritten: Int
        val position = buffer.position()
        bytesWritten = if (buffer.isDirect) {
            writeBytes(fd, buffer, position, buffer.remaining())
        } else {
            writeBytes(
                fd,
                NioUtils.unsafeArray(buffer),
                NioUtils.unsafeArrayOffset(buffer) + position,
                buffer.remaining()
            )
        }
        maybeUpdateBufferPosition(buffer, position, bytesWritten)
        return bytesWritten
    }

    @Throws(ErrnoException::class, InterruptedIOException::class)
    override fun write(fd: FileDescriptor, bytes: ByteArray, byteOffset: Int, byteCount: Int): Int {
        // This indirection isn't strictly necessary, but ensures that our public interface is type safe.
        return writeBytes(fd, bytes, byteOffset, byteCount)
    }

    @Throws(ErrnoException::class, InterruptedIOException::class)
    external fun writeBytes(fd: FileDescriptor, buffer: Any, offset: Int, byteCount: Int): Int
    @Throws(ErrnoException::class, InterruptedIOException::class)
    external override fun writev(fd: FileDescriptor?, buffers: Array<Any?>?, offsets: IntArray?, byteCounts: IntArray?): Int

}