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
  const couponTypePointRadio = document.querySelector("#POINT");
  const couponDiscountTypeSelect = document.querySelector(
      "#coupon-discount-type-select");
  const couponTypeRateRadio = document.querySelector("#FIXED_RATE");
  const couponMaxDiscountPriceDiv = document.querySelector(
      "#coupon-max-discount-price-div");
  const couponMinOrderPriceDiv = document.querySelector(
      "#coupon-min-order-price-div");
  const couponBoundSelectDiv = document.querySelector(
      "#coupon-bound-select-div");
  couponTypeRadioList.forEach(radio => radio.addEventListener("click", () => {
    couponDiscountTypeSelect.disabled = couponTypePointRadio.checked;
    couponBoundSelectDiv.style.display = "";
    couponMinOrderPriceDiv.style.display = "";
    couponMaxDiscountPriceDiv.style.display = "none";
    couponMinOrderPriceDiv.querySelector("input").disabled = false;
    couponMaxDiscountPriceDiv.querySelector("input").disabled = true;
    couponBoundSelectDiv.querySelector("select").disabled = false;
    if (couponTypePointRadio.checked) {
      couponBoundSelectDiv.style.display = "none";
      couponBoundSelectDiv.querySelector("select").disabled = true;
      couponMinOrderPriceDiv.style.display = "none";
      couponMinOrderPriceDiv.querySelector("input").disabled = true;
      couponMaxDiscountPriceDiv.style.display = "none";
      couponMaxDiscountPriceDiv.querySelector("input").disabled = true;
    } else if (couponTypeRateRadio.checked) {
      couponMaxDiscountPriceDiv.style.display = "";
      couponMaxDiscountPriceDiv.querySelector("input").disabled = false;
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
