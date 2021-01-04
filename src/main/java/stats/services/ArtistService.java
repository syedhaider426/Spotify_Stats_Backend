package stats.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import stats.config.DynamoDBConfiguration;
import stats.models.Artist;
import stats.repository.ArtistRepository;

import java.util.*;

public class ArtistService implements ArtistRepository {

    private DynamoDBMapper mapper;

    public ArtistService(){
        mapper = new DynamoDBConfiguration().getMapper();
    }

    // Used to load in data
    public Map<String,String> getAllArtists(){
        Map<String,String> artists = new HashMap<>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Artist> scanResult = mapper.scan(Artist.class,scanExpression);
        for(Artist artist: scanResult){
            artists.put(artist.getArtist(),artist.getSpotifyId());
        }
        return artists;
    }

    // Used to populate autocomplete
    public List<String> getArtists(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Artist> scanResult = mapper.scan(Artist.class,scanExpression);
        List<String> artists = new ArrayList<>();
        for(Artist artist: scanResult){
            artists.add(artist.getArtist());
        }
        Collections.sort(artists);
        return artists;
    }

    public void create(String name, String spotifyId){
        Artist artist = new Artist(name,spotifyId);
        mapper.save(artist);
    }

    public void create(Artist artist){
        mapper.save(artist);
    }

    public void create(List<Artist> artist){
        mapper.batchSave(artist);
    }

    public void update(String name, String spotifyId){
        Artist artist = mapper.load(Artist.class,name);
        artist.setArtist(spotifyId);
        mapper.save(artist);
    }

    public void update(Artist artist){
        mapper.save(artist);
    }

    public void delete(String name){
        Artist artist = mapper.load(Artist.class,name);
        mapper.delete(artist);
    }

    public void delete(Artist artist){
        mapper.delete(artist);
    }




}
