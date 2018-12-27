package by.artsoimiv.nexttry.scanner;

 // Details a scanned token.
public class Token
{
  String    token;
  TokenType type;
  int       scanned = 1;

  public Token() { };

  public Token( String token, TokenType type )
  {
    this.token = token;
    this.type = type;
  }

  public Token( String token, TokenType type, int scanned )
  {
    this(token, type);
    this.scanned = scanned;
  }

  public TokenType getType() { return type; }
  public String getToken() { return token; }
  public int getScanned() { return scanned; }

  public String toString()
  {
    return "{"
        + "\ntoken   = " + this.token
        + "\ntype    = " + this.type.toString()
        + "\nscanned = " + this.scanned
        + "\n}";
  }
}
