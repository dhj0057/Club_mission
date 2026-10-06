# Java 동아리 5회차 — 참조와 복사

JDK 17 이상, 외부 라이브러리 없음. ZIP을 모두 압축 해제한 뒤 실행합니다.

## 파일
- Main.java: 실험 4개(Main), 학생 상태와 복사 생성자(Student), 자동 확인(Checks).
- run.bat: Windows에서 네 가지 실험 실행.
- test.bat: Windows에서 19개 조건 검증.
- EXECUTION.txt: 제작 환경 Java 17의 실제 출력.

## Windows
run.bat을 더블클릭합니다. java 명령을 못 찾으면 JDK 설치 및 PATH를 확인하고 터미널을 다시 엽니다.
PowerShell에서는 .\run.bat 또는 .\test.bat으로 실행합니다.
소스 파일은 UTF-8입니다. 한글이 깨지면 Windows Terminal에서 chcp 65001 후 실행합니다.

## 터미널 (현재 폴더에 Main.java가 있어야 함)
```text
java -version
java -Dfile.encoding=UTF-8 Main.java
java -Dfile.encoding=UTF-8 Main.java --test
```
JDK의 소스 파일 실행 기능을 사용합니다. 명시적으로 컴파일하려면:
```text
javac -encoding UTF-8 -d out Main.java
java -Dfile.encoding=UTF-8 -cp out Main
java -Dfile.encoding=UTF-8 -cp out Main --test
```

## 예상되는 핵심 결과
1. 참조 대입: 같은 학생 true, 원본 100점.
2. 목록만 복사: 같은 목록 false, 같은 학생 true. 학생 수정은 공유, clear는 독립.
3. List.copyOf: clear 거부, Student 수정 가능. 원본 목록의 후속 추가는 반환 목록에 반영되지 않음.
4. 학생까지 복사: 같은 학생 false, 원본 85점, 복사본 100점.

각 실험은 새 85점 학생으로 시작합니다. 입력 메뉴 없이 정해진 실험을 순서대로 실행하므로 시연할 때 입력 실수를 줄일 수 있습니다.

## 공부 순서
PPT 3~7장: 결과 예측 → 실행.
PPT 8~9장: 복사 생성자와 반복문을 직접 재작성.
PPT 10~12장: 결과와 공유 목적을 자기 말로 설명.

## 범위
현재 Student는 int 점수와 불변 String 이름만 가집니다. 중첩 가변 객체, 동시성, 직렬화 복사, clone은 필수 범위 밖입니다.
Linux Java 17에서 소스 실행·컴파일 후 실행 및 검증을 확인했습니다. Windows 배치 파일은 실제 Windows에서 아직 확인하지 않았습니다.
자료 생성·제작 환경 검증과 본인의 실행·독립 작성·발표 완료는 별개입니다.
