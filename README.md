# accountcontrol

*by Nalan Ekici Karanfil*

**Note** This documentation is about how to run application easily.

**Prerequsities**

Install docker on your environment. [Docker Documentation](https://docs.docker.com/get-docker/)

**Features**

**1.** The user can see all customer information by using /api/customer/all end point.

**2.** The user can see only one customer information with customer id by using /api/customer/{id} end point.

**3.** The user can create new account for existing customer by using /api/account/create end point.

**4.** The user can create new transaction request for existing customer by using /api/transaction/new end point.

**How to Run**

**Step 1:** Open directory which contains docker-compose.yml file on your terminal.

**Step 2:** Write below command to build and run the application:

docker-compose up --build -d

**NOTE** If you want to delete docker images, please follow below steps

**1** Find all docker images by using below command:

docker images

**2** Delete each images by using below command:

ddocker image rm <image id>
  
**NOTE** You can find postman exported json file in the "doc" folder.
  
**NOTE** You can access swagger by using this link http://localhost:8080/swagger-ui/ in your local machine.

<sub>*development is ongoing for microservice structure...*</sub>
