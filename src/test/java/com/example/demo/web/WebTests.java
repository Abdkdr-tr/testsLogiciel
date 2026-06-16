package com.example.demo.web;

import com.example.demo.data.Voiture;
import com.example.demo.service.Echantillon;
import com.example.demo.service.StatistiqueImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetStatistiques() throws Exception {
        // On prépare l'enironnement
        Echantillon fauxEchantillon = new Echantillon(1,5000);
        org.mockito.Mockito.when(statistiqueImpl.prixMoyen()).thenReturn(fauxEchantillon);

        // On simule la requête GET
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/statistique"))
            .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.nombreDeVoitures").value("1"))
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.prixMoyen").value("5000"));
    }

    @Test
    public void testAjouterVoiture() throws Exception {
        // On configure l'environnement pour qu'il accepte l'ajout
        org.mockito.Mockito.doNothing().when(statistiqueImpl).ajouter(org.mockito.Mockito.any(Voiture.class));

        // On simule cette fois-ci la requête POST
        String donnee_Json = "{\"marque\":\"f\",\"prix\":100}";

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/voiture")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(donnee_Json))
            .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }

}
