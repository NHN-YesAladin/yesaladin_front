function addEventListenerToCouponDuration() {
  const couponDurationRadioList = document.querySelectorAll(
      ".coupon-duration-type-radio");
  const couponDurationInput = document.querySelector("#coupon-duration-input");
  const couponDurationStartDateInput = document.querySelector(
      "#coupon-duration-start-date-input");
  const couponDurationEndDateInput = document.querySelector(
      "#coupon-duration-end-date-input");
  const couponDurationTypeDurationRadio = document.querySelector(
      "#coupon-duration-type-duration");
  const couponDurationTypeDateRadio = document.querySelector(
      "#coupon-duration-type-date");
  couponDurationRadioList.forEach(
      radio => radio.addEventListener("click", () => {
        couponDurationInput.disabled = true;
        couponDurationStartDateInput.disabled = true;
        couponDurationEndDateInput.disabled = true;

        if (couponDurationTypeDurationRadio.checked) {
          couponDurationInput.disabled = false;
        } else if (couponDurationTypeDateRadio.checked) {
          couponDurationStartDateInput.disabled = false;
          couponDurationEndDateInput.disabled = false;
        }
      }));
}

function addEventListenerToCouponType() {
  const couponTypeRadioList = document.querySelectorAll(".coupon-type-radio")
  const couponTypePointRadio = document.querySelector("#coupon-type-point");
  const couponDiscountTypeSelect = document.querySelector(
      "#coupon-discount-type-select");
  const couponTypeRateRadio = document.querySelector("#coupon-type-fix-rate");
  const couponMaxDiscountPriceDiv = document.querySelector(
      "#coupon-max-discount-price-div");
  const couponMinOrderPriceDiv = document.querySelector(
      "#coupon-min-order-price-div");
  couponTypeRadioList.forEach(radio => radio.addEventListener("click", () => {
    couponDiscountTypeSelect.disabled = couponTypePointRadio.checked;
    couponMinOrderPriceDiv.style.display = "";
    couponMaxDiscountPriceDiv.style.display = "none";
    couponMinOrderPriceDiv.disabled = false;
    couponMaxDiscountPriceDiv.disabled = true;
    if (couponTypePointRadio.checked) {
      couponMinOrderPriceDiv.style.display = "none";
      couponMinOrderPriceDiv.disabled = true;
      couponMaxDiscountPriceDiv.style.display = "none";
      couponMaxDiscountPriceDiv.disabled = true;
    } else if (couponTypeRateRadio.checked) {
      couponMaxDiscountPriceDiv.style.display = "";
      couponMaxDiscountPriceDiv.disabled = false;
    }
  }));
}

function addEventListenerToCouponQuantity() {
  const unlimitedQuantityCheckbox = document.querySelector(
      "#coupon-unlimited-quantity-check");
  const quantityInput = document.querySelector("#coupon-quantity-input");
  unlimitedQuantityCheckbox.addEventListener("click", () => {
    quantityInput.disabled = unlimitedQuantityCheckbox.checked;
  })
}

(function init() {
  addEventListenerToCouponQuantity();
  addEventListenerToCouponType();
  addEventListenerToCouponDuration();
})();
