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
});