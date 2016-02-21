import StockMarket.StockTest;
import indy.portfolio;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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
                System.out.println("save --save stocks to csv");
                System.out.println("list Stocks");
                System.out.println(" ");
                System.out.println(" ");
            }
            //save
            if(input.equals("save"))
            {
                System.out.println("Saving File....");
                p1.saveCSV();
            }

            //print portfoliol value
            if(input.equals("portfolio value"))
            {
                System.out.println("Money in Porfolio: " + "$" + p1.totalValue );
                System.out.println("Stock Value: " + "$" + p1.PortfolioValue() );
            }

            if(input.equals("list Stocks"))
            {
                p1.listStocks();
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
                p1.saveCSV();

            }



            //timer functions
            Timer timer = new Timer();
            TimerTask hourlyTask = new TimerTask() {
                @Override
                public void run () {
                    System.out.println("Running Stock Retriever Task");
                    // your code here...
                }
            };

            // schedule the task to run starting now and then every hour...
            timer.schedule (hourlyTask, 0l, 1000*60*60);


        }//dont put any realtime stuff below this

    }

}
