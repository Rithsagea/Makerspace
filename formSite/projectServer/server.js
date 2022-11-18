const express = require('express');
const multer = require('multer');
const cors = require('cors');

const app = express();
const port = 8080;
const upload = multer({ dest: 'uploads/' });

app.use(cors());
app.use(express.static('public'));

app.post('/api/form', upload.any(), (req, res) => {
	const form = req.body;

	console.log(req.body);
	console.log(req.files);
	res.send('Received form!');
});

app.listen(port, () => {
	console.log(`Makerspace server listening on port ${port}`);
});