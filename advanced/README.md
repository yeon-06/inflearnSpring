# 스프링 핵심 원리 - 고급편

## section 1 - 예제 만들기

- [v0. 기본 예제](./src/main/java/hello/advanced/v0)
- [v1. 로그 추적기 - 프로토타입 개발](./src/main/java/hello/advanced/v1)
- [v2. 로그 추적기 - 파라미터로 동기화 개발](./src/main/java/hello/advanced/v2)

### 로그추적기 요구사항

- 모든 public 메서드의 request, response를 로그로 출력
- 비즈니스 로직의 동작에 영향 X
- 메서드 호출에 걸린 시간 출력
- 정상 흐름 또는 예외 흐름을 구분하여 출력
    - 예외 흐름인 경우, 예외 정보가 남아야 함
- 메서드 호출의 뎁스 출력
- HTTP 요청을 구분하여 출력
    - HTTP 요청 단위로 특정 id를 남겨 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함
    - 트랜잭션 ID 출력 (HTTP 요청이 시작해서 끝날 때까지를 하나의 트랜잭션이라고 표현)

## section 2 - 쓰레드 로컬

- [v3. 로그 추적기 - 필드 동기화 방식 개발](./src/main/java/hello/advanced/v3)
- [v4. 로그 추적기 - 쓰레드 로컬 이용하기](./src/main/java/hello/advanced/v4)

### 쓰레드 로컬

- 각 thread마다 별도의 내부 저장소를 제공해주는 기능
- java에서 `java.lang.ThreadLocal` 지원

주의할 점
- WAS는 스레드를 재사용한다.
- 이전 스레드에서 ThreadLocal 데이터가 제거를 안했다면, 재사용될 때 해당 데이터가 남아있을 수 있다.
