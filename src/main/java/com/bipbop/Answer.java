package com.bipbop;

import java.util.List;

public record Answer(
		List<Attraction> result,
		int totalImportance,
		double timeSpent,
		long ns) {}