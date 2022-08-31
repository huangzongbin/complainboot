#!/bin/sh

## java环境地址
export JAVA_HOME=/usr/local/jdk1.8.0_271
export JRE_HOME=$JAVA_HOME/jre

## 项目路径
SERVICE_DIR=/home/complain-test
## 服务名称
SERVICE_NAME=complainboot
## jar名称
JAR_NAME=$SERVICE_NAME\.jar
## 进程名称
PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR

case "$1" in

    start)
          nohup $JRE_HOME/bin/java -jar  -Xms1024m -Xmx1024m  $JAR_NAME --spring.profiles.active=test >/dev/null 2>&1 &
        echo $! > $SERVICE_DIR/$PID
        echo "=== start $SERVICE_NAME"
        ;;

    stop)
        kill `cat $SERVICE_DIR/$PID`
        rm -rf $SERVICE_DIR/$PID
        echo "=== stop $SERVICE_NAME"

        sleep 5

        P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "=== $SERVICE_NAME process not exists or stop success"
        else
            echo "=== $SERVICE_NAME process pid is:$P_ID"
            echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
        fi
        ;;

    restart)
        $0 stop
        sleep 2
        $0 start
        echo "=== restart $SERVICE_NAME"
        ;;

    *)
        ## restart
        $0 stop
        sleep 2
        $0 start
        ;;

esac
exit 0

