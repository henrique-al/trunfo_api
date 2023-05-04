package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.DTO.CartaDTO;
import br.senai.sc.trunfo_api.model.Entity.Carta;
import br.senai.sc.trunfo_api.service.CartaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartaService cartaService;

    @Test
    void createCardTest() throws Exception {
        // given
        Carta carta = new Carta(
                1L,
                "País",
                1,
                1,
                1.1,
                1,
                1
        );
        String json = objectMapper.writeValueAsString(carta);

        when(cartaService.createOrUpdate(any())).thenReturn(carta);

        mockMvc.perform(post("/cards")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.populacao").value(1))
                .andExpect(jsonPath("$.area").value(1))
                .andExpect(jsonPath("$.idh").value(1.1))
                .andExpect(jsonPath("$.pib").value(1))
                .andExpect(jsonPath("$.turista").value(1));
    }

    @Test
    public void getAllCartasTest() throws Exception {
        CartaDTO cartaDTO = new CartaDTO(
                "País",
                1,
                1,
                1.1,
                1,
                1
        );

        Carta carta = new Carta();
        BeanUtils.copyProperties(cartaDTO, carta);

        when(cartaService.readAll()).thenReturn(List.of(carta));

        mockMvc.perform(get("/cards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("País"))
                .andExpect(jsonPath("$[0].populacao").value(1))
                .andExpect(jsonPath("$[0].area").value(1))
                .andExpect(jsonPath("$[0].idh").value(1.1))
                .andExpect(jsonPath("$[0].pib").value(1))
                .andExpect(jsonPath("$[0].turista").value(1));
    }

    @Test
    void getOneCardTest() throws Exception {
        Carta carta= new Carta(
                1L,
                "País",
                1,
                1,
                1.1,
                1,
                1
        );

        when(cartaService.read(1L)).thenReturn(carta);

        mockMvc.perform(get("/cards/{id}", carta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.populacao").value(1))
                .andExpect(jsonPath("$.area").value(1))
                .andExpect(jsonPath("$.idh").value(1.1))
                .andExpect(jsonPath("$.pib").value(1))
                .andExpect(jsonPath("$.turista").value(1));
    }

    @Test
    public void updateCardTest() throws Exception {
        Carta cartaNovo = new Carta(1L, "País 2", 1, 1, 1.1, 1, 1);

        when(cartaService.read(1L)).thenReturn(cartaNovo);
        when(cartaService.createOrUpdate(any())).thenReturn(cartaNovo);

        mockMvc.perform(put("/cards/{id}", cartaNovo.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaNovo))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("País 2"))
                .andExpect(jsonPath("$.populacao").value(1))
                .andExpect(jsonPath("$.area").value(1))
                .andExpect(jsonPath("$.idh").value(1))
                .andExpect(jsonPath("$.pib").value(1))
                .andExpect(jsonPath("$.turista").value(1));
    }

    @Test
    public void deleteCardTest() throws Exception {
        mockMvc.perform(delete("/cards/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}