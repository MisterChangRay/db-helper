# db-helper
数据库辅助,迁移工具


# 特性
- 支持mysql数据库迁移
  当服务进行环境迁移时， 需要进行数据库迁移。比如当数据库记录上亿时，全量迁移耗时耗力，
  可以先根据时间或其他业务条件迁移大部分数据。上线时再将剩下的数据进行迁移以节省时间
  
- 支持redis数据迁移，使用scan进行扫描，自定义频率
  配置账号密码即可进行迁移， 在数据量不大时可以使用。
  
- 支持定义任务脚本，比如分库分表创建
  根据cron进行定时任务执行，可以自动递增或日期时间进行分表
  
- 支持数据库数据清洗
  遍历表所有数据并根据js脚本来更新数据。分批拉取自定义大小和频率。不用担心数据库压力。
  