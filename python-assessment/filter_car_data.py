# --------------------------- IMPORTS ---------------------------------------------
import pandas as pd
import numpy as np

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

# ------------------------- MAIN LOGIC --------------------------------------------

class FilteredDataSet:
    '''This class contains data and several pieces of metadata relating to a user's current query. Its methods relate to 
    filtering the data by values in the columns. '''

    def __init__(self, data_set):
        self.data_set = data_set
        self.current_filter_desc = 'FILTERED BY '
        
    def get_data(self):
        return self.data_set
    
    def get_filter_desc(self):
        return self.current_filter_desc
    
    def truncate_filter_desc(self):
        '''Deletes the last two characters from the filter description; used after creating a list within the filter description to 
        delete the unnecessary comma and space from the end'''
        self.current_filter_desc = self.current_filter_desc[:-2]

    def filter_by_match(self, column_name):
        '''For filtering by the value in a column. All values currently in that column are displayed and the user is prompted to enter 
        which ones they want included in the results. When they are done, the data is filtered and assigned to data_set instance variable.'''
        choice = ''
        filter_desc = column_name + ': '
        filtered = False
        mask = np.repeat(False, len(self.data_set.index))
        options_list = self.data_set[column_name].unique().tolist()
        options_list.sort()
        while choice.lower() != 'cancel' and choice.lower() != 'done':
            print(f'{BLUE}Copy and paste each option to include, pressing enter after each, then enter {CYAN}done{BLUE} (or {CYAN}cancel{BLUE} to proceed without filtering):{RESET}\n')
            for item in options_list:
                print(f'({CYAN}' + item, end=f'{RESET}) ')
            print('\b ')
            choice = input(f'{BLUE}\nEnter your choice or {CYAN}done{BLUE}: {RESET}')
            if choice in options_list:
                print(f'{GREEN}Added {CYAN}{choice}{GREEN} to filter{RESET}')
                mask = mask | (self.data_set[column_name] == choice)
                options_list.remove(choice)
                filtered = True
                filter_desc += f'{choice}, '
            elif choice.lower() != 'cancel' and choice.lower() != 'done':
                print(f'{RED}Option not recognized.{RESET}')
        if filtered:
            self.current_filter_desc += filter_desc[:-2] + '; '
            self.data_set = self.data_set[mask]

    def filter_by_transmission(self):
        '''For filtering by transmission type. Prompts the user to enter auto or manual and filters accordingly, assigning the 
        filtered data set to the instance variable data_set.'''
        choice = ''
        filtered = False
        options_list = self.data_set['Transmission'].unique().tolist()
        mask = np.repeat(False, len(self.data_set.index))
        while choice.lower() != 'cancel' and not filtered:
            print(f'{BLUE}Choose {CYAN}auto{BLUE} or {CYAN}manual{BLUE} transmission (or {CYAN}cancel{BLUE} to proceed without filtering).{RESET}\n')
            choice = input(f'{BLUE}\nEnter your choice or {CYAN}done{BLUE}: {RESET}')
            for item in options_list:
                if choice.lower() in item.lower():
                    mask = mask | (self.data_set['Transmission'] == item)
                    filtered = True
            if choice.lower() != 'cancel' and not filtered:
                print(f'{RED}Option not recognized.{RESET}')
        if filtered:
            self.current_filter_desc += 'Transmission: ' + choice + '; '
            self.data_set = self.data_set[mask]

    def filter_by_year(self):
        '''For filtering by model year. Prints a list of years currently in the data set and prompts the user to enter 
        minimum or maximum, then the year, and filters accordingly, assigning the 
        filtered data set to the instance variable data_set.'''
        choice = ''
        while choice.lower() != 'cancel' and choice.lower() != 'done':
            years_list = self.data_set['Model Year'].unique().tolist()
            years_list.sort()
            print(f'{BLUE}The data currently contains cars from the following years: ')
            for item in years_list:
                print(item, end=', ')
            print(f'\b\b  \n')
            choice = input(f'Would you like to enter a {CYAN}minimum{BLUE} or {CYAN}maximum{BLUE}? (Or enter {CYAN}done{BLUE}): {RESET}').lower()
            if choice == 'minimum' or choice == 'maximum':
                year = input(f'{BLUE}Please enter the minimum year: {RESET}')
                try:
                    year = int(year)
                    if choice == 'minimum':
                        self.data_set = self.data_set[self.data_set['Model Year'] >= year]
                        self.current_filter_desc += f'minimum year: {year}; '
                    else:
                        self.data_set = self.data_set[self.data_set['Model Year'] <= year]
                        self.current_filter_desc += f'maximum year: {year}; '
                    filtered = True
                except:
                    print(f'{RED}Not a valid year. Please try again.{RESET}')                
            elif choice.lower() != 'cancel' and choice.lower() != 'done':
                print(f'{RED}Option not recognized.{RESET}')
        
    def filter_by_engine_power(self):
        '''For filtering by engine power. Prints the minimum and maximum engine power currently in the data set and prompts the 
        user to enter minimum or maximum, then a number, and filters accordingly, assigning the 
        filtered data set to the instance variable data_set.'''
        choice = ''
        while choice.lower() != 'cancel' and choice.lower() != 'done':
            power_list = self.data_set['Engine Displacement'].unique().tolist()
            power_list.sort()
            print(f'{BLUE}The current minimum engine power is {power_list[0]}')
            print(f'{BLUE}The current maximum engine power is {power_list[-1]}')
            choice = input(f'Would you like to enter a {CYAN}minimum{BLUE} or {CYAN}maximum{BLUE}? (Or enter {CYAN}done{BLUE}): {RESET}').lower()
            if choice == 'minimum' or choice == 'maximum':
                power = input(f'{BLUE}Please enter the minimum engine power: {RESET}')
                try:
                    power = float(power)
                    if choice == 'minimum':
                        self.data_set = self.data_set[self.data_set['Engine Displacement'] >= power]
                        self.current_filter_desc += f'minimum power: {power}; '
                    else:
                        self.data_set = self.data_set[self.data_set['Engine Displacement'] <= power]
                        self.current_filter_desc += f'maximum power: {power}; '
                    filtered = True
                except:
                    print(f'{RED}Not a valid number. Please try again.{RESET}')
            elif choice.lower() != 'cancel' and choice.lower() != 'done':
                print(f'{RED}Option not recognized.{RESET}')

