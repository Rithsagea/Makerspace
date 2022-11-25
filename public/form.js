let fileCount = 1;

function addRow() {
	let table = document.getElementById('files');
	let row = table.insertRow();

	$.get('/views/partials/form-file.ejs').then((str, status, xhr) => {
		row.innerHTML = ejs.render(str, { num: ++fileCount });
		console.log(row);
	});
}

$(document).ready(() => {
	$('button#add-file').click(addRow);

	$('form').submit((event) => {
		event.preventDefault();
		var formData = new FormData($('#form')[0]);

		var fileNames = formData.get('file-name');
		var fileObjects = formData.get('file-upload');

		var data = {
			'project-name': formData.get('project-name'),
			'project-source': formData.get('project-source'),
			'attributions': formData.get('attributions'),
			'files': []
		};

		var rows = $(files)[0].rows;
		for (let i = 1; i <= rows.length; i++) {
			console.log(rows[i]);
			// data.files.push({

			// });
		}

		const request = new XMLHttpRequest();
		request.open("POST", "/api/form");
		request.setRequestHeader("Content-Type", "application/json");
		request.onreadystatechange = () => {
			if (request.readyState === 4 && request.status === 200) {
				console.log(`Response: ${request.responseText}`);
			}
		};
		console.log(JSON.stringify(data));
		request.send(JSON.stringify(data));
	});
});