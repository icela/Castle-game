CREATE TABLE ROOM(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  disc TEXT, welc TEXT,
  boss TEXT,blood INTEGER,
  strike INTEGER, defence INTEGER,
  exp INTEGER, die TEXT
);


INSERT INTO ROOM(id, disc, boss, blood, strike, defence, exp, die) VALUES (
  0, '城堡外','英俊的小偷头目', 200,25,10,15, '小偷头目的钱全掉出来了！'                        -- 0
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '一楼大堂','欢迎来到城堡！','礼貌的大堂经理',100,15,12,8,'大堂经理的帐算错了！'              -- 1
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '小酒吧','一大股酒香飘来。','酒吧老板', 150,10,5,5,'酒吧流氓喝醉了！'                  -- 2
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '书房','读书的气氛很浓厚。' , '优雅的读书人',100,7,5,3,'读书人的书掉出来了！'                -- 3
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '旅馆', '周围干净整洁。', '可爱的女仆', 10,6,3,2,'女仆被你推倒了！'                          -- 4
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '二楼睡房','公主的管家', 300,20,5,25,'管家扑街、公主被你推倒了！'                            -- 5
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '负一楼','奇怪的男人',200,30,15,25,'男人身边站出来一名浑身是伤的女孩。。'                    -- 6
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '负二楼','穿着霸气的绅士',100,50,35,35,'绅士的衣服脏了！'                                    -- 7
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '负三楼','身穿铠甲的战士',300,30,25,45,'战士被自己绊倒了！'                                  -- 8
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '负四楼','持剑的骑士',400,40,35,60,'骑士的剑断了！'                                          -- 9
);
INSERT INTO ROOM(disc) VALUES (
  '三楼阳台'                                                                                   -- 10
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '城堡顶部瞭望塔', '瞭望塔守卫',150, 20, 2, 20, '守卫倒下了！'                                -- 11
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp) VALUES (
  '羊肠小道', '街边小混混',100,30,1,20                                                         -- 12
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '日出村大门','欢迎来到城堡西边的日出村！', '和善的门卫',150,20,20,30 ,'门卫露出了和善的笑容' -- 13
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间祭坛','冰封',1000,150,100,200,'冰封认真地写着客户端。。。'                          -- 14
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间西','无',1000,150,100,200,'无认真地写这服务器端。。。'                              -- 15
);
INSERT INTO ROOM(disc,boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间东','奶茶',1000,150,100,200,'奶茶去复习考试了。。。'                                -- 16
);
INSERT INTO ROOM(disc,  boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间北','果冻',1000,150,100,200,'果冻正在打酱油。。。'                                  -- 17
);
INSERT INTO ROOM(disc) VALUES (
  '日出村民居'                                                                                 -- 18
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '日出村教堂','你瞬间被这里神圣的气息闪瞎了。' , '聆听忏悔的牧师',200, 30, 20, 40, '牧师聆听着忏悔。' -- 19
);
INSERT INTO ROOM(disc, boss, blood, strike, defence, exp, die) VALUES (
  '神秘的井', '打水的熊孩子',50, 10, 1, 5, '熊孩子掉头就跑。'                                 -- 20
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '井底', '这里很潮湿，阴森恐怖。', '青蛙怪',300, 80, 50, 70, '青蛙怪被烤熟了！'              -- 21
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '井底北', '更加潮湿了。', '戴皇冠的青蛙怪王',500, 100, 40, 100, '青蛙怪王的皇冠掉了下来！'  -- 22
);
INSERT INTO ROOM(disc, welc, boss, blood, strike, defence, exp, die) VALUES (
  '井底密室', '空气中弥漫着阴冷潮湿的气息。', '戴头灯的探险家',400, 80, 30, 80, '探险家的头灯没电了！' -- 23
);

CREATE TABLE DIR(id INTEGER PRIMARY KEY AUTOINCREMENT, from_text TEXT, to_text TEXT);
INSERT INTO DIR(from_text, to_text) VALUES ('up', 'down');
INSERT INTO DIR(from_text, to_text) VALUES ('north', 'south');
INSERT INTO DIR(from_text, to_text) VALUES ('east', 'west');

CREATE TABLE MAP( id INTEGER PRIMARY KEY AUTOINCREMENT, fromid INTEGER, toid INTEGER, dir INTEGER);
INSERT INTO MAP(fromid, toid, dir) VALUES (1, 5, 1);
INSERT INTO MAP(fromid, toid, dir) VALUES (5, 10,1);
INSERT INTO MAP(fromid, toid, dir) VALUES (10,11,1);
INSERT INTO MAP(fromid, toid, dir) VALUES (6, 1, 1);
INSERT INTO MAP(fromid, toid, dir) VALUES (7, 6, 1);
INSERT INTO MAP(fromid, toid, dir) VALUES (8, 7, 1);
INSERT INTO MAP(fromid, toid, dir) VALUES (9, 8, 1);
INSERT INTO MAP(fromid, toid, dir) VALUES (20,21,1);
INSERT INTO MAP(fromid, toid, dir) VALUES (21,22,1);
INSERT INTO MAP(fromid, toid, dir) VALUES (22,23,1);
INSERT INTO MAP(fromid, toid, dir) VALUES (3, 0, 2);
INSERT INTO MAP(fromid, toid, dir) VALUES (20,19,2);
INSERT INTO MAP(fromid, toid, dir) VALUES (4, 1, 2);
INSERT INTO MAP(fromid, toid, dir) VALUES (14,17,2);
INSERT INTO MAP(fromid, toid, dir) VALUES (0, 1, 3);
INSERT INTO MAP(fromid, toid, dir) VALUES (2, 0, 3);
INSERT INTO MAP(fromid, toid, dir) VALUES (3, 4, 3);
INSERT INTO MAP(fromid, toid, dir) VALUES (12,2, 3);
INSERT INTO MAP(fromid, toid, dir) VALUES (13,12,3);
INSERT INTO MAP(fromid, toid, dir) VALUES (15,14,3);
INSERT INTO MAP(fromid, toid, dir) VALUES (14,16,3);
INSERT INTO MAP(fromid, toid, dir) VALUES (17,14,3);
INSERT INTO MAP(fromid, toid, dir) VALUES (18,13,3);
INSERT INTO MAP(fromid, toid, dir) VALUES (19,18,3);

