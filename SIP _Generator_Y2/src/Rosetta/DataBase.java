/**
 * @program: DataBase
 * @see klass som skriver och hämtar info från MySQL DB.
 * @author: Goran Lindqvist LDB-centrum | LTU
 */
package Rosetta;
import java.sql.*;
import javax.sound.midi.SysexMessage;
/**
 *
 */
public class DataBase {

    //klassvariabler
    private static String pathConfig;
    private static String L_url;
    private static String L_user;
    private static String L_pw;
    private static String L_jdbc;
    private static Connection L_con;
    private static Statement L_stmt;
    protected ResultSet rSet;
    
    //koppling till configfil
    public void setPathConfig(String pathConfig) {
        this.pathConfig = pathConfig;
    }
          
    /**
     * @method writeToMetsDB
     * @see En metod skriver ner data (tbl:mets) till sip_db DB (mySQL).
     * @return boolean true/false
     */
    public boolean writeToMetsDB(String paketUid, String filUid, String filNamn, String filDatum,
                             String Mime, String Version, String Byte, String hashSum,
                             String hashsumType, String filePath){
    try{
        //connect db
        ReadWriteXml rwXML = new ReadWriteXml();
        L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
        L_url = rwXML.readConfigfile(pathConfig, "url01");
        L_user = rwXML.readConfigfile(pathConfig, "user01");
        L_pw = rwXML.readConfigfile(pathConfig, "pw01");

        //System.out.println("start");
        //reg JDBC
        Class.forName(L_jdbc);
        //conn DB
        Connection con = DriverManager.getConnection(L_url, L_user,L_pw);
        //få statement
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO mets(paketuid,filuid,filnamn,fildatum,mime,version,filbyte,hashsum,hashsumtype,path)" +
                    "VALUES ('"+paketUid+"','"+filUid+"','"+filNamn+"','"+filDatum+"','"+Mime+"'"
                    + ",'"+Version+"','"+Byte+"','"+hashSum+"','"+hashsumType+"','"+filePath+"')";
        
//        String sql = "INSERT INTO mets(paketuid,filuid,filnamn,fildatum,mime,version,filbyte,hashsum,hashsumtype,path)" +
//                    "VALUES ('"+paketUid+"','"+" "+"','"+filNamn+"','"+filDatum+"','"+Mime+"'"
//                    + ",'"+Version+"','"+Byte+"','"+hashSum+"','"+hashsumType+"','"+filePath+"')";  
        
        
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
       }catch(Exception e){
            System.out.println("error:DataBase:writeToMetsDB... "+ e.getMessage());
            return false;
       }
        return true;
    }//end writeMetsDB     

