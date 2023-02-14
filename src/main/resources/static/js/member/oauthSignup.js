function nicknameCheck() {
  let nicknameCheckBtn = document.getElementById('nicknameCheckBtn');
  let nicknameInput = document.getElementById('nickname');
  let nicknameVal = nicknameInput.value;
  let nicknameRegex = /^.*(?=.*[a-z])(?=.*[a-z\d])(?=.{2,8}).*$/;
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
    alert('닉네임은 영문으로 2자 이상 15자 이하만 가능합니다.');
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
  let createAccountBtn = document.getElementById('createAccountBtn');
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
          createAccountBtn.disabled = false;
        } else {
          alert('이미 사용 중인 휴대폰 번호 입니다.');
        }
      })
    });
  } else {
    alert('휴대폰 번호를 - 없이 11자 입력해주세요.');
  }
}