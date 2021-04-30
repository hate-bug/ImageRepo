# Image Repository 
A public endpoint that contains a CRUD repository allows Users to store, query and delete images. 

## Framework

* Spring MVC
* Junit Testing suite

## Manual 
* Endpoint: https://imagerepo1018.herokuapp.com/

* Upload a single image with access information: 
  - https://imagerepo1018.herokuapp.com/api/image/save 
  - Method: POST
  - Body: form-data 
    - file: image.png 
    - isPublic: true/false
   
* Upload bulk images (All images will be set as private by default)
  - https://imagerepo1018.herokuapp.com/api/image/saveall
  - Method: POST
  - Body: form-data
    - file: image1.png, image2.jpg ... 
    
* Provide Permission information for images based on Id 
  - https://imagerepo1018.herokuapp.com/api/image/updateimages
  - Method: PUT
  - Body: JSON 
    - [{"imageId": 1, 
        "isPublic": false}, 
        {"imageId": 3, 
        "isPublic": true} ]
 
* Register user with password 
  - https://imagerepo1018.herokuapp.com/api/user/register
  - Method: POST
  - Body: JSON
    - {"userName" : "jizhe", 
       "password" : "password"}

* User log in 
  - https://imagerepo1018.herokuapp.com/api/user/login
  - Method: POST
  - Body: JSON
    - {"username" : "jizhe", 
       "password" : "password"}
  - Response: JWT 
  

## UML Diagram: 
![image](https://user-images.githubusercontent.com/19366514/116284147-be81b980-a75a-11eb-9b02-6b3e5bd916b2.png)


## ORM Mapping: 
![image](https://user-images.githubusercontent.com/19366514/116284083-ad38ad00-a75a-11eb-9a98-463bd967b431.png)

## DataBase
SQL Relationa DB powered by H2DB and Postgres SQL Service

## DevOps Pipeline
* Travis CI (Continuous Integration)
* Heroku (Continuous Deployment)


