# Backend messaging app

Backend for messaging application. 

**Requirements:** Docker, Docker Compose and Java 8+

**Run command:** `./gradlew composeUp (Linux, Mac)` or `gradlew.bat composeUp (Windows)`

## Documentation

### Create user :heavy_check_mark:
Endpoint to create user account.

HTTP REQUEST POST http://localhost:8080/v1/users

Request body example:
```javascript
{
    "nickname": "Johnny"
}
```

`curl -i -X POST http://localhost:8080/v1/users -H "Content-Type:application/json" -d "{\"nickname\": \"Johnny\"}"
`
### Send message :heavy_check_mark:
Endpoint to send message from one user to another.

HTTP REQUEST POST http://localhost:8080/v1/messages

Request body example:
```javascript
{
    "content": "Hello, user!",
    "receiverId": 1
}
```
`curl -i -X POST http://localhost:8080/v1/messages -H "Content-Type:application/json" -H "User-Id:1" -d "{\"content\": \"Hello, user!\", \"receiverId\": 2}"`

### List messages :heavy_check_mark:
Endpoint to list messages of authenticated user.

HTTP REQUEST GET http://localhost:8080/v1/messages

#### Query parameters
senderId

| Name        | Available values|
| :----------:|:---------------:|
| senderId    | _any user id_   |
| status      | sent, received  |

`curl -i -X GET http://localhost:8080/v1/messages?status=received -H "User-Id:1"`

`curl -i -X GET http://localhost:8080/v1/messages?status=sent -H "User-Id:1"`

`curl -i -X GET http://localhost:8080/v1/messages?status=sent&senderId=2 -H "User-Id:1"`

### Integration tests :soon:
