package search.keyword.common;

public enum CommonErrorMessage {

    INVALID_KEYWORD_LENGTH ("[ERROR] 검색 키워드 길이를 초과하였습니다."),
    INVALID_KEYWORD_FORMAT_IS_EMPTY("[ERROR] 검색어를 입력해주세요.");

    private final String errorMessage;

    CommonErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
