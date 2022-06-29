package my.book.bumgun.tdd.chap08;


public class AuthService {

    private String authKey = "str";

    public int authenticate(String id, String pw) {
        boolean authorize = AuthUtil.authorize(authKey);

        if (authorize) {
            return AuthUtil.authenticate(id, pw);
        } else {
            return -1;
        }
    }

}
