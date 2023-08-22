package hello.advanced.trace;

public record TraceStatus(TraceId traceId, Long startTimeMs, String message) {

}
