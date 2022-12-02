#!/bin/sh



openssl genrsa -out CA.key 2048

openssl req -key CA.key -new -out CA.csr \
 -subj "/C=PL/ST=MAZ/L=WARSAW/O=raberix/OU=it/CN=ca"

openssl x509 -CA rootCA.crt -CAkey rootCA.key -in CA.csr -req -days 3650 -CAcreateserial -out CA.crt
