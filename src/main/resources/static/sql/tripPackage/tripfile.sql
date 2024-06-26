DROP TABLE IF EXISTS tripfile;

create table tripfile
(
    file_id      VARCHAR(255) primary key,
    main_img     varchar(255) null,
    intro_img    varchar(255) null,
    route_img    varchar(255) null,
    schedule_img varchar(255) null,
    detail_img   varchar(255) null
)

ALTER TABLE tripfile
    MODIFY main_img VARCHAR(500) NULL,
    MODIFY intro_img VARCHAR(500) NULL,
    MODIFY route_img VARCHAR(500) NULL,
    MODIFY schedule_img VARCHAR(500) NULL,
    MODIFY detail_img VARCHAR(500) NULL;

SELECT * FROM tripfile;

UPDATE tripfile
SET main_img = "https://mbtiawsbucket.s3.ap-northeast-2.amazonaws.com/1718511993394-%E1%84%8B%E1%85%A7%E1%86%A8%E1%84%89%E1%85%A1%E1%84%90%E1%85%AE%E1%84%8B%E1%85%A5.png"
WHERE file_id = "fd411210-4a3f-430b-ad02-d2a77866d259";

UPDATE tripfile
SET main_img = "1"
WHERE file_id = "fd411210-4a3f-430b-ad02-d2a77866d259";


INSERT INTO tripfile (file_id, main_img, intro_img, route_img, schedule_img, detail_img)
VALUES
    ('1', '/image/product/mainIMG/WEU1.png', '/image/product/introIMG/Wintro1.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail1.png'),
    ('2', '/image/product/mainIMG/WEU2.png', '/image/product/introIMG/Wintro2.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail2.png'),
    ('3', '/image/product/mainIMG/WEU3.png', '/image/product/introIMG/Wintro3.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail3.png'),
    ('4', '/image/product/mainIMG/WEU4.png', '/image/product/introIMG/Wintro4.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail4.png'),
    ('5', '/image/product/mainIMG/WEU5.png', '/image/product/introIMG/Wintro5.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail5.png'),
    ('6', '/image/product/mainIMG/WEU6.png', '/image/product/introIMG/Wintro6.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail6.png'),
    ('7', '/image/product/mainIMG/WEU7.png', '/image/product/introIMG/Wintro7.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail7.png'),
    ('8', '/image/product/mainIMG/WEU8.png', '/image/product/introIMG/Wintro8.png', '/image/product/routeIMG/WEUroute.png', '/image/product/scheduleIMG/WEUschedule.png', '/image/product/detailIMG/Wdetail8.png'),
    ('9', '/image/product/mainIMG/CEU1.png', '/image/product/introIMG/CEUIntro.png', '/image/product/routeIMG/CEUroute.png', '/image/product/scheduleIMG/CEUschedule.png', '/image/product/detailIMG/CEUdetail1.png'),
    ('10', '/image/product/mainIMG/CEU2.png', '/image/product/introIMG/CEUIntro.png', '/image/product/routeIMG/CEUroute.png', '/image/product/scheduleIMG/CEUschedule.png', '/image/product/detailIMG/CEUdetail2.png'),
    ('11', '/image/product/mainIMG/CEU3.png', '/image/product/introIMG/CEUIntro.png', '/image/product/routeIMG/CEUroute.png', '/image/product/scheduleIMG/CEUschedule.png', '/image/product/detailIMG/CEUdetail3.png'),
    ('12', '/image/product/mainIMG/CEU4.png', '/image/product/introIMG/CEUIntro.png', '/image/product/routeIMG/CEUroute.png', '/image/product/scheduleIMG/CEUschedule.png', '/image/product/detailIMG/CEUdetail4.png'),
    ('13', '/image/product/mainIMG/CEU5.png', '/image/product/introIMG/CEUIntro.png', '/image/product/routeIMG/CEUroute.png', '/image/product/scheduleIMG/CEUschedule.png', '/image/product/detailIMG/CEUdetail5.png'),
    ('14', '/image/product/mainIMG/SEU1.png', '/image/product/introIMG/SEUIntro.png', '/image/product/routeIMG/SEUroute.png', '/image/product/scheduleIMG/SEUschedule.png', '/image/product/detailIMG/SEUdetail1.png'),
    ('15', '/image/product/mainIMG/SEU2.png', '/image/product/introIMG/SEUIntro.png', '/image/product/routeIMG/SEUroute.png', '/image/product/scheduleIMG/SEUschedule.png', '/image/product/detailIMG/SEUdetail2.png'),
    ('16', '/image/product/mainIMG/SEU3.png', '/image/product/introIMG/SEUIntro.png', '/image/product/routeIMG/SEUroute.png', '/image/product/scheduleIMG/SEUschedule.png', '/image/product/detailIMG/SEUdetail3.png'),
    ('17', '/image/product/mainIMG/SEU4.png', '/image/product/introIMG/SEUIntro.png', '/image/product/routeIMG/SEUroute.png', '/image/product/scheduleIMG/SEUschedule.png', '/image/product/detailIMG/SEUdetail4.png'),
    ('18', '/image/product/mainIMG/SEU5.png', '/image/product/introIMG/SEUIntro.png', '/image/product/routeIMG/SEUroute.png', '/image/product/scheduleIMG/SEUschedule.png', '/image/product/detailIMG/SEUdetail5.png'),
    ('19', '/image/product/mainIMG/SEU6.png', '/image/product/introIMG/SEUIntro.png', '/image/product/routeIMG/SEUroute.png', '/image/product/scheduleIMG/SEUschedule.png', '/image/product/detailIMG/SEUdetail6.png');