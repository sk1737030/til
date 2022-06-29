package my.book.bumgun.tdd.chap07.register;

public class SpyEmailNotifier implements EmailNotifier {

    private boolean called;
    private String email;

    @Override
    public void sendRegisterEmail(String email) {
        this.called = true;
        this.email = email;
    }

    public boolean isCalled() {
        return this.called;
    }

    public String getEmail() {
        return email;
    }
}
