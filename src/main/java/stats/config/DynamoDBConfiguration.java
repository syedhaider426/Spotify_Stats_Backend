package stats.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Setup the connection to DynamoDB
 */
@Configuration
public class DynamoDBConfiguration {

    @Value("${aws.env}")
    private String env;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.endpoint}")
    private String endpoint;

    @Value("${spotify.clientId")
    private String spotifyClientId;

    @Value("${spotify.clientSecret")
    private String spotifyClientSecret;

    /**
     * This will create the connection the DynamoDB endpoint
     *
     * @return AmazonDynamoDB Client
     */
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        //Development
        if (env.equals("development")) {
            return AmazonDynamoDBClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                    .build();
        }
        //Production
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new ProfileCredentialsProvider())
                .build();
    }

    @Bean
    public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDB(amazonDynamoDB);
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    public String spotifyClientId(){
        return spotifyClientId;
    }

    @Bean
    public String spotifyClientSecret(){
        return spotifyClientSecret;
    }

}
