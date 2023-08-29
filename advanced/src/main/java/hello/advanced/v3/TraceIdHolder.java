package hello.advanced.v3;

import hello.advanced.trace.TraceId;

public class TraceIdHolder {

    private TraceId traceId;

    public TraceId getTraceId() {
        if (traceId == null) {
            traceId = new TraceId();
        } else {
            traceId = traceId.createNextId();
        }
        return traceId;
    }

    public void release() {
        if(traceId.isFirstLevel()) {
            traceId = null;
            return;
        }
        traceId = traceId.createPreviousId();
    }
}
