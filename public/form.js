let pageCount = 1;

$(document).ready(function () {
	$('button#add-file').click(function () {
		let table = document.getElementById('files');
		let row = table.insertRow();
		row.innerHTML=
			`<tr>
				<th scope="row">${++pageCount}</th>
				<td><input class="form-control" name="file-name"></td>
				<td><textarea class="form-control text-area"></textarea></td>
				<td><input class="form-control" type="file" name="file-upload"></td>
			</tr>`;
		console.log(row);
	});
});

// $("form").submit(function(event) {
// 	console.log(event);
// 	event.preventDefault();
// });