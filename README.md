# Noki general App
NFC와 FCM를 통해 매장에서 주문하는 키오스크를 App으로 만든 프로젝트 입니다.
General App, Merchant App, Web Server  총 3개의 프로젝트 중 Genderal App의 Repository입니다.


## 파일구조
```
└─orderspot_general
    ├─controller
    │      ChartActivity.java
    │      HomeActivity.java
    │      JoinActivity.java
    │      LoginActivity.java
    │      MenuActivity.java
    │      MenuRecommendationActivity.java
    │      OrderActivity.java
    │      OrderWaitActivity.java
    │
    ├─domain
    │      MenuVO.java
    │      MerchantVO.java
    │      UserExpenditureHistoryVO.java
    │      UserTendencyVO.java
    │      UserVO.java
    │
    ├─service
    │  │  ChartService.java
    │  │  FirebaseMessaginService.java
    │  │  MenuRecommendationService.java
    │  │  MenuService.java
    │  │  OrderService.java
    │  │  UserService.java
    │  │
    │  └─Adepter
    │          MenuListRecyclerViewAdepter.java
    │          MenuRecommendationRecyclerViewAdepter.java
    │          OrderListRecyclerViewAdepter.java
    │
    └─util
            HttpRequest.java
            Util.java
```

## 개발환경
- Android Studio 3.5.3
	- Firebase Cloud Messaging
	- MpAndroidChart

## 프로젝트 설명
- Web Server와 Rest API 형식으로 통신합니다.
- NFC를 통해 가맹점 정보를 얻습니다.
- FCM을 사용하여 Push Message를 전송합니다.
