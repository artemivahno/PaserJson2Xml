package by.artsoimiv.nexttry.generator;

import by.artsoimiv.nexttry.parser.Element;
import by.artsoimiv.nexttry.parser.Key;
import by.artsoimiv.nexttry.parser.Value;

import java.util.Map;

public class XmlGenerator {
    private String rootName;
    private String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    private String dtd;
    private String docType;
    private StringBuilder xml;

    private PrettyPrinter prettyPrinter = new PrettyPrinter();

    public XmlGenerator() {
    }

    public void configurePrettyPrinter(boolean enabled) {
        prettyPrinter.enabled = enabled;
    }

    public void configurePrettyPrinter(boolean enabled, int indentWidth) {
        prettyPrinter.enabled = enabled;
        prettyPrinter.indentWidth = indentWidth;
    }

    public void configurePrettyPrinter(boolean enabled, int tabLevel, int indentWidth) {
        prettyPrinter.enabled = enabled;
        prettyPrinter.currentTabLevel = tabLevel;
        prettyPrinter.indentWidth = indentWidth;
    }

    /**
     * Generates the XML based on the parsed JSON document. We replace
     * whatever string buffer may have been used before as this is a
     * brand new generation.
     *
     * @param root as returned from <tt>JsonParser.parse()</tt>.
     * @return XML output.
     */
    public String generate(Element root) {
        xml = new StringBuilder();

        prettyPrinter.injectXmlStringBuilder(xml);
        prettyPrinter.reallocateTab();

        handleHeaders();

        if (!isEmpty(rootName)) {
            newline();
            xml.append("<" + rootName + ">");
            prettyPrinter.currentTabLevel++;
        }

        if (root != null && root.size() > 0) {
            generateElement(root);
        }

        if (!isEmpty(rootName)) {
            newline();
            xml.append("</" + rootName + ">");
            prettyPrinter.currentTabLevel--;
        }

        return xml.toString();
    }

    /**
     * Generate XML from the intermediate <tt>Element</tt>s, <tt>Key</tt>s and
     * <tt>Value</tt>s.
     *
     * @param root dominating a JSON element representation that is to be rendered in XML.
     */
    public void generateElement(Element root) {
        for (Map.Entry<Key, Value> element : root.getKeyValuePairs().entrySet()) {
            String key = element.getKey().getKey();

            newlineAndIndent();
            issueOpeningTag(key);

            Value value = element.getValue();

            /* If the value is a simple one, just put out the closing tag.
             */
            if (value.getValue() != null) {
                issueElementContent(value.getValue());
            } else // if( value.getElement() != null && value.getElement().size() > 0 )
            {
                /* If the value is a complex (hierarchical) entity, call ourselves
                 * recursively, bumping the indentation level.
                 */
                Element descend = value.getElement();

                prettyPrinter.currentTabLevel++;
                generateElement(descend);
                prettyPrinter.currentTabLevel--;
                newlineAndIndent();
            }

            issueClosingTag(key);
        }
    }

    public void issueOpeningTag(String tag) {
        xml.append("<" + tag + ">");
    }

    public void issueClosingTag(String tag) {
        xml.append("</" + tag + ">");
    }

    public void issueElementContent(String content) {
        xml.append(content);
    }

    private void handleHeaders() {
        boolean newlineNeeded = false;

        if (!isEmpty(xmlDeclaration)) {
            newlineNeeded = true;
            xml.append(xmlDeclaration);
        }

        if (!isEmpty(dtd)) {
            if (newlineNeeded)
                newline();

            newlineNeeded = true;
            xml.append(dtd);
        }

        if (!isEmpty(docType)) {
            if (newlineNeeded)
                newline();

            newlineNeeded = true;
            xml.append(docType);
        }
    }

    private void newline() {
        prettyPrinter.newline();
    }

    private void newlineAndIndent() {
        prettyPrinter.newlineAndIndent();
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String name) {
        this.rootName = name;
    }

    public String getXmlDeclaration() {
        return xmlDeclaration;
    }

    public void setXmlDeclaration(String header) {
        this.xmlDeclaration = header;
    }

    public String getDtd() {
        return dtd;
    }

    public void setDtd(String dtd) {
        this.dtd = dtd;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void makeTabsVisible() {
        prettyPrinter.showTabs = true;
    }

    private boolean isEmpty(String string) {
        return (string == null || string.length() < 1);
    }

    // =================================================================

    /**
     * Manage pretty printing for XML generator output. By default,
     * the service is turned off. Allows for making the leftmost margin
     * begin at any even increment to the right of the actual column 1.
     * Allows width of tab to be specified, default is 2 spaces.
     *
     * <h3> Rules </h3>
     * <p>
     * - Issue newlines <u>before</u> line (not after).
     * <p/>
     */
    static class PrettyPrinter {
        private static final char VISIBLE_TAB = '\u00B7';
        private static final char SPACE = ' ';

        public boolean enabled = false;// default: don't pretty-print
        public int currentTabLevel = 0;    // default: start at column 1
        public int indentWidth = 2;    // default: indentation is two spaces
        public String tab = "  "; // (the two spaces)
        public boolean showTabs = false;// (for debugging)

        public StringBuilder xml;

        protected PrettyPrinter() {
        }

        protected void injectXmlStringBuilder(StringBuilder sb) {
            this.xml = sb;
        }

        protected void reallocateTab() {
            char character = (showTabs) ? VISIBLE_TAB : SPACE;
            int width = indentWidth;

            StringBuilder sb = new StringBuilder(width);
            while (width-- > 0)
                sb.append(character);
            tab = sb.toString();
        }

        protected void nl() {
            xml.append('\n');
        }

        protected void tab() {
            for (int indent = 0; indent < currentTabLevel; indent++)
                xml.append(tab);
        }

        protected void newline() {
            if (enabled)
                nl();
        }

        protected void indent() {
            if (enabled)
                tab();
        }

        protected void unindent() {
            // do nothing...
        }

        protected void newlineAndIndent() {
            if (enabled) {
                nl();
                tab();
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");

            sb.append("\n  hashCode = " + this.hashCode());
            sb.append("\n  enabled = " + enabled);
            sb.append("\n  currentTabLevel = " + currentTabLevel);
            sb.append("\n  indentWidth = " + indentWidth);
            sb.append("\n  tab = " + tab);
            sb.append("\n  xml = " + xml.toString());

            return sb.append("\n}").toString();
        }
    }
}
