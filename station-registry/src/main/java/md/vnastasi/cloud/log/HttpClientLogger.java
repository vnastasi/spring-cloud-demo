package md.vnastasi.cloud.log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

import static io.netty.util.internal.PlatformDependent.allocateUninitializedArray;
import static java.lang.Integer.max;

public class HttpClientLogger extends LoggingHandler {

    public HttpClientLogger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if (arg instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) arg;
            return decode(msg, msg.readerIndex(), msg.readableBytes(), Charset.defaultCharset());
        }
        return super.format(ctx, eventName, arg);
    }

    private String decode(ByteBuf src, int readerIndex, int len, Charset charset) {
        if (len == 0) return "";

        byte[] array;
        int offset;
        if (src.hasArray()) {
            array = src.array();
            offset = src.arrayOffset() + readerIndex;
        } else {
            array = allocateUninitializedArray(max(len, 1024));
            offset = 0;
            src.getBytes(readerIndex, array, 0, len);
        }
        return new String(array, offset, len, charset);
    }
}
