/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BagIT;



import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.sql.ResultSet; 
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class CreateIndDC {
 
	public static void main(String argv[]) {
           
       
	  try {
 
              
        DataBase dbx = new DataBase();
        dbx.setDbForRs();
        ResultSet rs4 = dbx.getDataForMets(3);
        rs4.next();  
 
              
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
                
                
          
        Element rec = doc.createElementNS("http://purl.org/dc/elements/1.1/", "dc:record");
        doc.appendChild(rec);
        
//        Element rec = doc.createElement("record");
        rec.setTextContent("xmlns:dc=http://purl.org/dc/elements/1.1/ xmlns:dcterms=http://purl.org/dc/terms/ xmlns:xsi= http://www.w3.org/2001/XMLSchema-instance");
//        root.appendChild(rec);
        
        Element t = doc.createElement("dc:identifier");
        t.setTextContent(rs4.getString(2));
        rec.appendChild(t);
//        root.appendChild(rec);
        
        Element c = doc.createElement("dc:creator");
        c.setTextContent(rs4.getString(3));
        rec.appendChild(c);
//        root.appendChild(rec);
        
        Element p = doc.createElement("dc:name");
        p.setTextContent(rs4.getString(4));
        rec.appendChild(p);
 //       root.appendChild(rec);
        
        Element d = doc.createElement("dc:date");  
        d.setTextContent(rs4.getString(5));
        rec.appendChild(d);              
                
        Element desc = doc.createElement("dc:description");  
        desc.setTextContent(rs4.getString(5));
        rec.appendChild(desc);                              
                
                
                
//                
//		Element rootElement = doc.createElement("dc");
//		doc.appendChild(rootElement);
//                
//                Element record = doc.createElement("record");
//		doc.appendChild(record);
// 
//		// staff elements
//		Element creator = doc.createElement("creator");
//		record.appendChild(creator);           
//                
//		// set attribute to staff element
////		Attr attr = doc.createAttribute("Architect");
////		attr.setValue("1");
////		creator.setAttributeNode(attr);
// 
//		// shorten way
//		// staff.setAttribute("id", "1");
// 
//		// firstname elements
//		Element identifier = doc.createElement("identifier");
////		firstname.appendChild(doc.createTextNode("yong"));
//                identifier.setTextContent(rs4.getString(2));
//		record.appendChild(identifier);
// 
//		// lastname elements
//		Element name = doc.createElement("name");
//		name.setTextContent(rs4.getString(3));
//		record.appendChild(name);
// 
//		// nickname elements
//		Element date = doc.createElement("date");
//		date.setTextContent(rs4.getString(4));
//		record.appendChild(date);
// 
//		// salary elements
//		Element description = doc.createElement("description");
//		description.setTextContent(rs4.getString(5));
//		record.appendChild(description);
// 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("/home/saleh/duraark/built/dc.xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
           // catch ()    
	  } catch (Exception ex) {
                Logger.getLogger(CreateIndDC.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}