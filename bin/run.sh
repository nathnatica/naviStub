#!/bin/sh
#*************************************************************************
#*************************************************************************
result=100


HOME_DIR="/home/tomcat/navistub"
PROCESS="naviStub-0.0.1-SNAPSHOT.jar"
LOG_DIR="/home/tomcat/navistub"
LISTEN_PORT="18787"

#*************************************************************************
# Start
#*************************************************************************
func_start() {

  func_status > /dev/null 2>&1
  if [ $? -eq 0 ];
  then
     echo "NaviStub Aleady Started"
     result=0
  else

    cd ${HOME_DIR}
    java -jar ${PROCESS} \
      --server.port=${LISTEN_PORT} > ${LOG_DIR}/log.log 2>&1 &
   func_status > /dev/null 2>&1
    if [ $? -eq 0 ];
    then
      result=0
    fi 
  fi

  return ${result}

}


#*************************************************************************
# Status
#*************************************************************************
func_status() {

  PROCESS_COUNT="0"

  PROCESS_COUNT=`ps -ef | grep ${PROCESS} | grep ${LISTEN_PORT} | grep -v grep | wc -l`
  
  if [ ${PROCESS_COUNT} -ne 0 ] ;
  then
    echo "NaviStub is Active"
    result=0
  else
    echo "NaviStub is NOT Active"
  fi
  return ${result}

}

#*************************************************************************
# Stop
#*************************************************************************
func_stop() {

  PROCESS_PID="0"

  func_status > /dev/null 2>&1
  if [ $? -ne 0 ] ;
  then
     echo "NaviStub Aleady Stopped"
     result=0
  else

    PROCESS_PID=`ps -ef | grep ${PROCESS} | grep ${LISTEN_PORT} | grep -v grep | awk {print'$2'}`

    kill -15 ${PROCESS_PID}
    sleep 5

    func_status > /dev/null 2>&1
    if [ $? -ne 0 ];
    then
      result=0
    fi
  fi


  return ${result}

}


#*************************************************************************
# Main
#*************************************************************************

case "$1" in
  start)
        func_start
        ;;
  status)
        func_status
        ;;
  stop)
        func_stop
        ;;
  *)
        echo "Argument Error"
        ;;
esac

exit ${result}

