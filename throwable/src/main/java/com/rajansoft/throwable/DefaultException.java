package com.rajansoft.throwable;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class DefaultException extends ThrowableException {

    @Serial
    private static final long serialVersionUID = -4297525937869528758L;

    private final String error;
    private final String path;
    private final Integer code;
    private final LocalDateTime timestamp;
    private final transient Map<String, Object> parameters;

    public DefaultException(ExceptionBuilder builder) {
        super(builder.message, builder.traceId, builder.cause);
        this.error = builder.error;
        this.path = builder.path;
        this.code = builder.code;
        this.timestamp = builder.timestamp;
        this.parameters = Optional.ofNullable(builder.parameters).orElseGet(HashMap::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DefaultException that = (DefaultException) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(error, that.error)
            .append(path, that.path)
            .append(code, that.code)
            .append(timestamp, that.timestamp)
            .append(parameters, that.parameters)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(error)
            .append(path)
            .append(code)
            .append(timestamp)
            .append(parameters)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .appendSuper(super.toString())
            .append("error", error)
            .append("path", path)
            .append("code", code)
            .append("timestamp", timestamp)
            .append("parameters", parameters)
            .toString();
    }

}
