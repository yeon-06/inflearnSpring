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

<br/>

## section 2 - 쓰레드 로컬

- [v3. 로그 추적기 - 필드 동기화 방식 개발](./src/main/java/hello/advanced/v3)
- [v4. 로그 추적기 - 쓰레드 로컬 이용하기](./src/main/java/hello/advanced/v4)

### 쓰레드 로컬

- 각 thread마다 별도의 내부 저장소를 제공해주는 기능
- java에서 `java.lang.ThreadLocal` 지원

주의할 점

- WAS는 스레드를 재사용한다.
- 이전 스레드에서 ThreadLocal 데이터가 제거를 안했다면, 재사용될 때 해당 데이터가 남아있을 수 있다.

<br/>

## section 3 - 템플릿 메서드 패턴과 콜백 패턴

- 토비의 스프링에서 공부한 내용과 일치. ([참고 링크](https://github.com/yeon-06/toby-spring/pull/5))

### 템플릿 메서드 패턴

- 목적: 작업에서 알고리즘의 골격을 정의하고 일부 단계를 하위 클래스로 연기한다. 템플릿 메서드를 사용하면 하위 클래스가 알고리즘의 구조를 변경하지 않고도 알고리즘의 특정 단계를 재정의할 수 있다.
- 정리: 부모 클래스에 골격인 템플릿을 정의하고, 로직은 자식 클래스에 정의하는 것. 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것.
- 문제점: 상속의 문제점과 동일 ([Inheritance(상속) vs Composition(조합)](https://yeonyeon.tistory.com/206))

### 템플릿 콜백 패턴

- [v5. 로그 추적기 - 템플릿 콜백 패턴](./src/main/java/hello/advanced/v5)
- 상속이 아닌 interface 이용

<br/>

## section 4 - 프록시 패턴과 데코레이터 패턴

- 토비의 스프링에서 공부한 내용과 일치. ([참고 링크](https://github.com/yeon-06/toby-spring/pull/8))

### Spring bean 등록 방법

1. @Bean 이용
2. @Component 이용

### 로그 추적기의 추가 요구사항

- **원본 코드를 수정하지 않는** 로그 추적기 적용
- 보안상 특정 메서드는 로그를 출력하지 않게 만들기
- 다양한 케이스에 적용할 수 있어야 함
    - 인터페이스가 있는 구현 클래스에 적용
    - 인터페이스가 없는 구현 클래스에 적용
    - 컴포넌트 스캔 대상에 기능 적용

### 프록시 패턴과 데코레이터 패턴

- 모두 프록시를 사용하지만, 의도가 다르다.
- 프록시 패턴: 접근 제어가 목적
- 데코레이터 패턴: 새로운 기능 추가가 목적

### 인터페이스 기반 프록시와 클래스 기반 프록시

- 클래스 기반 프록시
    - 특정 클래스에만 적용 가능
    - 상속을 이용하기 때문에 몇가지 제약이 있음 (부모 클래스의 생성자 호출, final 클래스는 프록시 적용 불가 등)
- 인터페이스 기반 프록시
    - 인터페이스가 같은 모든 클래스에 적용 가능
    - 인터페이스가 있어야 적용 가능

<br/>

## section 5 - 동적 프록시 기술

- section 4에서 원본 코드를 변경하지 않고도 부가 기능을 적용할 수 있었다.
- 하지만 프록시 클래스가 너무 많이 발생하는 문제가 발생하였으며, section 5에서는 이 문제를 해결할 수 있다.

### 리플렉션; Reflection

- 클래스나 메서드의 메타정보를 사용해 애플리케이션을 유연하게 다룰 수 있음
- 오류를 컴파일 시점에 알 수 없음 (런타임에 오류 발생)
- 프레임워크 개발이나 매우 일반적인 공통 처리가 필요할 때 부분적으로 사용해야 함

### 프록시

> 관련 포스팅: [JDK Dynamic Proxy vs CGLIB Proxy](https://yeonyeon.tistory.com/289)

<br/>

## section 6 - 스프링이 지원하는 프록시

- 포인트컷: 어디에 부가 기능을 적용할 것인가
- 어드바이스: 프록시가 호출하는 부가 기능
- 어드바이저: 포인트컷 1 + 어드바이스 1

### AOP와 어드바이저

- AOP 적용 수만큼 프록시가 생성되지 않는다.
- 타깃 하나에 여러 AOP가 동시에 적용되어도, target 하나에 proxy 하나만 생성한다. (여러개의 advisor를 적용할 수 있으므로)

<br/>

## section 7 - 빈 후처리기

예제 코드

- [BeanPostProcessor 기본 예제](./src/test/java/hello/advanced/bean)
- [BeanPostProcessor 적용 예제](./src/main/java/hello/advanced/bean/LogTraceBeanPostProcessor.java)
- [스프링이 제공하는 빈 후처리기 예제](./src/main/java/hello/advanced/bean)

빈 후처리기

- 빈 생성 후에 무언가를 처리하는 용도로 사용
- `BeanPostProcessor`의 문제점: 설정할 것이 많아 귀찮음 -> 스프링이 지원해주는 빈 후처리기가 존재함.

Spring과 AspectJ

- Spring Boot 없이 Spring을 사용한다면 `@EnableAspectJAutoProxy` 필요
- `AnnotationAwareAspectJAutoProxyCreator`
    - Spring Boot가 자동 설정으로 스프링 빈에 자동으로 등록 (`AopAutoConfiguration` 참고)
    - :프록시를 자동으로 생성해주는 빈 후처리기
    - bean으로 등록된 Advisor 찾고 프록시가 필요한 곳에 자동으로 프록시 적용
    - `@AspectJ`와 관련된 AOP 기능 자동적으로 처리
- 포인트컷의 사용
    - 생성 단계: 프록시 적용 여부 판단
    - 사용 단계: 어드바이스 적용 여부 판단

<br/>

## section 8 - @Aspect

예제 코드

- [적용 예제](./src/main/java/hello/advanced/aspect)

정리

- @Aspect
    - AspectJ가 지원하는 어노테이션
    - Spring이 해당 기능을 이용해 AOP를 편리하게 지원해주고 있음
    - BeanFactoryAspectJAdvisorBuilder가 @Aspect 정보 기반으로 포인트컷, 어드바이스, 어드바이저를 생성하고 보관하는 일을 담당
- Advisor 기반으로 프록시 생성
    - bean 등록
    - 자동 프록시 생성기에 bean이 넌ㅁ어감
    - Advisor 모두 조회
    - Pointcut을 보고 프록시 생성 대상인지 점검
    - 조건에 해당되면 프록시 생성하여 반환

## section 9 - 스프링 AOP 개념

> 관련 포스팅: [Spring의 트랜잭션 관리](https://yeonyeon.tistory.com/223)

- 애플리케이션 기능은 '핵심 기능'과 '부가 기능'으로 구성
- 반복적으로 사용되거나 책임을 분리하기 위해 등의 이유로 '부가 기능'을 분리하고, 어디에 적용할지 선택하는 기능을 하나의 모듈로 만든 것이 Aspect

AOP를 사용하여 부가 기능 로직이 실제 로직에 추가되는 방법

- 위빙; Weaving: 원본 로직에 부가 기능 로직이 추가되는 것
1. 컴파일 시점
    - AspectJ 컴파일러를 이용해 해당 클래스가 적용 대상인지 확인 후 부가 기능 로직 적용.
2. 클래스 로딩 시점
    - 자바는 .class 파일을 JVM 내부의 클래스 로더에 보관. 이 시점을 이용
    - 해당 시점에 .class에 조작 (= 로드 타임 위빙)
    - java -javaagent를 통해 클래스 로더 조작기 지정
3. 런타임 시점 (프록시)
    - 여태 강의를 들어왔던 내용이라 생략. 해당 방법이 가장 간단

용어 정리
- Join Point: 부가 기능을 적용 가능한 모든 지점
- Pointcut: 부가 기능을 적용할 지점을 선별하는 기능
- Target: 부가 기능을 적용할 대상. 어드바이스를 받는 객체.
- Aspect: 부가 기능 (Advice) + 적용 대상 (Pointcut)을 모듈화 한 것
- Advisor: Pointcut + Advice. (Spring AOP에서만 사용되는 용어)
- Weaving: 부가 기능을 적용하는 행위

## section 13 - 스프링 AOP - 실무 주의사항

- 자기자신을 호출하면 동작 X. (참고 글: [@Transactional이 동작하지 않는다?](https://yeonyeon.tistory.com/283))
- 타입 캐스팅이 불편한 케이스가 생김 (interface를 이용해 프록시를 생성한 경우, abstract class로 타입캐스팅 불가능)
