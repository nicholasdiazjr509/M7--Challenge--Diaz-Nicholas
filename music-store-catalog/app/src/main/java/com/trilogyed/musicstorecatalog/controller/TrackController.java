package com.trilogyed.musicstorecatalog.controller;


import com.trilogyed.musicstorecatalog.model.Track;

import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackRepository trackRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks() {
        List<Track> allTracks = trackRepo.findAll();
        if(allTracks.isEmpty()) {
            throw new IllegalArgumentException("No tracks were found!");
        }else{
            return allTracks;
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTrack(@RequestBody @Valid Track track) {
        if (track == null || track.getId() < 1) {
            throw new IllegalArgumentException("Track does not exist!");
        } else if (track.getId() > 0){
            trackRepo.save(track);
        }
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrackById(@PathVariable("id") Integer trackId){
        Optional<Track> track = trackRepo.findById(trackId);
        if(!track.isPresent()){
            throw new IllegalArgumentException("Track not found!");
        }else{
            return (track.get());
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track addNewTrack(@RequestBody Track track){
        return trackRepo.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackById(@PathVariable("id")Integer trackId){
        trackRepo.deleteById(trackId);
    }
}
