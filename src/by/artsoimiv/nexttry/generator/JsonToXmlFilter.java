package by.artsoimiv.nexttry.generator;

import by.artsoimiv.nexttry.exception.EarlyExitException;
import by.artsoimiv.nexttry.exception.ParserBrokenException;
import by.artsoimiv.nexttry.parser.Element;
import by.artsoimiv.nexttry.parser.JsonParser;
import by.artsoimiv.nexttry.utils.FileUtil;

import java.io.IOException;
import java.util.Scanner;

public class JsonToXmlFilter {
    private static String  ROOTNAME = null;
    private static String  XMLDECL = null;
    private static String  DTD = null;
    private static String  DOCTYPE = null;
    private static String  TABWIDTH = null;
    private static String  INPUTFILE = null;

    private static boolean PASSIVE = false;
    private static boolean LOGGING = false;
    private static boolean PRETTY = false;



    public static void main( String[] args )
    {
        String content = FileUtil.readJson();

        try
        {
            JsonParser parser = new JsonParser( content );

            Element element = null;

            try
            {
                if( PASSIVE )
                {
                    outputOptionsAsIfToRun();
                    throw new EarlyExitException();
                }
                element = parser.parse();
            }
            catch( ParserBrokenException e )
            {
                e.printStackTrace();
            }

            XmlGenerator generator = createAndConfigureGenerator();

            String xml = generator.generate( element );

            FileUtil.writeXml(xml);
        }
        catch( EarlyExitException e ){}
    }

    private static final boolean isEmpty( String string ) { return( string == null || string.length() < 1 ); }


    private static String readContentFromStdIn() throws IOException
    {
        StringBuilder sb = new StringBuilder();

        Scanner sc = new Scanner( System.in );

        while( sc.hasNextLine() )
            sb.append( sc.nextLine() );

        sc.close();

        return sb.toString();
    }

    private static final void outputOptionsAsIfToRun()
    {
      //  doApplicationHeader();
        System.out.println( "Here are the options in force were you to run the filter:" );
        System.out.println( " ROOTNAME = " + ROOTNAME );
        System.out.println( "  XMLDECL = " + XMLDECL );
        System.out.println( "      DTD = " + DTD );
        System.out.println( "  DOCTYPE = " + DOCTYPE );
        System.out.println( " TABWIDTH = " + TABWIDTH );
        System.out.println( "INPUTFILE = " + INPUTFILE );
        System.out.println( "  PASSIVE = " + PASSIVE );
        System.out.println( "  LOGGING = " + LOGGING );
        System.out.println( "   PRETTY = " + PRETTY );
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
    }}
