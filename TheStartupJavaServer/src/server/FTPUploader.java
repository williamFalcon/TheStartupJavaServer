package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
 
/**
 * Simple FTP upload clients
 *
 */
public class FTPUploader {
     
    FTPClient ftp = null;
     
    /**
     * Class constructor
     * @param host
     * @param user
     * @param pwd
     * @throws Exception
     */
    public FTPUploader(String host, String user, String pwd) throws Exception{
    	
    	//Init FTP client
        ftp = new FTPClient();
        
        //Add protocol listener
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        
        //Connect to host
        ftp.connect(host);
        reply = ftp.getReplyCode();
        
        //If negative reply, disconnect and throw error
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        
        //Login
        ftp.login(user, pwd);
        
        //Set file type
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        
        //Passive mode
        ftp.enterLocalPassiveMode();
    }
    
    /**
     * Uploads a file
     * @param file
     * @param fileName
     * @param hostDir
     * @throws Exception
     */
    public void uploadFile(File file, String fileName, String hostDir)
            throws Exception {
    	
    	try {
    		
    		//Init input stream and store the file
    		InputStream input = new FileInputStream(file);
            this.ftp.storeFile(hostDir + fileName, input);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
 
    /**
     * Disconnects from FTP server
     */
    public void disconnect(){
    	
    	//If connected, disconnect
        if (this.ftp.isConnected()) {
            
        	try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
            	
                // do nothing as file is already saved to server
            	f.printStackTrace();
            }
        }
    }
    
    
    /*******************************************************/
    /********************* STATIC **************************/
    /*******************************************************/
    
    /**
	 * Uploads a file through the ftp server
	 * @param file
	 * @param fileName
	 * @param ftpUrl
	 * @param userName
	 * @param password
	 * @param fileRoute
	 * @throws Exception
	 */
	public static void uploadFileToFtp(File file, String fileName, String ftpUrl, String userName, String password, String fileRoute) throws Exception{
		
		//Start upload
		System.out.println("Start");
		
		//Init ftp client and uplaod
        FTPUploader ftpUploader = new FTPUploader(ftpUrl, userName, password);
        ftpUploader.uploadFile(file, fileName, fileRoute);
        
        //Disconnect and finish
        ftpUploader.disconnect();
        System.out.println("Done");
	}
}