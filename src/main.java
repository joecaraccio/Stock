import StockMarket.Stock;
import StockMarket.StockFetcher;
import StockMarket.StockTest;
import indy.StockWorplace;
import indy.nero1;
import indy.portfolio;
import indy.shuttle;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Joe on 2/19/2016.
 */
public class main {

    //our list of stocks that we want to Moniter and Test
    static String[] stocks = {"HPQ","VZ","HTCKF","SNE","ADBE","BAC","GPRO",
            "FB","GOOG","AAPL","F",
            "ARWR", "BWLD","BP","WMT","TGT"
    , "AMZN", "MSFT", "OTIV", "PXLW", "GOGO"
    };

    //N1 - opening for that day, 4 stock prices and preditct the output
    //n2 - look at prices of related stocks
    //n3 - longer but maybe time of year/tempature

    static Vector<StockWorplace> stockList = new Vector<StockWorplace>();
    static boolean marketOpen = false;

    public static void main(String[] args){
        System.out.println("Stock Machine is Running");
        portfolio p1 = new portfolio();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 30);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 4);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 30);
        Date alarmtime2 = calendar2.getTime();


        long period = 77000;

        //open the market
        Date alarmTime = calendar.getTime();
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() { // Function runs every MINUTES minutes.
                marketOpen = true;

            }
        }, alarmTime, period );

        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() { // Function runs every MINUTES minutes.
                marketOpen = false;

            }
        }, alarmtime2, period );


        System.out.println("This is alive");
        int MINUTES = 1; // The delay in minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //will not run unless market is open
                //marketOpen = true;
                //if( marketOpen == true ) {
                    //System.out.println("LETS GO");
                    // Function runs every MINUTES minutes.
                    // Run the code you want here
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    //20160222_114810
                    //2016,02,22_11:48:10
                    //System.out.println(" ");
                    //System.out.println("Time " + timeStamp );
                    //System.out.println("Running Stock Retriever Task");
                    //fbn.retrieveStockPrice();

                    //Lets move through the list
                    for (int i = 0; i < stocks.length; i++) {
                        //System.out.println("Getting " + stocks[i] );
                        StockWorplace t1 = new StockWorplace();
                        t1.stockName = stocks[i];
                        Stock st1 = StockFetcher.getStock(stocks[i]);
                        double price = st1.getPrice();
                        t1.name = st1.getName();
                        //System.out.println("Price getting" + " " + price );
                        t1.stockPrice = price;
                        t1.stockOpen = st1.getOpen();
                        t1.timeStamp = timeStamp;
                        t1.daylow = st1.getDaylow();
                        t1.dayhigh = st1.getDayhigh();
                        t1.yearhigh = st1.getWeek52high();
                        t1.yearlow = st1.getWeek52low();
                        stockList.add(t1);


                   // }

                }
                shuttle.saveList( stockList );
            }
        }, 0, 5000 );
        //1 minute = 60000
        //1000 * 60 * MINUTES
        // 1000 milliseconds in a second * 60 per minute * the MINUTES variable.


        //StockTest t = new StockTest();
        boolean running = true;

        Scanner scanner = new Scanner( System.in );

        nero1 n1 = new nero1(stockList, stocks);
        System.out.println("Welcome to JK_Stocks");
        while(running)
        {

            String input = scanner.nextLine();

            //print symbols
            if( input.equals("get symbols"))
            {
                n1.printSymbols();
            }
            if( input.equals("run neuro") )
            {
                n1.runNuero();
            }
            if( input.equals("save net") )
            {
                n1.saveCSV();
            }

            if( input.equals("ntest"))
            {
                n1.test1("FB");
            }


            //newer functions
            if( input.equals("ssss")){
                shuttle.saveList( stockList );
            }
            if( input.equals("runTest1"))
            {
                shuttle.runTests();
            }



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

            if( input.equals("ls"))
            {
                for( int i = 0; i < stockList.size(); i++ )
                {
                    System.out.println("Lets Print what we currently have");
                    System.out.println(stockList.get(i).stockName + " " + stockList.get(i).stockPrice + " " + stockList.get(i).timeStamp);
                }

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






        }//dont put any realtime stuff below this

    }


}
