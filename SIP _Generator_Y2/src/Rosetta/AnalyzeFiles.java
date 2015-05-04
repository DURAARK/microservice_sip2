/**
 * @program: AnalyzeFiles
 * @see klass som analyserar filer med Droid
 * @author: Goran Lindqvist LDB-centrum
 */
package Rosetta;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//Klass
public class AnalyzeFiles {

    
    

    protected static String L_pathKatalog;
    protected static String L_droidLista;
    

    /**
     * Set the value of string
     *
     * @param string skall peka på katalog som innehåller kataloger
     * content,metadata,system.
     */
    public void setPathCatalog(String pathkatalog) {
        AnalyzeFiles.L_pathKatalog = pathkatalog;
    }

    /**
     * Set the value of string
     *
     * @param string output för droidlista inkl. filnamnet
     */    
    public void setDroidList(String droidLista) {
        AnalyzeFiles.L_droidLista = droidLista;
    }

        
    /**
     * @method createFileListForDroid
     * @see Skapar en fillista av de filer som finns uppackade i en katalog,
     * fillistan används av DROID4. 
     * @param Sträng med sökväg till överliggande katalog med uppackade filer
     * @param Sträng till där fillista skall skapas.
     * @return boolean true/false
     */
    public boolean createFileListForDroid(){

        String katalog = "";
        String l_katalog;        
        String FileString1;
        String FileString2;
        
        //PathKatalog = pathkatalog;
        //bygger lista
        FileString1 = "<IdentificationFile Name= ";
        FileString2 = "/>";
        
        //DroidLista = droidlista;         
        try{

            //skapar streamobj för att skriva till fil
            FileOutputStream CreateFile = new FileOutputStream(L_droidLista);
            BufferedOutputStream buffer = new BufferedOutputStream(CreateFile);
            DataOutputStream data = new DataOutputStream(buffer);  
            
            Integer j = 1;
            data.writeBytes("<FileCollection>" + '\n');
            while(j<2){
                switch(j){
                    case 1: katalog = "content/streams";
                    break;
//                    case 2: katalog = "metadata";
//                    break;
//                    case 3: katalog = "system";
//                    break;    
            }
            //fixar söksträng    
            l_katalog = null;        
            l_katalog = L_pathKatalog + katalog;
            //System.out.println(l_katalog);            
            
            //skapar filobjekt
            File kat = new File(l_katalog);
           
            if(kat.isDirectory()) {
                String [] filer = kat.list();
                File[] f = new File[filer.length];
                
                for(int i=0; i < f.length; i++){
                    f[i] = new File(kat,filer[i]);
                }
                //Börjar skriva Droidfillista
                for(int i=0; i < f.length; i++){
                    String XmlTag = FileString1 + '"'+f[i]+'"' +FileString2;
                    data.writeBytes(XmlTag);
                }
                j= j+1;
            } else
            {
               System.out.println("Hittade ej katalogen");
               return false;
            }            
            }//end while
            data.writeBytes("</FileCollection>");
            data.close();
            buffer.close();
            return true;
        } catch(Exception e) {
            System.out.println("error:AnalyzeFiles:SkapaFillistaForDroid... " +e.toString());
            return false;            
        }
    }//en metod      
 
    /**
     * @method createFileList
     * @see Metod som skapar en fillista innehållande sökväg till filer
     * @return boolean true/false
     */
    public static boolean createFileList(){
     
     try{
            //läser och hämtar sträng från configfil
            String katalog = "";
            String L_lista;
            String l_katalog;
//            L_lista ="/home/goran/testfiler/";
            L_lista ="";

            //Skapar streamobj för skriva ner till fil 
            FileOutputStream minFil = new FileOutputStream(L_lista + "fillista.txt");
            BufferedOutputStream buffer = new BufferedOutputStream(minFil);
            DataOutputStream data = new DataOutputStream(buffer);
           //väljer kataloger
            Integer j = 1;
            while(j<2){
                switch(j){
                    case 1: katalog = "content/streams";
                    break;
//                    case 2: katalog = "metadata";
//                    break;
//                    case 3: katalog = "system";
//                    break;    
            }
            //fixar söksträng    
            l_katalog = null;        
            l_katalog = L_pathKatalog + katalog;
            System.out.println(l_katalog);
            
            //Skapar filobjekt
            File kat = new File(l_katalog + File.separatorChar);
            System.out.println("\n kat = " + kat);
            if (kat.isDirectory()) {
                //listar filer och kataloger i given katalog
                String[] filer = kat.list();
                File[] f = new File[filer.length];
                for(int i=0; i< f.length; i++) {
                    f[i]= new File(kat, filer[i]);
                } //endfor                    
                    //Skriver filer och katloger till fil
                    for(int i=0; i< f.length; i++) {
                        data.writeBytes(f[i].toString() + '\n');
                    }
                System.out.println(j);
                j= j+1;           
            } else {
             System.out.println("AnalyzeFiles:Fel katalog");
             return false;
            }    
     } //endwhile
        data.close();
        buffer.close();                
     }catch(Exception e)
     {
        System.out.print("error:AnalyzeFiles:CreateFileList... " +e.toString());
        return false;
     }
    return true;     
    }//end metod    
        
}//end klass
