#
# Creates the Halton Energy database
# Run with mysql -u [user] -p -vvv < halton_database.sql
#

DROP DATABASE IF EXISTS halton_energy; # Uncomment to replace existing databases (dangerous)
CREATE DATABASE halton_energy;
USE halton_energy;

CREATE TABLE statistic (
    statistic_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    generated INTEGER UNSIGNED NOT NULL,
    used INTEGER UNSIGNED,
    start DATE NOT NULL,
    end DATE NOT NULL,
    PRIMARY KEY(statistic_id)
);

CREATE TABLE appliance (
    appliance_id VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(256) NOT NULL,
    model VARCHAR(100),
    size VARCHAR(40),
    annualconsumption VARCHAR(150),
    imageurl VARCHAR(250),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    PRIMARY KEY(appliance_id)
);

CREATE TABLE uses (
    use_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    appliance_id VARCHAR(255),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(256) NOT NULL,
    used INTEGER UNSIGNED NOT NULL,
    PRIMARY KEY(use_id),
    FOREIGN KEY(appliance_id) REFERENCES appliance(appliance_id)
);
