import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Student student = new Student("민준", 85);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(student.getName() + " 현재 점수: " + student.getScore());
                System.out.print("변경할 정수 점수 (종료 q): ");
                if (!scanner.hasNextLine()) break;
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("q")) break;
                try {
                    int score = Integer.parseInt(input);
                    student.changeScore(score);
                    System.out.println("변경 완료");
                } catch (NumberFormatException e) {
                    System.out.println("정수를 입력하세요.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
