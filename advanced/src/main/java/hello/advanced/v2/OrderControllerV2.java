package hello.advanced.v2;

import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/* 로그추적기 v2 요구사항 충족 체크
- [x] 모든 public 메서드의 request, response를 로그로 출력
- [x] 비즈니스 로직의 동작에 영향 X
- [x] 메서드 호출에 걸린 시간 출력
- [x] 정상 흐름 또는 예외 흐름을 구분하여 출력
- [x] 메서드 호출의 뎁스 출력
- [x] HTTP 요청을 구분하여 출력
*/
@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String orderItem(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin("OrderControllerV2.orderItem()");
            orderServiceV2.orderItem(itemId, traceStatus.traceId());
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
        return "ok";
    }
}
