# Comments

댓글과 관련된 API

---

## 댓글 추가

특정 핀에 대한 댓글을 추가합니다. <br>
댓글의 depth는 최대 2단계까지 가능합니다. (0~1)<br>

### Request

```
POST /api/pins/comments
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드                  | 타입        | 필수 여부 | 설명                               |
|---------------------|-----------|-------|----------------------------------|
| `pinId`             | `String`  | O     | 핀 ID                             |
| `topLevelCommentId` | `Integer` | X     | 최상위 댓글 ID. 자신이 최상위인 경우 null      |
| `parentNickname`    | `String`  | X     | 부모 댓글의 작성자 닉네임. 자신이 최상위인 경우 null |
| `level`             | `int`     | X     | 댓글의 depth. 최대 1                  |
| `content`           | `String`  | O     | 핀 ID                             |

```json
{
  "pinId": "1",
  "topLevelCommentId": null,
  "parentNickname": null,
  "level": 0,
  "content": "최상위 부모댓글"
}
```

```json
{
  "pinId": "1",
  "topLevelCommentId": 1,
  "parentNickname": "부모댓글 작성자",
  "level": 1,
  "content": "대댓글"
}
```

### Response

    성공 : 200 OK

---

## 댓글 리스트 조회

특정 핀에 대한 모든 댓글 리스트를 조회합니다.

### Request

```
GET /api/pins/comments?pinId=1
```

### Request Query

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

배열로 반환되며, 각 댓글은 다음과 같은 필드를 가집니다.

| 필드                  | 타입              | 설명                               |
|---------------------|-----------------|----------------------------------|
| `commentId`         | `Integer`       | 댓글 ID                            |
| `pinId`             | `String`        | 핀 ID                             |
| `nickname`          | `String`        | 작성자 닉네임                          |
| `topLevelCommentId` | `Integer`       | 최상위 댓글 ID. 자신이 최상위인 경우 null      |
| `parentNickname`    | `String`        | 부모 댓글의 작성자 닉네임. 자신이 최상위인 경우 null |
| `level`             | `int`           | 댓글의 depth. 최대 1                  |
| `content`           | `String`        | 댓글 내용                            |
| `createdDate`       | `LocalDateTime` | 댓글 생성 날짜                         |
| `updatedDate`       | `LocalDateTime` | 댓글 최신 갱신 날짜                      |

```json
[
  {
    "commentId": 1,
    "pinId": "1",
    "nickname": "작성자",
    "topLevelCommentId": null,
    "parentNickname": null,
    "level": 0,
    "content": "최상위 부모댓글",
    "createdDate": "2021-01-01T00:00:00",
    "updatedDate": "2021-01-01T00:00:00"
  },
  {
    "commentId": 2,
    "pinId": "1",
    "nickname": "작성자",
    "topLevelCommentId": 1,
    "parentNickname": "부모댓글 작성자",
    "level": 1,
    "content": "대댓글",
    "createdDate": "2021-01-01T00:00:00",
    "updatedDate": "2021-01-01T00:00:00"
  }
]
```

---

## 내 댓글 리스트 조회

내가 작성한 모든 댓글 리스트를 조회합니다.

### Request

```
GET /api/pins/comments/my-comments
```

```http
Authorization: Bearer {access_token}
```

### Response

배열로 반환되며, 각 댓글은 다음과 같은 필드를 가집니다.

| 필드                  | 타입              | 설명                               |
|---------------------|-----------------|----------------------------------|
| `commentId`         | `Integer`       | 댓글 ID                            |
| `pinId`             | `String`        | 핀 ID                             |
| `nickname`          | `String`        | 작성자 닉네임                          |
| `topLevelCommentId` | `Integer`       | 최상위 댓글 ID. 자신이 최상위인 경우 null      |
| `parentNickname`    | `String`        | 부모 댓글의 작성자 닉네임. 자신이 최상위인 경우 null |
| `level`             | `int`           | 댓글의 depth. 최대 1                  |
| `content`           | `String`        | 댓글 내용                            |
| `createdDate`       | `LocalDateTime` | 댓글 생성 날짜                         |
| `updatedDate`       | `LocalDateTime` | 댓글 최신 갱신 날짜                      |

```json
[
  {
    "commentId": 1,
    "pinId": "1",
    "nickname": "작성자",
    "topLevelCommentId": null,
    "parentNickname": null,
    "level": 0,
    "content": "최상위 부모댓글",
    "createdDate": "2021-01-01T00:00:00",
    "updatedDate": "2021-01-01T00:00:00"
  },
  {
    "commentId": 2,
    "pinId": "1",
    "nickname": "작성자",
    "topLevelCommentId": 1,
    "parentNickname": "부모댓글 작성자",
    "level": 1,
    "content": "대댓글",
    "createdDate": "2021-01-01T00:00:00",
    "updatedDate": "2021-01-01T00:00:00"
  }
]
```

---

## 댓글 수정

특정 댓글을 수정합니다.

### Request

```
PUT /api/pins/comments
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드                  | 타입        | 필수 여부 | 설명                               |
|---------------------|-----------|-------|----------------------------------|
| `commentId`         | `Integer` | O     | 댓글 ID                            |
| `pinId`             | `String`  | O     | 핀 ID                             |
| `topLevelCommentId` | `Integer` | X     | 최상위 댓글 ID. 자신이 최상위인 경우 null      |
| `parentNickname`    | `String`  | X     | 부모 댓글의 작성자 닉네임. 자신이 최상위인 경우 null |
| `level`             | `int`     | X     | 댓글의 depth. 최대 1                  |
| `content`           | `String`  | O     | 댓글 내용                            |

```json
{
  "commentId": 1,
  "pinId": "1",
  "topLevelCommentId": null,
  "parentNickname": null,
  "level": 0,
  "content": "수정된 댓글"
}
```

### Response

    성공 : 200 OK

---

## 댓글 삭제

특정 댓글을 삭제합니다. <br>
댓글 작성자만 삭제할 수 있습니다. <br>
자식 댓글이 있는 경우 삭제할 수 없습니다.

### Request

```
DELETE /api/pins/comments/{commentId}
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

#### 실패

| 상태    | reason                      | 설명                    |
|-------|-----------------------------|-----------------------|
| `500` | `해당 댓글이 존재하지 않습니다.`         | commentId가 잘못된 경우     |
|       | `대댓글이 존재하는 댓글은 삭제할 수 없습니다.` | 자식 댓글이 있는 댓글을 삭제하는 경우 |

---

