package com.masmovil;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;

import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.Buckets;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

public class CloudStorage 
{
    private static final String APPLICATION_NAME = "GoogleAPIClient-CloudStorage";
    private static final String PROJECT_ID = "stunning-strand-207120";
    private static final String BUCKET_NAME = "jmpb_23061982";

    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Arrays.asList(
        StorageScopes.DEVSTORAGE_READ_WRITE
    );

    public static void main( String[] args )
    {
    	
    	System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:/Users/jpalaciosbarriga/Downloads/My Project-4f6b92733888.json");
        try {

            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            GoogleCredential credential = GoogleCredential.getApplicationDefault();

            Storage storage = new Storage.Builder(
                httpTransport,
                JSON_FACTORY,
                credential
            ).setApplicationName(
                APPLICATION_NAME
            ).build();

            Storage.Buckets.List bucketsList = storage.buckets().list(PROJECT_ID);
            Buckets buckets;
            do {
                buckets = bucketsList.execute();
                List<Bucket> items = buckets.getItems();
                if (items != null) {
                    for (Bucket bucket: items) {
                        System.out.println(bucket.getName());    
                    }
                }
                bucketsList.setPageToken(buckets.getNextPageToken());
            } while (buckets.getNextPageToken() != null);

            Storage.Objects.List objectsList = storage.objects().list(BUCKET_NAME);
            Objects objects;
            do {
                objects = objectsList.execute();
                List<StorageObject> items = objects.getItems();
                if (items != null) {
                    for (StorageObject object : items) {
                        System.out.println(object.getName());
                    }
                }
                objectsList.setPageToken(objects.getNextPageToken());
            } while (objects.getNextPageToken() != null);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);

    }
}
