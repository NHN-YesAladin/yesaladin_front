function processPayment(orderResp) {
  let randomON = `0000_${new Date().getDate()}_${new Date().getHours()}_${new Date().getMinutes()}_${new Date().getMilliseconds()}`;
  orderResp["orderId"] = 12421345325;
  orderResp["orderNumber"] = randomON;
  let realOrderName = "테스트 주문 외 4권";
  orderResp["orderName"] = encodeURI(realOrderName);
  orderResp["totalAmount"] = 100000;
  orderResp["shippingFee"] = 3000;
  orderResp["wrappingFee"] = 2000;

  let orderId = orderResp["orderId"];
  let orderNumber = orderResp["orderNumber"];
  let orderName = orderResp["orderName"];
  let totalAmount = orderResp["totalAmount"] + orderResp["shippingFee"]
      + orderResp["wrappingFee"];
  // console.log(decodeURI(orderName))

  let successUrl = `${FRONT_SERVER}/payments/order-pay`;
  let failUrl = `${FRONT_SERVER}/payments/fail?o-num=${orderNumber}&o-name=${orderName}&o-amount=${totalAmount}`;

  // 토스 페이먼츠 결제 시퀀스
  tossLink(totalAmount, orderNumber, realOrderName, successUrl, failUrl);
}

function tossLink(totalAmount, orderNumber, orderName, successUrl, failUrl) {
  tossPayments.requestPayment('카드', { // 결제 수단 파라미터
    // 결제 정보 파라미터
    amount: totalAmount,
    orderId: orderNumber,
    orderName: orderName,
    successUrl: successUrl,
    failUrl: failUrl,
  })
  .then(resp => {
    let data = resp.json;
    console.log(data)
  })
  .catch(function (error) {
    writeContentToAlert(error.message);
    console.error(error);
  });
}
