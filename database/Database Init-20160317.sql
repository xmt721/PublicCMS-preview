-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2016 年 03 月 17 日 02:07
-- 服务器版本: 5.0.90-community-nt
-- PHP 版本: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `public_cms`
--

-- --------------------------------------------------------

--
-- 表的结构 `cms_category`
--

CREATE TABLE IF NOT EXISTS `cms_category` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_id` int(11) default NULL COMMENT '父分类ID',
  `type_id` int(11) default NULL COMMENT '分类类型',
  `child_ids` text COMMENT '所有子分类ID',
  `tag_type_ids` varchar(100) default NULL COMMENT '标签分类',
  `english_name` varchar(50) default NULL COMMENT '英文名',
  `template_path` varchar(255) NOT NULL COMMENT '模板路径',
  `path` varchar(2000) NOT NULL COMMENT '首页路径',
  `has_static` tinyint(1) NOT NULL COMMENT '已经静态化',
  `url` varchar(2048) default NULL COMMENT '首页地址',
  `content_path` varchar(500) NOT NULL COMMENT '内容路径',
  `page_size` int(11) default NULL COMMENT '每页数据条数',
  `allow_contribute` tinyint(1) NOT NULL COMMENT '允许投稿',
  `sort` int(11) NOT NULL default '0' COMMENT '顺序',
  `disabled` tinyint(1) NOT NULL COMMENT '是否删除',
  `contents` int(11) NOT NULL default '0' COMMENT '内容数',
  `extend_id` int(11) default NULL COMMENT '扩展ID',
  PRIMARY KEY  (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `disabled` (`disabled`),
  KEY `sort` (`sort`),
  KEY `site_id` (`site_id`),
  KEY `type_id` (`type_id`),
  KEY `allow_contribute` (`allow_contribute`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='分类' AUTO_INCREMENT=19 ;

--
-- 转存表中的数据 `cms_category`
--

INSERT INTO `cms_category` (`id`, `site_id`, `name`, `parent_id`, `type_id`, `child_ids`, `tag_type_ids`, `english_name`, `template_path`, `path`, `has_static`, `url`, `content_path`, `page_size`, `allow_contribute`, `sort`, `disabled`, `contents`, `extend_id`) VALUES
(1, 1, '演示', NULL, NULL, '6,7,8', NULL, 'demonstrate', '/category/parent.html', '${category.englishName}/index.html', 0, 'demonstrate/index.html', '${content.publishDate?string(''yyyy/MM/dd'')}/${content.id}.html', 10, 0, 0, 0, 0, NULL),
(6, 1, '汽车', 1, NULL, NULL, NULL, 'car', '/category/list.html', 'demonstrate/${category.englishName}.html', 0, 'demonstrate/car.html', '${content.categoryId}/${content.id}.html', 10, 0, 0, 0, 1, NULL),
(7, 1, '社会', 1, NULL, NULL, NULL, 'social', '/category/list.html', 'demonstrate/${category.englishName}.html', 0, 'demonstrate/social.html', '${content.categoryId}/${content.id}.html', 10, 0, 0, 0, 11, NULL),
(8, 1, '美图', 1, NULL, NULL, NULL, 'picture', '/category/list.html', 'demonstrate/${category.englishName}.html', 0, 'demonstrate/picture.html', '${content.categoryId}/${content.id}.html', 10, 0, 0, 0, 15, NULL),
(9, 1, '系统介绍', 11, NULL, NULL, NULL, 'article', '/category/list.html', 'demonstrate/${category.englishName}.html', 0, 'demonstrate/article.html', '${content.categoryId}/${content.id}.html', 10, 0, 0, 0, 6, NULL),
(12, 1, '文章', 11, NULL, NULL, NULL, 'article', '/category/list.html', 'test/${category.englishName}/index.html', 0, 'test/article/index.html', '${content.publishDate?string(''yyyy/MM/dd'')}/${content.id}.html', 20, 0, 0, 0, 10, NULL),
(11, 1, '测试', NULL, NULL, '9,12,17', NULL, 'test', '/category/parent.html', '${category.englishName}/index.html', 0, 'test/index.html', '${content.publishDate?string(''yyyy/MM/dd'')}/${content.id}.html', 20, 0, 0, 0, 0, NULL),
(13, 1, '下载', NULL, NULL, '16', NULL, 'download', '', 'https://github.com/sanluan/PublicCMS', 0, 'https://github.com/sanluan/PublicCMS', '', 20, 0, 0, 0, 0, NULL),
(14, 1, '演示站点1', NULL, NULL, '15', NULL, 'demo1', '/category/parent.html', '${category.englishName}/index.html', 0, 'demo1/index.html', '${category.englishName}/${content.publishDate?string(''yyyy/MM/dd'')}/${content.id}.html', 20, 0, 0, 0, 0, NULL),
(15, 1, '小说', 14, NULL, NULL, NULL, 'novel', '', 'list.html?id=${category.id}', 0, 'list.html?id=15', 'article.html?id=${content.id}', 20, 0, 0, 0, 9, NULL),
(16, 1, 'OSChina下载', 13, NULL, NULL, NULL, 'download', '', 'http://git.oschina.net/sanluan/PublicCMS', 0, 'http://git.oschina.net/sanluan/PublicCMS', '', 20, 0, 0, 0, 0, NULL),
(17, 1, '科技', 11, NULL, NULL, NULL, 'science', '/category/product_list.html', '${category.englishName}/index.html', 0, 'science/index.html', '${content.publishDate?string(''yyyy/MM/dd'')}/${content.id}.html', 20, 0, 0, 0, 5, NULL),
(18, 1, 'asdf', NULL, NULL, NULL, '', 'asdf', '/category/list.html', '${category.englishName}/index.html', 0, 'asdf/index.html', '${content.publishDate?string(''yyyy/MM-dd'')}/${content.id}.html', 20, 0, 0, 0, 3, 0);

-- --------------------------------------------------------

--
-- 表的结构 `cms_category_attribute`
--

CREATE TABLE IF NOT EXISTS `cms_category_attribute` (
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  `title` varchar(80) default NULL COMMENT '标题',
  `keywords` varchar(100) default NULL COMMENT '关键词',
  `description` varchar(300) default NULL COMMENT '描述',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY  (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='分类扩展';

--
-- 转存表中的数据 `cms_category_attribute`
--

INSERT INTO `cms_category_attribute` (`category_id`, `title`, `keywords`, `description`, `data`) VALUES
(3, NULL, NULL, NULL, '{}'),
(1, NULL, NULL, NULL, '{}'),
(2, NULL, NULL, NULL, '{}'),
(4, NULL, NULL, NULL, '{}'),
(5, NULL, NULL, NULL, '{}'),
(6, NULL, NULL, NULL, '{}'),
(7, NULL, NULL, NULL, '{}'),
(8, NULL, NULL, NULL, '{}'),
(9, NULL, NULL, NULL, '{}'),
(10, NULL, NULL, NULL, '{}'),
(11, NULL, NULL, NULL, '{}'),
(12, NULL, NULL, NULL, '{}'),
(13, NULL, NULL, NULL, '{}'),
(14, NULL, NULL, NULL, '{}'),
(15, NULL, NULL, NULL, '{}'),
(16, NULL, NULL, NULL, '{}'),
(17, NULL, NULL, NULL, '{}'),
(18, NULL, NULL, NULL, '{}');

-- --------------------------------------------------------

--
-- 表的结构 `cms_category_model`
--

CREATE TABLE IF NOT EXISTS `cms_category_model` (
  `id` int(11) NOT NULL auto_increment,
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `template_path` varchar(200) default NULL COMMENT '内容模板路径',
  PRIMARY KEY  (`id`),
  KEY `category_id` (`category_id`),
  KEY `model_id` (`model_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='分类模型' AUTO_INCREMENT=42 ;

