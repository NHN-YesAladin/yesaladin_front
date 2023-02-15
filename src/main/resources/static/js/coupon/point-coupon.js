let FRONT_SERVER;

function initConnectionInfo() {
  FRONT_SERVER = document.querySelector(`#shop-server-url`).textContent;
}

function addEventListenerToUsePointCouponBtn() {
  const useCouponBtn = document.querySelector('.use-point-coupon-btn');
  useCouponBtn.addEventListener('click', async (e) => {
    await requestCouponUse(e.target);
  })
}