## Addition of 5 More Datasets and Removal of ROC Data Above 3000 feet/min

The plane will never have an ROC of above 3000 feet/min normally. More datasets were included to accomadate for less data and fill in gaps of data seen in Weight and ROC graph view in previous revisions.\
Data used were **not ANTIANOMALY** (included the take off ROC data)


**EDIT:** On revision, **R Squared is inadequate measure** for fitness for 2nd order and above. Data and comments below relating to R Squared should be take to **less consideration**. MSE is a much better comparison tool (see Excel worksheet) and in the context of our working, max residual as well.


### Data Settings
Total datasets for 787-8 (B787-8 (502)eis v11) plane with settings (the added ones italicized)\
FORMAT: `Range // Payload // Initial Mass`
  1. `7400 nm (design range) // Standard Payload (close to 227930 kg MTOW) // 227818 kg`
  2. `7400 nm (design range) // 10000 kg // 211368 kg`
  3. `7400 nm (design range) // 0 kg // 196454 kg`
  4. `500 nm // 0 kg // 130228 kg`
  5. `3500 nm // 0 kg // 156045 kg`
  6. *3500 nm // 10000 kg // 168155 kg*
  7. *3500 nm // 15000 kg // 174261 kg*
  8. *3500 nm // 20000 kg // 180390 kg*
  9. *3500 nm // 25000 kg // 186546 kg*
  10 *2000 nm // 0 kg // 142697 kg*

Spacing and density of data points could affect the regression (currently somewhat uneven spacing, seen in Weight and ROC graph view of rev4).
- [ ] Redo datasets with a more even weight and range spacing

### Regression Analysis Summation
Only R Squared, Standard Error, Max Residual, Average Residual, and Equation were deemed significant.\
Adjusted R Square and R Square were essentially the same value for all regression.\
All had a similar pattern in residual plots as seen previously.\
FORMAT: `R Squared // Standard Error // MAX Residual // Average Residual` // `Equation (not all s.f. shown) where R = ROC, W = Weight, A = Altitude`\
**1ST ORDER:** `0.879434 // 256.7423 // 848.9893 // 207.5285` // `R = -1.1784e-3(W) -6.8189e-2(A) + 5.6169e3`\
**2ND ORDER:** `0.961271 // 145.7302 // 524.3858 // 104.2483` // `R = -0.0039424627253(W) + 7.41858e-10(W^2) + 3.89078e-3(A) - 1.57152e-6(A^2) + 7662.1951276`\
**4TH ORDER:** `0.974605 // 118.358 // 472.7458 // 84.73256` // `R = 3.022e-2(W) -2.915e-8(W^2) + 1.1487(W^3) - 1.64269(W^4) + 1.549e-1(A) - 1.7679e-5(A^2) + 5.54875e-10(A^3) - 6.111e-15(A^4)`\
**6TH ORDER:** `0.97574 // 116.03 // 513.2387 // 76.377732` // `R = -8.515e-1(W) + 1.169e-6(W^2) - 8.4558e-13(W^3) + 3.38686e-19(W^4) - 7.11462e-26(W^5) + 6.11762e-33(W^6) + 5.877e-2(A) + 6.3524e-6(A^2) - 1.6782e-9(A^3) + 8.89e-14(A^4) - 1.88e-14(A^5) + 1.411e-23(A^6)`\
**8TH ORDER:** `0.979 // 107.36 // 478.3756 // 72.66976` // `See excel sheet (too long); visually linear because all coefficients for weight is essentially 0`\
See excel worksheet for more info

Overfitting does not seem to be a problem for the data we will use because
  1. Coefficients are very small and would not change value much
  2. Estimation of ROC will use altitude and weight similar to training data, so rarely is it ever extrapolated
  
**8th Order is a linear equation, ???????** Excel was used for regression; visually appears to have the fit of a linear, but Excel said it has much better fit?\
8th Order is an anomaly and should not be used, probably a bug with the amount of data and variables.\
Regardless, **Order 2-8 does not have much difference in terms of residual and fit**\

**6th Order** should be chosen if lower residual average is prefered.\
**4th Order** should be chosen if lower max residual is prefered.\
**2nd Order** should be chosen if it estimation of take off ROS is prefered.\
See their respective graphs\
4th Order and 6th Order have the **same fit**

Max residual could still be considered high (~500 difference), but it is unlikely to get lower residuals unless another independent variable is introduced or an excessively high order regression, considering the amount of revisions made.
- [ ] use numpy/matplotlib for machine learning?

### Issues
All residual plots for Altitude and ROC have a pattern (see images) (Weight and ROC residual plots do not matter because it is essentially a side view of points).\
This is a problem as it usually indicates a bias in the regression and therefore less reliable. It is prefered that the residual plots are random scatter plots so that only random error is left.\
This indicates that
- [x] a missing variable
- [x] too low of an order
- [ ] a missing interaction between terms (a different function model)
In previous revisions, we showed that higher models do not change much of the equation because its coefficients of higher polynomials converge to zero. We were told that only two variables are needed and will be used to estimate the ROC, so based on our given constraint, it will not be changed. A possibility is that a different model function could better describe the relationships.
