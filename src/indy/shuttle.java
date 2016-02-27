package indy;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

import java.io.*;
import java.util.Vector;

/**
 * Created by Joe on 2/23/2016.
 */
public class shuttle {
    public static Vector<StockWorplace> stocks;
    public static int i = 0;

    public static Vector<HistoryTest> history;

    public static void histTest()
    {
        history = new Vector<HistoryTest>();

        try {
            BufferedReader CSV =
                    new BufferedReader(new FileReader("yahoo.csv"));
            try {

                int lines = 0;
                while (CSV.readLine() != null)
                {
                    lines = lines + 1;
                }
                try {
                    BufferedReader CSV2 =
                            new BufferedReader(new FileReader("yahoo.csv"));
                    for (int i = 0; i < lines; i++) {
                        //data in csv is formated this way
                        //date,open,high,low,close,volume,adju close
                        String current = CSV2.readLine();
                        if( i > 0 ) {
                            String[] input = current.split(",");
                            HistoryTest w1 = new HistoryTest();
                            w1.date = input[0];
                            w1.open = Double.valueOf( input[1]);
                            w1.high = Double.valueOf( input[2]);
                            w1.low = Double.valueOf( input[3]);
                            w1.close = Double.valueOf( input[4] );
                            w1.volume = Double.valueOf( input[5]);
                            w1.adjclose = Double.valueOf( input[6] );
                            history.add( w1 );
                        }
                    }
                } catch (FileNotFoundException e)
                {

                }
            } catch (IOException ei)
            {

            }
        }
        catch(FileNotFoundException e )
        {
            System.out.println("Reader: Error");
            e.printStackTrace();

        }
        System.out.println("First lets read in the data");
        System.out.println("We have " + history.size() + " results");



        int maxIterations = 10000; //number of times the data is sent through Atticus (Network)
        NeuralNetwork neuralNet = new MultiLayerPerceptron(16,33,1); //creates Atticus (The Network) -- MultiLayerPerceptron
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//The amount of exceptable error -- very small
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);// applies a greater or lesser portion of the respective adjustment to the old weight
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//sets iterations

        TrainingSet trainingSet = new TrainingSet(); //creates Tset



        int data = history.size();
        int tests = data/6;
        int testCount = 0;
        //System.out.println("We are going to have " + tests );

        for( int i =  history.size() - 1; i > 0; i = i - 6 )
        {
            System.out.println("For Loop");
            HistoryTest h = history.get(i);
            double[] input = new double[16];
            System.out.println( h.open );
            input[0] = h.open;
            input[1] = h.high;
            input[2] = h.low;
            HistoryTest h1 = history.get( i - 1 );
            input[3] = h1.open;
            input[4] = h1.high;
            input[5] = h1.low;
            HistoryTest h2 = history.get( i - 2 );
            input[ 6 ] = h2.open;
            input[ 7 ] = h2.high;
            input[ 8 ] = h2.low;
            HistoryTest h3 = history.get( i - 3 );
            input[ 9 ] = h3.open;
            input[ 10 ] = h3.high;
            input[ 11 ] = h3.low;
            HistoryTest h4 = history.get( i - 4 );
            input[ 12 ] = h4.open;
            input[ 13 ] = h4.high;
            input[ 14 ] = h4.low;
            HistoryTest h5 = history.get( i - 5);
            input[15] = h5.open;


            System.out.println("-----");
            System.out.println( i );
            System.out.println( i - 1 );
            System.out.println( i - 2 );
            System.out.println( i - 3 );
            System.out.println( i - 4 );
            System.out.println( i - 5 );

            double[] output = new double[1];
            output[0] = h5.adjclose;
            System.out.println("------");

            trainingSet.addElement(new SupervisedTrainingElement( input, output ));

        }
       // trainingSet.addElement(new SupervisedTrainingElement( new double[]{ 20, 40 , 30 , 23}, new double[]{23432} ));
        System.out.println("Training set is: " + trainingSet.size());
        System.out.println("Finished with For Loop");
        neuralNet.learnInSameThread(trainingSet);//starts learning -- comment out if you use live data
        System.out.println("Output");
        TrainingSet testSet = new TrainingSet();//trainingSet objects used for test sets as well in this program
        double[] inputO = new double[16];
        inputO[0] = 27.91;
        inputO[1] = 29.23;
        inputO[2] = 27.71;

