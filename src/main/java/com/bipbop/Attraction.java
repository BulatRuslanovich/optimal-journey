package com.bipbop;

public record Attraction(
        String name,
        double time,
        int importance
) {

    public double getCoefficient() {
        return importance / time;
    }

    @Override
    public String toString() {
        return String.format("%-38s (Time: %5.1f ч, Importance: %2d)", name, time, importance);
    }
}
