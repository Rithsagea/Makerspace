let fileCount = 1;

function addRow() {
	let table = document.getElementById('files');
	let row = table.insertRow();

	$.get('/views/partials/form-file.ejs').then((str, status, xhr) => {
		row.innerHTML = ejs.render(str, { num: ++fileCount });
	});
}

async function uploadFile(file) {
	let formData = new FormData();
	formData.append('file', file);
	const result = await fetch('/api/file', {
		method: 'POST',
		body: formData
	});

	return result.text();
}

async function submitData(formData) {
	var data = {
		'project-name': formData.get('project-name'),
		'project-source': formData.get('project-source'),
		'attributions': formData.get('attributions'),
		'files': []
	};

	var rows = $(files)[0].rows;
	for (let i = 1; i < rows.length; i++) {
		let file = {};

		file['name'] = rows[i].querySelector(`input[name=file-name]`).value;
		file['description'] = rows[i].querySelector(`textarea[name=file-description]`).value;
		file['file'] = await uploadFile(rows[i].querySelector(`input[name=file-upload]`).files[0]);
		data.files.push(file);
	}

	const res = await fetch('/api/form', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(data)
	});

	alert(await res.text());
}

$(document).ready(() => {
	$('button#add-file').click(addRow);

	$('form').submit((event) => {
		event.preventDefault();
		submitData(new FormData($('#form')[0]));
	});
});