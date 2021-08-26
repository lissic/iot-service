-- 初始化菜单表
INSERT INTO netservice.sys_menu (id, name, resource_type, url, permission, parent_id, parent_ids, available,
                                 create_user, update_user, create_time, update_time, remarks, is_delete)
VALUES ('10435f08286564df713a4a093b266a3d', '设备管理', 'menu', '/devices', '*', '', NULL, 1, '1', '1',
        '2021-06-09 09:30:20.0', '2021-07-05 17:30:20.0', NULL, 0),
       ('142308492da8a4bf0d21bc9a59b21ace', '设备概览', 'menu', '/statistic/devOverview', '*',
        'a5129bd7430120caf2647725f78a0164', '', 1, '1', '1', '2021-06-08 10:54:12.0', '2021-06-08 10:54:12.0', '', 0),
       ('2abd0bf9b9196de20b361ef4832f8427', '告警项', 'menu', '/sys/alarm/item/list', '*',
        '3ed1ba767af416ee31220b608cccf5ef', '', 1, '1', '1', '2021-06-09 10:45:41.0', '2021-06-09 10:45:41.0', '告警项列表',
        0),
       ('372467f17518038d18c028a28b57d64f', '系统监控', 'menu', '/statistic/sysMonitor', '*',
        'a5129bd7430120caf2647725f78a0164', '', 1, '1', '1', '2021-06-03 18:47:53.0', '2021-06-03 18:47:53.0', NULL, 0),
       ('3ed1ba767af416ee31220b608cccf5ef', '告警设置', 'menu', '/sys/alarm', '*', 'e8e6af6448cad1d8199ad3b83c3933aa', '',
        1, '1', '1', '2021-06-09 10:44:46.0', '2021-06-09 10:44:46.0', '告警设置', 0),
       ('3ed1ba767af416ee31220b608cccf5eg', '运维管理', 'menu', '/sys/operations', '*', 'e8e6af6448cad1d8199ad3b83c3933aa',
        '', 1, '1', '1', '2021-06-09 10:44:46.0', '2021-06-09 10:44:46.0', '告警设置', 0),
       ('3ed1ba767af416ee31220b608cccf5eh', '定时任务', 'menu', '/sys/operations/task', '*',
        '3ed1ba767af416ee31220b608cccf5eg', '', 1, '1', '1', '2021-06-09 10:44:46.0', '2021-06-09 10:44:46.0', '告警设置',
        0),
       ('3ed1ba767af416ee31220b608cccf5ei', '系统告警', 'menu', '/sys/clear', '*', '3ed1ba767af416ee31220b608cccf5eg', '',
        1, '1', '1', '2021-06-09 10:44:46.0', '2021-06-09 10:44:46.0', '告警设置', 0),
       ('4757ed2e0b7a59e67f540f3359398f3e', '告警策略', 'menu', '/sys/alarm/strategy/list', '*',
        '3ed1ba767af416ee31220b608cccf5ef', '', 1, '1', '1', '2021-06-09 10:45:20.0', '2021-06-09 10:45:20.0', '告警策略列表',
        0),
       ('579b2a3d665ecb6d9feb898070c549af', '菜单管理', 'menu', '/sys/menu/list', '*', 'e8e6af6448cad1d8199ad3b83c3933aa',
        '', 1, '1', '1', '2021-06-09 10:41:54.0', '2021-07-07 14:29:54.0', '菜单列表', 0);
INSERT INTO netservice.sys_menu (id, name, resource_type, url, permission, parent_id, parent_ids, available,
                                 create_user, update_user, create_time, update_time, remarks, is_delete)
