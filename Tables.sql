CREATE TABLE IF NOT EXISTS user
(
    userID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    photo VARCHAR(255),
    birthDate Date ,
    gender VARCHAR(255) ,
    adhaarNumber VARCHAR(255) ,
    emailID VARCHAR(255) ,
    firstName VARCHAR(255) ,
    middleName VARCHAR(255) ,
    lastName VARCHAR(255) ,
    street VARCHAR(255),
    city VARCHAR(255) ,
    state VARCHAR(255) ,
    country VARCHAR(255),
    phone VARCHAR(255) ,
    PRIMARY KEY (userID)
);

CREATE TABLE IF NOT EXISTS appointment
(
    appointmentID INT NOT NULL AUTO_INCREMENT,
    patientName VARCHAR(255) NOT NULL,
    doctorName VARCHAR(255) NOT NULL,
    currdate VARCHAR(255) NOT NULL,
    bookDate Date NOT NULL,
    bookStime VARCHAR(255) NOT NULL,
    bookEtime VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (appointmentID),
    FOREIGN KEY (patientName) REFERENCES user(username),
    FOREIGN KEY (doctorName) REFERENCES user(username)
);

CREATE TABLE IF NOT EXISTS payment
(
    paymentID INT NOT NULL AUTO_INCREMENT,
    purpose VARCHAR(255) NOT NULL,
    purposeID INT NOT NULL,
    amount INT NOT NULL,
    payDate VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (paymentID)
);

CREATE TABLE IF NOT EXISTS payorderMed
(
    payorderMedID INT NOT NULL AUTO_INCREMENT,
    paymentID INT NOT NULL,
    medicineID INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (paymentID) REFERENCES payment(paymentID),
    FOREIGN KEY (medicineID) REFERENCES medicine(medicineID),
    PRIMARY KEY (payorderMedID)
);


CREATE TABLE IF NOT EXISTS test
(
    testID INT NOT NULL AUTO_INCREMENT,
    testName VARCHAR(255) UNIQUE NOT NULL,
    setupDate VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    photo VARCHAR(255),
    cost INT NOT NULL,
    PRIMARY KEY (testID)
);

CREATE TABLE IF NOT EXISTS testbooking
(
    testbookingID INT NOT NULL AUTO_INCREMENT,
    testName VARCHAR(255) NOT NULL,
    patientName VARCHAR(255) NOT NULL,
    currDate VARCHAR(255) NOT NULL,
    bookDate Date NOT NULL,
    bookTime VARCHAR(255) NOT NULL,
    diseaseDescription VARCHAR(255) NOT NULL,
    cost INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (testbookingID),
    FOREIGN KEY (patientName) REFERENCES user(username),
    FOREIGN KEY (testName) REFERENCES test(testName)
);


CREATE TABLE IF NOT EXISTS bill
(
    billID INT NOT NULL AUTO_INCREMENT,
    patientName VARCHAR(255) NOT NULL,
    doctorName VARCHAR(255) NOT NULL,
    date Date NOT NULL,
    time VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (billID),
    FOREIGN KEY (patientName) REFERENCES patient(username),
    FOREIGN KEY (doctorName) REFERENCES doctor(username)
);

CREATE TABLE IF NOT EXISTS contactUsForm
(
    queryID INT NOT NULL AUTO_INCREMENT,
    query VARCHAR(255) NOT NULL,
    emailID VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    subject varchar(255) NOT NULL,
    date varchar(255) NOT NULL,
    replyGiven VARCHAR(255),
    PRIMARY KEY (queryID)
);

insert into contactUsForm (query,emailID,phoneNumber,name,subject,date) values("check","check@gmail.com","123","checking","checkk","1212-12-12");

CREATE TABLE IF NOT EXISTS doctor
(
    doctorID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    designation VARCHAR(255) NOT NULL,
    parentName VARCHAR(255) NOT NULL,
    parentOccupation VARCHAR(255) NOT NULL,
    collegeGrad VARCHAR(255) NOT NULL,
    percentageGrad INT NOT NULL,
    collegePGrad VARCHAR(255) NOT NULL,
    percentagePGrad INT NOT NULL,
    board10th VARCHAR(255) NOT NULL,
    percentage10th INT NOT NULL,
    board12th VARCHAR(255) NOT NULL,
    percentage12th INT NOT NULL,
    appointmentCost INT NOT NULL,
    salary INT NOT NULL,
    PRIMARY KEY (doctorID),
    FOREIGN KEY (username) REFERENCES user(username)
);

CREATE TABLE IF NOT EXISTS employee
(
    employeeID INT NOT NULL AUTO_INCREMENT,
    accountNumber VARCHAR(255) NOT NULL,
    PAN VARCHAR(255) NOT NULL,
    joiningDate Date NOT NULL,
    username VARCHAR(255) NOT NULL,
    salary VARCHAR(255),
    PRIMARY KEY (employeeID),
    FOREIGN KEY (username) REFERENCES user(username)
);

CREATE TABLE IF NOT EXISTS feedback
(
    feedbackID INT NOT NULL AUTO_INCREMENT,
    purpose VARCHAR(255) NOT NULL,
    purposeID INT NOT NULL,
    rating INT NOT NULL,
    complaint VARCHAR(255),
    suggestion VARCHAR(255),
    date varchar(255) NOT NULL,
    PRIMARY KEY (feedbackID)
);

