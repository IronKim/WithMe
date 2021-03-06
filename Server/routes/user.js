var express = require('express');
var User = require('../models/User');
var router = express.Router();
var multer = require('multer');
var fs = require('fs');

var multipart = require('multiparty');

var storage = multer.diskStorage({
        destination: function(req, file, cb) {
            cb(null, './public/images/user'); // Make sure this folder exists
        },
        filename: function(req, file, cb) {
          console.log("ok");
            cb(null, file.originalname);
        }
    }),
    upload = multer({ storage: storage }).single('image');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.post('/', function(req, res, next) {
  console.log(req.body);
  var newUser = new User({
    mail : req.body.mail,
    password : req.body.password,
    token : req.body.token,
    name: req.body.name,
    birth: req.body.birth,
    phone: req.body.phone,
    gender: req.body.gender
  });

  newUser.save(function(err, user){
    if(err) {
      console.log("사용자 등록 실패");
      console.log(err);
      return res.json({result: 'fail'});
    }
    console.log("사용자 등록 성공");
    return res.json({result: 'success'});
  });
});

// router.post('/image', upload);
router.post('/image', function(req, res, next) {
  var form = new multipart.Form();
    form.parse(req, function(err, fields, files) {
      var newUser = new User();
      // console.log(files.image[0].originalFilename);
      newUser.img = files.image[0].originalFilename;
      var body = JSON.parse(fields.body);
      newUser.mail = body.mail;
      newUser.password = body.password;
      newUser.token = body.token;
      newUser.name = body.name;
      newUser.birth = body.birth;
      newUser.phone = body.phone;
      newUser.gender = body.gender;
      /*
      upload(files, function (err) {
        if (err) {
          console.log(err);
        }
        // Everything went fine
      });
      */
      newUser.save(function(err, user){
        if(err) {
          console.log("사용자 등록 실패");
          return res.json({result: 'fail'});
        }
        console.log("사용자 등록 성공");
        fs.readFile(files.image[0].path, function(err, data){
            var filePath = "./public/images/user/" + files.image[0].originalFilename;
            fs.writeFile(filePath, data, function(err){
              if(err){
                console.log(err);
              }
            });
        });
        return res.json({result: 'success'});
      });
     //put in here all the logic applied to your files.
  });
});
router.post('/edit/:id', function(req, res, next) {
  console.log(req.body);
  User.findById(req.params.id, function(err, user){
    user.mail = req.body.mail;
    user.password = req.body.password;
    user.token = req.body.token;
    user.name = req.body.name;
    user.birth = req.body.birth;
    user.phone = req.body.phone;
    user.gender = req.body.gender;
    user.save(function(err, user){
      if(err) {
        console.log("사용자 등록 실패");
        console.log(err);
        return res.json({result: 'fail'});
      }
      console.log("사용자 등록 성공");
      return res.json({result: 'success', user: user});
    });
  });
});

router.post('/edit/image/:id', function(req, res, next) {
  console.log("/edit/image/");
  var form = new multipart.Form();
    form.parse(req, function(err, fields, files) {
      var body = JSON.parse(fields.body);
      User.findById(req.params.id, function(err, user){
          if(err){
              console.log("회원 정보 수정 실패");
              return res.json({result: 'fail'});
          }
          user.img = files.image[0].originalFilename;
          user.mail = body.mail;
          user.password = body.password;
          user.token = body.token;
          user.name = body.name;
          user.birth = body.birth;
          user.phone = body.phone;
          user.gender = body.gender;
          user.save(function(err, user){
            if(err) {
              console.log("사용자 수정 실패");
              return res.json({result: 'fail'});
            }
            console.log("사용자 수정 성공");
            fs.readFile(files.image[0].path, function(err, data){
                var filePath = "./public/images/user/" + files.image[0].originalFilename;
                fs.writeFile(filePath, data, function(err){
                  if(err){
                    console.log(err);
                  }
                });
            });
            return res.json({result: 'success', user: user});
          });
      });
     //put in here all the logic applied to your files.
  });
});

router.post('/login', function(req, res, next) {
  // console.log(req.body);
  User.findOne({mail: req.body.mail, password: req.body.password}, function(err, user){
    if(err){
      console.log('err');
      return res.json({result: 'error'});
    }
    if(user === null){
      console.log('null');
      return res.json({result: 'null'});
    }
    user.token = req.body.token;
    user.save(function(err, user){
      if(err){
        console.log('err');
        return res.json({result: 'error'});
      }
      if(user !== null){
        console.log('user !== null');
        user.result = 'success';
        console.log(user);
        return res.json({result: 'success', user: user});
      }
      else {
        console.log('else');
        return res.json({result: 'fail'});
      }
    });
  });
});

router.post('/logout', function(req, res, next) {
  console.log(req.body);
  User.findOne({_id:req.body.id}, function(err, user){
    if(err){
      console.log('err');
      return res.json({result: 'error'});
    }
    if(user === null){
      console.log('null');
      return res.json({result: 'null'});
    }
    user.token = "";
    user.save(function(err, user){
      if(err){
        console.log('err');
        return res.json({result: 'error'});
      }
      if(user !== null){
        console.log('user !== null');
        user.result = 'success';
        console.log(user);
        return res.json({result: 'success', user: user});
      }
      else {
        console.log('else');
        return res.json({result: 'fail'});
      }
    });
  });
});

router.put('/', function(req, res, next) {
  res.send('respond with a resource');
  console.log(req.body);
});

module.exports = router;
