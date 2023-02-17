let FRONT_SERVER;
let SHOP_SERVER;
let pointCouponCode;
const SOCKET_PATH = '/ws';

function initConnectionInfo() {
  FRONT_SERVER = document.querySelector(`#front-server-url`).textContent;
  SHOP_SERVER = document.querySelector("#shop-server-url").textContent
}

function saveCouponCode(couponCode) {
  if (couponCode) {
    pointCouponCode = couponCode;
  }
}

function showLoadingScreen() {
  const loadingScreen = document.querySelector('#loading');

  loadingScreen.style.display = 'flex';
}

function hideLoadingScreen() {
  const loadingScreen = document.querySelector('#loading');

  loadingScreen.style.display = 'none';
}

function openSocket(requestId) {
  const socket = new SockJS(`${SHOP_SERVER}${SOCKET_PATH}`);
  const stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    stompClient.subscribe(`/ws/topic/coupon/use/result/${requestId}`,
        function (data) {
          const parsedBody = JSON.parse(data.body);
          const message = parsedBody.success ? parsedBody.message
              : '이 쿠폰은 사용할 수 없습니다.'
          alert(message);
          hideLoadingScreen();
          stompClient.disconnect();
          location.reload();
        });
    stompClient.send(`/shop/coupon/use/result/connect/${requestId}`, {},
        'CONNECT');
  });
}

async function requestUseCoupon() {
  const response = await fetch(`${FRONT_SERVER}/member-coupons/usage`,
      {
        method: 'POST',
        body: JSON.stringify({couponCodes: [pointCouponCode]}),
        headers: {'Content-Type': 'application/json'}
      });
  const parsedResponse = await response.json();
  if (response.status !== 200) {
    alert(parsedResponse.errorMessages);
  }
  const requestId = parsedResponse.data.requestId;
  showLoadingScreen();
  openSocket(requestId);
}

function addEventListenerToUsePointCouponBtn() {
  const useCouponBtn = document.querySelector('.use-point-coupon-btn');
  const modal = document.querySelector('.modal');
  useCouponBtn.addEventListener('click', async (e) => {
    await requestUseCoupon();
  })
}

(() => {
  initConnectionInfo();
  addEventListenerToUsePointCouponBtn();
})();