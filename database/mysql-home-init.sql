SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for home_active
-- ----------------------------
DROP TABLE IF EXISTS `home_active`;
CREATE TABLE `home_active` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `create_date` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `item_type` (`user_id`,`item_type`,`item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间动态';

-- ----------------------------
-- Records of home_active
-- ----------------------------

-- ----------------------------
-- Table structure for home_article
-- ----------------------------
DROP TABLE IF EXISTS `home_article`;
CREATE TABLE `home_article` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `directory_id` bigint(20) default NULL COMMENT '目录ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `cover` varchar(255) default NULL COMMENT '封面图',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `last_comment_id` bigint(20) NOT NULL COMMENT '最新回复',
  `best_comment_id` bigint(20) NOT NULL COMMENT '最佳回复',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`directory_id`,`user_id`,`create_date`),
  KEY `best_comment_id` (`best_comment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间文章';

-- ----------------------------
-- Records of home_article
-- ----------------------------

-- ----------------------------
-- Table structure for home_article_content
-- ----------------------------
DROP TABLE IF EXISTS `home_article_content`;
CREATE TABLE `home_article_content` (
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `content` longtext COMMENT '内容',
  PRIMARY KEY  (`article_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='文章内容';

-- ----------------------------
-- Records of home_article_content
-- ----------------------------

-- ----------------------------
-- Table structure for home_attention
-- ----------------------------
DROP TABLE IF EXISTS `home_attention`;
CREATE TABLE `home_attention` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `attention_id` bigint(20) NOT NULL COMMENT '关注ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`user_id`,`attention_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间关注';

-- ----------------------------
-- Records of home_attention
-- ----------------------------

-- ----------------------------
-- Table structure for home_broadcast
-- ----------------------------
DROP TABLE IF EXISTS `home_broadcast`;
CREATE TABLE `home_broadcast` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `scores` int(11) NOT NULL COMMENT '分数',
  `reposts` int(11) NOT NULL COMMENT '转发数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `message` varchar(300) NOT NULL COMMENT '消息',
  `reposted` tinyint(1) NOT NULL COMMENT '转发',
  `repost_id` bigint(20) NOT NULL COMMENT '转发广播ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`id`),
  KEY `reposted` (`reposted`,`repost_id`),
  KEY `site_id` (`site_id`,`user_id`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间广播';

-- ----------------------------
-- Records of home_broadcast
-- ----------------------------

-- ----------------------------
-- Table structure for home_comment
-- ----------------------------
DROP TABLE IF EXISTS `home_comment`;
CREATE TABLE `home_comment` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `scores` int(11) NOT NULL COMMENT '分数',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`user_id`,`item_type`,`item_id`,`disabled`,`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of home_comment
-- ----------------------------

-- ----------------------------
-- Table structure for home_comment_content
-- ----------------------------
DROP TABLE IF EXISTS `home_comment_content`;
CREATE TABLE `home_comment_content` (
  `comment_id` bigint(20) NOT NULL,
  `content` longtext,
  PRIMARY KEY  (`comment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评论内容';

-- ----------------------------
-- Records of home_comment_content
-- ----------------------------

-- ----------------------------
-- Table structure for home_dialog
-- ----------------------------
DROP TABLE IF EXISTS `home_dialog`;
CREATE TABLE `home_dialog` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `messages` int(11) NOT NULL COMMENT '消息数',
  `last_message_date` datetime NOT NULL COMMENT '最新消息日期',
  `readed_message_date` datetime NOT NULL COMMENT '阅读日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`user_id`,`item_type`,`item_id`),
  KEY `last_message_date` (`disabled`,`last_message_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='对话';

-- ----------------------------
-- Records of home_dialog
-- ----------------------------

-- ----------------------------
-- Table structure for home_directory
-- ----------------------------
DROP TABLE IF EXISTS `home_directory`;
CREATE TABLE `home_directory` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `cover` varchar(255) default NULL COMMENT '封面图',
  `files` int(11) NOT NULL COMMENT '文件数',
  `secret` tinyint(1) NOT NULL COMMENT '私密目录',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`user_id`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间目录';

-- ----------------------------
-- Records of home_directory
-- ----------------------------

-- ----------------------------
-- Table structure for home_file
-- ----------------------------
DROP TABLE IF EXISTS `home_file`;
CREATE TABLE `home_file` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `directory_id` bigint(20) default NULL COMMENT '目录',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `file_path` varchar(255) NOT NULL COMMENT '封面图',
  `image` tinyint(1) NOT NULL COMMENT '是否图片',
  `file_size` int(11) NOT NULL COMMENT '文件大小',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`user_id`,`directory_id`,`image`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间文件';

-- ----------------------------
-- Records of home_file
-- ----------------------------

-- ----------------------------
-- Table structure for home_friend
-- ----------------------------
DROP TABLE IF EXISTS `home_friend`;
CREATE TABLE `home_friend` (
  `id` bigint(20) NOT NULL auto_increment,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '好友ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `remark_name` varchar(100) NOT NULL COMMENT '备注名',
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`,`friend_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='好友';

-- ----------------------------
-- Records of home_friend
-- ----------------------------

-- ----------------------------
-- Table structure for home_friend_apply
-- ----------------------------
DROP TABLE IF EXISTS `home_friend_apply`;
CREATE TABLE `home_friend_apply` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '好友ID',
  `message` varchar(300) NOT NULL COMMENT '消息',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`user_id`,`friend_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='好友申请';

-- ----------------------------
-- Records of home_friend_apply
-- ----------------------------

-- ----------------------------
-- Table structure for home_group
-- ----------------------------
DROP TABLE IF EXISTS `home_group`;
CREATE TABLE `home_group` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(300) default NULL,
  `users` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`user_id`,`users`,`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='群组';

-- ----------------------------
-- Records of home_group
-- ----------------------------

-- ----------------------------
-- Table structure for home_group_active
-- ----------------------------
DROP TABLE IF EXISTS `home_group_active`;
CREATE TABLE `home_group_active` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `group_id` bigint(11) NOT NULL COMMENT '站点ID',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `create_date` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `item_type` (`group_id`,`user_id`,`item_type`,`item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='空间动态';

-- ----------------------------
-- Records of home_group_active
-- ----------------------------

-- ----------------------------
-- Table structure for home_group_apply
-- ----------------------------
DROP TABLE IF EXISTS `home_group_apply`;
CREATE TABLE `home_group_apply` (
  `group_id` bigint(20) NOT NULL COMMENT '群组ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `message` text NOT NULL COMMENT '消息',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`group_id`,`user_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='群组申请';

-- ----------------------------
-- Records of home_group_apply
-- ----------------------------

-- ----------------------------
-- Table structure for home_group_post
-- ----------------------------
DROP TABLE IF EXISTS `home_group_post`;
CREATE TABLE `home_group_post` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `group_id` bigint(20) default NULL COMMENT '群组ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `create_date` datetime NOT NULL COMMENT '发布日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`group_id`,`user_id`,`disabled`,`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='群组帖子';

-- ----------------------------
-- Records of home_group_post
-- ----------------------------

-- ----------------------------
-- Table structure for home_group_post_content
-- ----------------------------
DROP TABLE IF EXISTS `home_group_post_content`;
CREATE TABLE `home_group_post_content` (
  `post_id` bigint(20) NOT NULL,
  `content` longtext,
  PRIMARY KEY  (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='帖子内容';

-- ----------------------------
-- Records of home_group_post_content
-- ----------------------------

-- ----------------------------
-- Table structure for home_group_user
-- ----------------------------
DROP TABLE IF EXISTS `home_group_user`;
CREATE TABLE `home_group_user` (
  `group_id` bigint(20) NOT NULL COMMENT '群组ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `superuser_access` tinyint(1) NOT NULL COMMENT '管理员',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`group_id`,`user_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='群组用户';

-- ----------------------------
-- Records of home_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for home_message
-- ----------------------------
DROP TABLE IF EXISTS `home_message`;
CREATE TABLE `home_message` (
  `id` bigint(20) NOT NULL auto_increment,
  `user_id` bigint(20) NOT NULL COMMENT '所属用户',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `content` longtext NOT NULL COMMENT '内容',
  PRIMARY KEY  (`id`),
  KEY `create_date` (`create_date`),
  KEY `user_id` (`user_id`,`item_type`,`item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户消息';

-- ----------------------------
-- Records of home_message
-- ----------------------------

-- ----------------------------
-- Table structure for home_score
-- ----------------------------
DROP TABLE IF EXISTS `home_score`;
CREATE TABLE `home_score` (
  `id` bigint(20) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_type` varchar(20) NOT NULL COMMENT '项目类型',
  `item_id` bigint(20) NOT NULL COMMENT '项目ID',
  `score` int(11) NOT NULL COMMENT '分数',
  `ip` varchar(64) NOT NULL COMMENT 'IP地址',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`,`user_id`,`item_type`,`item_id`,`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评分';

-- ----------------------------
-- Records of home_score
-- ----------------------------

-- ----------------------------
-- Table structure for home_user
-- ----------------------------
DROP TABLE IF EXISTS `home_user`;
CREATE TABLE `home_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `title` varchar(255) default NULL COMMENT '标题',
  `signature` varchar(300) default NULL,
  `friends` int(11) NOT NULL COMMENT '好友数',
  `messages` int(11) NOT NULL COMMENT '消息数',
  `questions` int(11) NOT NULL COMMENT '问题数',
  `answers` int(11) NOT NULL COMMENT '回答数',
  `articles` int(11) NOT NULL COMMENT '文章数',
  `clicks` int(11) NOT NULL COMMENT '点击数数',
  `broadcasts` int(11) NOT NULL COMMENT '广播数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `attention_ids` text COMMENT '关注用户',
  `attentions` int(11) NOT NULL COMMENT '关注数',
  `fans` int(11) NOT NULL COMMENT '粉丝数',
  `last_login_date` datetime default NULL COMMENT '上次登陆日期',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`user_id`),
  KEY `site_id` (`site_id`,`last_login_date`,`create_date`,`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户空间';

-- ----------------------------
-- Records of home_user
-- ----------------------------
