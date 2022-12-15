const { db_url, site_url } = require('./config.json');

const http = require('http');
const https = require('https');
const fs = require('fs');

const express = require('express');
const multer = require('multer');
const bodyparser = require('body-parser');
const stream = require('stream');

// Certificate
const privateKey = fs.readFileSync(`/etc/letsencrypt/live/${site_url}/privkey.pem`, 'utf8');
const certificate = fs.readFileSync(`/etc/letsencrypt/live/${site_url}/cert.pem`, 'utf8');
const ca = fs.readFileSync(`/etc/letsencrypt/live/${site_url}/chain.pem`, 'utf8');

const credentials = {
	key: privateKey,
	cert: certificate,
	ca: ca
};

// Mongo

const { MongoClient, ObjectId } = require("mongodb");
const client = new MongoClient(db_url);
const db = client.db('makerspace');
const projectCollection = db.collection('projects');
const fileCollection = db.collection('files');

// Web Server

const app = express();
const upload = multer();

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({ extended: false }));

app.set("view engine", "ejs");
app.use(express.static("public"));
app.use("/views", express.static(__dirname + "/views"));

app.get("/", (req, res) => {
  res.render("pages/index");
});

app.get("/projectForm", (req, res) => {
  res.render("pages/projectForm");
});

app.get("/printForm", (req, res) => {
  res.render("pages/printForm");
});

app.get("/project", (req, res) => {
  projectCollection
    .find()
    .toArray()
    .then((projects) => {
      res.render("pages/project_index", { projects: projects });
    });
});

app.get("/project/:projectId", (req, res) => {
  projectCollection
    .findOne({ _id: ObjectId(req.params.projectId) })
    .then((project) => {
      res.render("pages/project", project);
    });
});

app.get("/file/:fileId", (req, res) => {
  fileCollection.findOne({ _id: ObjectId(req.params.fileId) }).then((file) => {
    var readStream = new stream.PassThrough();
    readStream.end(Buffer.from(file.data.buffer));

    res.set("Content-disposition", "attachment; filename=" + file.name);
    res.set("Content-Type", "text/plain");

    readStream.pipe(res);
  });
});

app.post("/api/form/project", upload.any(), (req, res) => {
  console.log(req.body);
  projectCollection.insertOne(req.body).then((result) => {
    res.status(200).send(result.insertedId.toJSON());
    console.log(`Uploaded Project: ${result.insertedId.toString()}`);
  });
});

app.post("/api/form/print", (req, res) => {
  projectCollection.updateOne({ _id: ObjectId(req.body.project_id) }, {$push: {prints: req.body}});
  res.status(200).send("Succesfully submitted print!");
});

app.post("/api/file", upload.any(), (req, res) => {
  let file = req.files[0];
  if(file == null) {
    res.status(400).send("No File Provided!");
    return;
  }

  fileCollection
    .insertOne({
      name: file.originalname,
      data: file.buffer,
    })
    .then((result) => {
      res.send(result.insertedId.toString());
    });
});

const httpServer = http.createServer(app);
const httpsServer = https.createServer(credentials, app);

httpServer.listen(80, () => {
	console.log('HTTP Server running on port 80');
});

httpsServer.listen(443, () => {
	console.log('HTTPS Server running on port 443');
});
