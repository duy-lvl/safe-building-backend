//package com.safepass.safebuilding.config;
//
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.iam.v1.Iam;
//import com.google.api.services.iam.v1.IamScopes;
//import com.google.api.services.iam.v1.model.CreateServiceAccountKeyRequest;
//import com.google.api.services.iam.v1.model.ServiceAccountKey;
//import com.google.auth.http.HttpCredentialsAdapter;
//import com.google.auth.oauth2.GoogleCredentials;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Base64;
//import java.util.Collections;
//
//public class CreateServiceAccountKey {
//
//    // Creates a key for a service account.
//    public static String createKey(String projectId, String serviceAccountName) {
//        // String projectId = "my-project-id";
//        // String serviceAccountName = "my-service-account-name";
//
//        Iam service = null;
//        try {
//            service = initService();
//        } catch (IOException | GeneralSecurityException e) {
//            System.out.println("Unable to initialize service: \n" + e);
//            return null;
//        }
//
//        // Construct the service account email.
//        // You can modify the ".iam.gserviceaccount.com" to match the service account name in which
//        // you want to create the key.
//        // See, https://cloud.google.com/iam/docs/creating-managing-service-account-keys?hl=en#creating
//        String serviceAccountEmail = serviceAccountName + "@" + projectId + ".iam.gserviceaccount.com";
//        try {
//            ServiceAccountKey key =
//                    service
//                            .projects()
//                            .serviceAccounts()
//                            .keys()
//                            .create(
//                                    "projects/-/serviceAccounts/" + serviceAccountEmail,
//                                    new CreateServiceAccountKeyRequest())
//                            .execute();
//
//            // The privateKeyData field contains the base64-encoded service account key
//            // in JSON format.
//            // TODO(Developer): Save the below key (jsonKeyFile) to a secure location.
//            //  You cannot download it later.
//            String jsonKeyFile = new String(Base64.getDecoder().decode(key.getPrivateKeyData()));
//
//            System.out.println("Key created successfully");
//            String keyName = key.getName();
//            return keyName.substring(keyName.lastIndexOf("/") + 1).trim();
//        } catch (IOException e) {
//            System.out.println("Unable to create service account key: \n" + e);
//            return null;
//        }
//    }
//
//    private static Iam initService() throws GeneralSecurityException, IOException {
//        // Use the Application Default Credentials strategy for authentication. For more info, see:
//        // https://cloud.google.com/docs/authentication/production#finding_credentials_automatically
//        GoogleCredentials credential =
//                GoogleCredentials.getApplicationDefault()
//                        .createScoped(Collections.singleton(IamScopes.CLOUD_PLATFORM));
//        // Initialize the IAM service, which can be used to send requests to the IAM API.
//        Iam service =
//                new Iam.Builder(
//                        GoogleNetHttpTransport.newTrustedTransport(),
//                        JacksonFactory.getDefaultInstance(),
//                        new HttpCredentialsAdapter(credential))
//                        .setApplicationName("service-account-keys")
//                        .build();
//        return service;
//    }
//}
