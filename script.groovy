def status
pipeline {
  agent any
  stages {
    stage('Run Docker') {
        sh 'sudo -S docker run --name mynginx -p 9889:80 -d -v /$WORKSPACE/index/:/usr/share/nginx/html/ nginx < /tmp/pass'
    }
    stage('Check Nginx Status') {
        sh 'curl -s -o /dev/null -w "%{http_code}" localhost:9889 > status.txt'
        status = sh (
          script: 'cat status.txt',
          returnStdout:true
        ).trim()
        if("${status}"=="200") {
          println("Nginx is UP correctly")
        }
    }
  }
}
