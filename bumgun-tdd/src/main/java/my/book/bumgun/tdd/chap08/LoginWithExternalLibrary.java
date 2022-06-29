package my.book.bumgun.tdd.chap08;

public class LoginWithExternalLibrary {

    private AuthService authService = new AuthService();

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public LoginResult login(String id, String pw) {
        int authorized = authService.authenticate(id, pw);

        return LoginResult.fail;
    }

}
