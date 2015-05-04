/**
 * @program: eARDsip
 * @see Mjukvara som skall generera en eARD SIP.
 * @author: Goran Lindqvist LDB-centrum
 */
package Rosetta;
import java.io.*;
import java.sql.ResultSet;


/* Start huvudklass*/
public class ROSETTAsip {

    protected static String hashsumType;
    protected String filepath;
    protected static String pathConfig = "/home/saleh/duraark/run/rosetta-config.xml";
//    protected static String pathConfig = "/home/user1/duraark/run/rosetta-config.xml";
    protected static String dbFullpath;
    protected static String dbXlinkPath;
    protected static String dbChecksum;
    protected static String dbMime;
    protected static String dbSize;
    protected static String dbUse;
    protected static String dbDate;
    protected static String dbFileID;
    protected static String dbPaketuid;
    static String fid ;
          
    /**
     * @method main
     * @see main metod som kör övriga klasser,
     * @return void
     */
    public static void main(String[] args) {
        System.out.println("***************************************");
        System.out.println("------SIP Generation for DURAARK------");
        System.out.println("          ----- LTU ------");
        System.out.println("***************************************");
        System.out.println("***************************************");
        System.out.println();
        
        String katalog = null;        
        //Instans
        Common useCom = new Common();
        HashSum genUID = new HashSum(null, null);
        ReadWriteXml rwXML = new ReadWriteXml();
        AnalyzeFiles AF = new AnalyzeFiles();
        Droid droid = new Droid();
        DataBase db = new DataBase();
  

        MetsCreator mets = new MetsCreator();
 
        CreateTarFile CTF = new CreateTarFile();
        try{        
        //uid paket
        db.setPathConfig(pathConfig);    
        dbPaketuid = "UUID:"+ db.readUidToTxt();
        rwXML.writeConfigfile(pathConfig, "uid", dbPaketuid);
        Integer i = 1;
        System.out.println("**Start SIP-GEN**");
        // tömmer tbl
        db.setPathConfig(pathConfig);
        db.truncTable("mets");
        db.truncTable("droidinfo"); 
        while(i<4){
            switch(i){
                case 1: katalog = rwXML.readConfigfile(pathConfig, "katContent");
                break;
                case 2: katalog = rwXML.readConfigfile(pathConfig, "katMetadata");
                break;
                case 3: katalog = rwXML.readConfigfile(pathConfig, "katSystem");
                break;    
            }
            
            //skapa md5 (content)
            genUID.setCatalog(katalog);
            genUID.setOutFile(rwXML.readConfigfile(pathConfig, "utFilContent"));
            hashsumType = genUID.setAlgoritm(rwXML.readConfigfile(pathConfig, "hashSumType"));
            genUID.createHashsum();
            //gå till metod           
            hashAndPath();
            i= i+1;
            //tar bort hashfil
            useCom.deleteFile(rwXML.readConfigfile(pathConfig, "utFilContent"));
            
        } //end while
        
        // Creates separate dc.xml
        //CreateIndDC.main(args);
    
        //metod droida filer
        AF.setPathCatalog(rwXML.readConfigfile(pathConfig, "katalogOverSubkat"));
        AF.setDroidList(rwXML.readConfigfile(pathConfig, "droidInputFil"));
        AF.createFileListForDroid();
        //startar droid och kör
        String pathDroid = rwXML.readConfigfile(pathConfig, "pathDroidJar");
        String argA = rwXML.readConfigfile(pathConfig, "droidInputFil");
        String argS = rwXML.readConfigfile(pathConfig, "argSignaturfil");
        String argO = rwXML.readConfigfile(pathConfig, "argUtskriftfil");
        StartDroid.main(pathDroid, argA, argS, argO);
        //tar bort inputfil för droid
        useCom.deleteFile(rwXML.readConfigfile(pathConfig, "droidInputFil"));
       // Thread.sleep(5000);
        //Droid
        droid.setPathConfig(pathConfig);
        droid.setDroidOutputXml(rwXML.readConfigfile(pathConfig, "argUtskriftfil")+".xml");
        droid.droidOutputXmlToDB();
        //tar bort fil
        useCom.deleteFile(rwXML.readConfigfile(pathConfig, "argUtskriftfil")+".xml");
        //skapa mets
        mets.setPathToMetsfile(rwXML.readConfigfile(pathConfig, "utFilMets"));
        mets.metsForWebb();
        
               
        //Tar filer till paket
        CTF.setInputFileCatalog(rwXML.readConfigfile(pathConfig, "katTarStart"));
        CTF.setPathTarBall(rwXML.readConfigfile(pathConfig, "katTarPaket"),rwXML.readConfigfile(pathConfig,"uid"));
        CTF.tarFiles();
        //rensar tbl  // *** HR *** 
        //db.truncTable("rdf");
        //tömmer filer
        useCom.deleteFile(rwXML.readConfigfile(pathConfig, "utFilMets"));
        //*** HR ***
        useCom.deleteFile(rwXML.readConfigfile(pathConfig, "dcXml"));
       //*** HR ***
        useCom.delAllFilesCatalog(rwXML.readConfigfile(pathConfig, "katContent")); 
        useCom.delAllFilesCatalog(rwXML.readConfigfile(pathConfig, "katMetadata"));
        useCom.delAllFilesCatalog(rwXML.readConfigfile(pathConfig, "katSystem"));
        }catch(Exception e){
          /*  db.truncTable("manuellinfo"); */
            System.out.println("error:SIP_GEN:main... " +e.toString());
        }
        System.out.println("\n**SIP-GEN Completed**");
        
    } //end main
    
  
    
