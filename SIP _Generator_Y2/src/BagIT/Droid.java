/**
 * @program: BagIT-SIP
 * @see klass som tar ut information ur droid output och skickar det  databas.
 * @author: Hamid Rofoogaran LDB-centrum | LTU
 */
package BagIT;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *
 */
public class Droid {

private String droidOutputXml;
private String pathConfig;

    /**
     * @method setpathConfig
     * @see Sätter sökväg till configfil.
     * @return void.
     */
    public void setPathConfig(String pathConfig) {
        this.pathConfig = pathConfig;
    }

    /**
     * @method setDroidOutputXml
     * @see Sätter sökväg till DROID outputfil.
     * @return void.
     */
    public void setDroidOutputXml(String droidOutputXml) {
        this.droidOutputXml = droidOutputXml;
    }

    
    /**
     * @method droidOutputXmlToDB
     * @see tar ut info från xml-fil och gör anrop tilll metod som skriver
     * @see ner info tilll DB.
     * @return void.
     */    
    public void droidOutputXmlToDB(){
        String paketUID="", filepath="", status="",name="",
               version="",puid="",mime="",idwarning="";

        //System.out.println("Start");
        try {
                //instans
                DataBase writeDB = new DataBase();
                ReadWriteXml rwXML = new ReadWriteXml();
                paketUID = rwXML.readConfigfile(pathConfig, "uid");
  
                
                String inputfile = droidOutputXml;
                File fXmlFile = new File(inputfile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
 
                
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("IdentificationFile"); 
                //låt oss loopa                
		for (int i = 0; i < nList.getLength(); i++) {
		   Node nNode = nList.item(i);
		   if (null != nNode && nNode.getNodeType() == Node.ELEMENT_NODE) {                       
		      Element eElement = (Element) nNode; 
                      filepath = tagValue("FilePath", eElement);
		      //System.out.println("File Name : " + filename);
                      status = tagValue("Status", eElement);
		      //System.out.println("StatuS : " + status);
                      name = tagValue("Name", eElement);
	              //System.out.println("Name : " + name);
                      version = tagValue("Version", eElement);
                      //System.out.println("Version : " + version);
                      puid = tagValue("PUID", eElement);
                      //System.out.println("PUID : " + puid);
                      mime = tagValue("MimeType", eElement);
                      //System.out.println("Mime : " + mime);
                      idwarning = tagValue("IdentificationWarning", eElement);
                      //System.out.println("IdentificationWarning: "+ idwarning);                     
		   } //end if
                   //till DB
                   writeDB.setPathConfig(pathConfig);
                   writeDB.writeToDroidInfoDB(paketUID, filepath, status, name, version, puid, mime, idwarning);
		} //end for
                
	  } catch (Exception e) {
              System.out.println("error:Droid:droidOutputXmlToDB... " +e.getMessage());	                  
          }                   
        //System.out.println("Stop");      
                              
    }//end metod 
    
    /**
     * @method tagValue
     * @see tar ut tagvärde ur given tag.
     * @return String, tagvärde.
     */      
  private String tagValue(String sTag, Element eElement) {
    String backvalue = "";      
    try{
        NodeList x = eElement.getElementsByTagName(sTag);
        
        if (null == x ||x.equals(null)||x.getLength() < 1 ) {
          return "none";
      }
        NodeList nlList = x.item(0).getChildNodes();
        if (x.item(0).hasChildNodes() == false) {
            return "none";
      }        
        Node nValue = (Node) nlList.item(0);
        backvalue = nValue.getTextContent(); 
    }catch(Exception e){
        System.out.println("error:Droid:tagValue... " +e.toString());
        return "";
    }
	return  backvalue;
  } //end metod           
}//end klass
