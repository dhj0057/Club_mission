public class Check {
    public static void main(String[] args) {
        for (int value : new int[]{0,100,85}) {
            Student s = new Student("학생", value);
            if (s.getScore()!=value) throw new AssertionError("생성");
            s.changeScore(value);
            if (s.getScore()!=value) throw new AssertionError("변경");
        }
        for (int value : new int[]{-1,101,150}) {
            Student s = new Student("학생",85);
            try {s.changeScore(value); throw new AssertionError("허용됨");}
            catch (IllegalArgumentException expected) {}
            if (s.getScore()!=85) throw new AssertionError("상태 손상");
            try {new Student("학생",value); throw new AssertionError("생성됨");}
            catch (IllegalArgumentException expected) {}
        }
        System.out.println("경계값·생성 거부·실패 후 상태 유지 확인 완료");
    }
}
