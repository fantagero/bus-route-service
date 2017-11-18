FROM java:8
VOLUME /tmp
ADD bus-route-service-0.1.0.jar bus-route-service-0.1.0.jar
ADD bus_route_data_file.csv /root/bus_route_data_file.csv
RUN bash -c 'touch /bus-route-service-0.1.0.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bus-route-service-0.1.0.jar"]