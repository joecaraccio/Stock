package indy;

import StockMarket.Stock;
import StockMarket.StockFetcher;

import java.io.*;
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
        reader();
        //load in csv
    }

    //file Reader
    public void reader()
    {

        try {
            BufferedReader CSV =
                    new BufferedReader(new FileReader("stockRecord2.csv"));


            try {

                int lines = 0;
                while (CSV.readLine() != null)
                {
                    lines = lines + 1;
                }
                try {
                    BufferedReader CSV2 =
                            new BufferedReader(new FileReader("stockRecord2.csv"));
                    for (int i = 0; i < lines; i++) {
                        String current = CSV2.readLine();
                        if( i > 0 ) {
                            String[] input = current.split(",");
                            String name = input[0];
                            int quanity = Integer.parseInt(input[1]);
                            double qp = Double.parseDouble( input[2] );
                            double cp = Double.parseDouble( input[3] );
                            Stock tempstock = new Stock(name,quanity,qp,cp);
                            myStocks.add( tempstock );
                        }
                    }
                } catch (FileNotFoundException e)
                {

                }
               /*
                String current = CSV.readLine();
                    System.out.println("Current: " + current );
                    String[] input = current.split(",");
                    System.out.println("SHould be 3 or 4 " + input.length );
                    Stock tempstock = new Stock( input[0], Integer.parseInt( input[1]), Double.parseDouble( input[2]), Double.parseDouble( input[3]) );
                    myStocks.add( tempstock );
                 */
            } catch (IOException ei)
            {

            }
        }
        catch(FileNotFoundException e )
        {
            System.out.println("Reader: Error");
            e.printStackTrace();

        }
    }


    public void listStocks() {
        System.out.println("---Your Stocks----");
        System.out.println("You Currently own " + myStocks.size() + " stocks");
        for(int i = 0; i < myStocks.size(); i++ )
        {
            System.out.println("Name: " + myStocks.get(i).getSymbol() + " " + "Q:" + myStocks.get(i).quanityOwned
            + " Purchased At: " + myStocks.get(i).purchasedPrice + " Current Price: " + myStocks.get(i).currentPrice );
        }
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

    public void saveCSV()
    {
        System.out.println("Save Stock Values to CSV....");
        try
        {
            FileWriter writer = new FileWriter("stockRecord2.csv");

            writer.append("Symbol");
            writer.append(',');
            writer.append("Quanity");
            writer.append(',');
            writer.append("Purchased Price");
            writer.append(',');
            writer.append("CurrentPrice");
            writer.append('\n');


            for(int i = 0; i < myStocks.size(); i++)
            {

                writer.append( myStocks.get(i).getSymbol());
                writer.append(',');
                writer.append( Integer.toString(myStocks.get(i).quanityOwned));
                writer.append(',');
                writer.append( Double.toString( myStocks.get(i).purchasedPrice ));
                writer.append(',');
                writer.append( Double.toString( myStocks.get(i).currentPrice ));
                writer.append('\n');
            }

            //generate whatever data you want

            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
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
            stock.purchasedPrice = price;
            stock.quanityOwned = quanity;
            myStocks.add( stock );
        }

    }

}
