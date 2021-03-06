package by.artsoimiv.nexttry.scanner;

import by.artsoimiv.nexttry.exception.IllegalCharacterOutsideOfTokenException;
import by.artsoimiv.nexttry.exception.NoMoreTokensException;

import java.util.LinkedList;

/**
 * Scans and manages tokenization.
 */
public class JsonScanner {
    private String content;  // content buffer
    private int previous; // beginning of previous token (unused?)
    private int scanned;  // total characters scanned
    private LinkedList<Token> pushed = new LinkedList<>(); //использую adFirst - поэтому Linked

    public JsonScanner() {
    }

    public JsonScanner(String input) {
        this.content = input;
        this.scanned = 0;
    }

    /**
     * If there is one or more pushed tokens on the stack, pop the last
     * one pushed and return.
     * <p>
     * Walk the content beginning at current. Analyze and return the next
     * token. Reset current to point at rest of unanalyzed content and
     * return the newly scanned token.
     *
     * @return the next token discovered.
     * @throws IndexOutOfBoundsException Means either that there are no more tokens or that analysis
     *                                   ended in mid-token because run out of characters and, therefore,
     *                                   that the emerging token was ill-formed (and therefore the JSON
     *                                   document as well).
     */
    public Token getNextToken()
            throws NoMoreTokensException, IllegalCharacterOutsideOfTokenException {
        Token token = popToken();

        if (token != null)
            return token;

        try {
            token = JsonScannerUtil.scanToken(content.substring(scanned));
            previous = scanned;
            scanned += token.getScanned();
        } catch (IndexOutOfBoundsException e) {
            if (scanned < content.length())
                throw new NoMoreTokensException("Stopped in middle of invalid token", e);
            else
                throw new NoMoreTokensException("No more tokens to scan", e);
        }

        return token;
    }

    /**
     * For use by the caller.
     *
     * @param token already got and wanted next time <tt>getNextToken()</tt> is called.
     */
    public void pushToken(Token token) {
        pushed.addFirst(token);
    }

    private Token popToken() {
        if (pushed.size() < 1)
            return null;

        return pushed.getFirst();
    }

    public int getScanned() {
        return scanned;
    }
}