    /**
     * @method hashAndParh
     * @see Går igenom filer i katalog och tar ut information om dessa.
     * detta skrivs ner i DB.
     * @return boolean true/false
     */    
    private static boolean hashAndPath(){
        
        try{
            Integer raknare = 0;
            //Instans
            Common useCom = new Common();
            ReadWriteXml rwXml = new ReadWriteXml();
            FileValues fv = new FileValues();
            HashSum genFileUid = new HashSum(null, null);
            DataBase writeDB = new DataBase();
            
            String pathToFile = rwXml.readConfigfile(pathConfig, "utFilContent");
            File openFile = new File(pathToFile);
            FileReader ReadToVektor = new FileReader(pathToFile);
            BufferedReader iBuffer = new BufferedReader(ReadToVektor);
            boolean eof = false;
           
            //kontrollerar om fil finnes
            if(openFile.exists()) {
                //System.out.print("Antal filer: ");
              //räknar antal rader i fil
              while(!eof){
                String line = iBuffer.readLine();
                if(line == null)
                    eof = true;
                else {  
                        raknare = raknare + 1;
                        //System.out.print(", ");
                        //System.out.print(raknare);
                        Integer i  = line.indexOf(" ");
                        String s1 = line;
                        dbChecksum= s1.substring(0, i);
                        //System.out.println(s1);
                        String path = line.substring(line.indexOf(" "));
                        path = path.trim();
                        //skapar filuid
                        dbFileID = genFileUid.generateFileUID();
//                        dbFileID = "fid" + dbFileID ;
//                        dbFileID = dbFileID.substring(0,2);
//                        dbFileID = "fid";
//                        fid = new String ("fid1");
                        //System.out.println(dbFileID);
                        dbFullpath = path;
                        //System.out.println("fullPath: " + path);
                        //fildata
                        fv.fileData(path);
                        dbXlinkPath = fv.getFilename();
                        dbMime = fv.getMimevalue();
                        dbSize = fv.getMB();
                        dbUse = fv.getFileversion();
                        //String s1;
                        //datumTid till db
                        dbDate = useCom.dateAndTime();
                        //till db
                        writeDB.setPathConfig(pathConfig);
                        writeDB.writeToMetsDB(dbPaketuid, dbFileID, dbXlinkPath, dbDate, dbMime,
                                          dbUse, dbSize, dbChecksum, hashsumType, dbFullpath);
                        
//                        System.out.println("Raknare: " + raknare + "\n" +
//                                           "paketuid: " + dbPaketuid + "\n" +
//                                           "fileID: " + dbFileID + "\n" +
//                                           "checksum: " + dbChecksum + "\n" +
//                                           "datum: " + dbDate +"\n" +
//                                           "mime: " + dbMime + "\n" +
//                                           "Size: " + dbSize + "\n" +
//                                           "USE: " + dbUse + "\n" +
//                                           "XlinkPath: " + dbXlinkPath +"\n" +
//                                           "FullPath: " + dbFullpath +"\n");                        
                            
                } //else
              }//while            
            }            
        }catch(Exception e){
            System.out.println("error:SIP-GEN:HashAndPath... " + e.toString());
            return false;
        }
        return true;
    }    
} //end klass
