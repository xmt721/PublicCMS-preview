-- 20160414 --
update `cms_category` set extend_id = NULL where extend_id = 0;
update `cms_category_type` set extend_id = NULL where extend_id = 0;
update `cms_model` set extend_id = NULL where extend_id = 0;
ALTER TABLE  `sys_user_token` ADD  `site_id` INT NOT NULL COMMENT  '站点ID' AFTER  `auth_token` , ADD INDEX (  `site_id` );
ALTER TABLE  `sys_extend` ADD  `item_type` varchar(20) NOT NULL COMMENT '扩展类型' AFTER  `id`,ADD  `item_id` INT NOT NULL COMMENT  '扩展项目ID' AFTER  `item_type`;
UPDATE  `public_cms`.`sys_extend` SET  `item_type` =  'model',`item_id` =  7 WHERE  `sys_extend`.`id` =1;
UPDATE  `public_cms`.`sys_extend` SET  `item_type` =  'category',`item_id` =  19 WHERE  `sys_extend`.`id` =2;
UPDATE  `public_cms`.`sys_extend` SET  `item_type` =  'category',`item_id` =  20 WHERE  `sys_extend`.`id` =3;
-- end --