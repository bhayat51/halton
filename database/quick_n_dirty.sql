
LOAD DATA LOCAL INFILE "C:/Users/Joshua/Documents/GitHub/halton/database/dummy/appliance.txt" REPLACE INTO TABLE halton_energy.appliance FIELDS ESCAPED BY "^";
LOAD DATA LOCAL INFILE "C:/Users/Joshua/Documents/GitHub/halton/database/dummy/statistic.txt" REPLACE INTO TABLE halton_energy.statistic FIELDS ESCAPED BY "^";
LOAD DATA LOCAL INFILE "C:/Users/Joshua/Documents/GitHub/halton/database/dummy/uses.txt" REPLACE INTO TABLE halton_energy.uses FIELDS ESCAPED BY "^";