package hello.advanced.v5;

import hello.advanced.trace.LogTrace;
import hello.advanced.trace.TraceStatus;
import hello.advanced.v4.FieldLogTraceV4;
import org.springframework.stereotype.Component;

@Component
public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(FieldLogTraceV4 trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin(message);
            T result = callback.execute();
            trace.end(traceStatus);
            return result;
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
    }
}