--
-- 转存表中的数据 `cms_category_model`
--

INSERT INTO `cms_category_model` (`id`, `category_id`, `model_id`, `template_path`) VALUES
(1, 9, 1, '/system/article.html'),
(2, 8, 3, '/system/picture.html'),
(3, 7, 3, '/system/picture.html'),
(4, 7, 1, '/system/article.html'),
(5, 6, 2, ''),
(6, 12, 2, ''),
(7, 12, 1, '/system/article.html'),
(8, 15, 4, ''),
(9, 15, 6, ''),
(10, 15, 5, ''),
(11, 9, 6, '/system/chapter.html'),
(12, 9, 5, ''),
(13, 9, 3, '/system/picture.html'),
(14, 9, 2, ''),
(15, 16, 6, '/system/chapter.html'),
(16, 16, 5, ''),
(17, 6, 6, '/system/chapter.html'),
(18, 6, 5, ''),
(19, 6, 3, '/system/picture.html'),
(20, 6, 1, '/system/article.html'),
(21, 8, 6, '/system/chapter.html'),
(22, 8, 5, ''),
(23, 7, 6, '/system/chapter.html'),
(24, 7, 5, ''),
(25, 17, 6, '/system/chapter.html'),
(26, 17, 5, ''),
(27, 17, 3, '/system/picture.html'),
(28, 17, 2, ''),
(29, 17, 1, '/system/article.html'),
(30, 7, 2, ''),
(31, 14, 6, '/system/chapter.html'),
(32, 14, 5, ''),
(33, 18, 6, '/system/chapter.html'),
(34, 18, 5, ''),
(39, 18, 9, '/category/list.html'),
(40, 18, 8, ''),
(41, 18, 4, '');

-- --------------------------------------------------------

--
-- 表的结构 `cms_category_type`
--

