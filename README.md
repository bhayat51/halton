Halton Village Energy Project
======
An energy monitoring website for the co-housed eco-village in Halton, Lancashire.

Backend
------
[![Build Status](http://prenderj.co.uk:8080/buildStatus/icon?job=halton)](http://prenderj.co.uk:8080/job/halton/)

The backend is a RESTful API that serves information from the database. The available operations are as follows:  

*GET /appliances/all* Download all appliance details  
*GET /appliances/[appliance_id]* Download details for a single appliance  

*GET /usages/all/[appliance_id]* Download all usages for an appliance  
*GET /usages/single/[use_id]* Download details for a single usage  

*GET /statistics/[start]/[end]* Download statistics for the date range start (inclusive) to end (exclusive). Date format is dd/MM/yyyy

Website
------
The frontend uses several technologies. The most notable are PHP, Javascript and Bootstrap. It is a prototype, so please excuse the visuals.

Database
------
A simple MySQL setup. There are scripts available to setup the database from scratch without much aggro.

Setup guide
======
You need the following:
- a MySQL server

- a HTTP server (with PHP)

- Java 7 and Maven  



1. Create the database using halton_database.sql.

2. (Optional) Populate the database with the provided dummy data using populate.sh. It will likely need some filepath adjustments to work on your system.

3. Move the contents of the website folder into your HTTP server's root directory (/var/www/html/ for Apache).

4. [Download the backend jar.](http://prenderj.co.uk:8080/job/halton/lastSuccessfulBuild/artifact/backend/target/haltonbackend-0.0.1-SNAPSHOT.jar)

5. You'll need to download the dependencies (of which there are many) to run the backend. Assuming your JAR is located in "~/halton_backend/", run the following commands:  

        wget https://raw.github.com/prenderj/halton/master/backend/pom.xml
        mvn org.apache.maven.plugins:maven-dependency-plugin:2.8:copy-dependencies
        mkdir /halton_backend/lib
        cp -a target/dependency/. /halton_backend/lib
        (Optional) rm -rf target

6. Download and edit db.properties and logj4.properties. Place these in the same folder as your JAR.

7. Run the backend:

        java -jar [filename] &

8. Start MySQL and your HTTP server if you haven't already. Example:

        service mysql start
        service httpd start

Success!
