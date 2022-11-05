const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 8080;

app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post('/api/form', (req, res) => {
	const form = req.body;

	console.log(form);
	res.send('Received form!');
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});