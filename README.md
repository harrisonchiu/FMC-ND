# X-Plane-Altitude Part 1

**This is a documentation of the data collection, regression and its analysis so that future changes can easily be made to the altitude estimator and flight path computer in X-Plane.**

Estimate the 787-9 plane's ROC with multiple regression from the 787-8 plane's altitude and weight


**Units for weight:** `Newtons`\
**Units for Altitude:** `Feet`\
**Units for ROC:** `Feet/min`\



#### Revisions
In each revision, I regress the data and analyze it to improve it on the next revision. Different changes were made to previous revisions to see any changes/improvements in the regression. I also did new revisions when new info about X-Plane came to light (ex: changing to max 3000 feet/min ROC).\

See each revision's `README.md` for more detail

Summary of each revision:
  1) Linear surface regression because 3D scatter plot generally looked like a flat plane
    - Linear may not enough
  2) Comparing Quadratic regression and Linear regression
    - Quadratic is better
  3) Removal of take-off ROC data (kinda outliers) and increasing order to 8th
    - Increasing order does make it fit better and does not drastically change the plane (overfitting may not be a problem)
  4) Adding 5 more datasets and changing max ROC to 3000 feet/min
    - Regression fits better with removal of above 3000 feet/min data
    - 6th order was chosen since overfitting was not considered a problem

#### Assumptions
- The following assumptions are valid because of initial data analysis and was discussed
- Assumes ROC is only (or mostly) affected by `ALTITUDE and WEIGHT`
- Assumes `ALTITUDE and WEIGHT` are completely independent
  - EDIT: Significance P and F in regression analysis supports this
- Mass is only lost from fuel burnt (no refilling fuel)
- The datasets above shows the general range of altitude and weight that will happen to the plane, so no extrapolation is needed (i.e. X-Plane will use altitude and weight combinations similar to the training data) (this and *very* small value coefficients **suggest overfitting is not a problem for our use** in that range)
- Max 3000 feet/min ROC

#### Removal of Data
Data with negative and 0 ROC were omitted because they are anomalies and will not fit with the regression\
Data with above 3000 ROC were omitted because it is unlikely a real plane will climb that fast.

#### General Issues and Explanations
##### Residual Plots
On all regressions (even high order ones) have a pattern in its residual plots (Altitude and ROC). This implies that there is a bias in the regression.\
However, in the *Error View* of the graphs (see `rev4 Updated Dataset (Order 6) Error View` plot) that the take-off data (low altitude) causes its initial pattern because of the sudden jump in ROC followed by a max ROC and a slow decline. The other pattern is at high altitudes where there are two lines of ROC on top of each other. The regression does its best to fit both of them (initially fitting the bottom then slowly trying to fit the top one) which causes the pattern in the residual plot.\
This can be fixed by a revision in the dataset and a possible omission to anomalies. Changes to the model function is unlikely to decrease residual since no single equation can fit two lines on top of each other (but it can fix the take-off ROC jump).

Other causes summary (details explained in `README.md` in `rev4`)
- [x] missing independent variable (a constraint we have)
- [x] too low of an order (unlikely shown in many revisions)
- [ ] a different function model (most possible reason)
  - Log model poor fit (< 46 R^2 although inadequate measure, still good summary of high residuals) (no documentation)

##### Regression Analysis
EDIT: Some regression analysis were wrong (ex: using R^2 for goodness of fit in non-linear models) however, generally, the statements still stands because MSE and Std Error were also calculated. Take R^2 to less consideration for non-linear models.

#### Current Model/Criteria to Choose
Current model is *rev4 6th Order*\
Type of model is `polynomial surface`\
See excel worksheet for equation\
Criteria
- Must have decent fit (seen visually)
- Low errors
- Low max/avg magnitudal residual (cannot have model estimating ROC poorly at any point based on training data)

#### Data Settings
Rate of climb (feet/sec), ROC, for 787-9 plane to help estimate altitude in X-Plane\
Multiple datasets with varying ranges and initial mass improves the accuracy of the regression\
Datasets for 787-8 (B787-8 (502)eis v11) plane with settings:\
  FORMAT: `Range // Payload // Initial Mass`
  1. 7400 nm (design range) // Standard Payload (close to 22000 kg) // 227818 kg
  2. 7400 nm (design range) // 10000 kg // 211368 kg
  3. 7400 nm (design range) // 0 kg // 196454 kg
  4. 500 nm // 0 kg // 130228 kg
  5. 3500 nm // 0 kg // 156045 kg
  6. 3500 nm // 10000 kg // 168155 kg
  7. 3500 nm // 15000 kg // 174261 kg
  8. 3500 nm // 20000 kg // 180390 kg
  9. 3500 nm // 25000 kg // 186546 kg
  10. 2000 nm // 0 kg // 142697 kg
  
Regression was done with both Excel and Octave. Data was gathered with the help of PianoX.

#### Data Adjustments
Data was taken for 787-8 (B787-8 (502)eis v11) but **will be** adjusted for 787-9 (a slightly larger aircraft)\
Factors determined by discussion and comparisons of the two planes\
May be adjusted by after regression:
  - Climb rate (feet/sec) multiplied by a factor of `0.92`
  - Fuel burnt (kg) multiplied by a factor of `1.08`
  - Mass adjusted by the ratio of MTOW of 787-9 to 787-8

#### Calculations
Regression was done with the function model: `z = (a)x + (b)x^2 + . . . + (A)y + (B)y^2 + . . . + (intercept)`\
Because data points were mostly a flat plane and exponential/log would not fit well.\

Weight was calculated by `F = (m_{init} - m_{fuelburnt}) \times g_{altitude}`

##### G
The `g` value was calculated at various altitudes by

`g_{altitude} = g_{0} \times ( Re / (Re + $(altitude)) )^2`

where altitude was converted and (approximated) from feet to meters\
`altitude_{feet} / 3.28084 = altitude_{meters}   (1 feet = 0.3048 metres exactly)`\
and `g0 = 9.80661716 m/s^2` and `Re = 63710088 m`\

##### Mass
Mass `m` at a point in time was calculated by

`m = m_{init} - m_{fuelburnt}`

where `g_{altitude}` was calculated as above and `m_{fuelburnt}` was from the data given and `m_{i}` was the initial mass as described in **Data Settings**
