# Spring MVC 프로젝트 구조

## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [프로젝트 구조](#프로젝트-구조)
3. [주요 설정 파일](#주요-설정-파일)
4. [프로파일 설정](#프로파일-설정)
5. [테스트 설정](#테스트-설정)
6. [빌드 및 배포](#빌드-및-배포)

---

## 프로젝트 개요
- Spring MVC 기반 웹 애플리케이션
- XML 기반 설정
- Spring Boot 사용하지 않음
- MyBatis를 사용한 데이터베이스 연동
- 환경별 설정 (local, dev, prod) 지원

---

## 프로젝트 구조
```plaintext
spring-demo/
├── demo/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/cmsoft/
│   │   │   ├── resources/
│   │   │   │   ├── config/
│   │   │   │   │   ├── mybatis/
│   │   │   │   │   ├── properties/
│   │   │   │   │   └── spring/
│   │   │   │   ├── sql/sqlmaps/
│   │   │   └── webapp/
│   │   │       ├── static/
│   │   │       └── WEB-INF/
│   │   │           ├── web.xml
│   │   │           └── views/
│   │   └── test/
│   │       ├── java/com/cmsoft/
```

---

## 주요 설정 파일

### 1. 웹 애플리케이션 설정
- **`web.xml`**: 웹 애플리케이션 기본 설정
  - 프로파일 설정
  - DispatcherServlet 설정
  - ContextLoaderListener 설정

### 2. Spring 설정
- **`dispatcher-servlet.xml`**: 웹 애플리케이션 컨텍스트 설정
  - 컨트롤러 스캔
  - 정적 리소스 매핑
  - 뷰 리졸버 설정
- **`context-*.xml`**: 루트 컨텍스트 설정
  - `context-common.xml`: 공통 설정
  - `context-local-common.xml`: 로컬 환경 설정
  - `context-datasource.xml`: 데이터소스 설정
  - `context-transaction.xml`: 트랜잭션 설정

### 3. 프로파일별 설정
- **`global-*.properties`**: 프로파일별 프로퍼티 파일
  - `global-local.properties`: 로컬 환경 프로퍼티
  - `global-dev.properties`: 개발 환경 프로퍼티
  - `global-prod.properties`: 운영 환경 프로퍼티

---

## 프로파일 설정
- `web.xml`의 `context-param`으로 설정
- 기본값: `local`
- JVM 옵션을 사용하여 프로파일 변경 가능:
  ```bash
  java -Dspring.profiles.active=dev -jar demo.war
  ```

### 프로파일 적용 방법
1. **로컬 환경 (기본값)**  
   - `web.xml`의 `spring.profiles.active` 값이 `local`로 설정
   - `context-local-common.xml`과 `global-local.properties` 적용

2. **개발 환경**  
   - JVM 옵션: `-Dspring.profiles.active=dev`
   - `global-dev.properties` 적용

3. **운영 환경**  
   - JVM 옵션: `-Dspring.profiles.active=prod`
   - `global-prod.properties` 적용

---

## 테스트 설정
- **테스트 프레임워크**: JUnit 4, Spring Test
- **MockMvc**를 사용하여 컨트롤러 테스트
- 테스트 실행 명령어:
  ```bash
  mvn test
  ```
- **예제 테스트 코드**:
  ```java
  @Test
  public void testHelloEndpoint() throws Exception {
      mockMvc.perform(get("/hello"))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$[0].hello").value("hello"));
  }
  ```

---

## 빌드 및 배포
1. **WAR 파일 생성**:
   ```bash
   mvn clean package
   ```
   - 생성된 WAR 파일 경로: `target/demo.war`

2. **Tomcat에 배포**:
   - `demo.war` 파일을 Tomcat의 `webapps` 디렉토리에 복사.
   - Tomcat 실행:
     ```bash
     ./catalina.sh run
     ```
   - 브라우저에서 접속: `http://localhost:8080/demo`
