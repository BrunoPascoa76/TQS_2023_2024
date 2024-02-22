package ua.bp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class StockTest {

    @InjectMocks
    private StocksPortfolio portfolio;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setupPortfolio() {
        portfolio = new StocksPortfolio(stockmarket);
    }

    @Mock
    private IStockmarketService stockmarket;

    @Test
    public void totalValueTestJunit() {
        portfolio.addStock(new Stock("Tesla", 3));
        portfolio.addStock(new Stock("Amazon", 10));

        Mockito.when(stockmarket.lookUpPrice("Amazon")).thenReturn(10.0);
        Mockito.when(stockmarket.lookUpPrice("Tesla")).thenReturn(7.0);

        assertEquals(121.0, portfolio.totalValue());
    }

    @Test
    public void totalValueTestHamcrest() {
        portfolio.addStock(new Stock("Tesla", 3));
        portfolio.addStock(new Stock("Amazon", 10));

        Mockito.when(stockmarket.lookUpPrice("Amazon")).thenReturn(10.0);
        Mockito.when(stockmarket.lookUpPrice("Tesla")).thenReturn(7.0);

        assertThat(portfolio.totalValue(), equalTo(121.0));
    }

}
