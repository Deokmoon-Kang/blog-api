## 기능 구현 API
블로그검색
~~~http request
method: get
locat test url: localhost:8080/v1/blog/search?query=keyword" 
~~~
인기검색어
~~~http request
method: get
local test url: localhost:8080//v1/blog/popular/keyword
~~~

## jar 다운로드(github 링크)
- https://github.com/Deokmoon-Kang/blog-api
- master 브랜치

## 기능 요구사항
### 블로그 검색
- `키워드`를 통해 블로그를 `검색`
- 검색할 때 `정렬` 조건을 줄 수 있는데, `정확도순`, `최신순`을 선택하여 조회 가능  
- 검색 결과는 `Pagination`  형태로 제공
- 검색소스는 카카오 API의 키워드로 블로그 검색 기능을 활용
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려
### 인기 검색어 목록
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공
- `검색어` 별로 `검색된 횟수`도 함께 표기
### 설계 및 성능 요구사항
- `멀티 모듈` 구성 및 모듈간 의존성 제약
- `다량의 트래픽` 및 `대량의 데이터` 고려
- `동시성 이슈` 발생할 수 있는 부분을 염두하여 구현
    - 키워드 별로 검색된 횟수의 정확도
- 장애에 대한 이슈 고려 
    - 카카오 API 이슈 시 네이버 블로그 검색 API를 통해 데이터 제공
## 블로그 검색 API
### 카카오
~~~http request
GET /v2/search/blog HTTP/1.1
Host: dapi.kakao.com
Authorization: KakaoAK ${REST_API_KEY}
~~~
Request 
- Parameter

|Name|Type|Description|Required|
|------|---|---|---|
|query|String|검색을 원하는 질의어.특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음|O|
|sort|String|결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy|X|
|page|Integer|결과 페이지 번호, 1~50 사이의 값, 기본 값 1|X|
|size|Integer|한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10|X|
Response 
- meta

|Name|Type|Description|
|------|---|---|
|total_count|Integer|검색된 문서 수|
|pageable_count|Integer|total_count 중 노출 가능 문서 수|
|is_end|Boolean|현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음|
- documents

|Name|Type|Description|
|------|---|---|
|title|String|블로그 글 제목|
|contents|String|블로그 글 요약|
|url|String|블로그 글 URL|
|blogname|String|블로그의 이름|
|thumbnail|Boolean|검색 시스템에서 추출한 대표 미리보기 이미지 URL|
|datetime|Datetime|블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]|
Sample
~~~http request
Request

curl -v -X GET "https://dapi.kakao.com/v2/search/blog" \
--data-urlencode "query=https://brunch.co.kr/@tourism 집짓기" \
-H "Authorization: KakaoAK ${REST_API_KEY}"

Response

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "meta": {
    "total_count": 5,
    "pageable_count": 5,
    "is_end": true
  },
  "documents": [
    {
    "title": "작은 <b>집</b> <b>짓기</b> 기본컨셉 - <b>집</b><b>짓기</b> 초기구상하기",    
    "contents": "이 점은 <b>집</b>을 지으면서 고민해보아야 한다. 하지만, 금액에 대한 가성비 대비 크게 문제되지 않을 부분이라 생각하여 설계로 극복하자고 생각했다. 전체 <b>집</b><b>짓기</b>의 기본방향은 크게 세 가지이다. 우선은 여가의 영역 증대이다. 현대 시대 일도 중요하지만, 여가시간 <b>집</b>에서 어떻게 보내느냐가 중요하니깐 이를 기본적...",
    "url": "https://brunch.co.kr/@tourism/91",
    "blogname": "정란수의 브런치",
    "thumbnail": "http://search3.kakaocdn.net/argon/130x130_85_c/7r6ygzbvBDc",
    "datetime": "2017-05-07T18:50:07.000+09:00"
    },
    ...
  ]
}
~~~

### 네이버

