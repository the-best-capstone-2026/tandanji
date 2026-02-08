# Tandanji Backend 🍽️  
Food Image Classification API Server

## 📌 Overview
**Tandanji Backend**는 음식 이미지 분류 서비스를 위한  
**중앙 API 서버(Spring Boot)**입니다.

프론트엔드(React)로부터 음식 이미지를 전달받아 검증 및 처리를 수행하고,  
AI 추론 서버(FastAPI)에 요청을 전달한 뒤  
분류 결과를 프론트엔드에 반환하는 역할을 담당합니다.

본 서버는 **비즈니스 로직, API 관리, AI 연동, 확장성**을 고려하여 설계되었습니다.

---

## 🏗️ Architecture

[ React Frontend ]
|
v
[ Spring Boot Backend (this repo) ]
|
v
[ FastAPI AI Server ]


### Backend Responsibilities
- 이미지 업로드 처리 (multipart)
- 요청 검증 및 예외 처리
- AI 추론 서버 호출
- 응답 포맷 통합
- (확장) 사용자 기록 저장, 로그 관리

---

## 🧩 Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Web
- WebClient
- Lombok
- Swagger (springdoc-openapi)
- Gradle

---

## 📂 Project Structure

com.sjcapstone.tandanji
├── global
│ ├── config # CORS, Swagger, Web 설정
│ ├── exception # 전역 예외 처리
│ └── common # 공통 응답 포맷
│
├── domain
│ └── classification
│ ├── controller
│ ├── service
│ ├── dto
│ └── repository
│
└── TandanjiApplication.java


---

## 🚀 API Specification

### POST `/api/v1/classifications`
음식 이미지 파일을 입력받아 분류 결과를 반환합니다.

#### Request
- **Content-Type**: `multipart/form-data`
- **Body**
  - `file`: image file (jpg, png)

#### Response (Example)
```json
{
  "status": "SUCCESS",
  "data": {
    "label": "pizza",
    "confidence": 0.92,
    "topK": [
      { "label": "pizza", "prob": 0.92 },
      { "label": "hamburger", "prob": 0.04 },
      { "label": "fried_rice", "prob": 0.02 }
    ]
  },
  "message": null
}
