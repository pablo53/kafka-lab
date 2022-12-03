# Kafka Laboratory Project

## Prerequisites

1. Install:

   a. Micro-Kubernetes (microk8s)

   b. Helm.

2. Start Micro-Kubernetes by typing:

   `microk8s start`

## Laboratory

1. Befoe the first run, create the necessary certificates and private keys. Enter `/certificates` subdirectory and type

   > `./1.create-rootCA.sh`
   > `./2.create-CA.sh`
   > `./3.1.create-kafka.sh`.

   Next, install them by entering `/kubernetes` subdirectory and typing:

   > `./cert-install.sh`

2. Run the laboratory in Kubernetes ia Helm:

   > `./helm-install.sh`

3. Once You have finished Your work, stop the laboratory:

   > `./helm-uninstall.sh`

## Kafka clients

You can connect to Kafka with various clients.

1. IntelliJ IDEA plugin "Big Data Tools"

   You can use one of he following "Boostrap servers": `localhost:30092`, `localhost:30093`. `localhost:30094`.
   Since they expose Kafka server endpoints via SSL, You need to set the necessary properties - e.g.:
   ```
    ssl.enabled.protocols=TLSv1.2
    ssl.endpoint.identification.algorithm=
    ssl.protocol=TLSv1.2
    security.protocol=SSL
    ssl.truststore.location=/.../kafka-lab/certificates/kafka1.truststore.jks
    ssl.truststore.password=kafka1
   ```
   Note that, kafka #1 broker truststore is reused here. It is fine, as this contains `rootCA` and `CA` certificates. They could be necessary for SSL client to trust the connection to a Kafka broker.