~~~http request
GET /v1/search/blog.json?query=%EB%A6%AC%EB%B7%B0&display=10&start=1&sort=sim HTTP/1.1
Host: openapi.naver.com
User-Agent: curl/7.49.1
Accept: */*
X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}
X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}
~~~
Request

|Name|Type|Description|Required|
|------|---|---|---|
|query|String|검색어(UTF-8)|O|
|start|Integer|검색 시작 위치(기본값: 1, 최댓값: 1000)|X|
|display|Integer|한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)|X|
|sort|String|sim: 정확도순으로 내림차순 정렬(기본값), date: 날짜순으로 내림차순 정렬|X|
Response(XML)

|Name|Type|Description|
|------|---|---|
|rss|-|RSS 컨테이너. RSS 리더기를 사용해 검색 결과를 확인할 수 있습니다.|
|rss/channel|-|검색 결과를 포함하는 컨테이너. channel 요소의 하위 요소인 title, link, description은 RSS에서 사용하는 정보이며, 검색 결과와는 상관이 없습니다.|
|rss/channel/lastBuildDate|dateTime|검색 결과를 생성한 시간|
|rss/channel/total|Integer|	총 검색 결과 개수|
|rss/channel/start		|Integer|검색 시작 위치|
|rss/channel/display		|Integer|한 번에 표시할 검색 결과 개수|
|rss/channel/item		|-|개별 검색 결과. JSON 형식의 결괏값에서는 items 속성의 JSON 배열로 개별 검색 결과를 반환합니다.|
|rss/channel/item/title		|String|블로그 포스트의 제목. 제목에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.|
|rss/channel/item/link		|String|블로그 포스트의 URL|
|rss/channel/item/description	|String|블로그 포스트의 내용을 요약한 패시지 정보. 패시지 정보에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.|
|rss/channel/item/bloggername	|String|블로그 포스트가 있는 블로그의 이름|
|rss/channel/item/bloggerlink	|String|블로그 포스트가 있는 블로그의 주소|
|rss/channel/item/bloggerlink	|dateTime|블로그 포스트가 작성된 날짜|


Sample
~~~http request
Request

curl  "https://openapi.naver.com/v1/search/blog.xml?query=%EB%A6%AC%EB%B7%B0&display=10&start=1&sort=sim" \
    -H "X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}" \
    -H "X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}" -v

Response

< HTTP/1.1 200 OK
< Server: nginx
< Date: Mon, 26 Sep 2016 01:39:37 GMT
< Content-Type: text/xml;charset=utf-8
< Transfer-Encoding: chunked
< Connection: keep-alive
< Keep-Alive: timeout=5
< Vary: Accept-Encoding
< X-Powered-By: Naver
< Cache-Control: no-cache, no-store, must-revalidate
< Pragma: no-cache
<
<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0">
    <channel>
        <title>Naver Open API - blog ::'리뷰'</title>
        <link>http://search.naver.com</link>
        <description>Naver Search Result</description>
        <lastBuildDate>Mon, 26 Sep 2016 10:39:37 +0900</lastBuildDate>
        <total>8714891</total>
        <start>1</start><display>10</display>
        <item>
            <title>명예훼손 없이 <b>리뷰</b>쓰기</title>
            <link>http://openapi.naver.com/l?AAABWLyw6CMBREv+ayNJe2UrrogvJwg8aYKGvACiSUalNR/t6azGLO5Mzrrd0moVSQJZDl/6I4KIxGpx9y9P4JNANShXSzHXZLu2q3660Jw2bt0k1+aF1rgFYXfZ+c7j3QorYDkCT4JxuIEEyRUYGcxpGXMeMs3VPBOUEWGXntynUW03k7ohBYfG+mOdRqbPL6E84/apnqgaEAAAA=</link>
            <description>명예훼손 없이 <b>리뷰</b>쓰기 우리 블로그하시는 분들께는 꽤 중요한 내용일 수도 있습니다 그것도 주로 <b>리뷰</b> 위주로 블로그를 진행하신 분이라면 더욱 더 말이죠
                오늘 포스팅은, 어떻게 하면 객관적이고 좋은 <b>리뷰</b>를... </description>
            <bloggername>건짱의 Best Drawing World2</bloggername>
            <bloggerlink>http://blog.naver.com/yoonbitgaram</bloggerlink>
            <postdate>20161208</postdate>
        </item>
        ...
    </channel>
</rss>
~~~

## 기능 구현 사항
- [X] UI Controller 및 RequestDTO, ResponseDTO 구현
- [X] Feign Kakao(API 호출 용도)
  - [ ] 1순위 Kakao, 3-Try 후 성공 응답을 받지 못하면 2순위 Naver로 호출
  - [X] sort의 default 값은 accuracy
  - [X] default page는 1, size는 10
- [X] 검색 Entity는 키워드와 횟수를 가진다.
- [X] 검색 시 검색 결과를 호출하며 키워드 및 Entity를 저장한다. 
- [X] 인기검색어 조회 기능
- [X] 기능별 인수테스트 작성
- [X] 에러메세지 상수화 적용

## 회고
- 설계/개발/테스트에 대한 적절한 리소스 배분이 이루어져야 하는데, 설계에 치중되어 그러지 못했다.
- 가장 하고 싶었던 DIP 를 적용하고 싶었는데 그러지 못했습니다.
  - 블로그 검색하는 Interface 를 구현 후
  - 각 도메인은 해당 Interface를 사용하면, 직접적인 참조를 하지 않고(어떤 포털에서 검색을 하는지 모르고)
  - 결과를 받아 처리할 수 있는 것을 처음 목표를 두었지만,
  - 시간적인 리소스 배분을 적절히 하지 못했습니다.
- 그러다보니 요구사항의 기능을 구현하는데 급급했습니다.
- 아쉬웠지만 어떤 점이 부족한지, 앞으로 처음부터 설계하는 프로젝트에선 무엇을 더 집중할지 느낀 과제였습니다.