    /**
     * @method writeToPaketinfoDB
     * @see En metod skriver ner data (tbl:paketinfo) till sip_db DB (mySQL).
     * @return boolean true/false
     **/
    public boolean writeToPaketinfoDB(String paketBesk, String paketDatum, String levOrg, String kontaktNamn,
                                   String kontaktTele, String kontaktMail, String sipMjukvara, String arkivSkaparNamn,
                                   String arkivSkaparOrgnr, String levSysNamn, String bevOrgNamn, String bevOrgID,
                                   String levTyp, String levSpec, String levDatum, String levOverKommelse, 
                                   String dokumentId, String paketUID){
        
        try{
            //connect db
            ReadWriteXml rwXML = new ReadWriteXml();
            L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
            L_url = rwXML.readConfigfile(pathConfig, "url01");
            L_user = rwXML.readConfigfile(pathConfig, "user01");
            L_pw = rwXML.readConfigfile(pathConfig, "pw01");
            //reg jdbc
            Class.forName(L_jdbc);
            //con DB
            L_url = L_url+"?useUnicode=true&characterEncoding=UTF-8";
            Connection con = DriverManager.getConnection(L_url,L_user,L_pw);
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO paketinfo(paketbeskrivning, paketdatum, levererandeorg, kontaktnamn,"
                       + "kontakttelefon, kontaktmail, sipmjukvara, arkivskaparnamn, arkivskaparorgnr, "
                       + "levsystemnamn, bevorgnamn, bevorgid, leveranstyp, leveransspec, leveransdatum, "
                       + "levoverkommelse, dokumentid, paketuid)"+
                    "VALUES('"+paketBesk+"','"+paketDatum+"','"+levOrg+"','"+kontaktNamn+"','"+kontaktTele+"',"
                         + "'"+kontaktMail+"','"+sipMjukvara+"','"+arkivSkaparNamn+"','"+arkivSkaparOrgnr+"',"
                         + "'"+levSysNamn+"','"+bevOrgNamn+"','"+bevOrgID+"','"+levTyp+"','"+levSpec+"','"+levDatum+"',"
                         + "'"+levOverKommelse+"','"+dokumentId+"','"+paketUID+"')";
        
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();           
        }catch(Exception e){
            System.out.println("error:DataBase:writeToPaketinfoDB... " + e.getMessage());
            return false;
        }
        return true;
    }//end writeTopaketinfoDB

    /**
     * @method writeToDroidInfoDB
     * @see En metod skriver ner data från DROID till sip_db DB (mySQL).
     * @return boolean true/false
     **/    
    public boolean writeToDroidInfoDB(String paketUID, String filePath, String Status, String Name,
                                      String Version, String PUID, String mimeValue, String idWarning){
        try{
             //connect db
            ReadWriteXml rwXML = new ReadWriteXml();
            L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
            L_url = rwXML.readConfigfile(pathConfig, "url01");
            L_user = rwXML.readConfigfile(pathConfig, "user01");
            L_pw = rwXML.readConfigfile(pathConfig, "pw01");
        
            //reg jdbc
            Class.forName(L_jdbc);
            //con DB
            Connection con = DriverManager.getConnection(L_url,L_user,L_pw);
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO droidinfo(paketuid, filepath, status, name, version, puid, mimevalue,"
                       + "idwarning)"+
                       "VALUES('"+paketUID+"','"+filePath+"','"+Status+"','"+Name+"','"+Version+"','"+PUID+"',"
                       + "'"+mimeValue+"','"+idWarning+"')";
        
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        
        }catch(Exception e){
            System.out.println("error:DataBase:writeToDroidInfoDB... " + e.getMessage());
            return false;
        }
        return true;
    }//end writeToDroidInfoDB

    /**
     * @method getNrOfFiles
     * @see En metod räknar antalet poster i tbl:mets.
     * @return Integer: antalet poster i tbl.
     **/        
    public Integer getNrOfFiles(){
    
        try{
             //connect db
            ReadWriteXml rwXML = new ReadWriteXml();
            L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
            L_url = rwXML.readConfigfile(pathConfig, "url01");
            L_user = rwXML.readConfigfile(pathConfig, "user01");
            L_pw = rwXML.readConfigfile(pathConfig, "pw01");
        
             //reg jdbc
            Class.forName(L_jdbc);
            //con DB
            Connection con = DriverManager.getConnection(L_url,L_user,L_pw);
            Statement stmt = con.createStatement();
            ResultSet rs;
            String sql ="SELECT COUNT(filuid) FROM mets";
            rs = stmt.executeQuery(sql);
            rs.next();
            Integer nrOfFiles = rs.getInt(1);
            //stänger
            rs.close();
            stmt.close();
            con.close();
            return nrOfFiles;    
        }catch(Exception e){
            System.out.println("error:DataBase:getNrOfFiles... " + e.getMessage());
            return 0;
        }
    }//end getnroffiles    

    /**
     * @method setDbForRs
     * @see En metod som skapar DB koppling, anv för RS.
     * @return boolean true/false
     */
    public boolean setDbForRs(){
        try{
            //connect db
            ReadWriteXml rwXML = new ReadWriteXml();
            L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
            L_url = rwXML.readConfigfile(pathConfig, "url01");
            L_user = rwXML.readConfigfile(pathConfig, "user01");
            L_pw = rwXML.readConfigfile(pathConfig, "pw01");

            //System.out.println("start");
            //reg JDBC
            Class.forName(L_jdbc);
        //conn DB
        L_con = DriverManager.getConnection(L_url, L_user,L_pw);        
        //få statement
        L_stmt = L_con.createStatement();
        return true;
        }catch(Exception e){
            System.out.println("error:DataBase:setDbForRs... " + e.getMessage());
            return false;
        }                   
    }//end setDb    

    /**
     * @method getDataForMets
     * @see En metod som hämtar data från databas
     * @param integer, för val av sql sträng.
     * @return ResultSet
     */    
    public ResultSet getDataForMets(Integer selectSQL) throws Exception{
        
        try{
            L_stmt = L_con.createStatement();
            ResultSet rs;
            String sql = null;
            switch(selectSQL){
                case 1: sql = "SELECT filuid FROM mets";
                    break;
                case 2: sql = "SELECT mets.paketuid, mets.filuid, mets.fildatum, mets.filnamn ,mets.hashsumtype,"+
                        "mets.hashsum, mets.filbyte, droidinfo.`name`,droidinfo.mimevalue, droidinfo.version," +
                        "droidinfo.puid,droidinfo.idwarning FROM droidinfo "+
                        "INNER JOIN mets ON droidinfo.filepath=mets.`path`";
                    break;
                case 3: sql = "SELECT * FROM rdf";
                    break;
                case 4: sql = "";
                    break;
                case 5: sql = "";
                    break;                    
            }//end select
            rs = L_stmt.executeQuery(sql);
            return rs;
        }catch(Exception e){
            System.out.println("error:DataBase:getDataForMets... " + e.getMessage());
            return null;
        }
    }//end getdataformets

    /**
     * @method setCloseDbForRs
     * @see En metod stänger databas, anv för RS
     * @return void
     */
    public void setCloseDbForRs() throws Exception{
        L_stmt.close();
        L_con.close();
    }
    
    /**
     * 
     */
    public String readUidToTxt(){
        
        try{
             //connect db
            ReadWriteXml rwXML = new ReadWriteXml();
            L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
            L_url = rwXML.readConfigfile(pathConfig, "url01");
            L_user = rwXML.readConfigfile(pathConfig, "user01");
            L_pw = rwXML.readConfigfile(pathConfig, "pw01");
        
             //reg jdbc
            Class.forName(L_jdbc);
            //con DB
            Connection con = DriverManager.getConnection(L_url,L_user,L_pw);
            Statement stmt = con.createStatement();
            ResultSet rs;
            String sql ="SELECT paketuid FROM rdf";
            rs = stmt.executeQuery(sql);
            rs.next();
            String uid = rs.getObject(1).toString();
            //stänger
            rs.close();
            stmt.close();
            con.close();
            return uid;
        }catch(Exception e){
            System.out.println("error:DataBase:readUidToTxt... " + e.getMessage());
            return "error";
        }    
        
        
        
        
    }
    
    /**
     * @method truncTable
     * @see En metod som tömmer tbl i mySQL DB.
     * @return boolean true/false
     **/     
    public boolean truncTable(String tblName){
        try{
            //connect db
            ReadWriteXml rwXML = new ReadWriteXml();
            L_jdbc = rwXML.readConfigfile(pathConfig, "jdbcreg01");
            L_url = rwXML.readConfigfile(pathConfig, "url01");
            L_user = rwXML.readConfigfile(pathConfig, "user01");
            L_pw = rwXML.readConfigfile(pathConfig, "pw01");
        
            //reg jdbc
            Class.forName(L_jdbc);
            //con DB
            Connection con = DriverManager.getConnection(L_url,L_user,L_pw);
            Statement stmt = con.createStatement();            
            String sql = "TRUNCATE TABLE " +tblName;
            stmt.execute(sql);
            stmt.close();
            con.close();
        }catch(Exception e){
            System.out.println("error:dataBase:truncTable... " +e.getMessage());
            return false;
        }
        return true;
    }        
}