CREATE TABLE IF NOT EXISTS `cms_category_type` (
  `id` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `extend_id` int(11) default NULL COMMENT '扩展ID',
  PRIMARY KEY  (`id`),
  KEY `siteId` (`siteId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `cms_category_type`
--

INSERT INTO `cms_category_type` (`id`, `siteId`, `name`, `sort`, `extend_id`) VALUES
(1, 1, '有图标', 0, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `cms_content`
--

CREATE TABLE IF NOT EXISTS `cms_content` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `user_id` int(11) NOT NULL COMMENT '发表用户',
  `check_user_id` int(11) default NULL COMMENT '审核用户',
  `category_id` int(11) NOT NULL COMMENT '分类',
  `model_id` int(11) NOT NULL COMMENT '模型',
  `parent_id` int(11) default NULL COMMENT '父内容ID',
  `copied` tinyint(1) NOT NULL COMMENT '是否转载',
  `author` varchar(50) default NULL COMMENT '作者',
  `editor` varchar(50) default NULL COMMENT '编辑',
  `only_url` tinyint(1) NOT NULL COMMENT '外链',
  `has_images` tinyint(1) NOT NULL COMMENT '拥有图片列表',
  `has_files` tinyint(1) NOT NULL COMMENT '拥有附件列表',
  `has_static` tinyint(1) NOT NULL COMMENT '已经静态化',
  `url` varchar(2048) default NULL COMMENT '地址',
  `description` varchar(300) default NULL COMMENT '简介',
  `tag_ids` varchar(100) default NULL COMMENT '标签',
  `cover` varchar(255) default NULL COMMENT '封面',
  `childs` int(11) NOT NULL COMMENT '内容页数',
  `scores` int(11) NOT NULL COMMENT '分数',
  `comments` int(11) NOT NULL COMMENT '评论数',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `publish_date` datetime NOT NULL COMMENT '发布日期',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `status` int(11) NOT NULL COMMENT '状态：0、草稿 1、已发布 2、待审核',
  `disabled` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY  (`id`),
  KEY `publish_date` (`publish_date`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  KEY `model_id` (`model_id`),
  KEY `parent_id` (`parent_id`),
  KEY `status` (`status`),
  KEY `childs` (`childs`),
  KEY `scores` (`scores`),
  KEY `comments` (`comments`),
  KEY `clicks` (`clicks`),
  KEY `title` (`title`),
  KEY `check_user_id` (`check_user_id`),
  KEY `site_id` (`site_id`),
  KEY `has_files` (`has_files`),
  KEY `has_images` (`has_images`),
  KEY `only_url` (`only_url`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='内容' AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `cms_content`
--

INSERT INTO `cms_content` (`id`, `site_id`, `title`, `user_id`, `check_user_id`, `category_id`, `model_id`, `parent_id`, `copied`, `author`, `editor`, `only_url`, `has_images`, `has_files`, `has_static`, `url`, `description`, `tag_ids`, `cover`, `childs`, `scores`, `comments`, `clicks`, `publish_date`, `create_date`, `status`, `disabled`) VALUES
(1, 1, '测试标题测试标题测试标题测试标题', 1, NULL, 18, 3, NULL, 0, '', '', 0, 0, 0, 0, NULL, '', NULL, '2016/02/27/12-41-5608711778979289.jpg', 0, 0, 0, 0, '2016-02-27 13:24:15', '2016-02-27 13:24:15', 2, 0),
(2, 1, '阿斯蒂芬', 1, 1, 18, 3, NULL, 0, '', '', 0, 0, 0, 0, '', '阿斯蒂芬 ', '', '2016/02/27/12-41-5608711778979289.jpg', 0, 0, 0, 0, '2016-02-27 15:14:59', '2016-02-27 13:25:02', 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `cms_content_attribute`
--

CREATE TABLE IF NOT EXISTS `cms_content_attribute` (
  `content_id` int(11) NOT NULL,
  `source` varchar(50) default NULL COMMENT '内容来源',
  `source_url` varchar(2048) default NULL COMMENT '来源地址',
  `data` longtext COMMENT '数据JSON',
  `text` text COMMENT '内容',
  `word_count` int(11) NOT NULL COMMENT '字数',
  PRIMARY KEY  (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容扩展';

--
-- 转存表中的数据 `cms_content_attribute`
--

INSERT INTO `cms_content_attribute` (`content_id`, `source`, `source_url`, `data`, `text`, `word_count`) VALUES
(2, NULL, NULL, '{}', '<p>aa<br/></p>', 14);

-- --------------------------------------------------------

--
-- 表的结构 `cms_content_file`
--

CREATE TABLE IF NOT EXISTS `cms_content_file` (
  `id` int(11) NOT NULL auto_increment,
  `content_id` int(11) NOT NULL COMMENT '内容',
  `user_id` int(11) NOT NULL COMMENT '用户',
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  `image` tinyint(1) NOT NULL COMMENT '是否图片',
  `size` int(11) NOT NULL COMMENT '大小',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `sort` int(11) NOT NULL COMMENT '排序',
  `description` varchar(300) default NULL COMMENT '描述',
  PRIMARY KEY  (`id`),
  KEY `content_id` (`content_id`),
  KEY `sort` (`sort`),
  KEY `image` (`image`),
  KEY `size` (`size`),
  KEY `clicks` (`clicks`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='内容附件' AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `cms_content_file`
--

INSERT INTO `cms_content_file` (`id`, `content_id`, `user_id`, `file_path`, `image`, `size`, `clicks`, `sort`, `description`) VALUES
(1, 2, 0, '2016/02/27/12-42-3306271645032138.jpg', 1, 0, 0, 1, 'aaa');

-- --------------------------------------------------------

--
-- 表的结构 `cms_content_related`
--

CREATE TABLE IF NOT EXISTS `cms_content_related` (
  `id` int(11) NOT NULL auto_increment,
  `content_id` int(11) NOT NULL COMMENT '内容',
  `related_content_id` int(11) default NULL COMMENT '推荐内容',
  `user_id` int(11) NOT NULL COMMENT '推荐用户',
  `url` varchar(2048) default NULL COMMENT '推荐链接地址',
  `title` varchar(255) default NULL COMMENT '推荐标题',
  `description` varchar(300) default NULL COMMENT '推荐简介',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY  (`id`),
  KEY `content_id` (`content_id`),
  KEY `related_content_id` (`related_content_id`),
  KEY `sort` (`sort`),
  KEY `user_id` (`user_id`),
  KEY `clicks` (`clicks`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='推荐推荐' AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `cms_content_related`
--

INSERT INTO `cms_content_related` (`id`, `content_id`, `related_content_id`, `user_id`, `url`, `title`, `description`, `clicks`, `sort`) VALUES
(1, 2, 2, 0, '//localhost/', '阿斯蒂芬', '阿斯蒂芬 ', 0, 0),
(2, 2, 2, 0, '//localhost/', '阿斯蒂芬', '阿斯蒂芬 ', 0, 0),
(3, 2, 2, 0, '//localhost/', '阿斯蒂芬', '阿斯蒂芬 ', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `cms_content_tag`
--

CREATE TABLE IF NOT EXISTS `cms_content_tag` (
  `id` int(11) NOT NULL auto_increment,
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  `content_id` int(11) NOT NULL COMMENT '内容ID',
  PRIMARY KEY  (`id`),
  KEY `tag_id` (`tag_id`),
  KEY `content_id` (`content_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容标签' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `cms_model`
--

CREATE TABLE IF NOT EXISTS `cms_model` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `parent_id` int(11) default NULL COMMENT '父模型',
  `name` varchar(50) NOT NULL COMMENT '内容模型名称',
  `template_path` varchar(200) default NULL COMMENT '默认内容模板路径',
  `has_child` tinyint(1) NOT NULL COMMENT '拥有子模型',
  `only_url` tinyint(1) NOT NULL default '0' COMMENT '是链接',
  `has_images` tinyint(1) NOT NULL COMMENT '拥有图片列表',
  `has_files` tinyint(1) NOT NULL COMMENT '拥有附件列表',
  `disabled` tinyint(1) NOT NULL COMMENT '是否删除',
  `extend_id` int(11) default NULL COMMENT '扩展ID',
  PRIMARY KEY  (`id`),
  KEY `disabled` (`disabled`),
  KEY `parent_id` (`parent_id`),
  KEY `has_child` (`has_child`),
  KEY `site_id` (`site_id`),
  KEY `has_images` (`has_images`),
  KEY `has_files` (`has_files`),
  KEY `only_url` (`only_url`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='模型' AUTO_INCREMENT=10 ;

--
-- 转存表中的数据 `cms_model`
--

INSERT INTO `cms_model` (`id`, `site_id`, `parent_id`, `name`, `template_path`, `has_child`, `only_url`, `has_images`, `has_files`, `disabled`, `extend_id`) VALUES
(1, 1, NULL, '文章', '/system/article.html', 0, 0, 0, 0, 0, NULL),
(2, 1, NULL, '链接', '', 0, 1, 0, 0, 0, NULL),
(3, 1, NULL, '图集', '/system/picture.html', 0, 0, 1, 0, 0, NULL),
(4, 1, NULL, '图书', '', 0, 0, 0, 0, 0, NULL),
(5, 1, 4, '卷', '', 1, 0, 0, 0, 0, NULL),
(6, 1, 5, '章节', '/system/chapter.html', 0, 0, 0, 0, 0, NULL),
(7, 1, 4, '章节', '/system/chapter.html', 0, 0, 0, 0, 1, NULL),
(8, 1, NULL, 'adfadsf', '', 0, 1, 0, 0, 0, NULL),
(9, 1, NULL, 'aaaa', '/category/list.html', 0, 0, 0, 0, 0, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `cms_page_data`
--

CREATE TABLE IF NOT EXISTS `cms_page_data` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `path` varchar(255) NOT NULL COMMENT '模板路径',
  `type` varchar(50) NOT NULL COMMENT '模板类型',
  `user_id` int(11) NOT NULL COMMENT '推荐用户',
  `item_type` varchar(50) default NULL COMMENT '推荐项目类型',
  `item_id` int(11) default NULL COMMENT '推荐项目ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `url` varchar(2048) default NULL COMMENT '超链接',
  `cover` varchar(255) default NULL COMMENT '封面图',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `publish_date` datetime NOT NULL COMMENT '发布日期',
  `status` int(11) NOT NULL COMMENT '状态：0、前台提交 1、已发布 ',
  `clicks` int(11) NOT NULL COMMENT '点击数',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  PRIMARY KEY  (`id`),
  KEY `path` (`path`),
  KEY `disabled` (`disabled`),
  KEY `publish_date` (`publish_date`),
  KEY `create_date` (`create_date`),
  KEY `site_id` (`site_id`),
  KEY `status` (`status`),
  KEY `item_id` (`item_id`),
  KEY `item_type` (`item_type`),
  KEY `type` (`type`),
  KEY `user_id` (`user_id`),
  KEY `clicks` (`clicks`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='页面数据' AUTO_INCREMENT=7 ;

--
-- 转存表中的数据 `cms_page_data`
--

INSERT INTO `cms_page_data` (`id`, `site_id`, `path`, `type`, `user_id`, `item_type`, `item_id`, `title`, `url`, `cover`, `create_date`, `publish_date`, `status`, `clicks`, `disabled`) VALUES
(4, 1, '/index.html/3.html', 'static', 1, 'content', 2, '阿斯蒂芬', '//localhost/', '2016/02/27/12-41-5608711778979289.jpg', '2016-03-02 22:38:31', '2016-02-27 15:14:59', 1, 0, 0),
(6, 1, '/index.html/3.html', 'dynamic', 1, 'content', 2, '阿斯蒂芬', '//localhost/', '2016/02/27/12-41-5608711778979289.jpg', '2016-03-02 23:00:37', '2016-02-27 15:14:59', 1, 0, 0),
(5, 1, '/index.html/business.html', 'static', 1, 'content', 2, '阿斯蒂芬', '//localhost/', '2016/02/27/12-41-5608711778979289.jpg', '2016-03-02 22:48:32', '2016-02-27 15:14:59', 1, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `cms_page_data_attribute`
--

CREATE TABLE IF NOT EXISTS `cms_page_data_attribute` (
  `page_data_id` int(11) NOT NULL COMMENT '分类ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY  (`page_data_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='推荐位数据扩展';

-- --------------------------------------------------------

--
-- 表的结构 `cms_tag`
--

CREATE TABLE IF NOT EXISTS `cms_tag` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type_id` int(11) default NULL COMMENT '分类ID',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='标签' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `cms_tag_type`
--

CREATE TABLE IF NOT EXISTS `cms_tag_type` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `count` int(11) NOT NULL COMMENT '标签数',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='标签类型' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `log_login`
--

CREATE TABLE IF NOT EXISTS `log_login` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `user_id` int(11) default NULL COMMENT '用户ID',
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='登陆日志' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `log_operate`
--

CREATE TABLE IF NOT EXISTS `log_operate` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `channel` varchar(50) NOT NULL COMMENT '操作取到',
  `operate` varchar(40) NOT NULL COMMENT '操作',
  `ip` varchar(64) default NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `content` varchar(500) NOT NULL COMMENT '内容',
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`),
  KEY `operate` (`operate`),
  KEY `create_date` (`create_date`),
  KEY `ip` (`ip`),
  KEY `site_id` (`site_id`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='操作日志' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `log_task`
--

CREATE TABLE IF NOT EXISTS `log_task` (
  `id` int(11) NOT NULL auto_increment,
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='任务计划日志' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `plugin_lottery`
--

CREATE TABLE IF NOT EXISTS `plugin_lottery` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `interval_hour` int(11) NOT NULL COMMENT '抽奖间隔小时',
  `gift` int(11) NOT NULL COMMENT '每次可抽奖数量',
  `total_gift` int(11) NOT NULL COMMENT '奖品总数',
  `last_gift` int(11) NOT NULL COMMENT '剩余数量',
  `lottery_count` int(11) NOT NULL COMMENT '可抽奖次数',
  `fractions` int(11) NOT NULL COMMENT '概率分子',
  `numerator` int(11) NOT NULL COMMENT '概率分母',
  `url` varchar(2048) default NULL COMMENT '地址',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) default NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `extend_id` int(11) default NULL COMMENT '扩展ID',
  PRIMARY KEY  (`id`),
  KEY `start_date` (`start_date`,`end_date`),
  KEY `disabled` (`disabled`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `plugin_lottery_user`
--

CREATE TABLE IF NOT EXISTS `plugin_lottery_user` (
  `id` int(11) NOT NULL auto_increment,
  `lottery_id` int(11) NOT NULL COMMENT '抽奖ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `winning` tinyint(1) NOT NULL COMMENT '是否中奖',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `attribute_id` int(11) default NULL COMMENT '扩展属性ID',
  PRIMARY KEY  (`id`),
  KEY `lottery_id` (`lottery_id`),
  KEY `user_id` (`user_id`),
  KEY `winning` (`winning`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `plugin_lottery_user_attribute`
--

CREATE TABLE IF NOT EXISTS `plugin_lottery_user_attribute` (
  `lottery_user_id` int(11) NOT NULL COMMENT '抽奖用户ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY  (`lottery_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='抽奖用户扩展';

-- --------------------------------------------------------

--
-- 表的结构 `plugin_site`
--

CREATE TABLE IF NOT EXISTS `plugin_site` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点',
  `plugin_code` varchar(50) NOT NULL COMMENT '插件',
  `widget_template` varchar(255) default NULL COMMENT '内容插件模板',
  `static_template` varchar(255) default NULL COMMENT '静态化模板',
  `path` varchar(2000) default NULL COMMENT '静态化路径',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `site_id` (`site_id`,`plugin_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `plugin_vote`
--

CREATE TABLE IF NOT EXISTS `plugin_vote` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `start_date` datetime NOT NULL COMMENT '开始日期',
  `end_date` datetime NOT NULL COMMENT '结束日期',
  `interval_hour` int(11) NOT NULL COMMENT '投票间隔小时',
  `max_vote` int(11) NOT NULL COMMENT '最大投票数',
  `anonymous` tinyint(1) NOT NULL COMMENT '匿名投票',
  `url` varchar(2048) NOT NULL COMMENT '地址',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) default NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL COMMENT '已禁用',
  `item_extend_id` int(11) NOT NULL COMMENT '扩展ID',
  PRIMARY KEY  (`id`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `plugin_vote_item`
--

CREATE TABLE IF NOT EXISTS `plugin_vote_item` (
  `id` int(11) NOT NULL auto_increment,
  `vote_id` int(11) NOT NULL COMMENT '投票',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(300) default NULL COMMENT '描述',
  `scores` int(11) NOT NULL COMMENT '票数',
  `sort` int(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY  (`id`),
  KEY `lottery_id` (`vote_id`),
  KEY `user_id` (`title`),
  KEY `create_date` (`sort`),
  KEY `scores` (`scores`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `plugin_vote_item_attribute`
--

CREATE TABLE IF NOT EXISTS `plugin_vote_item_attribute` (
  `vote_item_id` int(11) NOT NULL COMMENT '选项ID',
  `data` longtext COMMENT '数据JSON',
  PRIMARY KEY  (`vote_item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='投票选项扩展';

-- --------------------------------------------------------

--
-- 表的结构 `plugin_vote_user`
--

CREATE TABLE IF NOT EXISTS `plugin_vote_user` (
  `id` int(11) NOT NULL auto_increment,
  `lottery_id` int(11) NOT NULL COMMENT '抽奖ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `item_ids` varchar(100) NOT NULL COMMENT '投票选项',
  `ip` varchar(64) NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`id`),
  KEY `lottery_id` (`lottery_id`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_app`
--

CREATE TABLE IF NOT EXISTS `sys_app` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `app_secret` varchar(50) NOT NULL COMMENT 'APP secret',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key` (`app_key`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_app_client`
--

CREATE TABLE IF NOT EXISTS `sys_app_client` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `channel` varchar(20) NOT NULL COMMENT '渠道',
  `uuid` varchar(50) NOT NULL COMMENT '唯一标识',
  `user_id` int(11) default NULL COMMENT '绑定用户',
  `client_version` varchar(50) NOT NULL COMMENT '版本',
  `allow_push` tinyint(1) NOT NULL COMMENT '允许推送',
  `push_token` varchar(50) default NULL COMMENT '推送授权码',
  `last_login_date` datetime default NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(20) default NULL COMMENT '上次登录IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `site_id` (`site_id`,`uuid`,`channel`),
  KEY `user_id` (`user_id`),
  KEY `disabled` (`disabled`),
  KEY `create_date` (`create_date`),
  KEY `allow_push` (`allow_push`),
  KEY `channel` (`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_app_token`
--

CREATE TABLE IF NOT EXISTS `sys_app_token` (
  `id` int(11) NOT NULL auto_increment,
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `auth_token` varchar(40) NOT NULL COMMENT '授权验证',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `auth_token` (`auth_token`),
  KEY `app_id` (`app_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_dept`
--

CREATE TABLE IF NOT EXISTS `sys_dept` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_id` int(11) default NULL COMMENT '父部门ID',
  `description` varchar(300) default NULL COMMENT '描述',
  `user_id` int(11) default NULL COMMENT '负责人',
  `owns_all_category` tinyint(1) NOT NULL COMMENT '拥有全部分类权限',
  `owns_all_page` tinyint(1) NOT NULL COMMENT '拥有全部页面权限',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='部门' AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `sys_dept`
--

INSERT INTO `sys_dept` (`id`, `site_id`, `name`, `parent_id`, `description`, `user_id`, `owns_all_category`, `owns_all_page`) VALUES
(1, 1, '总公司', NULL, '集团总公司', 1, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys_dept_category`
--

CREATE TABLE IF NOT EXISTS `sys_dept_category` (
  `id` int(11) NOT NULL auto_increment,
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  PRIMARY KEY  (`id`),
  KEY `dept_id` (`dept_id`),
  KEY `category_id` (`category_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='部门分类' AUTO_INCREMENT=14 ;

--
-- 转存表中的数据 `sys_dept_category`
--

INSERT INTO `sys_dept_category` (`id`, `dept_id`, `category_id`) VALUES
(1, 1, 18),
(2, 1, 1),
(3, 1, 6),
(4, 1, 7),
(5, 1, 8),
(6, 1, 11),
(7, 1, 9),
(8, 1, 12),
(9, 1, 17),
(10, 1, 13),
(11, 1, 16),
(12, 1, 14),
(13, 1, 15);

-- --------------------------------------------------------

--
-- 表的结构 `sys_dept_page`
--

CREATE TABLE IF NOT EXISTS `sys_dept_page` (
  `id` int(11) NOT NULL auto_increment,
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `type` varchar(50) NOT NULL COMMENT '页面类型',
  `page` varchar(255) NOT NULL COMMENT '页面',
  PRIMARY KEY  (`id`),
  KEY `dept_id` (`dept_id`),
  KEY `page` (`page`),
  KEY `type` (`type`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='部门页面' AUTO_INCREMENT=69 ;

--
-- 转存表中的数据 `sys_dept_page`
--

INSERT INTO `sys_dept_page` (`id`, `dept_id`, `type`, `page`) VALUES
(59, 1, 'dynamic', '/content.html'),
(60, 1, 'dynamic', '/facetSearch.html'),
(63, 1, 'dynamic', '/list.html'),
(64, 1, 'dynamic', '/login.html'),
(65, 1, 'dynamic', '/search.html'),
(66, 1, 'dynamic', '/tags.html'),
(26, 1, 'static', '/category'),
(27, 1, 'static', '/category/list.html'),
(28, 1, 'static', '/category/parent.html'),
(29, 1, 'static', '/category/product_list.html'),
(30, 1, 'static', '/system'),
(31, 1, 'static', '/system/article.html'),
(32, 1, 'static', '/system/book.html'),
(33, 1, 'static', '/system/chapter.html'),
(34, 1, 'static', '/system/picture.html'),
(67, 1, 'dynamic', '/index.html'),
(36, 1, 'static', '/index.html'),
(37, 1, 'static', '/registerMail.html'),
(53, 1, 'dynamic', '/system'),
(54, 1, 'dynamic', '/system/article.html'),
(55, 1, 'dynamic', '/system/book.html'),
(56, 1, 'dynamic', '/system/chapter.html'),
(57, 1, 'dynamic', '/system/picture.html'),
(58, 1, 'dynamic', '/category.html'),
(68, 1, 'dynamic', '/interface.html');

-- --------------------------------------------------------

--
-- 表的结构 `sys_domain`
--

CREATE TABLE IF NOT EXISTS `sys_domain` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) NOT NULL COMMENT '域名',
  `site_id` int(11) NOT NULL COMMENT '站点',
  `path` varchar(255) default NULL COMMENT '路径',
  `login_path` varchar(255) default NULL COMMENT '登陆模板路径',
  `register_path` varchar(255) default NULL COMMENT '注册模板路径',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`),
  KEY `name` (`name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='域名' AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `sys_domain`
--

INSERT INTO `sys_domain` (`id`, `name`, `site_id`, `path`, `login_path`, `register_path`) VALUES
(1, '127.0.0.1', 1, '', '/login.html', NULL),
(2, '127.0.0.1:8080', 1, '', '/login.html', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `sys_email_token`
--

CREATE TABLE IF NOT EXISTS `sys_email_token` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `email` varchar(100) NOT NULL COMMENT '邮件地址',
  `auth_token` varchar(40) NOT NULL COMMENT '验证码',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `auth_token` (`auth_token`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='邮件地址验证日志' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_extend`
--

CREATE TABLE IF NOT EXISTS `sys_extend` (
  `id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `sys_extend_field`
--

CREATE TABLE IF NOT EXISTS `sys_extend_field` (
  `id` int(11) NOT NULL auto_increment,
  `extend_id` int(11) NOT NULL COMMENT '扩展ID',
  `required` tinyint(1) NOT NULL COMMENT '是否必填',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `description` varchar(100) default NULL COMMENT '解释',
  `code` varchar(20) NOT NULL COMMENT '编码',
  `input_type` varchar(20) NOT NULL COMMENT '表单类型',
  `default_value` varchar(50) default NULL COMMENT '默认值',
  PRIMARY KEY  (`id`),
  KEY `item_id` (`extend_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='扩展' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_ftp_user`
--

CREATE TABLE IF NOT EXISTS `sys_ftp_user` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `path` varchar(255) default NULL COMMENT '路径',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `sys_ftp_user`
--

INSERT INTO `sys_ftp_user` (`id`, `site_id`, `name`, `password`, `path`) VALUES
(1, 1, 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `sys_moudle`
--

CREATE TABLE IF NOT EXISTS `sys_moudle` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `url` varchar(255) default NULL COMMENT '链接地址',
  `authorized_url` text COMMENT '授权地址',
  `parent_id` int(11) default NULL COMMENT '父模块',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY  (`id`),
  KEY `url` (`url`),
  KEY `parent_id` (`parent_id`),
  KEY `sort` (`sort`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='模块' AUTO_INCREMENT=107 ;

--
-- 转存表中的数据 `sys_moudle`
--

INSERT INTO `sys_moudle` (`id`, `name`, `url`, `authorized_url`, `parent_id`, `sort`) VALUES
(1, '我的', '', '', NULL, 0),
(2, '内容', '', '', NULL, 0),
(3, '分类', '', '', NULL, 0),
(4, '页面', '', '', NULL, 0),
(5, '维护', '', '', NULL, 0),
(6, '与我相关', '', '', 1, 0),
(7, '修改密码', 'myself/password', 'changePassword', 6, 0),
(8, '我的内容', 'myself/contentList', '', 6, 0),
(9, '我的操作日志', 'myself/logOperate', '', 6, 0),
(10, '我的登陆日志', 'myself/logLogin', '', 6, 0),
(11, '我的登陆授权', 'myself/userTokenList', '', 6, 0),
(12, '内容管理', 'cmsContent/list', 'sysUser/lookup', 2, 0),
(13, '标签管理', '', '', 2, 0),
(14, '标签管理', 'cmsTag/list', 'cmsTagType/lookup', 13, 0),
(15, '增加/修改', 'cmsTag/add', 'cmsTagType/lookup,cmsTag/save', 14, 0),
(16, '删除', 'cmsTag/delete', '', 14, 0),
(17, '增加/修改', 'cmsContent/add', 'cmsContent/addMore,file/upload,file/doUpload,cmsContent/lookup,cmsContent/lookup_list,cmsTag/suggest,cmsContent/save,ueditor', 12, 0),
(18, '删除', 'cmsContent/delete', '', 12, 0),
(19, '审核', 'cmsContent/check', '', 12, 0),
(20, '刷新', 'cmsContent/refresh', '', 12, 0),
(21, '生成', 'cmsContent/publish', '', 12, 0),
(22, '移动', 'cmsContent/moveParameters', 'cmsContent/move', 12, 0),
(23, '推荐', 'cmsContent/push', 'cmsContent/push_content,cmsContent/push_content_list,cmsContent/push_to_content,cmsContent/push_page,cmsContent/push_page_list,cmsContent/push_to_place', 12, 0),
(24, '分类管理', 'cmsCategory/list', '', 3, 0),
(25, '增加/修改', 'cmsCategory/add', 'cmsCategory/addMore,staticTemplate/lookup,cmsCategory/categoryPath,cmsCategory/contentPath,file/upload,file/doUpload,cmsTagType/suggest,cmsCategory/save', 24, 0),
(26, '删除', 'cmsCategory/delete', '', 24, 0),
(27, '生成', 'cmsCategory/publish', 'cmsCategory/publishParameters', 24, 0),
(28, '移动', 'cmsCategory/moveParameters', 'cmsCategory/move,cmsCategory/lookup', 24, 0),
(29, '静态页面管理', 'cmsPage/staticPlaceList', 'sysUser/lookup,placeDataList', 4, 0),
(30, '动态页面管理', 'cmdPage/dynamicPlaceList', 'sysUser/lookup,placeDataList', 4, 0),
(31, '分类扩展', '', '', 3, 0),
(32, '分类类型', 'cmsCategoryType/list', '', 31, 0),
(33, '标签分类', 'cmsTagType/list', '', 31, 0),
(34, '增加/修改', 'cmsTagType/add', 'cmsTagType/save', 33, 0),
(35, '删除', 'cmsTagType/delete', '', 33, 0),
(36, '增加/修改', 'cmsCategoryType/add', 'cmsCategoryType/save', 32, 0),
(37, '删除', 'cmsCategoryType/delete', '', 32, 0),
(38, '模板管理', '', '', 4, 0),
(39, '静态化模板', 'staticTemplate/list', 'staticTemplate/directory', 38, 0),
(40, '动态模板', 'dynamicTemplate/list', 'dynamicTemplate/directory', 38, 0),
(41, '修改模板', 'staticTemplate/metadata', 'cmsTemplate/saveMetadata,staticTemplate/content,cmsTemplate/save,staticTemplate/chipLookup', 39, 0),
(42, '修改推荐位', 'staticTemplate/placeList', 'staticTemplate/placeMetadata,cmsTemplate/saveMetadata,staticTemplate/createPlace', 39, 0),
(43, '删除模板', 'cmsTemplate/delete', '', 39, 0),
(44, '修改模板', 'dynamicTemplate/metadata', 'cmsTemplate/saveMetadata,dynamicTemplate/content,cmsTemplate/save,dynamicTemplate/chipLookup', 40, 0),
(45, '修改推荐位', 'dynamicTemplate/placeList', 'dynamicTemplate/placeMetadata,cmsTemplate/saveMetadata,dynamicTemplate/createPlace', 40, 0),
(46, '删除模板', 'cmsTemplate/delete', '', 40, 0),
(47, '生成页面', 'cmsTemplate/publish', '', 29, 0),
(48, '保存页面元数据', 'cmsPage/saveMetaData', 'file/upload,file/doUpload', 29, 0),
(49, '增加/修改推荐位数据', 'cmsPage/placeDataAdd', 'cmsPage/lookup,cmsPage/lookup_content_list,file/upload,file/doUpload', 29, 0),
(50, '删除推荐位数据', 'cmsPage/delete', '', 29, 0),
(51, '刷新推荐位数据', 'cmsPage/refresh', '', 29, 0),
(52, '审核推荐位数据', 'cmsPage/check', '', 29, 0),
(53, '发布推荐位', 'cmsTemplate/publishPlace', '', 29, 0),
(54, '清空推荐位数据', 'cmsPage/clear', '', 29, 0),
(55, '保存页面元数据', 'cmsPage/saveMetaData', '', 30, 0),
(56, '增加/修改推荐位数据', 'cmsPage/placeDataAdd', 'cmsPage/lookup,cmsPage/lookup_content_list,file/upload,file/doUpload', 30, 0),
(57, '删除推荐位数据', 'cmsPage/delete', '', 30, 0),
(58, '刷新推荐位数据', 'cmsPage/refresh', '', 30, 0),
(59, '审核推荐位数据', 'cmsPage/check', '', 30, 0),
(60, '清空推荐位数据', 'cmsPage/clear', '', 30, 0),
(61, '用户管理', '', '', 5, 0),
(62, '系统维护', '', '', 5, 0),
(63, '日志管理', '', '', 5, 0),
(64, '操作日志', 'log/operate', 'sysUser/lookup', 63, 0),
(65, '登录日志', 'log/login', 'sysUser/lookup', 63, 0),
(66, '任务计划日志', 'log/task', 'sysUser/lookup', 63, 0),
(67, '删除', 'logOperate/delete', '', 64, 0),
(68, '删除', 'logLogin/delete', '', 65, 0),
(69, '删除', 'logTask/delete', '', 66, 0),
(70, '查看', 'log/taskView', '', 66, 0),
(71, '用户管理', 'sysUser/list', '', 61, 0),
(72, '部门管理', 'sysDept/list', 'sysDept/lookup,sysUser/lookup', 61, 0),
(73, '角色管理', 'sysRole/list', '', 61, 0),
(74, '增加/修改', 'sysUser/add', 'sysDept/lookup,sysUser/save', 71, 0),
(75, '启用', 'sysUser/enable', '', 71, 0),
(76, '禁用', 'sysUser/disable', '', 71, 0),
(77, '增加/修改', 'sysDept/add', 'sysDept/lookup,sysUser/lookup,sysDept/save', 72, 0),
(78, '删除', 'sysDept/delete', '', 72, 0),
(79, '增加/修改', 'sysRole/add', 'sysRole/save', 73, 0),
(80, '删除', 'sysRole/delete', '', 73, 0),
(81, '模型管理', 'cmsModel/list', '', 62, 0),
(82, '任务计划', 'sysTask/list', '', 62, 0),
(83, 'FTP用户', 'cmsFtpUser/list', '', 62, 0),
(84, '动态域名', 'cmsDomain/list', '', 62, 0),
(85, '任务计划脚本', 'taskTemplate/list', '', 38, 0),
(86, '修改脚本', 'taskTemplate/metadata', 'cmsTemplate/saveMetadata,taskTemplate/content,cmsTemplate/save,taskTemplate/chipLookup', 85, 0),
(87, '删除脚本', 'cmsTemplate/delete', '', 85, 0),
(88, '用户登录授权', 'sysUserToken/list', 'sysUser/lookup', 61, 0),
(89, '删除', 'sysUserToken/delete', '', 88, 0),
(90, '增加/修改', 'cmsModel/add', 'cmsModel/save,staticTemplate/lookup', 81, 0),
(91, '删除', 'cmsModel/delete', '', 81, 0),
(92, '增加/修改', 'sysTask/add', 'sysTask/save,sysTask/example,taskTemplate/lookup', 82, 0),
(93, '删除', 'sysTask/delete', '', 82, 0),
(94, '立刻执行', 'sysTask/runOnce', '', 82, 0),
(95, '暂停', 'sysTask/pause', '', 82, 0),
(96, '恢复', 'sysTask/resume', '', 82, 0),
(97, '重新初始化', 'sysTask/recreate', '', 82, 0),
(98, '增加/修改', 'cmsFtpUser/add', 'cmsFtpUser/save', 83, 0),
(99, '删除', 'cmsFtpUser/delete', '', 83, 0),
(100, '修改', 'cmsDomain/add', 'cmsDomain/save', 84, 0),
(102, '修改', 'cmsContent/add', 'cmsContent/addMore,file/upload,file/doUpload,cmsContent/lookup,cmsContent/lookup_list,cmsTag/suggest,cmsContent/save,ueditor', 8, 0),
(103, '删除', 'cmsContent/delete', '', 8, 0),
(104, '刷新', 'cmsContent/refresh', '', 8, 0),
(105, '生成', 'cmsContent/publish', '', 8, 0),
(106, '推荐', 'cmsContent/push', 'cmsContent/push_content,cmsContent/push_content_list,cmsContent/push_to_content,cmsContent/push_page,cmsContent/push_page_list,cmsContent/push_to_place', 8, 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys_role`
--

CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `owns_all_right` tinyint(1) NOT NULL COMMENT '拥有全部权限',
  `show_all_moudle` tinyint(1) NOT NULL COMMENT '显示全部模块',
  PRIMARY KEY  (`id`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='角色' AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `sys_role`
--

INSERT INTO `sys_role` (`id`, `site_id`, `name`, `owns_all_right`, `show_all_moudle`) VALUES
(1, 1, '超级管理员', 1, 0),
(2, 1, '测试管理员', 0, 1),
(3, 2, '站长', 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys_role_authorized`
--

CREATE TABLE IF NOT EXISTS `sys_role_authorized` (
  `id` int(11) NOT NULL auto_increment,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `url` varchar(255) NOT NULL COMMENT '授权地址',
  PRIMARY KEY  (`id`),
  KEY `role_id` (`role_id`),
  KEY `url` (`url`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色授权地址' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_role_moudle`
--

CREATE TABLE IF NOT EXISTS `sys_role_moudle` (
  `id` int(11) NOT NULL auto_increment,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `moudle_id` int(11) NOT NULL COMMENT '模块ID',
  PRIMARY KEY  (`id`),
  KEY `role_id` (`role_id`),
  KEY `moudle_id` (`moudle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色授权模块' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `sys_role_user`
--

CREATE TABLE IF NOT EXISTS `sys_role_user` (
  `id` int(11) NOT NULL auto_increment,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY  (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='用户角色' AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `sys_role_user`
--

INSERT INTO `sys_role_user` (`id`, `role_id`, `user_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

-- --------------------------------------------------------

--
-- 表的结构 `sys_site`
--

CREATE TABLE IF NOT EXISTS `sys_site` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `site_path` varchar(255) NOT NULL COMMENT '站点地址',
  `dynamic_path` varchar(255) NOT NULL COMMENT '动态站点地址',
  `resource_path` varchar(255) NOT NULL COMMENT '资源站点地址',
  `disabled` tinyint(1) NOT NULL COMMENT '禁用',
  PRIMARY KEY  (`id`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='站点' AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `sys_site`
--

INSERT INTO `sys_site` (`id`, `name`, `site_path`, `dynamic_path`, `resource_path`, `disabled`) VALUES
(1, '测试', '//localhost/', '//127.0.0.1/', '//localhost/upload/', 0),
(2, '测试站点', '//localhost/', '//localhost/', '//localhost/upload/', 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys_task`
--

CREATE TABLE IF NOT EXISTS `sys_task` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `status` int(11) NOT NULL COMMENT '状态',
  `cron_expression` varchar(50) NOT NULL COMMENT '表达式',
  `description` varchar(300) default NULL COMMENT '描述',
  `file_path` varchar(255) default NULL COMMENT '文件路径',
  PRIMARY KEY  (`id`),
  KEY `status` (`status`),
  KEY `site_id` (`site_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='任务计划' AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `sys_task`
--

INSERT INTO `sys_task` (`id`, `site_id`, `name`, `status`, `cron_expression`, `description`, `file_path`) VALUES
(1, 1, '重新生成所有页面', 0, '0 0/2 * * ?', '重新生成所有页面', 'staticPage.task'),
(2, 1, '重建索引', 0, '0 0 1 1 ? 2099', '重建全部索引', 'reCreateIndex.task'),
(3, 1, '清理日志', 0, '0 0 1 * ?', '清理三个月以前的日志', 'clearLog.task'),
(4, 1, '重新生成内容页面', 0, '0 0 1 1 ? 2099', '重新生成内容页面', 'staticContent.task'),
(5, 1, '重新生成所有分类页面', 0, '0 0/6 * * ?', '重新生成所有分类页面', 'staticCategory.task'),
(7, 1, '重新生成全站', 0, '0 0 1 1 ? 2099', '重新生成全站', 'staticAll.task');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(11) NOT NULL auto_increment,
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户' AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`id`, `site_id`, `name`, `password`, `nick_name`, `dept_id`, `roles`, `email`, `email_checked`, `superuser_access`, `disabled`, `last_login_date`, `last_login_ip`, `login_count`, `registered_date`) VALUES
(1, 1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', 1, '1', 'master@sanluan.com', 0, 1, 0, '2016-03-16 17:45:51', '127.0.0.1', 108, '2015-07-06 00:00:00'),
(2, 1, 'test', '098f6bcd4621d373cade4e832627b4f6', '演示账号', 1, '2', 'test@test.com', 0, 1, 0, '2015-07-28 15:55:54', '127.0.0.1', 0, '2015-07-06 00:00:00'),
(3, 2, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', NULL, '3', NULL, 0, 1, 0, '2016-03-16 16:10:21', '127.0.0.1', 2, '2016-03-08 17:42:26');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user_token`
--

CREATE TABLE IF NOT EXISTS `sys_user_token` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `auth_token` varchar(40) NOT NULL COMMENT '登陆授权',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `login_ip` varchar(20) NOT NULL COMMENT '登陆IP',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `auth_token` (`auth_token`),
  KEY `user_id` (`user_id`),
  KEY `create_date` (`create_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户令牌' AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
