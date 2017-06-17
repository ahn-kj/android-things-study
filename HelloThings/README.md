# 프로젝트 개요

[![IMAGE ALT TEXT](http://img.youtube.com/vi/jvyNrBTJSbA&t/0.jpg)](http://www.youtube.com/watch?v=jvyNrBTJSbA&t "Video Title")

Android Things 와 라즈베리파이 3 을 연계하여 LED 1개를 제어하는 매우 간단한 샘플입니다.

이 프로젝트는 다음 블로그를 참조하여 작성되었습니다. 

[https://www.androidthings.rocks/2017/01/03/get-started-with-android-things-today/](https://www.androidthings.rocks/2017/01/03/get-started-with-android-things-today/)

[https://androidthings.rocks/2017/01/08/your-first-blinking-led/](https://androidthings.rocks/2017/01/08/your-first-blinking-led/)

원본 Git Repository 는 다음과 같습니다.

[https://github.com/mplacona/HelloThings](https://github.com/mplacona/HelloThings)

Thanks to mplacona.

원본 Repo 에는 Android-Things 프로젝트 템플릿만 있으며 실제 동작 코드는 블로그를 참조하여 구현 해야 합니다.

이 프로젝트는 해당 블로그를 참조하여 실제로 코드를 작성하였습니다.

--- 

## 라즈베리파이 3 세팅

라즈베리파이3 와 BreadBoard(일명 빵판) 연결 방법, 회로 만드는 법을 가이드 합니다.

## 프로젝트 실행 방법

프로젝트를 실제로 실행하기 위한 방법을 가이드합니다.


## Hello Things 코드 설명 및 동작 흐름.

#### Android Things 프로젝트 설정

* app/build.gradle 에 Android Things 라이브러리 의존성을 추가.

```groovy
dependencies {
    // 안드로이드 팅스 라이브러리 디펜덴시. provided 컴파일시 클래스파일을 포함시키지 않음.
    provided 'com.google.android.things:androidthings:0.2-devpreview'
}
```

* AndroidManifest.xml 에 library 사용 추가

```xml
<uses-library android:name="com.google.android.things"/>
```

* IOT_LAUNCHER 인텐트필터 카테고리 추가

```xml
<category android:name="android.intent.category.IOT_LAUNCHER"/>
```

여기까지 설정하면 Android Things 프로젝트로 실행할 수 있게 된다.

#### GPIO 를 이용한 LED ON/OFF 방법

PeripheralManagerService 는 안드로이드 팅스에서 PinOut 을 관리할 수 있는 서비스이다. PeripheralManagerService 를 사용하여 

* PeripheralManagerService 객체를 생성한다.

```java
PeripheralManagerService service = new PeripheralManagerService();
```

* "BCM6" 이름의 GPIO 를 오픈한다.

```java
mLedGpio = service.openGpio("BCM6");
```

* 입출력 설정을 체크한다.

```java
mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
```

* GPIO 포트를 사용하여 LED ON/OFF

```java
mLedGpio.setValue(!mLedGpio.getValue());
```



