package com.csc4702;

public class Chromosome {
    // 1. Genes (Joint Angles)
    private double q1;
    private double q2;
    
    // 2. Fitness
    private double fitness;
    
    // 3. Physical Constants (Optional, can be passed from RobotArm)
    private static final double MIN_ANGLE_Q1 = 0.0;
    private static final double MAX_ANGLE_Q1 = 2 * Math.PI; // 0 to 360 degrees
    private static final double MIN_ANGLE_Q2 = -Math.PI;
    private static final double MAX_ANGLE_Q2 = Math.PI;    // -180 to +180 degrees
    
    // Constructor 1: Random Initialization
    public Chromosome() {
        // Initialize q1 and q2 randomly within their allowed range
        this.q1 = MIN_ANGLE_Q1 + Math.random() * (MAX_ANGLE_Q1 - MIN_ANGLE_Q1);
        this.q2 = MIN_ANGLE_Q2 + Math.random() * (MAX_ANGLE_Q2 - MIN_ANGLE_Q2);
    }
    
    // Constructor 2: Used for crossover/mutation
    public Chromosome(double q1, double q2) {
        this.q1 = q1;
        this.q2 = q2;
    }
    
    // --- Getters and Setters (Important for P4's mutation and P2's FK) ---
    public double getQ1() { return q1; }
    public double getQ2() { return q2; }
    public double getFitness() { return fitness; }
    
    public void setQ1(double q1) { this.q1 = q1; }
    public void setQ2(double q2) { this.q2 = q2; }
    // P4 will use this method to store the result of the Fitness Function
    public void setFitness(double fitness) { this.fitness = fitness; }
    
    // Helper method for mutation to ensure angles stay in bounds
    public void clampAngles() {
        // Ensure q1 is in [0, 2pi]
        if (q1 < MIN_ANGLE_Q1) q1 += MAX_ANGLE_Q1;
        if (q1 > MAX_ANGLE_Q1) q1 -= MAX_ANGLE_Q1;
        
        // Ensure q2 is in [-pi, pi]
        if (q2 < MIN_ANGLE_Q2) q2 += 2 * Math.PI;
        if (q2 > MAX_ANGLE_Q2) q2 -= 2 * Math.PI;
    }
}
