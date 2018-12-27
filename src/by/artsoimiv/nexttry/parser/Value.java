package by.artsoimiv.nexttry.parser;

/**
 * A value can be either simply an identifier (a quoted string, number, etc.)
 * or an arbitrarily complex construct/element.
 */
public class Value
{
  private String  value;    // if scalar
  private Element element;  // if of hierarchical composition

  public Value() { }
  public Value( String value ) { this.value = value; }
  public Value( Element element ) { this.element = element; }

  public String getValue() { return value; }
  public void setValue( String value ) { this.value = value; }

  public Element getElement() { return element; }
  public void setElements( Element element ) { this.element = element; }

  public String toString()
  {
    if( value != null )
      return "\"" + this.value + "\"";

    if ( element != null )
      return this.element.toString();

    return "null";
  }
}
