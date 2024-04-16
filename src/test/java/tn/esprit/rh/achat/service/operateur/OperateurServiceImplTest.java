package tn.esprit.rh.achat.service.operateur;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.runner.RunWith;

import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tn.esprit.rh.achat.controllers.OperateurController;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplTest {
    @Mock
    private OperateurRepository operateurRepositoryMock;
    @InjectMocks
    private OperateurServiceImpl operateurService = new OperateurServiceImpl();
    @InjectMocks
    private OperateurController operateurController = new OperateurController();
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(operateurController).build();


    @Test
    public void testRetrieveAllOperators() {
        List<Operateur> expectedOperators = Arrays.asList(
                new Operateur(1L, "Operator A","A","yassine", new HashSet<>()),
                new Operateur(2L, "Operator B","B","yassine", new HashSet<>())
        );

        when(operateurRepositoryMock.findAll()).thenReturn(expectedOperators);
        List<Operateur> actualOperators = operateurService.retrieveAllOperateurs();

        assertEquals(expectedOperators.size(), actualOperators.size());
        assertEquals(expectedOperators, actualOperators);
    }


    @Test
    void addOperator() {
        Operateur newOperateur = new Operateur(1L, "operator 1", "yass", "yassine", new HashSet<>());
        when(operateurService.addOperateur(newOperateur)).thenReturn(new Operateur());

        Operateur addedOperator = operateurController.addOperateur(newOperateur);
        assertEquals(newOperateur, addedOperator);
    }

    @Test
    void removeOperateur() throws Exception {
        Long OperateurId = 1L;

        mockMvc.perform(delete("/operatot/{operatorId}", OperateurId))
                .andExpect(status().isOk());
    }

    @Test
    void modifyOperateur() {
        Operateur modifiedOperateur = new Operateur(1L,"operator 1" ,"yass" ,"yassine",new HashSet<>());
        when(operateurService.updateOperateur(modifiedOperateur)).thenReturn(modifiedOperateur);
        Operateur updatedOperator  = operateurController.modifyOperateur(modifiedOperateur);
        assertEquals(modifiedOperateur, updatedOperator);
    }


}
