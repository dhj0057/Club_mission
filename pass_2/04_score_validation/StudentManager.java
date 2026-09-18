import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(String name, int score) {
        students.add(new Student(name, score));
        System.out.println(name + " 학생을 " + score + "점으로 추가했습니다.");
    }

    public void printStudents() {
        if (students.isEmpty()) {
            System.out.println("등록된 학생이 없습니다.");
            return;
        }

        System.out.println("===== 학생 목록 =====");
        int number = 1;
        for (Student student : students) {
            System.out.println(number++ + ". " + student.getName() + " (" + student.getScore() + "점)");
        }
    }

    public void showStudent(int number) {
        Student student = getStudentByNumber(number);
        if (student == null) {
            System.out.println("해당 번호의 학생이 없습니다.");
            return;
        }
        System.out.println("조회한 학생: " + student.getName() + " (" + student.getScore() + "점)");
    }

    public void changeScore(int number, int score) {
        Student student = getStudentByNumber(number);
        if (student == null) {
            System.out.println("해당 번호의 학생이 없습니다.");
            return;
        }
        student.changeScore(score);
        System.out.println(student.getName() + " 학생의 점수를 " + score + "점으로 바꿨습니다.");
    }

    public void removeStudent(int number) {
        Student student = getStudentByNumber(number);
        if (student == null) {
            System.out.println("해당 번호의 학생이 없습니다.");
            return;
        }
        students.remove(number - 1);
        System.out.println(student.getName() + " 학생을 삭제했습니다.");
    }

    private Student getStudentByNumber(int number) {
        try {
            return students.get(number - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
