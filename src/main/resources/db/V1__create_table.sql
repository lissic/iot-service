use netservice;
CREATE TABLE `net_alarm`
(
    `id`                varchar(64) NOT NULL COMMENT '主键',
    `alarm_object`      varchar(100) DEFAULT NULL COMMENT '告警对象',
    `alarm_item_id`     varchar(64)  DEFAULT NULL COMMENT '告警项ID',
    `alarm_strategy_id` varchar(100) DEFAULT NULL COMMENT '告警策略ID',
    `alarm_val`         varchar(100) DEFAULT NULL COMMENT '告警值',
    `alarm_time`        datetime     DEFAULT NULL COMMENT '告警时间',
    `alarm_type`        tinyint(1) DEFAULT NULL COMMENT '告警类型：0-系统告警；1-设备告警',
    `status`            tinyint(1) DEFAULT '0' COMMENT '告警状态：0-未处理；1-已处理',
    `device_id`         varchar(64)  DEFAULT NULL COMMENT '设备ID',
    `handle_res`        varchar(255) DEFAULT NULL COMMENT '处理结果',
    `create_user`       varchar(64) NOT NULL COMMENT '创建人',
    `update_user`       varchar(64) NOT NULL COMMENT '更新人',
    `create_time`       datetime    NOT NULL COMMENT '创建时间',
    `update_time`       datetime    NOT NULL COMMENT '更新时间',
    `remarks`           varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警信息表';

CREATE TABLE `net_alarm_item`
(
    `id`            varchar(64) NOT NULL COMMENT '主键',
    `item_code`     varchar(32) NOT NULL COMMENT '报警项编码',
    `item_name`     varchar(64) NOT NULL COMMENT '告警项名称',
    `standard_val`  int(3) DEFAULT NULL COMMENT '标准值',
    `threshold_val` int(3) DEFAULT NULL COMMENT '阈值',
    `create_user`   varchar(64) NOT NULL COMMENT '创建者',
    `update_user`   varchar(64) NOT NULL COMMENT '更新者',
    `create_time`   datetime    NOT NULL COMMENT '创建时间',
    `update_time`   datetime    NOT NULL COMMENT '更新时间',
    `remarks`       varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `item_code` (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警项表';

CREATE TABLE `net_alarm_strategy`
(
    `id`                varchar(64)  NOT NULL COMMENT '主键',
    `name`              varchar(100) NOT NULL COMMENT '告警策略名称',
    `type`              tinyint(1) NOT NULL DEFAULT '0' COMMENT '策略类型：0-主动上报;1-被动监控',
    `status`            tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用：0-否;1-是',
    `alarm_level`       tinyint(1) NOT NULL DEFAULT '0' COMMENT '告警级别：0-非告警;1-一般告警;2-中度告警;3-严重告警',
    `alarm_method`      varchar(16)  DEFAULT NULL COMMENT '告警方式：0-不告警;1-弹窗告警;2-声音告警;3-APP告警;4-短信告警;5-邮件告警;6-电话告警;99-其他',
    `description`       varchar(255) DEFAULT NULL COMMENT '策略描述',
    `alarm_items`       varchar(500) DEFAULT NULL COMMENT '告警项',
    `alarm_rule`        tinyint(1) DEFAULT NULL COMMENT '告警规则：0-重复次数;1-时间间隔',
    `alarm_repeat_time` int(8) DEFAULT NULL COMMENT '告警时间间隔：单位分钟',
    `alarm_repeat_num`  int(2) DEFAULT NULL COMMENT '告警重复次数',
    `create_user`       varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`       varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`       datetime     NOT NULL COMMENT '创建时间',
    `update_time`       datetime     NOT NULL COMMENT '更新时间',
    `remarks`           varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警策略表';

CREATE TABLE `net_assets`
(
    `id`               varchar(64)  NOT NULL COMMENT '主键',
    `asset_code`       varchar(64)  NOT NULL COMMENT '资产编码',
    `category`         tinyint(2) NOT NULL COMMENT '类别',
    `name`             varchar(100) NOT NULL COMMENT '资产名称',
    `model`            varchar(100)  DEFAULT NULL COMMENT '规格型号',
    `source`           varchar(32)   DEFAULT NULL COMMENT '来源',
    `supplier`         varchar(32)   DEFAULT NULL COMMENT '供应商',
    `brand`            varchar(32)   DEFAULT NULL COMMENT '品牌',
    `status`           tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-闲置;1-在用;2-借用;3-维修;4-报废;5-停用',
    `department_id`    varchar(64)  NOT NULL COMMENT '所属部门ID',
    `department_name`  varchar(100)  DEFAULT NULL COMMENT '所属部门名称',
    `use_person_id`    varchar(64)   DEFAULT NULL COMMENT '使用人ID',
    `use_person_name`  varchar(100)  DEFAULT NULL COMMENT '使用人名称',
    `location`         varchar(100)  DEFAULT NULL COMMENT '位置',
    `procurement_time` datetime      DEFAULT NULL COMMENT '采购日期',
    `photo`            varchar(1000) DEFAULT NULL COMMENT '资产照片',
    `create_user`      varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`      varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `update_time`      datetime     NOT NULL COMMENT '更新时间',
    `remarks`          varchar(255)  DEFAULT NULL COMMENT '备注',
    `is_delete`        tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产信息表';

CREATE TABLE `net_battery`
(
    `id`               varchar(64)  NOT NULL COMMENT '主键',
    `battery_id`       varchar(64)  NOT NULL COMMENT '电池序列号',
    `name`             varchar(100) NOT NULL COMMENT '电池名称',
    `device_id`        varchar(64)  NOT NULL COMMENT '设备ID',
    `battery_group_id` varchar(64)  NOT NULL COMMENT '电池组ID',
    `voltage`          double       DEFAULT NULL COMMENT '单体电压',
    `capacity`         double       DEFAULT NULL COMMENT '单体容量',
    `type`             tinyint(2) DEFAULT NULL COMMENT '电池类型',
    `status`           tinyint(4) NOT NULL DEFAULT '0' COMMENT '电池状态：0-正常;1-异常',
    `create_user`      varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`      varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `update_time`      datetime     NOT NULL COMMENT '更新时间',
    `remarks`          varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`        tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池信息表';

CREATE TABLE `net_battery_group`
(
    `id`                    varchar(64)  NOT NULL COMMENT '主键',
    `device_id`             varchar(64)  NOT NULL COMMENT '设备ID',
    `name`                  varchar(100) NOT NULL COMMENT '电池组名称',
    `group_id`              varchar(64)  NOT NULL COMMENT '电池组序号',
    `status`                tinyint(2) DEFAULT '1' COMMENT '状态：0-监控中;1-无效;99-其他',
    `brand`                 varchar(64)  DEFAULT NULL COMMENT '品牌',
    `manufacturer`          varchar(64)  DEFAULT NULL COMMENT '制造商',
    `type`                  tinyint(2) DEFAULT '0' COMMENT '电池类型:0-铅酸蓄电池;1-UPS;2-磷酸铁锂蓄电池;3-超级蓄电池;99-其他',
    `model`                 varchar(64)  DEFAULT NULL COMMENT '规格型号',
    `battery_num`           int(3) DEFAULT NULL COMMENT '电池个数',
    `batch_num`             varchar(64)  DEFAULT NULL COMMENT '生产批号',
    `install_time`          datetime     DEFAULT NULL COMMENT '安装日期',
    `total_voltage`         double       DEFAULT NULL COMMENT '电池组电压',
    `total_electricity`     double       DEFAULT NULL COMMENT '电池组电流',
    `total_capacity`        double       DEFAULT NULL COMMENT '总容量',
    `remaining_capacity`    double       DEFAULT NULL COMMENT '剩余容量',
    `boost_voltage`         double       DEFAULT NULL COMMENT '升电压',
    `emi_gasket_status`     varchar(32)  DEFAULT NULL COMMENT '导电条连接状态',
    `remaining_usable_time` varchar(10)  DEFAULT NULL COMMENT '剩余可用时长',
    `create_user`           varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`           varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`           datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`           datetime     DEFAULT NULL COMMENT '更新时间',
    `remarks`               varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`             tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电池组信息表';

CREATE TABLE `net_device`
(
    `id`              varchar(64)  NOT NULL COMMENT '主键',
    `card_id`         varchar(64)  NOT NULL COMMENT '板卡编号',
    `name`            varchar(64)  NOT NULL COMMENT '设备名称',
    `station_id`      varchar(64)  NOT NULL COMMENT '站点ID',
    `dev_type`        varchar(100) NOT NULL COMMENT '设备型号',
    `battery_grp_num` int(3) DEFAULT '0' COMMENT '电池组数',
    `vol_level`       tinyint(2) NOT NULL COMMENT '电压级别:0-220V;1-110V;2-48V',
    `battery_num`     int(3) DEFAULT NULL COMMENT '电池个数',
    `charge_num`      int(3) DEFAULT '0' COMMENT '充电子模块个数',
    `discharge_num`   int(3) DEFAULT '0' COMMENT '放电子模块个数',
    `longitude`       decimal(10, 3) DEFAULT NULL COMMENT '经度',
    `latitude`        decimal(10, 3) DEFAULT NULL COMMENT '纬度',
    `discharge_type`  tinyint(2) DEFAULT NULL COMMENT '放电类型: 0-逆变放电;1-升压放电;2-PTC放电;3-第三方放电',
    `status`          tinyint(2) DEFAULT '0' COMMENT '设备状态：0-离线;1-在线;2-休眠;3-充电;4-放电;5-故障',
    `monitor_num`     int(3) DEFAULT '0' COMMENT '单体监控子模块个数',
    `address`         varchar(100)   DEFAULT NULL COMMENT '设备地址',
    `last_conn_time`  datetime       DEFAULT NULL COMMENT '最后连接时间',
    `create_user`     varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`     varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`     datetime     NOT NULL COMMENT '创建时间',
    `update_time`     datetime     NOT NULL COMMENT '更新时间',
    `remarks`         varchar(255)   DEFAULT NULL COMMENT '备注',
    `is_delete`       tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备基本信息表';

CREATE TABLE `net_device_config`
(
    `id`             varchar(64) NOT NULL COMMENT '主键',
    `device_id`      varchar(64) NOT NULL COMMENT '设备ID',
    `config_type`    tinyint(2) NOT NULL COMMENT '配置类型：0-网络配置;1-充电配置;2-放电配置;3-监控配置;4-固件配置;5-温度配置;99-其他配置',
    `config_content` json         DEFAULT NULL COMMENT '配置内容',
    `create_user`    varchar(64) NOT NULL COMMENT '创建者',
    `update_user`    varchar(64) NOT NULL COMMENT '更新者',
    `create_time`    datetime    NOT NULL COMMENT '创建时间',
    `update_time`    datetime    NOT NULL COMMENT '更新时间',
    `remarks`        varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`      tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备配置表';

CREATE TABLE `net_device_log`
(
    `id`               varchar(64)  NOT NULL COMMENT '主键ID',
    `device_id`        varchar(64)  NOT NULL COMMENT '设备ID',
    `card_id`          varchar(64)  NOT NULL COMMENT '板卡ID',
    `operation_type`   tinyint(1) DEFAULT NULL COMMENT '操作类型：0-同步状态，1-充电，2-放电，3-上传配置，4-下载配置',
    `content`          varchar(500) DEFAULT NULL COMMENT '操作内容',
    `result`            tinyint(1) DEFAULT '2' COMMENT '操作结果:0-失败；1-成功；2-执行中',
    `operation_person` varchar(100) NOT NULL COMMENT '操作人',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `update_time`      datetime     NOT NULL COMMENT '更新时间',
    `create_user`      varchar(64)  NOT NULL COMMENT '创建人',
    `update_user`      varchar(64)  NOT NULL COMMENT '更新人',
    `remarks`          varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`        tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备操作日志表';

CREATE TABLE `net_device_monitor`
(
    `id`                varchar(64) NOT NULL COMMENT '主键',
    `device_id`         varchar(64) NOT NULL COMMENT '设备ID',
    `rectifier_vol`     double       DEFAULT NULL COMMENT '整流器电压',
    `env_temp`          double       DEFAULT NULL COMMENT '环境温度',
    `battery_group_id`  varchar(500) DEFAULT NULL COMMENT '电池组ID,可多个，用逗号分隔',
    `alarm_num`         int(3) DEFAULT NULL COMMENT '告警数',
    `output_electric`   double       DEFAULT NULL COMMENT '输出电流',
    `electric_quantity` double       DEFAULT NULL COMMENT '电量：kWh',
    `suggested_load`    double       DEFAULT NULL COMMENT '参考负载',
    `real_load`         double       DEFAULT NULL COMMENT '实际负载',
    `total_vol`         double       DEFAULT NULL COMMENT '电池总电压',
    `env_humidity`      double       DEFAULT NULL COMMENT '环境湿度',
    `signal_dbm`        int(11) DEFAULT NULL COMMENT '信号强度',
    `create_user`       varchar(64) NOT NULL COMMENT '创建者',
    `update_user`       varchar(64) NOT NULL COMMENT '更新者',
    `create_time`       datetime    NOT NULL COMMENT '创建时间',
    `update_time`       datetime    NOT NULL COMMENT '更新时间',
    `remarks`           varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备监控表';

CREATE TABLE `net_device_soft`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `name`        varchar(255) DEFAULT NULL COMMENT '固件名称',
    `version`     varchar(100) DEFAULT NULL COMMENT '固件版本',
    `file_path`   varchar(255) DEFAULT NULL COMMENT '软件包地址',
    `utype`       tinyint(1) DEFAULT NULL COMMENT '升级方式：0-主动推送；1-设备拉取',
    `create_user` varchar(64) NOT NULL COMMENT '创建人',
    `update_user` varchar(64) NOT NULL COMMENT '更新人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    `remarks`     varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件升级表';

CREATE TABLE `net_inspection`
(
    `id`                varchar(64)  NOT NULL COMMENT '主键',
    `name`              varchar(100) NOT NULL COMMENT '巡检名称',
    `start_time`        datetime     DEFAULT NULL COMMENT '开始时间',
    `end_time`          datetime     NOT NULL COMMENT '结束时间',
    `sign_in`           tinyint(1) NOT NULL DEFAULT '0' COMMENT '签到方式：0-现场定位;1-现场拍照',
    `status`            tinyint(1) NOT NULL DEFAULT '0' COMMENT '巡检状态：0-未开始;1-进行中;2-已完成',
    `real_inspect_time` datetime     DEFAULT NULL COMMENT '实际巡检时间',
    `check_person`      varchar(64)  DEFAULT NULL COMMENT '巡检人',
    `check_result`      varchar(500) DEFAULT NULL COMMENT '巡检结果',
    `check_photo`       varchar(800) DEFAULT NULL COMMENT '巡检结果照片',
    `create_user`       varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`       varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`       datetime     NOT NULL COMMENT '创建时间',
    `update_time`       varchar(64)  NOT NULL COMMENT '更新时间',
    `remarks`           varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='巡检任务表';

CREATE TABLE `net_knowledge_repository`
(
    `id`           varchar(64)  NOT NULL COMMENT '主键',
    `title`        varchar(100) NOT NULL COMMENT '标题',
    `synopsis`     varchar(255) DEFAULT NULL COMMENT '简介',
    `keywords`     varchar(100) DEFAULT NULL COMMENT '关键字',
    `category`     tinyint(1) DEFAULT NULL COMMENT '知识分类',
    `status`       tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-隐藏;1-显示',
    `review_count` int(8) NOT NULL DEFAULT '0' COMMENT '浏览次数',
    `content`      text COMMENT '内容',
    `attach`       varchar(800) DEFAULT NULL COMMENT '附件',
    `create_user`  varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`  varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `update_time`  datetime     NOT NULL COMMENT '更新时间',
    `remarks`      text COMMENT '备注',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识库表';

CREATE TABLE `net_task`
(
    `id`              varchar(64)  NOT NULL COMMENT '主键',
    `name`            varchar(100) NOT NULL COMMENT '任务名',
    `description`     varchar(255) DEFAULT NULL COMMENT '描述',
    `invoke_target`   varchar(255) DEFAULT NULL COMMENT '调用目标',
    `cron_expression` varchar(100) DEFAULT NULL COMMENT 'Cron表达式',
    `status`          tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务状态：0-暂停;1-运行中',
    `last_exec_time`  datetime     DEFAULT NULL COMMENT '上次执行时间',
    `next_exec_time`  datetime     DEFAULT NULL COMMENT '下次执行时间',
    `create_user`     varchar(64)  NOT NULL COMMENT '创建者',
    `update_user`     varchar(64)  NOT NULL COMMENT '更新者',
    `create_time`     datetime     NOT NULL COMMENT '创建时间',
    `update_time`     datetime     NOT NULL COMMENT '更新时间',
    `remarks`         varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`       tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务表';

CREATE TABLE `net_upgrade_record`
(
    `id`          varchar(64) NOT NULL COMMENT '主键ID',
    `progress`    int(11) DEFAULT NULL COMMENT '升级进度',
    `status`      tinyint(1) DEFAULT '0' COMMENT '升级状态：0-未开始;1-升级中；2-升级完成；3-升级失败',
    `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称',
    `device_id`   varchar(64)  DEFAULT NULL COMMENT '设备ID',
    `soft_id`     varchar(64)  DEFAULT NULL COMMENT '固件ID',
    `create_user` varchar(64) NOT NULL COMMENT '创建人',
    `update_user` varchar(64) NOT NULL COMMENT '更新人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    `remarks`     varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件升级记录表';

CREATE TABLE `sys_department`
(
    `id`          varchar(64)  NOT NULL COMMENT '编号',
    `parent_id`   varchar(64)  NOT NULL COMMENT '父级编号',
    `parent_ids`  varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
    `name`        varchar(100) NOT NULL COMMENT '名称',
    `address`     varchar(255)  DEFAULT NULL COMMENT '联系地址',
    `zip_code`    varchar(100)  DEFAULT NULL COMMENT '邮政编码',
    `master`      varchar(100)  DEFAULT NULL COMMENT '负责人',
    `phone`       varchar(200)  DEFAULT NULL COMMENT '电话',
    `avaliable`   tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用：0-否，1-是',
    `create_user` varchar(64)  NOT NULL COMMENT '创建者',
    `update_user` varchar(64)  NOT NULL COMMENT '更新者',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_time` datetime     NOT NULL COMMENT '更新时间',
    `remarks`     varchar(255)  DEFAULT NULL COMMENT '备注信息',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sys_department_name_IDX` (`name`) USING BTREE,
    KEY           `sys_office_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

CREATE TABLE `sys_log`
(
    `id`           varchar(64) NOT NULL,
    `title`        varchar(100)  DEFAULT NULL COMMENT '标题',
    `request_url`  varchar(100)  DEFAULT NULL COMMENT '请求地址',
    `type`         varchar(64)   DEFAULT NULL COMMENT '类型',
    `biz_type`     varchar(64)   DEFAULT NULL COMMENT '业务类型',
    `content`      varchar(1000) DEFAULT NULL COMMENT '日志内容',
    `client_ip`    varchar(100)  DEFAULT NULL COMMENT '客户端IP',
    `is_exception` tinyint(1) DEFAULT NULL COMMENT '是否异常：0-否，1-是',
    `execute_time` bigint(20) DEFAULT NULL COMMENT '执行时间，单位毫秒',
    `operator`     varchar(64)   DEFAULT NULL COMMENT '操作人',
    `create_user`  varchar(64)   DEFAULT NULL COMMENT '创建人',
    `update_user`  varchar(64)   DEFAULT NULL COMMENT '更新人',
    `create_time`  datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime      DEFAULT NULL COMMENT '更新时间',
    `remarks`      varchar(255)  DEFAULT NULL COMMENT '备注',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

CREATE TABLE `sys_menu`
(
    `id`            varchar(64)  NOT NULL COMMENT '菜单id',
    `name`          varchar(255) NOT NULL COMMENT '名称',
    `resource_type` varchar(64)  DEFAULT NULL COMMENT '资源类型：menu/button',
    `url`           varchar(255) NOT NULL COMMENT '路径',
    `permission`    varchar(255) DEFAULT NULL COMMENT '权限标识',
    `parent_id`     varchar(64)  DEFAULT NULL COMMENT '父ID',
    `parent_ids`    varchar(500) DEFAULT NULL COMMENT '父ID集合',
    `available`     tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用：0-否，1-是',
    `create_user`   varchar(64)  NOT NULL COMMENT '创建人',
    `update_user`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    `create_time`   datetime     NOT NULL COMMENT '创建时间',
    `update_time`   datetime     NOT NULL COMMENT '更新时间',
    `remarks`       varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE TABLE `sys_role`
(
    `id`          varchar(64)  NOT NULL COMMENT '角色id',
    `role`        varchar(255) NOT NULL COMMENT '角色名称',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `available`   tinyint(1) DEFAULT '1' COMMENT '是否可用：0-不可用；1-可用',
    `is_admin`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为管理员：0-否；1-是',
    `create_user` varchar(64)  NOT NULL COMMENT '创建人',
    `update_user` varchar(64)  NOT NULL COMMENT '更新人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_time` datetime     NOT NULL COMMENT '更新时间',
    `remarks`     varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`),
    UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `sys_role_menu`
(
    `role_id` varchar(64)  NOT NULL COMMENT '角色编号',
    `menu_id` varchar(500) NOT NULL COMMENT '菜单编号',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

CREATE TABLE `sys_setting`
(
    `id`          varchar(64) NOT NULL COMMENT '主键',
    `set_option`  varchar(100) DEFAULT NULL COMMENT '设置项',
    `set_content` varchar(100) DEFAULT NULL COMMENT '设置内容',
    `create_user` varchar(64) NOT NULL COMMENT '创建人',
    `update_user` varchar(64) NOT NULL COMMENT '更新人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    `remarks`     varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

CREATE TABLE `sys_station`
(
    `id`        varchar(64)  NOT NULL COMMENT '编号',
    `area`      varchar(64)  NOT NULL COMMENT '所属区域',
    `name`      varchar(100) NOT NULL COMMENT '站点名称',
    `code`      varchar(100) DEFAULT NULL COMMENT '站点编码',
    `type`      tinyint(1) DEFAULT NULL COMMENT '站点类型',
    `longitude` double(10, 6
) DEFAULT NULL COMMENT '经度',
  `latitude` double(10,6) DEFAULT NULL COMMENT '纬度',
  `create_user` varchar(64) NOT NULL COMMENT '创建者',
  `update_user` varchar(64) NOT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站点表';

CREATE TABLE `sys_user`
(
    `id`           varchar(64)  NOT NULL COMMENT '用户id',
    `nick_name`    varchar(100) NOT NULL COMMENT '用户昵称',
    `area`         varchar(100) DEFAULT NULL COMMENT '所属区域',
    `sex`          tinyint(1) NOT NULL DEFAULT '1' COMMENT '性别：0-女;1-男',
    `password`     varchar(100) NOT NULL COMMENT '密码:aes加密',
    `email`        varchar(100) DEFAULT NULL COMMENT '邮件',
    `phone`        varchar(32)  DEFAULT NULL COMMENT '电话',
    `photo`        varchar(100) DEFAULT NULL COMMENT '头像',
    `introduction` varchar(255) DEFAULT NULL COMMENT '简介',
    `login_name`   varchar(32)  NOT NULL COMMENT '登录名',
    `dept_id`      varchar(64)  DEFAULT NULL COMMENT '部门id',
    `status`       tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态：1-正常，2-锁定',
    `create_user`  varchar(64)  NOT NULL COMMENT '创建人',
    `update_user`  varchar(64)  NOT NULL COMMENT '更新人',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `update_time`  datetime     NOT NULL COMMENT '更新时间',
    `remarks`      varchar(255) DEFAULT NULL COMMENT '备注',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `sys_user_role`
(
    `user_id` varchar(64)  NOT NULL COMMENT '用户编号',
    `role_id` varchar(500) NOT NULL COMMENT '角色编号',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';
