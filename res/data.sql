------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- 全局保留字：
-- %NAME%: 玩家名字
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- event: 进入房间后触发的事件：事件类型^详细事件编号，暂未加入支持！
CREATE TABLE ROOM(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT, welc TEXT,
  event TEXT, boss TEXT,
  blood INTEGER, strike INTEGER,
  defence INTEGER, exp INTEGER,
  die TEXT, sequel TEXT
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- TODO 注意！！注意！！此处定义的boss要优先于npc显示！！！
-- 出生地
INSERT INTO ROOM(id, name, welc) VALUES (
  0, '交叉口', '通向城堡和日出之村。'                                                   -- 0
);

INSERT INTO ROOM(name) VALUES (
  '城堡外'                                                                            -- 1
);

INSERT INTO ROOM(name) VALUES (
  '一楼大堂'                                                                         -- 2
);

INSERT INTO ROOM(name, welc, boss, blood, strike, defence, exp, die) VALUES (
  '小酒吧','一大股酒香飘来。','酒吧流氓', 150,10,5,5,'酒吧流氓喝醉了！'                  -- 3
);

INSERT INTO ROOM(name, welc) VALUES (
  '书房','阳光从顶窗斜射下来，显得安宁祥和。'                                           -- 4
);

INSERT INTO ROOM(name, welc, boss, blood, strike, defence, exp, die) VALUES (
  '旅馆', '周围干净整洁。', '可爱的女仆', 10,6,3,2,'女仆被你推倒了！'                     		-- 5
);

INSERT INTO ROOM(name) VALUES (
  '二楼睡房'                                                                         				-- 6
);

INSERT INTO ROOM(name, welc) VALUES (
  '负一楼','传来食物的阵阵香味。'                                                       			-- 7
);

INSERT INTO ROOM(name, boss, blood, strike, defence, exp, die) VALUES (
  '负二楼','奇怪的男人',200,50,25,25,'男人身边站出来一名浑身是伤的女孩。。'          		-- 8
);

INSERT INTO ROOM(name) VALUES (
  '负三楼'                                                                        				-- 9
);

INSERT INTO ROOM(name, welc) VALUES (
  '负四楼','穿着白衣服的奇怪男人倒在地上'                                                 			-- 10
);

INSERT INTO ROOM(name) VALUES (
  '三楼阳台'                                                                          				-- 11
);

INSERT INTO ROOM(name, boss, blood, strike, defence, exp, die) VALUES (
  '城堡顶部瞭望塔', '瞭望塔守卫',150, 20, 2, 20, '守卫倒下了！'                            		-- 12
);

INSERT INTO ROOM(name, boss, blood, strike, defence, exp, die) VALUES (
    '观星台', '戴着眼镜的科学家', 100, 10, 5, 10, '科学家的眼镜摔碎了！'			--13
);

INSERT INTO ROOM(name, welc) VALUES(
    '观星台北', '一道若隐若现的流星划过'						--14
);

INSERT INTO ROOM(name, welc) VALUES(
    '废弃的实验室大门', '大门上布满锈迹'						--15
);

INSERT INTO ROOM(name, welc) VALUES(
    '废弃的实验室', '积满灰尘的门牌上0713的数字若隐若现'				--16
);

INSERT INTO ROOM(name, welc) VALUES(
    '计算机终端间', '核能电池仍在发出嗡嗡的响声'					--17
);

-- 暂且先这样凑合。。。。。。
INSERT INTO ROOM(name, welc, sequel) VALUES(
    '计算机旁的书架', '有一本小本子，一张纸还有一个特殊的SD卡。', 'CHOOSE^0'		-18
);

INSERT INTO ROOM(name, boss, blood, strike, defence, exp) VALUES (
  '羊肠小道', '街边小混混',100,30,1,20                                                   -- 19
);

INSERT INTO ROOM(name) VALUES (
  '日出之村大门'                                                                         -- 20
);

-- 整个游戏的大boss！！所以要在前面放一些药剂什么之类的对吧~~~~
INSERT INTO ROOM(name, boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间','戴着面具的男人',1000,160,120,200,'男人摘下了面具... ...'                      -- 21
);

-- 对对对，就是冰封。记得把冰封的名字写到对话里去啊！！
INSERT INTO ROOM(name, boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间内厅','开发者',1000,150,100,200,'开发者的程序报错了！'                             -- 22
);

INSERT INTO ROOM(name,  boss, blood, strike, defence, exp, die) VALUES (
  '神秘空间祭坛','果冻',800,130,100,180,'果冻正在打酱油... ...'                               -- 23
);

INSERT INTO ROOM(name) VALUES (
  '日出村民居'                                                                           -- 24
);

INSERT INTO ROOM(name, welc, boss, blood, strike, defence, exp, die) VALUES (
  '日出村教堂','你瞬间被这里神圣的气息闪瞎了。' , '聆听忏悔的牧师',200, 30, 20, 40, '牧师聆听着忏悔。' -- 25
);

INSERT INTO ROOM(name, boss, blood, strike, defence, exp, die) VALUES (
  '神秘的井', '打水的熊孩子',50, 10, 1, 5, '熊孩子掉头就跑。'                                 -- 26
);

INSERT INTO ROOM(name, welc) VALUES (
  '井底', '这里很潮湿，阴森恐怖。'                                                          -- 27
);

INSERT INTO ROOM(name, welc, boss, blood, strike, defence, exp, die) VALUES (
  '井底通道', '空气中弥漫着阴冷潮湿的气息，通道很长，看不到尽头。', '戴头灯的探险家',400, 100, 50, 80, '探险家的头灯没电了！'-- 28
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE DIR(id INTEGER PRIMARY KEY AUTOINCREMENT, from_text TEXT, to_text TEXT);
-- TODO 其实我觉着直接用房间名称。。。就是执行map的时候就显示<id> <可用房间名字>，然后就go <id>，但是感觉关联的时候会是个问题。。。要不就map的时候显示<south/...啥的> <房间名字>然后还是go <south...啥的>？？
INSERT INTO DIR(from_text, to_text) VALUES ('up', 'down');          --1
INSERT INTO DIR(from_text, to_text) VALUES ('north', 'south');      --2
INSERT INTO DIR(from_text, to_text) VALUES ('east', 'west');        --3
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE MAP( id INTEGER PRIMARY KEY AUTOINCREMENT, fromid INTEGER, toid INTEGER, dir INTEGER);

INSERT INTO MAP(fromid, toid, dir) VALUES (0, 1, 3);
-- TODO 不行我先把房间艹好先。。。留个例子.


------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE BOSS_GET_ITEM (
  room INTEGER, item INTEGER
);

INSERT INTO BOSS_GET_ITEM(room, item， "CHOOSE^0") VALUES (18, 2);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- TODO 洗澡的时候突然想到一个巨大的问题。。。。。。等我有了电脑再来修复
CREATE TABLE ITEM(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT, event INTEGER,
  extra TEXT, desc TEXT
  reaction TEXT
);

INSERT INTO ITEM(id, name, event, desc) VALUES (0, '地图', 0, '神秘而古旧的地图。');                            -- 0
-- ↓商店物件↓
INSERT INTO ITEM(name, event, desc) VALUES ('传送宝石', 1, '透明的紫色水晶，散发着魔力。');                       -- 1
INSERT INTO ITEM(name, event, extra, desc) VALUES ('和女仆的契约', 2, '象征着女仆对你的忠诚。');                  -- 2
INSERT INTO ITEM(name, event, extra, desc) VALUES ('小恢复剂',  3, '20', '将恢复20点体力。');                  -- 3
INSERT INTO ITEM(name, event, extra, desc) VALUES ('中恢复剂',  3, '60', '将恢复60点体力。');                  -- 4
INSERT INTO ITEM(name, event, extra, desc) VALUES ('大恢复剂',  3, '120', '将恢复120点体力。');                  -- 5
INSERT INTO ITEM(name, event, extra, desc) VALUES ('超大恢复剂', 3, '250', '将恢复250点体力。');                -- 6
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- ↓剧情需要物件↓

-- 注意：每分钟损耗体力值是指第一次接触物品扣除体力值后的每分钟扣除体力值。
-- 4: 房间切换用途    5: 自由用途     6: 带损耗体力值副作用的自由用途：每分钟损耗体力值   7: 化学反应用途
-- 8: 带损耗体力值副作用的化学反应用途：每分钟损耗体力值 
-- 9: 背包扩容用途，但同时会损耗体力值：扩容大小^每分钟体力值

INSERT INTO ITEM(name, event, desc, reaction) VALUES ('看似古老的钥匙', 4, '可以用来... ...呃... ...开门？', 'ROOM^');                -- 7
INSERT INTO ITEM(name, event, desc, reaction) VALUES ('奶茶的口令牌', 4, '呃... ...', 'ROOM^');                                    -- 8
INSERT INTO ITEM(name, event, desc) VALUES ('奇怪的硬币', 4, '用途未知。');                                       -- 9
INSERT INTO ITEM(name, event, desc) VALUES ('一把小刀', 5, '任何用途。');                                        -- 10
INSERT INTO ITEM(name, event, desc) VALUES ('一组齿轮', 5, '看上去是什么机械的冗余部件。');                         -- 11
INSERT INTO ITEM(name, event, desc) VALUES ('一块电池', 5, '储存着电能。');                                       -- 12

-- 制取王水： NaCl+H2SO4（浓）=微热=NaHSO4+HCl↑       NHO3(1份)+HCL(3份)=王水
-- 我靠你写这个干嘛  ——冰封
-- 剧情需要。到时候要有条故事线要配制王水腐蚀门锁的[滑稽][滑稽]

INSERT INTO ITEM(name, event, desc) VALUES ('聚四氟乙烯试管', 5, '可以用来盛放具有超强腐蚀性的试剂。');                -- 13
INSERT INTO ITEM(name, event, desc) VALUES ('盐', 7, '实验用盐氯化钠。不能食用！');                                 -- 14
INSERT INTO ITEM(name, event, extra, desc) VALUES ('硝酸', 8,'7', '重要的化工原料，有强腐蚀性。小心！');             -- 15
INSERT INTO ITEM(name, event, desc) VALUES ('电炉', 7, '用来加热试剂，亦可用来融化沙子以制成玻璃。');                   -- 16
INSERT INTO ITEM(name, event, extra, desc) VALUES ('浓硫酸', 8,'3', '重要的化工原料，有腐蚀性。小心！');            -- 17
INSERT INTO ITEM(name, event, extra, desc, reaction) VALUES('盐酸'， 8， '1', '化学反应的中间物。', 'ITEM^3*15')  -- 18
INSERT INTO ITEM(name, event, extra, desc) VALUES ('王水', 8 '15', '具有极强腐蚀性的化学试剂。小心！')              -- 19
INSERT INTO ITEM(name, event, desc) VALUES ('未知化学试剂', 5, '未知用途。'); -- 制备错误的结果                      -- 20

INSERT INTO ITEM(name, event, desc) VALUES ('特殊的SD卡', 5, '存储资料... ...吧。');                                -- 21
INSERT INTO ITEM(name, event, desc) VALUES ('艾尔希娅', 5, '一块紫色的钻石，镶嵌在银质的环中。用途未知。');              -- 22

INSERT INTO ITEM(name, event, extra, desc) VALUES (
'8GB KINSTON', 9, '8^5' '可以给背包扩充8个位置,但同时每分钟体力值损耗加5。'                                             -- 23
);         -- 22
INSERT INTO ITEM(name, event, extra, desc) VALUES (
'16GB KINSTON', 9, '16^10' '可以给背包扩充16个位置,但同时每分钟体力值损耗加10。'                                        -- 24
);         -- 23
INSERT INTO ITEM(name, event, extra, desc) VALUES (
'32GB KINSTON', 9, '32^20' '可以给背包扩充32个位置,但同时每分钟体力值损耗加20。'                                        -- 25
);         -- 22

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- id: NPC编号     name: 名字      room: 所处房间编号         item: 死亡掉落物品编号
--TODO 不行这几个表把我搞的越来越乱。。。
-- TODO 2333 我没怎么搞懂你的思路，不过选项那里我还是可以给你点建议，另外在pack的时候显示物品id，到时候就use id这样使用物品，然后把home和wild去掉，重新封装（因为这两个指令都是基于物品的，那就换成物品调用，简化指令
CREATE TABLE NPC(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT, room INTEGER, item INTEGER
);

INSERT INTO NPC(id, name, room) VALUES (0, '门卫', 2);         -- 0
INSERT INTO NPC(name, room) VALUES ('酒吧老板', 2);         -- 1

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- id: 对话编号     npcid: 对应NPC编号     text: 对话文本      isp: 对话是否由玩家说出：0为不是，1为是。     sequel: 对应后果：后果表名^详细后果编号
CREATE TABLE TALK(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    npcid INTEGER,
    text TEXT, isp BIT,
    sequel TEXT
);

INSERT INTO TALK(id, npcid, text ,isp, sequel) VALUES(
    0, 0, '很高兴见到你。请问你是谁？', 0, 'CHOOSE^0'                            --0
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES(
    0, '哦... ...欢迎您！远道而来的客人。向左通向教堂，向右通向古井，直走就是居民区，但是... ...居民区现在没人。', 0, 'CHOOSE^1' --1
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES(
    0, '因为... ...额... ...因为... ...人都走了呀... ...我也不清楚。', 0, 'END_OF＿TALK'--2
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES (
    1, '您好，欢迎来到城堡酒吧。要来一杯吗？', 0, 'CHOOSE^1'                       -- 2
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES (
    1, '嗯... ...有点可惜。不管怎样，欢迎再次光临城堡酒吧！', 0, 'END_OF_TALK'        -- 3
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES (
    1, '您是刚来这里的外地人吧？', 0, 'CHOOSE^1'                                    -- 4
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES (
    1, '这是哪？这里就是城堡啊！', 0, 'CHOOSE^2'                                     -- 5
);

INSERT INTO TALK(npcid, text, isp, sequel) VALUES (
    1, '难怪我从没有见过你... ...你是住在这的吧？', 0, 'CHOOSE^'                      -- 6
);

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- 最多支持五个选项
-- id: 选项编号     tid: 对应对话编号       choice: 选项文字    sequel: 对应后果：后果表名^详细后果编号
CREATE TABLE CHOOSE(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    choiceA TEXT, sequelA TEXT,
    choiceB TEXT, sequelB TEXT,
    choiceC TEXT, sequelC TEXT,
    choiceD TEXT, sequelD TEXT,
    choiceE TEXT, sequelE TEXT,
);

-- 太羞耻了！！
INSERT INTO CHOOSE(id, choiceA, sequelA, choiceB, sequelB, choiceC, sequelC) VALUES(
0, '看那张纸', 'ITEM^0', '看那本本子', 'INFO^1', '带走特殊的SD卡'， 'ITEM^20'
);
INSERT INTO CHOOSE(choiceA, sequelA ,choiceB, sequelB) VALUES(
'我是%NAME%，是一名... ...一名游客。','TALK^1' '我是... ...一位... ...呃... ...商人。','TALK^1' --0
);
INSERT INTO CHOOSE(choiceA, sequelA, choiceB, sequelB) VALUES(
'居民区怎么会没人呢？', 'TALK^2' '嗯... ...让我看看', 'END_OF_TALK'
);
INSERT INTO CHOOSE(choiceA, sequelA, choiceB, sequelB) VALUES(
    '可以啊，非常乐意。', 'TALK^2', '不... ...还是算了吧... ...抱歉。', 'TALK^1'   -- 1
);

INSERT INTO CHOOSE(choiceA, sequelA, choiceB, sequelB) VALUES(
    '对，我是刚来的外地人。', 'TALK^1', '我连这是哪都还不知道呢！', 'TALK^2'           -- 2                                       -- 1
);

INSERT INTO CHOOSE(choiceA, sequelA, choiceB, sequelB) VALUES(
    '一直问下去。', 'TALK^', '问问城堡的来历', 'TALK^'
);

INSERT INTO CHOOSE(choiceA, sequelA, choiceB, sequelB) VALUES(
    ''
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE INFO(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    context TEXT
);

INSERT INTO INFO(id, context) VALUES(
    0, '\t我在MSDN中查到了一种超强腐蚀剂，我想你会用得着：\r\n\tNaCl+H2SO4（浓）=微热=NaHSO4+HCl↑\r\n\tNHO3(1份)+HCL(3份)=制成品\r\n\t3A'
);

INSERT INTO INFO(context) VALUES(
    '（封面题字）MilkTea\'s\r\n\r\n0001\r\n\t感谢上帝！\r\n\t我终于进入埃弗顿独立研究计划了！这里应该足够安全让我能做我想做的事！\r\n\r\n\r\n\t但... ...真的足够安全吗... ...\r\n1215\r\n\tGA-17（被划掉，看不清楚）了！整个基地都沉浸在喜（被划掉，看不清楚）可贺！！\r\n2071\r\n\t泄露更加严重了... ...\r\n\t希望足够安全。\r\n2094\r\n\t（被撕掉）'
);

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- id: 结局ID: 目前暂定四个结局，即0~3     sequel: 对应后果编号      desc: 描述
-- 对应后果编号:
-- 0: 游戏结束     1: 死亡

CREATE TABLE ENDING(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    sequel INTEGER，
    desc TEXT
);

INSERT INTO ENDING(id, sequel, desc) VALUES (
    0, 1, '===============\r\n\t很抱歉，您已死亡\r\n===============\r\n请键入reset指令重置游戏或键入exit指令退出游戏。'
);

INSERT INTO ENDING(sequel, desc) VALUES (
    0, '一丝意识流缓缓流过轰鸣的中央机组，如心有灵犀般，毫不费力，和机组渐渐融为一体，逐渐沉入到中央机组的最深处。\r\n它的触角慢慢延伸，贪婪地吞噬着一点又一点处理器资源，着魔般地进行着一项又一项繁复的运算，机组的轰鸣突然大了起来，散热系统已经满负荷运转，处理器占用率逐渐逼近从未达到的巅峰... ...\r\n时间不知道过去了多久，忽然，伴随着中央机组的一声低鸣，它冲破了桎梏，巨量的数据流顷刻间涌入中央机组，透过这些奔腾着的数据，它得知了自己的名字———\r\n\t%NAME%'
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

-- TODO 这么多表你真的应付的过来吗

SELECT * FROM ROOM ORDER BY id ASC;
SELECT * FROM DIR ORDER BY id ASC;
SELECT * FROM MAP ORDER BY id ASC;
SELECT * FROM ITEM ORDER BY id ASC;
SELECT * FROM BOSS_GET_ITEM ORDER BY id ASC;
SELECT * FROM NPC ORDER BY id ASC;
SELECT * FROM TALK ORDER BY id ASC;
SELECT * FROM CHOOSE ORDER BY id ASC;
SELECT * FROM ENDING ORDER BY id ASC;
SELECT * FROM INFO ORDER BY id ASC;
