Google Console for setting up openid client

https://console.cloud.google.com/auth/clients?organizationId=0&project=springboot-491218

Delete oauth2 approval
https://myaccount.google.com/connections

Spring Security Settings
https://docs.spring.io/spring-security/reference/servlet/oauth2/login/index.html


---
OAuth2 Authorization Code Flow:
Syfte: ge access tokens för att komma åt resurser.

Resultat: access token (och ev. refresh token).

OpenID Connect Authorization Code Flow:
Samma flöde, men med extra krav:

scope=openid måste finnas
OP måste returnera ett ID Token
Klienten måste verifiera ID Token


---
Inloggningsflöde:
<a href="/oauth2/authorization/google">Google</a>
/oauth2/authorization/google

Steg 1: Redirect till Googles Authorization Endpoint
https://accounts.google.com/o/oauth2/v2/auth

Steg 2: Google skickar tillbaka användaren till din Redirection Endpoint
/login/oauth2/code/google

Här får din app:
code
state

Steg 3: Din server anropar Googles Token Endpoint (server to server)
POST https://oauth2.googleapis.com/token
Du får:
ID Token (identitet)
Access Token (för UserInfo)
ev. Refresh Token

Steg 4: Din server hämtar UserInfo (om scopes kräver det)
GET https://openidconnect.googleapis.com/v1/userinfo
Authorization: Bearer <access_token>