# Input filters method is not part of FilteredDataSet class; it creates a FilteredDataSet object, calls class methods on it, 
# and returns it when done        

def input_filters(all_data):
    '''Takes a dataframe as an argument and uses it to create a FilteredDataSet object. Iterates through a menu, allowing 
    the user to choose and specify filters. When the user indicates that they are done specifying filters, returns the 
    FilteredDataSet object.'''
    filtered_data_set = FilteredDataSet(all_data)
    choice = ''
    while choice != 0:
        print(f'''{BLUE}Choose a filter or enter 0 proceed to results: 
        {CYAN}1 {BLUE}- Transmission type
        {CYAN}2 {BLUE}- Body type
        {CYAN}3 {BLUE}- Drive type
        {CYAN}4 {BLUE}- Model year
        {CYAN}5 {BLUE}- Manufacturer
        {CYAN}6 {BLUE}- Make
        {CYAN}7 {BLUE}- Engine power
        {CYAN}0 {BLUE}- Proceed to results{RESET}
        ''')
        try:
            choice = int(input(f'Please enter the number of your choice ({CYAN}0{RESET} to proceed to results): '))
        except:
            choice = -1

        if choice == 0:
            filtered_data_set.truncate_filter_desc()
            return filtered_data_set
        elif choice == 1:
            filtered_data_set.filter_by_transmission()
        elif choice == 2:
            filtered_data_set.filter_by_match('Carline Class Desc')
        elif choice == 3:
            filtered_data_set.filter_by_match('Drive Desc')
        elif choice == 4:
            filtered_data_set.filter_by_year()
        elif choice == 5:
            filtered_data_set.filter_by_match('Mfr Name')
        elif choice == 6:
            filtered_data_set.filter_by_match('Division')
        elif choice == 7:
            filtered_data_set.filter_by_engine_power()
        else:
            print(f'{RED}Invalid option.')
                
        
        

