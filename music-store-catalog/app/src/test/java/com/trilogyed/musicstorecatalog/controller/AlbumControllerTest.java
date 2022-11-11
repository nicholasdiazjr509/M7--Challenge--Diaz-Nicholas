package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AlbumRepository albumRepo;

    @Test
    public void updateAlbumByIdAndReturn200() throws Exception {
        Album album = new Album(101, "Metallica", 2, LocalDate.ofEpochDay(1999-6-1), 3, BigDecimal.valueOf(15.99));
        String expectedJson = mapper.writeValueAsString(album);

        mockMvc.perform(put("/album/101")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAlbumById() throws Exception {
        Album album = new Album(101, "Metallica", 2, LocalDate.ofEpochDay(1999-6-1), 3, BigDecimal.valueOf(15.99));
        String expectedJson = mapper.writeValueAsString(album);

        doReturn(Optional.of(album)).when(albumRepo).findById(101);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/album/101"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJson)));
    }

    @Test
    public void ShouldAddNewAlbumAndReturnNewLabel() throws Exception{
        Album output = new Album(16, "Pray To God", 7, LocalDate.ofEpochDay(1999-11-12), 79, BigDecimal.valueOf(12.99));
        Album input = new Album("Halo", 1, LocalDate.ofEpochDay(2001-12-16), 1, BigDecimal.valueOf(14.99));
        String outputAlbumJson = mapper.writeValueAsString(output);
        String inputAlbumJson = mapper.writeValueAsString(input);

        when(albumRepo.save(input)).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/album")
                        .content(inputAlbumJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputAlbumJson));
    }

    @Test
    public void deleteAlbumByIdAndReturn200() throws Exception{
        Album artist = new Album(1, "Go For It", 1, LocalDate.ofEpochDay(2011-6-7), 1, BigDecimal.valueOf(14.99));
        mockMvc.perform(MockMvcRequestBuilders.delete("/album/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllAlbumsAndReturn200() throws Exception {
        Album album = new Album(509, "Ghost Mile", 1, LocalDate.ofEpochDay(2017-1-11), 1, BigDecimal.valueOf(13.99));
        List<Album> albumList = Arrays.asList(album);

        String expectedJson = mapper.writeValueAsString(albumList);

        doReturn(albumList).when(albumRepo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
//
//  @JsonSerialize(using = LocalDateSerializer.class)
//@JsonDeserialize(using = LocalDateDeserializer.class)
//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//private LocalDate releaseDate;

//    For a date  - this allows a client to send a string formatted as the
//    JsonFormat describes, and then the jackson mapper creates a LocalDate from it.