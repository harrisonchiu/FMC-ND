# X-Plane-Altitude Part 1

### Estimate ROC with multiple linear regression based on the planes' altitude and weight

#### Data Settings
Rate of climb (feet/sec), ROC, for 787-9 plane to help estimate altitude in X-Plane\
Multiple datasets with varying ranges and initial mass improves the accuracy of the regression\
Datasets for 787-8 (B787-8 (502)eis v11) plane with settings:
  1. 7400 nm (design range) and 227818 kg initial mass (standard payload and close to MTOW of 227930 kg)
  2. 7400 nm (design range) and 211368 kg initial mass (10000 kg payload)
  3. 7400 nm (design range) and 196454 kg initial mass (0 kg payload)
  4. 500 nm and 130228 kg initial mass (0 kg payload)
  5. 3500 nm and 156045 kg initial mass (0 kg payload)
  
As seen in graphs (weight vs ROC angle), there are notable gaps in data for $(weight) ∈ [1.57e+6, 1.85e+6]\
Although relationship is already established, more datasets can be included for a more complete regression
- [ ] Add more datasets
  
#### Data Adjustments
Data was taken for 787-8 (B787-8 (502)eis v11) but was adjusted for 787-9 (a slightly larger aircraft)
  - Climb rate (feet/sec) was multiplied by a factor of `0.92`
  - Fuel burnt (kg) should be multiplied by a factor of `1.08`

#### Versions
Two versions of this was done:
  1. One with **preadjusted data** as specified above **before regression**
  2. One **not adjusted** in any way using the `Fuel burnt and ROC` data of 787-8\
The first will be adjusted later for `Weight` to fit the data of 787-9 *(initial mass used was a 787-8)*\
The second will be adjusted later for `Fuel burnt, ROC, and Weight` to fit the data of 787-9\

#### Assumptions
- Data is linearly regressed, so it is all estimations
  - [ ] check if data is overfitted (github.com/trekhleb/machine-learning-octave/tree/master/linear-regression)
- Assumes ROC is only (or mostly) affected by `ALTITUDE and WEIGHT`
- Assumes `ALTITUDE and WEIGHT` are completely independent
- Mass is only lost from fuel burnt (no refilling fuel)

#### Anomalies
Data with negative and 0 ROC were omitted because they are anomalies and will not fit with the regression\
Based on 3-D scatter plot, linear regression was chosen for the best fit to estimate ROC

#### Calculations
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
