# Like

핀과 댓글의 좋아요를 관리하는 API

---

## 핀_좋아요_추가

특정 핀에 좋아요를 추가합니다.

### Request

```
POST /api/likes/pin
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드      | 타입    | 필수 여부 | 설명   |
|---------|-------|-------|------|
| `pinId` | `int` | O     | 핀 ID |

```json
{
  "pinId": 1
}
```

### Response

    성공 : 200 OK

---

## 핀_좋아요_체크_조회

사용자의 특정 핀에 대한 좋아요 여부를 확인합니다.
반환은 boolean 타입으로 이루어집니다.

### Request

```
GET /api/likes/check/pin/{pinId}
```

```http
Authorization: Bearer {access_token}
```

### Request Query

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

    좋아요가 되어있으면 : true
    좋아요가 되어있지 않으면 : false

---

## 핀_좋아요_개수_조회

특정 핀에 대한 좋아요 개수를 조회합니다.

### Request

```
GET /api/likes/pin/{pinId}
```

### Request Query

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

반환은 int 타입으로 이루어집니다.

    성공 : { count }

---

## 핀_좋아요_해제

특정 핀에 대한 좋아요를 해제합니다.

### Request

```
DELETE /api/likes/pin/{pinId}
```

```http
Authorization: Bearer {access_token}
```

### Request Query

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

    성공 : 200 OK

---

## 댓글_좋아요_추가

특정 댓글에 좋아요를 추가합니다.

### Request

```
POST /api/likes/comment
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드          | 타입    | 필수 여부 | 설명    |
|-------------|-------|-------|-------|
| `commentId` | `int` | O     | 댓글 ID |

```json
{
  "commentId": 1
}
```

### Response

    성공 : 200 OK

---

## 댓글_좋아요_체크_조회

사용자의 특정 댓글에 대한 좋아요 여부를 확인합니다.
반환은 boolean 타입으로 이루어집니다.

### Request

```
GET /api/likes/check/comment/{commentId}
```

```http
Authorization: Bearer {access_token}
```

### Request Query

| 파라미터        | 타입    | 설명    |
|-------------|-------|-------|
| `commentId` | `int` | 댓글 ID |

### Response

    좋아요가 되어있으면 : true
    좋아요가 되어있지 않으면 : false

---

## 댓글_좋아요_개수_조회

특정 댓글에 대한 좋아요 개수를 조회합니다.

### Request

```
GET /api/likes/comment/{commentId}
```

### Request Query

| 파라미터        | 타입    | 설명    |
|-------------|-------|-------|
| `commentId` | `int` | 댓글 ID |

### Response

반환은 int 타입으로 이루어집니다.

    성공 : { count }

---

## 댓글_좋아요_해제

특정 댓글에 대한 좋아요를 해제합니다.

### Request

```
DELETE /api/likes/comment/{commentId}
```

```http
Authorization: Bearer {access_token}
```

### Request Query

| 파라미터        | 타입    | 설명    |
|-------------|-------|-------|
| `commentId` | `int` | 댓글 ID |

### Response

    성공 : 200 OK

---


