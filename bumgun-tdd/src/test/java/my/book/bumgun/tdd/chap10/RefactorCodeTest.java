package my.book.bumgun.tdd.chap10;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RefactorCodeTest {

    /**
     * 실행 시점이 다르다고 실패하지 않기
     */


    public static class Member {

        private LocalDateTime expiryDate;

        public Member(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
        }

        public boolean isExpired() {
            return expiryDate.isBefore(LocalDateTime.now());
        }

    }

    @Test
    void notExpired() {
        LocalDateTime expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0);
        Member m = new Member(expiry);
        assertFalse(m.isExpired());
    }



    /**
     * 셋업을 이용해서 중복된 상황을 설정하지 않기
     */
//    ChangeUserService changeUserService;
//
//    @BeforeEach
//    void setUp() {
////        changeUserService = new ChangeUserService(memoryRepositrory);
////        memoryRepository.save(new User("id", "name", "pw", new Address("서울", "남부 ")));
//    }
//
//    @Test
//    void noUser() {
//        assertThrows(UserNotFoundException.class, () -> changeUserService.changeAddress("id2", new Address("서울","남부")));
//    }
//
//    @Test
//    void changePw() {
//        changeUserService.changePw("id", "pw", "newpw");
//
//        User user = memoryRepository.findById("id");
//        assertTrue(user.matchPassword("newpw"));
//    }

    /**
     * 두 가지를 검증하지 않기
     */
    @Test
    void 같은_Id가_없으면_가입에_성공하고_메일을_전송함() {
        // 검증1: 회원 데이터가 올바르게 저장되었는지 검증
//        User savedUser = fakeRepository.findById("id");
//        assertEquals("id", savedUser.getId());
//        assertEquals("email", savedUser.getEmail());
//
//        // 검증2: 이메일 발송을 요청했는지 검증
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        BDDMockito.then(mockEmailNotifier)
//            .should().sendRegisterEmail(captor.capture());
//
//        String realEmail = captor.getValue();
//        assertEquals("email@email.com", realEmail);

    }

    /**
     * 변수나 필드를 사용해서 기댓값 표현하지 않기
     */
//    private List<Integer> answers = Arrays.asList(1, 2, 3, 4);
//    private Long respondentId = 100L;
    @Test
    void 답변에_성공하면_결과_저장함() {
//        // 답변할 설문이 존재
//        Survey survey = SurveyFactory.createApprovedSurvey(1L);
//        surveyRepository.save(survey);
//
//        // 설문 답변
//        SurveyAnswerRequest surveyAnswerRequest = SurveyAnswerRequest.builder()
//            .surveyId(survey.getId())
//            .respondentId(respondentId)
//            .answers(answers)
//            .build();
//
//        svc.answerSurvey(surveyAnswerRequest);
//
//        // 저장결과확인
//        SurveyAnswer savedAnswer =
//            surveyRepository.findBySurveyAndRespondent(survey.getId(), respondentId);
//
//        assertAll(
//            () -> assertEquals(respondentId, savedAnswer.getRespondentId()),
//            () -> assertEquals(answers.size(), savedAnswer.getAnswers().size()),
//            () -> assertEquals(answers.get(0), savedAnswer.getAnswers().get(0)),
//            () -> assertEquals(answers.get(1), savedAnswer.getAnswers().get(1)),
//            () -> assertEquals(answers.get(2), savedAnswer.getAnswers().get(2))
//        );
//        // 답변할 설문이 존재
//        Survey survey = SurveyFactory.createApprovedSurvey(1L);
//        surveyRepository.save(survey);
//
//        // 설문 답변
//        SurveyAnswerRequest surveyAnswerRequest = SurveyAnswerRequest.builder()
//            .surveyId(survey.getId())
//            .respondentId(respondentId)
//            .answers(answers)
//            .build();
//
//        svc.answerSurvey(surveyAnswerRequest);
//
//        // 저장결과확인
//        SurveyAnswer savedAnswer =
//            surveyRepository.findBySurveyAndRespondent(survey.getId(), respondentId);
//
//        assertAll(
//            () -> assertEquals(respondentId, savedAnswer.getRespondentId()),
//            () -> assertEquals(answers.size(), savedAnswer.getAnswers().size()),
//            () -> assertEquals(answers.get(0), savedAnswer.getAnswers().get(0)),
//            () -> assertEquals(answers.get(1), savedAnswer.getAnswers().get(1)),
//            () -> assertEquals(answers.get(2), savedAnswer.getAnswers().get(2))
//        );
//

//                // 답변할 설문이 존재
//                Survey survey = SurveyFactory.createApprovedSurvey(1L);
//                surveyRepository.save(survey);
//
//                // 설문 답변
//                SurveyAnswerRequest surveyAnswerRequest = SurveyAnswerRequest.builder()
//                    .surveyId(1L)
//                    .respondentId(100L)
//                    .answers(Arrays.asList(1,2,3,4))
//                    .build();
//
//                svc.answerSurvey(surveyAnswerRequest);
//
//                // 저장결과확인
//                SurveyAnswer savedAnswer =
//                    surveyRepository.findBySurveyAndRespondent(1L, 100L);
//
//                assertAll(
//                    () -> assertEquals(100L, savedAnswer.getRespondentId()),
//                    () -> assertEquals(4, savedAnswer.getAnswers().size()),
//                    () -> assertEquals(1, savedAnswer.getAnswers().get(0)),
//                    () -> assertEquals(2, savedAnswer.getAnswers().get(1)),
//                    () -> assertEquals(3, savedAnswer.getAnswers().get(2))
//                );

    }

    private String formatDate(LocalDate date) {
        return date.getYear() + "년 "
            + date.getMonthValue() + "월 "
            + date.getDayOfMonth() + "일";
    }

    @Test
    void dateFormat() {
        LocalDate date = LocalDate.of(1945, 8, 15);
        String dateStr = formatDate(date);
        assertEquals(date.getYear() + "년 "
            + date.getMonthValue() + "월 "
            + date.getDayOfMonth() + "일", dateStr);

        // better than upper code
        assertEquals("1945년 8월 15일", dateStr);
    }


}
