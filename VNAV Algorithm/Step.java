/** PART 1 of VNAV: Calculate Information of a Step
 * 
 * Calculate ROC, Altitude, Weight, Distance of a step to be used in VNAV
 * 
 * ???
 * # "Final distance to Altitude (altitude)" <- from Code Layout
 * # Should use constant or variable g value?
 *     # Which altitude to use (initial or final) for the g value?
 * # Constants for fuel loss calculations?
 */

public class Step {
    // Find current altitude
    private double initial_altitude; // Use initial or final altitude for fuel/roc calculations??? USE INITAL
    private double final_altitude;
    private double initial_mass;
    
    // Calculate g value at relative altitudes
    private double findGValue(double altitude) {
        // Constants for formula
        double g_0 = 9.80661716;
        double earth_radius = 63710088;
        double g_altitude;

        // g value changes depending altitude (relative to sea level-ish)
        g_altitude = g_0 * Math.pow( (earth_radius / (earth_radius + altitude)), 2 );
        
        return g_altitude;
    }

    // Calculate amount of fuel burnt to find current mass
    private double findFuelLoss(int sfc, int thrust, int step_time) {
        // Constants not yet given
        double fuel_loss = 2 * sfc * thrust * step_time;
        return fuel_loss;
    }

    // Use findFuelLoss() and findGValue() to calculate current weight
    private double findWeight(double mass, double altitude) {
        // Initialize unknown constants for fuel_loss
        int sfc = 1;
        int thrust = 1;
        int step_time = 1;
        double fuel_loss = findFuelLoss(sfc, thrust, step_time);

        double g_altitude = findGValue(altitude);
        double weight;

        // Calculate current weight
        weight = (mass - fuel_loss) * g_altitude;
    
        return weight;
    }

    // Use current weight and altitude to estimate Climb Rate in findClimbRate()
    // Use initial_mass and initial_altitude??? -> should calculate current mass/altitude???
    private double current_weight = findWeight(initial_mass, initial_altitude);
    private double current_altitude = initial_altitude;

    public double findClimbRate() {
        // To make equation more conscise
        double w = current_weight;
        double a = current_altitude;

        double roc, roc_weight, roc_altitude;

        // Estimate ROC from regression (6th Order Pure Polynomial)
        // Equation uses all 11 s.f. for coefficients
        // If this accuracy of coefficients not necessary, can trunucate
        roc_weight = -8.5155166852E-1 * w + 1.169157177E-6 * Math.pow(w, 2) 
                    - 8.4558287346E-13 * Math.pow(w, 3) + 3.3868606907E-19 * Math.pow(w, 4) 
                    - 7.1146198811E-26 * Math.pow(w, 5) + 6.1176204379E-33 * Math.pow(w, 6);
        roc_altitude = 0.058774323642 * a + 6.3524585085E-6 * Math.pow(a, 2) 
                    - 1.6782477276E-9 * Math.pow(a, 3) + 8.8933712943E-14 * Math.pow(a, 4) 
                    - 1.8840407579E-18 * Math.pow(a, 5) + 1.1411854827E-23 * Math.pow(a, 6);
        roc = roc_altitude + roc_weight + 259959.11195;

        // Clamp ROC
        if (roc > 3000) {
            roc = 3000;
        }

        return roc;
    }

    // Unused method
    public double findDistanceToAltitude(int altitude) {
        double dist_to_altitude = 1;
        
        // Not known yet

        return dist_to_altitude;
    }
}