VALUES ('824a463c0b816274873bf897227bd70c', '日志管理', 'button', '/log/list', '*', '', NULL, 1, '1', '1',
        '2021-06-10 16:52:20.0', '2021-07-05 17:41:59.0', NULL, 0),
       ('a5129bd7430120caf2647725f78a0164', '统计分析', 'menu', '/statistic', '*', NULL, '', 1, '1', '1',
        '2021-06-08 10:47:54.0', '2021-08-11 13:47:16.0', '统计分析', 0),
       ('c951e799801408f1dc3c4364fd365401', '部门管理', 'menu', '/sys/dept/list', '*', 'e8e6af6448cad1d8199ad3b83c3933aa',
        '', 1, '1', '1', '2021-06-09 10:41:06.0', '2021-07-07 14:29:52.0', '部门列表', 0),
       ('e16e793c88b270249bb14e50b2917cd4', '用户管理', 'menu', '/sys/user/list', '*', 'e8e6af6448cad1d8199ad3b83c3933aa',
        '', 1, '1', '1', '2021-06-09 10:35:06.0', '2021-07-07 14:29:51.0', '用户列表', 0),
       ('e8e6af6448cad1d8199ad3b83c3933aa', '系统管理', 'menu', '/sys', '*', '', '', 1, '1', '1', '2021-06-09 10:33:47.0',
        '2021-07-07 14:29:50.0', '系统设置', 0),
       ('f112e6a75d38b34c8110f9fe94f3ac27', '角色管理', 'menu', '/sys/role/list', '*', 'e8e6af6448cad1d8199ad3b83c3933aa',
        '', 1, '1', '1', '2021-06-09 10:41:25.0', '2021-07-07 14:29:53.0', '角色列表', 0),
       ('f112e6a75d38b34c8110f9fe94f3ac28', '站点管理', 'menu', '/sys/station/list', '*',
        'e8e6af6448cad1d8199ad3b83c3933aa', '', 1, '1', '1', '2021-06-09 10:42:25.0', '2021-07-07 14:29:53.0', '角色列表',
        0),
       ('f112e6a75d38b34c8110f9fe94f3ac29', '系统设置', 'menu', '/sys/setting', '*', 'e8e6af6448cad1d8199ad3b83c3933aa', '',
        1, '1', '1', '2021-06-09 10:43:25.0', '2021-07-07 14:29:53.0', '角色列表', 0),
       ('f920a03840507097b4fa0f85bf3ec599', '设备列表', 'menu', '/devices/devicesList', '*',
        '10435f08286564df713a4a093b266a3d', NULL, 1, '1', '1', '2021-07-05 17:30:05.0', '2021-07-05 17:30:20.0', NULL,
        0),
       ('f920a03840507097b4fa0f85bf3ec600', '设备巡检', 'menu', '/devices/inspection', '*',
        '10435f08286564df713a4a093b266a3d', NULL, 1, '1', '1', '2021-07-05 17:30:20.0', '2021-07-05 17:30:20.0', NULL,
        0);
INSERT INTO netservice.sys_menu (id, name, resource_type, url, permission, parent_id, parent_ids, available,
                                 create_user, update_user, create_time, update_time, remarks, is_delete)
VALUES ('f920a03840507097b4fa0f85bf3ec601', '固件升级', 'menu', '/devices/hardwareupgrade', '*',
        '10435f08286564df713a4a093b266a3d', NULL, 1, '1', '1', '2021-07-05 17:30:30.0', '2021-07-05 17:30:20.0', NULL,
        0),
       ('f920a03840507097b4fa0f85bf3ec602', '设备监控', 'menu', '/devices', '*', '10435f08286564df713a4a093b266a3d', NULL,
        1, '1', '1', '2021-07-05 17:30:10.0', '2021-07-05 17:30:20.0', NULL, 0),
       ('f920a03840507097b4fa0f85bf3ec603', '资产管理', 'menu', '/assets', '*', '', NULL, 1, '1', '1',
        '2021-07-05 17:30:10.0', '2021-07-05 17:30:20.0', NULL, 0),
       ('f920a03840507097b4fa0f85bf3ec604', '知识库', 'menu', '/knowledge', '*', '', NULL, 1, '1', '1',
        '2021-07-05 17:30:10.0', '2021-07-05 17:30:20.0', NULL, 0),
       ('f920a03840507097b4fa0f85bf3ed600', '设备告警', 'menu', '/devices/alarm', '*', '10435f08286564df713a4a093b266a3d',
        NULL, 1, '1', '1', '2021-07-05 17:30:20.0', '2021-07-05 17:30:20.0', NULL, 0);
