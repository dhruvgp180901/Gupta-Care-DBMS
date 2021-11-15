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

insert into payout(username,month,year,leavesAllowed,leavesTaken,overdaysWorked,netAmount,status) values ("dhruv","sep",2001,1,1,2,133,1);



CREATE TABLE IF NOT EXISTS ward
(
    wardID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    numberofrooms INT NOT NULL,
    PRIMARY KEY (wardID)
);