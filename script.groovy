def status
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:''' 
          sudo -S docker run --name mynginx -p 9889:80 -d -v /$WORKSPACE/index/:/usr/share/nginx/html/ nginx < /tmp/pass
        ''')
      }
    }
    stage('Check Nginx Status') {
      steps {
        sh 'curl -s -o /dev/null -w "%{http_code}" localhost:9889 > status.txt'
        script {
          status = cat status.txt
          if("${status}"=="200") {
            println("Nginx is UP correctly")
          }
        }
      }
    }
  }
}
