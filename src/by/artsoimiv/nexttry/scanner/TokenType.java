package by.artsoimiv.nexttry.scanner;

/**
 * Qualifies a scanned token.
 */
public enum TokenType {
    UNKNOWN,          // as yet undiscovered
    IDENT,            // tokens are delimited by double-quotes not by spaces and other punctuation
    OPEN_BRACE,
    CLOSE_BRACE,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    COLON,
    COMMA;

    private TokenType() {
    }

    public String toString() {
        return this.name();
    }
}
