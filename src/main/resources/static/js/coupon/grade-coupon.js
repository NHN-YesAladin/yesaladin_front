let SHOP_SERVER;
let FRONT_SERVER;
const SOCKET_PATH = '/ws';

function initConnectionInfo() {
  SHOP_SERVER = document.querySelector("#shop-server-url").textContent
  FRONT_SERVER = document.querySelector("#front-server-url").textContent
}

function showLoadingScreen() {
  const loadingScreen = document.querySelector('#loading');

  loadingScreen.style.display = 'flex';
}

function hideLoadingScreen() {
  const loadingScreen = document.querySelector('#loading');

  loadingScreen.style.display = 'none';
}

async function requestCouponGive(target) {
  const triggerTypeCode = target.dataset.triggertypecode;
  const couponId = target.dataset.couponid;
  const requestBody = {triggerTypeCode, couponId};

  const response = await fetch(`${FRONT_SERVER}/member-coupons`, {
    method: 'POST',
    body: JSON.stringify(requestBody),
    headers: {'Content-Type': 'application/json'}
  });
  const parsedResponse = await response.json();
  if (response.status !== 200) {
    alert(parsedResponse.errorMessages[0])
    hideLoadingScreen()
    return;
  }
  openSocketAndAddEventListener(parsedResponse.data.requestId);
}

function openSocketAndAddEventListener(requestId) {
  const socket = new SockJS(`${SHOP_SERVER}${SOCKET_PATH}`);
  const stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    stompClient.subscribe(`/ws/topic/coupon/give/result/${requestId}`,
        function (data) {
          const parsedBody = JSON.parse(data.body);
          alert(parsedBody.message);
          hideLoadingScreen();
          stompClient.disconnect();
        });
  });

}

function addEventListenerToIssueBtn() {
  const issueBtn = document.querySelectorAll('.issue-btn')
  issueBtn.forEach(btn => {
    btn.addEventListener('click', async (e) => {
      showLoadingScreen();
      await requestCouponGive(e.target);
    });
  });
}

(function init() {
  initConnectionInfo()
  addEventListenerToIssueBtn();
})()