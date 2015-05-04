/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BagIT;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author goran
 */
public class Common {
    
    /**
     * @method deleteFile
     * @see Raderar given fil
     * @param Sträng med sökväg inkl. filnamnet
     * @return boolean true/false
     */
    public boolean deleteFile(String pathtofile){
         
        try{
            File delfile = new File(pathtofile);
            
            if(delfile.isFile()){
                delfile.delete();  
            } else{
// HR 2015-03-18  Vilken fil fanns ej ?  verkar ändå funka !!               
//                System.out.println("Common:Fil fanns ej");
//                System.out.println("pathtofile = " + pathtofile);
            }
            return true;
        } catch(Exception e) {
            System.out.println("error:Common:DeleteFile... " +e.toString());
            return false;
        }
    } //end delfil
    
    /**
     * @method DelAllFilerKatalog
     * @see En generell metod för att ta bort filer i en katalog
     * @return boolean true/false
     */
   public boolean delAllFilesCatalog(String path){
        String katalogpath = path;
        try{
            File openkatalog = new File(katalogpath);

            if(openkatalog.isDirectory()){
                String[] filer = openkatalog.list();
                File[] f = new File[filer.length];

                for(int i=0; i<filer.length; i++){
                    f[i] = new File(openkatalog, filer[i]);
                }

                for(int i=0; i<f.length; i++){
                    //System.out.println(f[i].toString());
                    if(true==f[i].isFile())
                        f[i].delete();
                }

            } else{
                return false;
            }
            return true;
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }// del katalog    
          
    /**
     * @method dateAndTime
     * @see Skapar en datum och tidsstämpel
     * @return String, datumtidstämpel.
     */    
    public String dateAndTime(){
        try{
        //datum
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal.getTime()));
        return dateFormat.format(cal.getTime());
        }catch(Exception e){
            System.out.println("error:Common:DateAndTime... " +e.toString());
            return "";
        }            
    } //end metod    
}
