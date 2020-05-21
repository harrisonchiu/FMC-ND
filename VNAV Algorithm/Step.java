/** PART 1 of VNAV: Calculate Information of a Step
 * 
 * Calculate ROC, Altitude, Weight, Distance of a step to be used in VNAV
 * 
 * TO-DO:
 * # finish get_climbrate()
 * # finish in general lol
 * 
 * ???
 * # Use of initial/final altitude?
 * # "Final distance to Altitude (altitude)" <- from Code Layout
 * # Should use constant or variable g value?
 *     # Which altitude to use (initial or final) for the g value?
 *          # Need a get_altitude() ?
 * # Constants for fuel loss calculations?
 * # Over excessive use of <private> keyword?
 */

class Step {
    private double initial_mass;
    private double climbrate;
    private double initial_altitude, final_altitude;

    // Calculate g value at relative altitudes
    private get_g_value(altitude) {
        private double g_0 = 9.80661716;
        private double earth_radius = 63710088;
        private double g_altitude;

        // g value changes depending altitude (relative to sea level-ish)
        g_altitude = g_0 * ( earth_radius / (earth_radius + altitude) ) ** 2;
        
        return g_altitude;
    }

    // Calculate the amount of fuel burnt to find current mass
    private get_fuelloss(sfc, thrust, step_time) {
        private double fuel_loss = 2 * sfc * thrust * step_time;
        
        return fuel_loss;
    }

    // Use get_fuelloss() and get_g_value() to calculate current weight
    private get_weight(mass, altitude) {
        // Don't know values yet to calculate fuel loss
        private double sfc, thrust, step_time;

        // Which altitude should it use (start/end/mean of the step's altitude)?
        private double g_altitude = get_g_value(altitude);
        private double fuel_loss = get_fuelloss(sfc, thrust, step_time);
        private double weight;

        // Use the start of the step's plane mass?
        weight = (mass - fuel_loss) * g_altitude;

        return weight;
    }

    // Estimate ROC from current weight and altitude
    public get_climbrate(weight, altitude) {
        private double w = weight;
        private double a = altitude;
        private double roc, roc_weight, roc_altitude;

        // Estimate ROC from regression (6th Order Pure Polynomial)
        // Equation uses all 11 s.f. for coefficients
        // If this accuracy of coefficients not necessary, can trunucate
        roc_weight = -8.5155166852E-1 * w + 1.169157177E-6 * w ** 2 - 8.4558287346E-13 * w ** 3 + 
                3.3868606907E-19 * w ** 4 - 7.1146198811E-26 * w ** 5 + 6.1176204379E-33 * w ** 6;
        roc_altitude = 0.058774323642 * a + 6.3524585085E-6 * a ** 2 - 1.6782477276E-9 * a ** 3 + 
                8.8933712943E-14 * a ** 4 - 1.8840407579E-18 * a ** 5 + 1.1411854827E-23 * a ** 6;
        roc = roc_altitude + roc_weight + 259959.11195;

        // Clamp ROC
        if (roc > 3000) {
            roc = 3000;
        }

        return roc;
    }
}
