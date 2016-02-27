package indy;

import StockMarket.Stock;
import StockMarket.StockFetcher;
import com.sun.deploy.config.VerboseDefaultConfig;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Joe on 2/20/2016.
 */
public class nero1 {
    //this class is used to predict the stocka t the next hour

    private String stockname;
    private Vector<StockWorplace> stocksPrice;
    private String[] symbols;


    public nero1(  Vector<StockWorplace> stocksPrice1 , String[] stockSymbols)
    {
        symbols = stockSymbols;
        stocksPrice = stocksPrice1;
        System.out.println( "Stock List Current Size " + stocksPrice.size() );
        //unddate
        reader();
        System.out.println( "Stock List Begining Size " + stocksPrice.size() );
    }


    //file Reader
    //would be called to initilized date

    public void reader()
    {

        try {
            BufferedReader CSV =
                    new BufferedReader(new FileReader("record.csv"));


            try {

                int lines = 0;
                while (CSV.readLine() != null)
                {
                    lines = lines + 1;
                }
                try {
                    BufferedReader CSV2 =
                            new BufferedReader(new FileReader("record.csv"));
                    for (int i = 0; i < lines; i++) {
                        String current = CSV2.readLine();
                        if( i > 0 ) {
                            String[] input = current.split(",");
                            StockWorplace w1 = new StockWorplace();
                            w1.stockName = input[0];
                            w1.stockPrice =Double.valueOf(input[1]);
                            w1.timeStamp = input[2];
                            w1.stockOpen = Double.valueOf(input[3]);
                           stocksPrice.add( w1 );

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

    //writes StockPrice array to a file
    public void writer()
    {

    }

    //retrieve the test for the inputed the stocksymbol
    public void test1( String stockSymbol )
    {
        System.out.println("Running Test1 for " + stockSymbol);
        //programmatically setting the options seen in Neuroph Studio
        int maxIterations = 10000; //number of times the data is sent through Atticus (Network)
        NeuralNetwork neuralNet = new MultiLayerPerceptron(4,9,1); //creates Atticus (The Network) -- MultiLayerPerceptron
        //apparently formuly is for hidden layer neurons = input*2+1
        //4 input neurons, 9 hidden layer neurons, 1 output **These will change**
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//The amount of exceptable error -- very small
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);// applies a greater or lesser portion of the respective adjustment to the old weight
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//sets iterations

        TrainingSet trainingSet = new TrainingSet(); //creates Tset
        int amountOfTests = 0;
        Vector<StockWorplace> tempList = new Vector<StockWorplace>();
        //find all the stock symbols that match
        for( int i = 0; i < stocksPrice.size(); i++ )
        {
            //if we have the correct symbol add it to the temp list
            if( stocksPrice.get(i).stockName.equals(stockSymbol) )
            {
                tempList.add( stocksPrice.get(i) );
            }

        } //end of for loop
        System.out.println("Joe Test here");
        System.out.println( stocksPrice.size() );
        System.out.println( tempList.size() );

        int datasize = tempList.size();
        int testAmount = datasize/5; //how many times we can run a complete test
        int testCount = 0;
        while(testCount < testAmount )
        {
            Double[] input = new Double[]{
            tempList.get( 0 + ( testCount * 5 ) ).stockPrice,
            tempList.get( 1 + ( testCount * 5 ) ).stockPrice,
            tempList.get( 2 + ( testCount * 5 ) ).stockPrice,
            tempList.get( 3 + ( testCount * 5 ) ).stockPrice

            };

            Double[] output = new Double[]{
                    tempList.get( 4 + ( testCount * 5 ) ).stockPrice
            };
            //
            // trainingSet.addElement(new SupervisedTrainingElement(input,output );

            testCount++;
        }
    }

    //prints the symbols we are currently tracking
    public void printSymbols()
    {
        System.out.println("--Retrieving Stock Symbols--");
        for( int i = 0; i < symbols.length; i++ )
        {
            System.out.println(symbols[i]);
        }
    }

    public void runNuero(){
        System.out.println("Run Nuero");
        //Time Stamp --pretty useless at the moment
        System.out.println("Time stamp N1:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

        //programmatically setting the options seen in Neuroph Studio
        int maxIterations = 10000; //number of times the data is sent through Atticus (Network)
        NeuralNetwork neuralNet = new MultiLayerPerceptron(4,9,1); //creates Atticus (The Network) -- MultiLayerPerceptron
        //4 input neurons, 9 hidden layer neurons, 1 output **These will change**
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);//The amount of exceptable error -- very small

        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);// applies a greater or lesser portion of the respective adjustment to the old weight


        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);//sets iterations

        TrainingSet trainingSet = new TrainingSet(); //creates Tset

        //Uncomment this for fetching live data
//        StockDataFetcher yahooFetch = new StockDataFetcher();
//        TrainingDataUpdater updater = new TrainingDataUpdater();
//        yahooFetch.run();
//        String[] yahooData = yahooFetch.getValuesRow();
//        updater.setValuesRow(yahooData);
//



        double daxmax = 100000.0D;//Normalizer    data/normalizer = 0.0 <= x >=1.0

        //Hard coded training data
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10084.61D / daxmax, 10174.80D / daxmax, 10185.42D / daxmax, 10213.01D / daxmax}, new double[]{10610.33D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10791.18D / daxmax, 10761.37D / daxmax, 10728.16D / daxmax, 10867.19D / daxmax}, new double[]{10842.52D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10749.98D / daxmax, 10945.35D / daxmax, 10957.06D / daxmax, 10847.50D / daxmax}, new double[]{10872.98D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10993.24D / daxmax, 10846.96D / daxmax, 10843.68D / daxmax, 10895.74D / daxmax}, new double[]{10764.57D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10810.92D / daxmax, 10901.74D / daxmax, 11061.88D / daxmax, 11123.81D / daxmax}, new double[]{11105.92D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{11064.78D / daxmax, 10958.40D / daxmax, 11180.94D / daxmax, 11277.91D / daxmax}, new double[]{11268.31D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{11422.47D / daxmax, 11301.51D / daxmax, 11173.79D / daxmax, 10770.16D / daxmax}, new double[]{10832.42D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10872.68D / daxmax, 10719.70D / daxmax, 10547.52D / daxmax, 10572.86D / daxmax}, new double[]{10406.71D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10239.80D / daxmax, 10476.06D / daxmax, 10685.30D / daxmax, 10639.98D / daxmax}, new double[]{10607.20D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10598.19D / daxmax, 10623.56D / daxmax, 10748.37D / daxmax, 10744.96D / daxmax}, new double[]{10855.17D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{10485.81D / daxmax, 10373.27D / daxmax, 10288.68D / daxmax, 10144.17D / daxmax}, new double[]{10010.47D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{9814.04D / daxmax, 9832.82D / daxmax, 10112.35D / daxmax, 9836.85D / daxmax}, new double[]{9778.36D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{9542.60D / daxmax, 9722.64D / daxmax, 9430.13D / daxmax, 9400.69D / daxmax}, new double[]{9762.55D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{9790.44D / daxmax, 9599.56D / daxmax, 9781.42D / daxmax, 9826.28D / daxmax}, new double[]{9772.58D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{9823.73D / daxmax, 9721.18D / daxmax, 9541.53D / daxmax, 9522.67D / daxmax}, new double[]{9375.29D / daxmax}));
        trainingSet.addElement(new SupervisedTrainingElement(new double[]{9329.87D / daxmax, 8980.71D / daxmax, 8937.98D / daxmax, 8887.89D / daxmax}, new double[]{8854.4D / daxmax}));

        neuralNet.learnInSameThread(trainingSet);//starts learning -- comment out if you use live data
        //Guessing you can split training between multiple threads for faster speed?

        //uncomment this if fetching live data
        //neuralNet.learnInSameThread(updater.getTrainingSet());


        System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date())); //just marks end of training

        TrainingSet testSet = new TrainingSet();//trainingSet objects used for test sets as well in this program
        testSet.addElement(new TrainingElement(new double[]{9329.87D / daxmax, 8980.71D / daxmax, 8937.98D / daxmax,8887.89D / daxmax})); //add test data

        for (TrainingElement testElement : testSet.trainingElements()) { //for test element in test elements
            neuralNet.setInput(testElement.getInput()); //gives input to Atticus
            neuralNet.calculate(); //Atticus thinks
            Vector<Double> networkOutput = neuralNet.getOutput(); //get output
            System.out.print("Input: " + testElement.getInput());
            System.out.println(" Output: " + networkOutput);
            System.out.println(" Ouput SHOULD be: " + 8854.4D / daxmax);
            System.out.println(" Ouput SHOULD be: " + 8854.4D );

        }

        //Experiments:
        //                   calculated
        //31;3;2009;4084,76 -> 4121 Error=0.01 Rate=0.7 Iterat=100
        //31;3;2009;4084,76 -> 4096 Error=0.01 Rate=0.7 Iterat=1000
        //31;3;2009;4084,76 -> 4093 Error=0.01 Rate=0.7 Iterat=10000
        //31;3;2009;4084,76 -> 4108 Error=0.01 Rate=0.7 Iterat=100000
        //31;3;2009;4084,76 -> 4084 Error=0.001 Rate=0.7 Iterat=10000

        System.out.println("Time stamp N3:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));


    }

    //going to test values from the first 4 minutes, and see if they equal the 5th minute
    public void test()
    {
    System.out.println("Test is running");
    //first four input values in five

        //so test i1, i2, i3, i4 and say they should equal i5

        int amount = stocksPrice.size();
        int currentSpot = 1;
        if( amount < 5 )
        {
            System.out.println("Not Enough Data to test( Need Min of 5 Points)");
            return;
        }

        //1,2,3,4 == 5
        //6,7,8,9 == 10
        //11,12,13,14 == 15
        int possibleTests = amount / 5;
        System.out.println("Since we have " + amount + " data points, we can do " + possibleTests + " tests");

        while( currentSpot <= possibleTests )
        {
            Double[] inputs = new Double[4];
            Double[] ouputs = new Double[1];

        }




    }

    public void saveCSV()
    {
        System.out.println("Save Stock Values to CSV....");
        try
        {
            FileWriter writer = new FileWriter("record.csv");


            for(int i = 0; i < stocksPrice.size(); i++)
            {

                writer.append( stocksPrice.get(i).stockName );
                writer.append(',');
                writer.append( String.valueOf(stocksPrice.get(i).stockPrice) );
                writer.append(',');
                writer.append( String.valueOf(stocksPrice.get(i).timeStamp ) );
                writer.append(',');
                writer.append( String.valueOf(stocksPrice.get(i).stockOpen ) );
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


}
