package BagIT;

/**
 * @program ReadWriteXml
 * @see Läser & skriver till config fil som används i projektet
 * @author Hamid Rofoogaran LTU
 */
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ReadWriteXml {
    
    /**
     * @method WriteConfigfile
     * @see Metod som skriver ner data till xml-configurationsfil
     * @param Sträng, sökväg till configfil
     * @param Sträng, tagnamn dit värde skall skrivas
     * @param Sträng, värdet som skall skrivas ner
     */
    public void writeConfigfile(String pathconfigfile, String tag, String value){

        String inputfile = pathconfigfile;
        String tagname = tag;
        String argXMLfile = value;

        try{
            //hårdkodat till configfil
            //inputfile = "//home/goran/warctools_runfiles/AIPfilelist.xml";
            //factory instans
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = null;
            Document document =null;
            factory.setIgnoringComments(false);

        try{
            File sfile = new File(inputfile);
            //skapa en instans av dokbuilder
            docBuild = factory.newDocumentBuilder();
            document = docBuild.parse(sfile);
            Element rootElement = document.getDocumentElement();

            NodeList nl = rootElement.getElementsByTagName(tagname);
            //System.out.println(tagname);
            //sätter innehåll
            Element e1 = (Element)nl.item(0);
            e1.setTextContent(argXMLfile);

           //skriver ner XML fil
           Transformer tF = TransformerFactory.newInstance().newTransformer();
           //skapar outputström
           FileOutputStream fos = new FileOutputStream(sfile);
           DOMSource so = new DOMSource(document);
           StreamResult res = new StreamResult(fos);
           tF.transform(so,res);
            fos.close();

        }catch(SAXException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(IOError e){
            System.out.println(e.toString());
        }catch(TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch(TransformerException e){
            System.out.println(e.getMessage());
        }catch(ParserConfigurationException e){
            System.out.println(e.getMessage());
        }
        }catch(IOError e) {
            System.out.println("error:read_writeXML:WriteConfigfile" +e);
        }

    }//endwritconfigfile

    /**
     * @method readConfigfile
     * @see Läser ut data ur xml configurationsfil
     * @param Sträng, sökväg till configfil
     * @param Sträng, taggnamn där data finns att hämta
     * @return Sträng, värdet från given tagg
     */
    public String readConfigfile(String pathconfigfile, String tag){

        String inputfile = pathconfigfile;
        String xmltag = tag;

        try{
            //hårdkodat till configfil
            //inputfile="//home/goran/warctools_runfiles/AIPfilelist.xml";
            //factory instans
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = factory.newDocumentBuilder();
            Document document = docBuild.parse(new File(inputfile));
            factory.setIgnoringComments(false);

            //normalisera text
            document.getDocumentElement().normalize();
            //
            NodeList nl = document.getElementsByTagName(xmltag);
            Node node = nl.item(0);
            Element El = (Element) node;

            return El.getTextContent();
        }catch(SAXParseException e){
            System.out.println("error:read_writeXML:SAX " +e.getMessage());
            return "fel";
        }   catch(Throwable t)   {
// Borttaget 2015-03-18. Förstår inte riktigt vad det är men koden funkar ändå !
        //    System.out.println("error:read_writeXML:ReadConfigfile:Throwable " +t.getMessage());
            return "fel";
        }  
    }//end readconfigfile

    
    /**
     * @method writeToXmlTagOrAttribByIdx
     * @see Skriver data till xml configurationsfil, taggar med attribut.
     * @param Sträng, sökväg till configfil.
     * @param Sträng, taggnamn.
     * @param Sträng, attribut.
     * @param Sträng, värde som skall skrivas till attributet.
     * @param int, vilken nod i ordningen som skall skrivas till.
     * @return boolean, true/false.
     */
        public boolean writeToXmlTagOrAttribByIdx(String pathofxmlfile, String tag,
                                                  String attr, String value, int nodeIdx){
  
        //Time to write to XML-file
        try{
            //Prepare to capture doc structure
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            fact.setIgnoringComments(false);
            DocumentBuilder docBuild = fact.newDocumentBuilder();
            File sfile = new File(pathofxmlfile);
            //Read the structure of submitted xmlfile
            Document doc = docBuild.parse(sfile);
            Element el = doc.getDocumentElement();
            NodeList nl = doc.getElementsByTagName(tag);
            //Navigate to specified element and update element content
            el = (Element)nl.item(nodeIdx);
            //Write to attribute or tag
            if(attr!=null){
                el.setAttribute(attr, value);
            } else
                el.setTextContent(value);
            //Overwrite old xmlfile with updated xmlfile content
            writetoXMLfile(pathofxmlfile, doc);
            return true;
        }catch(SAXException e){
            System.out.println("error:read_writeXML:writeToXmlTagOrAttribByIdx... "+ e.getMessage());
            return false;
        }catch(IOException e){
            System.out.println("error:read_writeXML:writeToXmlTagOrAttribByIdx... "+ e.getMessage());
            return false;
        }catch(IOError e){
            System.out.println("error:read_writeXML:writeToXmlTagOrAttribByIdx... "+ e.toString());
            return false;
        }catch(ParserConfigurationException e){
            System.out.println("error:read_writeXML:writeToXmlTagOrAttribByIdx... "+ e.getMessage());
            return false;
        }
     }

    /**
     * @method writetoXMLfile
     * @see Metod som skriver ner datat i xml-filen.
     * @param Sträng, sökväg till configfil.
     * @param Document, doc.
     * @return boolean, true/false.
     */
     private boolean writetoXMLfile(String pathtofile, Document doc){

        //skapar outputström
        FileOutputStream fos = null;
        boolean retval = true;
        try {
            Transformer tF = TransformerFactory.newInstance().newTransformer();
            fos = new FileOutputStream(pathtofile);
            DOMSource so = new DOMSource(doc);
            StreamResult res = new StreamResult(fos);
            tF.transform(so, res);
            fos.close();
        } catch (IOException ex) {
            retval = false;
            System.out.println("error:read_writeXML:writetoXMLfile... "+ ex.getMessage());
        } catch (TransformerException ex) {
            retval = false;
            System.out.println("error:read_writeXML:writetoXMLfile... "+ ex.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                retval = false;
                System.out.println("error:read_writeXML:writetoXMLfile... "+ ex.getMessage());
        }
    }
        return retval;
 }           
}