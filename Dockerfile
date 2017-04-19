FROM jboss/wildfly:latest
ADD target/tts-service.war /opt/jboss/wildfly/standalone/deployments