#!/bin/bash
# 使用时请将该文件重命名为 db_bak_recover.sh
set -x

# 备份文件路径
bakPath="$1"
# 数据库名称
database="$2"

# 备份数据库表 ope_data_bak
mysqldump -u root $database ope_data_bak > ./ope_data_bak.sql
# 删除旧数据库
mysqladmin -u root drop -f $database
# 创建临时数据库
mysqladmin -u root create $database
# 从备份文件恢复到临时数据库
mysql -u root $database < $bakPath
# 杀掉所有已连接会话
mysqladmin -u root processlist | awk -F "|" '{print $2}'| xargs -n 1 mysqladmin -u root kill
# 删除旧的表 ope_data_bak
mysql -u root $database -e "drop table ope_data_bak"
# 恢复数据库表 ope_data_bak
mysql -u root $database < ./ope_data_bak.sql
# 删除备份表文件
rm -f ./ope_data_bak.sql

# 完成标志
echo "\$FINISH\$"

set +x