CREATE TABLE ITEM(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT, event INTEGER, extra TEXT, desc TEXT
);
-- 事件只能硬编码指定了，这是没办法的事

CREATE TABLE BOSS_GET_ITEM (
  room INTEGER, item INTEGER
);

INSERT INTO BOSS_GET_ITEM(room, item) VALUES (4, 2);

INSERT INTO ITEM(id, name, event, desc) VALUES (0, '地图', 0, '神秘而古旧的地图。');                             -- 0
INSERT INTO ITEM(name, event, desc) VALUES ('传送宝石', 1, '透明的紫色水晶，散发着魔力。');                       -- 1
INSERT INTO ITEM(name, event, extra, desc) VALUES ('和女仆的契约', 2, '象征着女仆对你的忠诚。');                   -- 2
INSERT INTO ITEM(name, event, extra, desc) VALUES ('小恢复剂',  3, '20', '可以恢复体力的药剂');                  -- 3
INSERT INTO ITEM(name, event, extra, desc) VALUES ('中恢复剂',  3, '60', '可以恢复体力的药剂');                  -- 4
INSERT INTO ITEM(name, event, extra, desc) VALUES ('大恢复剂',  3, '120', '可以恢复体力的药剂');                  -- 5
INSERT INTO ITEM(name, event, extra, desc) VALUES ('超大恢复剂', 3, '250', '可以恢复体力的药剂');                -- 6

-- ↓剧情需要物件↓

-- 4: 房间切换用途    5: 自由用途     6: 带损耗体力值副作用的自由用途   7: 化学反应用途   8: 带损耗体力值副作用的化学反应用途
-- 9: 背包扩容用途，但同时会损耗体力值

INSERT INTO ITEM(name, event, desc) VALUES ('看似古老的钥匙', 4, '可以用来... ...呃... ...开门？');                -- 7
INSERT INTO ITEM(name, event, desc) VALUES ('奶茶的口令牌', 4, '呃呃呃... ...');                                  -- 8
INSERT INTO ITEM(name, event, desc) VALUES ('奇怪的硬币', 4, '用途未知。');                                       -- 9
INSERT INTO ITEM(name, event, desc) VALUES ('一把小刀', 5, '任何用途。');                                        -- 10
INSERT INTO ITEM(name, event, desc) VALUES ('一组齿轮', 5, '看上去是什么机械的冗余部件。');                         -- 11
INSERT INTO ITEM(name, event, desc) VALUES ('一块电池', 5, '储存着电能。');                                       -- 12

-- 制取王水： NaCl+H2SO4（浓）=微热=NaHSO4+HCl↑       NHO3(1份)+HCL(3份)=王水
INSERT INTO ITEM(name, event, desc) VALUES ('聚四氟乙烯试管', 5, '可以用来盛放具有超强腐蚀性的试剂。');                -- 13
INSERT INTO ITEM(name, event, desc) VALUES ('盐', 7, '就是盐啊... ...咸咸的盐。');                                 -- 14
INSERT INTO ITEM(name, event, extra, desc) VALUES ('硝酸', 8,'7', '重要的化工原料，有强腐蚀性。小心！');             -- 15
INSERT INTO ITEM(name, event, desc) VALUES ('电炉', 7, '用来加热试剂。');                                         -- 16
INSERT INTO ITEM(name, event, extra, desc) VALUES ('浓硫酸', 8,'3', '重要的化工原料，有腐蚀性。小心！');            -- 17
INSERT INTO ITEM(name, event, extra, desc) VALUES ('王水', 8 '15', '具有极强腐蚀性的化学试剂。小心！')              -- 18
INSERT INTO ITEM(name, event, desc) VALUES ('未知化学试剂', 5, '未知用途。'); -- 制备错误的结果                      -- 19

INSERT INTO ITEM(name, event, desc) VALUES ('特殊的SD卡', 5, '存储资料... ...吧。');                                -- 20
INSERT INTO ITEM(name, event, desc) VALUES ('艾尔希娅', 5, '一块紫色的钻石，镶嵌在银质的环中。用途未知。');              -- 21



CREATE TABLE NPC(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT, room INTEGER, hello TEXT,
  item INTEGER
);

SELECT * FROM ROOM ORDER BY id ASC;
SELECT * FROM DIR ORDER BY id ASC;
SELECT * FROM MAP ORDER BY id ASC;
SELECT * FROM ITEM ORDER BY id ASC;
SELECT * FROM NPC ORDER BY id ASC;