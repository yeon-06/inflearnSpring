package hello.advanced.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String message) {
        return logBeginTraceId(new TraceId(), message);
    }

    public TraceStatus begin(TraceId beforeTraceId, String message) {
        return logBeginTraceId(beforeTraceId.createNextId(), message);
    }

    private TraceStatus logBeginTraceId(TraceId traceId, String message) {
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    public void end(TraceStatus status) {
        TraceId traceId = status.traceId();
        log.info("[{}] {}{} time={}ms",
            traceId.getId(),
            addSpace(COMPLETE_PREFIX, traceId.getLevel()),
            status.message(),
            System.currentTimeMillis() - status.startTimeMs()
        );
    }

    public void exception(TraceStatus status, Exception e) {
        TraceId traceId = status.traceId();
        log.info("[{}] {}{} time={}ms ex={}",
            traceId.getId(),
            addSpace(EX_PREFIX, traceId.getLevel()),
            status.message(),
            System.currentTimeMillis() - status.startTimeMs(),
            e.toString()
        );
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("|   ".repeat(Math.max(1, level) - 1));
        if (level != 0) {
            sb.append("|")
                .append(prefix);
        }
        return sb.toString();
    }
}
