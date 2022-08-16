# 1. 빌드 배포 정리

## 목차

1. [기술 스택](#기술-스택)
2. [빌드 및 배포](#빌드-및-배포)


## 기술 스택

1. 이슈 관리 : Jira
2. 형상 관리 : Gitlab
3. 빌드/배포 관리 : 수동배포-정수빈
4. 커뮤니케이션 : MatterMost, Notion, Discord, Webex
5. 개발 환경
    1) 운영체제 Window10
    2) IDE 
         - VSCode `1.69.2`
         - InteliJ `2022.1.3.`
    3) 데이터베이스 : MySQL `8.0.29`
    4) 서버 : AWS EC2
         - Ubuntu `20.04 LTS`
         - Docker `20.10.17`
         - Openvidu `2.22.0`
         - Nginx `1.18.0(ubuntu)`
         - puTTY `0.77`
         - Https `TSL(SSL)1.0`        
6. 세부사항
    1) Frontend
        - lang: HTML5, CSS3, JAVASCRIPT, Node.js `16.16.0` 
        - Framework: Vue.js (Vue3) 
        - Style : Sass 
        - Http: Axios 
        - Formatter : eslint + prettier 
        - Router : vue-router 
        - State management tool : Vuex4
    2) Backend
        - Java open-JDK zulu `8.33.0.1` 
        - SpringBoot 
        - Gradle `7.5` 
        - Lombok `1.18.24` 
        - JPA `1.0` 
        - Hibernate (ORM프레임워크) 
        - Swagger



## 빌드 및 배포
### 1. OPENVIDU 설치
📜 [공식 사이트](https://docs.openvidu.io/en/stable/deployment/ce/on-premises/#2-lets-encrypt-certificatep)
1) ROOT권한으로 설치 진행
    ```
    sudo su
    ```
2) OPT 폴더로 이동
    ```
    cd /opt
    ```
3) openvidu 설치
    ```
    curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash
    ```
4) 다운로드 완료후

    ![image](/uploads/15f38b26ada0f8ab98175d77ab05dd8a/image.png)

5) openvidu env(환경설정)파일 수정
    ```
    cd /opt/openvidu
    vim .env
    ```
    ![image](/uploads/e88fa6781a610d48e41125fe57436113/image.png)

    (i: 수정, ctrl+c:작성종료, :wq! 저장종료)

    🗝 **openvidu 관리 명령어** 
        ![image](/uploads/b9407eda690336a2ea247f0a4cfa3714/image.png)

6) 외부 공격을 피하기 위해 닫힌 포트 열기
    ```
    ufw allow ssh
    ```
    ![image](/uploads/8be027c932132286058b0b21edb8854e/image.png)

7) 현재 사용중인 포트 확인                 
    ```
    ufw status
    ```
    ![image](/uploads/8ca39911690fa3904b49d632cbe15286/image.png)



### 2. nginx 설치
```
apt-get update
apt-get install nginx
```




### 3. certbot 설치
1. 공통 certbot 설치
    ```
    apt-get update
    apt-get install software-propeties-common
    add-apt-repository universe 
    apt-get update
    apt-get install certbot python3-certbot-nginx
    ```

2. nginx 세팅                         
    `conf` 파일이 있는지 확인하고, conf파일이 있는 경우 `your server block`에 파일 이름을 넣고 없는 경우 원하는 이름으로 생성한다.
    ```A
    vim /etc/nginx/sites-available/[your server block]
    ```


    간혹 `conf`파일 수정 중 `swo`, `swp`파일이 생성되는 경우가 있는데 무시해도 된다.

    (파일 수정중 강제종료로 중지시 생성된다.) (i: 수정, ctrl+c:작성 종료, :wq! 저장종료)
    ![image](/uploads/1659991a79b642f325295459662c0385/image.png)


    nginx 상태를 확인하고 ok, succse 상태이면 nginx 재시작
    ```
    nginx -t
    service nginx reload
    ```


3. ssl인증획득하기
    example.com을 자신의 도메인을 넣어준다.

    ```
    certbot --nginx -d example.com -d www.example.com
    ```


    성공 화면 (redirect를 허용하고 싶으면 2번 선택)

    ![image](/uploads/6fcd2510760b7e594a323d7400796485/image.png)
    ![image](/uploads/493355fa3bc2e8a5fd6524d5066d596d/image.png)    



### 2. mysql 설치
1. mysql 설치하기
    ```
    apt-get install mysql-server
    ```

2. mysql 버전 확인
    ```
    mysql —version
    ```

3. mysql 접근 공유 해주기

    (1) `mysqld.cnf` 파일 수정하기 (위 경로는 상이할수 있음)
        ```
        vim /etc/mysql/mysql.conf.d/mysqld.cnf
        ```

    (2) bind-addrss 수정 (i: 수정, ctrl+c:작성종료, :wq! 저장종료)
    ![image](/uploads/5eb883b4e8fad7fe28e0b33207b88b66/image.png)

4. mysql 접속하기
    비밀번호 작성
    ```
    mysql -u [계정아이디] -p [비밀번호]
    ```

    mysql 접속 완료

    ![image](/uploads/f82d4c6d27fad18d40f3507bfcc3c6f1/image.png)


5. root 비밀번호 변경
    ```
    ALTER user '[계정아이디]'@'localhost' IDENTIFIED WITH mysql_native_password BY [변경 비밀번호];
    ```

6. 개정 생성 및 비밀번호 생성
    ```
    create user [계정아이디]@localhost identified by [비밀번호];
    ```


### 3. backend 배포

1. backend jar 파일 만들기

    ![image](/uploads/b97bbf6ffbab8762a5696c7b4bc8de01/image.png)

2. 만들어진 jar파일 경로 

    ![image](/uploads/6c0271034d97b96383494ab15019d043/image.png)


3. EC2 서버에 만들어진 jar 파일 옮기기


4. Dockerfile 설정(jar파일과 같은위치)

    Dockerfile은 다음과 같이 작성한다.

    (1) FROM 이미지: 태그 - 사용
    ```
    FROM openjdk:8-jdk-alpine
    ```

    (2) ARG JAR_FILE – 어플리케이션의 실행파일 연결
    ```
    ARG JAR_FILE=banggawawo-0.0.1-SNAPSHOT.jar
    ```

    (3) COPY 로컬 파일 또는 디렉토리를 Docker 이미지로 복사하는 경우 사용
    (${JAR_FILE}을 banggwawoBack.jar이름으로 복사)
    ```
    COPY ${JAR_FILE} banggwawoBack.jar
    ```
    (4) ENTRYPOINT 지정 명령어 수행
    ```
    ENTRYPOINT ["java", "-jar","banggwawoBack.jar"]
    ```

5. back dockerfile이 있는 디렉토리 가기
    ```
    cd /home/ubuntu/backend
    ```

6. 컨테이너에서 실행할 이미지 생성(backendt 이미지 생성)
    ```
    docker build –t backendt . 
    ```

7. 이미지 실행 컨테이너 생성

    ```
    docker run -p 8081:8081 --name backendt –v

    /opt/openvidu/recordings:/opt/openvidu/recordings backendt
    ```

8. 실행

    ```
    docker start backendt
    ```

---
🧨 이미 실행 중일때 update가 필요하다면
1. `docker stop backendt` (컨테이너 정지)
2. `docker rm backendt` (켄테이너 삭제)
3. `docker image rm backendt` (이미지 삭제)
4. 위 내용 작성 후 build부터 재실행





### 4. Frontend 배포
