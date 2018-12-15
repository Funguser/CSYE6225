package com.csye6225.lab.ziyao.AWSService;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public class SNSService {
    static AmazonSNS snsClient = null;
    public static void init() {
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();
        snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion("us-west-1")
                .build();
        System.out.println("created the sns client");
    }

    public static AmazonSNS getSnsClient() {
        if (snsClient == null)
            init();
        return snsClient;
    }
}
