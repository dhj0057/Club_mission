public class Student {
    private final String name;
    private int score;

    public Student(String name, int score) {
        validateScore(score);
        this.name = name;
        this.score = score;
    }
    public String getName() { return name; }
    public int getScore() { return score; }

    public void changeScore(int nextScore) {
        validateScore(nextScore);
        this.score = nextScore;
    }
    private static void validateScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("점수는 0~100이어야 합니다.");
        }
    }
}
