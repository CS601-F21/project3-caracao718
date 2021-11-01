package project1;

/**
 * A class that stores the JSON in the QA file.
 */
public class QuestionAndAnswer extends Item{
    private String questionType;
    private String answerTime;
    private long unixTime;
    private String question;
    private String answerType;
    private String answer;

    public QuestionAndAnswer(String questionType, String asin, String answerTime, long unixTime, String question, String answerType, String answer) {
        this.questionType = questionType;
        this.asin = asin;
        this.answerTime = answerTime;
        this.unixTime = unixTime;
        this.question = question;
        this.answerType = answerType;
        this.answer = answer;
    }

    /**
     * get the question in Q&A
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * get the answer in Q&A
     * @return String
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * get the asin number for the object
     * @return String
     */
    public String getAsin() {
        return asin;
    }


    /**
     * print the object with all it's fields
     * @return String
     */
    @Override
    public String toString() {
        return "QuestionAndAnswer{" +
                "questionType='" + questionType + '\'' +
                ", asin='" + asin + '\'' +
                ", answerTime='" + answerTime + '\'' +
                ", unixTime=" + unixTime +
                ", question='" + question + '\'' +
                ", answerType='" + answerType + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
