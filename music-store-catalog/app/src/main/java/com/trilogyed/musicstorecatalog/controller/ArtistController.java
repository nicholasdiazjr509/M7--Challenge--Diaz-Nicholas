package com.trilogyed.musicstorecatalog.controller;



import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepo;




    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists() {
        List<Artist> allArtists = artistRepo.findAll();
        /**  might need to change this after testing */
        if (allArtists.isEmpty()) {
            throw new IllegalArgumentException("No albums were found!");
        } else {
            return allArtists;
        }
    }
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAlbum(@RequestBody @Valid Artist artist) {
        if (artist == null || artist.getId() < 1) {
            throw new IllegalArgumentException("Album does not exist with this id!");
        } else if (artist.getId() > 0) {
            artistRepo.save(artist);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable("id") Integer artistId) {
        Optional<Artist> artist = artistRepo.findById(artistId);
//        if (artist == null) {
        if(!artist.isPresent()){
            throw new IllegalArgumentException("Artist not found with this id!");
        } else {
            return (artist.get());
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist addNewArtist(@RequestBody Artist artist){
        return artistRepo.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistById(@PathVariable("id") Integer artistId) {
        artistRepo.deleteById(artistId);

    }

}
