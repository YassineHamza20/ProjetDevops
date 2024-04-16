package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import tn.esprit.rh.achat.controllers.OperateurController;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.services.IOperateurService;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(OperateurController.class)
public class OperateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOperateurService operateurService;

    @InjectMocks
    private OperateurController operateurController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(operateurController).build();
    }

    @Test
    public void testRetrieveAllOperateurs() throws Exception {
        Operateur operateur = new Operateur(1L, "John", "Doe", "password123", null);
        List<Operateur> allOperateurs = Arrays.asList(operateur);

        when(operateurService.retrieveAllOperateurs()).thenReturn(allOperateurs);

        mockMvc.perform(get("/operateur/retrieve-all-operateurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idOperateur").value(operateur.getIdOperateur()))
                .andExpect(jsonPath("$[0].nom").value("John"))
                .andExpect(jsonPath("$[0].prenom").value("Doe"))
                .andDo(print());
    }

    @Test
    public void testRetrieveOperateur() throws Exception {
        Long operateurId = 1L;
        Operateur operateur = new Operateur(operateurId, "John", "Doe", "password123", null);

        when(operateurService.retrieveOperateur(operateurId)).thenReturn(operateur);

        mockMvc.perform(get("/operateur/retrieve-operateur/{operateur-id}", operateurId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOperateur").value(operateurId))
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andDo(print());
    }

    @Test
    public void testAddOperateur() throws Exception {
        Operateur operateur = new Operateur(1L, "John", "Doe", "password123", null);
        String operateurJson = "{\"idOperateur\":1,\"nom\":\"John\",\"prenom\":\"Doe\",\"password\":\"password123\"}";

        when(operateurService.addOperateur(any(Operateur.class))).thenReturn(operateur);

        mockMvc.perform(post("/operateur/add-operateur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(operateurJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andDo(print());
    }

    @Test
    public void testRemoveOperateur() throws Exception {
        Long operateurId = 1L;

        doNothing().when(operateurService).deleteOperateur(operateurId);

        mockMvc.perform(delete("/operateur/remove-operateur/{operateur-id}", operateurId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testModifyOperateur() throws Exception {
        Operateur updatedOperateur = new Operateur(1L, "John", "Doe", "newpassword123", null);
        String operateurJson = "{\"idOperateur\":1,\"nom\":\"John\",\"prenom\":\"Doe\",\"password\":\"newpassword123\"}";

        when(operateurService.updateOperateur(any(Operateur.class))).thenReturn(updatedOperateur);

        mockMvc.perform(put("/operateur/modify-operateur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(operateurJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword123"))
                .andDo(print());
    }
}
