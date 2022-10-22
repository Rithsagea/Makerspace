let fileCount = 1;

window.onload = function () {
	console.log("Hello World!");

	const projectNameInput = document.getElementById('projectName');
	const sourceInput = document.getElementById('source');

	document.getElementById('button-submit').addEventListener('click', (event) => {
		// event.preventDefault();
		console.log(`Project Name: ${projectNameInput.value}`);
		console.log(`Source: ${sourceInput.value}`);
	});

	const files = document.getElementById('file-table');

	document.getElementById('button-add-file').addEventListener('click', event => {
		console.log("Added a file!");

		var row = files.insertRow(-1);
		var fileCell = row.insertCell(0);
		var fileNameCell = row.insertCell(1);
		var fileDescriptionCell = row.insertCell(2);

		fileCell.innerHTML = '<input type="file" id="file" />';
		fileNameCell.innerHTML = '<input type="text" id="fileName" />';
		fileDescriptionCell.innerHTML = '<input type="text" id="fileDescription" />';

		row.value = 'test';
	});
};
