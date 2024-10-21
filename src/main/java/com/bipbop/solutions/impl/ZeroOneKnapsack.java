package com.bipbop.solutions.impl;

import com.bipbop.Attraction;
import com.bipbop.Answer;
import com.bipbop.solutions.Solution;
import java.util.ArrayList;
import java.util.List;

/**
 * Решение задачи о рюкзаке 0-1.
 * <p>
 * Данная задача заключается в том, чтобы определить максимальную ценность предметов,
 * которые можно взять с собой в рюкзак, при условии, что каждый предмет доступен
 * только в единственном экземпляре.
 * <p>
 * Обозначим m[i, w] как максимальную ценность предметов, полученных из первых i предметов,
 * с суммарным весом не превышающим w.
 * <p>
 * Рекуррентные соотношения:
 * <p>
 * 1. m[0, w] = 0; // Если нет предметов, ценность равна 0. <br>
 * 2. m[i, w] = m[i-1, w], если вес i-го предмета (wi) больше w;
 *    // Предмет не помещается в рюкзак, берем ценность без него. <br>
 * 3. m[i, w] = max(m[i-1, w], m[i-1, w-wi] + vi), если wi ≤ w;
 *    // Выбираем максимум между невыбором предмета и выбором с учетом его ценности.
 * <p>
 * Вычисляя m[n, W], мы можем найти точное решение задачи.
 * Если массив m[i, w] помещается в память, данный алгоритм является одним из наиболее
 * эффективных для решения задачи о рюкзаке 0-1.
 */
public class ZeroOneKnapsack implements Solution {
	@Override
	public Answer solve(List<Attraction> attractions, double availableTime) {
		long begin = System.nanoTime();
		List<Attraction> result = new ArrayList<>();
		int totalImportance = 0;
		double totalTimeSpent = 0;
		int n = attractions.size();
		int W = (int) (availableTime * 2);
		int[][] m = new int[n + 1][W + 1];

		for (int i = 1; i < n + 1; i++) {
			Attraction attraction = attractions.get(i - 1);
			int wi = (int) (attraction.time() * 2);
			int vi = attraction.importance();

			for (int w = 0; w <= W; w++) {
				if (wi > w) {
					m[i][w] = m[i - 1][w];
				} else {
					m[i][w] = Math.max(m[i - 1][w], m[i - 1][w - wi] + vi);
				}
			}
		}

		for (int i = n, w = W; i > 0; i--) {
			if (m[i][w] != m[i - 1][w]) {
				Attraction attraction = attractions.get(i - 1);
				result.add(attraction);
				totalImportance += attraction.importance();
				totalTimeSpent += attraction.time();
				w -= (int) (attraction.time() * 2);
			}
		}

		long end = System.nanoTime();
		return new Answer(result, totalImportance, totalTimeSpent, end - begin);
	}

	@Override
	public String toString() {
		return "Knapsack 0-1";
	}
}
