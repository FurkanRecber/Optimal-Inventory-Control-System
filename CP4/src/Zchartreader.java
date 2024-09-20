import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Zchartreader {
    private Map<Double, Double> zTable = new HashMap<>();
    private Map<Double, Double> lTable = new HashMap<>();

    public Zchartreader(String filePath) throws IOException {
        loadZChartData(filePath);
    }

    private void loadZChartData(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Z")) continue; // Skip header
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    double z = Double.parseDouble(parts[0]);
                    double fZ = Double.parseDouble(parts[1]);
                    double lZ = Double.parseDouble(parts[2]);
                    zTable.put(fZ, z);
                    lTable.put(z, lZ);
                }
            }
        }
    }

    public double getZ(double fZ) {
        double closestFZ = 0.0;
        double minDifference = Double.MAX_VALUE;

        // En yakın fZ değerini bul
        for (double key : zTable.keySet()) {
            double difference = Math.abs(key - fZ);
            if (difference < minDifference) {
                minDifference = difference;
                closestFZ = key;
            }
        }

        // En yakın fZ değerine karşılık gelen z değerini dön
        return zTable.getOrDefault(closestFZ, 0.0);
    }

    public double getL(double z) {
        return lTable.getOrDefault(z, 0.0);
    }
}