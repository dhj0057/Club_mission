import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            printMenu();
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 1) {
                    System.out.print("학생 이름: ");
                    String name = scanner.nextLine();
                    int score = readNumber(scanner, "점수 (0~100): ");
                    manager.addStudent(name, score);
                } else if (choice == 2) {
                    manager.printStudents();
                } else if (choice == 3) {
                    manager.showStudent(readNumber(scanner, "조회할 학생 번호: "));
                } else if (choice == 4) {
                    int number = readNumber(scanner, "점수를 바꿀 학생 번호: ");
                    int score = readNumber(scanner, "새 점수 (0~100): ");
                    manager.changeScore(number, score);
                } else if (choice == 5) {
                    manager.removeStudent(readNumber(scanner, "삭제할 학생 번호: "));
                } else if (choice == 6) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                } else {
                    System.out.println("1부터 6까지의 번호를 입력하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }

    private static int readNumber(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== 학생 명단 관리 프로그램 =====");
        System.out.println("1. 학생 추가");
        System.out.println("2. 학생 목록");
        System.out.println("3. 학생 조회");
        System.out.println("4. 점수 수정");
        System.out.println("5. 학생 삭제");
        System.out.println("6. 종료");
        System.out.print("선택: ");
    }
}
