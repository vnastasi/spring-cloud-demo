package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.exception.ApiErrorType;
import md.vnastasi.cloud.exception.ApiException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.codec.DecodingException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.IOException;
import java.util.Map;

@Component
public class ApiErrorAttributes extends DefaultErrorAttributes {

    private static final String KEY_REASON = "reason";
    private static final String KEY_MESSAGE = "message";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
        var errorAttributes = super.getErrorAttributes(request, options);
        var throwable = getError(request);

        if (throwable instanceof ApiException) {
            errorAttributes.put(KEY_REASON, ApiErrorType.NS_SERVICE_FAILURE);
            errorAttributes.put(KEY_MESSAGE, "There was a problem contacting NS API portal");
        } else if (throwable instanceof DecodingException) {
            errorAttributes.put(KEY_REASON, ApiErrorType.UNPARSABLE_RESPONSE);
            errorAttributes.put(KEY_MESSAGE, throwable.getMessage());
        } else if (throwable instanceof IOException) {
            errorAttributes.put(KEY_REASON, ApiErrorType.NS_SERVICE_FAILURE);
            errorAttributes.put(KEY_MESSAGE, throwable.getMessage());
        } else {
            errorAttributes.put(KEY_REASON, ApiErrorType.UNKNOWN_FAILURE);
            errorAttributes.put(KEY_MESSAGE, throwable.getMessage());
        }

        return errorAttributes;
    }
}
