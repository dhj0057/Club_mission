import java.util.ArrayList;
import java.util.List;

/** Java 동아리 5회차: 각 실험은 독립된 85점 학생으로 시작합니다. */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--test")) {
            Checks.run();
            return;
        }
        System.out.println("[1] 참조 대입");
        Student original = new Student("민준", 85);
        Student alias = original;
        alias.changeScore(100);
        System.out.println("same student: " + (original == alias));
        System.out.println("original score: " + original.getScore());

        System.out.println("\n[2] 목록만 복사");
        List<Student> source = sample();
        List<Student> shallow = new ArrayList<>(source);
        System.out.println("same list: " + (source == shallow));
        System.out.println("same student: " + (source.get(0) == shallow.get(0)));
        shallow.get(0).changeScore(100);
        System.out.println("source score: " + source.get(0).getScore());
        shallow.clear();
        System.out.println("sizes: source=" + source.size() + ", copy=" + shallow.size());

        System.out.println("\n[3] List.copyOf");
        source = sample();
        List<Student> fixed = List.copyOf(source);
        try {
            fixed.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("clear: blocked");
        }
        fixed.get(0).changeScore(100);
        System.out.println("source score: " + source.get(0).getScore());
        source.add(new Student("세은", 70));
        System.out.println("sizes: source=" + source.size() + ", fixed=" + fixed.size());

        System.out.println("\n[4] 학생까지 복사");
        source = sample();
        List<Student> independent = copyStudents(source);
        independent.get(0).changeScore(100);
        System.out.println("same student: " + (source.get(0) == independent.get(0)));
        System.out.println("scores: source=" + source.get(0).getScore()
                + ", copy=" + independent.get(0).getScore());
    }

    static List<Student> sample() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("민준", 85));
        return students;
    }

    static List<Student> copyStudents(List<Student> source) {
        List<Student> copied = new ArrayList<>();
        for (Student student : source) {
            copied.add(new Student(student));
        }
        return copied;
    }
}

final class Student {
    private final String name;
    private int score;

    Student(String name, int score) {
        validateScore(score);
        this.name = name;
        this.score = score;
    }

    // 직접 작성한 복사 생성자. 현재 필드는 불변 String과 기본형 int입니다.
    Student(Student other) {
        this(other.name, other.score);
    }

    String getName() { return name; }
    int getScore() { return score; }

    void changeScore(int newScore) {
        validateScore(newScore);
        this.score = newScore;
    }

    private static void validateScore(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("점수는 0~100 사이여야 합니다.");
        }
    }
}

final class Checks {
    private static int passed;

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        passed++;
    }

    static void run() {
        passed = 0;
        Student a = new Student("민준", 85);
        Student b = a;
        check(a == b, "참조 대입 후 같은 학생");
        b.changeScore(100);
        check(a.getScore() == 100, "별칭으로 변경한 결과 공유");
        b = new Student("세은", 70);
        check(a.getScore() == 100 && a != b, "변수 재대입은 원래 학생을 변경하지 않음");

        List<Student> source = Main.sample();
        List<Student> shallow = new ArrayList<>(source);
        check(source != shallow, "목록은 별개");
        check(source.get(0) == shallow.get(0), "원소는 공유");
        shallow.get(0).changeScore(100);
        check(source.get(0).getScore() == 100, "공유한 원소 변경");
        shallow.clear();
        check(source.size() == 1 && shallow.isEmpty(), "목록 삭제는 독립");

        source = Main.sample();
        List<Student> fixed = List.copyOf(source);
        boolean blocked = false;
        try { fixed.clear(); }
        catch (UnsupportedOperationException expected) { blocked = true; }
        check(blocked, "목록 변경 거부");
        check(source.get(0) == fixed.get(0), "copyOf 원소 공유");
        fixed.get(0).changeScore(100);
        check(source.get(0).getScore() == 100, "copyOf도 원소 변경 가능");
        source.add(new Student("세은", 70));
        check(source.size() == 2 && fixed.size() == 1, "원본 목록 추가는 반영되지 않음");

        source = Main.sample();
        List<Student> copied = Main.copyStudents(source);
        check(source != copied && source.get(0) != copied.get(0), "목록과 학생 분리");
        check(copied.get(0).getName().equals("민준") && copied.get(0).getScore() == 85,
                "복사 시 값 보존");
        copied.get(0).changeScore(100);
        check(source.get(0).getScore() == 85 && copied.get(0).getScore() == 100,
                "복사본 수정은 독립");
        blocked = false;
        try { copied.get(0).changeScore(150); }
        catch (IllegalArgumentException expected) { blocked = true; }
        check(blocked && copied.get(0).getScore() == 100, "전회차 검증 규칙 유지");
        copied.get(0).changeScore(0);
        check(copied.get(0).getScore() == 0, "경계값 0 허용");
        copied.clear();
        check(source.size() == 1, "복사 목록 삭제도 독립");

        Student local = new Student("민준", 85);
        change(local);
        check(local.getScore() == 100, "인수로 전달된 참조 값으로 객체 변경");
        replace(local);
        check(local.getScore() == 100, "매개변수 재대입은 호출자 변수에 영향 없음");
        System.out.println("PASS: " + passed + " checks");
    }

    static void change(Student s) { s.changeScore(100); }
    static void replace(Student s) { s = new Student("새 학생", 0); }
}
