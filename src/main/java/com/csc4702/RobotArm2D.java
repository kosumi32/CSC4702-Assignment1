package com.csc4702;

//P2 task

public class RobotArm2D {
    // Link Lengths (a1 and a2)
    private final double a1;
    private final double a2;

    /**
     * Fixes Error 1: Constructor mismatch in IKOptimizer.java
     * Initializes the Robot Arm with fixed link lengths.
     */
    public RobotArm2D(double a1, double a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    /**
     * Fixes Error 2: Method not found in GeneticAlgorithm.java
     * Implements the Forward Kinematics (FK) to calculate the end-effector position.
     * * FK Formulas:
     * x_E = a1 * cos(q1) + a2 * cos(q1 + q2)
     * y_E = a1 * sin(q1) + a2 * sin(q1 + q2)
     * * @param q1 Joint 1 angle (radians)
     * @param q2 Joint 2 angle (radians)
     * @return double[] {x_end, y_end}
     */
    public double[] getEndEffectorPosition(double q1, double q2) {
        
        // Calculate the end-effector position (x, y)
        double x_end = this.a1 * Math.cos(q1) + this.a2 * Math.cos(q1 + q2);
        double y_end = this.a1 * Math.sin(q1) + this.a2 * Math.sin(q1 + q2);
        
        return new double[]{x_end, y_end};
    }
    
    // P1 will also need a method to get the second joint position for drawing
    public double[] getJoint2Position(double q1) {
        double x_joint2 = this.a1 * Math.cos(q1);
        double y_joint2 = this.a1 * Math.sin(q1);
        return new double[]{x_joint2, y_joint2};
    }
}
