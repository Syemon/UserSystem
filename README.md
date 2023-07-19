# Description

Endpoint: ```/users/{login}``` Returns user information based on GitHub api response. 
Example response:
```
{
  "id": "583231",
  "login": "octocat",
  "name": "The Octocat",
  "type": "User",
  "avatarUrl": "https://avatars.githubusercontent.com/u/583231?v=4",
  "createdAt": "2011-01-25T18:44:36",
  "calculations": "0.00"
}
```

Field calculations means equation: ```6 / followers_count * (2 + public_repositories_count)```. 
If a user has 0 followers then it returns null value for calculations. If the application received negative numbers from client it will return an error response, because it would mean corrupted data.

# How to run locally

1. Clone project: ```git clone git@github.com:Syemon/UserSystem.git```
2. Enter project: ```cd UserSystem```
3. Build the project with maven: ```mvn clean test install```
4. Enter docker-local directory: ```cd docker-local```
5. Run project with docker-compose: ```docker-compose up --build```
6. Send request: ```curl --header "Content-Type: application/json" http://127.0.0.1:48080/users/octocat```
7. There are already prepared requests via InteliJ in ```http/get-users.http```



