/**
 * @program: eARDsip
 * @see klass som startar droid
 * @author: Goran Lindqvist LDB-centrum
 */
package Rosetta;
/**
 *
 */
public class StartDroid {

    /**
     * @method main
     * @see main metod som startar Droid, fixar problem med start (buggproblem).
     * @return void
     */
    public static void main(String pathDroid,String argA,String argS,String argO) {
        //
        String Argcommand = " -q -a " + argA + " -s " + argS + " -o " + argO;
        String Syscommand = "java -jar ";
        String command = "";
        String PathDroid = pathDroid;
       
       try{
           //bygger kommando
           command = Syscommand + PathDroid + Argcommand;
          /* System.out.print (command); */
            //anropar Droid
            Runtime rt = Runtime.getRuntime();
            Process pr = null;
            pr = rt.exec(command);
            pr.waitFor();
            rt.freeMemory();
            rt.gc();
       }catch(Exception e){
            System.out.println("error:StartDroid:main... " +e.toString());
       }
    }
}
