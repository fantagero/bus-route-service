# bus-route-service  
# Simple service to check if direct route exist 
# between 2 stations in any of provided routs

# Command to build out application .jar file
./service.sh dev_build

# Command to run application
./service.sh dev_run {provide_path_to_initial_routs_data}

# Command packages application into a docker image
./service.sh docker_build {provide_path_to_initial_routs_data}

# Command runs application using a docker image
./service.sh docker_run
