package com.rajansoft.throwable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.util.Objects;

public abstract class ThrowableException extends RuntimeException implements Exception {

    @Serial
    private static final long serialVersionUID = -5603693230819391139L;

    private final String traceId;

    protected ThrowableException(String message, String traceId) {
        super(message);
        this.traceId = traceId;
    }

    protected ThrowableException(String message, String traceId, ThrowableException cause) {
        super(message, cause);
        this.traceId = traceId;

        setStackTrace(getStackTrace().clone());
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    @Override
    public synchronized ThrowableException getCause() {
        return (ThrowableException) super.getCause();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ThrowableException that = (ThrowableException) o;

        return new EqualsBuilder().append(traceId, that.traceId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(traceId).toHashCode();
    }

    @Override
    public String toString() {
        String cause = Objects.isNull(getCause()) ? "null" : getCause().toString();

        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("message", getMessage())
            .append("traceId", traceId)
            .append("cause", cause)
            .toString();
    }

}
