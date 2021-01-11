package stats.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import stats.models.Artist;
import stats.services.ArtistService;

import java.util.List;

/**
 * Handles all the requests to any endpoints related to Artists
 */
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    /**
     * Gets all the available artists to search in the autocomplete feature
     *
     * @return
     */
    @GetMapping(value = "/allArtists")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getArtists() {
        List<String> artists = artistService.getArtists();
        return artists;
    }

    /**
     * Post request that creates an artist
     *
     * @param artist name of artist
     */
    @PostMapping(path = "/artist",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createArtist(@RequestBody Artist artist) {
        artistService.create(artist.getArtist());
    }

}
