#!/bin/sh


keytool -keystore $1 -storepass "$2" -list -v
