./mvnw clean install -DskipTests=true
cd target
mv meupetlindo*.jar mpl.jar
scp mpl.jar root@191.101.18.116:/mpl/
