import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--test")) {
            Checks.run();
            return;
        }
        StudentManager manager = new StudentManager();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1.추가 2.목록 3.조회 4.점수수정 5.삭제 6.종료");
                System.out.print("선택: ");
                if (!scanner.hasNextLine()) break;
                String input = scanner.nextLine().trim();
                try {
                    int choice = Integer.parseInt(input);
                    if (choice == 1) {
                        System.out.print("이름: ");
                        String name = scanner.nextLine();
                        int score = readNumber(scanner, "점수: ");
                        manager.addStudent(name, score);
                        System.out.println("추가 완료");
                    } else if (choice == 2) {
                        List<Student> students = manager.getStudents();
                        if (students.isEmpty()) System.out.println("등록된 학생이 없습니다.");
                        for (int i = 0; i < students.size(); i++) {
                            printStudent(i + 1, students.get(i));
                        }
                    } else if (choice == 3) {
                        int number = readNumber(scanner, "번호: ");
                        printStudent(number, manager.getStudentByNumber(number));
                    } else if (choice == 4) {
                        int number = readNumber(scanner, "번호: ");
                        int score = readNumber(scanner, "새 점수: ");
                        manager.changeScore(number, score);
                        System.out.println("변경 완료");
                    } else if (choice == 5) {
                        manager.removeStudent(readNumber(scanner, "번호: "));
                        System.out.println("삭제 완료");
                    } else if (choice == 6) {
                        break;
                    } else {
                        System.out.println("메뉴는 1~6입니다.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("int 범위의 정수를 입력하세요.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("해당 번호의 학생이 없습니다.");
                }
            }
        }
    }

    private static int readNumber(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static void printStudent(int number, Student student) {
        System.out.println(number + ". " + student.getName()
                + " (" + student.getScore() + "점)");
    }
}

final class Student {
    private final String name;
    private int score;

    public Student(String name, int score) {
        validateScore(score);
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    public void changeScore(int newScore) {
        validateScore(newScore);
        this.score = newScore;
    }

    private static void validateScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("점수는 0~100 사이여야 합니다.");
        }
    }
}

final class StudentManager {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(String name, int score) {
        students.add(new Student(name, score));
    }

    public Student getStudentByNumber(int number) {
        checkNumber(number);
        return students.get(number - 1);
    }

    public void changeScore(int number, int score) {
        getStudentByNumber(number).changeScore(score);
    }

    public void removeStudent(int number) {
        checkNumber(number);
        students.remove(number - 1);
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }

    private void checkNumber(int number) {
        if (number < 1 || number > students.size()) {
            throw new IndexOutOfBoundsException("해당 번호의 학생이 없습니다.");
        }
    }
}

// 학습용 확인 도구. 앱에서는 --test를 지정했을 때만 사용한다.
final class Checks {
    private static int passed;

    private static void check(boolean ok, String label) {
        if (!ok) throw new AssertionError(label);
        passed++;
    }

    private static void rejects(Class<? extends RuntimeException> type,
                                Runnable action, String label) {
        try {
            action.run();
        } catch (RuntimeException e) {
            check(e.getClass() == type, label);
            return;
        }
        throw new AssertionError(label + ": 예외 없음");
    }

    static void run() {
        passed = 0;
        for (int value : new int[]{0, 85, 100}) {
            Student s = new Student("민준", value);
            check(s.getScore() == value, "생성 경계 " + value);
            Student changing = new Student("민준", 85);
            changing.changeScore(value);
            check(changing.getScore() == value, "변경 경계 " + value);
        }
        for (int value : new int[]{-1, 101, 150}) {
            StudentManager manager = new StudentManager();
            rejects(IllegalArgumentException.class,
                    () -> manager.addStudent("세은", value), "생성 거부");
            check(manager.getStudents().isEmpty(), "목록 불변");
            Student s = new Student("민준", 85);
            rejects(IllegalArgumentException.class,
                    () -> s.changeScore(value), "변경 거부");
            check(s.getScore() == 85, "기존 85 유지");
        }
        for (String input : new String[]{"abc", "2147483648"}) {
            Student s = new Student("민준", 85);
            rejects(NumberFormatException.class,
                    () -> s.changeScore(Integer.parseInt(input)), "변환 거부");
            check(s.getScore() == 85, "변환 실패 후 상태");
        }
        StudentManager manager = new StudentManager();
        manager.addStudent("민준", 85);
        rejects(IndexOutOfBoundsException.class,
                () -> manager.changeScore(0, 100), "번호 거부");
        check(manager.getStudentByNumber(1).getScore() == 85, "번호 오류 후 상태");
        manager.changeScore(1, 100);
        check(manager.getStudentByNumber(1).getScore() == 100, "정상 변경");
        rejects(UnsupportedOperationException.class,
                () -> manager.getStudents().clear(), "목록 직접 변경 제한");
        check(manager.getStudents().size() == 1, "원본 목록 유지");

        BadStudent bad = new BadStudent();
        rejects(IllegalArgumentException.class, () -> bad.changeScore(150), "반례 예외");
        check(bad.score == 150, "catch는 이미 한 대입을 취소하지 않음");
        System.out.println("순서 반례: 실패 후 score=" + bad.score);

        Student a = new Student("A", 85);
        Student b = new Student("B", 70);
        rejects(IllegalArgumentException.class, () -> {
            a.changeScore(90);
            b.changeScore(150);
        }, "두 객체 연속 변경");
        check(a.getScore() == 90 && b.getScore() == 70, "부분 성공");
        System.out.println("두 객체 반례: A=" + a.getScore() + ", B=" + b.getScore());
        System.out.println("검증 통과: " + passed + "/" + passed);
    }

    // 의도적으로 잘못된 비교용 코드. Student 구현으로 사용하지 않는다.
    private static final class BadStudent {
        int score = 85;
        void changeScore(int newScore) {
            this.score = newScore;
            if (newScore < 0 || newScore > 100) {
                throw new IllegalArgumentException("범위 오류");
            }
        }
    }
}
