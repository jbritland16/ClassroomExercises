import pytest;
import mysql.connector;
from mysql.connector import Error;

@pytest.fixture(scope='module')
def cnxn():
    cnxn = mysql.connector.connect(host="localhost",
                                         database="employees_db",
                                         user="root",
                                         password="");
    yield cnxn;
    cnxn.close();
    
@pytest.fixture
def cursor(cnxn):
    cursor = cnxn.cursor();
    yield cursor;
    cnxn.rollback();
    
''' @pytest.fixture
def new_email(cursor):
    stmt = "INSERT INTO contact(emp_id, email) VALUES (1, 'julia@mail.com')"
    cursor.execute(stmt).fetchall()
    stmt = 'SELECT email FROM contact'
    email = cursor.execute(stmt).fetchall()
    print("the email is ", email)
    yield email '''