package com.bipbop.solutions.impl;

import com.bipbop.Attraction;
import com.bipbop.Answer;
import com.bipbop.solutions.Solution;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Жадный алгоритм решения задачи о рюкзаке.
 * <p>
 * Для эффективного использования рюкзака, необходимо отсортировать предметы
 * по их удельной ценности, то есть по отношению ценности предмета к его весу.
 * Затем следует помещать в рюкзак предметы с наибольшей удельной ценностью.
 * <p>
 * Время работы данного алгоритма складывается из времени сортировки и времени укладки предметов.
 * Сложность сортировки предметов составляет O(N log(N)).
 * После сортировки происходит вычисление того, сколько предметов поместится в рюкзак,
 * что занимает O(N).
 * <p>
 * Таким образом, итоговая сложность алгоритма составляет O(N log(N)) при необходимости сортировки
 * и O(N) при уже отсортированных данных.
 */
public class GreedyAlgorithm implements Solution {
	@Override
	public Answer solve(List<Attraction> attractions, double availableTime) {
		long begin = System.nanoTime();
		List<Attraction> result = new ArrayList<>();
		int totalImportance = 0;
		double totalTimeSpent = 0;

		attractions.sort(Comparator.comparingDouble(Attraction::getCoefficient).reversed());

		for (Attraction attraction : attractions) {
			if (totalTimeSpent + attraction.time() <= availableTime) {
				result.add(attraction);
				totalTimeSpent += attraction.time();
				totalImportance += attraction.importance();
			} else {
				break;
			}
		}

		long end = System.nanoTime();
		return new Answer(result, totalImportance, totalTimeSpent, end - begin);
	}
}
