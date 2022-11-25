const tabs = document.querySelectorAll('[data-tab-target]');
const tabsContents = document.querySelectorAll('[data-tab-content]');

tabs.forEach(tab => {
	tab.addEventListener('click', () => {
		const target = document.querySelector(tab.dataset.tabTarget);
		tabsContents.forEach(tabsContents => tabsContents.classList.remove('active'));
		tabs.forEach(tab => tab.classList.remove('active'));
		target.classList.add('active');
		tab.classList.add('active');
	});
});

window.onscroll = function () {
	if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
		document.getElementById("navbar").style.top = "0";
	} else {
		document.getElementById("navbar").style.top = "-50px";
	}
};