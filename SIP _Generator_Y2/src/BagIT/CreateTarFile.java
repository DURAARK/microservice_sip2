package BagIT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;


/*
 * @program: CreateTarFile
 * @see Skapar tarfil
 * @author: Goran Lindqvist LDB-centrum | LTU
 * 
 */
public class CreateTarFile {

    static List<String> pathFiles = new ArrayList();
    private String pathTarBall;
    private String inputFileCatalog;
    private String tarFileName;

    /**
     *
     * @param pathTarBall
     */
    public void setPathTarBall(String pathTarBall, String tarFileName) {
        this.pathTarBall = pathTarBall;
        this.tarFileName = tarFileName;
    }

    public void setInputFileCatalog(String inputFileCatalog) {
        this.inputFileCatalog = inputFileCatalog;
    }

    /**
     *
     */
    public void tarFiles() {
        try {
            Integer count = tarFileName.lastIndexOf(":");
            String cutFileName = tarFileName.substring(count + 1);
            String fullPathFileName = pathTarBall + cutFileName + ".tar";
            //Output Stream - Tar paket skapas
            OutputStream tarOutput = new FileOutputStream(new File(fullPathFileName));
            ArchiveOutputStream tarBall = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR, tarOutput);
            //inputkatalog
            File inputFolder = new File(inputFileCatalog);
            List absPathFiles = loopFileSystem(inputFolder, "");
            Iterator it = absPathFiles.iterator();
            //loopar
            while (it.hasNext()) {
                String files = (String) it.next();
                //System.out.println("**: " +files);
                File tar_input_file = new File(files);
                String relativePath = tar_input_file.getCanonicalPath().substring(inputFolder.getCanonicalPath().length(), tar_input_file.getCanonicalPath().length());
                TarArchiveEntry tar_file = new TarArchiveEntry(relativePath);
                tar_file.setSize(tar_input_file.length());
                tarBall.putArchiveEntry(tar_file);
                IOUtils.copy(new FileInputStream(tar_input_file), tarBall);

                //Stäng Archieve entry, skriver
                tarBall.closeArchiveEntry();
            }//end while                                   
            tarBall.finish();
            /* Close output stream, our files are zipped */
            tarOutput.close();
        } catch (Exception e) {
            System.out.println("error:CREATETARFILE:tarFiles... " + e.getMessage());
        }

    }

    /**
     *
     * @param parentNode
     * @param leftIndent
     * @return
     * @throws IOException
     */
    private static List loopFileSystem(File parentNode, String leftIndent) throws IOException {

        if (parentNode.isDirectory()) {
            leftIndent += "   ";
            File childNodes[] = parentNode.listFiles();
            for (File childNode : childNodes) {
                loopFileSystem(childNode, leftIndent);
            }
        } else {
            //System.out.println(parentNode.getAbsolutePath());
            pathFiles.add(parentNode.getAbsolutePath());
        }
        return pathFiles;
    } //end Trav            
}
