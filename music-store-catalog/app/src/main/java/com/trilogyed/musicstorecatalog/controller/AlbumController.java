package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums() {
        List<Album> allAlbums = albumRepo.findAll();
        /**  might need to change this after testing */
        if (allAlbums == null || allAlbums.isEmpty()) {
            throw new IllegalArgumentException("No albums were found!");
        } else {
            return allAlbums;
        }
    }
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAlbum(@RequestBody @Valid Album album) {
        if (album == null || album.getId() < 1) {
            throw new IllegalArgumentException("Album does not exist with this id!");
        } else if (album.getId() > 0) {
            albumRepo.save(album);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable("id") Integer albumId) {
        Optional<Album> album = albumRepo.findById(albumId);
//        if (album == null) {
        if(!album.isPresent()){
            throw new IllegalArgumentException("Album not found with this id!");
        } else {
            return (album.get());
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album addNewAlbum(@RequestBody @Valid Album album){
        return albumRepo.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumById(@PathVariable("id") Integer albumId) {
        albumRepo.deleteById(albumId);

    }


}

