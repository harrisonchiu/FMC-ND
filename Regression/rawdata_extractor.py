"""
# Script to help organize raw data and extract only the desired data
# For an easy copy paste into Excel
"""

# Read raw data
with open("pianox-raw-data.txt", "r") as f:
    alldata = f.readlines()


# Only get desired data
start, end = [], []
starting = False
for index, line in enumerate(alldata):
    if line[:9] == " History:":
        start.append(index)
        starting = True
    if line[:9] == " --------" and starting == True:
        end.append(index)
        starting = False

# Aggregate the desired data
desired = []
for i in range(len(start)):
    desired.append(alldata[ start[i]:end[i] ])

# Delete unneccessary headers
for segment in desired:
    del segment[0]

desired.insert(0,"             Alt    Time      Dist      Burnt    FN/eng   CAS    Mach    RoC\n")
print(len(desired))

# Write out organized data for easy Ctrl-C, Ctrl-V into Excel
f = open("only-desired-data.txt", "w")
for segment in desired:
    for line in segment:
        f.write(line)
f.close()


# Reorganize data into a single line seperated by space
# For inputing into Matlab/Octave
"""
with open("indata.txt", "r") as f:
    data = f.read().split()
data = " ".join(data)

f = open("new.txt", "w")
f.write(data)
f.close()
"""
