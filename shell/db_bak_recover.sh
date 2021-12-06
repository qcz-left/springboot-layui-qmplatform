#!/bin/bash
set -x

# 备份文件路径
bakPath="$1"
# 数据库名称
database="$2"
# 临时数据库名称
tmpDatabase="$database"_tmp

createdb -U postgres $tmpDatabase
pg_restore -U postgres -d $tmpDatabase $bakPath
psql -U postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE datname='$database' AND pid<>pg_backend_pid()"
# 备份数据库表 ope_data_bak
pg_dump -U postgres -t ope_data_bak $database > ./ope_data_bak.sql
# 删除旧数据库
dropdb -U postgres $database
# 修改数据名为原来的
psql -U postgres -c "update pg_database set datname = '$database' where datname = '$tmpDatabase'"
# 删除旧的表 ope_data_bak
psql -U postgres -d $database -c "DROP TABLE ope_data_bak"
# 恢复数据库表 ope_data_bak
psql -U postgres -d $database -f ./ope_data_bak.sql
# 删除备份表文件
rm -f ./ope_data_bak.sql

echo "Finish."
# 完成标志
echo "$FINISH$"

set +x
