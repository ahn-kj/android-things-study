# Android Things - 부팅부터 LED 제어까지

이번 컨텐츠는 Android Things 를 이용해 라즈베리파이3 를 부팅해보고, LED 한개를 제어해보는 것이 목적입니다.

## 준비물

* Rassberry PI 3
* Bread Board
* Micro SDCARD 
* SDCARD Adapter(PC 에 Micro SD 카드를 인식시키기 위한 어댑터)
* 소켓점프케이블 M/F 2개
* 저항 220 옴
* LED 1개

<img src="http://postfiles4.naver.net/MjAxNzExMjVfMjc1/MDAxNTExNTY1MzcyMjg0.Q3vmIPsZGLomnmi9ISsqGm83HA1rjKYXsUfzJw8TE1sg.bTjZGkQ2o3yMBCHG9zMypCYw1n3HwSVI2Wq_yuffQ08g.PNG.akj61300/readt.png?type=w773" width="600px" />

## 라즈베리파이3 에 Android Things 이미지로 부팅해보기

라즈베리파이 3 에 Android Things 로 부팅하기 위해서는 먼저 Android Things OS 로 부팅할 수 있는 SD 카드를 만들어야 합니다. 마치 PC 부팅을 위해서는 윈도우즈를 설치하는 것과 비슷합니다.

Android Things 시스템 이미지를 다운로드 받기 위하여 Android Things 콘솔을 방문합니다.

