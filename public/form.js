let fileCount = 1;

function addRow() {
	let table = document.getElementById('files');
	let row = table.insertRow();
	document.cookie = `fileCount=${++fileCount}; SameSite=None; Secure`;
	row.innerHTML =
		`<tr>
			<th scope="row">${fileCount}</th>
			<td><input class="form-control" name="file-name"></td>
			<td><textarea class="form-control text-area"></textarea></td>
			<td><input class="form-control" type="file" name="file-upload"></td>
		</tr>`;

	return row;
}

$(document).ready(function () {

	$('button#add-file').click(function () {
		let row = addRow();
		console.log(row);
	});

	// const formElem = document.querySelector('form');
	// formElem.addEventListener('submit', (event) => {
	// 	event.preventDefault();

	// 	const formData = new FormData(formElem);
	// 	const project = {};
	// 	for (const pair of formData.entries()) {
	// 		project[pair[0]] = pair[1];
	// 	}

	// 	console.log(project);
	// });

	// document.querySelector("form").addEventListener("formdata", (e) => {
	// 	console.log(e.formData);
	// });
});

