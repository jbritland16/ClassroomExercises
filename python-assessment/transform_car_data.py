# --------------------------- IMPORTS ---------------------------------------------
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# --------------------------- SET-UP ----------------------------------------------
# Setting up display for pandas
pd.set_option('display.max_columns', 40)
pd.set_option('display.max_rows', 40)
pd.set_option('display.min_rows', 40)
pd.set_option('display.width', 1000)
plt.rcParams["figure.figsize"] = (10, 6)
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

# -------------------------------- MAIN LOGIC ---------------------------------------

class TransformedDataSet:
    '''This class contains data and several pieces of metadata relating to a user's current query. Its methods relate to 
    transforming and visualizing the data. '''
    
    # numeric_columns contains the column names of data that can be used in aggregate functions
    numeric_columns = ['Engine Displacement', 'City FE', 'Highway FE', 'Combined FE', 'City CO2', 'Highway CO2', 'Combined CO2']
    
    def __init__(self, cars_data, query_no):
        self.cars_data = cars_data
        self.description = ''
        self.figure_filename = f'Figure{query_no}'
        self.clean_numeric_data()
    
    def get_data(self):
        return self.cars_data
    
    def get_description(self):
        return self.description
    
    def get_figure_filename(self):
        return self.figure_filename
        
    def next_subfile_name(self):
        '''For creating multiple figures for the same query. If this is the first figure, adds the character "a" to the file name, 
        or for subsequent figures, changes the last character to the next letter in the alphabet'''
        if self.figure_filename[-1].isnumeric():
            self.figure_filename += 'a'
        else:
            self.figure_filename = self.figure_filename[0:-1] + chr(ord(self.figure_filename[-1]) + 1)
    
    def clean_numeric_data(self):
        '''Removes non-numeric data from the columns used for aggregate functions'''
        for column in TransformedDataSet.numeric_columns:
            self.cars_data[column] = pd.to_numeric(self.cars_data[column], errors='coerce')
    
    def format_and_save_plot(self):
        '''Visually formats the figure, saves it to the plots folder, and then shows it using matplotlib'''
        plt.legend(loc='center left', bbox_to_anchor=(1.0, 0.5))
        plt.tight_layout()
        plt.savefig('plots/' + self.figure_filename + '.png')
        plt.show()
    
    def num_carlines_per_mfr(self):
        '''Groups data by manufacturer and counts the number of carlines for each manufacturer, then shows and saves plot'''
        self.cars_data = self.cars_data.groupby(['Mfr Name'])['Carline'].count().sort_values(ascending=False)
        self.description = 'Plotting number of models per manufacturer'
        self.cars_data.plot(kind='bar',
                   title='Models per Manufacturer',
                   xlabel='Manufacturer',
                   ylabel='Models')
        self.format_and_save_plot()
        
    def get_sort_ref_table(self, groupby_column):
        '''Gets a table that assists with sorting pivot tables numerically. Groups by specified column and returns a dataframe 
        with the groupby_column as the index and the means of numeric columns. This allows multi-index pivot tables, such as those grouped 
        by manufacturer, to be sorted by the average fuel economy, engine power, or emissions across years. This method should be called 
        before making any alterations to the cars_data instance variable.'''
        return self.cars_data.groupby([groupby_column])[TransformedDataSet.numeric_columns].mean()
    
    def plot_emissions(self):
        '''Asks the user whether to group data by manufacturer or make, then creates the corresponding pivot table with the 
        means of emissions for the manufacturer or make. For manufacturer, the data is also divided by model year. Prints pivot 
        table and shows and saves plot'''
        choice = ''
        columns = ['City CO2', 'Highway CO2', 'Combined CO2']
        while choice not in ['manufacturer', 'make']:
            print(f'{BLUE}Plot by {CYAN}manufacturer{BLUE} or {CYAN}make{BLUE}?{RESET}')
            choice = input(f'Enter {CYAN}manufacturer{BLUE} or {CYAN}make{BLUE}:{RESET} ').lower()
        if choice == 'make':
            self.cars_data = pd.pivot_table(self.cars_data, index='Division', values=columns, 
                                            aggfunc=np.mean)
            self.cars_data.sort_values('Combined CO2').plot(kind='bar', title='Average CO2 Emissions By Make, all years', 
                                                            xlabel='Make', ylabel='g CO2/mile')
            self.format_and_save_plot()
        else:
            sort_ref_table = self.get_sort_ref_table('Mfr Name')
            self.cars_data = pd.pivot_table(self.cars_data.sort_values('Model Year'), index='Mfr Name', values=columns,
                                                    columns='Model Year', aggfunc=np.mean)
            for column in columns:
                sort_ref_indices = sort_ref_table.sort_values([column]).index
                self.cars_data = self.cars_data.loc[sort_ref_indices]
                self.cars_data.plot(y=column, kind='bar', title=f'{column} Emissions By Manufacturer', 
                                    xlabel='Manufacturer', ylabel='g CO2/mile')
                self.next_subfile_name()
                self.format_and_save_plot()
            self.figure_filename = self.figure_filename[0:-1]
    
    def plot_fuel_efficiency(self):
        '''Asks the user whether to group data by manufacturer or make, then creates the corresponding pivot table with the 
        means of fuel economy for the manufacturer or make. For manufacturer, the data is also divided by model year. Prints pivot 
        table and shows and saves plot'''
        choice = ''
        columns = ['City FE', 'Highway FE', 'Combined FE']
        while choice not in ['manufacturer', 'make']:
            print(f'{BLUE}Plot by {CYAN}manufacturer{BLUE} or {CYAN}make{BLUE}?{RESET}')
            choice = input(f'Enter {CYAN}manufacturer{RESET} or {CYAN}make{BLUE}:{RESET} ').lower()
        if choice == 'make':
            self.cars_data = pd.pivot_table(self.cars_data, index='Division', values=columns, 
                                            aggfunc=np.mean)
            self.cars_data.sort_values('Combined FE').plot(kind='bar', title='Average Fuel Economy By Make, all years', 
                                                            xlabel='Make', ylabel='Mile/gallon')
            self.format_and_save_plot()
        else:
            sort_ref_table = self.get_sort_ref_table('Mfr Name')
            self.cars_data = pd.pivot_table(self.cars_data, index='Mfr Name', values=columns,
                                                    columns='Model Year', aggfunc=np.mean)
            for column in columns:
                sort_ref_indices = sort_ref_table.sort_values([column]).index
                self.cars_data = self.cars_data.loc[sort_ref_indices]
                plot_title = column.replace('FE', 'Fuel Economy') + ' By Manufacturer'
                self.cars_data.plot(y=column, kind='bar', title=plot_title, xlabel='Manufacturer', ylabel='Mile/gallon')
                self.next_subfile_name()
                self.format_and_save_plot()
            self.figure_filename = self.figure_filename[0:-1]
    
    def average_results_by_manufacturer(self):
        '''Groups the data by manufacturer and returns a dataframe containing the averages of each numeric column. Prints 
        the table and saves and shows plots for each numeric column.'''
        self.cars_data = self.cars_data[['Mfr Name'] + TransformedDataSet.numeric_columns].groupby(['Mfr Name']).mean()
        for column in TransformedDataSet.numeric_columns:
            plot_title = f"Average {column.replace('FE', 'Fuel Economy').replace('CO2', 'CO2 Emissions').replace('Displacement', 'Power')} by manufacturer"
            self.cars_data.sort_values(column).plot(y=column, kind='bar', title=plot_title, 
                    xlabel='Manufacturer', ylabel=column) 
            plt.figtext(0.05, 0.01, '''Fuel economy reported in miles per gallon; CO2 emissions reported in g CO2/mile; engine displacement reported in liters''', fontsize=6)
            self.next_subfile_name()
            self.format_and_save_plot()
        self.figure_filename = self.figure_filename[0:-1]
    
    def average_results_by_make(self):
        '''Groups the data by make and returns a dataframe containing the averages of each numeric column. Prints 
        the table and saves and shows plots for each numeric column.'''
        self.cars_data = self.cars_data[['Division'] + TransformedDataSet.numeric_columns].groupby(['Division']).mean()
        for column in TransformedDataSet.numeric_columns:
            plot_title = f"Average {column.replace('FE', 'Fuel Economy').replace('CO2', 'CO2 Emissions').replace('Displacement', 'Power')} by make"
            self.cars_data.sort_values(column).plot(y=column, kind='bar', title=plot_title, 
                xlabel='Manufacturer', ylabel=column)
            plt.figtext(0.05, 0.01, '''Fuel economy reported in miles per gallon; CO2 emissions reported in g CO2/mile; engine displacement reported in liters''', fontsize=6)
            self.next_subfile_name()
            self.format_and_save_plot()
        self.figure_filename = self.figure_filename[0:-1]
    