[https://partner.android.com/things/console](https://partner.android.com/things/console)


Android Things 는 기존에는 시스템 이미지를 직접 다운로드 하는 링크가 있었지만, 현재에는 웹에서 OTA 를 지원하기 위하여 웹 콘솔 형태로 변경되었습니다.

Android Things Console 에 접속하면 다음 화면처럼 나올 것입니다. 프로덕트를 추가합니다.

<img src="http://postfiles16.naver.net/MjAxNzExMjVfMzAg/MDAxNTExNTY1OTk2MTE0.ic3iuTdY8w1gHK0Lh0DDiHgJRJm2bcGi5BcAZbltgFIg.OpuvLK67I3k-2ROPpV2h2VrV--N9_KOzTLl3r0T4dykg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_8_24_51.png?type=w773" width="400px" />

다음 팝업에서는 생성할 제품에 이름, SOM 타입 등을 설정하는 화면입니다. 제품이름에는 FirstThings, SOM Type 에는 라즈베리파이3 을 선택합니다.

<img src="http://postfiles10.naver.net/MjAxNzExMjVfMjc5/MDAxNTExNTY2MTgzMTI5.t11LScBpOPKY3NhuTgt8MaDJzqu9F2X_ZAluy5s9Pcsg.5DHirxqyI28BFqh8hp8U4EC4gl9mx1EYSn8BZR_-zhUg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_8.28.01.png?type=w773" width="400px" />

Create 버튼으로 제품을 생성하면 제품 설정화면이 나오게 됩니다. 상단 탭에서 Factory Images 탭을 선택합니다.

<img src="http://postfiles9.naver.net/MjAxNzExMjVfMjI3/MDAxNTExNTY2Mzk4Njc3.8wLnoDuXvmjkA6AmPjmDtK4PXOTWldg0UFudQ1dxbmcg.aSy0lr-BKndQxe-Em5YFLxjUuJQWdPoOiPaDcD1oflcg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_8_31_53.png?type=w773" />

Factory Images 하단에 Android Things 버전을 고르고 CREATE BUILD CONFIGURATION 을 클릭합니다.

<img src="http://postfiles10.naver.net/MjAxNzExMjVfNjYg/MDAxNTExNTY2NjYyNDE4.SN4ioKSpiT0ks4XEs9qBSDrNSe89Cq8Veul9OvvMO8Qg.bAvCdwsUflzCVjqhx5e-ajvFZgLMp-jFPdvdiOpQx8Yg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_8_34_45.png?type=w773" />

조금 시간이 걸리고 작업이 완료되면 Build configuration list 항목이 새로 생긴 것을 확인할 수 있습니다. 리스트 항목 우측에 Download Build 버튼을 클릭합니다.

<img src="http://postfiles2.naver.net/MjAxNzExMjVfMjQx/MDAxNTExNTY3MDUyMzg5.oATJ8lrknmMsAeXSg33paML0DmqyI2Go4Ec2nqDHhlAg.FAW5qAF7SSSeUBDqspcG54FHcwq0RyKA2cimj2-wN5kg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_8_43_03.png?type=w773" />

다운로드가 끝나면 이제 다운로드 받은 이미지로 SDCARD 를 구울 차례입니다. 이미지를 굽기 위해서 [Etcher](https://etcher.io/) 라는 프로그램을 추천합니다. 윈도우, OSX, 리눅스 모든 플랫폼에서 사용가능하고 사용방법이 매우 간단합니다. Etcher 을 다운로드 하기 위해 Etcher 홈페이지를 방문합니다.

[https://etcher.io/](https://etcher.io/)

Etcher 홈페이지에 방문하여 운영체제에 맞는 버전을 다운로드 합니다.

<img src="http://postfiles7.naver.net/MjAxNzExMjVfMjgx/MDAxNTExNTY3NDQ1MTQ3.F3-78_2Pe9kLTPywamOMy89685yjuGQ0ptjPnAEOjgIg.K0N13iP5eGmDGrka6f19BdCHIWNR5XD3ZL4aA6WimVUg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_8_47_55.png?type=w773" /> 

다운로드 및 설치가 완료되면 Etcher 를 실행하세요. Etcher 사용법은 간단합니다. 구울 이미지를 선택하고 어디에 구울지 선택하면 됩니다. 먼저 Select Image 버튼을 누르고 조금전에 다운로드 한 Android Things 이미지 압축파일을 선택하세요.

<img src="http://postfiles5.naver.net/MjAxNzExMjVfMzMg/MDAxNTExNTY4MTA4NjM1._VnoyNxjS2SzZoPJrXaRwbEfc_C9GoC56Ox0WdOAsPUg.te_t73Nc2weMXnhlUk2zNdRZCx2t4sHaeX8SV73JUPEg.GIF.akj61300/11%EC%9B%94-25-2017_09-01-18.gif?type=w773" width="320px" />

PC 에 micro SD 카드를 연결하세요. 필자의 경우 맥북에 micro sd 카드를 바로 인식시킬수 없어 SD 카드 어댑터를 사용했습니다. 어떤 형태로든 Micro SD 카드를 인식할수 있으면 됩니다. 

Etcher 은 외장 디스크가 하나인 경우 그것을 자동으로 선택합니다. 만약 다른 드라이브에 이미지를 구워야 한다면 change 버튼을 눌러 드라이브를 변경합니다.

<img src="http://postfiles3.naver.net/MjAxNzExMjVfMTcw/MDAxNTExNTY5Mjg2ODY4.a5u6hXcB3wrerSVdSxnz51ZxrfeVGjRPm8TQ9PAiJKUg.0NekZO19b9eZuXEfp_h5SFe9maD5SvxIN5T85CiSo0kg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_9_20_02.png?type=w773" />

이미지와 드라이브 선택이 완료되었다면 이제 Flash 버튼으로 이미지를 굽기만 하면됩니다.

<img src="http://postfiles5.naver.net/MjAxNzExMjVfMTg3/MDAxNTExNTY5MzYxNjE1.sA__8niS2XlbRBIk0mMuvjhhIUrbG_phqPMrwvFDfFQg.7vnOqCd0IX9LcMS98FXne45NRQqcjUt3S8QBGIbbI8sg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-11-25_%EC%98%A4%EC%A0%84_9_20_02.png?type=w773" />

이미지를 굽는 작업은 컴퓨터 성능에 따라 시간이 상당히 걸릴수 있습니다. 차라도 한잔 마시면서 기다리면 됩니다.

이미지를 Micro SD 카드에 굽는 과정이 완료되었다면 이제 라즈베리파이에 Micro SD 카드를 마운트하면 됩니다.

라즈베리파이를 잘살펴보면 Micro SD 카드를 마운트 할것처럼 생긴 곳이 있습니다. 보통의 경우에는 USB 포트 반대편 쪽에 뒤집은 위치에 있습니다. 보드의 적혀있는 영어를 잘살펴보면 MICRO SD CARD 라고 적혀있습니다.

<img src="http://postfiles10.naver.net/MjAxNzExMjVfMjMz/MDAxNTExNTcwOTA0ODM1.FdRRtO17DvraQW5IRCOYwWUXqrcIUSqrW-l3Y6aLmtIg.rsNxOe8hrsevET-FpabJkbQjvQssFBXhfMxqXQ7zaXwg.PNG.akj61300/DSC_6025.png?type=w773" width="500px"/>

Micro SD 카드를 라즈베리파이 3 에 방향을 잘 맞춰서 마운트해주세요.

<img src="http://postfiles5.naver.net/MjAxNzExMjVfMjMz/MDAxNTExNTcxNDExMzg2.XIgByeegxnYtjQ8pDNR8zJajqDo7efiJTW0KCa_pZ_Yg.__2hHxsmBuaL1Vf2pkbKIv70-tk-Weyr0AC1jFCK03Eg.JPEG.akj61300/DSC_6026.jpg?type=w773" width="500px" />

이제 라즈베리파이3 를 부팅하면 됩니다. 화면을 보기위하여 HDMI 로 모니터에 연결하고 부팅해보겠습니다.

모니터에 다음과 같은 화면이 나온다면 정상적으로 모든것이 진행된 것입니다.

<img src="http://postfiles14.naver.net/MjAxNzExMjVfMTY0/MDAxNTExNTcxOTMxMjA0.kyOXjPWF7EfR01tIwWtFydq7wrXUOBNZ94h2ErO4U1Eg.YZPrJw_YA1DnQU7Fwre3jl3NwjWD0qfb0RyzwAb_Rhgg.JPEG.akj61300/DSC_6029.jpg?type=w773" />

## 개발환경을 설정하자

Android Things 는 다른 안드로이드 개발을 할때 처럼 ADB 라는 툴을 이용해 디바이스와 연결하고 프로그램을 설치합니다. 

그렇기 때문에 Android Things 역시 ADB 연결을 해야만 개발을 시작할 수 있습니다. 이제 어떻게 ADB 를 라즈베리파이 3와 연결할 수 있는지 살펴보겠습니다.

> 여기서 ADB 의 설치방법을 다루지는 않겠습니다. 구글에 안드로이드 개발환경 설정을 검색하여 ADB 및 안드로이드 개발환경 설정을 해주세요.

먼저 라즈베리파이에 다른 안드로이드 기기처럼 USB 로 ADB 연결을 가능하게 하는 5핀 케이블 포트를 찾아보세요. 딱 한개 보이긴 하지만 그것은 전원으로 사용합니다.

딱히 ADB 로 사용할 포트가 없으니 USB 말고 다른 ADB 연결 방법을 사용하는 것이 편리합니다. 다른 연결방법이라는 것은 바로 ADB 의 TCP 모드입니다.

Android Things 는 ADBD(ADB 데몬) 가 기본적으로 TCP 모드로 동작되게 설계되어 있습니다.(보통의 경우에는 TCP 모드가 부팅하자마자 동작하지는 않습니다) 

즉 Android Things 디바이스에 이더넷 케이블을 꼽고 ADB TCP 연결을 하면 된다는 이야기입니다.

라즈베리파이에 랜선을 연결하고 다시 부팅을 시도합니다.
 

## LED 를 켜기위한 회로를 만들자

## GND, 5V, GPIO 란?

## Android Studio 로 개발시작해보기

## 결과 확인해보기

## Next Step


