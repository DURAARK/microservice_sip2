# microservice_sip2
The new version of the SIP generator

(The following text will be made more precise).

You need java 8 and sqlite3 installed.

Here is how to get java 8 on Ubuntu 14.04 LTS: http://ubuntuhandbook.org/index.php/2015/01/install-openjdk-8-ubuntu-14-04-12-04-lts/

Go to the folder brilliantly located at ../run/run .

First set up the sqlite database using the initiate_sqlitedb.sql file.

Then run this from the terminal " java -jar PrototypeY2.jar Rosetta-SIP"

This will do the SIP generation of the content placed in the "content" folder.

Currently you need java version "51" (internal name) for it to work. #willfix.
