#!/bin/sh



openssl genrsa -out rootCA.key

openssl req -key rootCA.key -new -out rootCA.csr \
 -subj "/C=PL/ST=MAZ/L=WARSAW/O=raberix/OU=it/CN=pablo53"

openssl x509 -signkey rootCA.key -in rootCA.csr -req -days 3650 -out rootCA.crt
