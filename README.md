# accountcontrol

*by Nalan Ekici Karanfil*

**Note** This documentation is about how to run application easily.

**Prerequsities**

Install docker on your environment.[Docker Documentation](https://docs.docker.com/get-docker/)

**How to Run**

**Step 1:** Open directory which contains docker-compose.yml file on your terminal.

**Step 2:** Write below command to build and run the application:

docker-compose up --build -d

**NOTE** If you want to delete docker images

**1** Find all docker images by using below command

docker images

**2** Delete each images by using below command

ddocker image rm <image name>
  
**NOTE** you can find postman json file in the folder.
