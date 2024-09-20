# Optimal Inventory Control System (Java)
Project Overview
This is a Java software application designed to determine the optimal lot size (Q) and reorder point (R) for inventory control. It factors in unit cost, ordering cost, penalty cost, interest rates, and demand data. The program calculates key performance metrics such as safety stock, average holding cost, setup cost, penalty costs, and others using the Z-Chart & Loss Function Table for normal distribution values.

Features
Calculate optimal lot size and reorder point
Compute safety stock
Calculate average annual holding, setup, and penalty costs
Determine the proportion of order cycles with no stockouts and the proportion of unmet demand
Automatically uses Z-Chart & Loss Function during iteration
Prerequisites
Before running the project, ensure you have the following installed:

Java Development Kit (JDK) 8+
Maven (or your preferred build tool)
How to Run
Clone the repository:

bash
Kodu kopyala
git clone https://github.com/yourusername/Optimal-Inventory-Control-System.git
cd Optimal-Inventory-Control-System
Build the project using Maven:

bash
Kodu kopyala
mvn clean install
Run the program:

bash
Kodu kopyala
java -jar target/OptimalInventoryControlSystem.jar
Enter the required data when prompted:

Unit cost
Ordering cost
Penalty cost
Interest rate
Lead time
Lead time demand and standard deviation
Results will include:

Optimal lot size (Q) and reorder point (R)
Number of iterations run
Safety stock
Performance measures such as average costs, time between orders, and stockout probabilities
Example
You can test the software with the following example problem (Harvey’s Specialty Shop):

Parameter	Value
Unit cost	$20
Ordering cost	$100
Penalty cost	$20 per jar
Interest rate	25%
Lead time	4 months
Lead time demand (mean)	500 jars
Lead time demand (std dev)	100 jars
The program will calculate the optimal reorder strategy based on these inputs.

Output
Once the program runs, it will display:

Optimal lot size (Q)
Reorder point (R)
Safety stock
Annual cost metrics and additional performance indicators on a single screen.
Contributing
If you'd like to contribute, fork the repository and submit a pull request.

License
This project is licensed under the MIT License - see the LICENSE file for details.
