package BagIT;

/**
 * @program: hashMD5
 * @see klass som skapar hashsummor för filer i en katalog
 * @see genererar en textfil med sökväg samt hashsumma (md5)
 * @see Skall användas i eARD
 * @author: Goran Lindqvist LDB-centrum
 */
import java.io.*;
import java.util.UUID;
import java.util.Random;

public class HashSum {
    private String L_katalog, L_fil;
    private String L_val;
     //md5-kommandon
    private String Syscommand;
    private String tecken = "> ";
    private String deepTecken;

    /**
     * @method Konstruktor
     * @see konstruktor
     * @param katalog, string
     * @param sokvag_filnamn, string
     **/
    public HashSum(String katalog, String sokvag_filnamn) {
        L_katalog = katalog;
        L_fil = sokvag_filnamn;
    }//end konstruktor

    /**
     * @method setCatalog
     * @see sökväg till katalog
     * @param sKatalog, string
     **/       
    public void setCatalog(String sKatalog) {
        this.L_katalog = sKatalog;
    }

    /**
     * @method setOutFile
     * @see 
     * @param sUtfil, string
     **/      
    public void setOutFile(String sUtfil) {
        this.L_fil = sUtfil;
    }
    
    /**
     * @method setAlgoritm
     * @see ger en möjlighet att välja MD5 | SHA1
     * @param String md5 alt sha1
     **/    
    public String setAlgoritm(String valAlgoritm) {
        String sendback;
        this.L_val = valAlgoritm;
        L_val = L_val.toLowerCase();
        if (true == L_val.matches("sha1")){
            Syscommand = "sha1deep ";
            deepTecken = "-r ";
            sendback = "SHA1";
        } else if(true == L_val.matches("md5")){
            Syscommand = "md5deep ";
            deepTecken = "-r ";
            sendback = "MD5";
        }else {
            System.out.println("error... Valj hashsumma");
            sendback = "bye";
            System.exit(0);
        } 
        return sendback;
    }
       
    /**
     * @method createMD5sums
     * @see En metod som skapar en md5 lista över given katalogs filer
     * @return boolean true/false
     */
    public boolean createHashsum(){

        try{
            //sökväg samt filer
            String outputMD5 = L_fil;  //"/home/goran/Desktop/md_test.md5
            String filesToMD5 = L_katalog;
            String files ="";
            //Skapar filobjekt
//            File kat = new File(L_katalog + File.separatorChar);
//            if (kat.isDirectory()) {
//                //listar filer och kataloger i given katalog
//                String[] filer = kat.list();
//                File[] f = new File[filer.length];
//                for(int i=0; i< f.length; i++) {
//                    f[i]= new File(kat, filer[i]);
//                    //S1[i] = f[i].toString() + " ";
//                    filesToMD5 += f[i].toString() + " ";
                    //System.out.println(f[i].toString() +'\n');
//                }
           if(Syscommand.contains("md5deep") == true){
                //bygger kommando(MD5)
                 files = Syscommand + deepTecken + filesToMD5 + " "+ tecken + outputMD5;                
            }
            if(Syscommand.contains("sha1deep") == true){
                //bygger kommando(SHA1)
                 files = Syscommand + deepTecken + filesToMD5 + " "+ tecken + outputMD5;                
            }
            String[] command = {"/bin/sh", "-c", files};
            //fixar runtime
            Runtime rt = Runtime.getRuntime();
            Process pr = null;
            //gör anrop (skapar md5 lista)
            pr = rt.exec(command);
            pr.waitFor();
            rt.freeMemory();
            rt.gc();
            return true;
//            } else {
//             System.out.println("fel");
//             return false;
//            }
     }
     catch(Exception e){
        System.out.print("error:HASHSUM:createHashsum... " +e.toString());
        return false;
     }
    }//end createmd5sums

    /**
 * @program: createmd5forfile
 * @see klass som skapar hashsummor för en fil
 * @see genererar en textfil med sökväg samt hashsumma (md5)
 * @param fullsokvag, string
 * @author: Goran Lindqvist LDB-centrum
 */
    public boolean createHashsumforfile(String fullsokvag){
        String L_fullsokvag = fullsokvag;
        try{
            //bygger anropskommando
            String file = Syscommand +" " + L_fullsokvag +" " + tecken + L_fil;
            String[] command ={"/bin/sh", "-c", file};

            //fixar runtime
            Runtime rt = Runtime.getRuntime();
            Process pr = null;
            pr = rt.exec(command);
            pr.waitFor();
            rt.freeMemory();
            rt.gc();             
            return true;
        }catch(Exception e){
            System.out.println("error:HASHSUM:createHashsumforfile... " +e.toString());
            return false;
        }
    } //end createmd5forfile
    
    /**
     * @method generateUIID
     * @see Skapar en UUID och skriver ner den i configfilen
     * @return uid
     */
    public String generateUID() {

        try{
            String uid;
            //skapar en uuid
            uid = String.valueOf(UUID.randomUUID());
            //skriver ner den
            //read.WriteConfigfile("", "uuid", uid);
            String outUid = uid;
            return outUid;
        }catch(IOError e){
            System.out.println("error:generateUID... " +e.toString());
            return "";
        }
    }//end generateuuid 
    
    /**
     * @method generateFileUID
     * @see Skapar en UUID för en fil
     * @return file_uid
     */    
    public String generateFileUID(){
        String outFileUid = null;
        String fileUIID;
        try{
            Random randomGR = new Random();
            int rInt1 = new Integer(randomGR.nextInt(100000));
            int rInt2 = randomGR.nextInt(1000);
            int rInt3 = randomGR.nextInt(100); 
            fileUIID = "ID" +  Integer.toString(rInt1) + "-" + Integer.toString(rInt2) +
            "-" + rInt3;
            
        }catch(IOError e){
            System.out.println("error:HASHSUM:generateFileUID... " +e.toString());
            return "";
        }
        //System.out.println(fileUIID);        
        return fileUIID;
    } //end fileuuid
      
} //end klass

