package hello.advanced.ex;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Async
    public void async() {
        MdcPrinter.printAllMDC("TestService#async");
    }

    public void sync() {
        MdcPrinter.printAllMDC("TestService#sync");
    }
}
