# URI 와 URL 의 차이가 

- URL(Uniform Resource Locator)은 자원이 실제로 존재하는 위치를 가리키며, URI(Uniform Resource Identifier)는 자원의 위치뿐만 아니라 자원에 대한 고유 식별자로서 URL을 의미를 포함한다.

```
http://localhost:8080/asdf?key=value
```

위의 예시에서 마찬가지로 http://localhost:8080/asdf 까지는 자원의 실제 위치를 나타내기 때문에 URL이라고 할 수 있으며, 뒤의 쿼리스트링 식별자(?key=value)를 포함하여 URI라고 볼 수 있다.

[원본 URL](https://github.com/keepinmindsh/templates_for_all/tree/main/01_java/java_nextstep)


# P0001 로그인 Form에서 요청을 받은후, 각각의 파라미터를 DTO에 담고, 담은 파라미터 결과는 응답을 통해 Json 형식으로 반환하기

- DTO의 명칭은
    - LoginUserDTO로 만든다.
- 모든 코드는 순수 자바 코드를 활용하고 별도의 Library를 사용하지 않는다.
    - Json 라이브러리를 사용하지 않고 직접 구현한다.
- 모든 코드는 본인의 github 계정에 생성한다.
- 샘플 git 경로
    - https://drive.google.com/drive/folders/1E07_X9PHPfsLn51jAWlSC0SNs9bE9Wy0?usp=sharing
        - tar로 압축된 파일을 풀어서 프로젝트로 사용할 것

- 압축 해제 방법
```shell

# tar 를 이용해서 압축 했음. 


# 맥에서 압축 해제 방법 
$ tar -xvf {파일명}.tgz

```

# P0002 PostMan을 이용해서 POST, GET, DELETE, PUT 호출 시 각각의 호출 방식에 맞게 동작하는지 확인하기 

- POST의 경우, Request Body에서 값을 파싱해서 처리해야한다. 요청한 파라미터 값을 그대로 응답 Json으로 받는다. 
- GET의 경우, Query String을 통해서 값을 파싱해서 처리해야 한다. 
- GET의 경우, .html 로 들어오는 경우에는 화면을, 그게 아닐 경우에는 Query String으로 대응한다. 
- DELETE의 경우, "삭제되었습니다"라는 메세지를 반환할 것 
- PUT의 경우, 전달된 파라미터를 그대로 응답 값에 Json으로 반환할 것 

[원본 URL](https://github.com/keepinmindsh/tech-education/blob/7cdba0f28a9eb76f27c0953219632a1ae2bf86d8/99_problem/README.md)
