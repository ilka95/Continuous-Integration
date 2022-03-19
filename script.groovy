def status
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:''' 
          sudo -S docker run --name mynginx -p 9889:80 -d -v $WORKSPACE/index/:/usr/share/nginx/html/ nginx < /tmp/pass
        ''')
      }
    }
    stage('Check Nginx Status') {
      steps {
        sh 'curl -s -o /dev/null -w "%{http_code}" localhost:9889 > status'
        script {
          status = sh (
            script: 'cat status',
            returnStdout: true
          ).trim()
          if("${status}"=="200") {
            println("Nginx is UP correctly!")
          } else {
            println("Nginx is UP incorrectly! Status = ${status}")
          }
        }
      }
      stage('Check md5sum') {
      steps {
        script {
          def sum = sh (
            script: 'cat index/index.html | md5sum',
            returnStdout: true
          ).trim()
          def result = sh (
            script: 'curl localhost:9889 | grep ${sum}',
            returnStdout: true
          ).trim()
          
          if("${result}" != null) {
            println("Index.html is correct!")
          } else {
            println("Index.html is incorrect!")
          }
        }
      }
    }
  }
}
