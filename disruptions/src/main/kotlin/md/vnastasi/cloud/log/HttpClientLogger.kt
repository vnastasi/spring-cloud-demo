package md.vnastasi.cloud.log

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.logging.LoggingHandler
import io.netty.util.internal.PlatformDependent.allocateUninitializedArray
import java.nio.charset.Charset
import kotlin.math.max
import kotlin.reflect.KClass

class HttpClientLogger(clazz: KClass<*>) : LoggingHandler(clazz.java) {

    override fun format(ctx: ChannelHandlerContext?, eventName: String?, arg: Any?): String {
        if (arg is ByteBuf) {
            return decode(arg, arg.readerIndex(), arg.readableBytes(), Charset.defaultCharset())
        }
        return super.format(ctx, eventName, arg)
    }

    private fun decode(source: ByteBuf, readerIndex: Int, length: Int, charset: Charset): String {
        if (length == 0) return ""

        val array: ByteArray
        val offset: Int

        if (source.hasArray()) {
            array = source.array()
            offset = source.arrayOffset() + readerIndex
        } else {
            array = allocateUninitializedArray(max(length, 1024))
            offset = 0
            source.getBytes(readerIndex, array, 0, length)
        }

        return String(array, offset, length, charset)
    }
}
