(function init() {
  const loadingScreen = document.querySelector('#loading');
  const issueBtn = document.querySelectorAll('.issue-btn')
  issueBtn.forEach(btn => {
    btn.addEventListener('click', () => {
      loadingScreen.style.display = 'flex'
      setTimeout(() => {
        loadingScreen.style.display = 'none'
      }, 3000);
    })
  })
})()