# Image Repository 
A public endpoint that contains a CRUD repository allows Users to store, query and delete images. 

## Framework

* Spring MVC
* User Authentication secured by Spring Security 
* User Authorization secured by JWT token
* User password are encrypted by BCryptPasswordEncoder
* Test framework: Junit Testing suite

## Manual 
* Endpoint root url: https://imagerepo1018.herokuapp.com/

* Register user with password 
  - https://imagerepo1018.herokuapp.com/api/user/register
  - Method: POST
  - Request Body: JSON that contains user credentials
    - {"userName" : "jizhe", 
       "password" : "password"}

* User log in 
  - https://imagerepo1018.herokuapp.com/api/user/login
  - Method: POST
  - Request Body: JSON that contains user credentials
    - {"username" : "jizhe", 
       "password" : "password"}
  - Response Body: JSON
    - If success:  
    - {"jwtToken" : "string_for_jwt_token", 
       "success" " "true"}
    - If failed: 
    - {Invalid Username, 
       Invalid Password}

* Upload a single image with access information: 
  - https://imagerepo1018.herokuapp.com/api/image/save 
  - Method: POST
  - Put in header: [Authorization: jwtToken]
  - Request Body: form-data 
    - file: image.png 
    - isPublic: true/false
  - Response Body: 
    - {"name": "imagename", 
      "createDate": createddate, 
      "type": "image file type", 
      "public": true/false, 
      "id": imageid}
   
* Upload bulk images (All images will be set as private by default)
  - https://imagerepo1018.herokuapp.com/api/image/saveall
  - Method: POST
  - Put in header: [Authorization: jwtToken]
  - Request Body: form-data
    - ["file": image1.png, image2.jpg ...] 
  - Response Body: 
    - [image1json, image2json]
    
    
* Provide Permission information for images based on Id, user can only change the image permission that created by himself
  - https://imagerepo1018.herokuapp.com/api/image/updateimages
  - Method: PUT
  - Put in header: [Authorization: jwtToken]
  - Request Body: JSON 
    - [{"imageId": 1, 
        "isPublic": false}, 
        {"imageId": 3, 
        "isPublic": true}]

* User Delete Images that belongs to him (access-control)
  - https://imagerepo1018.herokuapp.com/api/image/deleteimages
  - Method: DELETE 
  - Put in header: [Authorization: jwtToken]
  - Request Body: JSON Array that contains Image ID
    - [1,2,3]
    
* User Get Image by Id (Only able to see his own images or public images)
  - https://imagerepo1018.herokuapp.com/api/image/getimagebyid?id=[id]
  - Method: GET
  - Put in header: [Authorization: jwtToken]
  - Respond Body: The image byte[]

* User Get all accessible images (Created by him or public images)
  - https://imagerepo1018.herokuapp.com/api/image/getallimages
  - Method: GET
  - Put in header: [Authorization: jwtToken]
  - Response Body: JSON Array
    - {
          "Image1_Name": "url to image1", 
          "Image2_Name": "url to image2"
      }

* User get all accessible images by the file name (created by him or public images)
    - https://imagerepo1018.herokuapp.com/api/image/getimagesbyname?name=[filename]
    - Method: GET
    - Put in header: [Authorization: jwtToken]
    - Response Body: JSON Array
      - {
            "Image1_Name": "url to image1", 
            "Image2_Name": "url to image2"
        }

## UML Diagram: 
![image](https://user-images.githubusercontent.com/19366514/116284147-be81b980-a75a-11eb-9b02-6b3e5bd916b2.png)


## ORM Mapping: 
![image](https://user-images.githubusercontent.com/19366514/116284083-ad38ad00-a75a-11eb-9a98-463bd967b431.png)

## DataBase
SQL Relationa DB powered by H2 In-memory Database

## DevOps Pipeline
* Travis CI (Continuous Integration)
* Heroku (Continuous Deployment)


