# Calculate the Probability of Wind Speeds Java
## Purpose
This program will create a cumulative probability distribution for wind energy that can be used to calculate the probability of the occurrence of wind speeds. 

This can be useful for people who pursue activities that depend on a minimum or maximum wind speed such as windsurfing or sailing – or for the placement of energy generation systems such as wind-powered turbines.

> “The principle of maximum entropy states that the probability
distribution which best represents the current state of knowledge is the one with largest entropy, in
the context of precisely stated prior data." (Wikipedia)
## Data Source
The data for [Augspurger (March 2017)](Augspurger_2017_03.csv) was obtained from Bonneville Power Administration site to test the program
[Bonneville Power Administration site](https://transmission.bpa.gov/business/operations/wind/MetData/default.aspx)

## Design of the Program
### Java
- ask the user for the width of the bin for the histogram (a number between 50 and 101)
- read in the data from the CSV file
- make a histogram from the data
- Normalize probability distribution function
- Perform ordinary least-squares (OLS) regression analysis to create a histogram based on Maximum Entropy function
- Write cumulative probability value for each of the bin values to [cumProbability.txt](cumProbability.txt)
- Prompt the user and ask them if they want to compute a probability of the wind speed being less than OR greater than/equal-to a value they will enter, and then return the requested probability

### JavaDoc
This is the JavaDoc (add link please) of the program

### JavaFx
A [JavaFx](JavaFX-cumProbability) program was written on IntelliJ to generate the Cumulative Probability of [cumProbability.txt](cumProbability.txt)
![Cumulative Probability of Wind Speed](Cumulative_Probability_graph.png)


