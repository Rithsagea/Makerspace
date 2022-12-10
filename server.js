const express = require("express");
const multer = require("multer");
const { MongoClient, ObjectId } = require("mongodb");
const bodyparser = require("body-parser");
const stream = require("stream");

const { db_url, port } = require("./config.json");
const client = new MongoClient(db_url);
const db = client.db("makerspace");
const projectCollection = db.collection("projects");
const fileCollection = db.collection("files");

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

app.post("/api/form", upload.any(), (req, res) => {
  console.log(req.body);
  projectCollection.insertOne(req.body).then((result) => {
    res.send(result.insertedId.toJSON());
    console.log(`Uploaded Project: ${result.insertedId.toString()}`);
  });
});

app.post("/api/file", upload.any(), (req, res) => {
  let file = req.files[0];
  fileCollection
    .insertOne({
      name: file.originalname,
      data: file.buffer,
    })
    .then((result) => {
      res.send(result.insertedId.toString());
    });
});

app.listen(port, () => {
  console.log(`Makerspace server listening on port ${port}`);
});