        inputO[3] = 29.06;
        inputO[4] = 29.14;
        inputO[5] = 27.73;

        inputO[6] = 27.61;
        inputO[7] = 27.97;
        inputO[8] = 26.48;

        inputO[9]= 26.64;
        inputO[10] = 27.69;
        inputO[11] = 26.51;

        inputO[12] = 27.11;
        inputO[13] = 27.81;
        inputO[14] = 26.84;
        inputO[15] = 26.46;



        testSet.addElement(new TrainingElement(inputO)); //add test data

        for (TrainingElement testElement : testSet.trainingElements()) { //for test element in test elements
            neuralNet.setInput(testElement.getInput()); //gives input to Atticus
            neuralNet.calculate(); //Atticus thinks
            Vector<Double> networkOutput = neuralNet.getOutput(); //get output
            System.out.print("Input: " + testElement.getInput());
            System.out.println(" Output: " + networkOutput);
        }
    }//end of method

    //pretested hardcoded test
    //this is for GE - 28.96
    public void pretest1()
    {
        //predict the close of each stock given
        //1open, 1high, 1close, 2open, 2high, 2close, 3open,3high,3close,4open, 4high -- output 4 close
       // trainingSet.addElement(new SupervisedTrainingElement(new double[]{31.28,31.44,31.05,30.86,31.49, }, new double[]{10610.33D / daxmax}));





    }


    public static void saveList(Vector<StockWorplace> list )
    {
        i = 3;
        System.out.println("Save List");
        try
        {
            FileWriter writer = new FileWriter("stockrecord.csv");


            for(int i = 0; i < list.size(); i++)
            {

                writer.append( list.get(i).stockName );
                writer.append(',');
                writer.append( String.valueOf(list.get(i).stockPrice) );
                writer.append(',');
                writer.append( String.valueOf(list.get(i).timeStamp ) );
                writer.append(',');
                writer.append( String.valueOf(list.get(i).stockOpen ) );
                writer.append(',');
                // writer.append( String.valueOf(stocksPrice.get(i).) );
                //writer.append(',');
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

    //reads our current list
    public static void readList()
    {

        try {
            BufferedReader CSV =
                    new BufferedReader(new FileReader("stockrecord.csv"));
            try {

                int lines = 0;
                while (CSV.readLine() != null)
                {
                    lines = lines + 1;
                }
                try {
                    BufferedReader CSV2 =
                            new BufferedReader(new FileReader("stockrecord.csv"));
                    for (int i = 0; i < lines; i++) {
                        String current = CSV2.readLine();
                        if( i > 0 ) {
                            String[] input = current.split(",");
                            StockWorplace w1 = new StockWorplace();
                            w1.stockName = input[0];
                            w1.stockPrice =Double.valueOf(input[1]);
                            w1.timeStamp = input[2];
                            w1.stockOpen = Double.valueOf(input[3]);
                            stocks .add( w1 );
          }
                    }
                } catch (FileNotFoundException e)
                {

                }
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


    //run a bunch of overal tests and analyze best results
    public static void runTests()
    {
        System.out.println(i);
        //run tests

        //input the same price

    }



    //Test1
    //stock open price
    //test stock from minute 10
    //test stock from minute 20
    //test stock from minute 30
    //test stock from minute 40
    //predict stock at minute 50

    //20160223_093031    <-- date model
    //we need to be able to seperate those by time
    //idea for seperating by time:
    //we know if we are querying every 5 seconds that we are going to get 1
    //entry for every 5 seconds. therefor we know how many indexs we can skip
    //to get a daily interval

    //so lets say we called every 1 minue
    //stock market is open 9:30 to 4
    //60 times an hour
    //Total amount of time market is open is 6:30 hours
    // therefore in a typical time that means we will collect 390 prices per time

    //formula for collecting every 10 minutes
    //min1-min2-min3-min4-min5-min6-min7-min8-min9-min10
    //so we would want min1 and min11, so add 10 to our index each time

    //symbols and then data that will be tested
    public static void test1(String symbol, double p1, double p2, double p3,double p4, double yearHigh, double op )
    {
        Vector<StockWorplace> tempList = new Vector<StockWorplace>();
        //sort our list by stock symbol
        for( int i = 0; i < stocks.size(); i++ )
        {
            if( stocks.get(i).stockName.equals( symbol )  )
            {
                tempList.add( stocks.get(i) );
                return;
            }
        }
        int maxIterations = 10000; //number of times the data is sent through Atticus (Network)
        NeuralNetwork neuralNet = new MultiLayerPerceptron(6,13,1); //creates Atticus (The Network) -- MultiLayerPerceptron
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//The amount of exceptable error -- very small
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);// applies a greater or lesser portion of the respective adjustment to the old weight
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//sets iterations

        TrainingSet trainingSet = new TrainingSet(); //creates Tset




        int inputAmount = 6; //price1, price2, price3, price4, YearHigh, openingPrice
        int outputAmount = 1; //output
        int testAmounts = tempList.size()/(inputAmount + outputAmount);
        int testCount = 0;
        while( testAmounts > 0 ) //each test, we subtract one
        { double[] inputs = new double[6];
            inputs[0] = tempList.get( ( testCount * 40 ) + 0  ).stockPrice;
            inputs[1] = tempList.get( ( testCount * 40 ) + 1 + ( 1 * 10) ).stockPrice;
            inputs[2] = tempList.get( ( testCount * 40 ) + 1 + ( 2 * 10) ).stockPrice;
            inputs[3] = tempList.get( ( testCount * 40 ) + 1 + ( 3 * 10) ).stockPrice;
            //year high
            inputs[4] = tempList.get( ( testCount * 40 ) + 1 + ( 3 * 10) ).yearhigh;
            //opening price
            inputs[5] = tempList.get( ( testCount * 40 ) + 1 + ( 3 * 10) ).stockOpen;

            System.out.println("Just a test to see what are numbers are at");
            System.out.println( ( testCount * 40 ) + 0 );
            System.out.println( ( testCount * 40 ) + 1 + ( 1 * 10) );
            System.out.println( ( testCount * 40 ) + 1 + ( 2 * 10) );

            //trainingSet.addElement(new SupervisedTrainingElement(new double[]{9329.87D / daxmax, 8980.71D / daxmax, 8937.98D / daxmax, 8887.89D / daxmax}, new double[]{8854.4D / daxmax}));


            double[] output = new double[1];
            output[0] = tempList.get( ( testCount * 40 ) + 1 + ( 4 * 10) ).stockPrice;

            trainingSet.addElement(new SupervisedTrainingElement( inputs , output ));


            testCount++;
            testAmounts--;
        }
        neuralNet.learnInSameThread(trainingSet);//starts learning -- comment out if you use live data
        //Guessing you can split training between multiple threads for faster speed?

        //uncomment this if fetching live data
        //neuralNet.learnInSameThread(updater.getTrainingSet());




    }//end test1



    public static void test3(String symbol )
    {
        Vector<StockWorplace> tempList = new Vector<StockWorplace>();
        //sort our list by stock symbol
        for( int i = 0; i < stocks.size(); i++ )
        {
            if( stocks.get(i).stockName.equals( symbol )  )
            {
                tempList.add( stocks.get(i) );
                return;
            }
        }

        System.out.println("Lets see our TempList to Ensure that is operating correctly");
        System.out.println("Temp list Size " + tempList.size() );
        System.out.println("Print out all the Temp lists and their prices");
        for( int i = 0; i < tempList.size(); i++ )
        {
            System.out.println( "Name: " + tempList.get(i).stockName + "   " + tempList.get(i).stockPrice );
        }

        //for this test we are going to test 5 prices leading up to the 6th price
        //attempting to guess the 6th price

        //so each needs 5 inputs and 1 output
        //therefore we can divide our total list by 6 to find how many entrys we can make
        int totalSize = tempList.size();
        int testsAmount = totalSize/6;
        int testCount = 0;
        while( testsAmount > 0 )
        {
            System.out.println("------------------------");
            //first entry is going to be 0,1,2,3,4,5
            //second entry is going to be 6,7,8,9,10,11
            System.out.println( tempList.get( 0 + ( 6 * testCount)).stockPrice + tempList.get( 1 ).stockPrice + tempList.get( 2 ).stockPrice + tempList.get( 3 ).stockPrice + tempList.get( 4 ).stockPrice + tempList.get( 5 ).stockPrice + tempList.get( 6 ).stockPrice );

            testsAmount--; //subtracts one from test amount
            testCount++; //multiplyer
        }



        int maxIterations = 10000; //number of times the data is sent through Atticus (Network)
        NeuralNetwork neuralNet = new MultiLayerPerceptron(6,13,1); //creates Atticus (The Network) -- MultiLayerPerceptron
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//The amount of exceptable error -- very small
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);// applies a greater or lesser portion of the respective adjustment to the old weight
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//sets iterations

        TrainingSet trainingSet = new TrainingSet(); //creates Tset




        int inputAmount = 6; //price1, price2, price3, price4, YearHigh, openingPrice
        int outputAmount = 1; //output
        int testAmounts = tempList.size()/(inputAmount + outputAmount);
        int testCoun2t = 0;
        while( testAmounts > 0 ) //each test, we subtract one
        { double[] inputs = new double[6];
            inputs[0] = tempList.get( ( testCount * 40 ) + 0  ).stockPrice;
            inputs[1] = tempList.get( ( testCount * 40 ) + 1 + ( 1 * 10) ).stockPrice;
            inputs[2] = tempList.get( ( testCount * 40 ) + 1 + ( 2 * 10) ).stockPrice;
            inputs[3] = tempList.get( ( testCount * 40 ) + 1 + ( 3 * 10) ).stockPrice;
            //year high
            inputs[4] = tempList.get( ( testCount * 40 ) + 1 + ( 3 * 10) ).yearhigh;
            //opening price
            inputs[5] = tempList.get( ( testCount * 40 ) + 1 + ( 3 * 10) ).stockOpen;

            System.out.println("Just a test to see what are numbers are at");
            System.out.println( ( testCount * 40 ) + 0 );
            System.out.println( ( testCount * 40 ) + 1 + ( 1 * 10) );
            System.out.println( ( testCount * 40 ) + 1 + ( 2 * 10) );

            //trainingSet.addElement(new SupervisedTrainingElement(new double[]{9329.87D / daxmax, 8980.71D / daxmax, 8937.98D / daxmax, 8887.89D / daxmax}, new double[]{8854.4D / daxmax}));


            double[] output = new double[1];
            output[0] = tempList.get( ( testCount * 40 ) + 1 + ( 4 * 10) ).stockPrice;

            trainingSet.addElement(new SupervisedTrainingElement( inputs , output ));


            testCount++;
            testAmounts--;
        }
        neuralNet.learnInSameThread(trainingSet);//starts learning -- comment out if you use live data
        //Guessing you can split training between multiple threads for faster speed?

        //uncomment this if fetching live data
        //neuralNet.learnInSameThread(updater.getTrainingSet());




    }//end test1




    //Test 2
    //i: Stock price
    //i: Overal Sector Price
    //i: volume
    //i: year high
    //i: year low
    //i: day low
    //i: day high
    public static void test2(String symbol)
    {

    }

}
