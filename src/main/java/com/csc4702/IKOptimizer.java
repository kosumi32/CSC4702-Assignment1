package com.csc4702;

public class IKOptimizer {
    
    // Target position for the IK problem
    private final double targetX;
    private final double targetY;
    
    // Link lengths (passed to P2's RobotArm)
    private final double a1 = 10.0; 
    private final double a2 = 8.0; 
    
    // GA Parameters (P5 will tune these)
    private static final int MAX_GENERATIONS = 1000;
    private static final double ERROR_TOLERANCE = 0.01;
    
    // P2's dependency
    private final RobotArm2D robotArm;
    
    public IKOptimizer(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        // Instantiate P2's class
        this.robotArm = new RobotArm2D(a1, a2); 
    }
    
    public void runGA() {
        System.out.println("\n--- Running GA for Target: (" + targetX + ", " + targetY + ")");
        
        // Instantiate P3's class
        GeneticAlgorithm ga = new GeneticAlgorithm(robotArm, targetX, targetY);
        
        // P3 Logic: Initialize Population
        ga.initializePopulation();
        
        // P3 Logic: Initial Fitness Check
        ga.calculateFitness();
        
        // Start the evolution loop
        for (int generation = 1; generation <= MAX_GENERATIONS; generation++) {
            
            Chromosome best = ga.getBestChromosome();
            
            // 1. P3 Logic: Check Termination Condition (The STOP condition)
            // Error is calculated from Fitness F = 1/(1+E) -> E = (1/F) - 1
            double bestError = (1.0 / best.getFitness()) - 1.0;
            
            // Output status (Crucial for P5's report)
            double[] endPos = robotArm.getEndEffectorPosition(best.getQ1(), best.getQ2());
            System.out.printf("Gen %d: Best Error=%.6f, Angles=(%.2f, %.2f), Pos=(%.2f, %.2f)\n", 
                                generation, 
                                bestError, 
                                Math.toDegrees(best.getQ1()), 
                                Math.toDegrees(best.getQ2()),
                                endPos[0], endPos[1]);

            if (bestError < ERROR_TOLERANCE) {
                System.out.println("✅ Convergence achieved! Error < " + ERROR_TOLERANCE);
                break;
            }
            
            // 2. P3 Logic: Evolve to the Next Generation
            ga.evolvePopulation(); // Calls P4's Selection, Crossover, and Mutation
            
            if (generation == MAX_GENERATIONS) {
                 System.out.println("❌ Stopped after max generations. Best error: " + bestError);
            }
        }
    }
    
    // Main method to test the required scenarios (P5's testing)
    public static void main(String[] args) {
        // Test Case 1
        IKOptimizer test1 = new IKOptimizer(15.0, 5.0); 
        test1.runGA();
        
        // Test Case 2
        IKOptimizer test2 = new IKOptimizer(-2.0, 16.0); 
        test2.runGA();

        // Test Case 3
        IKOptimizer test3 = new IKOptimizer(1.0, 1.0); 
        test3.runGA();
    }
}
