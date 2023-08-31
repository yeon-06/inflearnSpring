package hello.advanced.v4;

import hello.advanced.trace.TraceId;

public class TraceIdHolder {

    private final ThreadLocal<TraceId> threadLocal = new ThreadLocal<>();

    public TraceId getTraceId() {
        TraceId traceId = threadLocal.get();
        if (traceId == null) {
            threadLocal.set(new TraceId());
        } else {
            threadLocal.set(traceId.createNextId());
        }
        return threadLocal.get();
    }

    public void release() {
        TraceId traceId = threadLocal.get();

        if (traceId.isFirstLevel()) {
            threadLocal.remove();
            return;
        }

        threadLocal.set(traceId.createPreviousId());
    }
}
