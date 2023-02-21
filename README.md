# safe-building-backend
Download .msi:https://github.com/microsoftarchive/redis/releases

After download need to stop redis client in port 6379:
1. cmd: C:\Program Files\Redis>redis-cli.exe
2. shutdown
3. exit

After that use cmd below
cmd real server: C:\Program Files\Redis>redis-server redis.windows.conf

cmd start test server: redis-server --port 6380 --slaveof 127.0.0.1 6379
