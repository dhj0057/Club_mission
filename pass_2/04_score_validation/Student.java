public class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        validateScore(score);
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

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
