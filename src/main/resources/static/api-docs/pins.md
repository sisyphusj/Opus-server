# Pins

Pin(이미지 게시글)과 관련된 API

---

## 핀_추가

새로운 핀을 추가합니다. <br>
생성한 이미지와 이미지와 관련된 정보, 작성자에 대한 정보가 포함됩니다.

### Request

```
POST /api/pins/register
```

```http
Authorization: Bearer {access_token}
```

### Request Body

| 필드               | 타입       | 필수 여부 | 설명              |
|------------------|----------|-------|-----------------|
| `imagePath`      | `String` | O     | S3에 등록된 이미지 URL |
| `prompt`         | `String` | O     | 프롬프트            |
| `negativePrompt` | `String` | X     | 부정 프롬프트         |
| `width`          | `String` | O     | 이미지 픽셀 너비       |
| `height`         | `String` | O     | 이미지 픽셀 높이       |
| `seed`           | `String` | O     | 이미지 seed 값      |

```json
{
  "imagePath": "https://s3.ap-northeast-2.amazonaws.com/...",
  "prompt": "프롬프트",
  "negativePrompt": "부정 프롬프트",
  "width": "1000",
  "height": "1000",
  "seed": 129239
}
```

### Response

    성공 : 200 OK

---

## 핀_리스트

핀 목록을 조회합니다. <br> `offset`, `amount`를 이용하여 페이징 처리를 합니다. 1개 이상의 핀을 조회할 수 있습니다.

### Request

```
GET /api/pins?offset=0&amount=4
```

### Request Query

| 파라미터     | 타입    | 설명       |
|----------|-------|----------|
| `offset` | `int` | 조회 시작 위치 |
| `amount` | `int` | 조회 개수    |

### Response

배열로 반환되며, 각 핀은 다음과 같은 필드를 가집니다.

| 필드               | 타입        | 설명              |
|------------------|-----------|-----------------|
| `pinId`          | `Integer` | 핀 ID            |
| `nickname`       | `String`  | 작성자 닉네임         |
| `imagePath`      | `String`  | S3에 등록된 이미지 URL |
| `prompt`         | `String`  | 프롬프트            |
| `negativePrompt` | `String`  | 부정 프롬프트         |
| `width`          | `String`  | 이미지 픽셀 너비       |
| `height`         | `String`  | 이미지 픽셀 높이       |
| `seed`           | `String`  | 이미지 seed 값      |

```json
[
  {
    "pinId": 1,
    "nickname": "작성자",
    "imagePath": "https://s3.ap-northeast-2.amazonaws.com/...",
    "prompt": "프롬프트",
    "negativePrompt": "부정 프롬프트",
    "width": "1000",
    "height": "1000",
    "seed": 129239
  },
  {
    "pinId": 2,
    "nickname": "작성자",
    "imagePath": "https://s3.ap-northeast-2.amazonaws.com/...",
    "prompt": "프롬프트",
    "negativePrompt": "부정 프롬프트",
    "width": "1000",
    "height": "1000",
    "seed": 129239
  }
]
```

---

## 내_핀_리스트

내가 작성한 핀 목록을 조회합니다. <br> `offset`, `amount`를 이용하여 페이징 처리를 합니다. 1개 이상의 핀을 조회할 수 있습니다.

### Request

```
GET /api/pins/my-pins?offset=0&amount=4
```

```http
Authorization: Bearer {access_token}
```

### Request Query

| 파라미터     | 타입    | 설명       |
|----------|-------|----------|
| `offset` | `int` | 조회 시작 위치 |
| `amount` | `int` | 조회 개수    |

### Response

배열로 반환되며, 각 핀은 다음과 같은 필드를 가집니다.

| 필드               | 타입        | 설명              |
|------------------|-----------|-----------------|
| `pinId`          | `Integer` | 핀 ID            |
| `nickname`       | `String`  | 작성자 닉네임         |
| `imagePath`      | `String`  | S3에 등록된 이미지 URL |
| `prompt`         | `String`  | 프롬프트            |
| `negativePrompt` | `String`  | 부정 프롬프트         |
| `width`          | `String`  | 이미지 픽셀 너비       |
| `height`         | `String`  | 이미지 픽셀 높이       |
| `seed`           | `String`  | 이미지 seed 값      |

```json
[
  {
    "pinId": 1,
    "nickname": "작성자",
    "imagePath": "https://s3.ap-northeast-2.amazonaws.com/...",
    "prompt": "프롬프트",
    "negativePrompt": "부정 프롬프트",
    "width": "1000",
    "height": "1000",
    "seed": 129239
  },
  {
    "pinId": 2,
    "nickname": "작성자",
    "imagePath": "https://s3.ap-northeast-2.amazonaws.com/...",
    "prompt": "프롬프트",
    "negativePrompt": "부정 프롬프트",
    "width": "1000",
    "height": "1000",
    "seed": 129239
  }
]
```

---

## 핀_조회

`pinId`를 통해 핀을 조회합니다

### Request

```
GET /api/pins/{pinId}
```

```http
Authorization: Bearer {access_token}
```

### Path Variables

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

| 필드               | 타입        | 설명              |
|------------------|-----------|-----------------|
| `pinId`          | `Integer` | 핀 ID            |
| `nickname`       | `String`  | 작성자 닉네임         |
| `imagePath`      | `String`  | S3에 등록된 이미지 URL |
| `prompt`         | `String`  | 프롬프트            |
| `negativePrompt` | `String`  | 부정 프롬프트         |
| `width`          | `String`  | 이미지 픽셀 너비       |
| `height`         | `String`  | 이미지 픽셀 높이       |
| `seed`           | `String`  | 이미지 seed 값      |

```json
{
  "pinId": 1,
  "nickname": "작성자",
  "imagePath": "https://s3.ap-northeast-2.amazonaws.com/...",
  "prompt": "프롬프트",
  "negativePrompt": "부정 프롬프트",
  "width": "1000",
  "height": "1000",
  "seed": 129239
}
```

---

## 핀_개수

전체 핀 개수를 반환하거나, `keyword`를 통한 검색 결과의 핀 개수를 반환합니다.

### Request

```
GET /api/pins/total?keyword={keyword}
```

### Request Query

| 파라미터      | 타입       | 필수 | 설명     |
|-----------|----------|----|--------|
| `keyword` | `String` | X  | 검색 키워드 |

### Response

성공 시, 핀의 개수(int)를 반환합니다.

    성공 :  { count }

---

## 핀_제거

`pinId`를 통해 핀을 제거합니다. <br> 작성자 본인만이 핀을 제거할 수 있습니다.

### Request

```
DELETE /api/pins/{pinId}
```

```http
Authorization: Bearer {access_token}
```

### Path Variables

| 파라미터    | 타입    | 설명   |
|---------|-------|------|
| `pinId` | `int` | 핀 ID |

### Response

    성공 : 200 OK

---