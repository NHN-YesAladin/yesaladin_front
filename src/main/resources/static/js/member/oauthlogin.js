let githubLoginBtn = document.getElementById('github')
let naverLoginBtn = document.getElementById('naver')
let kakaoLoginBtn = document.getElementById('kakao')

githubLoginBtn.addEventListener("click", () => {
  $.ajax({
    type: "get",
    async: true,
    url: "/oauth/redirect-url",
    data: {"oauthProvider": "github"},
    success: function (result) {
      console.log("github redirect test")
      location.href = result;
    }
  })
})

naverLoginBtn.addEventListener("click", () => {
  // $.ajax({
  //   type: "get",
  //   async: true,
  //   url: "/oauth/redirect-url",
  //   data: {"oauthProvider": "naver"},
  //   success: function (result) {
  //     console.log("naver redirect test")
  //     location.href = result;
  //   }
  // })
  console.log("naver redirect test")
})

kakaoLoginBtn.addEventListener("click", () => {
  // $.ajax({
  //   type: "get",
  //   async: true,
  //   url: "/oauth/redirect-url",
  //   data: {"oauthProvider": "kakao"},
  //   success: function (result) {
  //     console.log("kakao redirect test")
  //     location.href = result;
  //   }
  // })
  console.log("kakao redirect test")
})