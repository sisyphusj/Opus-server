# Auth

회원 인증과 관련된 API

---

## 로그인

아이디와 비밀번호를 이용하여 서비스에 로그인합니다.

### Request

```
POST /api/auth/login
```

### Request Body

| 필드         | 타입       | 필수 여부 | 설명      |
|------------|----------|-------|---------|
| `username` | `String` | O     | 회원 아이디  |
| `password` | `String` | O     | 회원 비밀번호 |

```json
{
  "username": "user",
  "password": "password"
}
```

### Response

#### 성공

| 필드                     | 타입       | 설명                 |
|------------------------|----------|--------------------|
| `accessToken`          | `String` | 액세스 토큰 (30분 만료기한)  |
| `refreshToken`         | `String` | 리프레시 토큰 (1주일 만료기한) |
| `accessTokenExpiresIn` | `Long`   | 액세스 토큰 만료 시간       |

```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
  "accessTokenExpiresIn": 1719581967093
}
```

#### 실패

| 상태    | reason                       | 설명                        |
|-------|------------------------------|---------------------------|
| `400` | `{type} : must not be blank` | 필수 필드에 대한 입력값이 올바르지 않은 경우 |
| `401` | `잘못된 자격증명입니다.`               | 아이디 또는 비밀번호가 올바르지 않은 경우   |

---

## 토큰 재발급

리프레시 토큰을 이용하여 만료된 액세스 토큰을 재발급합니다.

### Request

```
POST /api/auth/reissue-token
```

### Request Body

| 필드             | 타입       | 필수 여부 | 설명      |
|----------------|----------|-------|---------|
| `accessToken`  | `String` | O     | 액세스 토큰  |
| `refreshToken` | `String` | O     | 리프레시 토큰 |

```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

### Response

| 필드                     | 타입       | 설명                 |
|------------------------|----------|--------------------|
| `accessToken`          | `String` | 액세스 토큰 (30분 만료기한)  |
| `refreshToken`         | `String` | 리프레시 토큰 (1주일 만료기한) |
| `accessTokenExpiresIn` | `Long`   | 액세스 토큰 만료 시간       |

```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
  "accessTokenExpiresIn": 1719581967093
}
```

---

## 로그아웃

회원 로그아웃을 진행하며 <br>
현재 로그인한 회원의 리프레시 토큰을 만료시킵니다.

### Request

```
POST /api/auth/logout
```

### Response

    성공 : 200 OK

---