# select_transform method is not part of TransformedDataSet class; it creates a TransformedDataSet object, calls class methods on it, 
# and returns it when done

def select_transform(cars_data, query_no):
    '''Takes a dataframe as an argument and uses it to create a TransformedDataSet object. Iterates through a menu, allowing 
    the user to choose what to do with the data. When the user has made their choices, returns the 
    TransformedDataSet object.'''
    transformed_data_set = TransformedDataSet(cars_data, query_no)
    choice = ''
    while choice != 0:
        print(f'''{BLUE}Choose an option: 
        {CYAN}1 {BLUE}- Plot number of models by manufacturer
        {CYAN}2 {BLUE}- Plot emissions
        {CYAN}3 {BLUE}- Plot fuel efficiency
        {CYAN}4 {BLUE}- View average data by manufacturer
        {CYAN}5 {BLUE}- View average data by make{RESET}
        ''')
        try:
            choice = int(input('Please enter the number of your choice: '))
        except:
            choice = -1
        if choice == 1:
            transformed_data_set.num_carlines_per_mfr()
            return transformed_data_set
        elif choice == 2:
            transformed_data_set.plot_emissions()
            return transformed_data_set
        elif choice == 3:
            transformed_data_set.plot_fuel_efficiency()
            return transformed_data_set
        elif choice == 4:
            transformed_data_set.average_results_by_manufacturer()
            return transformed_data_set
        elif choice == 5:
            transformed_data_set.average_results_by_make()
            return transformed_data_set
        else:
            print(f'{RED}Invalid option.')