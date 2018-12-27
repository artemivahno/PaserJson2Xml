package by.artsoimiv.nexttry.parser;

public class Key
{
  private String key;

  public Key() { }

  public Key( String key )
  {
    this.key = key;
  }

  public String getKey() { return key; }
  public void setKey( String key ) { this.key = key; }

  public String toString()
  {
    return "\"" + this.key + "\"";
  }
}
