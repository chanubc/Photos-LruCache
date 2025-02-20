# 1️⃣ 프로젝트 소개 및 기능

### 프로젝트 소개
이미지 라이브러리 없이 구현한 LRU기반 이미지 캐싱 앱

### **📌** 주요 기능 및 완성 화면

| 페이징 | Gray Scale, Blur | Zoom In/Out |
|--------|--------|--------|
|![KakaoTalk_20250210_033417890](https://github.com/user-attachments/assets/43e7807f-17de-4adf-bbdf-dd505d013697)|![KakaoTalk_20250210_033410220](https://github.com/user-attachments/assets/ea413617-b688-4932-8e0a-15a1ffbf10f5)  | ![KakaoTalk_20250210_033408503](https://github.com/user-attachments/assets/889bbb7b-95db-4a9b-8592-a724718125fb)|


| Thumbnail | 화면회전 | Retry |
|--------|--------|--------|
|![KakaoTalk_20250212_120005101](https://github.com/user-attachments/assets/28dcd1d8-23c2-40f0-887d-0c94c83dd234)|![KakaoTalk_20250212_135629675](https://github.com/user-attachments/assets/1ef7f81d-cac1-405d-a0a6-08710c7a3362)|![KakaoTalk_20250212_133447614](https://github.com/user-attachments/assets/ee49ea54-5fd3-40ef-a29c-bdcb783054e2)|


### **📌** 프로젝트 구조


**Android App Architercture 사용**

<img src = "https://github.com/user-attachments/assets/6b9da717-dc61-4196-8df5-9be11f80b67c" width = "40%"/>


**폴더 구조**

관심사를 분리해 `data`,  `ui`  두 개의 주요 레이어로 구성하였습니다

```kotlin
📂 photocache
┣ 📂 core
┃ ┣ 📂 common (base viewmodel,intent..)
┃ ┣ 📂 data
┃ ┃ ┣ 📂 cache (메모리, 디스크, uri to bitmap 관련 소스)
┃ ┃ ┣ 📂 repository (Repository 인터페이스)
┃ ┃ ┣ 📂 repositoryimpl (Repository 구현체)
┃ ┃ ┣ 📂 paging (페이징 소스)
┃ ┃ ┣ 📂 mapper (데이터 변환 로직)
┃ ┃ ┣ 📂 util (data layer 유틸 함수)
┃ ┣ 📂 network (API 통신 및 DTO 관련)
┃ ┣ 📂 model (앱 전반에서 사용하는 데이터 모델)
┃ ┣ 📂 navigation (네비게이션 목적지)
┃ ┣ 📂 designsystem (공통 UI 컴포넌트)
┣ 📂 feature
┃ ┣ 📂 detail (상세 화면)
┃ ┣ 📂 home (홈 화면)
┃ ┣ 📂 main (Nav Host)
📄 PhotoCacheApp (앱 엔트리 포인트)
```

### **📌**  Android App Architercture **선정 이유**


1. **의존성 분리**
    - UI Layer와 Data Layer를 분리하여 **각 계층 간의 불필요한 의존성을 제거**하였습니다.
    - 이를 통해 **UI 변경 시에도 Data Layer가 영향을 받지 않도록 설계**하여, 유지보수성을 높였습니다.
2. **유지보수와 확장성을 고려**
    - 현재는 기능이 많지 않지만 추**후 API 통신 추가 및 새로운 화면(View) 추가 시에도 기존 코드 수정 없이 확장할 수 있도록 구성**하였습니다.
    - 예를 들어, **현재 Compose UI를 XML 기반으로 변경하더라도 Data Layer는 그대로 유지할 수 있도록 설계**하였습니다. (OCP)
3. **Domain Layer 생략 (구글 권장 아키텍처 적용)**
    - 현재 앱에서는 **UseCase가 필요할 만큼의 복잡한 비즈니스 로직이 존재하지 않는다고 판단**하였습니다.
    - 따라서 **Domain layer가 필수인 클린 아키텍처(Clean Architecture)를  적용하는 대신, 구글 권장 아키텍처를 사용하여 불필요한 계층을 줄이고 코드의 일관성을 유지**하였습니다.
    - repository 패턴을 활용하여 추상화된 인터페이스에 의존하여 viewmodel에서 api연결 및 캐시 관련 코드를 불러오도록 하였습니다. 이로 인해 각 계층간 느슨한 결합을 완성하였습니다

### **📌 기술 스택**


| 분류 | 사용 기술 | 선정 이유 |
| --- | --- | --- |
| **아키텍처 패턴** | MVI + MVVM | UI와 데이터의 결합도를 낮추고, 상태 기반 관리로 유지보수 용이 |
| **디자인 패턴** | Repository, Observer | 데이터 소스를 일관성 있게 관리하고, UI와 데이터 자동 동기화 |
| **비동기 처리** | Coroutine | 비동기 작업을 안전하고 효율적으로 관리 |
| **네트워크** | Retrofit2 + OkHttp | API 요청 최적화 및 커스텀 설정 가능 |
| **로깅** | Timber | 가독성이 좋은 로깅 및 릴리즈시 숨김 가능 |
| **페이징 처리** | Paging3 | 대량의 데이터를 최적화하여 로드 |
| **JSON 직렬화** | kotlinx-serialization | 빠르고 가벼운 JSON 변환 |
| **의존성 주입** | Dagger-Hilt | 객체 생명주기 관리 및 코드 결합도 감소 |

### **Hilt 사용 이유**

Hilt를 이용해 의존성을 효율적으로 주입하였습니다. API 통신을 위한 Retrofit 인스턴스, Repository 인터페이스와 구현체, 메모리 및 디스크 캐시 구현체를 Hilt 모듈을 통해 싱글톤으로 제공하여, 객체 생명주기 관리와 코드 결합도를 낮췄습니다.

### **Paging3 사용 이유**

대량의 데이터를 효율적으로 로드하기 위해 Paging3를 도입하였으며, 자동으로 데이터 로딩, 로드 상태(로딩, 에러, retry) 관리를 지원하여 성능 최적화와 유지보수를 용이하게 하였습니다.

### **디자인 패턴: Repository Pattern + Observer Pattern 적용**

- **Repository Pattern**
    - Data Layer에서 **데이터 소스를 일관되게 관리하기 위해 사용**하였습니다.
    - API 또는 캐시 datasource 와의 연결을 관리하며, UI에서 데이터 소스의 변경에 영향을 받지 않도록 구성하였습니다.
    - viewmodel에서 추상화된 repository에 접근함에 따라 datasource의 구체적인 구현에 의존하지 않아도 되고 변경과 확장에 용이하게 되었습니다.
    - **suspend**함수를 사용하여 코루틴 환경에서 비동기 작업을 처리 하였습니다.
    
    ![image.png](image%201.png)
    
- **Observer Pattern**
    - **UI가 데이터의 변경을 실시간으로 감지하고 업데이트할 수 있도록 구현**하였습니다.
    - `StateFlow` 와 `collectAsStateWithLifecycle` 를 활용하여 **상태에 따른 UI 를 자동으로 동기화**합니다.

# 2️⃣ MVI + MVVM 패턴



### **🔹 MVI (Model-View-Intent) 패턴 적용**

MVI 패턴을 활용하여 **이벤트(Intent), 상태(State), 사이드 이펙트(Side Effect)** 를 명확하게 분리하고, 일관된 UI 상태 흐름을 유지하도록 설계했습니다.

일관된 데이터 흐름과 이벤트, 상태 추적에 용이하기 때문에 MVI패턴을 적용하였습니다.

### **핵심 구현 방식**

✅ **BaseViewModel**을 도입하여 공통 MVI 로직을 캡슐화

✅ **일관된 방식으로 이벤트(Intent), 상태(State), 사이드 이펙트(Side Effect) 관리**

✅ **불필요한 UI 업데이트 방지 및 예측 가능한 상태 관리**

### **주요 동작 흐름**

1. **사용자 Intent 정의**
    - `sealed interface`를 활용하여 ViewModel로 전달되는 이벤트를 정의
2. **State 관리 (예측 가능한 상태 관리)**
    - UI 업데이트 최소화
    - 불필요한 변경 방지
    - data class를 통하여 상태를 한곳에 묶음
3. **Side Effect 관리 (One-Time Event 처리)**
    - **`Channel`을 활용하여 이벤트 손실 방지**
    - `SharedFlow`는 화면 구성 변경 시 이벤트 소실 가능성이 있어 `Channel` 사용
    - 한 화면에서 발생하는 이벤트는 `sealed interface`로 묶어 관리

이런 구조를 통해 **명확한 상태 흐름과 안정적인 이벤트 처리**가 가능하도록 구성했습니다.

![image.png](image%202.png)

mvvm 과 혼용한 이유는 추후 고민한 부분에서 설명 드리겠습니다.

**Screen-Route패턴을 통해 StateLess UI를 구현 하였습니다.**

상태는 하위 컴포저블로 내리고 상태관리는 Route 혹은 viewmodel에서 함으로써 단방향 흐름을 유지할 수 있었습니다.

# 3️⃣ 기능 구현 사항


### **이미지 캐싱 로직**


**이미지 로드 과정**

이미지를 로드하는 과정은 **메모리 캐시 → 디스크 캐시 → 네트워크** 순서로 진행됩니다.

1. **메모리 캐시 확인 (`LruCache` 활용)**
    - Android의 `LruCache`를 활용하여 **메모리 캐싱**을 구현하였습니다.
    - `get()` 요청 시, 메모리에 해당 `key`(URL)와 매칭되는 **Bitmap이 존재하면 즉시 반환**합니다.
    - 존재하지 않는 경우, **디스크 캐시를 확인합니다.**
2. **디스크 캐시 확인 (`LinkedHashMap` 활용)**
    - `LinkedHashMap`을 활용하여 **디스크 캐싱을 구현**하였습니다.
    - `get()` 요청 시, 디스크에서 해당 `key`의 파일을 찾고, **있다면 메모리 캐시에 저장 후 반환**합니다.
    - `put()` 요청 시, **LRU(Least Recently Used) 정책**을 적용하여 **최대 크기를 초과하면 가장 오래된 항목을 삭제**합니다.
    - **Bitmap을 `FileOutputStream`을 활용하여 JPEG 포맷으로 변환 후 저장**합니다.
    - 저장된 디스크 캐시도 존재하지 않는다면, **네트워크에서 이미지를 가져옵니다.**
3. **네트워크에서 이미지 다운로드 (`URL → Bitmap 변환`)**
    - 메모리 캐시와 디스크 캐시에 해당 이미지가 존재하지 않으면, **네트워크에서 이미지를 다운로드하여 Bitmap으로 변환**합니다.
    - 다운로드된 Bitmap은 **메모리 캐시 및 디스크 캐시에 저장한 후 반환**합니다.

![image.png](image%203.png)

### 썸네일

[KakaoTalk_20250212_120005101.mp4](KakaoTalk_20250212_120005101.mp4)

상세 화면의 경우 사용자가 원본 이미지를 볼 수 있어야 한다 생각하였습니다. 하지만 원본 이미지의 경우 용량이 크기 때문에 로드 하는데 시간이 오래 걸렸습니다.

아래 Android developer의 문구를 참고하였습니다.

![image.png](image%204.png)

상세화면 진입시 size가 200인 이미지를 서버로부터 요청하여 화면에 보여준 후, 원본 이미지로 update하는 식으로 썸네일을 구현하였습니다.

**→ 하지만 개선해야 할 부분 입니다.**

### blur, gray


[KakaoTalk_20250210_033410220.mp4](KakaoTalk_20250210_033410220.mp4)

blur의 경우 modifer의 확장함수로 만들어 적용

```kotlin
fun Modifier.applyBlurStyle(
    style: ColorFilterType,
): Modifier = composed {
    when (style) {
        ColorFilterType.BLUR -> this.blur(
            radiusX = 10.dp,
            radiusY = 10.dp,
        )

        else -> this
    }
}
```

gray scale의 경우 image 컴포저블의 `colorFilter`  속성을 활용하여 enum과 함께 관리

```kotlin
enum class ColorFilterType {
    GRAYSCALE,
    BLUR,
    DEFAULT,
    ;

    fun toColorFilter(): ColorFilter? = when (this) {
        GRAYSCALE -> ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        else -> null
    }
}

```

### zoom in out

[KakaoTalk_20250212_135629675.mp4](KakaoTalk_20250212_135629675.mp4)

Slot을 활용하여 컴포저블을 받을 수 있도록 하였습니다. 이로 인해 다른 화면에서도 언제든지 재사용 가능합니다.

구성 변경에 대응하기 위해 scale과 offset을 rememberSaveable에 저장하였습니다.

offset의 경우 class이기 때문에 rememberSaveable에 저장이 되지 않는 문제가 있었습니다. x,y좌표가 필요 한 것이기 때문에 ListSaver를 활용하여 원시 타입이 아닌 class 객체도 저장이 되도록 하였습니다.

![image.png](image%205.png)

### 기타 페이징 에러 핸들링


[KakaoTalk_20250212_133447614.mp4](KakaoTalk_20250212_133447614.mp4)

[KakaoTalk_20250212_133446539.mp4](KakaoTalk_20250212_133446539.mp4)

**Slot Api를 활용하여 여러 case에 대응하는 PagingSateHandler 를 Custom**

![image.png](image%206.png)

## 고민한 부분


### MVI패턴 적용 시 copy메소드와 비트맵 객체

문제 상황 : 

mvi 패턴에서 상태 변경 될때 bitmap 객체를 mvi state에 포함 하게 되는 경우 data class copy를 통해 매번 객체가 생성되는 문제가 있었습니다.  물론 이전 값은 더이상 참조 되지 않기 때문에 gc가 해제를 하겠지만 용량이 큰 비트맵 객체가 포함되어 매번 생성 되면 성능 저하가 일어난다 생각하였습니다 

```kotlin
data class HomeState(
    val bitmap: Bitmap? = null,
) : BaseState
```

```kotlin
    override fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadInitialData -> getInitialData(args.id, intent.isMainImage)
            is DetailIntent.ClickBlurButton -> intent { copy(colorFilterType = ColorFilterType.BLUR) }
            is DetailIntent.ClickGrayButton -> intent { copy(colorFilterType = ColorFilterType.GRAYSCALE) }
            is DetailIntent.ClickDefaultButton -> intent { copy(colorFilterType = ColorFilterType.DEFAULT) }
            is DetailIntent.LoadThumbNail -> loadImage(args.downloadUrl, intent.isMainImage)
        }
    }
```

해결 방안 : 크기가 큰 비트맵 객체의 경우 mvi 의 state로 처리하는 것이 아닌 따로 변수화 하여 처리 하였습니다. 마찬가지로 비동기 스트림이며 자체적으로 페이징 관련 상태를 가지고 있는 PagingData 또한 따로 관리 하였습니다.

```kotlin
 private val _images = mutableStateMapOf<String, Bitmap>()
    val images: Map<String, Bitmap> get() = _images

    val newsPagingFlow = homeRepository.getPhotos()
        .cachedIn(viewModelScope)
        .catch {
            postSideEffect(HomeSideEffect.ShowSnackBar(it))
        }
```

### 람다 생성 시 오버헤드 방지.

```kotlin
 PagingStateHandler(
        lazyPagingItems = lazyPagingItems,
        loadingContent = { LoadingScreen() },
        emptyContent = { EmptyTextScreen() },
        errorContent = {
            ErrorScreen(onClick = lazyPagingItems::retry)
            viewModel.onIntent(HomeIntent.SetPagingError(it.toCustomError()))
        },
        content = {
            HomeScreen(
                lazyPagingItems = lazyPagingItems,
                navigateToDetail = { viewModel.onIntent(HomeIntent.ItemClick(it)) },
                onImageLoad = { url -> viewModel.onIntent(HomeIntent.LoadImage(url)) },
                onRetry = lazyPagingItems::retry,
                images = images,
            )
        },
    )
```

 { } 를 사용하게 되면 람다를 생성 하는 것이고 onClick 이벤트가 발생할때 마다 객체가 생성이 됨

불필요한 람다 오버헤드를 줄이기 위해 :: 를 통해 함수를 직접 호출 하여 불필요한 객체 생성을 방지.

```kotlin
onClick = { lazyPagingItems.retry() }
onClick = lazyPagingItems::retry
```

# 4️⃣ 트러블 슈팅


### **비트맵 메모리 저장 및 최적화 과정**

### **🛑 문제 상황**

- 화면에 **이미지를 로드하는 데 시간이 오래 걸림**.
- 로드하는 로직을 `Dispatcher.IO` 블록을 사용 UI 버벅임 문제가 해결되지 않음.
- 많은 이미지 요청으로 인해 UI가 심각하게 느려지고, 아래와 같은 경고 발생 후 앱 강제 종료됨.
    
    ```kotlin
    Skipped 137 frames! The application may be doing too much work on its main thread.
    ```


## **✅ 해결 과정 및 고려 사항**

### **1. `remember` 사용 → 실패**

**결과:**

- 화면에 정상적으로 이미지가 표시되었으나, **그리드 뷰에서 많은 이미지 요청 발생 시 앱이 강제 종료됨**.
- 화면 구성 변경(Configuration Change) 시 **이미지를 다시 로드하는 불필요한 요청 발생**.

**문제:**

- **메모리 부족(OOM) 발생 가능성 증가**
- **프레임 드롭 현상 (`Skipped 137 frames!`)**


### **2. `rememberSaveable` 사용 → 실패**

**결과:**

- 구성 변경 시 이미지를 다시 로드하는 문제는 해결됨.
- 하지만 **`Bundle`의 용량 제한(1MB)으로 인해 앱이 강제 종료됨**.

**문제:**

```kotlin
java.lang.RuntimeException: android.os.TransactionTooLargeException: data parcel size 2889752 bytes
```

- **많은 이미지 데이터를 `Bundle`에 저장하면 용량 제한 초과 발생**
- **여전히 대량의 비트맵을 관리해야 하는 문제가 존재**


### **3. `ViewModel`을 활용한 State 관리 → 부분 해결**

**결과:**

- `ViewModel`을 사용하여 상태를 유지하도록 변경.
- **구성 변경(Configuration Change)에 대응 가능**.
- `rememberSaveable`과 달리 **1MB 용량 제한이 없기 때문에 앱 강제 종료 문제 해결됨**.

**여전히 문제 발생**

- 홈 화면에서 **스크롤 시 버벅거림이 여전함**.
- **메모리 사용량이 높아 GC(Garbage Collector) 부담 증가**.


### **이미지 리사이징 적용 → 개선 ✅**

**결과:**

- `Bitmap`을 **메모리 내에서 직접 리사이징하여 크기 축소 후 로드**.
- 앱이 강제 종료되는 일 없이 정상적으로 작동함.

**하지만 문제점:**

- `while` 루프를 사용하여 이미지 크기를 **200 이하가 될 때까지 반복적으로 리사이징** → **속도가 다소 느림**.
- 성능 최적화를 위해 **보다 효과적인 방법이 필요함**.


### **서버에서 이미지 크기 조정 → 최적 해결 ✅**

**해결책:**

- **서버에서 제공하는 이미지 URL 구조 분석**
- Open API 문서를 확인한 결과, **URL 내에서 직접 `width` 및 `height` 값을 조정 가능**
    
    ![image.png](image%207.png)
    
    **원본 API URL 예시**
    
    ```kotlin
    
    "download_url": "https://picsum.photos/id/102/4320/3240"
    ```
    
    **변경된 URL 예시 (200px 크기로 조정)**
    
    ```kotlin
    "download_url": "https://picsum.photos/id/102/200/200"
    ```
    
    **→ 즉, `width` 및 `height` 값을 변경하면 원하는 크기의 이미지를 서버에서 직접 제공할 수 있음.**
    
- **필요한 사이즈만 받아오도록 Mapper에서 URL을 변환**

### **코드 적용 (`HomeMapper.kt`)**

```kotlin
kotlin
복사편집
private const val SIZE_200 = 200
private const val FILE_EXTENSION = ".webp"

fun ResponsePhotoDto.toPhotoModel(): PhotoModel = PhotoModel(
    id = id,
    downloadUrl = downloadUrl.toResizedUrl(),
)

fun String.toResizedUrl(): String = this
    .substringBeforeLast("/")
    .substringBeforeLast("/") + "/$SIZE_200$FILE_EXTENSION"

```

**이제 상위 레이어로 전달되는 URL 자체를 리사이징된 이미지 URL로 변환하여 성능 최적화**

**200으로 설정한 이유 :** 

- 홈 화면에서 **그리드 형태의 레이아웃을 사용**.
- 가로 380인 기기에서 **그리드 3개로 배치할 경우, 각 이미지의 크기는 약 126.66px 필요**.
- 따라서, **200 정도면 충분한 해상도를 유지하면서도 불필요한 리소스 낭비를 줄일 수 있음**.
- **4000 해상도의 이미지는 불필요하며 로딩 시간 증가 및 메모리 사용량 증가로 인해 최적화 필요**.


## **✅ 최종 결론**

### **1. 서버에서 이미지 크기 조정 가능하다면? → 서버에서 처리하는 것이 최선**

- URL에서 `width` 및 `height` 값을 조정하여 불필요한 데이터 다운로드 방지
- 메모리 절약 및 네트워크 트래픽 감소
- 성능 최적화 (빠른 이미지 로드)

### **2. 서버에서 크기 조정이 불가능하다면? → 클라이언트에서 리사이징 적용**

- 불필요한 고해상도 이미지 로드를 방지하기 위해 클라이언트에서 `Bitmap` 리사이징 적용
- 가능하면 **다운로드된 url을 `InputStream` 을 통해 Bitmap로 변경 하는 과정에서 크기를 줄이는 것이 효과적**


# 5️⃣ 개선 사항

1. **메모리 캐시 key**
    
    메모리 캐시 key와 디스크 캐시 key가 다른 오류를 발견 하였습니다.
    
    이로 인해 디스크 캐시는 정상적으로 불러와지나 메모리 캐시에서 불러와지지 않는 오류가 발생 하였습니다.
    
    ```kotlin
    key.hascode().toString()
    ```
    
    hascode를 통해 객체의 주소를 key로 활용 하여 문제를 해결 하였습니다.
    
    메모리 캐시에서 불러오는 과정 또한 정상적으로 작동하였습니다.
    
2. **썸네일 로드시 불필요한 서버 통신**
    
    **문제 상황** : 썸네일을 로드 시에 썸네일 이미지(200사이즈)를 서버로 부터 로드를 받음과 동시에 원본이미지(약4000사이즈)를 로드 하는 방식이였습니다. 이로 인해 불필요한 api 호출이 1회 발생 하였습니다.
    
    **해결 방안 :** 메모리, 혹은 디스크 캐시로 부터 앞서 받아온 200사이즈 이미지를 먼저 로드 시키는 방안으로 해결 하였습니다.
    
    **성과 :**  불필요한 api 호출을 줄이고 캐시로 부터 받아오기에 썸네일 로드 속도도 월등히 빨라졌습니다.
    
| **서버에서 로드** | **메모리 캐시로부터 로드** |
|:----------------:|:----------------:|
|![KakaoTalk_20250212_120005101](https://github.com/user-attachments/assets/28dcd1d8-23c2-40f0-887d-0c94c83dd234)|![KakaoTalk_20250212_120141467](https://github.com/user-attachments/assets/499d4e74-a7ae-4fd2-a13d-8302af10ade8) |