CREATE TABLE IF NOT EXISTS machine
(
    machineID INT NOT NULL AUTO_INCREMENT,
    wardID INT NOT NULL,
    roomID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    deliveredDate Date NOT NULL,
    PRIMARY KEY (machineID),
    FOREIGN KEY (wardID) REFERENCES ward(wardID),
    FOREIGN KEY (roomID) REFERENCES room(roomID)
);

CREATE TABLE IF NOT EXISTS medicine
(
    medicineID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    company varchar(255) NOT NULL,
    photo VARCHAR(255),
    color VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    cost INT NOT NULL,
    deliveredDate Date NOT NULL,
    deliveredAmount VARCHAR(255) NOT NULL,
    PRIMARY KEY (medicineID)
);

CREATE TABLE IF NOT EXISTS orderMed
(
    orderID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    medicineID INT NOT NULL,
    amount INT NOT NULL,
    date Date NOT NULL,
    cost INT NOT NULL,
    PRIMARY KEY (orderID),
    FOREIGN KEY (username) REFERENCES user(username),
    FOREIGN KEY (medicineID) REFERENCES medicine(medicineID)
);

CREATE TABLE IF NOT EXISTS patient
(
    patientID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    illness VARCHAR(255) NOT NULL,
    admitStatus VARCHAR(255) NOT NULL,
    progressStatus VARCHAR(255) NOT NULL,
    medicalrecord VARCHAR(255) NOT NULL,
    medicines VARCHAR(255) NOT NULL,
    PRIMARY KEY (patientID),
    FOREIGN KEY (username) REFERENCES user(username)
);

CREATE TABLE IF NOT EXISTS payout
(
    payoutID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    month VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    leavesAllowed INT NOT NULL,
    leavesTaken INT NOT NULL,
    overdaysWorked INT NOT NULL,
    netAmount INT NOT NULL,
    status INT NOT NULL,
    PRIMARY KEY (payoutID),
    FOREIGN KEY (username) REFERENCES doctor(username)
);

insert into payout(username,month,year,leavesAllowed,leavesTaken,overdaysWorked,netAmount,status) values ("dhruv","sep",2001,1,1,2,133,1);

CREATE TABLE IF NOT EXISTS doctorApplicant
(
    applicationID INT NOT NULL AUTO_INCREMENT,
    applicantName VARCHAR(255) NOT NULL,
    resumelink VARCHAR(255) NOT NULL,
    applicantPhoneNumber VARCHAR(255) NOT NULL,
    applicantEmailID VARCHAR(255) NOT NULL,
    applicationDate Date NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    paid VARCHAR(255) NOT NULL,
    PRIMARY KEY (applicationID)
);

CREATE TABLE IF NOT EXISTS ward
(
    wardID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    numberofrooms INT NOT NULL,
    PRIMARY KEY (wardID)
);

CREATE TABLE IF NOT EXISTS room
(
    roomID INT NOT NULL AUTO_INCREMENT,
    ward VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    numberofbeds INT NOT NULL,
    cost INT NOT NULL,
    PRIMARY KEY (roomID)
);

CREATE TABLE IF NOT EXISTS bookroom
(
    bookroomID INT NOT NULL AUTO_INCREMENT,
    roomID INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    currDate VARCHAR(255) NOT NULL,
    startDate VARCHAR(255) NOT NULL,
    endDate VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (bookroomID),
    FOREIGN KEY (username) REFERENCES user(username),
    FOREIGN KEY (roomID) REFERENCES room(roomID)
);

CREATE TABLE IF NOT EXISTS staff
(
    staffID INT NOT NULL AUTO_INCREMENT,
    work VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (staffID),
    FOREIGN KEY (username) REFERENCES employee(username)
);

CREATE TABLE IF NOT EXISTS work
(
    workID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    hospital VARCHAR(255) NOT NULL,
    workJoiningDate varchar(255) NOT NULL,
    workLeavingDate varchar(255) NOT NULL,
    workRole VARCHAR(255) NOT NULL,
    workDescription VARCHAR(255),
    PRIMARY KEY (workID),
    FOREIGN KEY (username) REFERENCES doctor(username)
);

insert into work(username,hospital,workJoiningDate,workLeavingDate,workRole,workDescription) values ("dhruv","gupta","thur","thur","doctor","qwert");

CREATE TABLE IF NOT EXISTS DoctorApplicant
(
    DoctorApplicantID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    emailID VARCHAR(255) NOT NULL,
    date varchar(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    resume VARCHAR(255),
    PRIMARY KEY (DoctorApplicantID)
);


insert into medicine(name,purpose,color,description,cost,amountleft,deliveredAmount,deliveredDate) values("Petdard","Petdard","Petdard","Petdard",100,5,
"Petdard","2022-10-20");
insert into medicine(name,purpose,color,description,cost,company,deliveredAmount,deliveredDate) values("Sirdard","Sirdard","Sirdard","Sirdard",200,"comapny",
"Sirdard","2021-11-30");



insert into doctor(username,specialization,designation,parentName,parentOccupation,collegeGrad,percentageGrad,collegePGrad,percentagePGrad,
board10th,percentage10th,board12th,percentage12th,appointmentCost) values("dhruvgp","muskan","muskan","muskan","muskan","muskan",10,"muskan",10,"muskan",10,"muskan",10,100);


