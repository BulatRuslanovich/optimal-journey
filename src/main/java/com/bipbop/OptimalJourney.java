package com.bipbop;

import com.bipbop.solutions.Solution;
import com.bipbop.solutions.impl.GreedyAlgorithm;
import com.bipbop.solutions.impl.ZeroOneKnapsack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class OptimalJourney {
	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Solution solution;
		double availableTime = 24 * 2 - 8 * 2;
		List<Attraction> attractions = loadAttractionsFromCSV("data.csv");
		System.out.print("Choose algorithm:\n1. Greedy algorithm\n2. Knapsack 0-1\n> ");
		int choose = sc.nextInt();

		if (choose == 1) {
			solution = new GreedyAlgorithm();
		} else if (choose == 2) {
			solution = new ZeroOneKnapsack();
		} else {
			throw new RuntimeException("Choose should be 1 or 2");
		}

		var answer = solution.solve(attractions, availableTime);

		System.out.println("Optimal route:");
		answer.result().forEach(System.out::println);

		System.out.printf("Total time spent: %.1f hours\n", answer.timeSpent());
		System.out.printf("Total importance: %d\n", answer.totalImportance());
		System.out.printf("Remaining time: %.1f hours\n", availableTime - answer.timeSpent());
		System.out.printf("Method worked time: %d ns\n", answer.ns());
	}

	public static List<Attraction> loadAttractionsFromCSV(String fileName) {
		List<Attraction> attractions = new ArrayList<>();
		String line;

		try (InputStream inputStream = OptimalJourney.class.getClassLoader()
				.getResourceAsStream(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {

			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				attractions.add(new Attraction(values[0], Double.parseDouble(values[1]), Integer.parseInt(values[2])));
			}
		} catch (IOException | NullPointerException e) {
			System.err.println("Read from csv error: " + e.getMessage());
			System.exit(-1);
		}

		return attractions;
	}
}