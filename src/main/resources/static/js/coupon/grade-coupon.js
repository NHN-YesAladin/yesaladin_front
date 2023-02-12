let SHOP_SERVER;
let FRONT_SERVER;

function initConnectionInfo() {
  SHOP_SERVER = document.querySelector("#shop-server-url").textContent
  FRONT_SERVER = document.querySelector("#front-server-url").textContent
}

function showLoadingScreen(loadingScreen) {
  loadingScreen.style.display = 'flex'
  setTimeout(() => {
    loadingScreen.style.display = 'none'
  }, 3000);
}

async function requestCouponGive(target) {
  const triggerTypeCode = target.dataset.triggertypecode;
  const couponId = target.dataset.couponid;
  const requestBody = {triggerTypeCode, couponId};

  console.log(requestBody)
  const response = await fetch(`${FRONT_SERVER}/member-coupons`,
      {
        method: 'POST',
        body: JSON.stringify(requestBody),
        headers: {'Content-Type': 'application/json'}
      });
}

function addEventListenerToIssueBtn() {
  const loadingScreen = document.querySelector('#loading');
  const issueBtn = document.querySelectorAll('.issue-btn')
  issueBtn.forEach(btn => {
    btn.addEventListener('click', async (e) => {
      showLoadingScreen(loadingScreen);
      await requestCouponGive(e.target);
    })
  })
}

(function init() {
  initConnectionInfo()
  addEventListenerToIssueBtn();
})()