package com.masmovil;
import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
// Imports the Google Cloud client library
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class QuickstartSamples {
  public static void main(String... args) throws Exception {
    // Instantiates a client
    //Storage storage = StorageOptions.getDefaultInstance().getService().set;
    
	StorageOptions.Builder optionsBuilder = StorageOptions.newBuilder();
	
	optionsBuilder.setProjectId("stunning-strand-207120");
	
	optionsBuilder.setCredentials(GoogleCredentials.fromStream(new 
	         FileInputStream("C:/Users/DTUser/googleCredentials/My Project-b50a3905517b.json")));
	
	Storage storage = optionsBuilder.build().getService();

    // The name for the new bucket
    String bucketName = "jmpb_23061982_1";

    // Creates the new bucket
    Bucket bucket = storage.create(BucketInfo.of(bucketName));

    System.out.printf("Bucket %s created.%n", bucket.getName());
  }
}
