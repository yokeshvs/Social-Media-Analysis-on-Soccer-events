CREATE DATABASE `UserTweetAnalysis`;

drop table userid;

CREATE TABLE `userid` (
    `userIDtweetsuser_10_5_2017` int(45),
    PRIMARY KEY (`userID`)
);

delete from userid where userID = 25073877;

select * from userid;


insert into userid values (285332860);
insert into userid values (25073877);


select  count(*) from (SELECT * FROM usertweetanalysis.tweetsuser_10_6_2017 union select * from usertweetanalysis.tweetsuser_10_5_2017 union
SELECT * FROM usertweetanalysis.tweetsuser_10_7_2017 union select * from usertweetanalysis.tweetsuser_10_8_2017) s where s.textOfTweet like '%pique%';

select  * from (SELECT * FROM usertweetanalysis.tweetsuser_10_6_2017 union select * from usertweetanalysis.tweetsuser_10_5_2017 union SELECT * FROM usertweetanalysis.tweetsuser_10_7_2017 union select * from usertweetanalysis.tweetsuser_10_8_2017) s where s.userId in (select * from userid);

select  s.isRetweet, count(*) from (SELECT * FROM usertweetanalysis.tweetsuser_10_6_2017 union select * from usertweetanalysis.tweetsuser_10_5_2017 union
SELECT * FROM usertweetanalysis.tweetsuser_10_7_2017 union select * from usertweetanalysis.tweetsuser_10_8_2017) s where s.textOfTweet like '%cristiano%' group by s.isRetweet;
