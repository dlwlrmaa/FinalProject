CREATE INDEX inxcustomer_customerid ON Customer USING BTREE(customerID);
CREATE INDEX inxcustomer_fname ON Customer USING BTREE(fname);
CREATE INDEX inxcustomer_lname ON Customer USING BTREE(lname);
CREATE INDEX inxcustomer_address ON Customer USING BTREE(address);
CREATE INDEX inxcustomer_phNo ON Customer USING BTREE(phNo);
CREATE INDEX inxcustomer_DOB ON Customer USING BTREE(DOB);
CREATE INDEX inxcustomer_gender ON Customer USING BTREE(gender);

CREATE INDEX inxstaff_SSN ON Staff USING BTREE(SSN);
CREATE INDEX inxstaff_fname ON Staff USING BTREE(fname);
CREATE INDEX inxstaff_lname ON Staff USING BTREE(lname);
CREATE INDEX inxstaff_address ON Staff USING BTREE(address);
CREATE INDEX inxstaff_role ON Staff USING BTREE(role);
CREATE INDEX inxstaff_employerID ON Staff USING BTREE(employerID);

CREATE INDEX inxroom_hotelid ON Room USING BTREE(hotelID);
CREATE INDEX inxroom_roomNo ON Room USING BTREE(roomNo);
CREATE INDEX inxroom_roomType ON Room USING BTREE(roomType);

CREATE INDEX inxmaintenancecompany_cmpid ON MaintenanceCompany USING BTREE(cmpID);
CREATE INDEX inxmaintenancecompany_name ON MaintenanceCompany USING BTREE(name);
CREATE INDEX inxmaintenancecompany_address ON MaintenanceCompany USING BTREE(address);
CREATE INDEX inxmaintenancecompany_iscertified ON MaintenanceCompany USING BTREE(iscertified);

CREATE INDEX inxbooking_bid ON Booking USING BTREE(bID);
CREATE INDEX inxbooking_customer ON Booking USING BTREE(customer);
CREATE INDEX inxbooking_hotelid ON Booking USING BTREE(hotelID);
CREATE INDEX inxbooking_roomno ON Booking USING BTREE(roomNo);
CREATE INDEX inxbooking_bookingdate ON Booking USING BTREE(bookingDate);
CREATE INDEX inxbooking_noofpeople ON Booking USING BTREE(noOfPeople);
CREATE INDEX inxbooking_price ON Booking USING BTREE(price);

CREATE INDEX inxhotel_hotelid ON Hotel USING BTREE(hotelID);
CREATE INDEX inxhotel_address ON Hotel USING BTREE(address);
CREATE INDEX inxhotel_manager ON Hotel USING BTREE(manager);
