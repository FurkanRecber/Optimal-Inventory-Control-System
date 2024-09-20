import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        // Create the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel unitCostLabel = new JLabel("Please enter the unit cost: ");
        JTextField unitCostField = new JTextField();

        JLabel orderingCostLabel = new JLabel("Please enter the ordering cost: ");
        JTextField orderingCostField = new JTextField();

        JLabel penaltyCostLabel = new JLabel("Please enter the penalty cost: ");
        JTextField penaltyCostField = new JTextField();

        JLabel interestRateLabel = new JLabel("Please enter the interest rate (%): ");
        JTextField interestRateField = new JTextField();

        JLabel leadTimeLabel = new JLabel("Please enter the lead time (months): ");
        JTextField leadTimeField = new JTextField();

        JLabel leadTimeDemandLabel = new JLabel("Please enter the lead time demand: ");
        JTextField leadTimeDemandField = new JTextField();

        JLabel standardDeviationLabel = new JLabel("Please enter the standard deviation: ");
        JTextField standardDeviationField = new JTextField();

        inputPanel.add(unitCostLabel);
        inputPanel.add(unitCostField);
        inputPanel.add(orderingCostLabel);
        inputPanel.add(orderingCostField);
        inputPanel.add(penaltyCostLabel);
        inputPanel.add(penaltyCostField);
        inputPanel.add(interestRateLabel);
        inputPanel.add(interestRateField);
        inputPanel.add(leadTimeLabel);
        inputPanel.add(leadTimeField);
        inputPanel.add(leadTimeDemandLabel);
        inputPanel.add(leadTimeDemandField);
        inputPanel.add(standardDeviationLabel);
        inputPanel.add(standardDeviationField);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputArea.setFont(new Font("Serif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double UNIT_COST = Double.parseDouble(unitCostField.getText());
                    double ORDERING_COST = Double.parseDouble(orderingCostField.getText());
                    double PENALTY_COST = Double.parseDouble(penaltyCostField.getText());
                    double INTEREST_RATE = Double.parseDouble(interestRateField.getText()) / 100;
                    double LEAD_TIME = Double.parseDouble(leadTimeField.getText());
                    double LEAD_TIME_DEMAND = Double.parseDouble(leadTimeDemandField.getText());
                    double STANDARD_DEVIATION = Double.parseDouble(standardDeviationField.getText());
                    final double HOLDING_COST = UNIT_COST * INTEREST_RATE;

                    Zchartreader zChartReader = new Zchartreader("src/z-chart.txt");

                    double annualDemand = (LEAD_TIME_DEMAND * 12) / LEAD_TIME;
                    double optimalLotSize = Math.ceil(Math.sqrt((2 * ORDERING_COST * annualDemand) / HOLDING_COST));
                    double z = zChartReader.getZ((1 - (optimalLotSize * HOLDING_COST) / (PENALTY_COST * annualDemand)));
                    double reorderPoint = LEAD_TIME_DEMAND + STANDARD_DEVIATION * z;
                    int iterations = 0;

                    double optimalLotSizeNew;
                    double reorderPointNew;

                    do {
                        iterations++;
                        double l = zChartReader.getL(z);
                        optimalLotSizeNew = Math.round(Math.sqrt((2 * annualDemand * (ORDERING_COST + PENALTY_COST * STANDARD_DEVIATION * l)) / HOLDING_COST));
                        z = zChartReader.getZ((1 - (optimalLotSizeNew * HOLDING_COST) / (PENALTY_COST * annualDemand)));
                        reorderPointNew = LEAD_TIME_DEMAND + STANDARD_DEVIATION * z;

                        if (Math.abs(optimalLotSizeNew - optimalLotSize) < 0.1 && Math.abs(reorderPointNew - reorderPoint) < 0.1) {
                            break;
                        }

                        optimalLotSize = optimalLotSizeNew;
                        reorderPoint = reorderPointNew;

                    } while (true);

                    double safetyStock = reorderPoint - LEAD_TIME_DEMAND;
                    double averageHoldingCost = HOLDING_COST * (optimalLotSize / 2 + safetyStock);
                    double averageOrderingCost = ORDERING_COST * (annualDemand / optimalLotSize);
                    double averagePenaltyCost = PENALTY_COST * (annualDemand * STANDARD_DEVIATION * zChartReader.getL(z) / optimalLotSize);
                    double averageTimeBetweenOrders = optimalLotSize / annualDemand;
                    double noStockoutProportions = 1 - (optimalLotSize * HOLDING_COST) / (PENALTY_COST * annualDemand);
                    double notMetProportions = STANDARD_DEVIATION * zChartReader.getL(z) / optimalLotSize;

                    // Display the results in the output area
                    outputArea.setText(String.format(Locale.ENGLISH, "Optimal Lot Size: %.2f\n", optimalLotSize));
                    outputArea.append(String.format(Locale.ENGLISH, "Reorder Point: %.2f\n", reorderPoint));
                    outputArea.append("Number of Iterations: " + iterations + "\n");
                    outputArea.append(String.format(Locale.ENGLISH, "Safety Stock: %.2f\n", safetyStock));
                    outputArea.append(String.format(Locale.ENGLISH, "Average Holding Cost: %.2f\n", averageHoldingCost));
                    outputArea.append(String.format(Locale.ENGLISH, "Average Ordering Cost: %.2f\n", averageOrderingCost));
                    outputArea.append(String.format(Locale.ENGLISH, "Average Penalty Cost: %.2f\n", averagePenaltyCost));
                    outputArea.append(String.format(Locale.ENGLISH, "Average Time Between Orders: %.3f years\n", averageTimeBetweenOrders));
                    outputArea.append(String.format(Locale.ENGLISH, "No Stockout Proportions: %.2f%%\n", noStockoutProportions * 100));
                    outputArea.append(String.format(Locale.ENGLISH, "Not Met Proportions: %.2f%%\n", notMetProportions * 100));

                } catch (IOException ex) {
                    outputArea.setText("Error reading Z-Chart data: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid input. Please enter valid numbers.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
