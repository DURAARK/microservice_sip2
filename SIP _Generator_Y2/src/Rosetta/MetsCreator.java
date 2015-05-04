/**
 * @program: MetsCreator
 * @see Class for creation of mets for Rosetta.
 * @author: Hamid Rofoogaran | LTU
 */
package Rosetta;

import au.edu.apsr.mtk.base.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
//import static javax.swing.text.html.HTML.Attribute.ID;
//import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;

//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;

         // Create separate dc.xml



public class MetsCreator {

private static METS mets = null;    
private static String pathToMetsfile;



    /**
     * @method setPathToMetsfile
     * @see sökväg till metsfil ink filnamn.
     * @param pathToMetsfile, string
     **/   
    public void setPathToMetsfile(String pathToMetsfile) {
        MetsCreator.pathToMetsfile = pathToMetsfile;
    }

    /**
     * @method metsForWebb
     * @see Metod som genererar en metsfil.
     * detta skrivs ner i DB.
     * @return boolean true/false
     */     
    public void metsForWebb()throws METSException, FileNotFoundException, SAXException, ParserConfigurationException,
    IOException, Exception {
        Integer i = 0;
        //instans
        DataBase db = new DataBase();
        Integer maxNrFiles = db.getNrOfFiles();
        System.out.println(maxNrFiles + " file(s) has been packaged") ;
        METSWrapper mw = new METSWrapper();
//        METSWrapper mw2 = new METSWrapper();
        
        db.setDbForRs();
        ResultSet rs0 = db.getDataForMets(3);
        rs0.next();
        mets = mw.getMETSObject(); 
             
//            mets.setObjID(rs0.getString(2));
//            mets.setID(rs0.getString(3));
//            mets.setProfile("http://www.loc.gov/standards/mets/profiles/00000038.xml");
//            mets.setLabel(rs0.getString(4));
//            mets.setType(rs0.getString(5));
        MetsHdr mh = mets.newMetsHdr();
		
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	String currentTime = df.format(cal.getTime());
            mh.setCreateDate(currentTime);
            mh.setLastModDate(currentTime);
 
        /*    Rosetta do not include MetsHdr
            //skapar <mets:metsHdr>
            Agent agent01 = mh.newAgent();
            agent01.setRole("CREATOR");
            agent01.setType("ORGANIZATION");
            agent01.setName(rs0.getString(6));
            mh.addAgent(agent01);
                
            Agent agent02 = mh.newAgent();
            agent02.setRole("CREATOR");
            agent02.setType("INDIVIDUAL");
            agent02.setName(rs0.getString(7));
            agent02.setNote(rs0.getString(8));
            agent02.setNote(rs0.getString(9));
            mh.addAgent(agent02);

            Agent agent03 = mh.newAgent();
            agent03.setOtherType("SOFTWARE");
            agent03.setRole("CREATOR");
            agent03.setType("OTHER");
            agent03.setName(rs0.getString(10));                
            mh.addAgent(agent03);                
                
            Agent agent04 = mh.newAgent();
            agent04.setRole("ARCHIVIST");
            agent04.setType("ORGANIZATION");
            agent04.setName(rs0.getString(11));
            agent04.setNote(rs0.getString(12));
            mh.addAgent(agent04);
                
            Agent agent05 = mh.newAgent();
            agent05.setRole("ARCHIVIST");
            agent05.setType("OTHER");
            agent05.setOtherType("SOFTWARE");
            agent05.setName(rs0.getString(13));
            mh.addAgent(agent05);
                
            Agent agent06 = mh.newAgent();
            agent06.setRole("PRESERVATION");
            agent06.setType("ORGANIZATION");
            agent06.setName(rs0.getString(14));
            agent06.setNote(rs0.getString(15));
            mh.addAgent(agent06);
               
            AltRecordID altID01 = mh.newAltRecordID();
            altID01.setType("DELIVERYTYPE");
            altID01.setValue(rs0.getString(16));
            mh.addAltRecordID(altID01);

            AltRecordID altID02 = mh.newAltRecordID();
            altID02.setType("DELIVERYSPECIFICATION");
            altID02.setValue(rs0.getString(17));
            mh.addAltRecordID(altID02);                
                
            AltRecordID altID03 = mh.newAltRecordID();
            altID03.setType("STARTDATE");
            altID03.setValue(rs0.getString(18));
            mh.addAltRecordID(altID03);
                
            AltRecordID altID04 = mh.newAltRecordID();
            altID04.setType("SUBMISSIONAGREEMENT");
            altID04.setValue(rs0.getString(19));
            mh.addAltRecordID(altID04);
                
            MetsDocumentID docID = mh.newMetsDocumentID();
            docID.setDocumentID(rs0.getString(20));
            mh.addMetsDocumentID(docID);
            mets.setMetsHdr(mh);
            db.setCloseDbForRs();
	//slut <mets:metsHdr>
        
        */    

         
        // Skapar <mets:DmdSec  
       
         DmdSec dmdsec = mets.newDmdSec();
         dmdsec.setID("ie-dmd") ;
         MdWrap mdwrap = dmdsec.newMdWrap();
         mdwrap.setMDType("DC");
        
        // mdwrap.setXmlData(createDC("Structure of a Rosetta SIP", "2014-08-26").getDocumentElement());
        mdwrap.setXmlData(createDC().getDocumentElement());
        dmdsec.setMdWrap (mdwrap);
        mets.addDmdSec(dmdsec);
        //slut <mets:DmdSec>       
         
           
        db.setDbForRs(); //Loopa AmdSec !
        ResultSet rs1 = db.getDataForMets(2);
            
        while(rs1.next()) {
         
         AmdSec amdsec = mets.newAmdSec();
         mets.addAmdSec(amdsec);     
         TechMD techmd =amdsec.newTechMD();
         amdsec.addTechMD(techmd);
         techmd.setID("techmd-1" + (i+1));
         MdWrap techwrap = techmd.newMdWrap();
         techwrap.setMDType("OTHER");
         techwrap.setOtherMDType("dnx");
         techwrap.setXmlData(createDNX().getDocumentElement());
         
         techmd.setMdWrap(techwrap);
         mets.addAmdSec(amdsec);
         
         
         RightsMD rightsmd = amdsec.newRightsMD();
         amdsec.addRightsMD(rightsmd);
         rightsmd.setID("rightsmd-1" + (i+1));
         
         SourceMD sourcemd = amdsec.newSourceMD();
         amdsec.addSourceMD(sourcemd);
         sourcemd.setID("sourcemd-1" + ( i+1));
         
         DigiprovMD digiprovmd = amdsec.newDigiprovMD();
         amdsec.addDigiprovMD(digiprovmd);
         digiprovmd.setID("digiprovmd-1" + ( i+1));
         
         mets.addAmdSec(amdsec);
         amdsec.setID("AmdSec-1" + ( i+1));
         
         
//         Create a second admsec 
         
         AmdSec amdsec2 = mets.newAmdSec();
         mets.addAmdSec(amdsec2);     
         TechMD techmd2 =amdsec2.newTechMD();
         amdsec2.addTechMD(techmd2);
         techmd2.setID("techmd-2" + (i+1));
         MdWrap techwrap2 = techmd2.newMdWrap();
         techwrap2.setMDType("OTHER");
         techwrap2.setOtherMDType("dnx");
         techwrap2.setXmlData(createDNX1().getDocumentElement());  //DNX2--> DNX1
         techmd2.setMdWrap(techwrap2);
         mets.addAmdSec(amdsec2);
         
         
         RightsMD rightsmd2 = amdsec2.newRightsMD();
         amdsec2.addRightsMD(rightsmd2);
         rightsmd2.setID("rightsmd-2" + (i+1));
         
         SourceMD sourcemd2 = amdsec2.newSourceMD();
         amdsec2.addSourceMD(sourcemd2);
         sourcemd2.setID("sourcemd-2" + (i+1));
         
         DigiprovMD digiprovmd2 = amdsec2.newDigiprovMD();
         amdsec2.addDigiprovMD(digiprovmd2);
         digiprovmd2.setID("digiprovmd-2" + (i+1));
         
         mets.addAmdSec(amdsec2);
         amdsec2.setID("AmdSec-2" + (i+1));

/*         
         
         //         Create a thired admsec 
         
         AmdSec amdsec3 = mets.newAmdSec();
         mets.addAmdSec(amdsec3);     
         TechMD techmd3 =amdsec3.newTechMD();
         amdsec3.addTechMD(techmd3);
         techmd3.setID("techmd-3" + (i+1));
         MdWrap techwrap3 = techmd3.newMdWrap();
         techwrap3.setMDType("OTHER");
         techwrap3.setOtherMDType("dnx");
         techwrap3.setXmlData(createDNX3().getDocumentElement());
         techmd3.setMdWrap(techwrap3);
         mets.addAmdSec(amdsec3);
         
         
         RightsMD rightsmd3 = amdsec3.newRightsMD();
         amdsec3.addRightsMD(rightsmd3);
         rightsmd3.setID("rightsmd-3" + (i+1));
         
         SourceMD sourcemd3 = amdsec3.newSourceMD();
         amdsec3.addSourceMD(sourcemd3);
         sourcemd3.setID("sourcemd-3" + (i+1));
         
         DigiprovMD digiprovmd3 = amdsec3.newDigiprovMD();
         amdsec3.addDigiprovMD(digiprovmd3);
         digiprovmd3.setID("digiprov-3" + (i+1));
         
         mets.addAmdSec(amdsec3);
         amdsec3.setID("AmdSec-3" + (i+1));
         
         i=i+1;
 //      }
      
       db.setCloseDbForRs();       */
        
        
       
 
 //       skapar <mets:filsec>		
	FileSec fs = mets.newFileSec();
	FileGrp fg = fs.newFileGrp();
        fg.setID("fgrp1");
        fg.setAdmID("AdmID");
        


        db.setDbForRs();
        ResultSet rs2 = db.getDataForMets(2);

//        String ifce57 = rs2.getString(11);
        File f = null;
        FLocat fl = null;
        //file values
        while(rs2.next()){
            f = fg.newFile();
            f.setAmdID("AdmID");
//            f.setID(rs2.getString(2)); 
            f.setMIMEType(rs2.getString(8));
            
            f.setID("FID" + i);           
//            f.setUse(rs2.getString(8)+";"+rs2.getString(10)+";"+rs2.getString(11));
//            f.setUse(rs2.getString(11));


            fl = f.newFLocat();
            fl.setLocType("URL");
//            fl.setHref(rs2.getString(4));

            fl.setType("simple");
            //skriver
            f.addFLocat(fl);
            fg.addFile(f);
 //           rs2.next();            
            i= i+1;
            
       }    //slut <mets:filsec>	
        
             //generisk skrivning
        fs.addFileGrp(fg);
        mets.setFileSec(fs);
        //stänger db
        db.setCloseDbForRs();       
        
        
        
        
        
////
////        //skapar <mets:filsec>	
////       
//	FileSec fs = mets.newFileSec();
//	FileGrp fg = fs.newFileGrp();
//        fg.setID("rep1");
//        fg.setUse("FILES");  
//        fg.setAdmID("rep1-amd");
//        db.setDbForRs();
//        ResultSet rs2 = db.getDataForMets(2);
//            
//        File f = null;
//        FLocat fl = null;
//        //file values
//        while(rs2.next()){
//
//            f = fg.newFile();
//            f.setID("fid1-1");          
//            f.setMIMEType("");
//            f.setAmdID("rep1"); 
////           
////            f.setChecksum(rs2.getString(6));
////            f.setChecksumType(rs2.getString(5));
//              f.setChecksumType("MD5");              
////            f.setCreated(currentTime);
////            f.setUse(rs2.getString(8)+";"+rs2.getString(10)+";"+rs2.getString(11));
////            f.setSize(Integer.parseInt(rs2.getString(7)));
////
////
////                
//            fl = f.newFLocat();
//            fl.setLocType("URL");
//            fl.setHref("Link");     //(rs2.getString(4));
//            fl.setType("simple");
//            f.addFLocat(fl);
//            fg.addFile(f);
//            i= i+1;
//
////            
//       }   //slut <mets:filsec>	                
//             //generisk skrivning
//        
//       
//        fs.addFileGrp(fg);
//        mets.setFileSec(fs);
//       //stänger db
//        db.setCloseDbForRs();     
//        
    
     /*   
    //  eARD structMap   
        
        //skapar <mets:structmap>
        StructMap sm = mets.newStructMap();
        mets.addStructMap(sm);
	//sm.setLabel("FILES");
        Div div = sm.newDiv();
            div.setLabel("FILES");
            sm.addDiv(div);                                                    
        db.setDbForRs();
        ResultSet rs2 = db.getDataForMets(1);    
        
        i=0;
        while(rs2.next()){
            Fptr fp001 = div.newFptr();
            fp001.setFileID(rs2.getString(1));
            div.addFptr(fp001);
            i= i+1;
                      
        }//end while
        db.setCloseDbForRs();
        //slut <mets:structmap>
                               
      // eARD structMap */     
            
       
        //skapar <mets:structmap> for Rosetta
        StructMap sm = mets.newStructMap();
        mets.addStructMap(sm);
	sm.setID("rep1-1");
        sm.setType("PHYSICAL");
        Div div = sm.newDiv();
            div.setLabel("PRESERVATION_MASTER;VIEW");
            sm.addDiv(div);  
            Div div2 = div.newDiv();
            div2.setLabel("Table of Contents");
            div.addDiv(div2);
            Div div3 = div2.newDiv();
            div3.setLabel("Rosetta SIP");
            div2.addDiv(div3);
            

    i=i+1;                    
        }          //end while
 
        //slut eArd:structmap    
        
              
        //skriver till fil
        OutputStream outToFile = new FileOutputStream(pathToMetsfile);
//        System.out.print("pathToMetsfile = " + pathToMetsfile );
//        //mw.write(System.out);
//       
//       
        mw.write(outToFile);

        outToFile.close();
        //rensa db
        db.truncTable("mets");
        db.truncTable("droidinfo");    
        
        
        
    }//end metsforwebb
    
    
     private static Document createDC() throws ParserConfigurationException, IOException, Exception
    {
        
        DataBase dbx = new DataBase();
        dbx.setDbForRs();
        ResultSet rs5 = dbx.getDataForMets(3);
        rs5.next();  
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
//        Element root = doc.createElementNS("http://purl.org/dc/elements/1.1/", "dc:record");
//        doc.appendChild(root);
        
        Element rec = doc.createElementNS("http://purl.org/dc/elements/1.1/", "dc:record");
        doc.appendChild(rec);
        
//        Element rec = doc.createElement("record");
        rec.setTextContent("xmlns:dc=http://purl.org/dc/elements/1.1/ xmlns:dcterms=http://purl.org/dc/terms/ xmlns:xsi= http://www.w3.org/2001/XMLSchema-instance");
//        root.appendChild(rec);
        
        Element i = doc.createElement("dc:identifier");
        i.setTextContent(rs5.getString(31));
        i.setAttribute("type", "digital");
        rec.appendChild(i);
//        root.appendChild(rec);
        
        Element i2 = doc.createElement("dc:identifier");
        i2.setTextContent(rs5.getString(9));
        i2.setAttribute("type", "physical");
        rec.appendChild(i2);
        
        
        Element c = doc.createElement("dc:creator");
        c.setTextContent(rs5.getString(23));
        c.setAttribute( "type", "physical");
        rec.appendChild(c);
        
        Element c2 = doc.createElement("dc:creator");
        c2.setTextContent(rs5.getString(2));
        c2.setAttribute( "type", "digital");
        rec.appendChild(c2);
//        root.appendChild(rec);
        
        Element t = doc.createElement("dc:title");
        t.setTextContent(rs5.getString(3));
        t.setAttribute( "type", "digital");
        rec.appendChild(t);
        
        Element t2 = doc.createElement("dc:title");
        t2.setTextContent(rs5.getString(5));
        t2.setAttribute( "type", "physical");
        rec.appendChild(t2);
        
 //       root.appendChild(rec);
        
        Element d = doc.createElement("dc:date");  
        d.setTextContent(rs5.getString(4));
        d.setAttribute("type", "created");
        rec.appendChild(d);
        

        Element r = doc.createElement("dc:relation");  
        r.setTextContent(rs5.getString(22));
        rec.appendChild(r);
        
        Element desc = doc.createElement("dc:description");  
        desc.setTextContent(rs5.getString(12));
        desc.setAttribute("type", "digital");
        rec.appendChild(desc);
 
        
        Element desc2 = doc.createElement("dc:description");  
        desc2.setTextContent(rs5.getString(30));
        desc2.setAttribute("type", "physical");
        rec.appendChild(desc2);
        
        
        Element f = doc.createElement("dc:format");  
        f.setTextContent(rs5.getString(10));
        rec.appendChild(f);
        
        
        Element type = doc.createElement("dc:type");  
        type.setTextContent(rs5.getString(11));
        rec.appendChild(type);
        
        
        Element rts = doc.createElement("dc:rights");  
        rts.setTextContent(rs5.getString(22));
        rts.setAttribute("type", "digital");
        rec.appendChild(rts);
        
        Element rts2 = doc.createElement("dc:rights");  
        rts2.setTextContent(rs5.getString(29));
        rts2.setAttribute("type", "physical");
        rec.appendChild(rts2);        
        
        Element cov1 = doc.createElement("dc:coverage");  
        cov1.setTextContent(rs5.getString(6));
        cov1.setAttribute("type", "location");
        rec.appendChild(cov1);  
        
        Element cov2 = doc.createElement("dc:coverage");  
        cov2.setTextContent(rs5.getString(7));
        cov2.setAttribute("type", "latitude");
        rec.appendChild(cov2);       
        
        Element cov3 = doc.createElement("dc:coverage");  
        cov3.setTextContent(rs5.getString(8));
        cov3.setAttribute("type", "longitude");
        rec.appendChild(cov3);   
        
        Element con = doc.createElement("dc:contributer");  
        con.setTextContent(rs5.getString(24));
        con.setAttribute("type", "loation");
        rec.appendChild(con);   
        
        Element date1 = doc.createElement("dc:date");  
        date1.setTextContent(rs5.getString(26));
        date1.setAttribute("type", "completionDate");
        rec.appendChild(date1);  
        
        Element date2 = doc.createElement("dc:date");  
        date2.setTextContent(rs5.getString(26));
        date2.setAttribute("type", "startDate");
        rec.appendChild(date2);               
        
        Element date3 = doc.createElement("dc:date");  
        date3.setTextContent(rs5.getString(27));
        date3.setAttribute("type", "constructionTime");
        rec.appendChild(date3);  
        
        Element date4 = doc.createElement("dc:date");  
        date4.setTextContent(rs5.getString(29));
        date4.setAttribute("type", "rebuildingDate");
        rec.appendChild(date4);                       
        
//        Element d1 = doc.createElement("dc:date of capture");
//        d1.setTextContent("rs5.getString(6)");
//        rec.appendChild(d1);
//        root.appendChild(rec);
//        dbx.truncTable("rdf");
         dbx.setCloseDbForRs();    

        return doc;

    }
     
     
     private static Document createDNX() throws ParserConfigurationException, IOException, Exception
    {
        
        DataBase dbx = new DataBase();
        dbx.setDbForRs();
        ResultSet rs5 = dbx.getDataForMets(3);
        rs5.next();  
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElementNS("http://purl.org/dnx/elements/1.1/", "dnx");
        doc.appendChild(root);
        
//      Element sec = doc.createElement("section");
        Element secExt = doc.createElement("section");
        secExt.setAttribute("id", "objectCharacteristicsExtension");  
        root.appendChild(secExt);        
//        sec.setAttribute("id", "generalFileCharacteristics");

//        root.appendChild(sec);

//e57m embedded 
//        String filem = rs5.getString(7); 

//        if (filem=="100"){
         
        
        Element e57 = doc.createElement("e57m");
        secExt.appendChild(e57);
        root.appendChild(secExt);
        
        Element e57root = doc.createElement("E57root");
        e57root.setTextContent(rs5.getString(45));
        e57.appendChild(e57root);
        secExt.appendChild(e57);
        root.appendChild(secExt);
//        }
 //e57m embedded     
        
//        Element rec = doc.createElement("record");
//        rec.setTextContent("xmlns:dc=http://purl.org/dc/elements/1.1/ xmlns:dcterms=http://purl.org/dc/terms/ xmlns:xsi= http://www.w3.org/2001/XMLSchema-instance");
//        sec.appendChild(rec);
//        root.appendChild(sec);

//        
//        Element k1 = doc.createElement("key");
////      t.setTextContent(rs5.getString(4));
////      t.setAttributeNS("id", "preservationType", "PRESERVATION_MASTER");
//        k1.setAttribute("preservationType", "PRESERVATION_MASTER");
//        rec.appendChild(k1);
//        sec.appendChild(rec);
//        
//        Element k2 = doc.createElement("key");
//        k2.setAttribute("usageType", "VIEW");
//        rec.appendChild(k2);
//        sec.appendChild(rec);
//        
//        Element k3 = doc.createElement("key");
//        k3.setAttribute("RevisionsNumber", "1");
//        rec.appendChild(k3);
//        sec.appendChild(rec);
//        
//        Element k4 = doc.createElement("key");
//        k4.setAttribute("label", "LOTS OF TEXT");
//        rec.appendChild(k4);
//        sec.appendChild(rec);
//       //dbx.truncTable("manuellinfo");       
          dbx.setCloseDbForRs(); 
        return doc;
    }
      
    
     
