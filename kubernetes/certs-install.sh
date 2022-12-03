#!/bin/sh


#
# First enter ../certificates dir and run
# there all the scripts generating PEM and
# JKS files.

for i in 1 2 3
do
	cp ../certificates/kafka$i.truststore.jks ./volumes/kafka/$i/secrets
	cp ../certificates/kafka$i.keystore.jks ./volumes/kafka/$i/secrets
done
