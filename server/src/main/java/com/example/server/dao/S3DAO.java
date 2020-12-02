package com.example.server.dao;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.ByteArrayInputStream;

public class S3DAO {

    public static String uploadProfileImage(byte[] image, String username) {

        String bucketName = "profile-images-tweeter";
        ByteArrayInputStream stream = new ByteArrayInputStream(image);

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withRegion("us-east-2")
                    .build();

            //build the upload request
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.length);
            metadata.setContentType("image/bmp");

            PutObjectRequest request = new PutObjectRequest(bucketName, username, stream, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3Client.putObject(request);

            System.out.println(result.getContentMd5());
            return s3Client.getUrl(bucketName, username).toString();
        } catch (SdkClientException ex) {
            System.out.println(ex.getMessage());
        }

        return "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    }

}
