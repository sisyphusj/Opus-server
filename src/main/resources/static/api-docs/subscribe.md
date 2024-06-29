# Subscribe

Pin에 대한 좋아요 구독을 관리합니다. <br>
사용자는 Pin 상세정보에 접근함과 동시에 구독을 진행하고 실시간으로 좋아요 개수를 확인할 수 있습니다.

--- 

## 핀 좋아요 구독

특정 핀에 대한 좋아요 구독을 진행합니다. <br>
해당 핀에 대한 좋아요 개수를 실시간으로 확인할 수 있습니다.

### Request

```
GET /api/like-subscribe/{pinId}
```

```http
Authorization: Bearer {access_token}
```

### Request Query

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

성공 시 구독이 완료되고 다른 사용자가 해당 핀에 대한 좋아요를 추가하면 브로드캐스트 메시지를 받습니다.

    like-update : {"likeCount":2,"pinId":77}

---

## 핀 좋아요 브로드캐스트

특정 핀에 대한 좋아요 카운트를 구독한 클라이언트들에게 브로드캐스트합니다.

### Request

```
POST /api/like-subscribe/broadcast
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
  "pinId": 77
}
```

성공 시 구독이 완료되고 다른 사용자가 해당 핀에 대한 좋아요를 추가하면 <br>
다른 사용자들은 다음과 같은 브로드캐스트 메시지를 받습니다.

    like-update : {"likeCount":2,"pinId":77}

---

## 핀 좋아요 구독해제

특정 핀에 대한 좋아요 구독을 해제합니다. <br>

### Request

```
DELETE /api/like-subscribe/unsubscribe/{pinId}
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



