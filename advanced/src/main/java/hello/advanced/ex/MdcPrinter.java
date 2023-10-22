package hello.advanced.ex;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MdcPrinter {

    public static void printAllMDC(String message) {
        log.info("====={}=====", message);
        var mdc = MDC.getCopyOfContextMap();
        if (mdc == null) {
            log.info("MDC is null");
        } else {
            mdc.forEach((key, value) -> log.info("{}: {}", key, value));
        }
        log.info("=====================================");
    }
}
