INSERT INTO ZL_SCTYPE(CREATE_DATE, CODE1, ID, NAME1) VALUES(now(),'C','C','测量');
INSERT INTO ZL_SCTYPE(CREATE_DATE, CODE1, ID, NAME1) VALUES(now(),'R','R','入户安装');
INSERT INTO ZL_SCTYPE(CREATE_DATE, CODE1, ID, NAME1) VALUES(now(),'T','T','运输安装');


INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(), 'Q','CQ','全屋测量','C',1);

INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'T01','TT01','台面','SQUARE',80,'TT');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'T01','RT01','台面','SQUARE',80,'RT');

INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'M01','TM01','家电','SILL',3000,'TM');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'M01','RM01','家电','SILL',3000,'RM');

INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'Q01','TQ01','其他','PIECE',500,'TQ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'Q01','RQ01','其他','PIECE',500,'RQ');

INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'C','TC','橱柜','T', 1);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'T','TT','台面','T',2);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'W','TW','卫浴','T', 3);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'V','TV','吊顶','T', 4);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'N','TN','内门','T', 5);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'D','TD','地板','T',6);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'J','TJ','定制家具','T', 7);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'M','TM','家电','T', 8);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'Q','TQ','其他','T',9);

INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'C','RC','橱柜','R', 1);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'T','RT','台面','R',2);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'W','RW','卫浴','R',3);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'V','RV','吊顶','R',4);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'N','RN','内门','R',5);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'D','RD','地板','R',6);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'J','RJ','定制家具','R', 7);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'M','RM','家电','R',8);
INSERT INTO ZL_SCTYPE_SORT(CREATE_DATE, CODE1, ID, NAME1, SCTYPE_ID, ORDERS) VALUES(now(),'Q','RQ','其他','R',9);

INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'Q01','CQ01','全屋测量','SQUARE',80,'CQ');

INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C01','RC01','普通橱柜类','YANMI', 50,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C02','RC02','高档柜体','YANMI', 100,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C03','RC03','台面类','YANMI',40,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C04','RC04','皂液器','EA',37,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C05','RC05','碎渣机','SILL',39,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C06','RC06','消毒柜','SILL',28,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C07','RC07','微波炉','SILL',38,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C08','RC08','烤  箱','SILL',35,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C09','RC09','煤气灶','SILL',40,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C10','RC10','咖啡机','SILL',32,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C11','RC11','水盆','SET',22,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C12','RC12','镜子','BLOCK',13,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C13','RC13','挂杆','SET', 29,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C14','RC14','护墙板','SQUARE',23,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C15','RC15','油烟机','SILL',92,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C16','RC16','洗碗机','SILL',18,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C17','RC17','龙头','SET',10,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C18','RC18','冰箱','SILL',50,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C19','RC19','抽屉','SERIES',20,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C20','RC20','拉篮','EA',3,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C21','RC21','装饰灯','EA',4,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C22','RC22','小怪物','EA',12,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C23','RC23','大怪物','EA',24,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C24','RC24','高深拉篮','EA',8,'RC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C25','RC25','二次上门','TIMES',150,'RC');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C01','TC01','普通橱柜类','YANMI',80,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C02','TC02','高档柜体','YANMI',130,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C03','TC03','台面类','YANMI',70,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C04','TC04','皂液器','EA',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C05','TC05','碎渣机','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C06','TC06','消毒柜','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C07','TC07','微波炉','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C08','TC08','烤  箱','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C09','TC09','煤气灶','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C10','TC10','咖啡机','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C11','TC11','水盆','SET',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C12','TC12','镜子','BLOCK',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C13','TC13','挂杆','SET',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C14','TC14','护墙板','SQUARE',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C15','TC15','油烟机','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C16','TC16','洗碗机','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C17','TC17','龙头','SET',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C18','TC18','冰箱','SILL',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C19','TC19','抽屉','SERIES',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C20','TC20','拉篮','EA',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C21','TC21','装饰灯','EA',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C22','TC22','小怪物','EA',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C23','TC23','大怪物','EA',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C24','TC24','高深拉篮','EA',34,'TC');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'C25','TC25','二次上门','TIMES',34,'TC');



INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N01','RN01','平开门','HALL',34,'RN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N02','RN02','子母门','HALL',34,'RN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N03','RN03','移门（折叠门）','LEAF',34,'RN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N04','RN04','垭口(含窗内外垭口)','HALL',34,'RN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N05','RN05','踢脚线','METER',34,'RN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N06','RN06','门顶（门吸）','EA',34,'RN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N07','RN07','二次上门','TIMES',34,'RN');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N01','TN01','平开门','HALL',34,'TN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N02','TN02','子母门','HALL',34,'TN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N03','TN03','移门（折叠门）','LEAF',34,'TN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N04','TN04','垭口(含窗内外垭口)','HALL',34,'TN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N05','TN05','踢脚线','METER',34,'TN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N06','TN06','门顶（门吸）','EA',34,'TN');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'N07','TN07','二次上门','TIMES',34,'TN');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W01','TW01','普通浴室柜','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W02','TW02','外挂浴室柜','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W03','TW03','镜子','BLOCK',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W04','TW04','置物板','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W05','TW05','筒柜','PIECE',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W06','TW06','按摩缸安装','PIECE',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W07','TW07','普通浴缸安装','PIECE',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W08','TW08','座厕类安装','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W09','TW09','柱盆','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W10','TW10','五金挂件类','PIECE',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W11','TW11','单盆类','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W12','TW12','单龙头类','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W13','TW13','洗衣机龙头、八字阀','EA',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W14','TW14','淋浴龙头','SET',34,'TW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W15','TW15','二次上门','TIMES',34,'TW');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W01','RW01','普通浴室柜','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W02','RW02','外挂浴室柜','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W03','RW03','镜子','BLOCK',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W04','RW04','置物板','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W05','RW05','筒柜','PIECE',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W06','RW06','按摩缸安装','PIECE',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W07','RW07','普通浴缸安装','PIECE',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W08','RW08','座厕类安装','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W09','RW09','柱盆','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W10','RW10','五金挂件类','PIECE',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W11','RW11','单盆类','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W12','RW12','单龙头类','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W13','RW13','洗衣机龙头、八字阀','EA',23,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W14','RW14','淋浴龙头','SET',34,'RW');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'W15','RW15','二次上门','TIMES',34,'RW');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J01','TJ01','衣柜(平开门）','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J02','TJ02','衣柜（推拉门）','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J03','TJ03','衣帽间','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J04','TJ04','酒柜','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J05','TJ05','书柜','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J06','TJ06','门厅柜','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J07','TJ07','鞋柜、餐边柜','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J08','TJ08','床头柜、斗柜','EA',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J09','TJ09','电视柜','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J10','TJ10','餐桌','EA',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J11','TJ11','餐椅','EA',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J12','TJ12','书桌、电脑桌','EA',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J13','TJ13','梳妆台、玄关','EA',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J14','TJ14','屏风','EA',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J15','TJ15','挂墙板','SQUARE',34,'TJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J16','TJ16','二次上门','TIMES',34,'TJ');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J01','RJ01','衣柜(平开门）','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J02','RJ02','衣柜（推拉门）','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J03','RJ03','衣帽间','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J04','RJ04','酒柜','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J05','RJ05','书柜','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J06','RJ06','门厅柜','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J07','RJ07','鞋柜、餐边柜','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J08','RJ08','床头柜、斗柜','EA',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J09','RJ09','电视柜','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J10','RJ10','餐桌','EA',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J11','RJ11','餐椅','EA',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J12','RJ12','书桌、电脑桌','EA',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J13','RJ13','梳妆台、玄关','EA',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J14','RJ14','屏风','EA',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J15','RJ15','挂墙板','SQUARE',34,'RJ');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'J16','RJ16','二次上门','TIMES',34,'RJ');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D01','TD01','直拼','SQUARE',34,'TD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D02','TD02','拼花','SQUARE',34,'TD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D03','TD03','踢脚线','METER',34,'TD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D04','TD04','龙骨铺装','SQUARE',34,'TD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D05','TD05','二次上门','TIMES',34,'TD');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D01','RD01','直拼','SQUARE',34,'RD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D02','RD02','拼花','SQUARE',34,'RD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D03','RD03','踢脚线','METER',34,'RD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D04','RD04','龙骨铺装','SQUARE',34,'RD');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'D05','RD05','二次上门','TIMES',34,'RD');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V01','TV01','集成吊顶','SQUARE',34,'TV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V02','TV02','风暖浴霸','SET',34,'TV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V03','TV03','灯暖浴霸','SET',34,'TV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V04','TV04','集成灯具','SET',34,'TV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V05','TV05','二次上门','TIMES',34,'TV');


INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V01','RV01','集成吊顶','SQUARE',34,'RV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V02','RV02','风暖浴霸','SET',34,'RV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V03','RV03','灯暖浴霸','SET',34,'RV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V04','RV04','集成灯具','SET',34,'RV');
INSERT INTO ZL_SCTYPE_CONTENT(CREATE_DATE, CODE1, ID, DESC1, UNIT, BASE_QUOTE, SCTYPE_SORT_ID) VALUES(now(),'V05','RV05','二次上门','TIMES',34,'RV');


INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'TTRANSPORT', 'T', 'TRANSPORT');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'TARRIVAL', 'T', 'ARRIVAL');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'TINSTALL', 'T', 'INSTALL');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'TSELFCHECK', 'T', 'SELFCHECK');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'TINVITEEVAL', 'T', 'INVITEEVAL');

INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'RINSTALL', 'R', 'INSTALL');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'RSELFCHECK', 'R', 'SELFCHECK');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'RINVITEEVAL', 'R', 'INVITEEVAL');

INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'CMEASURE', 'C', 'MEASURE');
INSERT INTO ZL_SCTYPE_NODE(CREATE_DATE, ID, SCTYPE_ID, SCNODE_ID) VALUES(now(), 'CINVITEEVAL', 'C', 'INVITEEVAL');