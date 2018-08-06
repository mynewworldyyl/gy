use gy;

insert into t_client_type(id,description,name,typecode,created_on,updated_on) values("1","Admin","Admin","Admin",now(),now());
insert into t_client_type(id,description,name,typecode,created_on,updated_on) values("2","Common","Common","Common",now(),now());

insert into t_client(id,description,name,typecode,remark,parent_id,created_on,updated_on) 
values("1","Admin","Admin","1","Admin",null,now(),now());
insert into t_client(id,description,name,typecode,remark,parent_id,created_on,updated_on) 
values("2","Common","Common","2","Common",null,now(),now());

insert into t_account(id,client_id,addr_l1,addr_l2,desc0,home_phone,icon_url,mobile,nick_name,
 office_phone,pwd,remark,status,type_code,acct_name,created_on,updated_on)
values(0,"2","addr1","addr2","admin","1234","icon","1111","admin","1111","888888","","Active","Admin","Admin",now(),now());

insert into t_attr( id, belong_id, created_on, model ,name ,type ,updated_on ,value ,client_id,created_by,updated_by) 
values("1",1,now(),"Account","bbsAdmin","boolean",now(),true,"2",0,0);


alter table t_img_match modify coll_li_id bigint(20) not null;
alter table t_img_match modify lost_info_id bigint(20) not null;

alter table t_lost_info modify channel_num varchar(8);

alter table t_img_match add constraint im_lost_info_key foreign key(lost_info_id) REFERENCES t_lost_info(id);
alter table t_img_match add constraint im_cl_key foreign key(coll_li_id) REFERENCES t_collect_lost_info(id);