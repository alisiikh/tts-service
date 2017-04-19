FROM jboss/wildfly:latest
ADD target/word-webservice.war /opt/jboss/wildfly/standalone/deployments