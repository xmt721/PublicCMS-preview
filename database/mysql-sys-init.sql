SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `user_id` bigint(20) default NULL COMMENT '用户ID',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `channel` varchar(50) NOT NULL default 'web' COMMENT '登陆渠道',
  `result` tinyint(1) NOT NULL COMMENT '结果',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `error_password` varchar(100) default NULL COMMENT '错误密码',
  PRIMARY KEY  (`id`),
  KEY `result` (`result`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='登陆日志';

-- ----------------------------
-- Records of log_login
-- ----------------------------

-- ----------------------------
-- Table structure for log_operate
-- ----------------------------
DROP TABLE IF EXISTS `log_operate`;
CREATE TABLE `log_operate` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) default NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '操作取到',
  `operate` varchar(40) NOT NULL COMMENT '操作',
  `ip` varchar(64) default NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `content` text NOT NULL COMMENT '内容',
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`),
  KEY `operate` (`operate`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of log_operate
-- ----------------------------

-- ----------------------------
-- Table structure for log_task
-- ----------------------------
DROP TABLE IF EXISTS `log_task`;
CREATE TABLE `log_task` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `task_id` int(11) NOT NULL COMMENT '任务',
  `begintime` datetime NOT NULL COMMENT '开始时间',
  `endtime` datetime default NULL COMMENT '结束时间',
  `success` tinyint(1) NOT NULL COMMENT '执行成功',
  `result` longtext COMMENT '执行结果',
  PRIMARY KEY  (`id`),
  KEY `task_id` (`task_id`),
  KEY `success` (`success`),
  KEY `site_id` (`site_id`),
  KEY `begintime` (`begintime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='任务计划日志';

-- ----------------------------
-- Records of log_task

