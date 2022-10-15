window.onload = function() {
	console.log("Hello World!");

	const form = document.getElementById('form');

	form.addEventListener('submit', (event) => {
		event.preventDefault();
		console.log(`Project Name: ${form.elements['projectName'].value}`);
		console.log(`Email: ${form.elements['email'].value}`);
	});
};
