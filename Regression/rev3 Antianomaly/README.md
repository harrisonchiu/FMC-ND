# Removal of take off ROC data and Increased Order

### Versions
Three versions were created to see the limit of absolute perfect fit (limiting maximum and average residuals to its absolute minimum). All versions removed the largest anomaly data points (initial ROC when taking off) although they are not really anomalies.\
- 2nd Order
- 4th Order
- 8th Order (no graphs, but equation and regression and residual data are available)

### Results
All equations from all versions have extremely low coefficients for high ordered variables (`X^2, X^3 and above`).\ 
All equations have **still have relatively high max residuals** (approximately `600` for 8th order and `700` for 2nd order).\
However, they all have approximately `200` magnitude residuals which is quite small.\
So, even in the scenario where the most prominent anomaly is removed, data still has a high residual.\
More data should **not** be removed to see a better fit.\
**Suggests that the influence of higher order polynomials is not a factor for a better fit and a new independent variable is needed for a better fit**\
All equations are visually similar to the linear equation and overfitting does not appear to be a problem (not sure about orders above 4).\
The 4th order can be used for the "best" fit but even a linear one is sufficient and a quadratic surface is more than sufficient.\
