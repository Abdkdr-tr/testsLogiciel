package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

@SpringBootTest
class StatistiqueTests {

	@MockBean
	StatistiqueImpl statistiqueImpl;

	@Test
	public void avecMockito() throws Exception {
        Voiture v1 = new Voiture("Tesla", 2000);
        Voiture v2 = new Voiture("Toyota", 4000);

		StatistiqueImpl resultat = Mockito.mock(StatistiqueImpl.class);
		// 1 argument, returns void
		doNothing().when(resultat).ajouter(v1);
        // 1 argument, returns void
		doNothing().when(resultat).ajouter(v2);
		// 0 argument, throws exception
		doThrow(Exception.class).when(resultat).ajouter(null);
		// 1 argument, return void
		doNothing().when(resultat).prixMoyen();
	}
}