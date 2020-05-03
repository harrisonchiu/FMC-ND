# X-Plane-Altitude

Rate of climb (feet/sec), ROC, for 787-9 plane to help estimate altitude in X-Plane\
Data was taken for 787-8 (a slightly smaller aircraft) but was adjusted for 787-9
  - Climb rate (feet/sec) was multiplied by a factor of 0.92
  - Fuel burned (kg) should be multiplied by a factor of 1.08
From the data, it was regressed to estimate roc for any altitude and weight\
Data with negative and 0 ROC were omitted because they are anomalies and will not fit with the regression\
Based on 3-D scatter plot, linear regression was chosen for the best fit to estimate ROC

Weight was calculated by multiplying given mass (kg) with g\
The g value was calculated at various altitudes using the formula

![Adjusted G Value](http://latex.codecogs.com/png.latex?%5Clarge%20g_%7Baltitude%7D%20%3D%20g_%7B0%7D%20%5Ctimes%20%28%5Cfrac%7BR_%7Bearth%7D%7D%7BR_%7Bearth%7D%20&plus;%20%28altitude%29%7D%29%5E2)

where altitude was converted (and approximated) from feet to meters by dividing 3.281\
and g_0 is the standard g value of 9.80661716 m/s^2\
and r_e is the radius of the Earth at 63710088 m
