/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver");
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n----------------------------------------------\n" +
         "              User Interface      	               \n" +
         "----------------------------------------------\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

 public static String getInput(String s) throws Exception {
      System.out.print(s);
      return in.readLine();
   }
   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB 
     String fName;

     do{
           try {
              fName = getInput("Customer First Name: ");
              if(fName.length() == 0){
                 throw new RuntimeException("Name cannot be empty!");
              }	
              break;
           }catch (Exception e){
              System.out.println(e);
              continue;
           }
     }while(true);
     
     String lName;
     do{
           try {
              lName = getInput("Customer Last Name: ");
              if(lName.length() == 0){
                 throw new RuntimeException("Name cannot be empty!");
              }
              break;
           }catch (Exception e){
              System.out.println(e);
           continue;
        }
     }while(true);
  
     String Address;      
     do {
        try{
           Address = getInput("Customer Address: "); 
           if(Address.length() == 0){
              throw new RuntimeException("Please put your address!");
           }
           break;
        }catch (Exception e){
           System.out.println(e);
           continue;
        }
     }while(true);
        
     String phNo;   
     do{
        try{
           phNo= getInput("Customer Phone Number (1234567890): ");
              if(phNo.length() < 10 || phNo.length() > 10){
                 throw new ArithmeticException("Make sure you put in a 10 digit phone number!");
              }
              else if(!phNo.matches("[0-9]+")){
                 throw new RuntimeException("Make sure only numbers are inputted!");
              }
              break;
        }catch (Exception e){
           System.out.println(e);
           continue;
        }
     }while(true);
     String DOB;
        do{
           try{
              DOB = getInput("Customer Date of Birth Yr-Mnth-Day (e.g. 2008-11-11): "); 
              break;
           }catch(Exception e){
              System.out.print("Please Put it in the correct syntax!");
              continue;
           }
        }while(true);
     String gender;
     do{
        try{
           gender  = getInput("Customer Gender (Male, Female): ");
           break;
        }catch (Exception e){
           System.out.println(e);
           continue;
        }
     }while(true);

        try{
           String query = String.format(
           "INSERT INTO Customer(customerID, fName, lName, Address, phNo, DOB, gender)" +
           "VALUES ((SELECT COUNT(*)+1 FROM Customer),'%s','%s','%s','%s','%s','%s');",
           fName,
           lName,
           Address,
           phNo,
           DOB,
           gender
           );
           esql.executeUpdate(query);
           System.out.println(
               "\n\n----------------------------------------------\n" +
                "              Your New Customer      	               \n" +
               "----------------------------------------------\n");
           query = String.format("SELECT * FROM Customer WHERE fName='%s';", fName);
           esql.executeQuery(query);
           System.out.println(
               "\n\n----------------------------------------------\n");
        }catch (SQLException e){
           System.out.println("Please Check if it's a valid date and gender is either Male or Female.");
        }
        catch (Exception e){
           System.out.println(e);
        }

   }//end addCustomer

	  // Given room details add the room in the DB
     public static void addRoom(DBProject esql){
      // Given room details add the room in the DB
       // Your code goes here.
          String hotelID;
          do{
             try{
                hotelID  = getInput("Enter hotel ID: ");
                if(hotelID.length() < 1){
                   throw new RuntimeException("Cannot be Null");
                }
                break;
             }catch(Exception e){
                System.out.println(e);
                continue;
             }
          }while(true);
 
          String roomNo;
          do{
             try{
                roomNo   = getInput("Enter room number: ");
                if(roomNo.length() < 1){
                   throw new RuntimeException("Cannot be Null");
                }
                break;
             }catch(Exception e){
                System.out.println(e);
                continue;
             }
          }while(true);
 
          String roomType;
          do{
             try{
                roomType = getInput("Enter room type: ");
                if(roomType.length() < 1){
                   throw new RuntimeException("Cannot be Null");
                }
                break;
             }catch(Exception e){
                System.out.println(e);
                continue;
             }
          }while(true);
          try{
           String query = String.format(
           "INSERT INTO Room(hotelID, roomNo, roomType)" + 
           "VALUES(%s, %s, '%s');", hotelID, roomNo, roomType);
           esql.executeUpdate(query);
           System.out.println(
               "\n\n----------------------------------------------\n" +
                "              Your New Room      	               \n" +
               "----------------------------------------------\n");
            esql.executeQuery(String.format("SELECT * FROM Room WHERE hotelID=%s ORDER BY roomNo", hotelID));
           System.out.println(
               "\n\n----------------------------------------------\n");
           
          }catch(SQLException e){
             System.out.println("There already exists a room there!");
          }
          catch(Exception e) {
           System.out.println(e);
 
       }
 
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){//DONE
		// Given maintenance Company details add the maintenance company in the DB
		
		int compID;
		while(true) {
			System.out.print("Input Company ID: ");
			try {
				compID = Integer.parseInt(in.readLine());
				break;
			}catch(Exception e) {
				System.out.println("This compID does not exist!");
				continue;
			}
		};
		
		String compName;
		while(true) {
			System.out.print("Input Company Name: ");
			try {
				compName = in.readLine();
				if(compName.length() > 50) {
					throw new RuntimeException("Company name cannot be longer than 50 characters");
				}
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};	  
		
		String compAddress;
		while(true) {
			System.out.print("Input Company Address: ");
			try {
				compAddress = in.readLine();
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
		
		String compCertified;
		boolean isCertified;
		while(true) {
			System.out.print("Is this company certified? (y/n): ");
			try {
				compCertified = in.readLine();
				if(compCertified.equals("y") || compCertified.equals("Y")) {
					isCertified = true;
				} else if(compCertified.equals("n") || compCertified.equals("N")) {
					isCertified = false;
				} else {
					throw new RuntimeException("Please enter \'y\' or \'n\'");
				}
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
		
		String query;
		try{
			query = "INSERT INTO MaintenanceCompany (cmpID, name, address, isCertified) VALUES (" + compID + ", \'" + compName + "\', \'" + compAddress + "\', \'" + isCertified + "\');";
			esql.executeUpdate(query);
			 System.out.println(
         "\n\n----------------------------------------------\n" +
         "              Your New Maintanence Company      	               \n" +
         "----------------------------------------------\n");
         esql.executeQuery(String.format("SELECT * FROM MaintenanceCompany WHERE cmpID=%s", compID));
         System.out.println(
            "\n\n----------------------------------------------\n");

		}catch(Exception e) {
			System.err.println("Query failed: " + e.getMessage());
		}
      // ...
      // ...
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){//DONE
	  // Given repair details add repair in the DB
      // Your code goes here.
      
		int repairID;
		while(true) {
			System.out.print("Input repair ID: ");
			try {
				repairID = Integer.parseInt(in.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
	  
		int hotelID;
		while(true) {
			System.out.print("Input hotel ID: ");
			try {
				hotelID = Integer.parseInt(in.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
	  
		int roomNum;
		while(true) {
			System.out.print("Input room number: ");
			try {
				roomNum = Integer.parseInt(in.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
	  
		int maintCompany;
		while(true) {
			System.out.print("Input maintenance company ID: ");
			try {
				maintCompany = Integer.parseInt(in.readLine());
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
	  
		int yearInput;
		int monthInput;
		int dayInput;
		boolean isLeap;
	  
		while(true) {
			System.out.print("Input Repair date year: ");
			try{
				yearInput = Integer.parseInt(in.readLine());
				if(yearInput == 0) {
					throw new RuntimeException("Repair date year cannot be left blank.");
				}
				if(yearInput <= 0 || yearInput > 9999) {
					throw new RuntimeException("Please input valid year (1 - 9999).");
				}
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
	  
		// Checking for leap year.
		if(yearInput % 4 == 0) {
			if(yearInput % 100 == 0) {
				if(yearInput % 400 == 0) {
					isLeap = true;
				} else {
					isLeap = false;
				}
			} else {
				isLeap = true;
			}
		} else {
			isLeap = false;
		}

	  while(true) {
		  System.out.print("Input Repair date month: ");
		  try {
			  monthInput = Integer.parseInt(in.readLine());
			  if(monthInput == 0) {
				  throw new RuntimeException("Repair date month cannot be left blank.");
			  }
			  if(monthInput < 0 || monthInput > 12) {
				  throw new RuntimeException("Please input valid month (1 - 12).");
			  }
			  break;
		  } catch (Exception e) {
			  System.out.println("Your input is invalid!");
			  continue;
		  }
	  };
	  while(true) {
		  System.out.print("Input Repair date day: ");
		  try {
			  dayInput = Integer.parseInt(in.readLine());
			  if(dayInput == 0) {
				  throw new RuntimeException("Repair date day cannot be left blank.");
			  }
			  if(monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 7 || monthInput == 8 || monthInput == 10 || monthInput == 12) {
				  if(dayInput <= 0 || dayInput > 31) {
					  throw new RuntimeException("Please input valid date.");
				  }
			  }
			  if(monthInput == 4 || monthInput == 6 || monthInput == 9 || monthInput == 11) {
				  if(dayInput <= 0 || dayInput > 30) {
					  throw new RuntimeException("Please input valid date.");
				  }
			  }
			  if(monthInput == 2) {
				  if(dayInput <= 0 || dayInput > 28) {
					  throw new RuntimeException("Please input valid date.");
				  }
			  }
			  break;
		  } catch (Exception e) {
			  System.out.println("Your input is invalid!");
			  continue;
		  }
	  };
   
  
    String fdate = monthInput + "/" + dayInput + "/" + yearInput;
    System.out.println("Your inputted date is: ");
    System.out.println(fdate);
    String repairDate = fdate;


		String description;
		while(true) {
			System.out.print("Input repair description: ");
			try {
				description = in.readLine();
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
		
		String repairType;
		while(true) {
			System.out.print("Input repair type: ");
			try {
				repairType = in.readLine();
				if(repairType.length() > 30) {
					throw new RuntimeException("Repair type cannot be longer than 30 characters.");
				}
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}
		};
	  
		String query;
		try {
			query = "INSERT INTO Repair (rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (" + repairID + ", \'" + hotelID + "\', \'" + roomNum + "\', \'" + maintCompany + "\', \'" + repairDate + "\', \'" + description + "\', \'" + repairType + "\');";
			esql.executeUpdate(query);
			System.out.println(
         "\n\n----------------------------------------------\n" +
         "              Your New Repair      	               \n" +
         "----------------------------------------------\n");
         query = String.format("SELECT * FROM Repair WHERE rID='%s';", repairID);
         esql.executeQuery(query);
         System.out.println(
         "\n\n----------------------------------------------\n");
		} catch (Exception e) {
			System.err.println("Query failed: " + e.getMessage());
	    }
	  // ...
      // ...
   }//end addRepair

   public static void bookRoom(DBProject esql){//DONE
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      int check = 0;
      int bID;
    	int bookingyear;
    	int bookingmonth;
    	int bookingday;
    	int noPeople;
      int hotelID;
      int roomNo;
      int customerID;
    	String query;
    	String input;
   
    	double price;
	    String temp;
    	while(true){
        	System.out.print("Please input Customer ID: ");
        	try{
          		customerID = Integer.parseInt(in.readLine());
          		break;
        	}catch (Exception e) {
          	System.out.println("Your Input is invalid!");
          	continue;
        	}
     	}

      while(true){
        System.out.print("Please input Hotel ID: ");
        try{
          hotelID = Integer.parseInt(in.readLine());
          break;
        }catch (Exception e) {
          System.out.println("Your Input is invalid!");
          continue;
        }
      }

      while(true){
        System.out.print("Please input Room Number: ");
        try{
          roomNo = Integer.parseInt(in.readLine());
          break;
        }catch (Exception e) {
          System.out.println("Your Input is invalid!");
          continue;
        }
      }

    	try{//try1
      	query = "SELECT bID\nFROM Booking\nWHERE hotelID = " + hotelID + " AND roomNo = " + roomNo + " AND customer = " + customerID + ";";
      	if(esql.executeQuery(query) == 0) {
      		while(true){
            if (check == 0) {
      			 System.out.println("Your Booking does not yet exist. Would you like to create a new Booking?(y/n): ");
             check = 1;
            }
            else {
              System.out.println("Create a new booking?(y/n): ");
            }
      			try{//try2
      				input = in.readLine();
      				if(input.equals("y") || input.equals("Y")) {
							  while(true){
								  System.out.print("Please input Booking Number: ");
								  try{//try3
									 bID = Integer.parseInt(in.readLine());
									 break;
								  }catch (Exception e) {// catch1
							      System.out.println("Your input is invalid!");
						      }
      				  }
                while(true){
                  System.out.println("Booking Date is required!");
                  System.out.print("Please input Booking Date Year: ");
                  try{
                    bookingyear = Integer.parseInt(in.readLine());
                    if(bookingyear == 0) {
                      throw new RuntimeException("Booking date year cannot be left blank.");
                    }
                    if(bookingyear < 0 || bookingyear > 9999) {
                      throw new RuntimeException("Please input valid year (1 - 9999).");
                    }
                    break;
                  }catch(Exception e) {
                    System.out.println("Your input is invalid");
                  }
                }
                while(true){
                  System.out.print("Please input Booking Date Month: ");
                  try{
                    bookingmonth = Integer.parseInt(in.readLine());
                    if(bookingmonth == 0) {
                      throw new RuntimeException("Booking date month cannot be left blank.");
                    }
                    if(bookingmonth < 0 || bookingmonth > 12) {
                      throw new RuntimeException("Please input valid month (1 - 12).");
                    }
                    break;
                  }catch(Exception e) {
                    System.out.println("Your input is invalid");
               	  }
               	}
               	while(true){
               	  System.out.print("Please input Booking Date Day: ");
                  try{
                    bookingday = Integer.parseInt(in.readLine());
                    if(bookingday == 0) {
                      throw new RuntimeException("Booking date day cannot be left blank.");
                    }
                    if(bookingmonth == 1 || bookingmonth == 3 || bookingmonth == 5 || bookingmonth == 7 || bookingmonth == 8 || bookingmonth == 10 || bookingmonth == 12) {
                      if(bookingday <= 0 || bookingday > 31) {
                        throw new RuntimeException("Please input valid date.");
                      }
                    }
                    if(bookingmonth == 4 || bookingmonth == 6 || bookingmonth == 9 || bookingmonth == 11) {
                      if(bookingday <= 0 || bookingday > 30) {
                        throw new RuntimeException("Please input valid date.");
                      }
                    }
                    if(bookingmonth == 2) {
                      if(bookingday <= 0 || bookingday > 28) {
                        throw new RuntimeException("Please input valid date.");
                      }
                    }
                    break;
                  }catch(Exception e) {
                    System.out.println("Your input is invalid");
                  }
                }

                String finaldate = bookingmonth + "/" + bookingday + "/" + bookingyear;
                System.out.println("Your inputted date is (In the format of MM/dd/yyyy ) : ");
                System.out.println(finaldate);
                String bookingDate = finaldate;
			          String tempDate = bookingDate;
                while(true){
                 	System.out.print("Please input the number of People for the Booking: ");
                 	try{
                    noPeople = Integer.parseInt(in.readLine());
                    break;
                 				}catch (Exception e) {
                   					System.out.println("Your input is invalid!");
                  				}
                			}
                			//PRICE IS DATATYPE NUMERIC(6,2) XXXXXX.XX <==== FIX
                			while(true) {
                  				System.out.print("Please input the Price: ");
                  				try{
                    				price = Double.parseDouble(in.readLine());
                    				break;
                  				}catch(Exception e) {
                    				System.out.println("Your input is invalid!");
                  				}
                			}
                			
                      try{
                  			query = "INSERT INTO Booking( bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price) VALUES (" + bID + ", \'" + customerID + "\', \'" + hotelID + "\', \'" + roomNo + "\', \'" + tempDate + "\', \'" + noPeople + "\', \'" + price + "\');";
                  			esql.executeUpdate(query);
                  			System.out.println(
                           "\n\n----------------------------------------------\n" +
                           "              Your Booking      	               \n" +
                           "----------------------------------------------\n");
                           query = String.format("SELECT * FROM Booking WHERE bID='%s';", bID);
                           esql.executeQuery(query);
                           System.out.println(
                           "\n\n----------------------------------------------\n");
                          break;
                			}catch(Exception e){
                  				System.out.println("Query failed: " + e.getMessage());
                			}
            			}
                  else{
                    return;
                  }

      			}catch(Exception e){
            			System.out.println("Your input is invalid!");
              			continue;
            		}
        	}
      	}

    	}catch(Exception e){
        System.out.println("Your input is invalid!");
    }    			
      // ...
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){//DONE
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.

	//int assignedID = 2001;

	int staffSSN;
	while(true) {
		System.out.println("Please input Staff SSN: ");
		try {
			staffSSN = Integer.parseInt(in.readLine());
			break;
		} catch (Exception e) {
			System.out.println("Your input is invalid!");
			continue;
		}
	}

	int hotelID;
	while(true) {
		System.out.println("Please input Hotel ID: ");
		try {
			hotelID = Integer.parseInt(in.readLine());
			break;
		} catch (Exception e) {
			System.out.println("Your input is invalid!");
			continue;
		}
	}

	int roomNum;
	while(true) {
		System.out.println("Please input Room number: ");
		try {
			roomNum = Integer.parseInt(in.readLine());
			break;
		} catch (Exception e) {
			System.out.println("Your input is invalid!");
			continue;
		}
	}

	String query;
		try {
			query = "INSERT INTO Assigned (asgID, staffID, hotelID, roomNo) VALUES (" + "(SELECT COUNT(*)+1 FROM Customer)" + ", " + staffSSN + ", " + hotelID + ", " + roomNum + ");";
			esql.executeUpdate(query);
			System.out.println(
         "\n\n----------------------------------------------\n" +
         "              ASSIGNED HOUSE CLEANING STAFF      	               \n" +
         "----------------------------------------------\n");
			query = String.format("SELECT * FROM Assigned WHERE staffID='%s';", staffSSN);
         esql.executeQuery(query);
         System.out.println(
         "\n\n----------------------------------------------\n");
		} catch (Exception e) {
			System.out.println("Query failed: " + e.getMessage());
		}		
      // ...
      // ...
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){//DONE
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
   int reqID;
   int staffSSN;
   int repairID;
   String description;

   //get reqID
   while(true) {
      System.out.print("Input Request ID: ");
      try {
         reqID = Integer.parseInt(in.readLine());
         break;
      }
      catch(Exception e) {
         System.out.println("Your input is invalid!");
         continue;
      }
   }
   //get SSN
   while(true) {
      System.out.print("Input manager ID: ");
      try {
         staffSSN = Integer.parseInt(in.readLine());
         break;
      }
      catch(Exception e) {
         System.out.println("Your input is invalid");
         continue;
      }
   }   
   
   while(true) {
      System.out.print("Input Repair ID: ");
      try {
         repairID = Integer.parseInt(in.readLine());
         break;
      }
      catch(Exception e) {
         System.out.println("Your input is invalid.");
         continue;
      }
   }
   int yearInput;
    int monthInput;
    int dayInput;
    boolean isLeap;
    
    while(true) {
      System.out.print("Input Repair date year: ");
      try{
        yearInput = Integer.parseInt(in.readLine());
        if(yearInput == 0) {
          throw new RuntimeException("Repair date year cannot be left blank.");
        }
        if(yearInput <= 0 || yearInput > 9999) {
          throw new RuntimeException("Please input valid year (1 - 9999).");
        }
        break;
      } catch (Exception e) {
        System.out.println("Your input is invalid!");
        continue;
      }
    };
    
    // Checking for leap year.
    if(yearInput % 4 == 0) {
      if(yearInput % 100 == 0) {
        if(yearInput % 400 == 0) {
          isLeap = true;
        } else {
          isLeap = false;
        }
      } else {
        isLeap = true;
      }
    } else {
      isLeap = false;
    }

    while(true) {
      System.out.print("Input Repair date month: ");
      try {
        monthInput = Integer.parseInt(in.readLine());
        if(monthInput == 0) {
          throw new RuntimeException("Repair date month cannot be left blank.");
        }
        if(monthInput < 0 || monthInput > 12) {
          throw new RuntimeException("Please input valid month (1 - 12).");
        }
        break;
      } catch (Exception e) {
        System.out.println("Your input is invalid!");
        continue;
      }
    };
    while(true) {
      System.out.print("Input Repair date day: ");
      try {
        dayInput = Integer.parseInt(in.readLine());
        if(dayInput == 0) {
          throw new RuntimeException("Repair date day cannot be left blank.");
        }
        if(monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 7 || monthInput == 8 || monthInput == 10 || monthInput == 12) {
          if(dayInput <= 0 || dayInput > 31) {
            throw new RuntimeException("Please input valid date.");
          }
        }
        if(monthInput == 4 || monthInput == 6 || monthInput == 9 || monthInput == 11) {
          if(dayInput <= 0 || dayInput > 30) {
            throw new RuntimeException("Please input valid date.");
          }
        }
        if(monthInput == 2) {
          if(dayInput <= 0 || dayInput > 28) {
            throw new RuntimeException("Please input valid date.");
          }
        }
        break;
      } catch (Exception e) {
        System.out.println("Your input is invalid!");
        continue;
      }
    };
        String fdate = monthInput + "/" + dayInput + "/" + yearInput;
        String requestDate =fdate;
    
    System.out.println("Your date is: ");
    System.out.println(fdate);
    
   while(true) {
      System.out.print("Input repair description: ");
      try {
         description = in.readLine();
         break;
      }
      catch(Exception e) {
         System.out.println("Your input is invalid.");
         continue;
      }
   }
   String query;
  try {
      query = "INSERT INTO Request(reqID, managerID, repairID, requestDate, description) VALUES( " + reqID + ", " + staffSSN + ", " + repairID + ",\' " + requestDate + " \', \' " + description + " \');";
      esql.executeUpdate(query);
      System.out.println(
         "\n\n----------------------------------------------\n" +
         "              Your New Repair Request      	               \n" +
         "----------------------------------------------\n");
			query = String.format("SELECT * FROM Request WHERE reqID='%s';", reqID);
         esql.executeQuery(query);
         System.out.println(
         "\n\n----------------------------------------------\n");
   }
   catch(Exception e) {
      System.out.println("Query failed. " + e.getMessage());
   }
      // ...
      // ...
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){//DONE
	  // Given a hotelID, get the count of rooms available  (NUM OF TOTAL ROOMS OF THE HOTEL - THE NUMBER ROOMS BOOKED)
      // Your code goes here.
   		int hotelID;
   		while(true){
   			System.out.println("Please input hotel ID: ");
   			try{
   				hotelID = Integer.parseInt(in.readLine());
   				break;
   			}catch(Exception e){
   				System.out.println("Your Input is invalid!");
   				continue;
   			}
   		}
;
   		String query;
   			try{

            System.out.println(
                  "\n\n----------------------------------------------\n" +
                  "              AVAILABLE ROOMS     	               \n" +
                  "----------------------------------------------\n");
               query = "SELECT COUNT(R.roomNo) AS AvailableRooms FROM Room R WHERE R.hotelID = " + hotelID + "\nEXCEPT\nSELECT COUNT(B.roomNo) FROM Booking B WHERE B.hotelID = " + hotelID + ";";
               esql.executeQuery(query);
               System.out.println(
                  "\n\n----------------------------------------------\n" );
   			}catch(Exception e) {
   				System.out.println("Query failed: " + e.getMessage());
   			}
      // ...
      // ...
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){//DONE
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
   		int hotelID;

   		while(true){
   			
   			System.out.println("Please input hotel ID: ");
   			try{
   				hotelID = Integer.parseInt(in.readLine());
   				break;
   			}catch(Exception e){
   				System.out.println("Your Input is invalid!");
   				continue;
   			}
   		}
   		String query;
   		while(true){
   			try{
               System.out.println(
                "\n\n------------------------------------------\n" +
               "               BOOKED ROOMS      	               \n" +
               "----------------------------------------------\n");
   				query = "SELECT count(B.roomNo)\n AS ReservedRooms FROM Booking B WHERE hotelID = " + hotelID + ";";
               esql.executeQuery(query);
               System.out.println(
                "\n\n------------------------------------------\n");
   				break;
   			}catch(Exception e){
   				System.out.println("Query failed: " + e.getMessage());
   			}
   		}
      // ...
      // ...
   }//end numberOfBookedRooms
   
public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      // Your code goes here.
	int hotelID;
         while(true){
                System.out.print("Please input hotel ID: ");
                try{
                        hotelID =  Integer.parseInt(in.readLine());
                        break;
                }catch(Exception e){
                        System.out.println("Your Input is invalid!");
                        continue;
                }
        }

        int yearInput;
        int monthInput;
        int dayInput;
        

                while(true) {
                        System.out.print("Input booking date year: ");
                        try{
                                yearInput = Integer.parseInt(in.readLine());
                                if(yearInput == 0) {
                                        throw new RuntimeException("Year cannot be left blank.");
                                }
                                if(yearInput <= 0 || yearInput > 9999) {
                                        throw new RuntimeException("Please input valid year (1 - 9999).");
                                }
                                break;
                        } catch (Exception e) {
                                System.out.println("Your input is invalid!");
                                continue;

                        }

                };
                while(true) {
                  System.out.print("Input booking date month: ");
                  try {

                          monthInput = Integer.parseInt(in.readLine());
                          if(monthInput == 0) {
                                  throw new RuntimeException("Month cannot be left blank.");
                          }
                          if(monthInput < 0 || monthInput > 12) {
                                  throw new RuntimeException("Please input valid month (1 - 12).");
                          }
                          break;
                  } catch (Exception e) {
                          System.out.println("Your input is invalid!");
                          continue;
                  }
          };
	
                   
	 while(true) {
                  System.out.print("Input booking date day: ");
                  try {
                          dayInput = Integer.parseInt(in.readLine());
                          if(dayInput == 0) {
                                  throw new RuntimeException("Day cannot be left blank.");
                          }
                          if(monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 7 || monthInput == 8 || monthInput == 10 || monthInput == 12) {
                                  if(dayInput <= 0 || dayInput > 31) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          if(monthInput == 4 || monthInput == 6 || monthInput == 9 || monthInput == 11) {
                                  if(dayInput <= 0 || dayInput > 30) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          if(monthInput == 2) {
                                  if(dayInput <= 0 || dayInput > 28) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          break;
                  } catch (Exception e) {
                          System.out.println("Your input is invalid!");
                          continue;
                  }
          };

        String fdate = monthInput + "/" + dayInput + "/" + yearInput;
        System.out.println("Your inputted date is: ");
        System.out.println(fdate);
        String bookingDate = fdate;
        String endDate = monthInput + "/" + (dayInput+7) + "/" + yearInput;
        String endBooking = endDate;

   	
	  String query;
                try {
                        System.out.println(
         "\n\n----------------------------------------------\n" +
         "              Bookings                       \n" +
         "----------------------------------------------\n");

         query = "SELECT R.roomNo FROM Booking B, Room R WHERE B.hotelID = " + hotelID + " AND R.hotelID = " + hotelID + "AND B.bookingDate >= \'" + bookingDate + "\' AND B.bookingDate <= \'" + endBooking + "\';";
         esql.executeQuery(query);
         System.out.println(
         "\n\n----------------------------------------------\n");
                } catch (Exception e) {
                        System.err.println("Query failed: " + e.getMessage());
            }
         
      
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
	int k;
        while(true){
                System.out.println("Please input number of prices to show: ");
                try{
                        k = Integer.parseInt(in.readLine());
                        break;
                  }catch(Exception e){
                                System.out.println("Your Input is invalid!");
                                continue;
                        }
        }

        int yearInput;
        int monthInput;
        int dayInput;
        boolean isLeap;

                while(true) {
                        System.out.print("Input start date year: ");
                        try{
                                yearInput = Integer.parseInt(in.readLine());
                                if(yearInput == 0) {
                                        throw new RuntimeException("Year cannot be left blank.");
                                }
                                if(yearInput <= 0 || yearInput > 9999) {
                                        throw new RuntimeException("Please input valid year (1 - 9999).");
                                }
                                break;
                        } catch (Exception e) {
                                System.out.println("Your input is invalid!");
                                continue;

                        }

                };

		 if(yearInput % 4 == 0) {
                        if(yearInput % 100 == 0) {
                                if(yearInput % 400 == 0) {
                                        isLeap = true;
                                } else {
                                        isLeap = false;
                                }
                        } else {
                                isLeap = true;
                        }
                } else {
                        isLeap = false;
                }

                  while(true) {
                  System.out.print("Input start date month: ");
                  try {

                          monthInput = Integer.parseInt(in.readLine());
                          if(monthInput == 0) {
                                   throw new RuntimeException("Month cannot be left blank.");
                          }
                          if(monthInput < 0 || monthInput > 12) {
                                  throw new RuntimeException("Please input valid month (1 - 12).");
                          }
                          break;
                  } catch (Exception e) {
                          System.out.println("Your input is invalid!");
                          continue;
                  }
          };
	
	 while(true) {
                  System.out.print("Input start date day: ");
                  try {
                          dayInput = Integer.parseInt(in.readLine());
                          if(dayInput == 0) {
                                  throw new RuntimeException("Day cannot be left blank.");
                          }
                          if(monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 7 || monthInput == 8 || monthInput == 10 || monthInput == 12) {
                                  if(dayInput <= 0 || dayInput > 31) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          if(monthInput == 4 || monthInput == 6 || monthInput == 9 || monthInput == 11) {
                                  if(dayInput <= 0 || dayInput > 30) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          if(monthInput == 2) {
                                  if(dayInput <= 0 || dayInput > 28) {
                                          throw new RuntimeException("Please input valid date.");
                                }
                        }
                        break;
                     }
                        
                        catch (Exception e) {
                                System.out.println("Your input is invalid!");
                                continue;
                        }
          };


    String fdate = monthInput + "/" + dayInput + "/" + yearInput;
    System.out.println("Your inputted date is: ");
    System.out.println(fdate);
    String startDate = fdate;

	  while(true) {
                        System.out.print("Input end date year: ");
                        try{
                                yearInput = Integer.parseInt(in.readLine());
                                if(yearInput == 0) {
                                        throw new RuntimeException("Year cannot be left blank.");
                                }
                                if(yearInput <= 0 || yearInput > 9999) {
                                        throw new RuntimeException("Please input valid year (1 - 9999).");
                                }
                                break;
                        } catch (Exception e) {
                                System.out.println("Your input is invalid!");
                                continue;

                        }

                };

                if(yearInput % 4 == 0) {
                        if(yearInput % 100 == 0) {
                                if(yearInput % 400 == 0) {
                                        isLeap = true;
                                } else {
                                        isLeap = false;
                                }
                        } else {
                                isLeap = true;
                        }
                } else {
                        isLeap = false;
                }

	 while(true) {
                  System.out.print("Input end date month: ");
                  try {

                          monthInput = Integer.parseInt(in.readLine());
                          if(monthInput == 0) {
                                   throw new RuntimeException("Month cannot be left blank.");
                          }
                          if(monthInput < 0 || monthInput > 12) {
                                  throw new RuntimeException("Please input valid month (1 - 12).");
                          }
                          break;
                  } catch (Exception e) {
                          System.out.println("Your input is invalid!");
                         continue;
                  }
          };

          while(true) {
                  System.out.print("Input end date day: ");
                  try {
                          dayInput = Integer.parseInt(in.readLine());
                          if(dayInput == 0) {
                                  throw new RuntimeException("Day cannot be left blank.");
                          }
                          if(monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 7 || monthInput == 8 || monthInput == 10 || monthInput == 12) {
                                  if(dayInput <= 0 || dayInput > 31) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          if(monthInput == 4 || monthInput == 6 || monthInput == 9 || monthInput == 11) {
                                  if(dayInput <= 0 || dayInput > 30) {
                                          throw new RuntimeException("Please input valid date.");
                                  }
                          }
                          if(monthInput == 2) {
                                  if(dayInput <= 0 || dayInput > 28) {
                                          throw new RuntimeException("Please input valid date.");
                                }
                        }
                        break;
                     }
                     
                        catch (Exception e) {
                                System.out.println("Your input is invalid!");
                                continue;
                        }
          };


    String finaldate = monthInput + "/" + dayInput + "/" + yearInput;
    System.out.println("Your inputted end date is: ");
    System.out.println(finaldate);
    String endDate = finaldate;


            String query;
                try {
                        System.out.println(
         "\n\n----------------------------------------------\n" +
         "              Highest Prices                       \n" +
         "----------------------------------------------\n");

         query = "SELECT * FROM Room R, Booking B WHERE B.bookingDate >= \'" + startDate + "\' AND B.bookingDate <= \'" + endDate + "\' ORDER BY B.price DESC LIMIT " + k + ";";
         esql.executeQuery(query);
         System.out.println(
         "\n\n----------------------------------------------\n");
                } catch (Exception e) {
                        System.err.println("Query failed: " + e.getMessage());
            }
      
         
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer ID, List Top K highest booking price for a customer 
      // Your code goes here.
       int customerID;
        while(true){
                System.out.println("Please input customer ID: ");
                try{
                        customerID = Integer.parseInt(in.readLine());
                        break;
                        }catch(Exception e){
                                System.out.println("Your Input is invalid!");
                                continue;
                        }
        }

        int k;
        while(true){
                System.out.println("Please input number of prices to show: ");
                try{
                        k = Integer.parseInt(in.readLine());
                        break;
                        }catch(Exception e){
                                System.out.println("Your Input is invalid!");
                                continue;
                        }
        }


        String query;
                try {
                        System.out.println(
         "\n\n----------------------------------------------\n" +
         "        Customer's Highest Booking Prices                       \n" +
         "----------------------------------------------\n");

         query = "SELECT B.price FROM Booking B, Customer C WHERE C.customerID = " + customerID + " ORDER BY price DESC LIMIT " + k + ";"; 
         esql.executeQuery(query);
         System.out.println(
         "\n\n----------------------------------------------\n");
                } catch (Exception e) {
                        System.err.println("Query failed: " + e.getMessage());
            }


   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){//DONE
	  // Given a hotelID, customerID and date range get the total cost incurred by the customer
    //PRICE PER DAY = ROOM COST PER DAY
    int hotelID;
    int customerID;
    while(true){
       System.out.println("Please input hotel ID: ");
       try{
          hotelID = Integer.parseInt(in.readLine());
          break;
       }catch(Exception e){
          System.out.println("Your Input is invalid!");
          continue;
       }
    }
   while(true){
    System.out.println("Please input customer ID: ");
    try{
       customerID = Integer.parseInt(in.readLine());
       break;
    }catch(Exception e){
       System.out.println("Your Input is invalid!");
       continue;
    }
   }
   int inputmonth;
	int inputday;
	int inputyear;
	while(true){
            System.out.print("Please input Starting Date Year: ");
            try{
                inputyear = Integer.parseInt(in.readLine());
                if(inputyear == 0) {
                    throw new RuntimeException("Starting date year cannot be left blank.");
                }
                if(inputyear < 0 || inputyear > 9999) {
                    throw new RuntimeException("Please input valid year (1 - 9999).");
                }
                break;
            }catch(Exception e) {
                System.out.println("Your input is invalid");
            }
        }
        while(true){
            System.out.print("Please input starting Date Month: ");
            try{
                inputmonth = Integer.parseInt(in.readLine());
                if(inputmonth == 0) {
                    throw new RuntimeException("starting date month cannot be left blank.");
                }
                if(inputmonth < 0 || inputmonth > 12) {
                    throw new RuntimeException("Please input valid month (1 - 12).");
                }
                break;
            }catch(Exception e) {
                System.out.println("Your input is invalid");
            }
        }
        while(true){
            System.out.print("Please input starting Date Day: ");
            try{
                inputday = Integer.parseInt(in.readLine());
                if(inputday == 0) {
                    throw new RuntimeException("starting date day cannot be left blank.");
                }
                if(inputmonth == 1 || inputmonth == 3 || inputmonth == 5 || inputmonth == 7 || inputmonth == 8 || inputmonth == 10 || inputmonth == 12) {
                    if(inputday <= 0 || inputday > 31) {
                        throw new RuntimeException("Please input valid date.");
                    }
                }
                if(inputmonth == 4 || inputmonth == 6 || inputmonth == 9 || inputmonth == 11) {
                    if(inputday <= 0 || inputday > 30) {
                        throw new RuntimeException("Please input valid date.");
                    }
                }
                if(inputmonth == 2) {
                    if(inputday <= 0 || inputday > 28) {
                        throw new RuntimeException("Please input valid date.");
                    }
                }
                break;
            }catch(Exception e) {
                System.out.println("Your input is invalid");
            }
        }

        String finaldate = inputmonth + "/" + inputday + "/" + inputyear;
        System.out.println("Your inputted date is (In the format of MM/dd/yyyy ) : ");
        System.out.println(finaldate);
        String startDate = finaldate;

	while(true){
	   
            System.out.print("Please input End Date Year: ");
            try{
                inputyear = Integer.parseInt(in.readLine());
                if(inputyear == 0) {
                    throw new RuntimeException("End date year cannot be left blank.");
                }
                if(inputyear < 0 || inputyear > 9999) {
                    throw new RuntimeException("Please input valid year (1 - 9999).");
                }
                break;
            }catch(Exception e) {
                System.out.println("Your input is invalid");
            }
        }
        while(true){
            System.out.print("Please input End Date Month: ");
            try{
                inputmonth = Integer.parseInt(in.readLine());
                if(inputmonth == 0) {
                    throw new RuntimeException("End date month cannot be left blank.");
                }
                if(inputmonth < 0 || inputmonth > 12) {
                    throw new RuntimeException("Please input valid month (1 - 12).");
                }
                break;
            }catch(Exception e) {
                System.out.println("Your input is invalid");
            }
        }
        while(true){
            System.out.print("Please input End Date Day: ");
            try{
                inputday = Integer.parseInt(in.readLine());
                if(inputday == 0) {
                    throw new RuntimeException("End date day cannot be left blank.");
                }
                if(inputmonth == 1 || inputmonth == 3 || inputmonth == 5 || inputmonth == 7 || inputmonth == 8 || inputmonth == 10 || inputmonth == 12) {
                    if(inputday <= 0 || inputday > 31) {
                        throw new RuntimeException("Please input valid date.");
                    }
                }
                if(inputmonth == 4 || inputmonth == 6 || inputmonth == 9 || inputmonth == 11) {
                    if(inputday <= 0 || inputday > 30) {
                        throw new RuntimeException("Please input valid date.");
                    }
                }
                if(inputmonth == 2) {
                    if(inputday <= 0 || inputday > 28) {
                        throw new RuntimeException("Please input valid date.");
                    }
                }
                break;
            }catch(Exception e) {
                System.out.println("Your input is invalid");
            }
        }

        String finishdate = inputmonth + "/" + inputday + "/" + inputyear;
        System.out.println("Your inputted date is (In the format of MM/dd/yyyy ) : ");
        System.out.println(finishdate);
        String endDate = finishdate;



	String query;
		try{
         System.out.println(
      "\n\n-------------------------------------\n" +
      "              Customer Total                           \n" +
      "------------------------------------------\n");
			query = "SELECT C.customerID, SUM(B.price) FROM Customer C, Booking B WHERE B.hotelID = " + hotelID + " AND C.customerID = " + customerID +  " AND B.bookingDate >= \'" + startDate + "\' AND B.bookingDate <= \'" + endDate + "\' GROUP BY C.customerID;";
         esql.executeQuery(query);
         System.out.println(
            "\n\n-------------------------------------\n");
		}catch(Exception e){
			System.out.println("Query failed. " + e.getMessage());
		}


   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){//DONE
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
  	String name;
	String query;
	while (true){
		System.out.println("Please enter a company: ");
     	try {
	    name = in.readLine();
       	     break;
    	 } catch (Exception e) {
      	  System.out.println("Your input is invalid!");
          continue;
     }
   }

	
try{
	System.out.println(
      "\n\n-------------------------------------\n" +
      "              REPAIRS Made                             \n" +
      "------------------------------------------\n");
      query = "SELECT M.name, R.rID, R.repairType, R.hotelID, R.roomNo FROM Repair R, MaintenanceCompany M WHERE M.cmpID = " + name + " AND M.cmpID = R.mCompany;";
     esql.executeQuery(query);
     System.out.println(
         "\n\n-------------------------------------\n");
  } catch (Exception e) {
     System.err.println(e.getMessage());
  }

}//end listRepairsMade 

   
   public static void topKMaintenanceCompany(DBProject esql){//DONE
	  // List Top K Maintenance Company Names based on total repair count (descending order)
  String k;
  String query;
  while(true) {
     System.out.println("Please enter a range: ");
     try {
        k = in.readLine();
        break;
     } catch (Exception e) {
        System.out.println("Your input is invalid!");
        continue;
     }
  }
  
  
  try {
   System.out.println(
      "\n\n-------------------------------------\n" +
      "              FIND REPAIRS PER YEAR      	               \n" +
      "------------------------------------------\n");
     query = "SELECT M.name, COUNT(DISTINCT R.rID)\nFROM MaintenanceCompany M, Repair R WHERE M.cmpID = R.mCompany GROUP BY M.name ORDER BY COUNT(DISTINCT R.rID) DESC LIMIT " + k + ";";
     esql.executeQuery(query);
     System.out.println(
         "\n\n-------------------------------------\n");
  } catch (Exception e) {
     System.err.println(e.getMessage());
  }
      
	 
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
   int hotelID;
   int roomNum;
   while(true) {
     System.out.println("Please enter a hotel ID: ");
     try {
        hotelID = Integer.parseInt(in.readLine());
        break;
     } catch (Exception e) {
        System.out.println("Your input is invalid!");
     }
  }
  
  
  while(true) {
     System.out.println("Please enter a room number: ");
     try {
        roomNum = Integer.parseInt(in.readLine());
        break;
     } catch (Exception e) {
        System.out.println("Your input is invalid!");
     }
  }
  
   
   String query;
   try {
      System.out.println(
      "\n\n-------------------------------------\n" +
      "              FIND REPAIRS PER YEAR      	               \n" +
      "------------------------------------------\n");
      query = "SELECT EXTRACT (YEAR FROM R.repairDate), COUNT(R.repairType) FROM Repair R WHERE R.roomNo = " + roomNum + " AND R.hotelID = " + hotelID + " GROUP BY EXTRACT(YEAR FROM R.repairDate) ORDER BY EXTRACT(YEAR FROM R.repairDate) DESC";
      esql.executeQuery(query);
      System.out.println(
         "\n\n-------------------------------------\n");
  } catch (Exception e) {
     System.err.println(e.getMessage());
  }
   }//end listRepairsMade

}//end DBProject
