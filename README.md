JAVA DOCUMENTATION:
https://api.mongodb.org/java/current/

INSTALL MONGODB:
(found at https://docs.mongodb.org/manual/tutorial/install-mongodb-on-os-x/)
1. brew install mongodb
2. make db folders in root (/data/db)
3. change folder owners (chmod 777 /data/db)

RUN MONGODB:
type "mongod"

TUTORIAL:
(found at http://mongodb.github.io/mongo-java-driver/2.13/getting-started/quick-tour/)

STOP MONGODB:
sudo service mongod stop (linux)
brew services stop mongodb (Mac, may need to install brew services with command "brew tap gapple/services")
ps -A | grep mongo (then kill the pid)

START MONGODB:
brew services start mongodb


