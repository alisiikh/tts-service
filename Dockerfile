FROM jboss/wildfly:latest

USER root

# copy mbrola to opt folder
ADD mbrola/* /opt/mbrola/

# install needed libs
RUN yum -y install glibc.i686

# set permissions for mbrola folder
RUN cd /opt/mbrola \
    && chown -R jboss . \
    && chgrp -R jboss . \
    && chmod a+x ./mbrola

# modify path to make mbrola accessible
ENV MBROLA_HOME /opt/mbrola
ENV PATH $MBROLA_HOME:$PATH

USER jboss

# copy war to deployment directory of widlfly
ADD target/tts-service.war /opt/jboss/wildfly/standalone/deployments