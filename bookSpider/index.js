var superagent = require("superagent"),
  async = require('async'),
  eventproxy = require('eventproxy'),
  cheerio = require("cheerio");
var ep = new eventproxy()

var doubanTagUrl = 'https://book.douban.com/tag/',
  doubanTagChunk = [],
  TagPageUrl = [],
  STagPageUrl = [],
  catchData = [];

var getSTagUrl = function() {
  superagent.get(doubanTagUrl).end(function(err, res) {
    if (err) {
      console.error('err at getStagUrl');
    } else {
      var $ = cheerio.load(res.text);
      ep.after('getUrl', 12, function(list) {
        TagPageUrl.forEach(function(el, index) {
          console.log(el);
          ep.emit('checkUrl', 0);
          superagent.get(encodeURI(el)).end(function(err, res) {
            if (err)
              console.log(el);
            else {
              for (var i = 0; i <= 100; i++) {
                STagPageUrl.push(encodeURI(el + '?start=' + i * 20 + '&type=T'));
              }
              getbookstart();
            }
          });
        });
        ep.after('checkUrl', 12, getbook);
      });
      for (var i = 1; i <= 3; i++) {
        for (var j = 1; j <= 4; j++) {
          TagPageUrl.push('https://book.douban.com' + $('#content > div > div.article > div:nth-child(2) > div:nth-child(1) > table > tbody > tr:nth-child(' + i + ') > td:nth-child(' + j + ') > a').attr('href'));
          ep.emit('getUrl', TagPageUrl.length);
        }
      }
    }
  });

}
var errCall = function() { console.error("error at getbookstart"); }
var getbookstart = function() {
  async.mapLimit(STagPageUrl, 5, function(el, errCall) {
    superagent.get(el).end(function(err, res) {
      if (err)
        console.error('err!')
      else {
        var $ = cheerio.load(res.text);
        for (var i = 1; i <= 20; i++) {
          var info = $$('#subject_list > ul > li:nth-child(' + i + ') > div.info > div.pub').text().replace(/^\s+|\s+$/g, "").split(' ');
          var bookOP = {
            bookName: $$('#subject_list > ul > li:nth-child(' + i + ') > div.info > h2 > a').attr('title'),
            authorName: info[0],
            publisherName: info[2],
            describe: $$('#subject_list > ul > li:nth-child(' + i + ') > div.info > p').text()
          }
          catchData.push(bookOP);
          console.log(bookOP.bookName);
        }
      }
    })
  }, function(){console.log("complete!");});
}
getSTagUrl();
