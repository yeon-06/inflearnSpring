package hello.advanced.trace;

import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TraceId {

    private static final int DEFAULT_LEVEL = 0;

    private final String id;
    private final int level;

    public TraceId() {
        this.id = createId();
        this.level = DEFAULT_LEVEL;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId createNextId() {
        return new TraceId(this.id, this.level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(this.id, this.level - 1);
    }

    public boolean isFirstLevel() {
        return level == DEFAULT_LEVEL;
    }
}
