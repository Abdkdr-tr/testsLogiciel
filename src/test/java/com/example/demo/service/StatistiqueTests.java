package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class StatistiqueTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Test
    public void avecMokito() throws Exception {
        Voiture v1 = new Voiture("Peugeot", 2000);
        Voiture v2 = new Voiture("Porsche", 10000);

        doNothing().when(statistiqueImpl).ajouter(v1);
        doNothing().when(statistiqueImpl).ajouter(v2);

        // Si on cherche à ajouter un élément null qui va poser problème au code, on renvoie vers une exception
        doThrow(IllegalArgumentException.class).when(statistiqueImpl).ajouter(null);

        // On créé une variable qui dit ce que devrait renvoyer la fonction prixMoyen()
        Echantillon fauxEchantillon = new Echantillon(2,6000);

        // On explique ce qu'il faudrait renvoyer lorsqu'on appelle la fonction dans notre cas
        when(statistiqueImpl.prixMoyen()).thenReturn(fauxEchantillon);

        // On vérifie que la fonction prixMoyen renvoie bel et bien notre faux échantillon qui a le bon résultat
        Echantillon resultat = statistiqueImpl.prixMoyen();
        assertEquals(resultat.getNombreDeVoitures(), 2, "Doit renvoyer 2 car c'est ce qu'on a défini dans notre faux échantillon");
        assertEquals(resultat.getPrixMoyen(), 6000, "Doit renvoyer 6000 car c'est ce qu'on a défini dans notre faux échantillon");
        
        // On test l'exception lorsque l'on veut ajouter un élément null
        assertThrows(IllegalArgumentException.class, ()->{ statistiqueImpl.ajouter(null);});
    }

}
