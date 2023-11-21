import mysql.connector;
from mysql.connector import Error;
import pytest;

def test_employee_table_exists(cursor):
    cursor.execute('select id from employee')
    rs = cursor.fetchall()
    assert len(rs) > 1 
    
def test_add_email(cursor):
    cursor.execute('insert into contact (emp_id, email) values(1, "julia@mail.com")')
    cursor.execute('select email from contact')
    rs = cursor.fetchall()[0][0]
    print(type(rs), rs)
    assert rs == "julia@mail.com"