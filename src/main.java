import StockMarket.StockTest;
import indy.portfolio;

import java.util.Scanner;

/**
 * Created by Joe on 2/19/2016.
 */
public class main {

    public static void main(String[] args){
        System.out.println("Stock Machine is Running");
        portfolio p1 = new portfolio();
        //StockTest t = new StockTest();
        boolean running = true;

        Scanner scanner = new Scanner( System.in );

        System.out.println("Welcome to JK_Stocks");
        while(running)
        {
            String input = scanner.nextLine();
            //System.out.println("Input = " + input );


            if( input.equals("help"))
            {
                System.out.println("-----------------------------");
                System.out.println("---Arguments for JK_Stocks---");
                System.out.println("buy STOCK_NAME QUANITY");
                System.out.println("portfolio value");
                System.out.println("list Stocks");
                System.out.println(" ");
                System.out.println(" ");
            }
            //print portfoliol value
            if(input.equals("portfolio value"))
            {
                System.out.println("Money in Porfolio: " + "$" + p1.totalValue );
                System.out.println("Stock Value: " + "$" + p1.PortfolioValue() );
            }

            if(input.equals("list Stocks"))
            {

            }

            //buying Stock
            //command line
            if(input.substring(0,3).equals("buy"))
            {
                //buy FB 10
                //lets buy this stock
                String[] arr = input.split(" ");
                int quan = Integer.parseInt( arr[2] );
                p1.buyStock(arr[1].toString(), quan );
            }


        }//dont put any realtime stuff below this

    }

}
