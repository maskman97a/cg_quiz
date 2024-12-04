public enum QuestionDifficultEnum {
    EASY("Easy", "Dễ"),
    NORMAL("Normal", "Trung Bình"),
    HARD("Hard", "Khó");

    private final String displayName;
    private final String displayNameVie;

    QuestionDifficultEnum(String displayName, String displayNameVie) {
        this.displayName = displayName;
        this.displayNameVie = displayNameVie;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameVie() {
        return displayNameVie;
    }
}
