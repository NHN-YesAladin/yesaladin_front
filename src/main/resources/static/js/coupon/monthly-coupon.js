let SHOP_SERVER;
let FRONT_SERVER;
let SOCKET_SERVER;
const SOCKET_PATH = '/ws';

function initConnectionInfo() {
  SHOP_SERVER = document.querySelector("#shop-server-url").textContent
  FRONT_SERVER = document.querySelector("#front-server-url").textContent
  SOCKET_SERVER = document.querySelector("#socket-server-url").textContent
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
  const openDateTime = target.dataset.opendatetime;

  const requestDateTime = new Date();
  const openTime = new Date(openDateTime);
  console.log("openTime:" + openTime);
  console.log("requestDateTime: " + requestDateTime);

  if (openTime > requestDateTime) {
    alert("오픈 시간을 기다려주세요.");
    hideLoadingScreen();
    return;
  }

  const requestBody = {triggerTypeCode, couponId, requestDateTime};

  const response = await fetch(`${FRONT_SERVER}/member-coupons`, {
    method: 'POST',
    body: JSON.stringify(requestBody),
    headers: {'Content-Type': 'application/json'}
  });

  const parsedResponse = await response.json();
  if (response.status !== 200) {
    alert(parsedResponse.errorMessages[0])
    hideLoadingScreen();
    return;
  }
  openSocketAndAddEventListener(parsedResponse.data.requestId);
}

function openSocketAndAddEventListener(requestId) {
  const socket = new SockJS(`${SOCKET_SERVER}${SOCKET_PATH}`);
  const stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    stompClient.subscribe(`/ws/topic/coupon/give/result/${requestId}`,
        function (data) {
          const parsedBody = JSON.parse(data.body);
          alert(parsedBody.message);
          hideLoadingScreen();
          stompClient.disconnect();
        });
    stompClient.send(`/shop/coupon/give/result/connect/${requestId}`, {},
        'CONNECT');
  });
}

function addEventListenerToIssueBtn() {
  const issueBtn = document.querySelector('#issue-btn')
  issueBtn.addEventListener('click', async (e) => {
    showLoadingScreen();
    await requestCouponGive(e.target);
  });
}

(function init() {
  initConnectionInfo()
  addEventListenerToIssueBtn();
})()