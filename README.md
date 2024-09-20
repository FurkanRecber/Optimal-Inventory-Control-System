# Optimal Inventory Control System (Java)
Project Overview
This is a **Java** software application designed to determine the **optimal lot size (Q)** and **reorder point (R)** for inventory control. It factors in unit cost, ordering cost, penalty cost, interest rates, and demand data. The program calculates key performance metrics such as safety stock, average holding cost, setup cost, penalty costs, and others using the Z-Chart & Loss Function Table for normal distribution values.

## Features
* Calculate **optimal lot size** and **reorder point**
* Compute **safety stock**
* Calculate **average annual holding**, **setup**, and **penalty costs**
* Determine the **proportion of order cycles** with no stockouts and the **proportion of unmet demand**
* Automatically uses Z-Chart & Loss Function during iteration

## Prerequisites
Before running the project, ensure you have the following installed:
* **Java Development Kit (JDK) 8+**
* **Maven** (or your preferred build tool)

<hr/>

## How to Run
#### 1. Clone the repository:
```
git clone https://github.com/FurkanRecber/Optimal-Inventory-Control-System.git
cd Optimal-Inventory-Control-System
```

#### 2. Build the project using Maven:
```
mvn clean install
```

#### 3. Run the program:
```
java -jar target/OptimalInventoryControlSystem.jar
```

#### 4. Enter the required data when prompted:
* Unit cost
* Ordering cost
* Penalty cost
* Interest rate
* Lead time
* Lead time demand and standard deviation

#### 5. Results will include:
* Optimal lot size (Q) and reorder point (R)
* Number of iterations run
* Safety stock
* Performance measures such as average costs, time between orders, and stockout probabilities

<hr/>

#### Contributing
If you'd like to contribute, fork the repository and submit a pull request.
