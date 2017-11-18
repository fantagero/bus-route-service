# bus-route-service  
# Simple service to check if direct route exist 
# between 2 stations in any of provided routs

# Command to build out application .jar file
./gradlew clean build 

# Command to run application
java -jar build/libs/bus-route-service-0.1.0.jar --routes.file={provide_path_to_initial_routs_data}

# Command packages application into a docker image
./gradlew build buildDocker

# Command runs application using a docker image
docker run -p 8088:8088 -t goeuro/bus-route-service --routes.file={provide_path_to_initial_routs_data}
