package com.bipbop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OptimalJourney {
    public static void main(String[] args) {
        List<Attraction> attractions = loadAttractionsFromCSV("data.csv");
        List<Attraction> result = new ArrayList<>();
        double availableTime = 24 * 2 - 8 * 2;
        int totalImportance = 0;
        double timeSpent = 0;

        attractions.sort(Comparator.comparingDouble(Attraction::getCoefficient).reversed());

        for (Attraction attraction : attractions) {
            if (timeSpent + attraction.time() <= availableTime) {
                result.add(attraction);
                timeSpent += attraction.time();
                totalImportance += attraction.importance();
            } else {
                break;
            }
        }

        System.out.println("Optimal route:");
        result.forEach(System.out::println);

        System.out.printf("Total time spent: %.1f hours\n", timeSpent);
        System.out.printf("Total importance: %d\n", totalImportance);
        System.out.printf("Remaining time: %.1f hours\n", availableTime - timeSpent);
    }

    public static List<Attraction> loadAttractionsFromCSV(String fileName) {
        List<Attraction> attractions = new ArrayList<>();
        String line;

        try (InputStream inputStream = OptimalJourney.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                attractions.add(new Attraction(values[0],
                        Double.parseDouble(values[1]), Integer.parseInt(values[2])));
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Read from csv error: " + e.getMessage());
            System.exit(-1);
        }

        return attractions;
    }
}