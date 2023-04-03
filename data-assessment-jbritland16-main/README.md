# Data assessment
#### Project by Julia Britland, Tech Pioneer at Albany Beck

## Description of files in repository
This repository contains my Databases Assessment. It includes 3 images containing diagrams of the conceptual, logical, and physical design of my database. Then, it contains a txt file with the code, which may be used in MySQL to create, populate, and test the database.

## How to use myhomes_db

This database contains 4 tables, as detailed below.

### sales_rep table

This table contains details about the company's sales reps, including their emails (which also act as their primary key), names, supervisors (identified by their email addresses), and salaries. It also includes the date they started working at MyHomes and a code corresponding to the geographic area for which they are responsible (this code can be used to get more details about their geographic area using another table).

### geo_area table

This table contains details about the geographic area divisions of the company. The table includes a unique identifying number, a descriptive name, and the rep in charge of the area (identified by their email and linked to the sales_rep table). Geographic areas are used to divide the workload of customers and properties between sales reps.

### property table

This table contains details about the properties bought and sold by MyHomes. The table includes a unique identifying number, several fields for the property address, an assigned sales rep (identified by their email and referenced in the sales_rep table), a property type (house, flat, bungalow, commercial, or land), a geographic area code (referenced in the geo_area table), and some more specific details about the property, including value, floor area, lot size, and description.

This table contains many columns, so for easy readability, the following views have been created:
* **property_summary** includes basic details needed for business operations
  * Identifying number
  * Area name (from geo_area table)
  * Property value
  * Property type
  * Assigned rep name (from sales_rep table) and email
* **property_details** includes more specific data about the property
  * Identifying number
  * Property type
  * Value
  * Floor area
  * Lot size
  * Description
* **property_location** includes all fields that area part of the property address and the area it is a part of
  * Identifying number
  * Street address
  * City
  * Locality
  * Country
  * Area name (from geo_area table)

### customer table

This table contains details about the customers of MyHomes. The table includes a unique identifying number, name, email, several fields for address, phone number, customer type (Business or Personal), geographic area code (referenced in geo_area table), and assigned rep (identified by email and referenced in the sales_rep table).

Like the property table, this table is wide and difficult to read, so the following views have been created for better readability:
* **customer_summary** contains details about customers relevant to business operations
  * Identifying number
  * Contact name
  * Customer type
  * Area name (from geo_area table)
  * Assigned rep name (from sales_rep table) and email
* **customer_contact** contains the contact details of the customer
  * Identifying number
  * Contact name
  * Email
  * Street address
  * City
  * Locality
  * Country
  * Phone number

## Sample data and queries

Later code populates the tables with data. Sample queries are included at the bottom of the txt file in response to the questions in the project instructions.
