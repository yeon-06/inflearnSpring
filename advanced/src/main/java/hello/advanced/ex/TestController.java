package hello.advanced.ex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService service;

    @GetMapping("/test/async")
    public String withAsync() {
        MdcPrinter.printAllMDC("TestController#withAsync");
        service.async();
        return "ok";
    }

    @GetMapping("/test/sync")
    public String withSync() {
        MdcPrinter.printAllMDC("TestController#withSync");
        service.sync();
        return "ok";
    }
}
