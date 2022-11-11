package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {

    @MockBean
    private TrackRepository trackRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldGetAllTracksAndReturn200() throws Exception {
        Track track = new Track(33, 1222,"someone is watching me", 2);
        List<Track> trackList = Arrays.asList(track);

        String expectedJson = mapper.writeValueAsString(trackList);

        doReturn(trackList).when(trackRepo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void updateTrackByIdAndReturn200() throws Exception {
        Track track = new Track(14,1,"por favor", 2);
        String expectedJson = mapper.writeValueAsString(track);

        mockMvc.perform(put("/track/14")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getTrackByIdAndReturn200() throws Exception {
        Track track = new Track(23,1,"ola", 2);
        String expectedJson = mapper.writeValueAsString(track);

        doReturn(Optional.of(track)).when(trackRepo).findById(23);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/track/23"))
                    .andExpect(status().isOk())
                    .andExpect((content().json(expectedJson)));
    }

    @Test
    public void shouldAddNewTrackOnPostRequest() throws Exception{
        Track output = new Track(122, 133, "carnivore", 3);
        Track input = new Track(123,163, "over you", 4);

        String outputTrackJson = mapper.writeValueAsString(output);
        String inputTrackJson = mapper.writeValueAsString(input);

        when(trackRepo.save(input)).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/track")
                        .content(inputTrackJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackJson));
    }

    @Test
    public void deleteTrackById() throws Exception {
        Artist artist = new Artist(142, "Dat Man", "datMan", "@datMan");
        mockMvc.perform(MockMvcRequestBuilders.delete("/track/142"))
                .andExpect(status().isNoContent());
    }
}