 private static Document createDNX1() throws ParserConfigurationException, IOException, Exception
    {
        
        DataBase dbx = new DataBase();
        dbx.setDbForRs();
        ResultSet rs5 = dbx.getDataForMets(3);
        rs5.next();  
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElementNS("http://purl.org/dnx/elements/1.1/", "dnx");
        doc.appendChild(root);
        
//      Element sec = doc.createElement("section");
        Element secExt = doc.createElement("section");
        secExt.setAttribute("id", "objectCharacteristicsExtension");  
        root.appendChild(secExt);        
//        sec.setAttribute("id", "generalFileCharacteristics");

//        root.appendChild(sec);

//ifcm embedded 
//        String filem = rs5.getString(7); 

//        if (filem=="100"){
         
        
        Element ifc = doc.createElement("ifcm");
        secExt.appendChild(ifc);
        root.appendChild(secExt);
        
        Element ifcroot = doc.createElement("IFCroot");
        ifcroot.setTextContent(rs5.getString(46));
        ifc.appendChild(ifcroot);
        secExt.appendChild(ifc);
        root.appendChild(secExt);
//        }
 //ifcm embedded     
        
//        Element rec = doc.createElement("record");
//        rec.setTextContent("xmlns:dc=http://purl.org/dc/elements/1.1/ xmlns:dcterms=http://purl.org/dc/terms/ xmlns:xsi= http://www.w3.org/2001/XMLSchema-instance");
//        sec.appendChild(rec);
//        root.appendChild(sec);

//        
//        Element k1 = doc.createElement("key");
////      t.setTextContent(rs5.getString(4));
////      t.setAttributeNS("id", "preservationType", "PRESERVATION_MASTER");
//        k1.setAttribute("preservationType", "PRESERVATION_MASTER");
//        rec.appendChild(k1);
//        sec.appendChild(rec);
//        
//        Element k2 = doc.createElement("key");
//        k2.setAttribute("usageType", "VIEW");
//        rec.appendChild(k2);
//        sec.appendChild(rec);
//        
//        Element k3 = doc.createElement("key");
//        k3.setAttribute("RevisionsNumber", "1");
//        rec.appendChild(k3);
//        sec.appendChild(rec);
//        
//        Element k4 = doc.createElement("key");
//        k4.setAttribute("label", "LOTS OF TEXT");
//        rec.appendChild(k4);
//        sec.appendChild(rec);
//       //dbx.truncTable("manuellinfo");       
          dbx.setCloseDbForRs(); 
        return doc;
    }
     
     
     
     
     private static Document createDNX2() throws ParserConfigurationException, IOException, Exception
    {
        
        DataBase dbx = new DataBase();
        dbx.setDbForRs();
        ResultSet rs5 = dbx.getDataForMets(3);
        rs5.next();  
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElementNS("http://purl.org/dnx/elements/1.1/", "dnx");
        doc.appendChild(root);
        
        Element sec = doc.createElement("section");
        sec.setAttribute("id", "generalFileCharacteristics");
        root.appendChild(sec);
        sec.setAttribute("id", "fileFixity");
        root.appendChild(sec);
        
        Element rec = doc.createElement("record");
//        rec.setTextContent("xmlns:dc=http://purl.org/dc/elements/1.1/ xmlns:dcterms=http://purl.org/dc/terms/ xmlns:xsi= http://www.w3.org/2001/XMLSchema-instance");
        sec.appendChild(rec);
        root.appendChild(sec);

        
        Element k1 = doc.createElement("key");
//      t.setTextContent(rs5.getString(4));
//      t.setAttributeNS("id", "preservationType", "PRESERVATION_MASTER");
        k1.setAttribute("preservationType", "PRESERVATION_MASTER");
        rec.appendChild(k1);
        sec.appendChild(rec);
        
        Element k2 = doc.createElement("key");
        k2.setAttribute("usageType", "VIEW");
        rec.appendChild(k2);
        sec.appendChild(rec);
        
        Element k3 = doc.createElement("key");
        k3.setAttribute("RevisionsNumber", "1");
        rec.appendChild(k3);
        sec.appendChild(rec);
        
        Element k4 = doc.createElement("key");
        k4.setAttribute("label", "LOTS OF TEXT");
        rec.appendChild(k4);
        sec.appendChild(rec);
        
       //dbx.truncTable("manuellinfo");
        return doc;
    }
     
     
     
