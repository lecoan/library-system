 const request = require("superagent"),
  async = require('async'),
  eventproxy = require('eventproxy'),
  cheerio = require("cheerio");
 require("superagent-proxy")(request);
 var ep = new eventproxy();
 const fs = require('fs');

 const proxyHost = "proxy.abuyun.com";
 const proxyPort = 9010;

 var blockCount = 4;
 var blockName = [ 'literature', 'popular', 'culture', 'newlife', 'Management', 'Technology' ];

 // 代理隧道验证信息
 const proxyUser = "H63FIP1T75Y1835P";
 const proxyPass = "8EB88E484BAA5704";

 const proxyUrl = "http://" + proxyUser + ":" + proxyPass + "@" + proxyHost + ":" + proxyPort;

 var doubanTagUrl = 'https://book.douban.com/tag/',
  TagPageUrl = [],
  STagPageUrl = [],
  catchData = [];
 var row;
 var getSTagUrl = function() {
  request.get(doubanTagUrl).proxy(proxyUrl).end(function(err, res) {
    if (err) {
      console.error('err at getStagUrl');
    } else {
      var $ = cheerio.load(res.text);
      row = parseInt($('#content > div > div.article > div:nth-child(2) > div:nth-child(' + blockCount + ') > table td').length / 4);
      ep.after('getUrl', row * 4, function(list) {
        TagPageUrl.forEach(function(el, index) {
          console.log(el);
          request.get(encodeURI(el)).proxy(proxyUrl).end(function(err, res) {
            ep.emit('checkUrl', el);
            if (err)
              console.error(el);
            else {
              for (var i = 0; i <= 200; i++) {
                STagPageUrl.push(el + '?start=' + i * 20 + '&type=T');
              }
            }
          });
        });
        ep.after('checkUrl', row * 4, getbookstart);
      });
      for (var i = 1; i <= row; i++) {
        for (var j = 1; j <= 4; j++) {
          TagPageUrl.push('https://book.douban.com' + $('#content > div > div.article > div:nth-child(2) > div:nth-child(' + blockCount + ') > table > tbody > tr:nth-child(' + i + ') > td:nth-child(' + j + ') > a').attr('href'));
          ep.emit('getUrl', TagPageUrl.length);
        }
      }
    }
  });

 }
 var errCall = function() { console.error("error at getbookstart"); }
 var catchBook = function(el, callback) {
  var delay = parseInt((Math.random() * 30000000) % 1000, 10);
  request.get(encodeURI(el)).proxy(proxyUrl).end(function(err, res) {
    if (err)
      console.error('err!')
    else {
      var $ = cheerio.load(res.text);
      for (var i = 1; i <= 20; i++) {
        // var bookOP = {
        //   bookName: $('#subject_list > ul > li:nth-child(' + i + ') > div.info > h2 > a').attr('title'),
        //   authorName: info[0],
        //   publisherName: info[2],
        //   describe: $('#subject_list > ul > li:nth-child(' + i + ') > div.info > p').text()
        // }
        var info = $('#subject_list > ul > li:nth-child(' + i + ') > div.info > div.pub').text().replace(/^\s+|\s+$/g, "").split(' ');
        var bookName = $('#subject_list > ul > li:nth-child(' + i + ') > div.info > h2 > a').attr('title'),
          authorName = '',
          publisherName = '',
          describe = $('#subject_list > ul > li:nth-child(' + i + ') > div.info > p').text();
        for (var k = 0; k < info.length; k++) {
          if (-1 == info[k].indexOf("社"))
            authorName += info[k];
          else {
            publisherName += info[k];
            authorName = authorName.slice(0,-1);
            k = info.length + 1;
          }
        }
        if (describe.length > 30) {
          describe = describe.slice(0, 30) + '...';
        }
        if (publisherName.length && authorName.length && bookName.length && describe.length) {
          // var bookOP = '{\'' + $('#subject_list > ul > li:nth-child(' + i + ') > div.info > h2 > a').attr('title') + '##' + info[0] + '##' + info[2] + '##' + $('#subject_list > ul > li:nth-child(' + i + ') > div.info > p').text() + '##' + el.match(/[\u4e00-\u9fa5]+/)[0] + '\'}';
          var bookOP = '{' + bookName + '##' + authorName + '##' + publisherName + '##' + describe + '##' + el.match(/[\u4e00-\u9fa5]+/)[0] + '}';
          catchData.push(bookOP);
          console.log(bookName,catchData.length);
        }
      }
    }
  });
  setTimeout(function() {
    callback(null, el + ' html content');
  }, delay);
 }
 var WriteFile = function() {
  fs.writeFile(blockName[blockCount - 1] + '.txt', catchData, (err) => {
    if (err) throw err;
    console.log(blockName[blockCount-1] + 'is saved!');
    if (blockCount <= 5) {
      setTimeout(function() {
        blockCount++;
        TagPageUrl = [];
        STagPageUrl = [];
        catchData = [];
        getSTagUrl();
      }, 3500);
    }
  });
 }
 var getbookstart = function() {
  console.log('STagPageUrl:' + STagPageUrl.length);
  async.mapLimit(STagPageUrl, 5, function(el, callback) { catchBook(el, callback); }, function() {
    console.log("complete!");
    setTimeout(WriteFile,1500);
    // WriteFile();
  });
 }
 getSTagUrl();
