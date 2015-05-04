package Rosetta;

/**
 * @program: fileValues
 * @see klass som tar fram värden kring filer.
 * @author: Goran Lindqvist LDB-centrum
 */

import java.io.*; 

public class FileValues {
  
private String L_filename;
private String L_mimevalue;
private String L_mb;
private  static String L_version;

   /**
    * @method getFilename
    * @return  string, filnamn
    **/    
    public String getFilename() {
        return L_filename;
    }

   /**
    * @method getMimevalue
    * @return  string, mimevärde
    **/    
    public String getMimevalue() {
        return L_mimevalue;
    }
 
   /**
    * @method getMB
    * @return  string, filens värdei MB
    **/
    public String getMB() {
        return L_mb;
    }
    
   /**
    * @method getFileversion
    * @see Anv endast för webb
    * @return  string, filversion
     **/
    public String getFileversion(){
        return L_version;
    }    
    
    /**
     * @method createMeFiles
     * @see Metod som sätter värden för de ingående filer som skall skrivas i
     * @see ADDML struktur.
     * @param Sträng, pathtocatalog, sökväg till katalog där filer finnes.
     * @return boolean, true/false
     */
  public boolean createMeFiles(String pathtocatalog){

        try{
            //läser och hämtar sträng från configfil
            String katalog = pathtocatalog; //"/home/goran/Desktop/mintest/";
           // L_lista = read.ReadConfigfile("", "sokvag_c01");
            //Skapar streamobj för skriva ner till fil
           // FileOutputStream minFil = new FileOutputStream(L_lista + "warcfillista.txt");
            //BufferedOutputStream buffer = new BufferedOutputStream(minFil);
           // DataOutputStream data = new DataOutputStream(buffer);
            //Skapar filobjekt
            File kat = new File(katalog + File.separatorChar);
            long size = 0;
            String filename = "", s1="", s2="", s3="";
            int cutvalue = 0;
            if (kat.isDirectory()) {
                //listar filer och kataloger i given katalog
                String[] filer = kat.list();
                File[] f = new File[filer.length];
                for(int i=0; i< f.length; i++) {
                    f[i]= new File(kat, filer[i]);
                    filename = f[i].toString();
                    cutvalue = filename.lastIndexOf("/") + 1;
                    File filesize = new File(f[i].toString());
                    size = filesize.length();
                     //filnamn
                    s1 = filename.substring(cutvalue);
                    System.out.println(s1);
                    //mime
                    s2 = mimeTypes(filename.substring(cutvalue));
                    System.out.println(s2);
                    //mb
                    s3 = Long.toString(size);
                    System.out.println(s3);
                    //skapar temp xmlfillista
                    //writefiledata(s1, s2, s3);
                }//end for
         
                //System.out.println("Stopp");
                return true;
            } else {
             System.out.println("fileValues:Fel katalog");
             return false;
            }
     }
     catch(Exception e)
     {
        System.out.print("error:fileValues:createmefiles... " +e.toString());
        return false;
     }
 } //end

    /**
     * @method fileData
     * @see Metod som sätter värden för ingående fil, namn mime, mb
     * @see AD
     * @param Sträng, path, sökväg till fil.
     * @return boolean, true/false
     */  
  public boolean fileData(String path) {
    String filename = path;
    //String s1, s2, s3;
    int cutvalue = 0;
    long size = 0;
    try{
        if(filename.contains("content") == true){
            cutvalue = filename.indexOf("content");
            //filnamn
            L_filename = "file:"+filename.substring(cutvalue);
            //System.out.println(L_filename);  
        } else if(filename.contains("metadata") == true){
            cutvalue = filename.indexOf("metadata");
            //filnamn
            L_filename = "file:"+filename.substring(cutvalue);
            //System.out.println(L_filename);  
        } else if(filename.contains("system") == true){
            cutvalue = filename.indexOf("system");
            //filnamn
            L_filename = "file:"+filename.substring(cutvalue);
            //System.out.println(L_filename);  
        }                              
        //mime
        L_mimevalue = mimeTypes(filename.substring(cutvalue));
        //System.out.println(L_mimevalue);
        //mb
        File filesize = new File(filename);
        size = filesize.length();        
        L_mb = Long.toString(size);
        //System.out.println(L_mb);               
    }catch(Exception e){
        System.out.println("error:fileValues:fileData... " +e.toString());
        return false;
    }
    return true;
}  

     /**
     * @method  mimeTypes
     * @see     tar emot filnamn, sätter mimetyp på fil
     * @param   Sträng, filnamnet
     * @return  Sträng, filens mimetyp
     */
    private static String mimeTypes(String input){

        String s1 = input;
        int cutvalue = s1.lastIndexOf(".") + 1;
        s1 = s1.substring(cutvalue);
        String output = "";
        if(s1.equals("txt") || s1.equals("log")) {
            output = "text/plain";
            L_version = "TXT";
        }
        if(s1.equals("xml")){
            output = "text/xml";
            L_version = "XML(1.0)";
        }
        if(s1.equals("warc")){
            output ="application/warc";
            L_version = "WARC;ISO 28500-2009";
        }
         return output;
    }//end mimetypes     
}