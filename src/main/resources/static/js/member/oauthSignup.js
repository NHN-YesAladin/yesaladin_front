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
    alert('숫자, 영어, 한국어와 언더스코어를 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.')
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
  let phoneRegex = /^01([0|1])\d{4}\d{4}$/;
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
    alert('휴대폰 번호를 - 없이 11자 입력해주세요. 앞 자리는 010 또는 011 양식입니다.');
  }
}

function checkBirthAndGender() {
  let finalCheckBtn = document.getElementById('finalCheckBtn');
  let genderVal = document.getElementById('gender').value;
  let birthVal = document.getElementById('datepicker-icon-prepend').value;

  let emptyRegex = /\s/g;

  if (!emptyRegex.test(genderVal) && !emptyRegex.test(birthVal)
      && genderVal !== '' && birthVal !== '') {
    finalCheckBtn.disabled = false;
  } else {
    alert('성별과 생년월일을 선택해 주세요.');
  }
}

function finalCheckEvent() {
  let createAccountBtn = document.getElementById('createAccountBtn');

  if (inputCheck()) {
    createAccountBtn.disabled = false;
  } else {
    alert('올바른 값을 모두 입력해주세요.');
  }
}

function againInputCheckEvent() {
  let createAccountBtn = document.getElementById('createAccountBtn');
  let phoneCheckBtn = document.getElementById('phoneCheckBtn');
  let phoneInput = document.getElementById('phone');
  let emailCheckBtn = document.getElementById('emailCheckBtn');
  let emailInput = document.getElementById('email');
  let nicknameCheckBtn = document.getElementById('nicknameCheckBtn');
  let nicknameInput = document.getElementById('nickname');

  nicknameInput.readOnly = false;
  nicknameCheckBtn.disabled = false;
  emailInput.readOnly = false;
  emailCheckBtn.disabled = false;
  phoneInput.readOnly = false;
  phoneCheckBtn.disabled = false;
  createAccountBtn.disabled = true;
}

function inputCheck() {
  let createAccountBtn = document.getElementById('createAccountBtn');
  let phoneCheckBtn = document.getElementById('phoneCheckBtn');
  let emailCheckBtn = document.getElementById('emailCheckBtn');
  let nicknameCheckBtn = document.getElementById('nicknameCheckBtn');
  return (createAccountBtn.disabled && phoneCheckBtn.disabled &&
      emailCheckBtn.disabled && nicknameCheckBtn.disabled)
}