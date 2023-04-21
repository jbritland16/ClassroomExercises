# --------------------------- IMPORTS ---------------------------------------------
# Python modules
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os
import shutil
# Imports from within the Python files in the directory
import filter_car_data as filters
import transform_car_data as transform

# --------------------------- SET-UP ----------------------------------------------
# Define constants for formatting output in the console
BLACK = '\033[1;30m'
RED = '\033[1;31m'
GREEN = '\033[1;32m'
YELLOW = '\033[1;33m'
BLUE = '\033[1;34m'
MAGENTA = '\033[1;35m'
CYAN = '\033[1;36m'
RESET = '\033[1;0m'
DIVIDER = '\n' + ('-' * 100) + '\n'
# Read data to a common dataframe
data2015 = pd.read_csv('2015-car-data.csv')
data2016 = pd.read_csv('2016-car-data.csv')
data2017 = pd.read_csv('2017-car-data.csv')
data2018 = pd.read_csv('2018-car-data.csv')
data2019 = pd.read_csv('2019-car-data.csv')
all_data = pd.concat([data2015, data2016, data2017, data2018, data2019], ignore_index=True, join='inner')
pd.set_option('display.max_rows', 100)
# Create/overwrite files that store session results
query_no = 1
print('Welcome to the Cars Data Viewer.')
with open('results_file.txt', 'w') as f:
    f.write('Session started.\n\n')
if os.path.exists('plots'):
    shutil.rmtree('plots')
os.makedirs('plots')
print(f'{GREEN}Created results.txt file and plots folder. These will be overwritten next time you run this program.{RESET}')

# ------------------------ DEFINE FUNCTIONS FOR OUTPUT -------------------------------------
    
def sort_by_fuel_economy(cars_data):
    '''Gets user input to determine which fuel economy column to sort by, then returns the sorted dataframe'''
    sort_by = ''
    while sort_by not in ['City', 'Highway', 'Combined']:
        sort_by = input(f'{BLUE}Would you like to sort by {CYAN}City{BLUE}, {CYAN}Highway{BLUE}, or {CYAN}Combined{BLUE} fuel economy?{RESET} ').title()
        if sort_by not in ['City', 'Highway', 'Combined']:
            print(f'{RED}Invalid option.{RESET}')
    df = cars_data.loc[pd.to_numeric(cars_data[sort_by + ' FE'], errors='coerce').sort_values(ascending=False).index].reset_index(drop=True)
    df.index += 1
    return df

def fuel_economy_view(cars_data):
    '''Filters columns to display those related to fuel economy and returns the resulting dataframe'''
    return cars_data[['Division', 'Carline', 'Model Year', 'City FE', 'Highway FE', 'Combined FE']]
    
def append_filtered_results_to_file(sorted_data, filter_desc):
    '''Adds the results of a query using filter_car_data.py to results_file.txt and increments query_no'''
    global query_no
    with open('results_file.txt', 'a') as f:
        f.write(f'\n{DIVIDER}\nQuery {query_no}:\n\n' + filter_desc + '\n' + fuel_economy_view(sorted_data).to_string())
        query_no += 1
    print(f'{GREEN}Full results have been written to results.txt.{RESET}')

def print_filtered_results(sorted_data):
    '''Prints the results of a query using filter_car_data.py to the console'''
    if len(sorted_data) > 40:
            print(f'{GREEN}Top 20 results: {RESET}')
            print(fuel_economy_view(sorted_data.head(20)))
            print(f'{GREEN}Bottom 20 results:{RESET}')
            print(fuel_economy_view(sorted_data.tail(20)))
    else:
        print(f'{GREEN}Results:{RESET}')
        print(fuel_economy_view(sorted_data))

def append_transformed_result_to_file(transformed_data_set):
    '''Appends the results of a query using transform_car_data.py to results_file.txt and increments query_no'''
    global query_no
    with open('results_file.txt', 'a') as f:
        f.write(f'\n{DIVIDER}\nData for query {query_no}:\n\n{transformed_data_set.get_description()}\n{transformed_data_set.get_data()}')
        f.write(f'\nPlot was saved as {transformed_data_set.get_figure_filename()} in plots folder')
        query_no += 1
    print(f'{GREEN}Data has been written to results.txt and image has been saved to plots folder.{RESET}')

# ----------------------- MAIN MENU --------------------------------------------

choice = ''
while choice != 0:
    print(f'''{BLUE}\nPlease select one of the following options:
    {CYAN}1 {BLUE}- Rank cars by fuel economy
    {CYAN}2 {BLUE}- Analyze and visualize data
    {CYAN}0 {BLUE}- Quit{RESET}
    ''')
    try:
        choice = int(input('Please enter the number of your choice: '))
    except:
        choice = -1
    
    if choice == 0:
        print('Goodbye!')
        
    elif choice == 1:
        filtered_data_set = filters.input_filters(all_data)
        sorted_data = sort_by_fuel_economy(filtered_data_set.get_data())
        append_filtered_results_to_file(sorted_data, filtered_data_set.get_filter_desc())
        print_filtered_results(sorted_data)
    
    elif choice == 2:
        transformed_data_set = transform.select_transform(all_data, query_no)
        append_transformed_result_to_file(transformed_data_set)
        
    else:
        print('Invalid option.')

