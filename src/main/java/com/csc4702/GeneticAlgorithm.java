package com.csc4702;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeneticAlgorithm {
    // 1. GA Parameters (Tuning is P5's job)
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.05;
    private static final double CROSSOVER_RATE = 0.90;
    private static final int ELITISM_COUNT = 5;
    
    // 2. Population and Dependencies
    private List<Chromosome> population;
    private final RobotArm2D robotArm; // P2's class
    private final double targetX;
    private final double targetY;
    
    public GeneticAlgorithm(RobotArm2D robotArm, double targetX, double targetY) {
        this.robotArm = robotArm;
        this.targetX = targetX;
        this.targetY = targetY;
        this.population = new ArrayList<>();
    }
    
    // P3 Task: Initialize the population
    public void initializePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Chromosome());
        }
    }
    
    // P3 Task: The Main Loop Engine (P4 implements the details)
    public List<Chromosome> evolvePopulation() {
        List<Chromosome> newPopulation = new ArrayList<>();
        
        // 1. Elitism: Preserve the best individuals (P5 will need this for tuning)
        // Sort population by fitness (descending)
        population.sort(Comparator.comparing(Chromosome::getFitness).reversed());
        
        // Add the top N chromosomes (Elites)
        for (int i = 0; i < ELITISM_COUNT; i++) {
            newPopulation.add(population.get(i));
        }

        // 2. Main Loop: Generate the rest of the new population
        while (newPopulation.size() < POPULATION_SIZE) {
            
            // a. Selection (P4's logic)
            Chromosome parent1 = selectParent(); // P4 will write the selection logic
            Chromosome parent2 = selectParent(); // P4 will write the selection logic
            
            // b. Crossover (P4's logic)
            Chromosome child = crossover(parent1, parent2); // P4 will write the crossover logic
            
            // c. Mutation (P4's logic)
            mutate(child); // P4 will write the mutation logic
            
            newPopulation.add(child);
        }
        
        this.population = newPopulation;
        
        // 3. Fitness Calculation (P4 and P2 coordination)
        calculateFitness(); // P4 will primarily write the fitness math
        
        return this.population;
    }
    
    // P4 Task: Selection (Placeholder method)
    private Chromosome selectParent() {
        // Implement Tournament or Roulette Wheel Selection here (P4 will fill this)
        // Placeholder returns a random one for structural testing
        return population.get((int) (Math.random() * POPULATION_SIZE)); 
    }
    
    // P4 Task: Crossover (Placeholder method)
    private Chromosome crossover(Chromosome p1, Chromosome p2) {
        // Implement Single-point or Uniform Crossover here (P4 will fill this)
        // Placeholder returns a copy of p1
        return new Chromosome(p1.getQ1(), p1.getQ2()); 
    }
    
    // P4 Task: Mutation (Placeholder method)
    private void mutate(Chromosome c) {
        // Implement angle perturbation (P4 will fill this)
    }

    // P4 and P2 Coordination: Fitness Calculation
    public void calculateFitness() {
        for (Chromosome c : population) {
            // P2's FK is called here
            double[] endEffectorPos = robotArm.getEndEffectorPosition(c.getQ1(), c.getQ2());
            double xE = endEffectorPos[0];
            double yE = endEffectorPos[1];
            
            // P4's Euclidean Distance logic is applied here
            double error = Math.sqrt(Math.pow(xE - targetX, 2) + Math.pow(yE - targetY, 2));
            
            // P4's Fitness transformation: Maximize 1 / (1 + Error)
            double fitness = 1.0 / (1.0 + error); 
            c.setFitness(fitness);
        }
    }
    
    // Public method to get the current best chromosome
    public Chromosome getBestChromosome() {
        // Ensure population is sorted (or sort it)
        population.sort(Comparator.comparing(Chromosome::getFitness).reversed());
        return population.get(0);
    }
}
