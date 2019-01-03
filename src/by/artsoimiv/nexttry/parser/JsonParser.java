package by.artsoimiv.nexttry.parser;

import by.artsoimiv.nexttry.exception.IllegalCharacterOutsideOfTokenException;
import by.artsoimiv.nexttry.exception.NoMoreTokensException;
import by.artsoimiv.nexttry.exception.ParserBrokenException;
import by.artsoimiv.nexttry.scanner.JsonScanner;
import by.artsoimiv.nexttry.scanner.Token;
import by.artsoimiv.nexttry.scanner.TokenType;



/**
 * JSON parser. It calls the scanner since there's little sense in holding
 * the scanner too separate anyway. However, the parser's output is in an
 * <tt>Element</tt> that can be consumed as desired or passed to this
 * library's XML generator.
 */
public class JsonParser {

    private JsonScanner scanner;

    public JsonParser( String content )
    {
        scanner = new JsonScanner( content );
    }

    public Element parse() throws ParserBrokenException
    {
        try
        {
            Token token = scanner.getNextToken();

            if( token.getType() != TokenType.OPEN_BRACE )
                throwParserException( "Ill-formed JSON construct", token );

            return parseElement();
        }
        catch( Exception e )
        {
            throw new ParserBrokenException( "Ill-formed JSON construct" );
        }
    }

    /**
     * Heavy lifting in the composition of elements. This is where it happens,
     * folks.
     * <p />
     * Think of this as starting immediately after a brace. We're looking for
     * a closing brace to pinch this off.
     *
     * @return a new <tt>Element</tt> consisting of key-value pairs.
     * @throws ParserBrokenException
     * @throws NoMoreTokensException
     */
    private Element parseElement()
            throws ParserBrokenException, NoMoreTokensException, IllegalCharacterOutsideOfTokenException
    {
        Element element = new Element();
        Token   token;
        Key     key = null;


        while( ( token = scanner.getNextToken() ) != null )
        {
            Value value;

            switch( token.getType() )
            {
                case IDENT :
                    if( key == null )
                    {
                        key = new Key( token.getToken() );
                    }
                    else
                    {
                        value = new Value( token.getToken() );
                        element.addKeyValuePair( key, value );
                        key = null; // got the value, make sure next time IDENT is a key again
                    }
                    break;

                case OPEN_BRACE :
                    Element subordinate = parseElement();

                    if( subordinate.size() > 0 )
                    {
                        value = new Value( subordinate );
                        element.addKeyValuePair( key, value );
                        key = null; // got the (complex) value, make sure next time IDENT is a key again
                    }
                    break;

                // start/stop array...
                case OPEN_BRACKET :
                    if( key == null )
                        throwParserException( "Syntax error (no key for array)", token );

                    Element maps = parseArray( key );

                    if( maps.size() > 0 )
                        element.addElementContents( maps );
                    break;

                case CLOSE_BRACE :
                    /* Been looking for this: we're done: tie off and pop back up.
                     */
                    return element;

                case COMMA :  // (okay, just skip it...)
                    break;

                case COLON :  // (okay, just skip it...)
                    break;

                case UNKNOWN :
                    throwParserException( "Unknown construct", token );

                default :
                    throwParserException( "Unexpected " + token.getType().name(), token );
            }
        }

        return element;
    }

    /**
     * Heavy lifting in the composition of array elements. Heading for XML as
     * we are, the strategy is simple: create a new element for each member of
     * the array we scan pairing it with a copy of the key.
     * <hr />
     * <b>Array parsing makes an non-trivial implementation decision</b>.
     * <hr />
     * We create and return a new element to our caller who will grab its
     * contents for adding to whatever element he had under construction in
     * the first place.
     * <p />
     * Note that one or more values in the array could just as well be complex
     * elements themselves.
     *
     * @param key with the array we're about to parse.
     * @return a new <tt>Element</tt> consisting of each array value paired
     *          with the (same) key.
     * @throws ParserBrokenException
     * @throws NoMoreTokensException
     */
    private Element parseArray( Key key )
            throws ParserBrokenException, NoMoreTokensException, IllegalCharacterOutsideOfTokenException
    {
        Element element = new Element();
        Token token;


        while( ( token = scanner.getNextToken() ) != null )
        {
            Key   newKey = new Key( key.getKey() );
            Value newValue;

            switch( token.getType() )
            {
                case IDENT :
                    newValue = new Value( token.getToken() );
                    element.addKeyValuePair( newKey, newValue );
                    break;

                case OPEN_BRACE :
                    Element subordinate = parseElement();

                    if( subordinate.size() > 0 )
                    {
                        newValue = new Value( subordinate );
                        element.addKeyValuePair( newKey, newValue );
                    }
                    break;

                case CLOSE_BRACKET :
                    /* Been looking for this: tie off and return.
                     */
                    return element;

                case COMMA :  // (okay, just skip it...)
                    break;

                case UNKNOWN :
                    throwParserException( "Unknown construct", token );

                default :
                    throwParserException( "Unexpected " + token.getType().name(), token );
            }
        }

        throwParserException( "Ill formed JSON construct parsing array", token );
        return element;
    }

    /**
     * Helper through which all broken-parser exceptions pass.
     *
     * @param message error statement.
     * @param location the token knows how many characters it has scanned so far.
     * @throws ParserBrokenException
     */
    private void throwParserException( String message, Token location ) throws ParserBrokenException
    {
        int position = location.getScanned() + scanner.getScanned();

        throw new ParserBrokenException( message + " at offset " + position );
    }

}
