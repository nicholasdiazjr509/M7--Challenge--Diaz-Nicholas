package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {
    @MockBean
    private ArtistRepository artistRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void updateArtistByIdAndReturn200() throws Exception {
        Artist artist = new Artist(14, "metallica","metallica", "@metallica");
        String expectedJson = mapper.writeValueAsString(artist);

        mockMvc.perform(put("/artist/14")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getArtistById() throws Exception {
        Artist artist = new Artist(11, "Mr Mr", "MrMr", "@MrMr");
        String expectedJson = mapper.writeValueAsString(artist);

        doReturn(Optional.of(artist)).when(artistRepo).findById(11);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/artist/11"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJson)));
    }

    @Test
    public void shouldAddNewArtistAndReturn201() throws Exception{
        Artist output = new Artist(122, "daMan", "daMan", "daMan");
        Artist input = new Artist(123,"datMan", "datMan", "datMan");

        String outputArtistJson = mapper.writeValueAsString(output);
        String inputArtistJson = mapper.writeValueAsString(input);

        when(artistRepo.save(input)).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/artist")
                        .content(inputArtistJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistJson));
    }

    @Test
    public void deleteAlbumById() throws Exception{
        Artist artist = new Artist(12,"Dat Man","datMan", "@datMan");
        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/12"))
                .andExpect(status().isNoContent());
    }
//
    @Test
    public void shouldGetAllAlbumsAndReturn200() throws Exception {
        Artist album = new Artist(33, "Who Dat", "whodat", "@whodat");
        List<Artist> artistList = Arrays.asList(album);

        String expectedJson = mapper.writeValueAsString(artistList);

        doReturn(artistList).when(artistRepo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}