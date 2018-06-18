package com.masmovil;
import java.io.File;
import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.Part;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class QuickstartSamples {
	
	private static Storage storage = null;

  // [START init]
  static {
    storage = StorageOptions.getDefaultInstance().getService();
  }
	
  public static void main(String... args) throws Exception {
    // Instantiates a client
    //Storage storage = StorageOptions.getDefaultInstance().getService().set;
    
	StorageOptions.Builder optionsBuilder = StorageOptions.newBuilder();
	
	optionsBuilder.setProjectId("stunning-strand-207120");
	
	optionsBuilder.setCredentials(GoogleCredentials.fromStream(new 
	         FileInputStream("C:/Users/DTUser/googleCredentials/My Project-b50a3905517b.json")));
	
	storage = optionsBuilder.build().getService();

    // The name for the new bucket
    String bucketName = "jmpb_23061982_1";

    // Creates the new bucket
    /* This code is to create the bucket
    Bucket bucket = storage.create(BucketInfo.of(bucketName));
    System.out.printf("Bucket %s created.%n", bucket.getName());
    */
    uploadFile(bucketName);
    
    
    
  }
  
  
  /**
   * Uploads a file to Google Cloud Storage to the bucket specified in the BUCKET_NAME
   * environment variable, appending a timestamp to end of the uploaded filename.
   */
  @SuppressWarnings("deprecation")
  public static String uploadFile(final String bucketName) throws IOException {
	  
    DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
    DateTime dt = DateTime.now(DateTimeZone.UTC);
    String dtString = dt.toString(dtf);
    
    File initialFile = new File("C:/Users/DTUser/Documents/issues.csv");
    InputStream targetStream = new FileInputStream(initialFile);
    final String fileName = initialFile.getName() + dtString;

    // the inputstream is closed by default, so we don't need to close it here
    BlobInfo blobInfo =
        storage.create(
            BlobInfo
                .newBuilder(bucketName, fileName)
                // Modify access list to allow all users with link to read file
                .setAcl(new ArrayList(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build(),
                targetStream);
    // return the public download link
    return blobInfo.getMediaLink();
  }
}
