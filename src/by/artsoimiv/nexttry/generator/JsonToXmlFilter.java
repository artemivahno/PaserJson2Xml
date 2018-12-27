package by.artsoimiv.nexttry.generator;

import by.artsoimiv.nexttry.JsonFileReader;
import by.artsoimiv.nexttry.exception.EarlyExitException;
import by.artsoimiv.nexttry.exception.ParserBrokenException;
import by.artsoimiv.nexttry.parser.Element;
import by.artsoimiv.nexttry.parser.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JsonToXmlFilter {

    private static final int EIO = 5;  // (from errno.h)

    private static String ROOTNAME = null;
    private static String XMLDECL = null;
    private static String DTD = null;
    private static String DOCTYPE = null;
    private static String TABWIDTH = null;
    private static String INPUTFILE = null;

    private static boolean PRETTY = false;

    private static void badCommandLine() throws EarlyExitException {
        throw new EarlyExitException();
    }

    private static void parseOptionsFromCommandLine(String[] args) throws EarlyExitException {
        if (args == null)    // shouldn't happen, but treat as if args.length == 1...
            return;

        String lookingFor = null;

        for (String arg : args) {
            switch (arg) {
                default:
                    if (lookingFor == null) {
                        INPUTFILE = arg;

                        if (isEmpty(INPUTFILE))
                            badCommandLine();
                        break;
                    }

                    switch (lookingFor) {
                        case "rootname":
                            ROOTNAME = arg;
                            lookingFor = null;
                            break;
                        case "xmldecl":
                            XMLDECL = arg;
                            lookingFor = null;
                            break;
                        case "dtd":
                            DTD = arg;
                            lookingFor = null;
                            break;
                        case "doctype":
                            DOCTYPE = arg;
                            lookingFor = null;
                            break;
                        case "tabwidth":
                            TABWIDTH = arg;
                            lookingFor = null;
                            break;
                    }
                    break;

                case "--rootname":
                    lookingFor = "rootname";
                    break;
                case "--xmldecl":
                    lookingFor = "xmldecl";
                    break;
                case "--dtd":
                    lookingFor = "dtd";
                    break;
                case "--doctype":
                    lookingFor = "doctype";
                    break;
                case "--tabwidth":
                    lookingFor = "tabwidth";
                    break;

                case "--pretty":
                    PRETTY = true;
                    break;

                case "--version":
                    System.exit(0);
                    break;

                case "--help":
                    throw new EarlyExitException();
            }
        }
    }

    public static void main(String[] args) {


        JsonFileReader readJson = new JsonFileReader("by/artsoimiv/recources/sample.json");

        JsonParser parser = new JsonParser(readJson.getFile("by/artsoimiv/recources/sample.json"));

        Element element = null;

        try {
            element = parser.parse();
        } catch (ParserBrokenException e) {
            e.printStackTrace();
        }
      XmlGenerator generator = createAndConfigureGenerator();

      String xml = generator.generate( element );

      System.out.println(xml);

//        try
//        {
//            parseOptionsFromCommandLine( args );
//
//            try
//            {
//                if( PASSIVE )
//                {
//                    outputOptionsAsIfToRun();
//                    throw new EarlyExitException();
//                }
//
//                element = parser.parse();
//            }
//            catch( ParserBrokenException e )
//            {
//                e.printStackTrace();
//            }
//
//            XmlGenerator generator = createAndConfigureGenerator();
//
//            String xml = generator.generate( element );
//
//            System.out.println( xml );
//        }
//        catch( EarlyExitException e )
//        {
//            ;
//        }


    }

    private static final boolean isEmpty(String string) {
        return (string == null || string.length() < 1);
    }

    private static String readContentFromFile(String filename) throws IOException {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        byte[] contents = new byte[(int) f.length()];

        fis.read(contents);
        fis.close();

        return new String(contents, "UTF-8");
    }

  private static XmlGenerator createAndConfigureGenerator()
  {
    XmlGenerator generator = new XmlGenerator();

    if( !isEmpty( ROOTNAME ) )
      generator.setRootName( ROOTNAME );
    if( !isEmpty( XMLDECL ) )
      generator.setXmlDeclaration( XMLDECL );
    if( !isEmpty( DTD ) )
      generator.setDtd( DTD );
    if( !isEmpty( DOCTYPE ) )
      generator.setDocType( DOCTYPE );

    // pretty printing stuff...
    int tabwidth = 0;

    if( !isEmpty( TABWIDTH ) )
      tabwidth = Integer.parseInt( TABWIDTH );

    if( PRETTY )
    {
      if( tabwidth > 0 )
        generator.configurePrettyPrinter( true, tabwidth );
      else
        generator.configurePrettyPrinter( true );
    }


    return generator;
  }
}
