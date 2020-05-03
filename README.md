# X-Plane-Altitude Part 1

### Purpose
Estimate ROC with multiple linear regression based on the planes altitude and weight

### Data Settings
Rate of climb (feet/sec), ROC, for 787-9 plane to help estimate altitude in X-Plane\
Data for 787-8 (B787-8 (502)eis v11) plane with settings:
  1. Design range (approximately 7400 nm)
  2. Payload varying with initial mass starting from 227818 to [x] kg

### Data Adjustments
Data was taken for 787-8 (B787-8 (502)eis v11) but was adjusted for 787-9 (a slightly larger aircraft)
  - Climb rate (feet/sec) was multiplied by a factor of 0.92
  - Fuel burned (kg) should be multiplied by a factor of 1.08

### Versions
Two versions of this was done:
  - One preadjusted as specified above
  - One not adjusted above (to be adjusted later along with the weight of the plane)

### Assumptions
Data is linearly regressed, so it is all estimations
  - [] check if data is overfitted (github.com/trekhleb/machine-learning-octave/tree/master/linear-regression)
Assumes ROC is only (or mostly) affected by ALTITUDE and WEIGHT\
Assumes ALTITUDE and WEIGHT are completely independent\
Takes into account that mass is constantly lost from fuel burnt\

### Anomalies
Data with negative and 0 ROC were omitted because they are anomalies and will not fit with the regression\
Based on 3-D scatter plot, linear regression was chosen for the best fit to estimate ROC

### Calculations
Weight was calculated by multiplying given mass (kg) with `g`\
The `g` value was calculated at various altitudes using the formula

![Adjusted G Value](http://www.sciweavers.org/upload/Tex2Img_1588474869/render.png)

where altitude was converted and (approximated) from feet to meters\
`altitude_feet / 3.28084 = altitude_meters   (1 feet = 0.3048 metres exactly)`\
and `g0 = 9.80661716 m/s^2` and `Re = 63710088 m`
