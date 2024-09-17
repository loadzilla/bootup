import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

 public class update {
	
	public static void main(String[] commandLineArguments) {
		
	try {
	
		URL url2 = new URL("https://loadzilla.net/cloud/test.txt");
		
		ReadableByteChannel readableByteChannel2 = Channels.newChannel(url2.openStream());
		
		FileOutputStream fileOutputStream2 = new FileOutputStream("../bin/Certificates/CapCal.Keystore", false);
	//	FileChannel fileChannel2 = fileOutputStream2.getChannel();
	
		fileOutputStream2.getChannel()
		  .transferFrom(readableByteChannel2, 0, Long.MAX_VALUE);
		
		fileOutputStream2.close();
		System.out.println("downloaded keystore");
		
		
		URL url = new URL("https://loadzilla.net/cloud/agent.jar");
		
		ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
		
		FileOutputStream fileOutputStream = new FileOutputStream("../lib/agent.jar", false);
	//	FileChannel fileChannel = fileOutputStream.getChannel();
	
		fileOutputStream.getChannel()
		  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		
		fileOutputStream.close();
		System.out.println("downloaded agent");
		
		URL url3 = new URL("https://loadzilla.net/cloud/lib.zip");
		
		ReadableByteChannel readableByteChannel3 = Channels.newChannel(url3.openStream());
		
		FileOutputStream fileOutputStream3 = new FileOutputStream("../lib/lib.zip", false);
	
		fileOutputStream3.getChannel()
		  .transferFrom(readableByteChannel3, 0, Long.MAX_VALUE);
		
		fileOutputStream3.close();
		System.out.println("downloaded lib");
		
		// unzip lib folder containing plug-ins
		
		unzip("../lib/lib.zip", "/opt/jmeter/");
		System.out.println("unzipped lib");
		
	}
	

	catch (Exception ex)  {
			String s = ex.getMessage();
			System.out.println(s);
		}
	}
	
	 private static void unzip(String zipFilePath, String destDirectory) throws IOException {
	        File destDir = new File(destDirectory);
	        if (!destDir.exists()) {
	            destDir.mkdir();
	        }
	        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	        ZipEntry entry = zipIn.getNextEntry();
	        // iterates over entries in the zip file
	        while (entry != null) {
	            String filePath = destDirectory + File.separator + entry.getName();
	            if (!entry.isDirectory()) {
	                // if the entry is a file, extracts it
	                extractFile(zipIn, filePath);
	            } else {
	                // if the entry is a directory, make the directory
	                File dir = new File(filePath);
	                dir.mkdir();
	            }
	            zipIn.closeEntry();
	            entry = zipIn.getNextEntry();
	        }
	        zipIn.close();
	    }
	 
	 /**
	     * Extracts a zip entry (file entry)
	     * @param zipIn
	     * @param filePath
	     * @throws IOException
	     */
	    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
	        byte[] bytesIn = new byte[4096];
	        int read = 0;
	        while ((read = zipIn.read(bytesIn)) != -1) {
	            bos.write(bytesIn, 0, read);
	        }
	        bos.close();
	    }
	    
}
