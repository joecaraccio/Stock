package indy;

import StockMarket.Stock;
import StockMarket.StockFetcher;

import java.util.Vector;

/**
 * Created by Joe on 2/20/2016.
 */
public class portfolio {

    public double totalValue;
    public Vector<Stock> myStocks;



    public portfolio()
    {
        totalValue = 10000; //10,000 dollars
        myStocks = new Vector<Stock>();
    }

    //looks through your portfollio ('myStocks') and updates the prices
    public void updateStockValue()
    {
        for( int i = 0; i < myStocks.size(); i++ )
        {
            Stock current = myStocks.get(i);

            Stock present = StockFetcher.getStock( current.getSymbol() );
            current.currentPrice = present.getPrice();
        }

    }


    //get Portfolio Value
    public double PortfolioValue() {
        double overalValue = 0;
        //update all stock prices
        this.updateStockValue();
        for( int i = 0; i < myStocks.size(); i ++ )
        {
            overalValue = overalValue + ( myStocks.get(i).currentPrice * myStocks.get(i).quanityOwned);
        }


        return overalValue;
    }


    public void sellStock() {

    }

    public void buyStock( String stockSymbol, int quanity)
    {
        Stock stock = StockFetcher.getStock(stockSymbol.toUpperCase());
        double price = stock.getPrice();
        if( totalValue < ( price * quanity ))
        {
            System.out.println("Can't Afford the Stock");
            System.out.println("Total Value in Portfolio is " + totalValue );
            System.out.println("Total Cost of Stock*Quanity is " + (price*quanity));
            return;
        } else {
            System.out.println("Succesfully purchased stock");
            totalValue = totalValue - ( price * quanity );
            stock.quanityOwned = quanity;
            myStocks.add( stock );
        }

    }

}
