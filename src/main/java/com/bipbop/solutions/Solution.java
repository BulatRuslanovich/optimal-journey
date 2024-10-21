package com.bipbop.solutions;

import com.bipbop.Answer;
import com.bipbop.Attraction;
import java.util.List;

public interface Solution {
	Answer solve(List<Attraction> attractions, double availableTime);
}
