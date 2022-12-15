let settingCount = 1;

function addRow() {
  let table = document.getElementById('settings');
  let row = table.insertRow();

  $.get('/views/partials/print_file.ejs').then((str, status, xhr) => {
    row.innerHTML = ejs.render(str, { num: ++settingCount });
  });
}

async function submitData(formData) {
  var data = {
    'project_id' : formData.get('project_id'),
    'print_name' : formData.get('print_name'),
    'printer_type' : formData.get('printer_type'),
    'filament_type' : formData.get('filament_type'),
    'filament_brand' : formData.get('filament_brand'),
    'slicer': formData.get('slicer'),
    'extruder_temperature' : formData.get('extruder_temperature'),
    'print_bed_temperature': formData.get('print_bed_temperature'),
    'settings': []
  };

  var rows = $(settings)[0].rows;
  for (let i = 1; i < rows.length; i++) {
    data.settings.push({
      'name': rows[i].querySelector(`input[name=setting_name]`).value,
      'value': rows[i].querySelector(`input[name=setting_value]`).value
    });
  }

  const res = await fetch('/api/form/print', {
    method: 'POST',
    headers: {
			'Content-Type': 'application/json',
		},
    body: JSON.stringify(data)
  });

  alert(`Submitted Print!`);
}

$(document).ready(() => {
  $('button#add-setting').click(addRow);

  $('form').submit((event) => {
    event.preventDefault();
    submitData(new FormData($('#form')[0]));
  });
});