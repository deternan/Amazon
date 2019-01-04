package SimpleStorageService;

/*
 * Reference
 * 
 * resource
 * https://aws.amazon.com/tw/transcribe/resources/
 * 
 * what-is-transcribe
 * https://docs.aws.amazon.com/zh_tw/transcribe/latest/dg/what-is-transcribe.html
 * 
 * Client region
 * https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-region-selection.html
 * 
 * Create bucket
 * https://docs.aws.amazon.com/zh_tw/AmazonS3/latest/dev/create-bucket-get-location-example.html#create-bucket-get-location-console
 * 
 * Step by Step
 * https://aws.amazon.com/tw/getting-started/tutorials/create-audio-transcript-transcribe/
 * 
 */

import java.io.IOException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

public class CreateBucket {

    public static void main(String[] args) throws IOException {
//        String clientRegion = "*** Client region ***";
//    	String bucketName = "*** Bucket name ***";
    	String clientRegion = Regions.US_WEST_1.toString();
        String bucketName = "NewBucket";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(clientRegion)
                    .build();

            if (!s3Client.doesBucketExistV2(bucketName)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                s3Client.createBucket(new CreateBucketRequest(bucketName));
                
                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("Bucket location: " + bucketLocation);
            }
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it and returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
