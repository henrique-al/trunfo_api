package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.DTO.UsuarioDTO;
import br.senai.sc.trunfo_api.model.Entity.Usuario;
import br.senai.sc.trunfo_api.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void createUserTest() throws Exception {
        Usuario usuario = new Usuario(1L, "U1", "123", 1, 1, "foto", null);

        String json = objectMapper.writeValueAsString(usuario);

        when(usuarioService.createOrUpdate(any())).thenReturn(usuario);

        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(usuario));
    }

    @Test
    public void getAllUsuariosTest() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO("U1", "123", "foto");

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        when(usuarioService.readAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("U1"))
                .andExpect(jsonPath("$[0].senha").value("123"))
                .andExpect(jsonPath("$[0].foto").value("foto"));
    }

    @Test
    void getOneUserTest() throws Exception {
        Usuario usuario = new Usuario(1L, "U1", "123", 1, 1, "foto", null);

        when(usuarioService.read(1L)).thenReturn(usuario);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(usuario));
    }

    @Test
    public void updateUserTest() throws Exception {
        Usuario usuarioNovo = new Usuario(1L, "U1", "123", 1, 1, "foto", null);

        when(usuarioService.read(1L)).thenReturn(usuarioNovo);
        when(usuarioService.createOrUpdate(any())).thenReturn(usuarioNovo);

        mockMvc.perform(put("/users/{id}", usuarioNovo.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioNovo))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(usuarioNovo));
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}