# Java 캡슐화와 데이터 검증

지난 학생 명단 관리 프로그램(ArrayList + 예외 처리)에 **점수(0~100)** 를 붙인 예제입니다.

## 파일 구성

- `Student.java`: 이름과 점수를 저장. 점수 범위 검사는 여기서만 합니다.
- `StudentManager.java`: 학생 추가·목록·조회·점수 수정·삭제
- `Main.java`: 메뉴 출력과 사용자 입력 처리. 형식 오류와 범위 오류를 따로 잡습니다.

## 실행

`run.bat`을 실행하거나, 터미널에서:

```text
javac -encoding UTF-8 -d .build Student.java StudentManager.java Main.java
java -cp .build Main
```

지난번 `.vscode` 설정(launch.json, tasks.json, settings.json)을 그대로 복사해도 파일 이름이 같아서 F5로 실행됩니다.

## 시연 순서

```
1 → 김민준 → 85      추가
1 → 장세은 → 150     거부 (학생이 만들어지지 않음)
2                    목록: 김민준만 있음
4 → 1 → 150          거부, 85 유지
4 → 1 → abc          숫자를 입력하세요
4 → 1 → 100          변경 (경계값)
4 → 1 → 0            변경 (경계값)
2                    목록: 김민준 (0점)
6                    종료
```

## 학습 포인트

- `private`은 직접 대입을 막을 뿐, 값이 올바른지는 보장하지 않습니다.
- 값이 들어오는 통로(생성자, `changeScore`)에서 같은 검사(`validateScore`)를 합니다.
- 검사가 대입보다 먼저이므로 실패하면 기존 점수가 그대로 남습니다.
- `Student`가 던진 `IllegalArgumentException`은 `StudentManager`를 지나 `Main`에서 잡힙니다.
- `NumberFormatException`(형식)은 `IllegalArgumentException`(범위)의 자식이라 먼저 잡아야 합니다.
