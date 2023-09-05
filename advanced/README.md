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

## section 3 - 템플릿 메서드 패턴과 콜백 패턴

- 토비의 스프링에서 공부한 내용이라 예제는 생략한다. ([참고 링크](https://github.com/yeon-06/toby-spring/pull/5))

### 템플릿 메서드 패턴

- 목적: 작업에서 알고리즘의 골격을 정의하고 일부 단계를 하위 클래스로 연기한다. 템플릿 메서드를 사용하면 하위 클래스가 알고리즘의 구조를 변경하지 않고도 알고리즘의 특정 단계를 재정의할 수 있다.
- 정리: 부모 클래스에 골격인 템플릿을 정의하고, 로직은 자식 클래스에 정의하는 것. 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것.
- 문제점: 상속의 문제점과 동일 ([Inheritance(상속) vs Composition(조합)](https://yeonyeon.tistory.com/206))

