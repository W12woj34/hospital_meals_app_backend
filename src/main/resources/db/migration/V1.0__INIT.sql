CREATE TABLE Person
(
    id         int      NOT NULL AUTO_INCREMENT,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    birth_date date         NOT NULL,
    pesel      bigint      NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Employee
(
    id       int NOT NULL,
    login_id int NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Patient
(
    id              int NOT NULL,
    additional_info varchar(255),
    PRIMARY KEY (id)
);
CREATE TABLE Login
(
    id       int      NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Ward_Nurse
(
    id      int NOT NULL,
    ward_id int NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Dietitian
(
    id int NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Main_Kitchen_Dietitian
(
    id int NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Ward
(
    id   int      NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Meal
(
    id              int NOT NULL,
    additional_info varchar(255),
    `option`        int NOT NULL,
    `date`          date    NOT NULL,
    type_id         int NOT NULL,
    diet_id         int NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Diet
(
    id   int      NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Patient_Movement
(
    id int NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Dietary_Restriction
(
    id           int      NOT NULL AUTO_INCREMENT,
    restriction  varchar(255) NOT NULL,
    patient_id   int      NOT NULL,
    dietitian_id int      NOT NULL,
    status_id    int      NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Log
(
    id                 int   NOT NULL AUTO_INCREMENT,
    timestamp          timestamp NOT NULL,
    modified_entity_id int   NOT NULL,
    user_id            int   NOT NULL,
    event_id           int   NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Event
(
    id         int      NOT NULL AUTO_INCREMENT,
    event_name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Meal_Type
(
    id   int      NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE `Order`
(
    id         int   NOT NULL AUTO_INCREMENT,
    patient_id int   NOT NULL,
    nurse_id   int   NOT NULL,
    status_id  int   NOT NULL,
    timestamp  timestamp NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Order_Status
(
    id   int      NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Patient_Diet
(
    id         int NOT NULL AUTO_INCREMENT,
    start_date date    NOT NULL,
    end_date   date,
    patient_id int NOT NULL,
    diet_id    int NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Restriction_Status
(
    id   int      NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Stay
(
    Id             int NOT NULL AUTO_INCREMENT,
    patient_id     int NOT NULL,
    admission_date date    NOT NULL,
    release_date   date,
    archived       boolean  NOT NULL,
    ward_id        int NOT NULL,
    PRIMARY KEY (Id)
);
ALTER TABLE Employee
    ADD CONSTRAINT FKEmployee729552 FOREIGN KEY (id) REFERENCES Person (id);
ALTER TABLE Employee
    ADD CONSTRAINT FKEmployee494537 FOREIGN KEY (login_id) REFERENCES Login (id);
ALTER TABLE Ward_Nurse
    ADD CONSTRAINT FKWard_Nurse264118 FOREIGN KEY (id) REFERENCES Employee (id);
ALTER TABLE Patient_Movement
    ADD CONSTRAINT FKPatient_Mo21888 FOREIGN KEY (id) REFERENCES Employee (id);
ALTER TABLE Dietitian
    ADD CONSTRAINT FKDietitian137946 FOREIGN KEY (id) REFERENCES Employee (id);
ALTER TABLE Main_Kitchen_Dietitian
    ADD CONSTRAINT FKMain_Kitch828511 FOREIGN KEY (id) REFERENCES Employee (id);
ALTER TABLE Dietary_Restriction
    ADD CONSTRAINT FKDietary_Re206305 FOREIGN KEY (patient_id) REFERENCES Patient (id);
ALTER TABLE Ward_Nurse
    ADD CONSTRAINT FKWard_Nurse973987 FOREIGN KEY (ward_id) REFERENCES Ward (id);
ALTER TABLE Log
    ADD CONSTRAINT FKLog61875 FOREIGN KEY (user_id) REFERENCES Employee (id);
ALTER TABLE Log
    ADD CONSTRAINT FKLog537778 FOREIGN KEY (event_id) REFERENCES Event (id);
ALTER TABLE Meal
    ADD CONSTRAINT FKMeal268671 FOREIGN KEY (type_id) REFERENCES Meal_Type (id);
ALTER TABLE Meal
    ADD CONSTRAINT FKMeal470807 FOREIGN KEY (diet_id) REFERENCES Diet (id);
ALTER TABLE `Order`
    ADD CONSTRAINT FKOrder184724 FOREIGN KEY (status_id) REFERENCES Order_Status (id);
ALTER TABLE `Order`
    ADD CONSTRAINT FKOrder552148 FOREIGN KEY (patient_id) REFERENCES Patient (id);
ALTER TABLE `Order`
    ADD CONSTRAINT FKOrder957857 FOREIGN KEY (nurse_id) REFERENCES Ward_Nurse (id);
ALTER TABLE Meal
    ADD CONSTRAINT FKMeal854549 FOREIGN KEY (id) REFERENCES `Order` (id);
ALTER TABLE Patient_Diet
    ADD CONSTRAINT FKPatient_Di613608 FOREIGN KEY (patient_id) REFERENCES Patient (id);
ALTER TABLE Patient_Diet
    ADD CONSTRAINT FKPatient_Di469016 FOREIGN KEY (diet_id) REFERENCES Diet (id);
ALTER TABLE Dietary_Restriction
    ADD CONSTRAINT FKDietary_Re323776 FOREIGN KEY (status_id) REFERENCES Restriction_Status (id);
ALTER TABLE Dietary_Restriction
    ADD CONSTRAINT FKDietary_Re396338 FOREIGN KEY (dietitian_id) REFERENCES Dietitian (id);
ALTER TABLE Patient
    ADD CONSTRAINT FKPatient608506 FOREIGN KEY (id) REFERENCES Person (id);
ALTER TABLE Stay
    ADD CONSTRAINT FKStay418643 FOREIGN KEY (patient_id) REFERENCES Patient (id);
ALTER TABLE Stay
    ADD CONSTRAINT FKStay418839 FOREIGN KEY (ward_id) REFERENCES Ward (id);