     private static Document createDNX3() throws ParserConfigurationException, IOException, Exception
    {
        
        DataBase dbx = new DataBase();
        dbx.setDbForRs();
        ResultSet rs5 = dbx.getDataForMets(3);
        rs5.next();  
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElementNS("http://purl.org/dnx/elements/1.1/", "dnx");
        doc.appendChild(root);
        
        Element sec = doc.createElement("section");
        sec.setAttribute("id", "generalFileCharacteristics");
        root.appendChild(sec);
        sec.setAttribute("id", "fileFixity");
        root.appendChild(sec);
        
        Element rec = doc.createElement("record");
//        rec.setTextContent("xmlns:dc=http://purl.org/dc/elements/1.1/ xmlns:dcterms=http://purl.org/dc/terms/ xmlns:xsi= http://www.w3.org/2001/XMLSchema-instance");
        sec.appendChild(rec);
        root.appendChild(sec);

        
        Element k1 = doc.createElement("key");
//      t.setTextContent(rs5.getString(4));
//      t.setAttributeNS("id", "preservationType", "PRESERVATION_MASTER");
        k1.setAttribute("preservationType", "PRESERVATION_MASTER");
        rec.appendChild(k1);
        sec.appendChild(rec);
        
        Element k2 = doc.createElement("key");
        k2.setAttribute("usageType", "VIEW");
        rec.appendChild(k2);
        sec.appendChild(rec);
        
        Element k3 = doc.createElement("key");
        k3.setAttribute("RevisionsNumber", "1");
        rec.appendChild(k3);
        sec.appendChild(rec);
        
        Element k4 = doc.createElement("key");
        k4.setAttribute("label", "LOTS OF TEXT");
        rec.appendChild(k4);
        sec.appendChild(rec);
        
       //dbx.truncTable("manuellinfo");
        return doc;
    }
     
     
     
}//end klass

