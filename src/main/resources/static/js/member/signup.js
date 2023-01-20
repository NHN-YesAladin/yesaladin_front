function nicknameCheck() {
  let nicknameVal = document.getElementById('nickname').value;
  let nicknameRegex = /^.*(?=.*[a-z])(?=.*[a-z\d])(?=.{2,8}).*$/;
  let emptyRegex = /\s/g;

  if (nicknameRegex.test(nicknameVal) && !emptyRegex.test(nicknameVal)) {
    console.log(nicknameVal);
    const url = `/checkNickname/${nicknameVal}`;
    fetch(url, {
      method: "GET"
    }).then((resp) => {
      console.log(resp);
      if (resp.ok) {
        alert('사용 가능한 닉네임 입니다.');
      } else {
        alert('이미 사용 중인 닉네임 입니다.');
      }
    });
  } else {
    alert('닉네임은 영문으로 2자 이상 15자 이하만 가능합니다.');
  }
}

function loginIdCheck() {
  let loginIdVal = document.getElementById('loginId').value;
  let loginIdRegex = /^.*(?=.*[a-z])(?=.*\d)(?=^.{8,15}).*$/;
  let emptyRegex = /\s/g;

  if (loginIdRegex.test(loginIdVal) && !emptyRegex.test(loginIdVal)) {
    console.log(loginIdVal);
    const url = `/checkLoginId/${loginIdVal}`;
    fetch(url, {
      method: "GET"
    }).then((resp) => {
      console.log(resp);
      if (resp.ok) {
        alert('사용 가능한 아이디 입니다.');
      } else {
        alert('이미 사용 중인 아이디 입니다.');
      }
    });
  } else {
    alert('로그인 아이디는 영문(필수)과 숫자(옵션) 순서로 8자 이상 15자 이하만 가능 합니다');
  }
}

function emailCheck() {
  let emailVal = document.getElementById('email').value;
  let emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
  let emptyRegex = /\s/g;

  if (emailRegex.test(emailVal) && !emptyRegex.test(emailVal)) {
    console.log(emailVal);
    const url = `/checkEmail/${emailVal}`;
    fetch(url, {
      method: "GET"
    }).then((resp) => {
      console.log(resp);
      if (resp.ok) {
        alert('사용 가능한 이메일 입니다.');
      } else {
        alert('이미 사용 중인 이메일 입니다.');
      }
    });
  } else {
    alert('이메일 양식에 맞춰 입력해주세요.');
  }
}

function phoneCheck() {
  let phoneVal = document.getElementById('phone').value;
  let phoneRegex = /^\d{11}$/;
  let emptyRegex = /\s/g;

  if (phoneRegex.test(phoneVal) && !emptyRegex.test(phoneVal)) {
    console.log(phoneVal);
    const url = `/checkPhone/${phoneVal}`;
    fetch(url, {
      method: "GET"
    }).then((resp) => {
      console.log(resp);
      if (resp.ok) {
        alert('사용 가능한 휴대폰 번호 입니다.');
      } else {
        alert('이미 사용 중인 휴대폰 번호 입니다.');
      }
    });
  } else {
    alert('휴대폰 번호를 - 없이 11자 입력해주세요.');
  }
}