-- 初始化用户表
INSERT INTO netservice.sys_user (id, nick_name, area, sex, password, email, phone, photo, introduction, login_name,
                                 dept_id, status, create_user, update_user, create_time, update_time, remarks,
                                 is_delete)
VALUES ('1', 'admin', '', 1, '80ae32c120cdcaa6b118d4302c286ff2', '', '','',
        'good good study !  day day up!', 'admin', '038809c28e30bfa4119de5242866b778', 1, '', '1',
        '2021-05-13 15:18:35.0', '2021-08-18 14:44:11.0', NULL, 0);
-- 初始化角色表
INSERT INTO netservice.sys_role (id, `role`, description, available, is_admin, create_user, update_user, create_time,
                                 update_time, remarks, is_delete)
VALUES ('1', 'admin', '超级管理员', 1, 1, '1', '1', '2021-06-04 10:15:06.0', '2021-08-10 14:19:53.0', '系统管理员', 0);
-- 初始化用户角色关联表
INSERT INTO netservice.sys_user_role (user_id, role_id) VALUES('1', '1');
-- 初始化角色菜单关联表
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '2abd0bf9b9196de20b361ef4832f8427');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '372467f17518038d18c028a28b57d64f');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '3ed1ba767af416ee31220b608cccf5ef');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '3ed1ba767af416ee31220b608cccf5eg');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '3ed1ba767af416ee31220b608cccf5eh');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '3ed1ba767af416ee31220b608cccf5ei');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '4757ed2e0b7a59e67f540f3359398f3e');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '579b2a3d665ecb6d9feb898070c549af');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', '824a463c0b816274873bf897227bd70c');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'a5129bd7430120caf2647725f78a0164');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'c951e799801408f1dc3c4364fd365401');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'e16e793c88b270249bb14e50b2917cd4');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'e8e6af6448cad1d8199ad3b83c3933aa');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f112e6a75d38b34c8110f9fe94f3ac27');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f112e6a75d38b34c8110f9fe94f3ac28');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f112e6a75d38b34c8110f9fe94f3ac29');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ec599');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ec600');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ec601');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ec602');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ec603');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ec604');
INSERT INTO netservice.sys_role_menu (role_id, menu_id) VALUES('1', 'f920a03840507097b4fa0f85bf3ed600');
-- 初始化告警项表
INSERT INTO netservice.net_alarm_item (id, item_code, item_name, standard_val, threshold_val, create_user, update_user,
                                       create_time, update_time, remarks, is_delete)
VALUES ('4b5eec1d56f79fba4413a51597a1d133', '3001', 'JVM过载', 20, 80, '1', '1', '2021-06-23 09:47:23.0',
        '2021-08-03 14:04:27.0', '单位%', 0);
INSERT INTO netservice.net_alarm_item (id, item_code, item_name, standard_val, threshold_val, create_user, update_user,
                                       create_time, update_time, remarks, is_delete)
VALUES ('4fbbb17bcc5394f062309e15845e943d', '3003', '内存过载', 50, 70, '1', '1', '2021-07-01 16:37:02.0',
        '2021-07-27 17:58:00.0', '单位%', 0);
INSERT INTO netservice.net_alarm_item (id, item_code, item_name, standard_val, threshold_val, create_user, update_user,
                                       create_time, update_time, remarks, is_delete)
VALUES ('78d3f8c605580f0b61c180b2d7ffdc35', '3002', 'Cpu过载', 10, 50, '1', '1', '2021-07-01 16:36:35.0',
        '2021-07-27 18:12:04.0', '单位%', 0);

