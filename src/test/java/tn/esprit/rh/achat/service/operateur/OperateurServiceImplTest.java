package tn.esprit.rh.achat.service.operateur;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;
import tn.esprit.rh.achat.controllers.OperateurController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplTest {
    @Mock
    private OperateurRepository operateurRepositoryMock;

    @InjectMocks
    private OperateurServiceImpl operateurService;

    @InjectMocks
    private OperateurController operateurController;

    private MockMvc mockMvc;

    public OperateurServiceImplTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(operateurController).build();
    }

    @Test
    public void testRetrieveAllOperators() {
        List<Operateur> expectedOperators = Arrays.asList(
                new Operateur(1L, "Operator A", "A", "yassine", new HashSet<>()),
                new Operateur(2L, "Operator B", "B", "yassine", new HashSet<>())
        );

        when(operateurRepositoryMock.findAll()).thenReturn(expectedOperators);
        List<Operateur> actualOperators = operateurService.retrieveAllOperateurs();

        assertEquals(expectedOperators.size(), actualOperators.size());
        assertEquals(expectedOperators, actualOperators);
    }

    @Test
    void addOperator() {
        Operateur newOperateur = new Operateur(1L, "operator 1", "yass", "yassine", new HashSet<>());
        when(operateurRepositoryMock.save(any(Operateur.class))).thenReturn(newOperateur);

        Operateur addedOperator = operateurController.addOperateur(newOperateur);
        assertEquals(newOperateur, addedOperator);
    }

    @Test
    void removeOperateur() throws Exception {
        Long operateurId = 1L;
        mockMvc.perform(delete("/operateur/remove-operateur/{operateur-id}", operateurId))
                .andExpect(status().isOk());
    }

    @Test
    void modifyOperateur() {
        Operateur modifiedOperateur = new Operateur(1L, "operator 1", "yass", "yassine", new HashSet<>());
        when(operateurRepositoryMock.save(any(Operateur.class))).thenReturn(modifiedOperateur);

        Operateur updatedOperator = operateurController.modifyOperateur(modifiedOperateur);
        assertEquals(modifiedOperateur, updatedOperator);
    }
}
