package my.book.bumgun.tdd.chap07.register;

public class StubWeakPasswordChecker implements WeakPasswordChecker {

    private boolean weak;

    @Override
    public boolean checkPasswordWeak(String pw) {
        return weak;
    }

    public void setWeak(boolean weak) {
        this.weak = weak;
    }
}
