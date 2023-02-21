# safe-building-backend
Download .msi:https://github.com/microsoftarchive/redis/releases

cmd real server: C:\Program Files\Redis>redis-server redis.windows.conf

cmd start test server: redis-server --port 6380 --slaveof 127.0.0.1 6379
