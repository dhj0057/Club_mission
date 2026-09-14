# Java 캡슐화와 데이터 검증

기존 프로그램과 별도 폴더에서 실행하세요. JDK 필요.

```sh
javac -encoding UTF-8 Student.java Main.java Check.java
java Main
java Check
```

시연: 150 → abc → 0 → 100 → q. 시작 점수는 85입니다. 실패 시 마지막 정상 점수를 유지합니다.
Check는 허용 경계값, 잘못된 초기 점수, 변경 실패 후 상태를 확인합니다.
Java 17에서 컴파일·실행 확인. 본인 독립 재작성 및 발표 완료와는 별도입니다.
