let githubLoginBtn = document.getElementById('github')
let kakaoLoginBtn = document.getElementById('kakao')

githubLoginBtn.addEventListener("click", () => {
  $.ajax({
    type: "get",
    async: true,
    url: "/oauth/redirect-url",
    data: {"oauthProvider": "github"},
    success: function (result) {
      location.href = result;
    }
  })
})

kakaoLoginBtn.addEventListener("click", () => {
  $.ajax({
    type: "get",
    async: true,
    url: "/oauth/redirect-url",
    data: {"oauthProvider": "kakao"},
    success: function (result) {
      location.href = result;
    }
  })
})