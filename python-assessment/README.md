## Thank you for visiting my Python data assessment. 

Julia Britland, Albany Beck Technology Pioneer

### Introduction

I created this program to explore Python's data libraries, particularly Pandas and Matplotlib. I also explored NumPy during this 
project. 

This console-based application reads data from csv files with data on fuel economy and emissions for car models from 2016 to 2019 
(source: https://www.fueleconomy.gov/feg/download.shtml), loads them into Dataframes, and 
performs a number of operations according to user choice. The user may choose to filter cars by a number of characteristics 
and then rank them by fuel economy, or visualize the aggregated data.

### Using this program

To start, open a command prompt from the project directory and execute the command 'python python_assessment.py'. This file is the 
main program; the other Python files only contain classes and functions used within python_assessment.py. 

Because this project's primary purpose was exploration of the features available in Pandas and Matplotlib, user-friendliness was 
not a high priority, but console output has been color-coded to assist users. Directions are generally in blue, success messages are 
in green, and failure messages are in red. Users should type options precisely as they are printed in cyan; it is recommended that 
the user makes good use of copy and paste to ensure that the option matches exactly.

Upon running the program, the user is presented with a menu with the following options:

#### Rank cars by fuel economy

This option allows users to filter records by a variety of different fields. Once they are done inputting filters, they can choose to 
proceed to the results. At this point, the entire list of records is printed to the results.txt file along with a description of the filters 
applied and a truncated list of records is displayed on the console. The filters were implemented using Pandas.

#### Analyze and visualize data

This option allows users to view charts showing a summary of data in the dataset. Data aggregation was implemented using Pandas and visualization 
was implemented using Matplotlib.

### Summary

This project provided me an introduction to Pandas, Matplotlib, and NumPy. There are improvements that I would make if I continued to work on 
this project; for one, I would consolidate some of the functions that are nearly the same but with small differences. In addition, there 
are certainly more features that could be implemented; e.g., combining filtering and visualization. However, with the foundation of 
knowledge gained working on this project, I am confident in my ability to complete further research 
on these modules and bring them into other projects.

