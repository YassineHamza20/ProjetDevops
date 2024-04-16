package tn.esprit.rh.achat;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StockTest {
    @Mock
    StockRepository stockRepository;
    @InjectMocks
    StockServiceImpl stockService;

    @Test
    public void testRetrieveAllStocksByid() {
        Stock stock = new Stock();
        stock.setIdStock(1L); // Specify the ID
        stock.setLibelleStock("lllll");
        stock.setQte(55);
        stock.setQteMin(963);

        // Mock the repository's behavior to return the sample Stock when ID 1L is passed
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        // Call the retrieveStock method
        Stock stock1 = stockService.retrieveStock(1L);

        // Assert that the retrieved stock is not null
        Assertions.assertNotNull(stock1);

        // Additional assertions if needed
        assertEquals("lllll", stock1.getLibelleStock());
        assertEquals(55, stock1.getQte());
        assertEquals(963, stock1.getQteMin());

    }
    @Test
    public void testAddStock() {

        Stock sampleStock = new Stock();
        sampleStock.setIdStock(1L); // Specify the ID
        sampleStock.setLibelleStock("Sample Stock");
        sampleStock.setQte(100);
        sampleStock.setQteMin(10);

        when(stockRepository.save(sampleStock)).thenReturn(sampleStock);


        Stock savedStock = stockService.addStock(sampleStock);


        assertEquals(1L, savedStock.getIdStock());
        assertEquals("Sample Stock", savedStock.getLibelleStock());
        assertEquals(100, savedStock.getQte());
        assertEquals(10, savedStock.getQteMin());


        verify(stockRepository).save(sampleStock);
    }
    @Test
    public void testUpdateStock() {
        // Créez un échantillon de Stock
        Stock sampleStock = new Stock();
        sampleStock.setIdStock(1L); // Spécifiez l'ID
        sampleStock.setLibelleStock("Sample Stock");
        sampleStock.setQte(100);
        sampleStock.setQteMin(10);

        // Mockez le comportement de la méthode save du référentiel pour renvoyer l'échantillon de Stock
        when(stockRepository.save(sampleStock)).thenReturn(sampleStock);

        // Appelez la méthode updateStock
        Stock updatedStock = stockService.updateStock(sampleStock);

        // Assurez-vous que le Stock mis à jour correspond aux valeurs attendues
        assertEquals(1L, updatedStock.getIdStock());
        assertEquals("Sample Stock", updatedStock.getLibelleStock());
        assertEquals(100, updatedStock.getQte());
        assertEquals(10, updatedStock.getQteMin());

        // Vérifiez que la méthode save du référentiel a été appelée
        verify(stockRepository).save(sampleStock);
    }
    @Test
    public void testRetrieveAllStocks() {

        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setLibelleStock("Stock 1");
        stock1.setQte(100);
        stock1.setQteMin(10);

        Stock stock2 = new Stock();
        stock2.setIdStock(2L);
        stock2.setLibelleStock("Stock 2");
        stock2.setQte(200);
        stock2.setQteMin(20);

        List<Stock> sampleStockList = Arrays.asList(stock1, stock2);


        when(stockRepository.findAll()).thenReturn(sampleStockList);


        List<Stock> retrievedStockList = stockService.retrieveAllStocks();


        assertEquals(2, retrievedStockList.size());
        assertEquals("Stock 1", retrievedStockList.get(0).getLibelleStock());
        assertEquals("Stock 2", retrievedStockList.get(1).getLibelleStock());


        verify(stockRepository).findAll();
    }
    @Test
    public void testDeleteStock() {

        stockService.deleteStock(1L);


        verify(stockRepository).deleteById(1L);
    }
    @Test
    public void testRetrieveStatusStock() {

        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setLibelleStock("Stock 1");
        stock1.setQte(5);
        stock1.setQteMin(10);

        Stock stock2 = new Stock();
        stock2.setIdStock(2L);
        stock2.setLibelleStock("Stock 2");
        stock2.setQte(15);
        stock2.setQteMin(10);

        List<Stock> sampleStockList = Arrays.asList(stock1, stock2);


        when(stockRepository.retrieveStatusStock()).thenReturn(sampleStockList);


        String statusMessage = stockService.retrieveStatusStock();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String msgDate = sdf.format(now);

        String expectedMessage = System.getProperty("line.separator") + msgDate + System.getProperty("line.separator") +
                ": le stock Stock 1 a une quantité de 5 inférieur à la quantité minimale a ne pas dépasser de 10" +
                System.getProperty("line.separator");


    }
}