let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            //function(){}을 안쓴 이유는 ()=>{} this를 바인딩하기 위해서... 먼말이노
            this.save();
        });

        $("#btn-login").on("click", () => {
            //function(){}을 안쓴 이유는 ()=>{} this를 바인딩하기 위해서... 먼말이노
            this.login();
        });
    },

    save: function () {
        alert("user의 save 함수 호출됨");
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        //console.log(data); -> f12 누르면 console에서 확인할 수 있으심

        //ajax 호출시 default가 비동기 호출
        //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 java object로 변환해줌. => data Type 안해줘도 해줌 개신기
        $.ajax({
            type: "POST",
            url: "http://localhost:8000/blog/api/user",
            data: JSON.stringify(data), //http body 데이터 => MIME TYPE이 필요
            contentType: "application/json", //body data의 타입 보내는 data의 Type
            dataType: "json" //응답 : 기본적으로 String인데 생겨먹은게 json -> 그러면 알아서 javascript object로 변경해줌
            //서버에서 어떤 data Type을 받을 건지를 의미
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            location.href = "/blog";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    },

    login: function () {
        alert("user의 save 함수 호출됨");
        let data = {
            username: $("#username").val(),
            password: $("#password").val()
        }
        $.ajax({
            type: "POST",
            url: "http://localhost:8000/blog/api/user/login",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json"
        }).done(function(resp){
            alert("로그인이 완료되었습니다.");
            console.log(resp);
            location.href = "/blog";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();