-- ----------------------------
-- Table structure for log_upload
-- ----------------------------
DROP TABLE IF EXISTS `log_upload`;
CREATE TABLE `log_upload` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '操作取到',
  `image` tinyint(1) NOT NULL COMMENT '图片',
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `ip` varchar(64) default NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `file_path` varchar(500) NOT NULL COMMENT '文件路径',
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`),
  KEY `image` (`image`),
  KEY `file_size` (`file_size`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='上传日志';

-- ----------------------------
-- Records of log_upload
-- ----------------------------

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `app_secret` varchar(50) NOT NULL COMMENT 'APP secret',
  `authorized_apis`  text NULL COMMENT '授权API',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key` (`app_key`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app
-- ----------------------------

-- ----------------------------
-- Table structure for sys_app_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_client`;
CREATE TABLE `sys_app_client` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `channel` varchar(20) NOT NULL COMMENT '渠道',
  `uuid` varchar(50) NOT NULL COMMENT '唯一标识',
  `user_id` bigint(20) default NULL COMMENT '绑定用户',
  `client_version` varchar(50) NOT NULL COMMENT '版本',
  `last_login_date` datetime default NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(64) default NULL COMMENT '上次登录IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `site_id` (`site_id`,`uuid`,`channel`),
  KEY `user_id` (`user_id`),
  KEY `disabled` (`disabled`),
  KEY `create_date` (`create_date`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app_client
-- ----------------------------

-- ----------------------------
-- Table structure for sys_app_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_token`;
CREATE TABLE `sys_app_token` (
  `auth_token` varchar(40) NOT NULL COMMENT '授权验证',
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`auth_token`),
  KEY `app_id` (`app_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app_token
-- ----------------------------

-- ----------------------------
-- Table structure for sys_cluster
-- ----------------------------
DROP TABLE IF EXISTS `sys_cluster`;
CREATE TABLE `sys_cluster` (
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `heartbeat_date` datetime NOT NULL COMMENT '心跳时间',
  `master` tinyint(1) NOT NULL COMMENT '是否管理',
  `cms_version` varchar(20) default NULL,
  PRIMARY KEY  (`uuid`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='服务器集群';

-- ----------------------------
-- Records of sys_cluster
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_data`;
CREATE TABLE `sys_config_data` (
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `code` varchar(50) NOT NULL COMMENT '配置项编码',
  `item_code` varchar(50) NOT NULL COMMENT '配置项编码',
  `data` longtext NOT NULL COMMENT '值',
  PRIMARY KEY  (`site_id`,`code`,`item_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='站点配置';

-- ----------------------------
-- Records of sys_config_data
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_id` int(11) default NULL COMMENT '父部门ID',
  `description` varchar(300) default NULL COMMENT '描述',
  `user_id` bigint(20) default NULL COMMENT '负责人',
  `owns_all_category` tinyint(1) NOT NULL COMMENT '拥有全部分类权限',
  `owns_all_page` tinyint(1) NOT NULL COMMENT '拥有全部页面权限',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '1', '总公司', null, '集团总公司', '1', '1', '1');
INSERT INTO `sys_dept` VALUES ('2', '2', '技术部', null, '', '3', '1', '1');

-- ----------------------------
-- Table structure for sys_dept_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_category`;
CREATE TABLE `sys_dept_category` (
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  PRIMARY KEY  (`dept_id`,`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='部门分类';

-- ----------------------------
-- Records of sys_dept_category
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept_page
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_page`;
CREATE TABLE `sys_dept_page` (
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `page` varchar(255) NOT NULL COMMENT '页面',
  PRIMARY KEY  (`dept_id`,`page`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='部门页面';

-- ----------------------------
-- Records of sys_dept_page
-- ----------------------------

-- ----------------------------
-- Table structure for sys_domain
-- ----------------------------
DROP TABLE IF EXISTS `sys_domain`;
CREATE TABLE `sys_domain` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT '域名',
  `site_id` int(11) NOT NULL COMMENT '站点',
  `path` varchar(255) default NULL COMMENT '路径',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='域名';

-- ----------------------------
-- Records of sys_domain
-- ----------------------------
INSERT INTO `sys_domain` VALUES ('1', 'dev.publiccms.com', '1', '');
INSERT INTO `sys_domain` VALUES ('2', 'member.dev.publiccms.com', '1', '/member/');
INSERT INTO `sys_domain` VALUES ('3', 'search.dev.publiccms.com', '1', '/search/');
INSERT INTO `sys_domain` VALUES ('4', 'site2.dev.publiccms.com', '2', '');

-- ----------------------------
-- Table structure for sys_email_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_token`;
CREATE TABLE `sys_email_token` (
  `auth_token` varchar(40) NOT NULL COMMENT '验证码',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `email` varchar(100) NOT NULL COMMENT '邮件地址',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`auth_token`),
  KEY `create_date` (`create_date`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='邮件地址验证日志';

-- ----------------------------
-- Records of sys_email_token
-- ----------------------------

-- ----------------------------
-- Table structure for sys_extend
-- ----------------------------
DROP TABLE IF EXISTS `sys_extend`;
CREATE TABLE `sys_extend` (
  `id` int(11) NOT NULL auto_increment,
  `item_type` varchar(20) NOT NULL COMMENT '扩展类型',
  `item_id` int(11) NOT NULL COMMENT '扩展项目ID',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_extend
-- ----------------------------
INSERT INTO `sys_extend` VALUES ('1', 'category', '19');

-- ----------------------------
-- Table structure for sys_extend_field
-- ----------------------------
DROP TABLE IF EXISTS `sys_extend_field`;
CREATE TABLE `sys_extend_field` (
  `extend_id` int(11) NOT NULL COMMENT '扩展ID',
  `code` varchar(20) NOT NULL COMMENT '编码',
  `required` tinyint(1) NOT NULL COMMENT '是否必填',
  `maxlength` int(11) default NULL,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `description` varchar(100) default NULL COMMENT '解释',
  `input_type` varchar(20) NOT NULL COMMENT '表单类型',
  `default_value` varchar(50) default NULL COMMENT '默认值',
  `dictionary_type` varchar(20) default NULL,
  `dictionary_id` varchar(20) default NULL,
  `sort` int(11) NOT NULL default '0' COMMENT '顺序',
  PRIMARY KEY  (`extend_id`,`code`),
  KEY `sort` (`sort`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='扩展';

-- ----------------------------
-- Records of sys_extend_field
-- ----------------------------

-- ----------------------------
-- Table structure for sys_moudle
-- ----------------------------
DROP TABLE IF EXISTS `sys_moudle`;
CREATE TABLE `sys_moudle` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `url` varchar(255) default NULL COMMENT '链接地址',
  `authorized_url` text COMMENT '授权地址',
  `attached` varchar(300) default NULL COMMENT '标题附加',
  `parent_id` int(11) default NULL COMMENT '父模块',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY  (`id`),
  KEY `url` (`url`),
  KEY `parent_id` (`parent_id`),
  KEY `sort` (`sort`)
) ENGINE=MyISAM AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='模块';

-- ----------------------------
-- Records of sys_moudle
-- ----------------------------
INSERT INTO `sys_moudle` VALUES ('1', '个人', null, null, '<i class=\"icon-user icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('2', '内容', null, null, '<i class=\"icon-book icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('3', '分类', null, null, '<i class=\"icon-folder-open icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('4', '页面', null, null, '<i class=\"icon-globe icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('5', '维护', null, null, '<i class=\"icon-cog icon-large\"></i>', null, '1');
INSERT INTO `sys_moudle` VALUES ('6', '与我相关', null, null, '<i class=\"icon-user icon-large\"></i>', '1', '0');
INSERT INTO `sys_moudle` VALUES ('7', '修改密码', 'myself/password', 'changePassword', '<i class=\"icon-key icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('8', '我的内容', 'myself/contentList', null, '<i class=\"icon-book icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('9', '我的操作日志', 'myself/logOperate', null, '<i class=\"icon-list-alt icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('10', '我的登陆日志', 'myself/logLogin', null, '<i class=\"icon-signin icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('11', ' 我的登陆授权', 'myself/userTokenList', null, '<i class=\"icon-unlock-alt icon-large\"></i>', '6', '0');
INSERT INTO `sys_moudle` VALUES ('12', '内容管理', 'cmsContent/list', 'sysUser/lookup', '<i class=\"icon-book icon-large\"></i>', '2', '0');
INSERT INTO `sys_moudle` VALUES ('13', '内容扩展', null, null, '<i class=\"icon-road icon-large\"></i>', '2', '0');
INSERT INTO `sys_moudle` VALUES ('14', '标签管理', 'cmsTag/list', 'cmsTagType/lookup', '<i class=\"icon-tag icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('15', '增加/修改', 'cmsTag/add', 'cmsTagType/lookup,cmsTag/save', null, '14', '0');
INSERT INTO `sys_moudle` VALUES ('16', '删除', null, 'cmsTag/delete', null, '14', '0');
INSERT INTO `sys_moudle` VALUES ('17', '增加/修改', 'cmsContent/add', 'cmsContent/addMore,cmsContent/lookup,cmsContent/lookup_list,cmsContent/save,ueditor', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('18', '删除', null, 'cmsContent/delete', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('19', '审核', null, 'cmsContent/check', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('20', '刷新', null, 'cmsContent/refresh', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('21', '生成', null, 'cmsContent/publish', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('22', '移动', 'cmsContent/moveParameters', 'cmsContent/move', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('23', '推荐', 'cmsContent/push', 'cmsContent/push_content,cmsContent/push_content_list,cmsContent/push_to_content,cmsContent/push_page,cmsContent/push_page_list,cmsPage/placeDataAdd,cmsPlace/save,cmsContent/related', null, '12', '0');
INSERT INTO `sys_moudle` VALUES ('24', '分类管理', 'cmsCategory/list', null, '<i class=\"icon-folder-open icon-large\"></i>', '3', '0');
INSERT INTO `sys_moudle` VALUES ('25', '增加/修改', 'cmsCategory/add', 'cmsCategory/addMore,cmsTemplate/lookup,cmsCategory/categoryPath,cmsCategory/contentPath,file/doUpload,cmsCategory/save', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('26', '删除', null, 'cmsCategory/delete', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('27', '生成', 'cmsCategory/publishParameters', 'cmsCategory/publish', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('28', '移动', 'cmsCategory/moveParameters', 'cmsCategory/move,cmsCategory/lookup', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('29', '推荐', 'cmsCategory/push_page', 'cmsCategory/push_page_list,cmsPage/placeDataAdd,cmsPlace/save', null, '24', '0');
INSERT INTO `sys_moudle` VALUES ('30', '页面管理', 'cmsPage/placeList', 'sysUser/lookup,sysUser/lookup_content_list,cmsPage/placeDataList,cmsPage/placeDataAdd,cmsPlace/save,cmsTemplate/publishPlace,cmsPage/publishPlace,cmsPage/push_page,cmsPage/push_page_list', '<i class=\"icon-globe icon-large\"></i>', '4', '0');
INSERT INTO `sys_moudle` VALUES ('31', '分类扩展', null, null, '<i class=\"icon-road icon-large\"></i>', '3', '0');
INSERT INTO `sys_moudle` VALUES ('32', '分类类型', 'cmsCategoryType/list', null, '<i class=\"icon-road icon-large\"></i>', '31', '0');
INSERT INTO `sys_moudle` VALUES ('33', '标签分类', 'cmsTagType/list', null, '<i class=\"icon-tags icon-large\"></i>', '31', '0');
INSERT INTO `sys_moudle` VALUES ('34', '增加/修改', 'cmsTagType/add', 'cmsTagType/save', null, '33', '0');
INSERT INTO `sys_moudle` VALUES ('35', '删除', null, 'cmsTagType/delete', null, '33', '0');
INSERT INTO `sys_moudle` VALUES ('36', '增加/修改', 'cmsCategoryType/add', 'cmsCategoryType/save', null, '32', '0');
INSERT INTO `sys_moudle` VALUES ('37', '删除', null, 'cmsCategoryType/delete', null, '32', '0');
INSERT INTO `sys_moudle` VALUES ('38', '文件管理', null, null, '<i class=\"icon-folder-close-alt icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('39', '模板文件管理', 'cmsTemplate/list', 'cmsTemplate/directory', '<i class=\"icon-code icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('40', '修改模板元数据', 'cmsTemplate/metadata', 'cmsTemplate/saveMetadata', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('41', '修改模板', 'cmsTemplate/content', 'cmsTemplate/save,cmsTemplate/chipLookup,cmsResource/lookup,cmsWebFile/lookup,cmsTemplate/upload,cmsTemplate/doUpload', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('42', '修改推荐位', 'cmsTemplate/placeList', 'cmsTemplate/placeMetadata,cmsTemplate/placeContent,cmsTemplate/placeForm,cmsTemplate/saveMetadata,cmsTemplate/createPlace', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('43', '删除模板', null, 'cmsTemplate/delete', null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('44', '搜索词管理', 'cmsWord/list', null, '<i class=\"icon-search icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('45', '运营', null, null, '<i class=\"icon-user icon-large\"></i>', null, '0');
INSERT INTO `sys_moudle` VALUES ('46', '报表管理', NULL, NULL, '<i class=\"icon-sort-by-attributes-alt icon-large\"></i>', '45', '0');
INSERT INTO `sys_moudle` VALUES ('47', '生成页面', null, 'cmsTemplate/publish', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('48', '保存页面元数据', '', 'cmsPage/saveMetaData,file/doUpload,cmsPage/clearCache', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('49', '增加/修改推荐位数据', 'cmsPage/placeDataAdd', 'cmsContent/lookup,cmsPage/lookup_content_list,file/doUpload,cmsPlace/save', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('50', '删除推荐位数据', null, 'cmsPlace/delete', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('51', '刷新推荐位数据', null, 'cmsPlace/refresh', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('52', '审核推荐位数据', null, 'cmsPlace/check', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('53', '发布推荐位', null, 'cmsTemplate/publishPlace', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('54', '清空推荐位数据', null, 'cmsPlace/clear', null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('55', '查看推荐位源码', 'cmsTemplate/placeContent', null, null, '39', '0');
INSERT INTO `sys_moudle` VALUES ('56', '应用授权', 'sysApp/list', NULL, '<i class=\"icon-linux icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('57', '增加/修改', 'sysApp/add', NULL, '', '56', '0');
INSERT INTO `sys_moudle` VALUES ('58', '保存', NULL, 'sysApp/save', '', '56', '0');
INSERT INTO `sys_moudle` VALUES ('59', '删除', NULL, 'sysApp/delete', NULL, '56', '0');
INSERT INTO `sys_moudle` VALUES ('60', '文件上传日志', 'log/upload', 'sysUser/lookup', '<i class=\"icon-list-alt icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('61', '用户管理', null, null, '<i class=\"icon-user icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('62', '系统维护', null, null, '<i class=\"icon-cogs icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('63', '日志管理', null, null, '<i class=\"icon-list-alt icon-large\"></i>', '5', '0');
INSERT INTO `sys_moudle` VALUES ('64', '操作日志', 'log/operate', 'sysUser/lookup', '<i class=\"icon-list-alt icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('65', '登录日志', 'log/login', 'sysUser/lookup', '<i class=\"icon-signin icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('66', '任务计划日志', 'log/task', 'sysUser/lookup', '<i class=\"icon-time icon-large\"></i>', '63', '0');
INSERT INTO `sys_moudle` VALUES ('67', '删除', null, 'logOperate/delete', null, '64', '0');
INSERT INTO `sys_moudle` VALUES ('68', '删除', null, 'logLogin/delete', null, '65', '0');
INSERT INTO `sys_moudle` VALUES ('69', '删除', null, 'logTask/delete', null, '66', '0');
INSERT INTO `sys_moudle` VALUES ('70', '查看', 'log/taskView', null, null, '66', '0');
INSERT INTO `sys_moudle` VALUES ('71', '用户管理', 'sysUser/list', null, '<i class=\"icon-user icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('72', '部门管理', 'sysDept/list', 'sysDept/lookup,sysUser/lookup', '<i class=\"icon-group icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('73', '角色管理', 'sysRole/list', null, '<i class=\"icon-user-md icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('74', '增加/修改', 'sysUser/add', 'sysDept/lookup,sysUser/save', null, '71', '0');
INSERT INTO `sys_moudle` VALUES ('75', '启用', null, 'sysUser/enable', null, '71', '0');
INSERT INTO `sys_moudle` VALUES ('76', '禁用', null, 'sysUser/disable', null, '71', '0');
INSERT INTO `sys_moudle` VALUES ('77', '增加/修改', 'sysDept/add', 'sysDept/lookup,sysUser/lookup,sysDept/save', null, '72', '0');
INSERT INTO `sys_moudle` VALUES ('78', '删除', null, 'sysDept/delete', null, '72', '0');
INSERT INTO `sys_moudle` VALUES ('79', '增加/修改', 'sysRole/add', 'sysRole/save', null, '73', '0');
INSERT INTO `sys_moudle` VALUES ('80', '删除', null, 'sysRole/delete', null, '73', '0');
INSERT INTO `sys_moudle` VALUES ('81', '内容模型管理', 'cmsModel/list', null, '<i class=\"icon-th-large icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('82', '任务计划', 'sysTask/list', null, '<i class=\"icon-time icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('83', '系统监控', 'report/cms', NULL, '<i class=\"icon-check-sign icon-large\"></i>', '46', '0');
INSERT INTO `sys_moudle` VALUES ('84', '动态域名', 'cmsDomain/list', null, '<i class=\"icon-qrcode icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('85', '任务计划脚本', 'taskTemplate/list', null, '<i class=\"icon-time icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('86', '修改脚本', 'taskTemplate/metadata', 'cmsTemplate/saveMetadata,taskTemplate/content,cmsTemplate/save,taskTemplate/chipLookup', null, '85', '0');
INSERT INTO `sys_moudle` VALUES ('87', '删除脚本', null, 'cmsTemplate/delete', null, '85', '0');
INSERT INTO `sys_moudle` VALUES ('88', '客户端管理', 'sysAppClient/list', NULL, '<i class=\"icon-coffee icon-large\"></i>', '61', '0');
INSERT INTO `sys_moudle` VALUES ('89', '启用', NULL, 'sysAppClient/enable', NULL, '88', '0');
INSERT INTO `sys_moudle` VALUES ('90', '增加/修改', 'cmsModel/add', 'cmsModel/save,cmsTemplate/lookup', null, '81', '0');
INSERT INTO `sys_moudle` VALUES ('91', '删除', null, 'cmsModel/delete', null, '81', '0');
INSERT INTO `sys_moudle` VALUES ('92', '增加/修改', 'sysTask/add', 'sysTask/save,sysTask/example,taskTemplate/lookup', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('93', '删除', null, 'sysTask/delete', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('94', '立刻执行', null, 'sysTask/runOnce', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('95', '暂停', null, 'sysTask/pause', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('96', '恢复', null, 'sysTask/resume', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('97', '重新初始化', null, 'sysTask/recreate', null, '82', '0');
INSERT INTO `sys_moudle` VALUES ('98', '禁用', NULL, 'sysAppClient/disable', NULL, '88', '0');
INSERT INTO `sys_moudle` VALUES ('99', '抽奖管理', 'cmsLottery/list', NULL, '<i class=\"icon-ticket icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('100', '修改', 'cmsDomain/add', 'cmsDomain/save,cmsTemplate/directoryLookup,cmsTemplate/lookup', null, '84', '0');
INSERT INTO `sys_moudle` VALUES ('101', '站点配置', 'sysConfigData/list', null, '<i class=\"icon-cog icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('102', '修改', 'cmsContent/add', 'cmsContent/addMore,file/doUpload,cmsContent/lookup,cmsContent/lookup_list,cmsContent/save,ueditor', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('103', '删除', null, 'cmsContent/delete', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('104', '刷新', null, 'cmsContent/refresh', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('105', '生成', null, 'cmsContent/publish', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('106', '推荐', 'cmsContent/push', 'cmsContent/push_content,cmsContent/push_content_list,cmsContent/push_to_content,cmsContent/push_page,cmsContent/push_page_list,cmsContent/push_to_place,cmsContent/related', null, '8', '0');
INSERT INTO `sys_moudle` VALUES ('107', '推荐位数据列表', 'cmsPage/placeDataList', null, null, '30', '0');
INSERT INTO `sys_moudle` VALUES ('108', '增加/修改', 'cmsLottery/add', NULL, NULL, '1007', '0');
INSERT INTO `sys_moudle` VALUES ('109', '空间管理', null, null, '<i class=\"icon-home icon-large\"></i>', '45', '0');
INSERT INTO `sys_moudle` VALUES ('110', '用户主页', 'homeUser/list', null, '<i class=\"icon-globe icon-large\"></i>', '109', '0');
INSERT INTO `sys_moudle` VALUES ('111', '查看', 'homeUser/view', null, '', '110', '0');
INSERT INTO `sys_moudle` VALUES ('112', '启用', null, 'homeUser/enable', '', '110', '0');
INSERT INTO `sys_moudle` VALUES ('113', '禁用', null, 'homeUser/disable', null, '110', '0');
INSERT INTO `sys_moudle` VALUES ('114', '目录管理', 'homeDirectory/list', 'sysUser/lookup', '<i class=\"icon-folder-close icon-large\"></i>', '109', '0');
INSERT INTO `sys_moudle` VALUES ('115', '启用', null, 'homeDirectory/enable', null, '114', '0');
INSERT INTO `sys_moudle` VALUES ('116', '禁用', null, 'homeDirectory/disable', null, '114', '0');
INSERT INTO `sys_moudle` VALUES ('117', '文章管理', 'homeArticle/list', 'sysUser/lookup', '<i class=\"icon-file-text icon-large\"></i>', '109', '0');
INSERT INTO `sys_moudle` VALUES ('118', '查看', 'homeFile/view', null, null, '117', '0');
INSERT INTO `sys_moudle` VALUES ('119', '启用', null, 'homeFile/enable', null, '117', '0');
INSERT INTO `sys_moudle` VALUES ('120', '禁用', null, 'homeFile/disable', null, '117', '0');
INSERT INTO `sys_moudle` VALUES ('121', '文件管理', 'homeFile/list', 'sysUser/lookup', '<i class=\"icon-file icon-large\"></i>', '109', '0');
INSERT INTO `sys_moudle` VALUES ('122', '启用', null, 'homeFile/enable', null, '121', '0');
INSERT INTO `sys_moudle` VALUES ('123', '禁用', null, 'homeFile/disable', null, '121', '0');
INSERT INTO `sys_moudle` VALUES ('124', '广播管理', 'homeBroadcast/list', 'sysUser/lookup', '<i class=\"icon-bullhorn icon-large\"></i>', '109', '0');
INSERT INTO `sys_moudle` VALUES ('125', '启用', null, 'homeBroadcast/enable', null, '124', '0');
INSERT INTO `sys_moudle` VALUES ('126', '禁用', null, 'homeBroadcast/disable', null, '124', '0');
INSERT INTO `sys_moudle` VALUES ('127', '群组管理', null, null, '<i class=\"icon-group icon-large\"></i>', '45', '0');
INSERT INTO `sys_moudle` VALUES ('128', '群组管理', 'homeGroup/list', null, '<i class=\"icon-group icon-large\"></i>', '127', '0');
INSERT INTO `sys_moudle` VALUES ('129', '帖子管理', 'homeGroupPost/list', null, '<i class=\"icon-list-alt icon-large\"></i>', '127', '0');
INSERT INTO `sys_moudle` VALUES ('130', '评论管理', 'homeComment/list', null, '<i class=\"icon-comment-alt icon-large\"></i>', '109', '0');
INSERT INTO `sys_moudle` VALUES ('131', '网站文件管理', 'cmsWebFile/list', null, '<i class=\"icon-globe icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('132', '新建目录', 'cmsWebFile/directory', 'cmsWebFile/createDirectory', null, '131', '0');
INSERT INTO `sys_moudle` VALUES ('133', '上传文件', 'cmsWebFile/upload', 'cmsWebFile/doUpload', null, '131', '0');
INSERT INTO `sys_moudle` VALUES ('134', '压缩', null, 'cmsWebFile/zip', null, '131', '0');
INSERT INTO `sys_moudle` VALUES ('135', '解压缩', null, 'cmsWebFile/unzip,cmsWebFile/unzipHere', null, '131', '0');
INSERT INTO `sys_moudle` VALUES ('136', '节点管理', 'sysCluster/list', NULL, '<i class=\"icon-code-fork icon-large\"></i>', '62', '0');
INSERT INTO `sys_moudle` VALUES ('137', '配置项列表', 'sysConfigData/itemList', null, null, '101', '0');
INSERT INTO `sys_moudle` VALUES ('138', '修改配置', 'sysConfigData/addItem', 'sysConfigData/save', null, '101', '0');
INSERT INTO `sys_moudle` VALUES ('139', '清空配置', null, 'sysConfigData/delete', null, '101', '0');
INSERT INTO `sys_moudle` VALUES ('140', '站点配置管理', 'sysConfig/list', null, '<i class=\"icon-cogs icon-large\"></i>', '38', '0');
INSERT INTO `sys_moudle` VALUES ('141', '配置项列表', 'sysConfig/itemList', null, null, '140', '0');
INSERT INTO `sys_moudle` VALUES ('142', '保存配置', null, 'sysConfig/save,sysConfig/saveItem', null, '140', '0');
INSERT INTO `sys_moudle` VALUES ('143', '修改配置', 'sysConfig/add', null, null, '140', '0');
INSERT INTO `sys_moudle` VALUES ('144', '删除配置', null, 'sysConfig/delete,sysConfig/deleteItem', null, '140', '0');
INSERT INTO `sys_moudle` VALUES ('145', '保存', NULL, 'cmsLottery/save', NULL, '1007', '0');
INSERT INTO `sys_moudle` VALUES ('146', '删除', NULL, 'cmsLottery/delete', NULL, '1007', '0');
INSERT INTO `sys_moudle` VALUES ('147', '抽奖用户管理', 'cmsLotteryUser/list', 'sysUser/lookup', '<i class=\"icon-smile icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('148', '删除', NULL, 'cmsLotteryUser/delete', NULL, '1011', '0');
INSERT INTO `sys_moudle` VALUES ('149', '投票管理', 'cmsVote/list', NULL, '<i class=\"icon-hand-right icon-large\"></i>', '13', '0');
INSERT INTO `sys_moudle` VALUES ('150', '增加/修改', 'cmsVote/add', NULL, NULL, '1013', '0');
INSERT INTO `sys_moudle` VALUES ('151', '保存', NULL, 'cmsVote/save', NULL, '1013', '0');
INSERT INTO `sys_moudle` VALUES ('152', '删除', NULL, 'cmsVote/delete', NULL, '1013', '0');
INSERT INTO `sys_moudle` VALUES ('153', '查看', 'cmsVote/view', NULL, NULL, '1013', '0');
INSERT INTO `sys_moudle` VALUES ('154', '投票用户', 'cmsVoteUser/list', 'sysUser/lookup', NULL, '1013', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `owns_all_right` tinyint(1) NOT NULL COMMENT '拥有全部权限',
  `show_all_moudle` tinyint(1) NOT NULL COMMENT '显示全部模块',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '超级管理员', '1', '0');
INSERT INTO `sys_role` VALUES ('2', '1', '测试管理员', '0', '1');
INSERT INTO `sys_role` VALUES ('3', '2', '站长', '1', '0');

-- ----------------------------
-- Table structure for sys_role_authorized
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorized`;
CREATE TABLE `sys_role_authorized` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `url` varchar(255) NOT NULL COMMENT '授权地址',
  PRIMARY KEY  (`role_id`,`url`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色授权地址';

-- ----------------------------
-- Records of sys_role_authorized
-- ----------------------------
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategory/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategory/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategory/moveParameters');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategory/publishParameters');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategory/push_page');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategoryType/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsCategoryType/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsContent/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsContent/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsContent/moveParameters');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsContent/push');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsDomain/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsDomain/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsFtpUser/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsFtpUser/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsModel/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsModel/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsPage/placeDataAdd');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsPage/placeDataList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsPage/placeList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTag/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTag/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTagType/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTagType/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTemplate/content');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTemplate/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTemplate/metadata');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTemplate/placeContent');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsTemplate/placeList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsWebFile/directory');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsWebFile/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsWebFile/upload');
INSERT INTO `sys_role_authorized` VALUES ('2', 'cmsWord/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeArticle/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeBroadcast/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeComment/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeDirectory/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeFile/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeFile/view');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeGroup/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeGroupPost/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeUser/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'homeUser/view');
INSERT INTO `sys_role_authorized` VALUES ('2', 'log/login');
INSERT INTO `sys_role_authorized` VALUES ('2', 'log/operate');
INSERT INTO `sys_role_authorized` VALUES ('2', 'log/task');
INSERT INTO `sys_role_authorized` VALUES ('2', 'log/taskView');
INSERT INTO `sys_role_authorized` VALUES ('2', 'log/upload');
INSERT INTO `sys_role_authorized` VALUES ('2', 'myself/contentList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'myself/logLogin');
INSERT INTO `sys_role_authorized` VALUES ('2', 'myself/logOperate');
INSERT INTO `sys_role_authorized` VALUES ('2', 'myself/password');
INSERT INTO `sys_role_authorized` VALUES ('2', 'myself/userTokenList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysCluster/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysConfig/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysConfig/itemList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysConfig/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysConfigData/addItem');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysConfigData/itemList');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysConfigData/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysDept/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysDept/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysRole/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysRole/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysTask/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysTask/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysUser/add');
INSERT INTO `sys_role_authorized` VALUES ('2', 'sysUser/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'taskTemplate/list');
INSERT INTO `sys_role_authorized` VALUES ('2', 'taskTemplate/metadata');

-- ----------------------------
-- Table structure for sys_role_moudle
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_moudle`;
CREATE TABLE `sys_role_moudle` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `moudle_id` int(11) NOT NULL COMMENT '模块ID',
  PRIMARY KEY  (`role_id`,`moudle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色授权模块';

-- ----------------------------
-- Records of sys_role_moudle
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY  (`role_id`,`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1');
INSERT INTO `sys_role_user` VALUES ('2', '2');
INSERT INTO `sys_role_user` VALUES ('3', '3');

-- ----------------------------
-- Table structure for sys_site
-- ----------------------------
DROP TABLE IF EXISTS `sys_site`;
CREATE TABLE `sys_site` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `use_static` tinyint(1) NOT NULL COMMENT '启用静态化',
  `site_path` varchar(255) NOT NULL COMMENT '站点地址',
  `use_ssi` tinyint(1) NOT NULL COMMENT '启用服务器端包含',
  `dynamic_path` varchar(255) NOT NULL COMMENT '动态站点地址',
  `disabled` tinyint(1) NOT NULL COMMENT '禁用',
  PRIMARY KEY  (`id`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='站点';

-- ----------------------------
-- Records of sys_site
-- ----------------------------
INSERT INTO `sys_site` VALUES ('1', 'PublicCMS', '1', '//dev.publiccms.com/', '0', '//dev.publiccms.com/cms/', '0');
INSERT INTO `sys_site` VALUES ('2', '演示站点1', '0', '//site2.dev.publiccms.com/', '0', 'site2.dev.publiccms.com', '0');

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `status` int(11) NOT NULL COMMENT '状态',
  `cron_expression` varchar(50) NOT NULL COMMENT '表达式',
  `description` varchar(300) default NULL COMMENT '描述',
  `file_path` varchar(255) default NULL COMMENT '文件路径',
  `update_date` datetime default NULL COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `status` (`status`),
  KEY `site_id` (`site_id`),
  KEY `update_date` (`update_date`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='任务计划';

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task` VALUES ('1', '1', '重新生成所有页面', '0', '0 0/2 * * ?', '重新生成所有页面', '/publishPage.task', NULL);
INSERT INTO `sys_task` VALUES ('2', '1', '重建索引', '0', '0 0 1 1 ? 2099', '重建全部索引', '/reCreateIndex.task', NULL);
INSERT INTO `sys_task` VALUES ('3', '1', '清理日志', '0', '0 0 1 * ?', '清理三个月以前的日志', '/clearLog.task', NULL);
INSERT INTO `sys_task` VALUES ('4', '1', '重新生成内容页面', '0', '0 0 1 1 ? 2099', '重新生成内容页面', '/publishContent.task', NULL);
INSERT INTO `sys_task` VALUES ('5', '1', '重新生成所有分类页面', '0', '0 0/6 * * ?', '重新生成所有分类页面', '/publishCategory.task', NULL);
INSERT INTO `sys_task` VALUES ('7', '1', '重新生成全站', '0', '0 0 1 1 ? 2099', '重新生成全站', '/publishAll.task', NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(45) NOT NULL COMMENT '昵称',
  `dept_id` int(11) default NULL COMMENT '部门',
  `roles` text COMMENT '角色',
  `email` varchar(100) default NULL COMMENT '邮箱地址',
  `email_checked` tinyint(1) NOT NULL COMMENT '已验证邮箱',
  `superuser_access` tinyint(1) NOT NULL COMMENT '是否管理员',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `last_login_date` datetime default NULL COMMENT '最后登录日期',
  `last_login_ip` varchar(20) default NULL COMMENT '最后登录ip',
  `login_count` int(11) NOT NULL COMMENT '登录次数',
  `registered_date` datetime default NULL COMMENT '注册日期',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`,`site_id`),
  UNIQUE KEY `nick_name` (`nick_name`,`site_id`),
  KEY `email` (`email`),
  KEY `disabled` (`disabled`),
  KEY `lastLoginDate` (`last_login_date`),
  KEY `email_checked` (`email_checked`),
  KEY `dept_id` (`dept_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', '1', '1', 'master@sanluan.com', '0', '1', '0', '2016-04-05 14:41:23', '127.0.0.1', '48', '2016-03-22 00:00:00');
INSERT INTO `sys_user` VALUES ('2', '1', 'test', '098f6bcd4621d373cade4e832627b4f6', '演示账号', '1', '2', 'test@test.com', '0', '1', '0', '2016-03-24 18:20:41', '112.23.82.255', '5455', '2016-03-22 00:00:00');
INSERT INTO `sys_user` VALUES ('3', '2', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', '2', '3', '', '0', '1', '0', '2016-03-23 11:51:11', '106.2.199.138', '6', '2016-03-22 17:42:26');


-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `auth_token` varchar(40) NOT NULL COMMENT '登陆授权',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `login_ip` varchar(20) NOT NULL COMMENT '登陆IP',
  PRIMARY KEY  (`auth_token`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`),
  KEY `channel` (`channel`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户令牌';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------

