# Image

이미지 생성과 관련된 API

---

## 이미지_생성

외부 API에 필요한 필드를 전달하여 생성된 이미지 리스트를 S3에 저장하고 반환합니다. <br>
반환되는 이미지는 1개 이상일 수 있습니다.

### Request

```
POST /api/images
```

```http
Authorization: Bearer {access_token}
```

### Request Body

[KAKAO Karlo API](https://developers.kakao.com/docs/latest/ko/karlo/rest-api#text-to-image)

| 필드                    | 타입          | 필수 여부 | 설명                                                                                          |
|-----------------------|-------------|-------|---------------------------------------------------------------------------------------------|
| `version`             | `String`    | O     | 사용하는 karlo 모델 버전                                                                            |
| `prompt`              | `String`    | O     | 이미지 생성에 사용되는 프롬프트(영어 사용 권장)                                                                 |
| `negative_prompt`     | `String`    | X     | 이미지 생성에 사용되는 부정 프롬프트.(영어 사용 권장)                                                             |
| `width`               | `Integer`   | O     | 이미지 pixel 너비                                                                                |
| `height`              | `Integer`   | O     | 이미지 pixel 높이                                                                                |
| `seed`                | `Integer[]` | X     | 각 이미지 생성 작업에 사용할 시드(Seed) 값 <br/>생성할 이미지 수와 같은 길이의 배열이어야 함<br/>0 이상 4,294,967,295 이하 숫자로 구성 |
| `num_inference_steps` | `Integer`   | X     | 이미지 품질 (기본값: 50, 최소: 10, 최대: 100)                                                           |
| `guidance_scale`      | `Double`    | X     | 프롬프트와 얼마나 가까운 결과를 도출하는지 설정하는 값 <br/>(기본값: 5.0, 최소: 1.0, 최대: 20.0)                           |
| `samples`             | `Integer`   | X     | 이미지 생성 개수                                                                                   |

### Response

배열로 반환됩니다.

| 필드      | 타입        | 설명            |
|---------|-----------|---------------|
| `id`    | `String`  | 이미지 ID        |
| `seed`  | `Integer` | 이미지 형성 seed 값 |
| `image` | `String`  | 이미지 URL       |

```json
 [
  {
    "id": "a2FybG8tdHJpdG9uLXY1..",
    "seed": 2356817040,
    "image": "https://s3.ap-northeast-2.amazonaws.com/.."
  }
]
```

---
