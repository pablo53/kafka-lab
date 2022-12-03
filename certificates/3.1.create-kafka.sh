#!/bin/sh


for i in 1 2 3
do

	openssl genrsa -out kafka$i.key 2048

	openssl req -key kafka$i.key -new -out kafka$i.csr \
	 -subj "/C=PL/ST=MAZ/L=WARSAW/O=raberix/OU=it/CN=kafka$i-service"

	openssl x509 -CA CA.crt -CAkey CA.key -in kafka$i.csr -req -days 3650 -CAcreateserial -out kafka$i.crt

	openssl pkcs12 -in kafka$i.crt -inkey kafka$i.key -export -password "pass:kafka$i" -name "kafka$i" -out kafka$i.p12

done


for i in 1 2 3
do

	keytool -keystore kafka$i.truststore.jks -storepass "kafka$i" -noprompt -alias rootCA -import -file rootCA.crt

	keytool -keystore kafka$i.truststore.jks -storepass "kafka$i" -import -alias CA -file CA.crt

	for j in 1 2 3
	do
		if [ $i -ne $j ]
		then
			keytool -keystore kafka$i.truststore.jks -storepass "kafka$i" -import -alias kafka$j -file kafka$j.crt
		fi
	done

	keytool -importkeystore -srckeystore kafka$i.p12 -srcstorepass "kafka$i" -destkeystore kafka$i.keystore.jks -deststorepass "kafka$i" -srcstoretype pkcs12 -alias kafka$i
	keytool -keystore kafka$i.keystore.jks -storepass "kafka$i" -noprompt -import -alias rootCA -file rootCA.crt
	keytool -keystore kafka$i.keystore.jks -storepass "kafka$i" -noprompt -import -alias CA -file CA.crt

done
