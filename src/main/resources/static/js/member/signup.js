function nicknameCheck() {
  let nicknameCheckBtn = document.getElementById('nicknameCheckBtn');
  let nicknameInput = document.getElementById('nickname');
  let nicknameVal = nicknameInput.value;
  let nicknameRegex = /^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$/;
  let emptyRegex = /\s/g;

  if (nicknameRegex.test(nicknameVal) && !emptyRegex.test(nicknameVal)) {
    console.log(nicknameVal);
    const url = `/checkNickname/${nicknameVal}`;
    fetch(url, {
      Accept: "application/json",
      method: "GET"
    }).then((resp) => {
      resp.json().then(resp => {
        if (!resp.result) {
          alert('사용 가능한 닉네임 입니다.');
          nicknameInput.readOnly = true;
          nicknameCheckBtn.disabled = true;
        } else {
          alert('이미 사용 중인 닉네임 입니다.');
        }
      })
    });
  } else {
    alert('숫자, 영어, 한국어와 언더스코어를 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.');
  }
}

function loginIdCheck() {
  let loginIdCheckBtn = document.getElementById('loginIdCheckBtn');
  let loginIdInput = document.getElementById('loginId');
  let loginIdVal = loginIdInput.value;
  let loginIdRegex = /^[a-zA-Z0-9]{8,15}$/;
  let emptyRegex = /\s/g;

  if (loginIdRegex.test(loginIdVal) && !emptyRegex.test(loginIdVal)) {
    console.log(loginIdVal);
    const url = `/checkLoginId/${loginIdVal}`;
    fetch(url, {
      Accept: "application/json",
      method: "GET"
    }).then((resp) => {
      resp.json().then(resp => {
        if (!resp.result) {
          alert('사용 가능한 아이디 입니다.');
          loginIdInput.readOnly = true;
          loginIdCheckBtn.disabled = true;
        } else {
          alert('이미 사용 중인 아이디 입니다.');
        }
      })
    });
  } else {
    alert('아이디는 영문 또는 숫자로 8자 이상 15자 이하만 가능 합니다.');
  }
}

function emailCheck() {
  let emailCheckBtn = document.getElementById('emailCheckBtn');
  let emailInput = document.getElementById('email');
  let emailVal = emailInput.value;
  let emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
  let emptyRegex = /\s/g;

  if (emailRegex.test(emailVal) && !emptyRegex.test(emailVal)) {
    console.log(emailVal);
    const url = `/checkEmail/${emailVal}`;
    fetch(url, {
      Accept: "application/json",
      method: "GET"
    }).then((resp) => {
      resp.json().then(resp => {
        if (!resp.result) {
          alert('사용 가능한 이메일 입니다.');
          emailInput.readOnly = true;
          emailCheckBtn.disabled = true;
        } else {
          alert('이미 사용 중인 이메일 입니다.');
        }
      })
    });
  } else {
    alert('이메일 양식에 맞춰 입력해주세요.');
  }
}

function phoneCheck() {
  let phoneCheckBtn = document.getElementById('phoneCheckBtn');
  let phoneInput = document.getElementById('phone');
  let phoneVal = phoneInput.value;
  let phoneRegex = /^\d{11}$/;
  let emptyRegex = /\s/g;

  if (phoneRegex.test(phoneVal) && !emptyRegex.test(phoneVal)) {
    console.log(phoneVal);
    const url = `/checkPhone/${phoneVal}`;
    fetch(url, {
      Accept: "application/json",
      method: "GET"
    }).then((resp) => {
      resp.json().then(resp => {
        if (!resp.result) {
          alert('사용 가능한 휴대폰 번호 입니다.');
          phoneInput.readOnly = true;
          phoneCheckBtn.disabled = true;
        } else {
          alert('이미 사용 중인 휴대폰 번호 입니다.');
        }
      })
    });
  } else {
    alert('휴대폰 번호를 - 없이 11자 입력해주세요.');
  }
}

function passwordCheckEvent() {
  let passwordCheckBtn = document.getElementById('passwordCheckBtn');
  let password = document.getElementById('password');
  let passwordVal = document.getElementById('password').value;
  let passwordCheck = document.getElementById('passwordCheck');
  let passwordCheckVal = document.getElementById('passwordCheck').value;

  let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/g
  let emptyRegex = /\s/g;

  if (passwordVal !== passwordCheckVal) {
    alert('Password가 일치하지 않습니다.');
  } else if (!passwordRegex.test(passwordVal) || emptyRegex.test(passwordVal)) {
    alert('Password는 최소 8자, 하나 이상의 문자와 하나의 숫자 및 하나의 특수 문자로 입력해주세요.');
  } else {
    console.log("ok");
    password.readOnly = true;
    passwordCheck.readOnly = true;
    passwordCheckBtn.disabl를ed = true;
  }
}

function finalCheckEvent() {
  let createAccountBtn = document.getElementById('createAccountBtn');
  let genderVal = document.getElementById('gender').value;
  let birthVal = document.getElementById('datepicker-icon-prepend').value;

  let emptyRegex = /\s/g;

  if (!emptyRegex.test(genderVal) && !emptyRegex.test(birthVal)) {
    createAccountBtn.disabled = false;
  } else {
    alert('성별과 생년월일을 선택해 주세요.');
  }
}

function againInputCheckEvent() {
  let createAccountBtn = document.getElementById('createAccountBtn');
  let passwordCheckBtn = document.getElementById('passwordCheckBtn');
  let password = document.getElementById('password');
  let passwordCheck = document.getElementById('passwordCheck');
  let phoneCheckBtn = document.getElementById('phoneCheckBtn');
  let phoneInput = document.getElementById('phone');
  let emailCheckBtn = document.getElementById('emailCheckBtn');
  let emailInput = document.getElementById('email');
  let loginIdCheckBtn = document.getElementById('loginIdCheckBtn');
  let loginIdInput = document.getElementById('loginId');
  let nicknameCheckBtn = document.getElementById('nicknameCheckBtn');
  let nicknameInput = document.getElementById('nickname');

  nicknameInput.readOnly = false;
  nicknameCheckBtn.disabled = false;
  loginIdInput.readOnly = false;
  loginIdCheckBtn.disabled = false;
  emailInput.readOnly = false;
  emailCheckBtn.disabled = false;
  phoneInput.readOnly = false;
  phoneCheckBtn.disabled = false;
  password.readOnly = false;
  passwordCheck.readOnly = false;
  passwordCheckBtn.disabled = false;
  createAccountBtn.disabled = true;
}