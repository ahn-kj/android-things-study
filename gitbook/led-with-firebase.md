# Android Things - Firebase 를 이용한 LED 제어

이번 예제는 Firebase 를 연동하여 LED 한개를 원격으로 제어하는 예제입니다. 더불어 Android 스마트폰에서 어떻게 원격으로 LED 를 제어하는지 알아봅니다.

## 준비물

준비물은 기존 [LED 제어 예제](https://c548adc0c815.gitbooks.io/iot-with-android-things/content/androidthings-controlled.html) 와 동일합니다.

* Rassberry PI 3
* Bread Board
* Micro SDCARD 
* SDCARD Adapter(PC 에 Micro SD 카드를 인식시키기 위한 어댑터)
* 소켓점프케이블 M/F 2개
* 저항 220 옴
* LED 1개

## 기존 소스가 필요합니다!

이 프로젝트는 시리즈물처럼 기존에 동작시킨 Android Things 로 LED 제어하기 와 이어지는 프로젝트입니다. 기존 코드와 문서도 참조해주세요.

[기존코드 및 문서 보기](https://github.com/ahn-kj/android-things-study/tree/master/FirstAndroidThings)

## Firebase 소개

Firebase 는 Google 이 제공하는 클라우드 기반 백엔드 기술로 최초에는 리얼타임 데이터베이스 기능을 주로 담당하였지만 발전을 거듭하여 현재에는 모바일 앱이 백엔드에 필요한 공통적인 기술 대부분을 지원합니다.  
지원하는 기능들은 다음과 같습니다.(2017.11.27 일 기준 BETA 버전은 제외)

|기능|설명|
|:---:|:---|
|실시간 데이터베이스|클라우드에 호스팅되는 noSQL 데이터 베이스로 데이터를 실시간으로 동기화 합니다.|
|오류보고|앱이 비정상으로 종료되었을때 문제점을 보고 받아 앱의 전반적인 상태를 모니터링 합니다. |
|인증|Email, Google, Facebook, Twitter, Github, 전화번호등 다양한 로그인 방법을 지원하고 안전한 인증방법을 제공합니다. |
|Cloud Storage|Google 이 제공하는 클라우드 저장소에 이미지, 오디오, 동영상등의 컨텐츠를 저장하고 공유합니다. <br>CDN 서버와 비슷한 기능을 제공한다고 생각하면 됩니다.|
|Android 용 Test Lab|Google 이 호스팅하는 가상 기기및 실제 기기에서 앱에 대해 자동 테스트 및 맞춤 테스트를 실행합니다.|
|Google 애널리틱스|사용자 기여도와 행동을 분석하여 제품 로드맵의 의사결정 근거로 활용합니다. 맞춤분성르 위해 원시 이벤트 데이터를 보낼 수 있습니다.|
|클라우드 메시징|Android, iOS, 웹 등 다양한 플랫폼에 푸쉬 알림을 무료로 보냅니다.|
|동적 링크|딥 링크를 사용하여 iOS, Android, 웹에서 원하는 화면으로 보낼수 있는 스마트 URL 을 제공합니다.|
|원격구성|각 사용자에게 앱이 표시되는 방식을 맞춤 설정합니다. 새버전을 배포하지 않아도 Firebas 콘솔에서 모양과 느낌에 변화를 주거나 기능을 단계적으로 출시 가능합니다.|
|초대|추천코드, 즐겨찾는 콘첸츠까지 이메일 또는 SMS 를 통해 공유할 수 있도록 지원합니다.|
|앱 색인 생성|구글 검색 통합으로 이미 앱을 설치한 사용자의 재참여를 유도합니다.|
|AdMob|전세계 잠재고객에게 광고를 게재하여 수익을 창출합니다.|
|애드워즈|Google 에 추천광고를 통해 사용자를 획득하고 유지합니다.|

Firebase 는 매년 Google IO 에서 새로 소개될 만큼 구글이 강력하게 지원하고 있는 서비스 중 하나입니다.  
위 기능만 보아도 전부 활용할 수 있을까 라는 의문이 생길정도로 다양하는데 BETA 버전으로 지원하는 기계학습 기반 예측, 서버코드 없이 백엔드 코드를 삽입하는 클라우드 함수, 성능 모니터링등 곧 생길 서비스 역시 다양하고 강력합니다.

구글이 Firebase 로 추구하는 것은 모바일 앱 또는 웹 같은 프론트엔드 작업에서 공통적으로 사용하지만 골치 아플 수 있는 백엔드 기술을 Firebase 로 통합하여 쉬운 방법으로 제공하는 것입니다. 결국 앱 또는 웹 프론트 개발자가 자신의 아이디어에 보다 더 집중할 수 있게 만드는 것입니다. 

필자가 Firebase 를 실제 외주 개발에서 사용한 경험에 의하면 개발 작업기간은 체감상 2배이상 빠르게 진행되었습니다. 백엔드 서버개발을 하지않고 기능에 집중해 구현이 가능했기 때문입니다. 

개발자 인력을 여러명 둘수 없고, 빠른 시간안에 기능을 구현해야하는 벤처회사에서는 Firebase 의 적극도입을 고려해볼만 합니다. Android Things 역시 Firebase 를 쉽게 사용가능하며, 이번 LED 제어에서는 Firebase 의 기능 중 실시간 데이터베이스 기능을 사용할 예정입니다.

## Firebase 설정하기

Firebase 는 구글 아이디가 있다면 바로 서비스 사용이 가능합니다. 구글에 로그인 후 Firebase 사이트에 접속하세요.

[https://firebase.google.com/](https://firebase.google.com/)

사이트에 접속 후 우측 상단 콘솔로이동을 클릭합니다.

<img src="http://postfiles3.naver.net/MjAxNzExMjdfMTM2/MDAxNTExNzk0MTYyNjcw.9MDoSW2-BlM9mPaVCimClWXsq2JKPmXOFuia7R1_1Swg.qPU1Kai7BEh8W0FimLTrRBuTZ0_vg-EE1ZqeCNRuCGsg.PNG.akj61300/gotoconsole.png?type=w773" width="500px" />

프로젝트 추가 버튼을 누릅니다.

<img src="http://postfiles14.naver.net/MjAxNzExMjdfMjA5/MDAxNTExNzk0NTQ5MjI3.nJKsnn9diwdWeKwoOIpRV1oPU0-p2xt9T1sCfAtZwfwg.nXTHjaZkBqSIT_Hjvr7wqQUoA20TMyPlTROeCow3sP0g.PNG.akj61300/add_prj.png?type=w773" width="250px"/>

프로젝트 이름을 android-things 로 합니다. 물론 프로젝트 이름은 바꾸어도 전혀 문제가 없습니다. 또 국가/지역을 선택할 수 있는데 대한민국 역시 선택이 가능하므로 대한민국을 선택하겠습니다.

<img src="http://postfiles11.naver.net/MjAxNzExMjdfODIg/MDAxNTExNzk0NjczNDY2.eICKscc_OhyHD_gtzNjAb000-hal566HHJlVvULixOIg.g2bOqu4tDbteqa9ifem2IZxFbrac-IExUqGVLZr6tg4g.PNG.akj61300/add_prj02.png?type=w773" width="300px"/>

프로젝트를 생성하면 대시보드 화면으로 이동하게 되는데 여기서 Android 앱에 Firebase 추가 버튼을 누릅니다.

<img src="http://postfiles16.naver.net/MjAxNzExMjhfMjky/MDAxNTExNzk2OTIwMTU0.hnSYUriM0WhyHD0ba2APyxt2wZ9iKQKTHTik2IN6oLgg.AKlATZtj2ARmPdDv1f_r8_OUVCRXCfd1d2LNU_Ie218g.PNG.akj61300/dashboard.png?type=w773" width="500px" />

다음 화면에서는 안드로이드 앱의 패키지이름을 넣어야합니다. Android Studio 에서 패키지 이름을 확인하고 넣도록 합시다. 기존 프로젝트를 그대로 이용한다면 패키지 이름은 다음과 같이 입력합니다.

<img src="http://postfiles4.naver.net/MjAxNzExMjhfMjM4/MDAxNTExODAwMDI1NTk5.CC8iORY708wgnQ6KfDIDwFBxr87rgce3tr9FmdsyMd8g.s6rihgZaC-iUNbNeUTmVgvGv_NDjlKMk5ZB8W1n5atog.PNG.akj61300/fire_android01.png?type=w773" width="500px" />

패키지 이름을 제외하고 나머지는 선택사항이므로 일단 앱등록 버튼을 누릅니다. 그리고 다음화면에서 다운로드 google-services.json 파일 버튼을 누릅니다.

<img src="http://postfiles9.naver.net/MjAxNzExMjhfMTM2/MDAxNTExODAwNjczODI1.MKcbSe_DoO-lTeBz4l0CjT4KMo5jc6Kbf9TVBbwcxI8g.a-C3LvmngECUfm7O4uuPKHgHI_p72-938cuykzwYCM8g.PNG.akj61300/fireabase022.png?type=w773" width="500px"/>

Android Studio 에서 기존 LED 제어 프로젝트를 열고 google-services.json 파일을 프로젝트의 app 루트 경로에 복사합니다.

<img src="http://postfiles2.naver.net/MjAxNzExMjhfOTkg/MDAxNTExODAwOTQ2OTM3.pw8xUgos79zAlue7Gy3f-K8OrudVGDOOrcWaBwEDZ1Yg.YqTg58cWgKHF4-W7p4m4M1vUvb1abtP1MBdD0y1PN28g.PNG.akj61300/app-gsj.png?type=w773" width="300px"/>

그 다음은 파이어베이스의 의존성 추가를 할 차례입니다. 프로젝트 단위의 build.gradle 파일을 다음과 같이 수정하세요.


```groovy
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.google.gms:google-services:3.1.0'
        ...
    }
}
```

그리고 모듈의 있는 build.gradle 파일에 다음과같이 수정하세요.

```groovy

dependencies {
   ...
    implementation 'com.google.firebase:firebase-core:11.6.0'
    implementation 'com.google.firebase:firebase-database:11.6.0'
}

apply plugin: 'com.google.gms.google-services'

```

클라이언트쪽 설정은 이것으로 끝입니다. 이제 Firebase 에 데이터를 추가해보겠습니다. 웹에서 Firebase 콘솔에 접속한뒤 좌측에서 Realtime Database 를 클릭하고 시작하기를 누릅니다.

<img src="http://postfiles10.naver.net/MjAxNzEyMDFfMTk0/MDAxNTEyMDU2NDA5Nzg3.eqfbs7p2UZ7oQGqERH9x2mFaVw6XDecLKpKadwX-Eecg.wsXIcdyE4ZOIlo4oyPuVB7Q5_tZURhEcL0uDEc__vk0g.PNG.akj61300/database01.png?type=w773" width="600px" />

데이터에 + 를 눌러 새 데이터를 추가합니다.

<img src="http://postfiles2.naver.net/MjAxNzEyMDFfMjY5/MDAxNTEyMDU2NjQ5MjAx.isEGPm5bwurofk5wcuhW8-GougVnC5LZIbufIeePqtsg.6j-hXho3iPCp2O7oLmVA5dC86P_QPVfocaa29M-w0TMg.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-12-01_%EC%98%A4%EC%A0%84_12.43.41.png?type=w773" width="400px" />

데이터의 이름은 bcm6 gpio 를 제어할 것이므로 bcm6 으로 하겠습니다. 값을 on 으로 설정합니다.

<img src="http://postfiles9.naver.net/MjAxNzEyMDFfMTU0/MDAxNTEyMDU2NzgxNTM5.afZ4BYCRD_9ih_uTH9lAYQZw2ukFqselY4cvPLbiu1cg.YOhU-91lPa4Sryj259QazIk_B9DkhBLCDfL4UIFV1iwg.PNG.akj61300/add_bcm.png?type=w773" width="500px" />

다음에는 Firebase 의 권한설정을 해주어야 합니다. Firebase 의 모든 데이터는 읽기, 쓰기 모두 [auth != null] 이 기본값입니다. 인증이 성공한 유저에게만 허용한다는 것이죠. 테스트를 위해 읽기 권한을 true 로 바꾸고 게시 버튼을 클릭합니다.

<img src="http://postfiles1.naver.net/MjAxNzEyMDFfNjkg/MDAxNTEyMDU2OTU2MzE2.BaVDxvM-Bis3P2IRsFJpLER7vmVtFs1qpsnNmsRKY3Mg.oi_6FEwOySQL_l2--D9bzQ-Q2yT4szPYIxIUVZPIwyIg.PNG.akj61300/rule01.png?type=w773" />

이제 모든 설정은 끝났으니 코드를 작성하면 됩니다.


## Firebase 에서 데이터 읽어 LED 제어하기

Firebase 에서 데이터를 읽어오기 위해 MainActicity.kt 소스를 다음과 같이 변경하겠습니다.

```kotlin
package com.akj.firstandroidthings

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : Activity() {

    val TAG = "MainActivity"

    /**
     * GPIO(General-purpose input/output) 의 이름.
     */
    val gpioName = "BCM6"

    /**
     * GPIO 인스턴스
     */
    var gpio: Gpio? = null

    /**
     * Firebase Reference
     */
    val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // PeripheralManagerService 는 주변장치 관리자 같은거
        val service = PeripheralManagerService()

        // gpio 현재 하드웨어에 존재하는 GPIO 리스트를 로깅
        Log.d(TAG, "Available GPIO: " + service.gpioList)

        try {
            gpio = service.openGpio(gpioName)

            // 입출력 설정을 체크한다.
            // DIRECTION_OUT_INITIALLY_LOW 는 GPIO 를 출력모드로 설정
            gpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            gpio?.let { gpio ->
                // Firebase 에서 bcm6 경로를 읽어온다.
                database.child("bcm6").addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError?) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        // Firebase 의 bcm6 데이터가 on 이면 LED 를 켜고 그렇지 않으면 끈다.
                        gpio.value = "on".equals(snapshot?.value.toString())
                    }
                })
            }

        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }
}

```


앱을 빌드후 라즈베리파이에서 실행해보세요. Firebase 의 bcm6 데이터가 on 이면 LED 가 켜지고, on 이 아니면 LED 가 꺼지는 것을 확인 가능합니다.

큰 어려움 없이 원격으로 웹에서 LED 제어를 성공한 셈입니다.

## 안드로이드 스마트폰용 앱 생성 및 설정

지금까지는 Firebase 웹 콘솔에서 LED 를 제어했으니 더 나아가 안드로이드 스마트폰에서 LED 를 제어해보겠습니다. 안드로이드 스튜디오에서 file --> new --> new module 을 선택하세요.

모듈 템플릿으로 phone & tablet 을 선택합니다. module 이름은 FirstThingsMobile 로 하겠습니다.

<img src="http://postfiles8.naver.net/MjAxNzEyMDFfMjQ4/MDAxNTEyMDU3OTg5NjM2.B_7PvLIaL7yfY-6K-3YPDECkhKW2P5sbpk9UJjCzo1Ag.MQy_lYhReaUYYVaEKLKRxRr9WqHTxDl47qi010zUl6Mg.PNG.akj61300/new_mobile01.png?type=w773" /width="600px">

다음화면에서는 EmptyActivity 를 선택하고 Activity 의 이름은 기본값으로 설정하여 모듈을 생성합니다. 좌측 네비게이터에서 모듈이 생성되었는지 확인해보세요. 다음화면처럼 모듈이 보여야 합니다.

<img src="http://postfiles12.naver.net/MjAxNzEyMDFfMjU3/MDAxNTEyMDU4MjI4NzMw.vh6NkKH5wkFN37Fiv_bgJ7dZKaZGTJogyWWpg20HDhAg.z_IFJfXJdz9YOGjvcauJ6h1Akxt6wAcsPb7S9DLrZcMg.PNG.akj61300/module02.png?type=w773" width="400px" />

스마트폰용 모듈 역시 Firebase 관련 설정은 똑같습니다 모듈의 build.gradle 파일을 다음과 같이 변경하세요.

```groovy
dependencies {
    ...
   implementation 'com.google.firebase:firebase-core:11.6.0'
    implementation 'com.google.firebase:firebase-database:11.6.0'
}

apply plugin: 'com.google.gms.google-services'
```

기존에 Firebase 는 안드로이드 앱중 com.akj.firstandroidthings 패키지만 사용을 허가했기 때문에 새로운 모듈에서는 사용할 수가 없습니다. 웹에 접속하여 Firebase 에 새 안드로이드 앱을 추가합니다.

<img src="http://postfiles16.naver.net/MjAxNzEyMDFfMTcg/MDAxNTEyMDU5NDI1OTAx.aLjnpBJF2nLoLTXeHres7qOy8hVWmybfGb3rIcWFz98g.cAQvX4-O9jk0RrnKQgdAc2-V7vzc0rohxoJDiIdxGy0g.PNG.akj61300/other_app01.png?type=w773" />

다음 스텝 역시 기존과 동일합니다. 다른것이 있다면 패키지 이름을 모바일 모듈로 생성한 패키지 이름을 사용한다는 것입니다.

<img src="http://postfiles6.naver.net/MjAxNzEyMDFfMjE3/MDAxNTEyMDU5NjU1Mjkx._N__00g5Sn41G7ONWoOPaXP8mQGmFlbkduBKS5Sf5iAg.36A3Uoad1hsKG42buenIBzyJZUyaicwpbMZhiMDAgQ0g.PNG.akj61300/reg20.png?type=w773" width="450px" />

나머지는 선택사항이므로 앱등록을 누르고 google-services.json 을 다운로드 합니다. 라즈베리파이 모듈을 만들때와 마찬가지로 모듈 루트 디렉토리에 google-services.json 을 카피합니다.

<img src="http://postfiles3.naver.net/MjAxNzEyMDFfNDIg/MDAxNTEyMDU5NzkzNjM4.reQ_Q9N4iBB7Qb8oR9JIc6pneE_aE14Tw28zJFyIdWgg.bR3GridBPWMx6hbCcroMOkKn2l1necuPshbTg6SMfZog.PNG.akj61300/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-12-01_%EC%98%A4%EC%A0%84_1.36.07.png?type=w773" width="400px" />

다음은 스마트폰에서 bcm6 데이터를 쓰기권한을 가질수 있도록 해주어야 합니다. Firebase Console 에서 다시 Rule 탭으로 가서 다음과 같이 변경하고 게시합니다.

<img src="http://postfiles11.naver.net/MjAxNzEyMDFfMTYg/MDAxNTEyMDYwMjcyODYy.JKLLymnnjdGVQOsGKwK-HMbTDmLPIpxbUm3KE6us5ugg.pJ_-U2oDPx5DLYhNVw1LCvSIzesYaDXuDKe9H5D4A1gg.PNG.akj61300/rule30.png?type=w773" />

변경된 권한을 주의깊게 봐주세요. Firebase 는 특정 데이터 영역만 별도로 권한을 줄 수 있습니다. 전체적인 권한은 읽기권한이 모두 허용되어 있고, 쓰기 권한은 인증된 사용자만 가능하지만, bcm6 하위 데이터에 한해서는 읽기권한 쓰기권한을 모두 사용 가능합니다.

이제 설정이 끝났으니 본격적으로 스마트폰용 어플 코드를 작성해보겠습니다. 

## 스마트폰에서 원격제어하는 코드 작성하기

스마트폰에서 LED 를 제어하기 위해 먼저 UI 를 만들도록 하겠습니다.
activity_main.xml 을 열고 드래그앤 드랍으로 Switch 를 가운데에 배치합니다.

<img src="http://postfiles5.naver.net/MjAxNzEyMDFfMTA0/MDAxNTEyMDYwNjc0NTM2.sUn_NLquD-12ImFAI8QOlhiQ1dsIvbfFVo2v4AqzUucg.KiZUy_IcIdxdXjb1ThrV5CGWMmqylIgLPp6l2afXx3Ig.PNG.akj61300/ui01.png?type=w773" width="400px"/>

Switch 의 텍스트는 bcm6, id 는 bcm6Switch 로 변경합니다.

<img src="http://postfiles8.naver.net/MjAxNzEyMDFfNTQg/MDAxNTEyMDYwNzk5NDgx.MLt8Bh6XbrtJDOEvTg1RGG6n7srXiKUvhe0MBtwnInog.kt1VfYuVLP54_L36ovd-skTdk0DA1JXL4D9iO5oRAXMg.PNG.akj61300/bcm6switch.png?type=w773" />

스마트폰 모듈의 MainActivity.kt 를 다음과 같이 편집합니다.

```kotlin
package com.akj.firstthingsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Firebase 에서 bcm6 값을 읽어 값이 on 이면 스위치를 켜고 아닌 경우 스위치를 끈다.
        database.child("bcm6").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    bcm6Switch.isChecked = "on".equals(it.value.toString())
                }
            }
        })
        
        bcm6Switch.setOnClickListener { 
            // 스위치가 클릭되면 스위치의 값에 따라 Firebase 의 bcm6 값을 on 또는 off 로 바꾼다.
            database.child("bcm6").setValue(if(bcm6Switch.isChecked) "on" else "off")
        }
    }
}
```

## 결과 확인

<img src="http://postfiles3.naver.net/MjAxNzEyMDJfMTcz/MDAxNTEyMjAyODUzNzc0.mxW7zpMAskgNLZiDxGO_-1Ot3P_qMN2W-G68thPzaUQg.qsqcyueJeIpBiUHTEvQNV-j9cEtiQ3MwyBCbc7B-nZ4g.GIF.akj61300/result02.gif?type=w773" width="320px" />

스마트폰에서 스위치를 켜면 LED 에 불이 들어오고, 스위치를 끄면 LED 의 불이 꺼지게 됩니다. Firebase 와 Android Things 를 이용하면 쉽게 원격제어가 가능합니다.

## 참고소스

참고소스는 GitHub 에 등록되어 있으며 링크는 다음과 같습니다.
[]()

> google-services.json 은 포함되어 있지않으며 반드시 google-services.json 을 추가하고 실행하시기 바랍니다.
> 

## Next Step

다음 스텝에서는 Android Things 에서 PWM 신호를 이용하여 LED 애니메이션을 제어해보도록 합니다.