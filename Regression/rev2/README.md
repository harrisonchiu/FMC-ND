# Multiple Quadratic Regression

Regression revision from excessive residual (1100.93548 max residual) from previous linear regression. NONPREADJUSTED data was chosen from rev1, so onward, only NONPREADJUSTED data will be used.

#### Linear Regression Summary and Comparison to Quadratic Regression
The linear regression had a good fit with `RSQUARED = 0.909505487` and visually fits\
The linear regression residual plots showed a pattern which suggested a lack of order or independent variable\
The residual plots still show a patter with the order increased, suggesting a missing `x` variable\
However, based on how the data is gathered, we are stuck with only WEIGHT/MASS and ALTITUDE for ROC, so that problem cannot be fixed.\

#### Problems
Data still has a large residual with `max_residual = 1248.742`, although the `avg_magnitude_residual = 242.777619`\
The max residual is still abnormally high despite having a better fit and lower average residual magnitude and lower standard error.\
**Suggests that this is an anomaly.**\
Visually, this anomaly can be seen in the graph at low altitudes and high ROC, where it starts slowly increasing and then has a large jump.\
If these data points are seperated, a much better regression can be done.

#### Cubic Regression
Although **a cubic regression is not recommended** due to already having a good fit and it is in best practice to use the lowest practical order possible to avoid overfitting. Personally, I think using a lower order to estimate values is better than using a overfitted regression; it may have a lower residual but extrapolating to fill the gaps in the datasets would be bad.\

**EDIT:** The quadratic regression equation has very small coefficients for `X^2`, so a cubic regression may not be terrible because its `X^3` coefficients would be negligible but sufficient enough in fitting better. Despite this, higher order polynomials (5+) still should not be done.
