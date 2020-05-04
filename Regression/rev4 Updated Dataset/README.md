# Addition of 5 More Datasets and Removal of ROC Data Above 3000 feet/min

The plane will never have an ROC of above 3000 feet/min normally. More datasets were included to accomadate for less data and fill in gaps of data seen in Weight and ROC graph view in previous revisions.\
Data used were **not ANTIANOMALY** (included the take off ROC data)


### Data Settings
Total datasets for 787-8 (B787-8 (502)eis v11) plane with settings (the added ones italicized)
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
FORMAT: `R Squared // Standard Error // MAX Residual // Average Residual` // `Equation (only 5 s.f. shown) where R = ROC, W = Weight, A = Altitude`\
**1ST ORDER:** `0.879434 // 256.7423 // 848.9893 // 207.5285` // `R = -1.1784e-3(W) -6.8189(A) + 5.6169`






