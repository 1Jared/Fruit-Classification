/*
    Name: Jubril Aderomilehin
    Class: EEGR 415
    Google Drive Link: https://drive.google.com/drive/folders/1qGJ3QjOdPjnIFkin5bEFn6U7hIIbDTT-?usp=sharing
    Description: Project 1
 */

import chapman.graphics.JPlot2D;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

import java.text.DecimalFormat;
import java.util.ConcurrentModificationException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Project_1 {
    final static int X1 = 0;           // X1 constant for X1 samples
    final static int X2 = 1;           // X2 constant for X2 samples

    final static int MAX_SAMPLES = 10;  // constant MAX_SAMPLES = 10

    public static void main(String[] args) {



        // Two double dimensional arrays
        double [][] tcData = new double [MAX_SAMPLES][2];   // True class data
        double [][] fcData = new double [MAX_SAMPLES][2];   // False class data

        // Open data.txt file
        File dataFile = new File("data.txt");

        try {
            // For reading data from file
            Scanner readData = new Scanner(dataFile);

            // For array index of all three arrays, used when reading from file
            int index = 0;

            while (readData.hasNextLine()) {
                // Read next data from data.txt
                String data = readData.nextLine();

                if(index < 10)                                                  // Input rows 1 - 10 as X1 true class data
                    tcData[index][X1] = Float.parseFloat(data);

                else if (index < 20)                                            // Input rows 11 - 20 as X2 true class data
                    tcData[index - 10][X2] = Float.parseFloat(data);

                else if (index < 30)                                            // Input row 21 - 30 as X1 false class data
                    fcData[index - 20][X1] = Float.parseFloat(data);

                else                                                            // Input row 31 - 40 as X2 false class data
                    fcData[index - 30][X2] = Float.parseFloat(data);

                // index = index + 1
                index++;
            }

            // Close the opened file
            readData.close();

        } catch (FileNotFoundException e) {             // For handling FileNotFoundException
            System.out.println("An error occurred.");
        }

        // Set decimal format for output
        DecimalFormat format = new DecimalFormat("0.#####");

        // Output contents of the two arrays
        System.out.println("X1 TRUE X2 TRUE X1 FALSE X2 FALSE");
        System.out.println("------- ------- ------- ------- ");

        // Loop from 0 up to MAX_SAMPLES - 1, and print using decimal format defined above
        for (int i = 0; i < MAX_SAMPLES; i++) {

            // Minus means Left aligned output
            System.out.printf("%-8s%-8s%-8s%s\n",
                    format.format(tcData[i][X1]),
                    format.format(tcData[i][X2]),
                    format.format(fcData[i][X1]),
                    format.format(fcData[i][X2]));
        }

        // Using x1[] and x2[] arrays for plotting true class data
        double [] x1 = new double[MAX_SAMPLES];
        double [] x2 = new double[MAX_SAMPLES];

        // Copy data corresponding to X1 and X2 from tcData[][] array to x1[] and x2[] arrays
        for(int i = 0; i < MAX_SAMPLES; i++) {
            x1[i] = tcData[i][X1];
            x2[i] = tcData[i][X2];
        }

        // Define myPlot for plotting graph using x1[] and x2[] arrays
        JPlot2D myPlot = new JPlot2D(x1, x2);
       // myPlot.setBounds(5,50,150,100);


        myPlot.setTitle("Project 1: Fruit Classification");         // Set plot title
        myPlot.setPlotType(JPlot2D.LINEAR);                         // Set plot type : LINEAR
        myPlot.setLineState(JPlot2D.LINE_OFF);                      // Set line state : LINE_OFF
        myPlot.setMarkerState(JPlot2D.MARKER_ON);                   // Set marker state : MARKER_ON
        myPlot.setMarkerStyle(JPlot2D.MARKER_CIRCLE);               // Set marker style : MARKER_CIRCLE
        myPlot.setMarkerColor(Color.red);                           // Set marker colour : red
        myPlot.setXLabel("X1 Feature");                             // Set X-axis label
        myPlot.setYLabel("X2 Feature");                             // Set Y-axis label


        // Using arrays for plotting false class data
        double [] x1False = new double[MAX_SAMPLES];
        double [] x2False = new double[MAX_SAMPLES];

        // Copy data corresponding to X1 and X2 from fcData[][] array to x1False[] and x2False[] arrays
        for(int i = 0; i < MAX_SAMPLES; i++) {
            x1False[i] = fcData[i][X1];
            x2False[i] = fcData[i][X2];
        }

        // Add the new set of data from false class to the plot
        myPlot.addCurve(x1False, x2False);

        // Set the plot data for new set of data
        myPlot.setLineState(JPlot2D.LINE_OFF);                      // Set line state : LINE_OFF
        myPlot.setMarkerState(JPlot2D.MARKER_ON);                   // Set marker state : MARKER_ON
        myPlot.setMarkerStyle(JPlot2D.MARKER_TRIANGLE);             // Set marker style : MARKER_TRIANGLE
        myPlot.setMarkerColor(Color.blue);                          // Set marker colour : blue

        // Declare a new JFrame with title "Project 2"
        JFrame frame = new JFrame("Project 2");
        frame.setLayout(null);


        double w0 = 39.0000;            // In w[1], from matlab
        double w1 = -1 * 4.7877;        // In w[2], from matlab
        double w2 = -1 * 1.3252;        // In w[3], from matlab

        // Array to calculate two points for plotting the line
        double [] x = new double[2];
        double [] y = new double[2];

        // Used formula, y = (w0 + x(w1))/-w2 to get plot points
        // Find y0, for x0 = 5
        x[0] = 5;
        y[0] = (w0 + x[0] * (w1))/(-1 * w2);

        // Find y1, for x1 = 9
        x[1] = 9;
        y[1] = (w0 + x[1] * (w1))/(-1 * w2);

        // Add the two plot points for plotting the line
        myPlot.addCurve(x, y);

        myPlot.setLineStyle(JPlot2D.LINESTYLE_SOLID);               // Set line style : LINESTYLE_BOLD
        myPlot.setLineState(JPlot2D.LINE_ON);                       // Set line state : LINE_ON
        myPlot.setLineColor(Color.green);                           // Set line color : green


       //panel for the trainedWeight with border called Trained Weights
        JPanel forTrainedWeights= new JPanel(new BorderLayout())
        {
            @Override
            public  Dimension getPreferredSize(){
                return new Dimension(400,300);
            }
        };

        //adding the panel to the frame
        frame.add(forTrainedWeights);

        forTrainedWeights.setBounds(867,305,470,155);
        //setting border name Trained Weights
        forTrainedWeights.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Trained Weights ", TitledBorder.LEFT, TitledBorder.TOP));

   //column name of the table
        String [] column ={
                "Bias",
                "Feature 1",
                "Feature 2"

        };
        //Array of type object to populate cells
        Double [][] data ={
                {0.0,0.0,0.0}
        };
        // Creating table called trainedWeights
        JTable trainedWeights= new JTable(data,column);
        trainedWeights.setShowGrid(false);

        JTableHeader header = trainedWeights.getTableHeader();
        //Modifies all JTables Header in the application
        UIManager.getDefaults().put("TableHeader.cellBorder",BorderFactory.createEmptyBorder(0,0,0,0));

        //Aligning the table Header to the left
        DefaultTableCellRenderer renderer=(DefaultTableCellRenderer)
        trainedWeights.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        forTrainedWeights.add(header, BorderLayout.NORTH);
        forTrainedWeights.add(trainedWeights,BorderLayout.CENTER);



     //Panel with a border Called Testing Options
        JPanel forTestingOptions= new JPanel(new BorderLayout())
        {
            @Override
            public  Dimension getPreferredSize(){
                return new Dimension(400,300);
            }
        };
        forTestingOptions.setLayout(null);

        //Border name
        forTestingOptions.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Testing Options", TitledBorder.LEFT, TitledBorder.TOP));


        //JRadioButton for selecting the single point
        JRadioButton singlePoint= new JRadioButton("Single Point",true);
        singlePoint.setBounds(105,20,120,20);
        forTestingOptions.add(singlePoint);

        //JRadioButton for selecting the multiple point
        JRadioButton multiPoint= new JRadioButton("Multi Point");
        multiPoint.setBounds(225,20,100,20);
        forTestingOptions.add(multiPoint);
        //Feature 1 JLabel
        JLabel label= new JLabel("Feature 1:");
        label.setBounds(10,50,70,20);
        forTestingOptions.add(label);
        //Feature 1 JTextField
        JTextField textField= new JTextField();
        textField.setBounds(85,50,140,20);
        forTestingOptions.add(textField);
        //Feature 2 JLabel
        JLabel label2= new JLabel("Feature 2:");
        label2.setBounds(235,50,70,20);
        forTestingOptions.add(label2);
        //Feature 2 JTextField
        JTextField textField2= new JTextField();
        textField2.setBounds(310,50,140,20);
        forTestingOptions.add(textField2);

        //Adding forTestingOptions Pane to the frame
        frame.add(forTestingOptions);
         //Setting its bound
        forTestingOptions.setBounds(867,457,470,100);


        //Panel for holding the train and test buttons
        JPanel  forButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
       //train button
        JButton  train = new JButton("Train");
        train.setBorder(new RoundedBorder(10));
        train.setPreferredSize(new Dimension(80,30));
        train.setBorderPainted(false);
        train.setBackground(Color.WHITE);
        forButtons.add(train);
        //test button
        JButton  test = new JButton("Test");
        test.setBorder(new RoundedBorder(10));
        test.setPreferredSize(new Dimension(80,30));
        test.setBorderPainted(false);
        test.setBackground(Color.WHITE);
        forButtons.add(test);
        //Adding the panel to the frame
        frame.add(forButtons);
        forButtons.setBounds(867,559,470,60);
        forButtons.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Button ", TitledBorder.LEFT, TitledBorder.TOP));

 //This  Panel is  used to hold combobox and table, the layout is Box layout

        JPanel forViewDataSamples= new JPanel( )
        {
            @Override
            public  Dimension getPreferredSize(){
                return new Dimension(400,300);
            }
        };
     //Vertical alignment
        forViewDataSamples.setLayout(new BoxLayout(forViewDataSamples, BoxLayout.Y_AXIS));

        String [] combo={
               "✓Show Test Data",
                "Show Training Data"
        };
       //Combobox with show test data and show training data
        JComboBox comboBox=new JComboBox(combo);
        comboBox.setBounds(20,20,450,30);

       //create vertical space between combobox and  table
        comboBox.setUI(ColorArrowUI.createUI(comboBox));
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, comboBox.getMinimumSize().height));

        comboBox.setBackground(Color.WHITE);


        forViewDataSamples.add(comboBox);

        forViewDataSamples.add(Box.createRigidArea(new Dimension(0, 10)));
        // Table column names
        String [] columnNames ={

                "Feature 1",
                "Feature 2"

        };
        //array to populate the table
        Double [][] dataFill ={
                {0.0,0.0}
        };

        JTable viewTable= new JTable(dataFill,columnNames);
        viewTable.setShowGrid(false);
        JTableHeader header1 = viewTable.getTableHeader();

        //Aligning the table Header to the left
        DefaultTableCellRenderer renderer1=(DefaultTableCellRenderer)
                viewTable.getTableHeader().getDefaultRenderer();
        renderer1.setHorizontalAlignment(JLabel.LEFT);


        forViewDataSamples.add(header1);

        forViewDataSamples.add(viewTable);



        frame.add(forViewDataSamples);


        forViewDataSamples.setBounds(867,0,470,305);
        //creating border name
        forViewDataSamples.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "View Data Samples ", TitledBorder.LEFT, TitledBorder.TOP));

       //Panel for holding the graph and the checkbox
        JPanel forGraph = new JPanel(new BorderLayout()){
            @Override
            public  Dimension getPreferredSize(){
                return new Dimension(868,300);
            }
        };

       //Adding the forGraph panel to the frame
        frame.add(forGraph);

        forGraph.setBounds(0,0,868,620);
        //Title for the border
        forGraph.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Data Samples Graph", TitledBorder.LEFT, TitledBorder.TOP));


        // Add myPlot to panel

        forGraph.add(myPlot,BorderLayout.CENTER);

        forGraph.add(myPlot);
       // checkbox to show training
        JCheckBox showTraining = new JCheckBox("Show Training");
        forGraph.add(showTraining,BorderLayout.PAGE_END);





        //Menu Bar
        JMenuBar menuBar= new JMenuBar();

        // Menu called File
        JMenu file =new JMenu("File");

        /**adding menu items    */
        JMenuItem loadTrainingData= new JMenuItem("Load Training Data");
        file.add(loadTrainingData);

        JMenuItem loadTestData= new JMenuItem("Load Test Data");
        file.add(loadTestData);

        JMenuItem loadWeights= new JMenuItem("Load Weights");
        file.add(loadWeights);

        JMenuItem saveWeights= new JMenuItem("Save weights");
        file.add(saveWeights);

        JMenuItem exit= new JMenuItem("Exit");
        file.add(exit);
        menuBar.add(file);


        JMenu view =new JMenu("View");
        JMenuItem showDecisionBoundary= new JMenuItem("✓ Show Decision Boundary");
        view.add(showDecisionBoundary);

        menuBar.add(view);
        menuBar.setVisible(true);
        frame.setJMenuBar(menuBar);


       forGraph.add(myPlot,BorderLayout.CENTER);
       forGraph.validate();



        frame.setSize(500, 500);                        // Set frame size : 500 * 500
        frame.setVisible(true);                                     // Set frame visibility : true

        // Declare frame for taking input
        JFrame inputFrame = new JFrame();

        // Declare variable for input
        String input;

        // Take input : X1 feature
        input = JOptionPane.showInputDialog(inputFrame, "Enter X1 feature of new sample");

        // X1 feature[] array to take X1 feature, and plotting on graph
        double [] X1Feature = new double[1];
        X1Feature[0] = Double.parseDouble(input);

        // Take input : X2 feature
        input = JOptionPane.showInputDialog(inputFrame, "Enter X2 feature of new sample");

        // X2 feature[] array to take X2 feature, and plotting on graph
        double [] X2Feature = new double[1];
        X2Feature[0] = Double.parseDouble(input);

        // Performing dot product
        double dotProduct = w0 * 1 + w1 * X1Feature[0] + w2 * X2Feature[0];

        // Variable for output
        String output;

        if(dotProduct >= 0)                                                     // If dot product >= 0, then belongs to TRUE class (good fruit)
            output = "The new sample belongs to TRUE class (good fruit)";
        else                                                                    // If dot product < 0, then belongs to FALSE class (too ripe)
            output = "The new sample belongs to FALSE class (too ripe)";

        // Declare frame for output message dialog box
        JFrame outputFrame = new JFrame();
        JOptionPane.showMessageDialog(outputFrame, output);

        // Set previous plot frame visibility to "false"
        frame.setVisible(false);

        // Add the new set of data for the point to plot
        myPlot.addCurve(X1Feature, X2Feature);


        myPlot.setLineState(JPlot2D.LINE_OFF);                      // Set line state : LINE_OFF
        myPlot.setMarkerState(JPlot2D.MARKER_ON);                   // Set marker state : MARKER_ON
        myPlot.setMarkerStyle(JPlot2D.MARKER_SQUARE);               // Set marker style : MARKER_SQUARE
        myPlot.setMarkerColor(Color.orange);                        // Set line color : orange


        forGraph.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Data Samples Graph", TitledBorder.LEFT, TitledBorder.TOP));













        frame.setSize(500, 500);                        // Set frame size : 500 * 500
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(true);                                     // Set frame visibility : true

    }



}

