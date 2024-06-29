# Member

회원과 관련된 API

---

## 회원가입

모든 서비스를 이용하기 위해 회원가입을 진행해야 합니다.

### Request

```
POST /api/member/register
```

### Request Body

| 필드         | 타입       | 필수 여부 | 설명                |
|------------|----------|-------|-------------------|
| `username` | `String` | O     | 회원 아이디            |
| `password` | `String` | O     | 회원 비밀번호           |
| `nickname` | `String` | O     | 회원 닉네임            |
| `email`    | `String` | O     | 회원 이메일, 이메일 형식 준수 |

### Response

    성공 : 200 OK

#### 실패

| 상태    | reason                       | 설명                        |
|-------|------------------------------|---------------------------|
| `400` | `{type : must not be blank}` | 필수 필드에 대한 입력값이 올바르지 않은 경우 |
| `500` | `아이디 중복.`                    | 아이디가 중복인 경우               |
|       | `닉네임 중복.`                    | 닉네임이 중복인 경우               |
|       | `이메일 중복.`                    | 이메일이 중복인 경우               |

---

## 중복체크

회원가입 시 중복체크를 위한 api입니다. <br>
아이디, 닉네임, 이메일을 중복체크하여 중복이면 **true**, 중복이 아니면 **false**를 반환합니다.

### Request

```
GET /api/member/check/username/{username}
GET /api/member/check/nickname/{nickname}
GET /api/member/check/email/{email}
```

### Request Query

| 파라미터                                | 타입       | 설명     |
|-------------------------------------|----------|--------|
| `username` or `nickname` or `email` | `String` | 중복체크 값 |

### Response

중복인 경우 : `true` <br>
중복이 아닌 경우 : `false`

---

## 프로필_조회

로그인한 회원의 프로필을 조회합니다. <br>

### Request

```
GET /api/member/profile
```

```http
Authorization: Bearer {access_token}
```

### Response

| 필드         | 타입       | 설명     |
|------------|----------|--------|
| `username` | `String` | 회원 아이디 |
| `nickname` | `String` | 회원 닉네임 |
| `email`    | `String` | 회원 이메일 |

```json
{
  "username": "1234",
  "nickname": "1234",
  "email": "12342@naver.com"
}
```

---

## 비밀번호_확인

비밀번호를 확인합니다. <br>
비밀번호가 일치하면 **true**, 일치하지 않으면 **false**를 반환합니다.

### Request

```
POST /api/member/confirm/password
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드         | 타입       | 필수 여부 | 설명      |
|------------|----------|-------|---------|
| `password` | `String` | O     | 회원 비밀번호 |

### Response

비밀번호가 일치하는 경우 : `true` <br>
비밀번호가 일치하지 않는 경우 : `false`

---

## 프로필_수정

로그인한 회원의 프로필을 수정합니다. <br>
수정된 필드만 구분하여 수정할 수 있습니다.

### Request

```
PUT /api/member
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드         | 타입       | 필수 여부 | 설명                |
|------------|----------|-------|-------------------|
| `password` | `String` | X     | 회원 비밀번호           |
| `nickname` | `String` | O     | 회원 닉네임            |
| `email`    | `String` | O     | 회원 이메일, 이메일 형식 준수 |

### Response

    성공 : 200 OK

#### 실패

| 상태    | reason                       | 설명                        |
|-------|------------------------------|---------------------------|
| `400` | `{type : must not be blank}` | 필수 필드에 대한 입력값이 올바르지 않은 경우 |
| `500` | `닉네임 중복.`                    | 닉네임이 중복인 경우               |
|       | `이메일 중복.`                    | 이메일이 중복인 경우               |

---

## 회원탈퇴

로그인한 회원의 회원탈퇴를 진행합니다.

### Request

```
DELETE /api/member
```

```http
Authorization: Bearer {access_token}
```

### Response

    성공 : 200 OK

---


